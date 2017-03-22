/**
 * 
 */
package pk.com.rsoft.classcontractstestbed.classcontract;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.tree.TreeNode;

/**
 * @author Rehan
 *
 */
public class CTConditionalConstraint implements Serializable{

	/**
	 * 
	 */
	private boolean isConditionProcessed = false;
	private CTConstraint theCondition;
	private ArrayList<CTConstraint> lstResults;
	private int count = 1;
	public CTConditionalConstraint(TreeNode theIfElseNode) {
		// TODO Auto-generated constructor stub
		lstResults  = new ArrayList<CTConstraint>();
		parseConditionalConstraint(theIfElseNode);
	}
	private void parseConditionalConstraint(TreeNode theIfElseNode)
	{
		Enumeration<?> e = theIfElseNode.children();
		String nodeVal;
		while(e.hasMoreElements())
		{
			TreeNode t = (TreeNode) e.nextElement();			
			nodeVal = t.toString();
			if(nodeVal.startsWith("Expression"))
			{
				this.theCondition = new CTConstraint(t);		
			}
			else if(nodeVal.startsWith("BinaryRelationalExpCs"))
        	{		
				if(isConditionProcessed==true)
				{
					lstResults.add(new CTConstraint(t));
				}
				else
				{
					isConditionProcessed =true;
				}

       		}
			parseConditionalConstraint(t);
		}
	}
	public CTConstraint getCondition()
	{
		return theCondition;
	}
	public CTConstraint getResultIfTrue()
	{
		return lstResults.get(0);
	}
	public CTConstraint getResultIfFalse()
	{
		return lstResults.get(1);
	}
	@Override
	public String toString()
	{
		String retStr = "If "+this.theCondition + " Then " + this.lstResults.get(0);
		if(this.lstResults.size()==2)
		{
			retStr +=" Else " + this.lstResults.get(1);
		}
		retStr+=" EndIf";
		return retStr;
	}
}
