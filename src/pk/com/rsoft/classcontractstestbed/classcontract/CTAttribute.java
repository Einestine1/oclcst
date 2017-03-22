/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pk.com.rsoft.classcontractstestbed.classcontract;

import java.io.Serializable;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import pk.com.rsoft.classcontractstestbed.util.parser.CTStringParser;

/**
 *
 * @author Hammad
 */
public class CTAttribute implements Serializable{
    private String strName;
    private String strType;
    private CTVariableType type = CTVariableType.OTHER;
    private String strDefaultVal;
//    private
    private String strContextName;
    public CTAttribute(TreeNode theNode)
    {
        parseAttributeNode(theNode);
    }
private void parseAttributeNode(TreeNode atNode)
    {
    DefaultMutableTreeNode tm = (DefaultMutableTreeNode)atNode;

    //java.util.Enumeration en = tm.children();
    for(int ind = 0; ind <tm.getChildCount();ind++)
    {
        testChildNode(tm.getChildAt(ind));
    }

    }

    private void testChildNode(TreeNode atNode) {
        String strNodeType = atNode.toString();
        if(strNodeType.startsWith("PathNameCs"))
        {
            DefaultMutableTreeNode tm = (DefaultMutableTreeNode) atNode;
            setName(tm.getChildAt(1));
        }
        else if (strNodeType.startsWith("SimpleTypeTypeSpecifier"))
        {
            setType(atNode);
        }
        else if (strNodeType.startsWith("InitInitOrDerValueCs"))
        {
            setInitVal(atNode);
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
    public String getType() {
        return strType;
    }

    /**
     * @param strType the strType to set
     */
    public void setType(String strType) {
        this.strType = strType;
        if(strType.trim().equalsIgnoreCase("INTEGER"))
        {
            this.setType(CTVariableType.INTEGER);
        }
        else if(strType.trim().equalsIgnoreCase("REAL"))            
        {
            this.setType(CTVariableType.REAL);
        }
        else if(strType.trim().equalsIgnoreCase("BOOLEAN"))
        {
            this.setType(CTVariableType.BOOLEAN);
        }
        else if(strType.trim().equalsIgnoreCase("STRING"))
        {
            this.setType(CTVariableType.STRING);
        }
        else
        {
            this.setType(CTVariableType.OTHER);
        }
    }

    /**
     * @return the strDefaultVal
     */
    public String getInitVal() {
        return strDefaultVal;
    }

    /**
     * @param strDefaultVal the strDefaultVal to set
     */
    public void setInitVal(String strDefaultVal) {
        this.strDefaultVal = strDefaultVal;
    }
    
    private void setName(TreeNode aNode)
    {
        if(aNode.isLeaf())
        {
            setName(aNode.toString());
            this.setName(CTStringParser.extractNameFromQuotes(getName()));
        }
        else
        {
            DefaultMutableTreeNode tm = (DefaultMutableTreeNode) aNode;
            setName(tm.getChildAt(0));
        }
    }

    private void setType(TreeNode aNode)
    {
        if(aNode.isLeaf())
        {
            setType(aNode.toString());
            this.setType(CTStringParser.extractNameFromQuotes(getType()));
        }
        else
        {
            DefaultMutableTreeNode tm = (DefaultMutableTreeNode) aNode;
            setType(tm.getChildAt(0));
        }
    }
    private void setInitVal(TreeNode aNode)
    {
        if(aNode.isLeaf())
        {
            setInitVal(aNode.toString());
            this.setInitVal(CTStringParser.extractNameFromQuotes(getInitVal()));
   //         System.out.println(this.getInitVal());

        }
        else
        {
            DefaultMutableTreeNode tm = (DefaultMutableTreeNode) aNode;
            setInitVal(tm.getChildAt(0));
        }
    }

    /**
     * @return the strContextName
     */
    public String getContextName() {
        return strContextName;
    }

    /**
     * @param strContextName the strContextName to set
     */
    public void setContextName(String strContextName) {
        this.strContextName = strContextName;
    }

    /**
     * @return the type
     */
    public CTVariableType getCTType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(CTVariableType type) {
        this.type = type;
    }
    
}
