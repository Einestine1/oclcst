/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pk.com.rsoft.classcontractstestbed.util.parser;

import pk.com.rsoft.classcontractstestbed.classcontract.CTConstraint;
import pk.com.rsoft.classcontractstestbed.classcontract.CTInvarient;
import pk.com.rsoft.classcontractstestbed.classcontract.CTVariableType;
import pk.com.rsoft.classcontractstestbed.util.graph.ClassVariable;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;

/**
 *
 * @author Hammad
 * Purpose : This Class is used for the evaluating the operation and 
 * 			 return new values to the state variables after 
 * 			 Operation execution  
 */
public class CTOperator {
	//The following two variables are class level and hence are declared as static
	private static ScriptEngineManager mgr= new ScriptEngineManager();//The ScriptEngineManager object to represent the used script manager 
	private static ScriptEngine engin;// to hold the ScriptEngine received from the ScriptEngineManager 

	private CTInvarient theInverient;//Holds the information about the related constraints of the class Context under analysis 
	
	static//the initializer for static variables of the class  
	{
		engin = mgr.getEngineByName("JavaScript");
	}
	
	
	public CTOperator(CTInvarient inv)
	{
		this.theInverient = inv;
		
	}
    public ClassVariable Operate(ClassVariable preVar, String strPreVal, CTInvarient varInv)
    {
    	//System.out.println("Variable Name :" + preVar.getName()+" Value :"+getVariableVal(preVar.getName(), varInv));
    	ClassVariable retval = new ClassVariable("","");
 
        if(strPreVal.contains("+"))
        {
        	retval = add(preVar, strPreVal);
        }
        else if(strPreVal.contains("-"))
        {
        	retval = sub(preVar, strPreVal);
        }
        else if(strPreVal.contains("/"))
        {
        	retval = div(preVar, strPreVal);
        }
        else if(strPreVal.contains("*"))
        {
        	retval = mul(preVar, strPreVal);
        }
        
       	if(isValid(retval))
    	{
    		System.out.println(preVar.getName() + " has a valid value =" + retval.getValue());
    		
    	}        
       	else
       	{
    		System.out.println(preVar.getName() + " has an invalid value " + retval.getValue());
    		setDefaultValue(retval);
    		System.out.println(preVar.getName() + " has a new value = " + retval.getValue());
       		
       	}
        if(retval.getName().equals(""))
        {
            return equ(preVar, strPreVal);        	
        }
        else
        {
        	return retval;
        }

    }
    private ClassVariable add(ClassVariable var, String preVal)
    {
        CTVariableType varType = var.getType();
        if(!isNumber(getRigtVal(preVal, '+')))
        {
            return new ClassVariable(var.getName(),preVal);
        }
        if(varType==CTVariableType.INTEGER)
        {
            int temResult = Integer.parseInt(var.getValue());
            temResult += Integer.parseInt(getRigtVal(preVal, '+'));
            return new ClassVariable(var.getName(), String.valueOf((int)temResult));
        }
        if(varType==CTVariableType.REAL)
        {
            double temResult = Double.parseDouble(var.getValue());
            temResult += Double.parseDouble(getRigtVal(preVal, '+'));
            return new ClassVariable(var.getName(), String.valueOf(temResult));
        }
        if(varType==CTVariableType.STRING)
        {
            return new ClassVariable(var.getName(), (var.getValue() + getRigtVal(preVal, '+')));
        }
        return new ClassVariable(var.getName(),var.getValue());
    }

    private ClassVariable sub(ClassVariable var, String preVal)
    {
        if(!isNumber(getRigtVal(preVal, '-')))
        {
            return new ClassVariable(var.getName(),preVal);
        }

        CTVariableType varType = var.getType();
        if(varType==CTVariableType.INTEGER)
        {
            int temResult = Integer.parseInt(var.getValue());
            temResult -= Integer.parseInt(getRigtVal(preVal, '-'));
            return new ClassVariable(var.getName(), String.valueOf((int)temResult));
        }
        if(varType==CTVariableType.REAL)
        {
            double temResult = Double.parseDouble(var.getValue());
            temResult -= Double.parseDouble(getRigtVal(preVal, '-'));
            return new ClassVariable(var.getName(), String.valueOf(temResult));
        }
        if(varType==CTVariableType.STRING)
        {
            return new ClassVariable(var.getName(), (var.getValue().replace(getRigtVal(preVal, '-'),"")));
        }
        return new ClassVariable(var.getName(),var.getValue());

    }
    private ClassVariable mul(ClassVariable var, String preVal)
    {
        if(!isNumber(getRigtVal(preVal, '*')))
        {
            return new ClassVariable(var.getName(),preVal);
        }

        CTVariableType varType = var.getType();
        if(varType==CTVariableType.INTEGER)
        {
            int temResult = Integer.parseInt(var.getValue());
            temResult *= Integer.parseInt(getRigtVal(preVal, '*'));
            return new ClassVariable(var.getName(), String.valueOf((int)temResult));
        }
        if(varType==CTVariableType.REAL)
        {
            double temResult = Double.parseDouble(var.getValue());
            temResult *= Double.parseDouble(getRigtVal(preVal, '*'));
            return new ClassVariable(var.getName(), String.valueOf(temResult));
        }
        return new ClassVariable(var.getName(),var.getValue());

    }
    private ClassVariable div(ClassVariable var, String preVal)
    {
        if(!isNumber(getRigtVal(preVal, '/')))
        {
            return new ClassVariable(var.getName(),preVal);
        }

        CTVariableType varType = var.getType();
        if(varType==CTVariableType.INTEGER)
        {
            int temResult = Integer.parseInt(var.getValue());
            temResult /= Integer.parseInt(getRigtVal(preVal, '/'));
            return new ClassVariable(var.getName(), String.valueOf((int)temResult));
        }
        if(varType==CTVariableType.REAL)
        {
            double temResult = Double.parseDouble(var.getValue());
            temResult /= Double.parseDouble(getRigtVal(preVal, '/'));
            return new ClassVariable(var.getName(), String.valueOf(temResult));
        }
        return new ClassVariable(var.getName(),var.getValue());

    }

    private ClassVariable equ(ClassVariable var, String preVal)
    {
        return new ClassVariable(var.getName(), getRigtVal(preVal, '='));
    }

    private String getRigtVal(String preVal, char operator)
    {
        return preVal.substring(preVal.lastIndexOf(operator)+1).trim();
    }
    public boolean isNumber(String aVal)
    {
        	if (aVal.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+"))
                {
                        return true;
                }
                else
                {
                        return false;
                }
    }

    private String getVariableVal(String varName, CTInvarient inv)
    {
    	for(CTConstraint cons: inv.getConstrantsList())
    	{
    		if(cons.getVariableName().equals(varName))
    		{
    			return cons.getVariableValue();
    		}
    	}
    	return "";
    }
	/**
	 * @return the theInverient
	 */
	public CTInvarient getTheInverient() {
		return theInverient;
	}
	/**
	 * @param theInverient the theInverient to set
	 */
	public void setTheInverient(CTInvarient theInverient) {
		this.theInverient = theInverient;
	}
	public boolean isValid(ClassVariable aVar)
	{
		for(CTConstraint cx : this.theInverient.getConstrantsList())
		{
			if(cx.getVariableName().equals(aVar.getName()))
			{
				return checkConstraint(cx, aVar);
			}
		}
		return false;
	}
	private boolean checkConstraint(CTConstraint cx, ClassVariable aVar)
	{
		System.out.println("The applicable constraint is -->"+cx.getVariableValue());
		try{
			if(engin!=null)
				{
					//System.out.println("-->      "+aVar.getValue()+cx.getVariableValue().toString());
				if (isNumber(aVar.getValue()))	
				{
					return Boolean.parseBoolean(engin.eval(aVar.getValue()+cx.getVariableValue()).toString());
				}
				}
			else
			{
				System.out.println("Engine is null");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return true;
	}
	private void setDefaultValue(ClassVariable aVar)
	{
		for(CTConstraint cx : theInverient.getConstrantsList())
		{
			if(cx.getVariableName().equals(aVar.getName()))
			{
				aVar.setValue(CTStringParser.removeOperator(cx.getVariableValue()));
			}
		}
	}
	
}
