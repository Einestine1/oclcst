/**
 * 
 */
package pk.com.rsoft.classcontractstestbed.util.graph;

import java.util.ArrayList;

import pk.com.rsoft.classcontractstestbed.classcontract.CTConstraint;

/**
 * @author Rehan
 * This class represents an atomic value in the list of values of a state variable 
 */
public abstract class  AbstractAtomicVariable {
	String name;
	String value;
	ArrayList<CTConstraint> constraints;
	public AbstractAtomicVariable()
	{
		this("","",new ArrayList<CTConstraint>());
	}
	public AbstractAtomicVariable(String name,String value)
	{
		this(name,value,new ArrayList<CTConstraint>());
	}
	public AbstractAtomicVariable(String name,String value, ArrayList<CTConstraint> constraint)	
	{
		this.name = name;
		this.value = value;
		this.constraints = constraint;
	}
	/*
	 *This method is critical and will try to generate new value for an atomic variable 
	 *by
	 */
	protected void computeNewValue(String newValExpression)
	{
		
	}

}
