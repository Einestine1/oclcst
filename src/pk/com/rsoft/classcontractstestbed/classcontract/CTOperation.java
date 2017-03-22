/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pk.com.rsoft.classcontractstestbed.classcontract;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import pk.com.rsoft.classcontractstestbed.util.inequality.InEqualitySimplified;
import pk.com.rsoft.classcontractstestbed.util.inequality.InequalityOperatorType;
import pk.com.rsoft.classcontractstestbed.util.inequality.InequationSolver;
import pk.com.rsoft.classcontractstestbed.util.parser.CTStringParser;

/**
 *
 * @author Hammad
 */
public class CTOperation implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String strCTOperationName;
    private String strSignature;
    private boolean Constructor = false;
    private ArrayList<CTPostCondition> lstPostConditions;
    private ArrayList<CTPreCondition> lstPreConditions;
    private ArrayList<CTMethodParameter> lstParams;
    static int count=0; 
    private int thisnumber;
    public CTOperation(TreeNode theNode)
    {
        this.lstPostConditions = new ArrayList<CTPostCondition>();
        this.lstPreConditions = new ArrayList<CTPreCondition> ();
        this.lstParams = new ArrayList<CTMethodParameter>();
        parseOperationNode(theNode);
        count++;
        setMethodNumber(count);
    }
    private void parseOperationNode(TreeNode cpNode)
    {
    DefaultMutableTreeNode tm = (DefaultMutableTreeNode)cpNode;

    for(int ind = 0; ind <tm.getChildCount();ind++)
    {
        testChildNode(tm.getChildAt(ind));
    }

    }
    private void testChildNode(TreeNode atNode) {
        String strNodeType = atNode.toString().trim();
        if(strNodeType.startsWith("PathNameCs"))
        {
            DefaultMutableTreeNode tm = (DefaultMutableTreeNode) atNode;
            setCTOperationName(tm.getChildAt(1));
        }
        else if (strNodeType.startsWith("OperationSignatureCs"))
        {
            addSignature(atNode);
        }
        else if (strNodeType.startsWith("OperationConstraintCs"))
        {
            addConstraint(atNode);
        }
    }

    /**
     * @return the strCTOperationName
     */
    public String getCTOperationName() {
        return strCTOperationName;
    }

    /**
     * @param strCTOperationName the strCTOperationName to set
     */
    public void setCTOperationName(String strCTOperationName) {
        this.strCTOperationName = strCTOperationName;
    }
    private void setCTOperationName(TreeNode aNode)
    {
        if(aNode.isLeaf())
        {
            setCTOperationName(aNode.toString());
            this.setCTOperationName(CTStringParser.extractNameFromQuotes(getCTOperationName()));

        }
        else
        {
            DefaultMutableTreeNode tm = (DefaultMutableTreeNode) aNode;
            setCTOperationName(tm.getChildAt(0));
        }
    }
    private void addSignature(TreeNode aNode)
    {
        this.strSignature = CTStringParser.extractNameFromQuotes(aNode.toString());
        parseSignature();
    }
    private void addConstraint(TreeNode aNode)
    {
        String strVal;
        strVal = aNode.toString();
        if(strVal.contains("\"pre"))
        {
            addPreCondition(aNode);
        }
        if(strVal.contains("\"post"))
        {
            addPostCondition(aNode);
        }
    }

    /**
     * @return the strSignature
     */
    public String getSignature() {
        return strSignature;
    }

    /**
     * @param strSignature the strSignature to set
     */
    public void setSignature(String strSignature) {
        this.strSignature = strSignature;
    }

    public ArrayList<CTMethodParameter> getParameters()
    {
        return this.lstParams;
    }
    private void parseSignature()
    {
        String strVal = this.strSignature.replace('(', ' ');
        strVal = strVal.substring(0,strVal.indexOf(")")-1);

        String []paramVals = strVal.split(",");
        for(int i=0; i<paramVals.length;i++)
        {
        	CTMethodParameter param = new CTMethodParameter(paramVals[i]);
            lstParams.add(param);
        }
    }
    private void addPreCondition(TreeNode aNode) {
       this.lstPreConditions.add(new CTPreCondition(aNode));
    }

    private void addPostCondition(TreeNode aNode) {
        this.lstPostConditions.add(new CTPostCondition(aNode));
    }
    public ArrayList<CTPostCondition> getPostConditions()
    {
        return this.lstPostConditions;
    }
    public ArrayList<CTPreCondition> getPreConditions()
    {
        return this.lstPreConditions;
    }

    public boolean isPreCondtionVariable(String strName)
    {
        for(CTPreCondition pre:this.lstPreConditions)
        {
            if(pre.isInCondition(strName) && pre.getType()==ConstraintType.PRE)
            {
                return true;
            }
        }
        return false;
    }
    public boolean isPostCondtionVariable(String strName)
    {
        for(CTPostCondition post:this.lstPostConditions)
        {
            if(post.isInCondition(strName) && post.getType()==ConstraintType.POST)
            {
                return true;
            }
        }
        return false;
    }    
    public ArrayList<String> getVarValues(String strName, ConstraintType type)
    {
        ArrayList<String> toReturn = new ArrayList<String>();
        if(type==ConstraintType.POST)
        {
        	for(CTPostCondition post:this.lstPostConditions)
	        {
	            for(String st: post.getVarVals(strName))
	            {
	                toReturn.add(st);
	            }
	        }
        }
        else if(type==ConstraintType.PRE)
        {        	
	        for(CTPreCondition pre : this.lstPreConditions)
	        {
	            for(String st: pre.getVarVals(strName))
	            toReturn.add(st);
	        }
        }

        return toReturn;
    }
    public ArrayList<InEqualitySimplified> getConstraints(ConstraintType type)
    {
    	ArrayList<InEqualitySimplified> retList = new ArrayList<InEqualitySimplified>();
    	if(type==ConstraintType.PRE)
    	{
    		for(CTPreCondition pre: this.lstPreConditions)
    		{
    		}
    	}
    	else if(type==ConstraintType.POST)
    	{
    		
    	}
    	return retList;
    	
    }
    public String getVarValue(String strName,ConstraintType type)
    {
        StringBuilder tem = new StringBuilder();
        if(type==ConstraintType.POST)
        {
	        for(CTPostCondition post:this.lstPostConditions)
	        {
	            if(post.getConditionalConstraint()!=null)
	            {
		            tem.append(post.getConditionalConstraint().getCondition().getVariableValue());
		            tem.append(post.getConditionalConstraint().getResultIfTrue().getVariableValue());
		            tem.append(post.getConditionalConstraint().getResultIfFalse().getVariableValue());
	            }
	            else
	            {
		            tem.append(post.getVarVal(strName));
	            }
	        }
        }
        else if(type==ConstraintType.PRE)
        {
	        for(CTPreCondition pre : this.lstPreConditions)
	        {
	            tem.append(pre.getVarVal(strName));
	        }
        }
        return tem.toString();
    }
public String getVarValue(InEqualitySimplified inq,ConstraintType type)
{
	String strName = inq.getVariableName();
	StringBuilder tem = new StringBuilder();
    if(type==ConstraintType.POST)
    {
        for(CTPostCondition post:this.lstPostConditions)
        {
            if(post.getConditionalConstraint()!=null)
            {
	            tem.append(post.getConditionalConstraint().getCondition().getVariableValue());
	            tem.append(post.getConditionalConstraint().getResultIfTrue().getVariableValue());
	            tem.append(post.getConditionalConstraint().getResultIfFalse().getVariableValue());
            }
            else
            {
	            tem.append(post.getVarVal(strName));
            }
        }
    }
    else if(type==ConstraintType.PRE)
    {
        for(CTPreCondition pre : this.lstPreConditions)
        {
            tem.append(pre.getVarVal(strName));
        }
    }
    return tem.toString();
}

public String getVarValue(InEqualitySimplified inqVar,ArrayList<InEqualitySimplified> preVals,ConstraintType type)
{
	String strName = inqVar.getVariableName();
	StringBuilder tem = new StringBuilder();
    if(type==ConstraintType.POST)
    {
        for(CTPostCondition post:this.lstPostConditions)
        {
        	
        	CTConditionalConstraint cons = post.getConditionalConstraint();
        	if(cons==null)
        	{
	            tem.append(post.getVarVal(strName));        		
        	}
        	else
        	{
        		
        		if(this.isInTrueCondition(inqVar, cons)||this.isInFalseCondition(inqVar, cons))	
        		{
        			if(isConditionTrue(preVals, cons))
        			{
            			tem.append(cons.getResultIfTrue().getVariableValue());
        			}
        			else
        			{
        				tem.append(cons.getResultIfFalse().getVariableValue());
        			}
        		}
        		else
        		{
        				tem.append(post.getVarVal(strName));
        		}
            }  
        }
    }
    else if(type==ConstraintType.PRE)
    {
        for(CTPreCondition pre : this.lstPreConditions)
        {
            tem.append(pre.getVarVal(strName));
        }
    }
    return tem.toString();
}

public ArrayList<String> getVarValues(InEqualitySimplified inqVar,ArrayList<InEqualitySimplified> preVals,ConstraintType type)
{
	String strName = inqVar.getVariableName();
	ArrayList<String> retList= new ArrayList<String>();
    if(type==ConstraintType.POST)
    {
        for(CTPostCondition post:this.lstPostConditions)
        {
        	
        	CTConditionalConstraint cons = post.getConditionalConstraint();
        	if(cons==null)
        	{
	            retList.add(post.getVarVal(strName));        		
        	}
        	else
        	{
        		
        		if(this.isInTrueCondition(inqVar, cons)||this.isInFalseCondition(inqVar, cons))	
        		{
        			if(isConditionTrue(preVals, cons))
        			{
            			retList.add(cons.getResultIfTrue().getVariableValue());
            			System.out.println("Its True..!    "+inqVar.getVariableName()+"--->"+cons.getResultIfTrue().getVariableValue());
        			}
        			else
        			{
        				retList.add(cons.getResultIfFalse().getVariableValue());
        				System.out.println("Its False..!    "+inqVar.getVariableName()+"--->"+cons.getResultIfFalse().getVariableValue());
        				
        			}
        		}
        		else
        		{
        				retList.add(post.getVarVal(strName));
        		}
            }  
        }
    }
    else if(type==ConstraintType.PRE)
    {
        for(CTPreCondition pre : this.lstPreConditions)
        {
            retList.add(pre.getVarVal(strName));
        }
    }
    return retList;
}


    /**
     * @return the Constructor
     */
    public boolean isConstructor() {
        return Constructor;
    }

    /**
     * @param Constructor the Constructor to set
     */
    public void setConstructor(boolean Constructor) {
        this.Constructor = Constructor;
    }
    public boolean isInParameterLis(String strVarName)
    {
    	for(CTMethodParameter param : lstParams)
    	{
    		if(param.getName().trim().equals(strVarName.trim()))
    		{
    			return true;
    		}
    	}
    	return false;
    }
    public CTMethodParameter getParameterByName(String strParamName)
    {
    	
    	for(CTMethodParameter param : lstParams)
    	{
    		if(param.getName().trim().equals(strParamName.trim()))
    		{
            	param.setConstraints(getParameterConstraints(param.getName()));
    			return param;
    		}
    	}
    	return null;
    	
    }
    public ArrayList<CTConstraint> getParameterConstraints(String strParam)
    {
    	ArrayList<CTConstraint> retList = new ArrayList<CTConstraint>();
    	for(CTPreCondition pre: lstPreConditions)
    	{
    		for(CTConstraint ctConst :pre.getConstrantsList())
    		{
    			if(ctConst.getVariableName().trim().equals(strParam.trim()))
    					{
    						retList.add(ctConst);
    					}
    		}
    	}
    	return retList;
    }
    private boolean isInTrueCondition(InEqualitySimplified inq, CTConditionalConstraint constraint)
    {
    	return inq.getVariableName().equals(constraint.getResultIfTrue().getVariableNameRemovingSelf().trim());
    }
    private boolean isInFalseCondition(InEqualitySimplified inq, CTConditionalConstraint constraint)
    {
    	return inq.getVariableName().equals(constraint.getResultIfFalse().getVariableNameRemovingSelf().trim());
    }
    private boolean isConditionTrue(ArrayList<InEqualitySimplified> currentVars, CTConditionalConstraint theConstraint)
    {

    	if(theConstraint==null)
    	{
    		return false;
    	}
    	for(InEqualitySimplified inq: currentVars)
    	{
    		if(inq.getVariableName().toUpperCase().equals(theConstraint.getCondition().getVariableNameRemovingSelf().toUpperCase().replace("@PRE", " ").trim()))
    		{
    			
    			String conditionalEquation = inq.getVariableName()+ theConstraint.getCondition().getVariableValue();
    			InEqualitySimplified cond = new InEqualitySimplified(conditionalEquation,inq.getVariableType());
				String strOperator = InequalityOperatorType.toString(cond.getType());
				if(strOperator.trim().equals("="))//if operator is equality '=' it is converted to '=='
					{
						strOperator+="=";
					}
				strOperator = " " + strOperator + " " ; //just add starting and ending spaces to the operator
				//Following line Computers the actual truth value for the  
				Object obj1 = InequationSolver.evaluate(inq.getVariable().getValue().toLowerCase() + strOperator + cond.getVariable().getValue().toLowerCase()) ;
//				System.out.println((inq.getVariable().getValue().toLowerCase() + strOperator + cond.getVariable().getValue().toLowerCase()));
				if(obj1!=null)
				{
//					System.out.println((inq.getVariable().getValue().toLowerCase() + strOperator + cond.getVariable().getValue().toLowerCase()));
//					System.out.println(Boolean.valueOf(obj1.toString()));
					return Boolean.valueOf(obj1.toString());
				}
    		}
    	}
    	return false;
    }
    /**
	 * @param thisnumber the thisnumber to set
	 */
	public void setMethodNumber(int thisnumber) {
		this.thisnumber = thisnumber;
	}
	/**
	 * @return the thisnumber
	 */
	public int getMethodNumber() {
		return thisnumber;
	}
	@Override
    public void finalize()
    {
    	count--;
    }
    
    public static int getCount()
    {
    	return count;
    }
    public int getParamCount()
    {
    	return this.lstParams.size();
    }
}
