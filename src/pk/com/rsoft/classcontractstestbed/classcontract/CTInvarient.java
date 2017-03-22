/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pk.com.rsoft.classcontractstestbed.classcontract;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

/**
 *
 * @author Hammad
 */
public class CTInvarient extends CTConstraintbase {
    public CTInvarient(TreeNode theNode)
    {
        this.cType = ConstraintType.INVARIENT;
        parseInvarientNode(theNode);
    }
    private void parseInvarientNode(TreeNode theNode)
    {
        ProcessAllChildren((DefaultMutableTreeNode) theNode);
    }
	@Override
	protected void processConditionalConstraint(TreeNode theIfElseNode) {
		// TODO Auto-generated method stub
		
	}
    
}
