/**
 * 
 */
package pk.com.rsoft.classcontractstestbed.util.inequality;

/**
 * @author Rehan
 *
 */
public final class Operator {
	InequalityOperatorType type;
	public static final Variable  Operate(String val)
	{
		return new Variable("", VariableType.OBJECT, val);
	}
}
