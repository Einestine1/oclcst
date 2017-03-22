package pk.com.rsoft.classcontractstestbed.util.parser;

public enum OperatorType {
	PLUS,
	MINUS,
	MULTIPLY,
	DIVIDE,
	AND,
	OR,
	NOT,
	INVALID;
public static OperatorType toOperator(String strOp)
	{
		if(strOp.trim().equals("+"))
		{
			return PLUS;
		}
		else if(strOp.trim().equals("-"))
		{
			return MINUS;
		}
		else if(strOp.trim().equals("*"))
		{
			return MULTIPLY;
		}
		else if(strOp.trim().equals("/"))
		{
			return DIVIDE;
		}
		else if(strOp.trim().equals("&&"))
		{
			return AND;
		}
		else if(strOp.trim().equals("||"))
		{
			return OR;
		}
		else if(strOp.trim().equals("!"))
		{
			return NOT;
		}
		else
		{
			return INVALID;
		}
	}
public static boolean isArithematicOperator(String op)
{
	OperatorType opType = toOperator(op);
	switch(opType)
	{
	case PLUS:
	case MINUS:
	case MULTIPLY:
	case DIVIDE:
		return true;
	default :
		return false;
	}
}
public static boolean isArithmeticExpression(String strExp)
{
	return strExp.contains("+") || strExp.contains("-") || strExp.contains("/") || strExp.contains("*");
}
}