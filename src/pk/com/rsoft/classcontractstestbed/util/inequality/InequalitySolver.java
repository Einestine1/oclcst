/**
 * 
 */
package pk.com.rsoft.classcontractstestbed.util.inequality;

import java.util.ArrayList;
import java.util.Stack;

import pk.com.rsoft.classcontractstestbed.classcontract.CTAttribute;
import pk.com.rsoft.classcontractstestbed.classcontract.CTConstraint;
import pk.com.rsoft.classcontractstestbed.classcontract.CTContext;
import pk.com.rsoft.classcontractstestbed.classcontract.CTMethodParameter;
import pk.com.rsoft.classcontractstestbed.classcontract.CTOperation;
import pk.com.rsoft.classcontractstestbed.classcontract.CTPostCondition;
import pk.com.rsoft.classcontractstestbed.classcontract.CTPreCondition;
import pk.com.rsoft.classcontractstestbed.classcontract.CTVariableType;
import pk.com.rsoft.classcontractstestbed.util.graph.ConsType;
import pk.com.rsoft.classcontractstestbed.util.graph.ClassVariable;
import pk.com.rsoft.classcontractstestbed.util.parser.NumberPorcessor;
import pk.com.rsoft.classcontractstestbed.util.parser.OperatorType;

/**
 * @author Rehan
 *
 */
public class InequalitySolver {
	private static int varCount =1;
	private ArrayList<InEqualitySimplified> lstParticipatingVars = new ArrayList<InEqualitySimplified>();
	private String strEquation ="";
	public InequalitySolver(String strEquation, ArrayList<InEqualitySimplified> lstParticipatingVars)
	{
		this.setEquation(strEquation);
		this.setParticipatingVars(lstParticipatingVars);
	}
	public static boolean isHigher(String first, String second)
	{	
		
		return Presidance.getIntVal(Presidance.getPresidance(first))>Presidance.getIntVal(Presidance.getPresidance(second));
	}

	private static boolean isOperator(String strVal)
	{
		if(strVal.trim().equals("+")||strVal.trim().equals("-")||strVal.trim().equals("*") ||strVal.trim().equals("/") ||strVal.trim().equals("(") ||strVal.trim().equals(")"))
		{
			return true;
		}
		return false;
	}
	public static Stack<String> getRPNStack(String strOp)
	{

		Stack<String> rpnStack = new Stack<String>();
		Stack<String> stack = new Stack<String>(); 
		String tokens[] = strOp.split(" ");
		for(String st: tokens)
		{   
			if(!st.trim().equals(""))
			{
				st=st.replace(",", "");
				if(isOperator(st))
			{
				if(stack.isEmpty())
				{
						stack.push(st);				
				}
				else 
				{
					if(isHigher(stack.peek(),st))
					{
						rpnStack.push(stack.pop());
						stack.push(st);
					}
					else
					{
						stack.push(st);
					}
				}
			}
			else 
			{
				if(st.trim().toUpperCase().equals("@PRE"))
				{
					rpnStack.push(rpnStack.pop()+"@pre");
				}
				else
				{
					rpnStack.push(st);
				}
			}
			}	
		}
		while(!stack.isEmpty())
		{
			rpnStack.push(stack.pop()) ;
		}
		return rpnStack;
	}
	/**
	 * @return the strEquation
	 */
	public String getEquation() {
		return strEquation;
	}
	/**
	 * @param strEquation the strEquation to set
	 */
	public void setEquation(String strEquation) {
		this.strEquation = strEquation;
	}
	/**
	 * @return the lstParticipatingVars
	 */
	public ArrayList<InEqualitySimplified> getParticipatingVars() {
		return lstParticipatingVars;
	}
	/**
	 * @param lstParticipatingVars the lstParticipatingVars to set
	 */
	public void setParticipatingVars(ArrayList<InEqualitySimplified> lstParticipatingVars) {
		this.lstParticipatingVars = lstParticipatingVars;
	}
	public static ArrayList<InEqualitySimplified> getConstraintsByName(String strName, ArrayList<InEqualitySimplified> lstList)
	{
		ArrayList<InEqualitySimplified> retList = new ArrayList<InEqualitySimplified>();
		for(InEqualitySimplified inq : lstList)
		{
			if(inq.getVariableName().trim().equals(strName.trim()))
			{
				retList.add(inq);
			}
		}
		return retList;
	}
	
	public static InEqualitySimplified simplify(InEqualitySimplified source, CTOperation op,CTContext context)
	{
		
		InEqualitySimplified retVal = source;
	    Stack<InEqualitySimplified> stkOperational = new Stack<InEqualitySimplified>();

	   		Stack<String> stk = InequalitySolver.getRPNStack(source.getVariable().getValue());

	   		stk = reverse(stk);
	    		while(!stk.isEmpty())
	    		{
	    			String strVal = stk.pop();
	    			if(!OperatorType.isArithematicOperator(strVal))
	    			{
	    				if(NumberPorcessor.isNumeric(strVal))//Poped value is a number
	    				{
	    					stkOperational.push(new InEqualitySimplified("v"+(varCount++) +" = " + strVal,VariableType.INTEGER));
	    				}
	    				else if(strVal.toUpperCase().contains("@PRE"))//Poped value is variable name
		    			{
		    				ArrayList<InEqualitySimplified> lst = getPreValues(strVal.replace("@pre", " "), op, context);	

		    				if(lst.size()<1)//No pre values found
		    				{
		    					InEqualitySimplified temp = findByName(strVal.replace("@pre", " "), op,context ,ConsType.INV);
		    					if(temp==null)
		    					{
		    						temp = new InEqualitySimplified(strVal.replace("@pre", " ")+" = 0 ", VariableType.INTEGER);
		    					}
		    					lst.add(temp);
		    					stkOperational.push(temp);
		    				}
		    				else
		    				{
		    					
		    					stkOperational.push(lst.get(0));
		    				}
		    			}
		    			else
		    			{
		    				stkOperational.push(findByName(strVal, op,context, ConsType.PARAM));
		    			}
	    			}
	    			else
	    			{
	    				//this is an operator so process the poped n values from the Operational Stack 
	    				InEqualitySimplified val1 = stkOperational.pop();
	    				InEqualitySimplified val2 = stkOperational.pop();
	    				if(val1!=null && val2 !=null)
	    				stkOperational.push(Operate(val1, val2, strVal));
	    			}
	    }
		if(!stkOperational.isEmpty())
		{
			retVal = stkOperational.pop();
		}
		else
		{
//			System.out.println("Stack is Empty");
		}
		ArrayList<InEqualitySimplified> preVals = getPreValues(source.getVariableName(), op, context);
		return Validate(retVal, preVals);
	}

	public static InEqualitySimplified simplify(InEqualitySimplified source, CTOperation op,CTContext context,ArrayList<InEqualitySimplified> currentVals,ArrayList<ClassVariable> constraintVals)
	{
		
		InEqualitySimplified retVal = source;
	    Stack<InEqualitySimplified> stkOperational = new Stack<InEqualitySimplified>();

	   		Stack<String> stk = InequalitySolver.getRPNStack(source.getVariable().getValue());

	   		stk = reverse(stk);
	    		while(!stk.isEmpty())
	    		{
	    			String strVal = stk.pop();
	    			if(!OperatorType.isArithematicOperator(strVal))
	    			{
	    				if(NumberPorcessor.isNumeric(strVal))//Poped value is a number
	    				{
	    					stkOperational.push(new InEqualitySimplified("v"+(varCount++) +" = " + strVal,VariableType.INTEGER));
	    				}
	    				else if(strVal.toUpperCase().contains("@PRE"))//Poped value is variable name
		    			{
		    				ArrayList<InEqualitySimplified> lst = currentVals;	
		    				if(lst.size()<1)//No pre values found
		    				{
		    					InEqualitySimplified temp = findByName(strVal.replace("@pre", " "), currentVals);
		    					if(temp==null)
		    					{
		    						temp = new InEqualitySimplified(strVal.replace("@pre", " ")+" = 0 ", VariableType.INTEGER);
		    					}
		    					lst.add(temp);
		    					stkOperational.push(temp);
		    				}
		    				else
		    				{
		    					
		    					stkOperational.push(lst.get(0));
		    				}
		    			}
		    			else
		    			{
		    				stkOperational.push(findByName(strVal, op,context, ConsType.PARAM));
		    			}
	    			}
	    			else
	    			{
	    				//this is an operator so process the poped n values from the Operational Stack 
	    				InEqualitySimplified val1 = stkOperational.pop();
	    				InEqualitySimplified val2 = stkOperational.pop();
	    				if(val1!=null && val2 !=null)
	    				stkOperational.push(Operate(val1, val2, strVal));
	    			}
	    }
		if(!stkOperational.isEmpty())
		{
			retVal = stkOperational.pop();
		}
		else
		{
//			System.out.println("Stack is Empty");
		}
		ArrayList<InEqualitySimplified> preVals = getPreValues(source.getVariableName(), op, context);
		retVal.getVariable().setName(source.getVariableName());
		
		retVal = Validate(retVal, preVals);
		if(existsInList(retVal, constraintVals))
		{
			return retVal;
		}
		retVal = getValidValueFromList(retVal, constraintVals);
		if(retVal==null)
		{
			retVal = findByName(source.getVariableName().trim(), getPreValues(source.getVariableName(), op, context));
		}
		return retVal; 
	}
	
	
	
	public static Stack<String> reverse(Stack<String> strStack)
	{		
		Stack<String> retStack = new Stack<String>();
		while(!strStack.isEmpty())
		{
			retStack.push(strStack.pop());
		}
		return retStack;
	}
	

	
	public static ArrayList<InEqualitySimplified> getPreValues(String varName, CTOperation op, CTContext context)
	{
		ArrayList<InEqualitySimplified> preConstraints = getOperationConstraintList(context,op, ConsType.PRE);
		ArrayList<InEqualitySimplified> retList = new ArrayList<InEqualitySimplified>();
		for(InEqualitySimplified inq : preConstraints)
		{
			if(inq.getVariableName().trim().equals(varName.trim()))
			{
				retList.add(inq);
			}
		}
		return retList;
	}	
	
	public static ArrayList<InEqualitySimplified> getPostValues(String varName, CTOperation op, CTContext context)
	{
		ArrayList<InEqualitySimplified> preConstraints = getOperationConstraintList(context,op, ConsType.POST);
		ArrayList<InEqualitySimplified> retList = new ArrayList<InEqualitySimplified>();
		for(InEqualitySimplified inq : preConstraints)
		{
			if(inq.getVariableName().trim().equals(varName.trim()))
			{
				retList.add(inq);
			}
		}
		return retList;
	}	

	
	
//////////////////////
	public static ArrayList<InEqualitySimplified> getPreValues(CTOperation op, CTContext context)
	{
		ArrayList<InEqualitySimplified> preConstraints = getOperationConstraintList(context,op, ConsType.PRE);
		ArrayList<InEqualitySimplified> retList = new ArrayList<InEqualitySimplified>();
		for(InEqualitySimplified inq : preConstraints)
		{
//			if(inq.getVariableName().trim().equals(varName.trim()))
//			{
				retList.add(inq);
//			}
		}
		return retList;
	}	
	
	public static ArrayList<InEqualitySimplified> getPostValues(CTOperation op, CTContext context)
	{
		ArrayList<InEqualitySimplified> preConstraints = getOperationConstraintList(context,op, ConsType.POST);
		ArrayList<InEqualitySimplified> retList = new ArrayList<InEqualitySimplified>();
		for(InEqualitySimplified inq : preConstraints)
		{
//			if(inq.getVariableName().trim().equals(varName.trim()))
//			{
				retList.add(inq);
//			}
		}
		return retList;
	}	
	
//////////////////////	
	
	
	public static InEqualitySimplified findByName(String varName, CTOperation cop, CTContext ctx ,ConsType type)
	{
		
		ArrayList<InEqualitySimplified> lst= getOperationConstraintList(ctx,cop,type);
		boolean first =true;
		InEqualitySimplified inqTemp = null;
		for(InEqualitySimplified inq:lst)
		{	
			if(inq.getVariableName().trim().equals(varName.trim()))
			{
				if(first)
				{
					inqTemp = inq;
					first = false;
				}
				else
				{
					inqTemp.add(inq); 
				}
			}
		}
			return inqTemp;
	}	
	
	
	public static InEqualitySimplified findByName(String varName, ArrayList<InEqualitySimplified> varVals)
	{
		for(InEqualitySimplified inq: varVals)
		{
			if(varName.trim().equals(inq.getVariableName().trim()))
			{
				return inq;
			}
		}
		return null;
	}	

	
	public static ArrayList<InEqualitySimplified> getOperationConstraintList(CTContext ctx, CTOperation op,ConsType type)
	 {	
		ArrayList<CTMethodParameter> methodParams = op.getParameters();
		ArrayList<CTPreCondition> preConditions = op.getPreConditions();
		ArrayList<CTPostCondition> postConditions = op.getPostConditions();
		InEqualitySimplified inSimple ;//= new InEqualitySimplified("", VariableType.OBJECT);
		ArrayList<InEqualitySimplified> retList = new ArrayList<InEqualitySimplified>();
	    if(type==ConsType.ALL || type==ConsType.PRE)
	    {
			for(CTPreCondition pre : preConditions)
			{
				for(CTConstraint constraint: pre.getConstrantsList())
				{
					if(getVariableType(ctx.getVariableType(constraint.getVariableNameRemovingSelf()))==VariableType.OBJECT)
					{
						if(op.isInParameterLis(constraint.getVariableNameRemovingSelf()))
						{
							CTMethodParameter param = op.getParameterByName(constraint.getVariableNameRemovingSelf());
							for(CTConstraint con : param.getConstraints())
							{
								inSimple = new InEqualitySimplified(con.getVariableNameRemovingSelf()+ " " + con.getVariableValue(),getVariableType(param.getType()));
								retList.add(inSimple);
							}
						}				
					}
					else
					{
						inSimple = new InEqualitySimplified(constraint.getVariableNameRemovingSelf() +" " +  constraint.getVariableValue(),getVariableType(ctx.getVariableType(constraint.getVariableNameRemovingSelf())));
						retList.add(inSimple);
					}
				}
			}
	    }
	    if(type==ConsType.ALL||type==ConsType.POST)
	    {
			for(CTPostCondition post: postConditions)
			{
				for(CTConstraint constraint: post.getConstrantsList())
				{
					if(getVariableType(ctx.getVariableType(constraint.getVariableNameRemovingSelf()))==VariableType.OBJECT)
					{
						if(op.isInParameterLis(constraint.getVariableNameRemovingSelf()))
						{
							CTMethodParameter param = op.getParameterByName(constraint.getVariableNameRemovingSelf());
							for(CTConstraint con : param.getConstraints())
							{
								inSimple = new InEqualitySimplified(con.getVariableNameRemovingSelf()+ " " + con.getVariableValue(),getVariableType(param.getType()));
								retList.add(inSimple);
							}
						}				
					}
					else
					{
						inSimple = new InEqualitySimplified(constraint.getVariableNameRemovingSelf() +" " +  constraint.getVariableValue(),getVariableType(ctx.getVariableType(constraint.getVariableNameRemovingSelf())));
						retList.add(inSimple);
					}
				}
				
			}
	    }
	    if(type==ConsType.ALL||type==ConsType.PARAM)
	    {
			for(CTMethodParameter param : methodParams)
			{

				for(CTConstraint constraint : param.getConstraints())
				{
					inSimple  = new InEqualitySimplified(param.getName() + " " + constraint.getVariableValue() , getVariableType(param.getType()));			
					retList.add(inSimple);
				}
			}
	    }
		return retList;
	 }
	public static ArrayList<InEqualitySimplified> getInvarients(ClassVariable aStateVariable, CTContext context)
	{
		ArrayList<InEqualitySimplified> lstInqSim = new ArrayList<InEqualitySimplified>();
		String strInEquation = "";
		for( CTConstraint constraint : context.getInvarient().getConstrantsList())
		{
			if(constraint.getVariableName().equals(aStateVariable.getName()))
			{
				strInEquation = aStateVariable.getName()+" " + constraint.getVariableValue();
				lstInqSim.add(new InEqualitySimplified(strInEquation, aStateVariable.getType()));
			}
		}
		return lstInqSim;
	}
	
	public static InEqualitySimplified Validate(InEqualitySimplified theInequality, ArrayList<InEqualitySimplified> inqList)
	{
		InEqualitySimplified inq = null;
		for(InEqualitySimplified inqSim : inqList)
		{
			String strExp ="";
			if(inqSim.getOrientationFace()!=InEqualityOrientation.NEUTRAL && NumberPorcessor.isNumeric(theInequality.getVariable().getValue()) && NumberPorcessor.isNumeric(inqSim.getVariable().getValue()))
			{	
				strExp = theInequality.getVariable().getValue() + " " +InequalityOperatorType.toString(inqSim.getType()) + " " + inqSim.getVariable().getValue();
				Boolean bool = Boolean.parseBoolean(InequationSolver.evaluate(strExp).toString());
			
				if(!bool)
				{
					inq = inqSim;
					return inq;
				}

			}
		}
		inq = theInequality;
		return inq;
	}

	
	
	
	public static boolean isValid(InEqualitySimplified theInequality, ArrayList<InEqualitySimplified> inqList)
	{
		Boolean bool = true;
		for(InEqualitySimplified inqSim : inqList)
		{
			String strExp ="";
			if(theInequality.getVariableName().trim().equals(inqSim.getVariableName().trim()) && inqSim.getOrientationFace()!=InEqualityOrientation.NEUTRAL && NumberPorcessor.isNumeric(theInequality.getVariable().getValue()) && NumberPorcessor.isNumeric(inqSim.getVariable().getValue()))
			{	
				strExp = theInequality.getVariable().getValue() + " " +InequalityOperatorType.toString(inqSim.getType()) + " " + inqSim.getVariable().getValue();
				bool = Boolean.parseBoolean(InequationSolver.evaluate(strExp).toString());
				if(bool==false)
				{
					return false;
				}
			}
		}
		return true;
	}

	
	public static InEqualitySimplified Validate(InEqualitySimplified theInequality, ArrayList<InEqualitySimplified> inqList, ArrayList<ClassVariable> constraintVals)
	{
		InEqualitySimplified inq = null;
		for(ClassVariable clsVar: constraintVals)
		{
			for(InEqualitySimplified inqSim: clsVar.getValues())
			{
				
				if(theInequality.getVariableName().trim().equals(inqSim.getVariableName().trim()))
				{
					String strOp = InequalityOperatorType.toString(inqSim.getType());
					if(strOp.trim().equals("="))
					{
						strOp+="=";
					}
					String strExp = theInequality.getVariable().getValue() + strOp + inqSim.getVariable().getValue();

					Boolean bool = Boolean.parseBoolean(InequationSolver.evaluate(strExp).toString());
					if(bool)
					{
						return inqSim;
					}
				}
			}
		}
		return inq;
	}
	
	
	public static CTVariableType  getVariableType(VariableType type)
	 {
		 switch(type)
		 {
			 case INTEGER:
				 return CTVariableType.INTEGER;
			 case BOOLEAN:
				 return CTVariableType.BOOLEAN;
			 case REAL:
				 return CTVariableType.REAL;
			 case STRING:
				 return CTVariableType.STRING;
			default:
				 return CTVariableType.OTHER;
		 }
	 }	
	
	
	public static InEqualitySimplified Operate(InEqualitySimplified first, InEqualitySimplified second, String strOperator)
	{
		OperatorType type = OperatorType.toOperator(strOperator);
		switch(type)
		{
		case PLUS:
			return first.add(second);
		case MINUS:
			return first.sub(second);
		case MULTIPLY:
			return first.mul(second);
		case DIVIDE:
			return first.div(second);
		default :
			System.out.println("Ohhhh MG!");
				return null;
		}
	}
	
	public static VariableType getVariableType(CTVariableType type)
	 {
		 switch(type)
		 {
			 case INTEGER:
				 return VariableType.INTEGER;
			 case BOOLEAN:
				 return VariableType.BOOLEAN;
			 case REAL:
				 return VariableType.REAL;
			 case STRING:
				 return VariableType.STRING;
			default:
				 return VariableType.OBJECT;
		 }
	 }
	
	
	public static VariableType getVariableType(String strType)
	 {
		 if(strType.trim().toUpperCase().equals("INTEGER"))
		 {
			 return VariableType.INTEGER;
		 }
		 else if(strType.trim().toUpperCase().equals("BOOLEAN"))
		 {
			 return VariableType.BOOLEAN;
		 }
		 else if(strType.trim().toUpperCase().equals("REAL"))
		 {
			 return VariableType.REAL;
		 }
		 else if(strType.trim().toUpperCase().equals("STRING"))
		 {
			 return VariableType.STRING;
		 }
		 else
		 {
			 return VariableType.OBJECT;
		 }
			  
	 }
	
	
	public ArrayList<InEqualitySimplified> ValidateInequalitySimplifiedList(String varName, ArrayList<InEqualitySimplified> lstToValidate)
	{
		return null;
	}
	
	public static boolean isVarContained(String varName, ArrayList<InEqualitySimplified> inqList)
	{
		for(InEqualitySimplified inqSimp : inqList)
		{
			if(inqSimp.getVariableName().trim().equals(varName.trim()))
			{
				return true;
			}
		}
		return false;
	}
	public static boolean isVarContained(ClassVariable var, ArrayList<InEqualitySimplified> inqList)
	{
		return isVarContained(var.getName(), inqList);
	}
	
	public static InEqualitySimplified getDefaultValue(ClassVariable aVar, CTContext context)
	{
		ArrayList<CTAttribute> lstAttribs = context.getLstAttributes();
		for(CTAttribute atr : lstAttribs)
		{
			if(atr.getName().trim().equals(aVar.getName().trim()))
			{
				return toInequality(atr);
			}
		}
		return new InEqualitySimplified(aVar.getName() + " = " + CTVariableType.getDafaultValue(aVar.getType()),aVar.getType() );
	}
	public static InEqualitySimplified toInequality(CTAttribute attrib)
	{
		return new InEqualitySimplified(attrib.getName() + " =  " +attrib.getInitVal(),getVariableType(attrib.getCTType()));
	}
	private static InEqualitySimplified getValidValueFromList(InEqualitySimplified inputValue,ArrayList<ClassVariable> constraints)
	{
		for(ClassVariable var:constraints)
		{

			if(var.getName().trim().equals(inputValue.getVariableName().trim()))
			{
				for(InEqualitySimplified constraintVal: var.getValues())
				{
					if(isInequlitySatisfied(inputValue, constraintVal))
					{
						return constraintVal;
					}
					
				}
			}
		}
		return null;
	}
	private static boolean isInequlitySatisfied(InEqualitySimplified source, InEqualitySimplified inqTobeTested)
	{
		String strOps = InequalityOperatorType.toString(inqTobeTested.getType());
		if(strOps.trim().equals("="))
		{
			strOps="==";
		}
		
		String strExp = source.getVariable().getValue()+" " + strOps+ " " +inqTobeTested.getVariable().getValue();

		Boolean bool = Boolean.parseBoolean(InequationSolver.evaluate(strExp).toString());
		return bool;
	}
	private static boolean existsInList(InEqualitySimplified inq, ArrayList<ClassVariable> lst)
	{
		for(ClassVariable clsVar : lst)
		{
			if(clsVar.getName().trim().equals(inq.getVariableName().trim()))
			{
				for(InEqualitySimplified insInList: clsVar.getValues())
				{
					if(InequationSolver.isTheSame(inq, insInList))
					{
						return true;
					}
				}
			}
		}
		return false;
	}
}
enum Presidance {
	FIRST,
	SECOND,
	THIRD,
//	FOURTH,
	FIFTH;
	public static int getIntVal(Presidance pre)
	{
		switch(pre)
		{		
		case FIRST:
			return -1;
		case SECOND:
			return -2;
		case THIRD:
			return -3;
		default:
			return -5;
		}
	}
	
	public static Presidance getPresidance (String strVal)
	{
		if(strVal.trim().equals("(")||strVal.trim().equals(")"))
		{
			return Presidance.FIRST;
		}
		else if(strVal.trim().equals("*")||strVal.trim().equals("/"))
		{ 
			return Presidance.SECOND;
		}
		else if(strVal.trim().equals("+") || strVal.trim().equals("-"))
		{
			return Presidance.THIRD;
		}
		else
		{
			return Presidance.FIFTH;
		}
		
	}	
}

