/**
 * 
 */
package pk.com.rsoft.classcontractstestbed.util.inequality;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import pk.com.rsoft.classcontractstestbed.util.parser.CTStringParser;
import pk.com.rsoft.classcontractstestbed.util.parser.OperatorType;

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;


/**
 * @author Rehan
 *
 */
public class InequationSolver {
	

	private static ScriptEngineManager mgr= new ScriptEngineManager();//The ScriptEngineManager object to represent the used script manager 
	private static ScriptEngine engin;// to hold the ScriptEngine received from the ScriptEngineManager 
	
	static//the initializer for static variables of the class  
	{
		engin = mgr.getEngineByName("JavaScript");
	}
	/**
	 * 
	 */

  public static  InEqualitySimplified add(InEqualitySimplified firstIneq,InEqualitySimplified secondIneq)
  {
	  if(firstIneq.getType()!=InequalityOperatorType.EQUAL)
	  {
	  		return process(firstIneq, secondIneq, OperatorType.PLUS);
	  }
	  else 
	  {
		  	return process(secondIneq,firstIneq, OperatorType.PLUS);
	  }
  }

  public static InEqualitySimplified sub(InEqualitySimplified firstIneq,InEqualitySimplified secondIneq)
  {
	  if(firstIneq.getType()!=InequalityOperatorType.EQUAL)
	  {
		  return process(firstIneq, secondIneq, OperatorType.MINUS);	  
	  }
	  else
	  {
		  return process(secondIneq, firstIneq,  OperatorType.MINUS);	  		  
	  }
  }
  public static InEqualitySimplified mul(InEqualitySimplified firstIneq,InEqualitySimplified secondIneq)
  {
	  if(firstIneq.getType()!=InequalityOperatorType.EQUAL)
	  {
		  return process(firstIneq, secondIneq, OperatorType.MULTIPLY);
	  }
	  else
	  {
		  return process(secondIneq, firstIneq,  OperatorType.MULTIPLY);		  
	  }
  }
  
  public static InEqualitySimplified div(InEqualitySimplified firstIneq,InEqualitySimplified secondIneq)
  {
	  if(firstIneq.getType()!=InequalityOperatorType.EQUAL)
	  {
		  return process(firstIneq,secondIneq,OperatorType.DIVIDE);
	  }
	  else
	  {
		  return process(secondIneq,firstIneq,OperatorType.DIVIDE);		  
	  }
  }
  public static InEqualitySimplified and(InEqualitySimplified firstIneq,InEqualitySimplified secondIneq)
  {
	  return process(firstIneq,secondIneq,OperatorType.AND);
  }
  public static InEqualitySimplified or(InEqualitySimplified firstIneq,InEqualitySimplified secondIneq)
  {
	  return process(firstIneq,secondIneq,OperatorType.OR);
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
	  return CTStringParser.getLeftOf(strInEquation, InequalityOperatorType.toString(opType));	  
  }
  
  public static InequationSolver Parse(String inEqString)
  {
	  return null;
  }
  public static InEqualitySimplified process(InEqualitySimplified firstIneq, InEqualitySimplified second, OperatorType type)
  {
	  String strOp ="";
//	  String strBoolOp ="";
	  switch(type)
	  {
	  case PLUS:
		  strOp ="+";
		  break;
	  case MINUS:
		  strOp = "-";
		  second.getVariable().setValue("("+second.getVariable().getValue()+")");
		  break;
	  case DIVIDE:
		  strOp ="/";
		  break;
	  case MULTIPLY:
		  strOp = "*";
		  break;
	  case AND:
		  strOp = "&&";
		  break;
	  case OR:
		  strOp = "||";
		  break;
	  case NOT:
		  strOp ="!";
		  break;
	  }
	  
		 if(firstIneq.getVariableType()!=second.getVariableType())
		 {
//			 System.out.println(firstIneq.getVariableType()+ " " +second.getVariableType());
			 
			 throw new InvalidParameterException("Input variable is not of the same type as of 'this'.");
		 }
		 
		 switch(firstIneq.getVariableType())
		 {
		 case INTEGER:
		 case REAL:
			 try {
				 return new InEqualitySimplified(firstIneq.getVariable().getName()+InequalityOperatorType.toString(firstIneq.getType())+engin.eval(firstIneq.getVariable().getValue()+ strOp + second.getVariable().getValue()).toString(), firstIneq.getVariableType());
			} catch (ScriptException e) {
				e.printStackTrace();
			}
			 break;
		 case BOOLEAN:
			 try {
				return new InEqualitySimplified(firstIneq.getVariable().getName()+InequalityOperatorType.toString(firstIneq.getType())+engin.eval(firstIneq.getVariable().getValue()+ strOp +second.getVariable().getValue()).toString(), firstIneq.getVariableType());
			} catch (ScriptException e) {
				e.printStackTrace();
			}
		 case STRING:
		 case OBJECT:
			 return null;
		 default:
			 return null;
		 }
		 return null;
  }
  public static Object evaluate(String expression)
  {
	  try {
		  System.out.println(expression);
			return engin.eval(expression);
			}
	  catch(ScriptException scExp)
	  {
		  scExp.printStackTrace();
		  return null;
	  }
  }
  
  public ArrayList<InEqualitySimplified> divideInIntervals(ArrayList<InEqualitySimplified> theInputList)
  {
	  //Must divide the received set of Inequalities into mutually exclusive an collectively exhaustive intervals 
	  for(InEqualitySimplified inq: theInputList)
	  {
		  
	  }
	  return null; //Change it to fix the things 
  }
  private ArrayList<InEqualitySimplified> divideInIntervals(InEqualitySimplified first, InEqualitySimplified second)
  {
	  Variable v1 = first.getVariable();
	  Variable v2 = second.getVariable();
	  ArrayList<InEqualitySimplified> retList = new ArrayList<InEqualitySimplified>();
	  if(v1.getValue().trim().equals(v2.getValue().trim()))
	  {
		  if(first.getType()==InequalityOperatorType.EQUAL)
		  {
			  
		  }
		  else
		  {
			  
		  }
	  }
	  return retList;
  }
  public static ArrayList<InEqualitySimplified> split(InEqualitySimplified theInq)
  {
	  ArrayList<InEqualitySimplified> retInqList = new ArrayList<InEqualitySimplified>();
	  if(InequalityOperatorType.isComposit(theInq.getType()))
	  {
		  switch(theInq.getType())
		  {
		  case GREATER_THAN_EQUAL:
			  retInqList.add(getInEquaitySimplified(theInq, InequalityOperatorType.EQUAL));
			  retInqList.add(getInEquaitySimplified(theInq, InequalityOperatorType.GREATER_THAN));
			  break;
		  case LESS_THAN_EQUAL:
			  retInqList.add(getInEquaitySimplified(theInq, InequalityOperatorType.EQUAL));
			  retInqList.add(getInEquaitySimplified(theInq, InequalityOperatorType.LESS_THAN));
			  break;
		  default:
			  System.out.println("Error while spliting Inequality");
			  break;
		  }
	  }
	  else
	  {
		retInqList.add(theInq);  
	  }
  return retInqList;
  }
  private static InEqualitySimplified getInEquaitySimplified(InEqualitySimplified inq, InequalityOperatorType type)
  {
	  return new InEqualitySimplified(getInqStr(inq, type), inq.getVariableType());
  }
  private static String getInqStr(InEqualitySimplified inq, InequalityOperatorType type)
  {
	  return inq.getVariableName() + " " + InequalityOperatorType.toString(type) + " " + inq.getVariable().getValue();
  }

	public static boolean isTheSame(InEqualitySimplified first, InEqualitySimplified second)
	{
		Variable fst = first.getVariable(), send = second.getVariable();
		return fst.getName().trim().equals(send.getName().trim()) && fst.getType()==send.getType() && fst.getValue().trim().equals(send.getValue().trim()) && first.getType()==second.getType();
	}
	public static ArrayList<InEqualitySimplified> sort(ArrayList<InEqualitySimplified> lst)
	{
		return null;
	}
}