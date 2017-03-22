/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pk.com.rsoft.classcontractstestbed.testsequences;

import java.io.Serializable;
import java.util.ArrayList;

import jmetal.core.Variable;

import org.jgap.InvalidConfigurationException;

import pk.com.rsoft.classcontractstestbed.classcontract.CTConstraint;
import pk.com.rsoft.classcontractstestbed.classcontract.CTMethodParameter;
import pk.com.rsoft.classcontractstestbed.classcontract.CTOperation;
import pk.com.rsoft.classcontractstestbed.classcontract.ConstraintType;
import pk.com.rsoft.classcontractstestbed.util.graph.ConsType;
import pk.com.rsoft.classcontractstestbed.util.graph.State;
import pk.com.rsoft.classcontractstestbed.util.graph.Transition;
import pk.com.rsoft.classcontractstestbed.util.inequality.InEqualitySimplified;
import pk.com.rsoft.classcontractstestbed.util.inequality.InequalitySolver;
import pk.com.rsoft.classcontractstestbed.util.parser.NumberPorcessor;
import pk.com.rsoft.testsequenceoptimization.ga.AFSMHolder;

/**
 *
 * @author Hammad
 */
public class TestTransition implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int FIX_NUMBER = 5;
	private State preState ;
	private State postState;
	CTOperation opt;
	public TestTransition(CTOperation op, State preState, State postState) {
		this.setFromState(preState);
		this.setToState(postState);
		this.opt = op;
	}

	public String toBooleanString() throws InvalidConfigurationException
    {
		String retVal = getBoolStateString(getFromState())+getMethodBoolString()+getBoolStateString(getToState());
		return retVal;
    }

        public int compareTo(Object o) {
       if(o instanceof TestTransition)
       {
           TestTransition w = (TestTransition) o;
           if(this.getFromState().isReachable(this.getToState()))
           {
        	   if(w.getFromState().isReachable(w.getToState()))
        	   {
        		   return 0;
        	   }
        	   else
        	   {
        		   return 1;
        	   }
           }
           else if(w.getFromState().isReachable(w.getToState()))
           {
        	   return -1;
           }
       }
       return 0;
}
 private int getNumberOfBitsforState()
 {
	 return FIX_NUMBER;
 }
 
 private int getNumberOfBitsforMethodNumber()
 {
	 return FIX_NUMBER;
 }
  
 private String getBoolStateString(State st)
 {
		String retBoolVal = st.toBooleanString();
		if(retBoolVal.length()<getNumberOfBitsforState())
		{
			retBoolVal = NumberPorcessor.padeZeros(retBoolVal,getNumberOfBitsforState() - retBoolVal.length(), true);			
		}
		return retBoolVal;
 }
 private String getMethodBoolString()
 {
	 	String retBoolVal =Integer.toBinaryString(opt.getMethodNumber());
	    retBoolVal = NumberPorcessor.padeZeros(retBoolVal,getNumberOfBitsforMethodNumber()-retBoolVal.length(), true);
	    return retBoolVal;
 }

public State getFromState() {
	return preState;
}

public void setFromState(State preState) {
	this.preState = preState;
}

public State getToState() {
	return postState;
}

public void setToState(State postState) {
	this.postState = postState;
}
public String getMethodName()
{
	State st = this.getFromState();
	for(Transition t :st.getArNextStates())
	{
		if(t.getToState().isSameAs(this.getToState()))
		{
			return t.getStrTitle();
		}
	}
	return null;
}
@Override
public String toString()
{
	return getMethodName();
}
public ArrayList<InEqualitySimplified> getParameterConstraints()
{	
	//ArrayList<InEqualitySimplified> retVal = new ArrayList<InEqualitySimplified>();
	return InequalitySolver.getOperationConstraintList(AFSMHolder.getOCLContext(), opt, ConsType.PARAM);
}
public Object[] getParamValues()
{
	if(opt.getParameters().size()==0 ||opt.getParameters().get(0).getName().trim().equals(""))
	{
		return new Object[0];
	}

	ArrayList<Object> retList = new ArrayList<Object>();
	for(InEqualitySimplified inq:getParameterConstraints())
	{
		System.out.println(inq.toString());
		retList.add(inq.getCurentValue());
	}

	return retList.toArray();
			
}
public Class[] getParameterTypes()
{
	System.out.println(opt.getParameters().size());
	if(opt.getParameters().size()==0 ||opt.getParameters().get(0).getName().trim().equals(""))
	{
		return new Class[0];
	}
	System.out.println(opt.getParameters().get(0).getName());
	Class[] retParamTypes = new Class[this.opt.getParameters().size()];
	int index =0;
	for(CTMethodParameter param: opt.getParameters())
	{
		retParamTypes[index++] = param.getJavaType();
	}
	
	return retParamTypes;
}
}
