package pk.com.rsoft.classcontractstestbed.classcontract;

import java.io.Serializable;
import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.tree.DefaultMutableTreeNode;

import pk.com.rsoft.classcontractstestbed.util.graph.ClassVariable;
import pk.com.rsoft.classcontractstestbed.util.inequality.InEqualitySimplified;
import pk.com.rsoft.classcontractstestbed.util.parser.CTStringParser;

/**
 * @author Rehan Farooq
 */
public class CTContext implements Serializable {
	private ArrayList<CTAttribute> lstAttributes;
	private ArrayList<CTOperation> lstOperations;
	private CTInvarient theInvarient;
	private String strCTContextName;
	private TreeNode theContextNode;

	public CTContext(TreeNode theNode) {
		strCTContextName = "";
		lstAttributes = new ArrayList<CTAttribute>();
		lstOperations = new ArrayList<CTOperation>();
		theContextNode = theNode;
		parseContextNode(theNode);
	}

	public void addAttribute(TreeNode attNode) {
		CTAttribute atr = new CTAttribute(attNode);
		this.addAttribute(atr);
	}

	public void addOperation(TreeNode optNode) {
		CTOperation tempOp = new CTOperation(optNode);
		if (tempOp.getCTOperationName().equals(this.getCTContextName())) {
			tempOp.setConstructor(true);
		}
		getLstOperations().add(tempOp);

	}

	public void addAttribute(CTAttribute anAttrib) {
		if (!isDuplicateAttribute(anAttrib)) {
			lstAttributes.add(anAttrib);
		}
	}

	public void addOperation(CTOperation anOpp) {
		getLstOperations().add(anOpp);
	}

	public void addInvarient(CTInvarient anInv) {
		this.setInvarient(anInv);
	}

	public void addInvarient(TreeNode invNode) {
		this.setInvarient(new CTInvarient(invNode));
	}

	private void parseContextNode(TreeNode ctNode) {
		if (ctNode.isLeaf()) {
			this.setCTContextName(ctNode.toString().trim());
			this.setCTContextName(CTStringParser
					.extractNameFromQuotes(getCTContextName()));
		} else {
			DefaultMutableTreeNode tm = (DefaultMutableTreeNode) ctNode;
			parseContextNode(tm.getChildAt(0));
		}
	}

	/**
	 * @return the lstAttributes
	 */
	public ArrayList<CTAttribute> getLstAttributes() {
		return lstAttributes;
	}

	/**
	 * @return the lstOperations
	 */
	public ArrayList<CTOperation> getLstOperations() {
		return lstOperations;
	}

	/**
	 * @return the strCTContextName
	 */
	public String getCTContextName() {
		return strCTContextName;
	}

	/**
	 * @param strCTContextName
	 *            the strCTContextName to set
	 */
	public void setCTContextName(String strCTContextName) {
		this.strCTContextName = strCTContextName;
	}

	/**
	 * @return the theInvarient
	 */
	public CTInvarient getInvarient() {
		return theInvarient;
	}

	/**
	 * @param theInvarient
	 *            the theInvarient to set
	 */
	public void setInvarient(CTInvarient theInvarient) {
		this.theInvarient = theInvarient;
	}

	public String getVaiableNames() {
		/*
		 * Very Very Important! This code extracts the varibale and methods from
		 * the OCL but at the moment there is a constraint on the varibale
		 * declartion that is only those variables are picked which are declared
		 * in the OCL Init statements (in future there can be a possibility of
		 * infering from the pre and post conditions but at the moment it is
		 * implemented that way)
		 */
		StringBuilder strRetVal = new StringBuilder("");
		if (this.lstAttributes != null) {
			for (CTAttribute at : this.lstAttributes) {
				strRetVal.append(at.getType()).append(" ").append(at.getName())
						.append(",");
			}
		} else {
			System.out.println("Variables Names list empty");
		}
		return strRetVal.toString();
	}

	public String getOperationNames() {
		StringBuilder strRetVal = new StringBuilder("");
		if (this.lstOperations != null) {
			for (CTOperation op : this.lstOperations)
				strRetVal.append(op.getCTOperationName()).append(",");

		}
		return strRetVal.toString();
	}

	public ArrayList<CTAttribute> getStateVariables() {
		ArrayList<CTAttribute> lst = new ArrayList<CTAttribute>();
		for (CTAttribute atr : this.lstAttributes) {
			for (CTOperation op : this.lstOperations) {
				if (op.isPreCondtionVariable(atr.getName())) {
					boolean duplicate = false;
					for (CTAttribute at : lst) {
						if (at.getName().equals(atr.getName())) {
							duplicate = true;
						}
					}
					if (!duplicate) {

						lst.add(atr);
					}
				}
			}
		}

		return lst;
	}

	public String getStateVariableNames() {
		HashSet<String> st = new HashSet<String>();
		for (CTAttribute atr : this.lstAttributes) {
			for (CTOperation op : this.lstOperations) {
				if (op.isPreCondtionVariable(atr.getName())) {
					st.add(atr.getName());
				}
			}
		}

		return st.toString();
	}

	public ArrayList<String> getAllValuesStrings(String strVarName) {

		ArrayList<String> retVals = new ArrayList<String>();
		for (CTOperation op : this.lstOperations) {
			for (String val : op.getVarValues(strVarName, ConstraintType.PRE)) {
				if (!Containts(retVals, val)) {
					retVals.add(val.trim());
				}
			}
			for (String val : op.getVarValues(strVarName, ConstraintType.POST)) {
				if (!Containts(retVals, val)) {
					retVals.add(val.trim());
				}
			}
		}
		for (CTConstraint ctx : this.theInvarient.lstConstraints) {
			if (ctx.getVariableName().equals(strVarName)
					&& !ctx.getVariableValue().trim().equals("")) {
				if (!Containts(retVals, strVarName)) {

					retVals.add("Inv " + ctx.getVariableValue().trim());
				}
			}
		}
		return retVals;
	}

	private boolean Containts(ArrayList<String> lst, String str) {
		for (String s : lst) {
			if (s.equals(str)) {
				return true;
			}
		}
		return false;
	}

	private boolean Containts(String strVal, ArrayList<InEqualitySimplified> lst) {
		for (InEqualitySimplified s : lst) {
			if (s.getVariable().getValue().trim().equals(strVal.trim())) {
				return true;
			}
		}
		return false;
	}

	public String getInvarientsDesc() {
		StringBuilder strBld = new StringBuilder();
		for (CTConstraint ct : this.theInvarient.getConstrantsList()) {
			strBld.append(ct.getVariableName()).append(" ")
					.append(ct.getVariableValue()).append(", ");
		}
		return strBld.toString();
	}

	public void ClearLists() {
		this.lstAttributes.clear();
		this.lstOperations.clear();
		this.theInvarient.lstConstraints.clear();
	}

	private boolean isDuplicateAttribute(CTAttribute atrib) {
		for (CTAttribute at : lstAttributes) {
			if (at.getName().equals(atrib.getName())) {
				return true;
			}
		}
		return false;
	}

	/*
	 * This method should return the state variables with simplified set of
	 * possible values These values should not include values having '@pre' etc
	 * but should have the refined possible set of values
	 */
	public ArrayList<ClassVariable> getStateVar() {
		// StateVaiable List to return
		ArrayList<ClassVariable> lstStateVars = new ArrayList<ClassVariable>();
		// Current variables of interest
		ArrayList<CTAttribute> lstAttrib = getStateVariables();

		for (CTAttribute atr : lstAttrib)// for each attribute
		{
			// Create new state variable to return
			ClassVariable stVar = new ClassVariable(atr.getName(),
					atr.getInitVal(), atr.getType());

			// Get All the values attached with this attribute
			ArrayList<String> lstVals = getAllValuesStrings(atr.getName());
			for (String str : lstVals)// for each attribute value
			{
				if (str.contains("@pre"))// if the value contains @pre tag try
											// to asses possible output value(s)
				{
					String temp = "";
					for (CTOperation op : this.lstOperations) {
						if (op.isPreCondtionVariable(stVar.getName())) {
							temp = str;
							if (!Containts(temp, stVar.getValues()))// Add if to
																	// values if
																	// not
																	// already
																	// added
							{
								InEqualitySimplified tempInq = new InEqualitySimplified(
										atr.getName() + " " + temp,
										getVariableType(atr.getName()));
								stVar.addValue(tempInq);
							}
						}
					}
				} else // No @Pre tag
				{
					if (!Containts(str, stVar.getValues())) // Add it to values
															// if not already
															// added
					{
						InEqualitySimplified tempInq = new InEqualitySimplified(
								atr.getName() + " " + str,
								getVariableType(atr.getName()));
						stVar.addValue(tempInq);
					}
				}
			}

			lstStateVars.add(stVar);
		}
		return lstStateVars;
	}

	public CTVariableType getVariableType(String strVarName) {
		for (CTAttribute att : this.lstAttributes) {
			if (att.getName().trim().equals(strVarName.trim())) {
				return att.getCTType();
			}
		}
		return CTVariableType.OTHER;
	}

	public TreeNode getContextNode() {
		return this.theContextNode;
	}
}
