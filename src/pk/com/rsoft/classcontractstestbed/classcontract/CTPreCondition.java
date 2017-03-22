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
public class CTPreCondition extends CTConstraintbase{
    public CTPreCondition(TreeNode aNode)
    {
        this.cType = ConstraintType.PRE;
        parsePreconditionNode(aNode);
    }
    private void parsePreconditionNode(TreeNode theNode)
    {
        ProcessAllChildren((DefaultMutableTreeNode)theNode);
    }
	@Override
	protected void processConditionalConstraint(TreeNode theIfElseNode) {
		// Empty body: we are not interested in handling the constraints 		
		
	}

}

