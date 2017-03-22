/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pk.com.rsoft.classcontractstestbed.classcontract;

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.tree.TreeNode;

/**
 *
 * @author Hammad
 */
public class CTMethodParameter implements Serializable{
    private String strName;
    private String strType;
    private ArrayList<CTConstraint> constraints;
    public CTMethodParameter(String name,String type)
    {
        this.strName = name;
        this.strType = type;
        constraints = new ArrayList<CTConstraint>();
    }
    public CTMethodParameter(String strParam)
    {
            this("","");
        if(strParam.trim().equals(""))
        {
            return;
        }
         this.strName = strParam.substring(0,strParam.indexOf(':')).trim();
         this.strType = strParam.substring(strParam.indexOf(":")+1,strParam.length()).trim();
    }
    public CTMethodParameter(TreeNode theNode) throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException("This Method is not implemented yet!");
        //parseParameterNode(theNode);
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
    public String getType() {
        return strType;
    }

    /**
     * @param strType the strType to set
     */
    public void setStrType(String strType) {
        this.strType = strType;
    }

    /**
     * @return the constraints
     */
    public ArrayList<CTConstraint> getConstraints() {
        return constraints;
    }

    /**
     * @param constraints the constraints to set
     */
    public void setConstraints(ArrayList<CTConstraint> constraints) {
        this.constraints = constraints;
    }
    public void addConstraint(CTConstraint constraint)
    {
    	this.constraints.add(constraint);
    }
//    private void parseParameterNode(TreeNode theNode)
//    {
//        DefaultMutableTreeNode tm = (DefaultMutableTreeNode) theNode;
//        Enumeration en =tm.depthFirstEnumeration();
//        DefaultMutableTreeNode tmTemp;
//        String strVal;
//        while(en.hasMoreElements())
//        {
//            tmTemp = (DefaultMutableTreeNode)en.nextElement();
//            strVal = tmTemp.toString();
//            if(strVal.startsWith("BinaryRelationalExpCs"))
//            {
//
//               // this.strDesc = CTStringParser.extractNameFromQuotes(strVal);
//               // this.strVariableValue = CTStringParser.extractNameFromQuotes(tmTemp.getChildAt(1).toString());
//               // this.strVariableName =  CTStringParser.extractNameFromQuotes(tmTemp.getChildAt(0).toString());
//            }
//
//        }
//
    public Object getTestInputValue()
    {
    	for(CTConstraint constraint: this.getConstraints())
    	{
    		return constraint.getVariableValue();
    	}
    	return null;
    }
    
    public Class getJavaType()
    {
    	if(this.strType.equalsIgnoreCase("Integer"))
    	{
    		return int.class;
    	}
    	else if(this.strType.equalsIgnoreCase("Real"))
    	{
    		return Double.class;
    	}
    	else if(this.strType.equalsIgnoreCase("Boolean"))
    	{
    		return Boolean.class;
    	}
    	else if(this.strType.equalsIgnoreCase("String"))
    	{
    		return String.class;
    	}
    	else
    	{
    		return Object.class;
    	}
    		 
    }
}
