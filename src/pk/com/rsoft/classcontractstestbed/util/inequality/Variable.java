/**
 * 
 */
package pk.com.rsoft.classcontractstestbed.util.inequality;

import java.io.Serializable;
import pk.com.rsoft.classcontractstestbed.util.parser.CTStringParser;
import pk.com.rsoft.classcontractstestbed.util.parser.NumberPorcessor;

//import choco.kernel.common.util.tools.StringUtils;

/**
 * @author Rehan
 *
 */
public class Variable implements Serializable{
	private String name;
	private VariableType type;
	private String value;
	/**
	 * 
	 */
	public Variable(String name,VariableType type, String value) {
		this.setName(name);
		this.setType(type);
		if(type==VariableType.INTEGER && NumberPorcessor.isNumeric(value) )
		{
			double val = Double.parseDouble(value);
			this.setValue(String.valueOf((int) val));
		}
		else
		{
			this.setValue(value);
		}		
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the type
	 */
	public VariableType getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(VariableType type) {
		this.type = type;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		value = CTStringParser.getRightOf(value, "=");
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
