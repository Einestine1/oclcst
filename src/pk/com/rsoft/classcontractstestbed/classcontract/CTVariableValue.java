/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pk.com.rsoft.classcontractstestbed.classcontract;

/**
 *
 * @author Hammad
 */
public class CTVariableValue {
    private String value="";
    private CTVariableType type = CTVariableType.OTHER;
    public CTVariableValue(String strVal)
    {
        this(strVal, CTVariableType.OTHER);
    }
    public CTVariableValue(String strVal, CTVariableType type)

    {
        this.value = strVal;
        this.type = type;
    }
    public int CompareTo(CTVariableValue val)
    {
        if(getValue().equals(val.getValue()))
        {
            return 0;
        }
        return 1;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the type
     */
    public CTVariableType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(CTVariableType type) {
        this.type = type;
    }
}
