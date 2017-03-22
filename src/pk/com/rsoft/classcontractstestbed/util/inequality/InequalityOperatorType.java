/**
 * 
 */
package pk.com.rsoft.classcontractstestbed.util.inequality;

/**
 * @author Rehan
 *
 */
public enum InequalityOperatorType {
	LESS_THAN,//Less than operator <
	LESS_THAN_EQUAL,//Less than or Equal operator <=
	GREATER_THAN,//Greater than operator >
	GREATER_THAN_EQUAL,//Greater than or Equal operator >= 
	EQUAL,//Equal operator =
	NOT_EQUAL,//Not Equal operator <>
//	AND,//And operator and
//	OR,//OR operator or
//	XOR,//XOR operator xor
	RIGHT_PREN,//Right parenthesis )
	LEFT_PREN,//Left parenthesis (
	INVALID;
	public static InequalityOperatorType checkOperatorType(String strStmt)
	{
		if(strStmt.contains("<="))
		{
			return InequalityOperatorType.LESS_THAN_EQUAL;
		}
		else if(strStmt.contains(">="))
		{
			return InequalityOperatorType.GREATER_THAN_EQUAL;
		}
		else if(strStmt.contains("<>"))
		{
			return InequalityOperatorType.NOT_EQUAL;
		}
		else if(strStmt.contains("="))
		{
			return InequalityOperatorType.EQUAL;
		}
		else if(strStmt.contains(">"))
		{
			return InequalityOperatorType.GREATER_THAN;
		}
		else if(strStmt.contains("<"))
		{
			return InequalityOperatorType.LESS_THAN;
		}
		else
		{
			return InequalityOperatorType.INVALID;
		}
	}
	public static String toString(InequalityOperatorType type)
	{
		switch(type)
		{
		case LESS_THAN_EQUAL:
			return "<=";
		case GREATER_THAN_EQUAL:
			return ">=";
		case NOT_EQUAL:
			return "<>";
		case EQUAL:		
			return "=";
		case GREATER_THAN:
			return ">";
		case LESS_THAN:
			return "<";
		default :
				return "invaid";
		}
	}
	public static InequalityOperatorType revers(InequalityOperatorType operator)
	{
		switch(operator)
		{
		case EQUAL:
			return EQUAL;
		case GREATER_THAN:
			return LESS_THAN;
		case GREATER_THAN_EQUAL:
			return LESS_THAN_EQUAL;
		case NOT_EQUAL:
			return EQUAL;
		case LESS_THAN_EQUAL:
			return GREATER_THAN_EQUAL;
		case LESS_THAN:
			return GREATER_THAN;
		case LEFT_PREN:
			return RIGHT_PREN;
		case RIGHT_PREN:
			return LEFT_PREN;
		default:
			return INVALID;		
		}
	}
	public static InEqualityOrientation getOperatorFace(InequalityOperatorType type)
	{
		switch(type)
		{
		case EQUAL:
			return InEqualityOrientation.NEUTRAL;
		case GREATER_THAN:
		case GREATER_THAN_EQUAL:
		case RIGHT_PREN:
			return InEqualityOrientation.RIGHT;
		case LESS_THAN:
		case LESS_THAN_EQUAL:
		case LEFT_PREN:
		case NOT_EQUAL:
			return InEqualityOrientation.LEFT;
		default:
			return InEqualityOrientation.NEUTRAL;
			
		}
	}
	public static boolean isOperator(String strOp)
	{
		if(checkOperatorType(strOp)!=INVALID)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public static boolean isComposit(InequalityOperatorType type)
	{
		if(type==GREATER_THAN_EQUAL || type==InequalityOperatorType.LESS_THAN_EQUAL)
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
}
