/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pk.com.rsoft.classcontractstestbed.classcontract;

import java.io.Serializable;
import java.util.ArrayList;
/**
 *
 * @author Hammad
 */
public class CTClassVariable<T> implements Serializable{
    private String strName;
    private String strType;
    private ArrayList<T> vValidVals;
public CTClassVariable(){
   this("","",new ArrayList<T>());
}
public CTClassVariable(String name,String type,ArrayList<T> validValls){
    this.strName = name;
    this.strType = type;
    if(validValls!=null)
    {
        this.vValidVals = validValls;
    }
    else
    {
        validValls = new ArrayList<T>();
    }

}
    /**
     * @return the strName
     */
    public String getName() {
        return strName;
    }

    /**
     * @param strName the strName to set
     */
    public void setName(String strName) {
        this.strName = strName;
    }

    /**
     * @return the strType
     */
    public String getStrType() {
        return strType;
    }

    /**
     * @return the vValidVals
     */
    public ArrayList<T> getValidVals() {
        return vValidVals;
    }
    public void addValue(T objVal)
    {
        this.vValidVals.add(objVal);
    }
    public T removeValueAt(int index)
    {
        return vValidVals.remove(index);
    }
    
}
