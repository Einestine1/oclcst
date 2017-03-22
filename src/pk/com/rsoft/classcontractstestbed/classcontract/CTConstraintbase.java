/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pk.com.rsoft.classcontractstestbed.classcontract;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

/**
 *
 * @author Hammad
 */
public abstract class CTConstraintbase implements Serializable{

 protected ArrayList<CTConstraint> lstConstraints;
 //protected ArrayList<DefaultMutableTreeNode> lstConditionalNodes;
 protected ConstraintType cType;
 public CTConstraintbase()
 {
    this.lstConstraints = new ArrayList<CTConstraint>();
 //   this.lstConditionalNodes = new ArrayList<DefaultMutableTreeNode>();
 }
 protected void ProcessAllChildren(DefaultMutableTreeNode theRoot)
 {

    processTreeNode(theRoot);
    Enumeration<?> e = theRoot.children();
     if(theRoot.toString().startsWith("IfExpCs"))//This code block skips if part of If Expersion
     {
//    	 this.lstConditionalNodes.add(theRoot);
    	 processConditionalConstraint(theRoot);
     }                   //to consider the varibles inside the if() brackets
     

    while(e.hasMoreElements())
    {
        Object o = e.nextElement();
        ProcessAllChildren((DefaultMutableTreeNode)o);
    }
}

    protected void processTreeNode(DefaultMutableTreeNode theRoot)
    {
        String nodeVal = theRoot.toString();
        if(nodeVal.startsWith("BinaryRelationalExpCs"))
        {
            this.lstConstraints.add(new CTConstraint(theRoot));
        }
    }
    public ArrayList<CTConstraint> getConstrantsList()
    {
        return this.lstConstraints;
    }
    public boolean isInCondition(String strVarName)
    {
        if(this.lstConstraints==null)
        {
            return false;
        }
        for(CTConstraint ct : this.lstConstraints)
        {
            if(ct.getVariableName().equalsIgnoreCase(strVarName)||ct.getVariableName().equalsIgnoreCase("self . "+strVarName))
            {
                return true;
            }
        }
        return false;
    }
    public ConstraintType getType(){
        return this.cType;
    }
    public void SetType(ConstraintType type)
    {
        this.cType = type;
    }
    public ArrayList<String> getVarVals(String varName)
    {
        ArrayList<String> toReturn = new ArrayList<String>();
        for(CTConstraint cs:this.lstConstraints)
        {
            if(cs.getVariableName().equalsIgnoreCase("self . "+varName))
            {
                toReturn.add(cs.getVariableValue());
            }
            else if(cs.getVariableName().startsWith("self . "+varName+" @"))
            {
                toReturn.add(cs.getVariableValue());
            }
        }

        return toReturn;
    }
/**
 * RED ALLERT 1 : Please check this method again
 * @param varName
 * @return
 */
    public String getVarVal(String varName)//, ArrayList<InEqualitySimplified> lstCurrentVals)
    {
    	boolean first =true;
    	//System.out.println("getVarVal: input variable name is " + varName);
        StringBuilder tmp = new StringBuilder();
        for(CTConstraint cs:this.lstConstraints)
        {
        	if(cs.getVariableName().equalsIgnoreCase("self . "+varName) && first==true)
            {
                tmp.append(cs.getVariableValue());
                first = false;
            }
            else if((cs.getVariableName().startsWith("self . "+varName+" @") || cs.getVariableName().startsWith("Self . "+varName+" @")) && first==true)
            {
                tmp.append(cs.getVariableValue());
                first = false;
            }
            else if(cs.getVariableName().trim().equals(varName.trim()) && first==true)
            {
            	tmp.append(cs.getVariableValue());
            	first = false;
            }
        }
        return tmp.toString();
    }
    /**
     *This abstract method is just like C# delegate and lets know the child classes
     *that a complex constraint is being processed so do some thing about it if desired 
     * @param theIfElseNode
     */
    protected abstract void processConditionalConstraint(TreeNode theIfElseNode);
}
