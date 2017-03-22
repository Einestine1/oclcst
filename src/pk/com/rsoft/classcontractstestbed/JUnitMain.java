/**
 * 
 */
package pk.com.rsoft.classcontractstestbed;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.*;
import pk.com.rsoft.classcontractstestbed.testsequences.TestTransition;
import pk.com.rsoft.classcontractstestbed.testsequences.TestSequence;
import pk.com.rsoft.classcontractstestbed.util.graph.AbstractFSM;
import pk.com.rsoft.classcontractstestbed.util.graph.State;
import pk.com.rsoft.classcontractstestbed.util.inequality.InEqualitySimplified;
import pk.com.rsoft.classcontractstestbed.util.inequality.InequalityOperatorType;
import pk.com.rsoft.classcontractstestbed.util.inequality.InequationSolver;
import pk.com.rsoft.classcontractstestbed.util.io.ObjectIO;
import pk.com.rsoft.testsequenceoptimization.ga.AFSMHolder;

/**
 * @author Rehan
 *
 */
public class JUnitMain {
	Class<?> testCls;
	Object ObjUnderTest;
	Field fieldsOfObjUnderTest [];
	int erroCount=0;
	/**
	 * 
	 */
	public JUnitMain() {

		loadFSM();	
		
		ArrayList<String> clsVarNames = AFSMHolder.getFSM().getClassVariableNames();
		int testCount = 0;
		try{
			testCls = Class.forName("pk.com.rsoft.classcontractstestbed.models.CoinBox");
			ObjUnderTest = testCls.newInstance();
			fieldsOfObjUnderTest = new Field[clsVarNames.size()];
			for(int index=0;index<fieldsOfObjUnderTest.length;index++)
			{
				fieldsOfObjUnderTest[index] = testCls.getDeclaredField(clsVarNames.get(index));
				fieldsOfObjUnderTest[index].setAccessible(true);
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}

	    for(TestSequence aSeq:AFSMHolder.getFSM().getGeneratedSequences() )
		{
	    		System.out.println("Runing Test Case Number " +(++testCount));	    		
	    		runTest(aSeq);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new JUnitMain();
	}

	private void runTest(TestSequence seq)
	{
		// TODO Auto-generated method stub
		
		try {

		    for(TestTransition call: seq.getMethodCalls())
		    {
		    	this.runMethodCall(call);
		    	if(isError(call.getToState()))
		    	{
		    		erroCount++;
//		    		System.out.println(call.toString());
//		    		System.out.println(ObjUnderTest);
		    		printFields();
		    		
		    	}
		    }
			
		    printFields();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
	}
	public synchronized void runMethodCall(TestTransition aCall)
	{

//		System.out.println(aCall.toString());
		System.out.println(aCall.getParamValues().length);
			try {
				Method aMethod =  testCls.getMethod(aCall.toString(),aCall.getParameterTypes());
				if(aCall.getParamValues().length==0)
				{
					System.out.println("Lenght of returned Parameters :"+aCall.getParamValues().length);
					return ;
				}
				aMethod.invoke(ObjUnderTest, aCall.getParamValues());
				
			} catch(Exception e) {	// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public void loadFSM()
	{
		AFSMHolder.setFSM((AbstractFSM)ObjectIO.fromFile("F:\\Rehan\\AFSM.fsm"));
	}
	public void printFields()
	{
		for(Field f: fieldsOfObjUnderTest)
		{
			try {
				System.out.println(f.getType() +" " +f.getName()+ " " + f.get(ObjUnderTest));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public boolean isError(State ExpectedState)
	{
		ArrayList<InEqualitySimplified> expectedStateValues = ExpectedState.getCurrentValues();
		String tempStr;
		for(InEqualitySimplified inq: expectedStateValues)
		{
			Field temp = getFieldByName(inq.getVariableName());
			try {
				if(InequalityOperatorType.toString(inq.getType()).equals("="))
				{
					tempStr = temp.get(ObjUnderTest).toString() + "="+InequalityOperatorType.toString(inq.getType())+inq.getCurentValue().toString();
					
				}
				else
				{
					tempStr = temp.get(ObjUnderTest).toString() + InequalityOperatorType.toString(inq.getType())+inq.getCurentValue().toString();

				}
				Object retVal = InequationSolver.evaluate(tempStr);
				if(retVal==null)
				{
					return true;
				}
				Boolean b = Boolean.parseBoolean(retVal.toString());
				System.out.println("Erro Count :" + this.erroCount);
				return b;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	public Field getFieldByName(String strName)
	{
		for(int index =0; index<fieldsOfObjUnderTest.length;index++)
		{
			if(fieldsOfObjUnderTest[index].getName().equals(strName))
			{
				return fieldsOfObjUnderTest[index];
			}
		}
		return null;
	}
}
