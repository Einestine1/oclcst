/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pk.com.rsoft.classcontractstestbed.classcontract;

import java.io.Serializable;
import java.util.Enumeration;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import pk.com.rsoft.classcontractstestbed.util.parser.CTStringParser;

/**
 *
 * @author Hammad
 */
public class CTConstraint implements Serializable{
    private String strConstraintName;
    private String strDesc;
    private String strVariableName;
    private String strVariableValue;

    public CTConstraint(TreeNode theNode)
    {
        parseConstraintNode(theNode);
    }
    public CTConstraint(String strName,String strDesc, String strVarName, String strVarVal)
    {
        this.strConstraintName = strName;
        this.strDesc = strDesc;
        this.strVariableName = strVarName;
        this.strVariableValue = strVarVal;
    }
    private void parseConstraintNode(TreeNode theNode)
    {
        DefaultMutableTreeNode tm = (DefaultMutableTreeNode) theNode;
        Enumeration<?> en =tm.depthFirstEnumeration();
        DefaultMutableTreeNode tmTemp;
        String strVal;
        while(en.hasMoreElements())
        {
            tmTemp = (DefaultMutableTreeNode)en.nextElement();
            strVal = tmTemp.toString();
            if(strVal.startsWith("BinaryRelationalExpCs"))
            {

                this.strDesc = CTStringParser.extractNameFromQuotes(strVal);
                this.strVariableValue = CTStringParser.extractNameFromQuotes(tmTemp.getChildAt(1).toString());
                this.strVariableName =  CTStringParser.extractNameFromQuotes(tmTemp.getChildAt(0).toString());
            }

        }
    }
    /**
     * @return the strConstraintName
     */
    public String getConstraintName() {
        return strConstraintName;
    }

    /**
     * @param strConstraintName the strConstraintName to set
     */
    public void setConstraintName(String strConstraintName) {
        this.strConstraintName = strConstraintName;
    }

    /**
     * @return the strDesc
     */
    public String getDesc() {
        return strDesc;
    }

    /**
     * @param strDesc the strDesc to set
     */
    public void setDesc(String strDesc) {
        this.strDesc = strDesc;
    }

    /**
     * @return the strVariableName
     */
    public String getVariableName() {
        return strVariableName;
    }

    /**
     * @return the strVariableName
     */
    public String getVariableNameRemovingSelf() {
        return strVariableName.replace("self . ", " ");
    }    
    
    /**
     * @param strVariableName the strVariableName to set
     */
    public void setVariableName(String strVariableName) {
        this.strVariableName = strVariableName;
    }

    /**
     * @return the strVariableValue
     */
    public String getVariableValue() {
        return strVariableValue;
    }
    /**
     * @param strVariableValue the strVariableValue to set
     */
    public void setVariableValue(String strVariableValue) {
        this.strVariableValue = strVariableValue;
    }
    @Override
    public String toString()
    {
        return this.strVariableName + this.strVariableValue;
    }
    //public ArrayList<
}
