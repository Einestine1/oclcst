/**
 * 
 */
package pk.com.rsoft.classcontractstestbed.util.inequality;

/**
 * @author Rehan
 *
 */
public enum VariableType {
	INTEGER,
	REAL,
	BOOLEAN,
	STRING,
	OBJECT;
	public static String getDefaultValue(VariableType type)
	{
		switch(type)
		{
		case BOOLEAN:
			return String.valueOf(false);
		case INTEGER:
			return String.valueOf(0);
		case REAL:
			return "0.0";
		case STRING:
		case OBJECT:
		default:
			return String.valueOf(null);		
		}
	}

}
