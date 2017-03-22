/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pk.com.rsoft.classcontractstestbed.util.parser;

import pk.com.rsoft.classcontractstestbed.classcontract.CTContext;
import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import tudresden.ocl20.core.parser.sablecc.lexer.Lexer;
import tudresden.ocl20.core.parser.sablecc.node.*;
import tudresden.ocl20.core.parser.sablecc.parser.Parser;
import tudresden.ocl20.core.parser.util.TextualCSTBuilder;
import java.util.HashSet;

/**
 *
 * @author Hammad
 */
public class OCLClassContractsParser {
    private StringBuilder builder = new StringBuilder("");
    private DefaultMutableTreeNode node;
    private HashSet<String> hs = new HashSet<String>();
    private static boolean isFirstContext = true;
    private ArrayList<CTContext> lstContext = new ArrayList<CTContext>();
    private CTContext ct;
    public DefaultMutableTreeNode getTree()
    {
        return node;
    }
    public String getOutput()
    {
        return builder.toString();
    }

    public void ParserOCL(String file)
    {
    //this.builder = new StringBuilder();
    try
    {
      long start_time = System.currentTimeMillis();

      Lexer lexer = new Lexer(new PushbackReader(new BufferedReader(new FileReader(file)), 1024));

      Parser parser = new Parser(lexer);

      Start ast = parser.parse();

      long stop_time = System.currentTimeMillis();
      System.out.println("Compilation time: " + (stop_time - start_time) + " ms");

        if (ast != null) {
        TextualCSTBuilder txtICTBuilder = new TextualCSTBuilder();
        ast.apply(txtICTBuilder);

        this.node = txtICTBuilder.getRootNode();
        ProcessAllChildren(node);
      }

    }
    catch (Exception e) {
      e.printStackTrace();
    }
    }
private void ProcessTreeNode(DefaultMutableTreeNode theRoot)
{

    String nodeVal = theRoot.toString().trim();
//    System.out.println(nodeVal);
    if(nodeVal.startsWith("ContextDeclarationListCs") &&isFirstContext)
        {
        addClassContext(theRoot);
        //System.out.println("---------------------->+"+nodeVal);

        isFirstContext=false;
    }
    if(nodeVal.startsWith("InvariantClassifierConstraintCs"))
        {
        //System.out.println("---------------------->+"+nodeVal);
        addInvarient(theRoot);
        }
    if(nodeVal.startsWith("OperationContextDeclarationCs"))
        {
        //System.out.println("---------------------->+"+nodeVal);
            addOperation(theRoot);
        }
    if(nodeVal.startsWith("AttrOrAssocContextDeclarationCs"))//||nodeVal.startsWith("OperationConstraintCs"))
    {
        //System.out.println("AttrOrAssocContextDeclarationCs---------------------->+"+nodeVal);
        addAttribute(theRoot);
    }
}
private void addClassContext(TreeNode theNode)    {
    if(this.ct ==null)
    {
        ct = new CTContext(theNode);
    }
//    this.hs.add(ct.getCTContextName());
    this.builder.append(theNode.toString()).append("\n");
}
private void addInvarient(TreeNode theNode)  {
    if(ct!=null)
    {

        ct.addInvarient(theNode);
    }
    this.builder.append(theNode.toString()).append("\n");
}
private void addOperation(TreeNode theNode){
    if(ct !=null)
    {
        ct.addOperation(theNode);
    }
//    CTOperation ctOpt = new CTOperation(theNode);
    this.builder.append(theNode.toString()).append("\n");
}
private void addAttribute(TreeNode theNode)
{
    if(ct!=null)
    {
        ct.addAttribute(theNode);
    }
//    CTAttribute at = new CTAttribute(theNode);
    this.builder.append(theNode.toString()).append("\n");
}
private void ProcessAllChildren(DefaultMutableTreeNode theRoot)
 {
    ProcessTreeNode(theRoot);
    Enumeration<?> e = theRoot.children();
    while(e.hasMoreElements())
    {
        Object o = e.nextElement();
        ProcessAllChildren((DefaultMutableTreeNode)o);
    }
}
public CTContext getContext()
{
    return this.ct;
}
}
