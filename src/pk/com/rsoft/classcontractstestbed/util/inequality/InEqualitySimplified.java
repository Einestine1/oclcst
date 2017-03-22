/**
 * 
 */
package pk.com.rsoft.classcontractstestbed.util.inequality;

import java.io.Serializable;
import java.util.ArrayList;

import pk.com.rsoft.classcontractstestbed.classcontract.CTVariableType;
import pk.com.rsoft.classcontractstestbed.util.parser.CTStringParser;

/**
 * @author Rehan
 *
 */
public class InEqualitySimplified implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Variable LHSVariable;
	private InequalityOperatorType type;
	
	/**
	 * 
	 */
	public InEqualitySimplified(String strInEquation, VariableType type) {
		this.setType(InequalityOperatorType.checkOperatorType(strInEquation));
//		System.out.println(strInEquation);
		setVariable(new Variable(getLHSString(strInEquation), type,getRHSString(strInEquation)));
//		System.out.println("nnnnnnnnnnn"+strInEquation);
	}
	public InEqualitySimplified(String strInEquation,
			CTVariableType variableType) {
		this(strInEquation,toVariableType(variableType));
//		super(strInEquation,toVariableType(variableType));
//		this.setConstratinType(constraintType);
	}
	 private InequalityOperatorType checkOperatorType(String strStmt)
	  {
		  return InequalityOperatorType.checkOperatorType(strStmt);
	  }
	  private String getRHSString(String strInEquation)
	  {
		  InequalityOperatorType opType = InequalityOperatorType.checkOperatorType(strInEquation);	 
		  return CTStringParser.getRightOf(strInEquation, InequalityOperatorType.toString(opType));
	  }
	  private String getLHSString(String strInEquation)
	  {
		  InequalityOperatorType opType = InequalityOperatorType.checkOperatorType(strInEquation);
//		  System.out.println(strInEquation);
		  return CTStringParser.getLeftOf(strInEquation, InequalityOperatorType.toString(opType));	  
	  }
	/**
	 * @return the lHSVariable
	 */
	public Variable getVariable() {
		return LHSVariable;
	}
	/**
	 * @param lHSVariable the lHSVariable to set
	 */
	public void setVariable(Variable lHSVariable) {
		LHSVariable = lHSVariable;
	}
	/**
	 * @return the type
	 */
	public InequalityOperatorType getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(InequalityOperatorType type) {
		this.type = type;
	}  
	public VariableType getVariableType()
	{
		return this.LHSVariable.getType();
	}
	public String getVariableName()
	{
		return LHSVariable.getName();
	}
	@Override
	public String toString()
	{	
		return LHSVariable.getName().trim() +" "+ this.type.toString() +" "+ LHSVariable.getValue().trim(); 
	}
	public void multiplyByMinus()
	{
		if(this.getVariableType()==VariableType.INTEGER || this.getVariableType()==VariableType.REAL)
		{
			Integer i = Integer.parseInt(this.LHSVariable.getValue());
			i *= -1;
			this.LHSVariable.setValue(i.toString());
			reverse();
			//this.type = InequalityOperatorType.revers(this.type);
		}
		else if(this.getVariableType()==VariableType.INTEGER || this.getVariableType()==VariableType.REAL)
		{
			Double d = Double.parseDouble(this.LHSVariable.getValue());
			d *= -1;
			this.LHSVariable.setValue(d.toString());
			reverse();
			//this.type = InequalityOperatorType.revers(this.type);			
		}
	}
	public void reverse()
	{
		this.type = InequalityOperatorType.revers(this.type);
	}
	public InEqualitySimplified add(InEqualitySimplified inq)
	{		
		return InequationSolver.add(this, getOrientationFixed(inq));
	}
	public InEqualitySimplified sub(InEqualitySimplified inq)
	{		
		return InequationSolver.sub(this,getOrientationFixed(inq));
	}
	public InEqualitySimplified mul(InEqualitySimplified inq)
	{
		return InequationSolver.mul(this,getOrientationFixed(inq));
	}
	public InEqualitySimplified div(InEqualitySimplified inq)
	{
		return InequationSolver.div(this,getOrientationFixed(inq));
	}

	
	public InEqualitySimplified add(double val)
	{		
		return InequationSolver.add(this, new InEqualitySimplified("temp"+InequalityOperatorType.toString(this.getType())+val, this.getVariableType()));
	}
	public InEqualitySimplified sub(double val)
	{		
		return InequationSolver.sub(this, new InEqualitySimplified("temp"+InequalityOperatorType.toString(this.getType())+val, this.getVariableType()));
	}
	public InEqualitySimplified mul(double val)
	{
		return InequationSolver.mul(this, new InEqualitySimplified("temp"+InequalityOperatorType.toString(this.getType())+val, this.getVariableType()));
	}
	public InEqualitySimplified div(double val)
	{
		return InequationSolver.div(this, new InEqualitySimplified("temp"+InequalityOperatorType.toString(this.getType())+val, this.getVariableType()));
	}
	
	
	public InEqualitySimplified and(InEqualitySimplified inq)
	{
		return InequationSolver.and(this,inq);
	}
	public InEqualitySimplified or(InEqualitySimplified inq)
	{
		return InequationSolver.or(this,inq);
	}
	public InEqualityOrientation getOrientationFace()
	{
		return InequalityOperatorType.getOperatorFace(this.getType());
	}
	private InEqualitySimplified getOrientationFixed(InEqualitySimplified inq)
	{
		InEqualitySimplified retVal = inq;
//		System.out.println(this==null);
//		System.out.println(inq==null);
		if(this.getOrientationFace()!=inq.getOrientationFace())
		{
			if(this.getOrientationFace()==InEqualityOrientation.NEUTRAL || inq.getOrientationFace()==InEqualityOrientation.NEUTRAL)
			{

			}
			else
			{
				retVal.multiplyByMinus();
			}
		}
		return retVal;
	}
	public boolean isContainedInSolution(InEqualitySimplified inq)
	{
		return false;
	}
	public static VariableType toVariableType(CTVariableType type)
	{
		switch(type)
		{
		case INTEGER:
			return VariableType.INTEGER;
		case REAL:
			return VariableType.REAL;
		case BOOLEAN:
			return VariableType.BOOLEAN;
		case STRING:
			return VariableType.STRING;
		case OTHER:
			return VariableType.OBJECT;
		default :
			return VariableType.OBJECT;
		}
	}
	public ArrayList<InEqualitySimplified> intersect(InEqualitySimplified second) 
	{
		Variable v1 = LHSVariable;
		Variable v2= second.LHSVariable;
		
		return null;
	}
	public ArrayList<InEqualitySimplified> subtract(InEqualitySimplified second)throws Exception
	{
		throw new Exception("Method InEqualitySimplified::subtract() is not imelemented yes");
//		return null;
	}
	public Object getCurentValue()
	{
		
		switch(this.LHSVariable.getType())
		{
		case BOOLEAN:
			if(this.type==InequalityOperatorType.EQUAL)
			{
				return Boolean.parseBoolean(LHSVariable.getValue());
			}
			else
			{
				return !(Boolean.parseBoolean(LHSVariable.getValue()));
			}
		case INTEGER:
			if(this.type==InequalityOperatorType.EQUAL)
			{
				return Integer.parseInt(LHSVariable.getValue());				
			}
			else if (this.type==InequalityOperatorType.NOT_EQUAL)
			{
				return Integer.parseInt(LHSVariable.getValue())+1;
			}
			else if(getOrientationFace()==InEqualityOrientation.RIGHT)
			{
				return Integer.parseInt(LHSVariable.getValue())+1;
			}
			else
			{
				return Integer.parseInt(LHSVariable.getValue())-1;
			}
		case REAL:
			if(this.type==InequalityOperatorType.EQUAL)
			{
				return Double.parseDouble(LHSVariable.getValue());				
			}
			else if (this.type==InequalityOperatorType.NOT_EQUAL)
			{
				return Double.parseDouble(LHSVariable.getValue())+1;
			}
			else if(getOrientationFace()==InEqualityOrientation.RIGHT)
			{
				return Double.parseDouble(LHSVariable.getValue())+1;
			}
			else
			{
				return Double.parseDouble(LHSVariable.getValue())-1;
			}
		case OBJECT:
		case STRING:
			return new Object();
		default:
			return null;
		}
	}
}
	
enum COMPARISION {
	MIDDLE,
	LEFT_HALF,
	RIGHT_HALF,
	ENCLOSED;
}