/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pk.com.rsoft.classcontractstestbed.classcontract;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

/**
 *
 * @author Hammad
 */
public class CTPostCondition extends CTConstraintbase{
	
	private CTConditionalConstraint theOptionalConditionalConstraint=null;
	public CTPostCondition(TreeNode theNode)
    {
        this.cType = ConstraintType.POST;
        parsePostconditionNode(theNode);
    }
    private void parsePostconditionNode(TreeNode theNode)
    {
        ProcessAllChildren((DefaultMutableTreeNode)theNode);
    }
    @Override 
    protected void processConditionalConstraint(TreeNode theIfElseNode) {
    	// TODO Auto-generated method stub    	
    	this.theOptionalConditionalConstraint = new CTConditionalConstraint(theIfElseNode);
    }
    public CTConditionalConstraint getConditionalConstraint()
    {
    	return this.theOptionalConditionalConstraint;
    }
    @Override
    public ArrayList<String> getVarVals(String varName)
    {
    	ArrayList<String> retList = super.getVarVals(varName);
    	if(this.theOptionalConditionalConstraint==null)
    	{
    		return retList;
    	}
    	else
    	{
    		
    		retList= removeIfCondition(theOptionalConditionalConstraint, retList);

    	}
    	return retList;
    }
    private ArrayList<String> removeIfCondition(CTConditionalConstraint constraint, ArrayList<String> sourceList)
    {
    	ArrayList<String> retList = new ArrayList<String>();
    	for(String string : sourceList)
    	{
    		if(!string.equals(constraint.getCondition().getVariableValue()))
    		{
    			retList.add(string);
    		}
    	}
    	return retList;
    }
}
