/*
 * This is the class representing an Abstract State Machine
 * it is responsible for creating, maintaining and running the AFSM Model
 */
package pk.com.rsoft.classcontractstestbed.util.graph;

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import pk.com.rsoft.classcontractstestbed.ClassVarDialog;
import pk.com.rsoft.classcontractstestbed.classcontract.CTContext;
import pk.com.rsoft.classcontractstestbed.classcontract.CTOperation;
import pk.com.rsoft.classcontractstestbed.classcontract.CTPostCondition;
import pk.com.rsoft.classcontractstestbed.classcontract.CTPreCondition;
import pk.com.rsoft.classcontractstestbed.classcontract.CTVariableType;
import pk.com.rsoft.classcontractstestbed.classcontract.ConstraintType;
import pk.com.rsoft.classcontractstestbed.testsequences.TestSequence;
import pk.com.rsoft.classcontractstestbed.testsequences.TestTransition;
import pk.com.rsoft.classcontractstestbed.util.graphics.Point;
import pk.com.rsoft.classcontractstestbed.util.graphics.Shape;
import pk.com.rsoft.classcontractstestbed.util.inequality.InEqualitySimplified;
import pk.com.rsoft.classcontractstestbed.util.inequality.InequalityOperatorType;
import pk.com.rsoft.classcontractstestbed.util.inequality.InequalitySolver;
import pk.com.rsoft.classcontractstestbed.util.inequality.InequationSolver;
import pk.com.rsoft.classcontractstestbed.util.parser.OperatorType;

/**
 * @author Rehan Farooq
 */
public class AbstractFSM implements Shape, Serializable {
	private static final long serialVersionUID = 1L;
	private ArrayList<State> lstStates;// The Array List containing all the
// states of the AFSM
static int intVal = 0;
// IMPORTANT: CLASS Variables are in the context of a Class and STATE
// Variables are in the context of AFSM State
private StringBuilder strLog = new StringBuilder();
private ArrayList<ClassVariable> lstStateVars;// The Array List containing
private CTContext ctx;// The Parsed Context for which AFSM is being built
ArrayList<TestSequence> testSequences;
final int START_X = 200;
final int X_INCREMENT = 150;
final int START_Y = 200;
final int Y_INCREMENT = 150;

/**
 * This Empty parameters constructor just initializes the state of AFSM with
 * Empty values! Caution: AFSM might not be useful after just this
 * initialization!
 */
public AbstractFSM() {
	this(null);// call the other constructor the DRY principal!
}

public AbstractFSM(CTContext ct) {
	this.setStatesList(new ArrayList<State>());
	this.lstStateVars = new ArrayList<ClassVariable>();
	if (ct == null) {
		return;// if ct CTContext is null no need to go further
	}
	this.setCtx(ct);// Record the this AFSM was built using this ct
	// ******************************//
	// Start of State Variables setup//
	// ******************************//
	initializeStateVariables();// Initialization of statate

	System.out.println("\nNumber of variables -->" + lstStateVars.size());
	System.out.println("State Variables as initialized :");
	System.out.println(this.lstStateVars);

	ArrayList<ClassVariable> lst = simp(ct.getLstOperations(),
			this.lstStateVars);

	System.out.println("\nList of states is Simplified:");// That is
															// constraints
															// having @pre
															// are
															// simplified
	System.out.println(lst);

	lst = removeExtraEqual(lst);
	System.out.println("\nAfter Removing unwanted EQUAL!");
	System.out.println(lst);

	lst = validateVariables(lst);
	System.out.println("\nAfter Validation!");// That is removing unwanted
												// and invalid values
	System.out.println(lst);

	this.lstStateVars = lst;

	lst = convertAllToAtomic(lst);
	System.out.println("\nAutomic vals the final Class Variable List:");
	System.out.println(lst);
	// ****************************//
	// End of State Variables setup//
	// ****************************//

	logAction(lst.toString());// log the value of state variabels after
								// setup
	lstStateVars = lst;
}

public void EditClassVariables() {
	ClassVarDialog dlg = new ClassVarDialog("Class Variables", true,
			this.lstStateVars);
	dlg.setVisible(true);
}

public boolean buildAFSM() {
	buildInitialStates();// Now buildup states

	if (this.lstStateVars.size() < 1) {
		JOptionPane
				.showMessageDialog(null,
						"No Variables of Interest found, this problems is not solveable!");
		return false;
	}
	this.lstStates = processAllStates(lstStates);
	adjustStatePositions();
	return true;
}

private ArrayList<ClassVariable> simp(ArrayList<CTOperation> ctOps,
		ArrayList<ClassVariable> theList) {
	ArrayList<ClassVariable> lstStVars = new ArrayList<ClassVariable>();
	for (ClassVariable var : theList) {
		lstStVars.add(simplyfy(var, ctOps));
	}
	return lstStVars;
}

private ClassVariable simplyfy(ClassVariable aVar,
		ArrayList<CTOperation> ctOps) {
	ClassVariable stVar = null;
	if (aVar.getType() != CTVariableType.INTEGER
			&& aVar.getType() != CTVariableType.REAL) {
		stVar = aVar;
	} else {
		stVar = new ClassVariable(aVar.getName(), aVar.getType());
		ArrayList<InEqualitySimplified> temInqs = new ArrayList<InEqualitySimplified>();
		for (CTOperation op : ctOps) {
			ClassVariable tempVar = getOutputValue(aVar, this.getContext(),
					op);
			for (InEqualitySimplified inq : tempVar.getValues()) {
				if (!inq.getVariable().getValue().toUpperCase()
						.contains("@PRE")) {
					inq.getVariable().setName(aVar.getName());
					if (!InqContainedInList(inq, temInqs)) {

						temInqs.add(inq);
					}
				}
			}
		}
		stVar.setVarValues(temInqs);

	}
	return stVar;
}

private void buildInitialStates() {
	CTContext theContext = this.getContext();
	lstStates.clear();
	ArrayList<CTOperation> lstOpts = getContext().getLstOperations();// Get
																		// the
																		// List
																		// of
																		// All
																		// Class
																		// Contract
																		// Operations
	// Get the List of All state variables
	// For All Operations in the Class Contract try to construct Absrtact
	// inital States
	for (CTOperation opt : lstOpts) {
		if (opt.isConstructor()) {
			State state = createSate(theContext, opt, true);
			lstStates.add(state);
		}
	}
}

/**
 * @param theContext
 * @param opt
 */
private State createSate(CTContext theContext, CTOperation opt,
		boolean isStartState) {
	// This is a Constructor create a new Initial abstract state
	State retState = new State(isStartState);
	for (ClassVariable clsVar : this.lstStateVars) {
		if (opt.isPostCondtionVariable(clsVar.getName())) {
			// It is in the post conditions so build it's output value
			String theVal = this.getPostConditionValue(clsVar.getName(),
					opt.getPostConditions());
			if (theVal != null) {
				retState.addStateVariable(new InEqualitySimplified(theVal,
						clsVar.getType()));
			}
		} else {//
				// It is not in the post condition so it's value from
				// defaults will be picked
			InEqualitySimplified defaultval = InequalitySolver
					.getDefaultValue(clsVar, theContext);
			retState.addStateVariable(defaultval);// add this value to state
													// variables of new
													// state
		}

	}// End for(ClassVariable v : this.lstStateVars)
	retState.setUnprocessed();
	return retState;
}
private ArrayList<State> getNextStates(State st, ArrayList<State> lstArray) {
	ArrayList<CTOperation> lstOps = getContext().getLstOperations();
	ArrayList<State> retList = new ArrayList<State>();
	for (CTOperation ops : lstOps) {
		if (isOperationExecutable(st, ops)) {
			System.out.println("Operation being processed "
					+ ops.getCTOperationName());
			State tempSt = getNextState(st, ops, lstArray);
			if (tempSt != null) {
				st.addTransition(new Transition(st, tempSt, ops));
				retList.add(tempSt);
			} else {
				System.out.println("No transition added ");
			}
		}
	}
	return retList;
}
private State getNextState(State st, CTOperation ops,
		ArrayList<State> lstArray) {
	State retState;
	if (isOperationExecutable(st, ops)) {
		ArrayList<InEqualitySimplified> nextVals = getNextValues(
				st.getCurrentValues(), ops, true);
		State stat = findInList(lstArray, nextVals);
		if (stat == null) {
			retState = new State(false);
			System.out.println("Current State count --> " + intVal);
			retState.setCurrentValues(nextVals);
		} else {
			retState = stat;
		}
		return retState;
	}

	return null;
}
private ArrayList<InEqualitySimplified> getNextValues(
		ArrayList<InEqualitySimplified> preValues, CTOperation ops,
		boolean simplifyIt) {
	ArrayList<InEqualitySimplified> retInqs = new ArrayList<InEqualitySimplified>();
	for (InEqualitySimplified theIneq : preValues)// for each value of
													// variables in the
													// preValues
	{
		if (ops.isPostCondtionVariable(theIneq.getVariableName().trim()))// if
																			// concerned
																			// variable
																			// is
																			// a
																			// Post
																			// condition
																			// variable
		{
			String strVal = ops.getVarValue(theIneq, preValues,
					ConstraintType.POST);// Get return constraint value from
											// the operation

			if (simplifyIt && strVal.toUpperCase().trim().contains("@PRE"))// if
																			// caller
																			// desired
																			// simplification
																			// and
																			// the
																			// value
																			// can
																			// be
																			// simplified
			{
				if (!OperatorType.isArithmeticExpression(strVal)) {
					retInqs.add(theIneq);
				} else {
					InEqualitySimplified newVal = new InEqualitySimplified(
							theIneq.getVariableName() + strVal,
							theIneq.getVariableType());
					newVal = InequalitySolver.simplify(newVal, ops,
							getContext(), preValues, lstStateVars);
					newVal.getVariable().setName(theIneq.getVariableName());
					retInqs.add(newVal);
				}
			} else// Just return the InEqualitySimplified without
					// simplification if any
			{
				InEqualitySimplified newVal = new InEqualitySimplified(
						theIneq.getVariableName() + strVal,
						theIneq.getVariableType());
				retInqs.add(newVal);
			}

		} else// if it is not a post condition variable
		{
			retInqs.add(theIneq);// just add it as unchanged value
		}
	}
	return retInqs;
}
private boolean isOperationExecutable(State s, CTOperation op) {
	if (op.isConstructor()) {
		return false;
	}
	ArrayList<InEqualitySimplified> lstCurrentVals = s.getCurrentValues();// States
																			// values
																			// of
																			// variables
	ArrayList<InEqualitySimplified> preConditions = InequalitySolver
			.getPreValues(op, getContext());// Pre condition values of the
											// variables
	boolean retVal = true;
	for (InEqualitySimplified varVal : lstCurrentVals)// for each current
														// value
	{
		for (InEqualitySimplified preVal : preConditions)// for each pre
															// condition
		{
			if (varVal.getVariableName().trim()
					.equals(preVal.getVariableName().trim()))// if we have
																// the same
																// variable
																// name
			{

				if (!InequationSolver.isTheSame(varVal, preVal)) {
					String strPreOperator = InequalityOperatorType
							.toString(preVal.getType());
					if (strPreOperator.trim().equals("="))// if operator is
															// equality '='
															// it is
															// converted to
															// '=='
					{
						strPreOperator += "=";
					}
					strPreOperator = " " + strPreOperator + " "; // just add
																	// starting
																	// and
																	// ending
																	// spaces
																	// to
																	// the
																	// operator
					// Following line Computers the actual truth value for
					// the
					Object obj1 = InequationSolver
							.evaluate(varVal.getVariable().getValue()
									.toLowerCase()
									+ strPreOperator
									+ preVal.getVariable().getValue()
											.toLowerCase());
					if (obj1 != null) {
						retVal = retVal && Boolean.valueOf(obj1.toString());
					}

					if (strPreOperator.trim().equals("==")) {
						String strPostOperator = InequalityOperatorType
								.toString(varVal.getType());
						if (strPostOperator.equals("=")) {
							strPostOperator += "=";
						}

						obj1 = InequationSolver.evaluate(preVal
								.getVariable().getValue().toLowerCase()
								+ strPostOperator
								+ varVal.getVariable().getValue()
										.toLowerCase());
						if (obj1 != null) {
							retVal = retVal
									&& Boolean.valueOf(obj1.toString());
						}

					}
				}
			}
		}
	}
	if (retVal == true)
		System.out.println(op.getCTOperationName() + " is executable!");
	else
		System.out.println(op.getCTOperationName() + " is not executable!");
	return retVal;
}
private void logAction(String strAction) {
	strLog.append(strAction).append("\n");
}
public void clearLog() {
	setLog(new StringBuilder().toString());
}
/**
 * @return the strLog
 */
public String getLog() {
	return strLog.toString();
}
/**
 * @param strLog
 * the strLog to set
 */
public void setLog(String strLog) {
	this.strLog = new StringBuilder(strLog);
}

public ClassVariable getOutputValue(ClassVariable currentVar,
		CTContext context, CTOperation op) {
	ClassVariable retVal = currentVar;
	if (!op.isPostCondtionVariable(currentVar.getName()))// if that veriable
															// is not in the
															// post
															// condition
															// then just
															// return
															// initial value
	{
		return retVal;
	} else {
		ClassVariable var = simplifyIt(currentVar, context, op);// Simplify
																// this
																// value of
																// state
																// variable
		return var;
	}
}

private ClassVariable simplifyIt(ClassVariable currentVar,
		CTContext context, CTOperation op) {
	ClassVariable stVar = new ClassVariable(currentVar.getName(),
			currentVar.getValueAt(0).toString(), currentVar.getType());
	// No this is the variable in the post condition of current operation we
	// need to
	// if we need processing to asses @pre key words
	for (InEqualitySimplified inq : currentVar.getValues()) {
		if (inq.getVariable().getValue().toUpperCase().contains("@PRE")) {
			stVar.addValue(InequalitySolver.simplify(inq, op, context));
		} else {
			stVar.addValue(inq);
		}
	}
	return stVar;
}
private void initializeStateVariables() {
	if (this.getContext() == null) {
		return;
	}
	ArrayList<ClassVariable> lstVars = getContext().getStateVar();
	InEqualitySimplified trueVal = new InEqualitySimplified(
			"trueVal = TRUE", CTVariableType.BOOLEAN);
	InEqualitySimplified falseVal = new InEqualitySimplified(
			"falseVal = FALSE", CTVariableType.BOOLEAN);

	ArrayList<InEqualitySimplified> lstInq = new ArrayList<InEqualitySimplified>();
	lstInq.add(trueVal);
	lstInq.add(falseVal);
	for (ClassVariable var : lstVars) {
		if (var.getType() == CTVariableType.BOOLEAN) {
			trueVal.getVariable().setName(var.getName());
			falseVal.getVariable().setName(var.getName());
			var.setVarValues(lstInq);
		}
	}
	this.lstStateVars = lstVars;
}

public boolean containsInPreCondition(CTOperation opt, ClassVariable var) {
	for (CTPreCondition pre : opt.getPreConditions()) {
		if (!pre.getVarVals(var.getName()).isEmpty()) {
			return true;
		}
	}
	return false;
}

public boolean containInPostCondition(CTOperation opt, ClassVariable var) {
	for (CTPostCondition post : opt.getPostConditions()) {
		if (post.isInCondition(var.getName())) {
			return true;
		}
	}
	return false;
}

private boolean InqContainedInList(InEqualitySimplified inq,
		ArrayList<InEqualitySimplified> lst) {
	for (InEqualitySimplified simpInq : lst) {
		if (InequationSolver.isTheSame(inq, simpInq)) {
			return true;
		}
	}
	return false;
}

private String RemoveEqualfromVal(String val) {
	String retVal = val;
	if (val.contains("EQUAL")) {
		retVal = val.substring(val.lastIndexOf("EQUAL") + "EQUAL".length());
	}

	return retVal;
}

private ArrayList<InEqualitySimplified> convertToAtomic(
		ArrayList<InEqualitySimplified> lst) {
	ArrayList<InEqualitySimplified> retList = new ArrayList<InEqualitySimplified>();
	for (InEqualitySimplified inq : lst) {
		if (InequalityOperatorType.isComposit(inq.getType())) {
			ArrayList<InEqualitySimplified> tempList = InequationSolver
					.split(inq);
			for (InEqualitySimplified simp : tempList) {

				if (!InqContainedInList(simp, retList)) {
					retList.add(simp);
				}
			}
		} else {
			if (!InqContainedInList(inq, retList)) {
				retList.add(inq);
			}

		}
	}
	return retList;
}

private String getPostConditionValue(String strName,
		ArrayList<CTPostCondition> postCons) {
	String retVal = null;
	for (CTPostCondition post : postCons) {
		retVal = post.getVarVal(strName).trim();
		if (retVal != null && retVal != "") {
			return strName + retVal.replace(",", " ").trim();
		}
	}
	return retVal;
}

private ArrayList<State> processAllStates(ArrayList<State> inputlst) {
	ArrayList<State> retList = new ArrayList<State>();
	retList.addAll(inputlst);

	while (hasUnProcessedStates(retList)) {
		for (int index = 0; index < retList.size(); index++) {
			State stTemp = retList.get(index);
			if (!stTemp.isProcessed()) {
				ArrayList<State> stNextStatesList = getNextStates(stTemp,
						retList);
				for (State state : stNextStatesList) {
					if (!isStateInTheList(state, retList)) {
						retList.add(state);

					}
				}
				stTemp.setProcessed();
			}
		}
	}
	return retList;
}

private boolean hasUnProcessedStates(ArrayList<State> lst) {
	for (State st : lst) {
		if (!st.isProcessed()) {
			return true;
		}
	}
	return false;
}

private boolean isStateInTheList(State state, ArrayList<State> stateList) {
	for (State st : stateList) {
		if (st.isSameAs(state)) {
			return true;
		}
	}
	return false;
}

/**
 * @param lstStates
 *            the lstStates to set
 */
public void setStatesList(ArrayList<State> lstStates) {
	this.lstStates = lstStates;
}

/**
 * @return the lstStates
 */
public ArrayList<State> getStatesList() {
	return lstStates;
}

@Override
public String toString() {
	StringBuilder retStr = new StringBuilder();
	retStr.append("[");
	for (State s : this.lstStates) {
		retStr.append(s.toString());
		retStr.append(",");
	}
	retStr.append("]");
	return retStr.toString();
}

private ArrayList<ClassVariable> removeExtraEqual(
		ArrayList<ClassVariable> lst) {
	for (ClassVariable var : lst) {
		if (var.getType() == CTVariableType.INTEGER
				|| var.getType() == CTVariableType.REAL)// form all integer
														// and real
														// variables
		{
			ArrayList<InEqualitySimplified> temList = new ArrayList<InEqualitySimplified>();
			for (InEqualitySimplified inSmp : var.getValues()) {
				if (inSmp.getVariable().getValue().contains("EQUAL"))// if
																		// value
																		// contains
																		// equal
																		// then
																		// remove
																		// it
				{
					inSmp.getVariable().setValue(
							RemoveEqualfromVal(inSmp.getVariable()
									.getValue()));
				}
				if (!InqContainedInList(inSmp, temList)) {
					temList.add(inSmp);
				}
			}
			var.setVarValues(temList);

		}
	}
	ArrayList<ClassVariable> retList = lst;
	return retList;
}

private ArrayList<ClassVariable> validateVariables(
		ArrayList<ClassVariable> lst) {
	for (ClassVariable avar : lst)// This loop checks for the validity of
									// the variable values (at the moment
									// just verifies Invariants over
									// variable values)
	{
		ArrayList<InEqualitySimplified> lstInverients = InequalitySolver
				.getInvarients(avar, getContext());

		ArrayList<InEqualitySimplified> temInqs = new ArrayList<InEqualitySimplified>();
		temInqs.addAll(avar.getValues());
		for (InEqualitySimplified temInq : temInqs) {
			if (!InequalitySolver.isValid(temInq, lstInverients)) {
				avar.getValues().remove(temInq);
			}
		}
	}
	ArrayList<ClassVariable> retLst = lst;
	return retLst;
}

private ArrayList<ClassVariable> convertAllToAtomic(
		ArrayList<ClassVariable> lst) {
	for (ClassVariable vars : lst)// This for loop splits the composite
									// inequalities (having >= and <=) to
									// atomic
	{
		ArrayList<InEqualitySimplified> temp = convertToAtomic(vars
				.getValues());
		vars.setVarValues(temp);
	}
	ArrayList<ClassVariable> retList = lst;
	return retList;
}

private State findInList(ArrayList<State> lst,
		ArrayList<InEqualitySimplified> stateVals) {
	for (State st : lst) {
		if (isStateSame(st, stateVals)) {
			return st;
		}
	}
	return null;
}

private boolean isStateSame(State s, ArrayList<InEqualitySimplified> list) {
	boolean retVal = true;
	for (InEqualitySimplified inq : s.getCurrentValues()) {
		for (InEqualitySimplified inqFromList : list) {
			if (inq.getVariableName().trim()
					.equals(inqFromList.getVariableName().trim()))// have
																	// same
																	// variable
																	// names
			{
				if (!InequationSolver.isTheSame(inq, inqFromList))// are
																	// they
																	// the
																	// same
				{
					// If not the same then return false here
					retVal = false;
				}
			}
		}
	}
	return retVal;
}

private void adjustStatePositions() {
	int count = 1;
	int xAxis = 100;
	int yAxis = 60;
	int xDistance = 200;
	int yDistance = 0;
	for (State st : lstStates) {
		st.setX((count) * xDistance + xAxis);
		st.setY(yAxis + count++ * yDistance);
	}

}

public void generateTestSequences() {
	testSequences = new ArrayList<TestSequence>();
	ArrayList<TestSequence> copyList = new ArrayList<TestSequence>();
	if (lstStates.size() > 0) {
		int i = 0;
		while (i++ < 25) {
			if (testSequences.size() < 1)// If this is the first test
											// sequence
			{
				State firstState = lstStates.get(0);
				for (Transition trans : firstState.getArNextStates()) {
					TestSequence tempSequence = new TestSequence();
					tempSequence.addToSequence(new TestTransition(trans
							.getMethod(), trans.getFromState(), trans
							.getToState()));
					testSequences.add(tempSequence);
				}

			} else // we have already got our first test sequence
			{

				copyList.addAll(testSequences);
				for (TestSequence seq : copyList)// for each test sequence
													// in the test sequences
				{
					TestTransition theCall = seq.getSequence().get(
							seq.getSequence().size() - 1);// get the last
															// method call
					Transition nextTrans = theCall.getToState()
							.getNextState(0);
					seq.getSequence().add(
							new TestTransition(nextTrans.getMethod(),
									nextTrans.getFromState(), nextTrans
											.getToState()));// just add this
															// call the
															// sequence

					if (theCall.getToState().getTransitionCount() > 1) {
						for (int index = 1; index < theCall.getToState()
								.getTransitionCount() - 1; index++) {
							ArrayList<TestTransition> newSeq = new ArrayList<TestTransition>();
							newSeq.addAll(seq.getSequence());

							nextTrans = theCall.getToState().getNextState(
									index);
							newSeq.add(new TestTransition(nextTrans
									.getMethod(), nextTrans.getFromState(),
									nextTrans.getToState()));
							testSequences.add(new TestSequence(newSeq));
						}
					}
				}
				copyList.clear();
			}

		}
	}
}

public ArrayList<TestSequence> getNTestSequences(int noSequences)
		throws Exception {
	if (noSequences > testSequences.size()) {
		throw new Exception(
				"Desired number of test sequnces is higher than the available sequences. available No : "
						+ testSequences.size()
						+ " desired No :"
						+ noSequences);
	}
	return new ArrayList<TestSequence>(
			testSequences.subList(0, noSequences));
}

public ArrayList<TestSequence> getGeneratedSequences() {
	return testSequences;
}
@Override
public void draw(Graphics g) {
	// TODO Auto-generated method stub
//	adjustStateCoordinates(20, 20, true);
	adjustStateCoordinates(new Point(100,100), new Dimension(150,90), g);
	for (State state : this.lstStates) {
		state.draw(g);
	}
}
@Override
public void Move(int newX, int newY, Graphics g) {
	// TODO Auto-generated method stub
	//Not Implemented! 
}
public static AbstractFSM fromFile(String fileNamewithPath) {
		FileInputStream fin = null;
		ObjectInputStream objIn = null;
		try {
			fin = new FileInputStream(fileNamewithPath);
			objIn = new ObjectInputStream(fin);
			return (AbstractFSM) objIn.readObject();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex);
		}
		return null;
	}
public static boolean toFile(AbstractFSM fsm, String fnameWithPath) {
		FileOutputStream fout = null;
		ObjectOutputStream oout = null;
		try {
			fout = new FileOutputStream(fnameWithPath);
			oout = new ObjectOutputStream(fout);
			oout.writeObject(fsm);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.toString());
			return false;
		}
		return true;
	}

public CTContext getContext() {
		return ctx;
	}

private void setCtx(CTContext ctx) {
		this.ctx = ctx;
	}

public ArrayList<String> getClassVariableNames() {
		ArrayList<String> retList = new ArrayList<String>();
		for (ClassVariable var : this.lstStateVars) {
			retList.add(var.getName());
		}
		return retList;
}
public void adjustStateCoordinates(int x, int y,boolean evenODD) {
		int i = 1;
		for (State st : this.lstStates) {
			if (evenODD) {
				if (i++ % 2 == 0) {
					st.setCenter(new Point(x, y));
				}

			} else {
				st.setCenter(new Point(x, y));
			}

		}
	}
public void adjustStateCoordinates(Point TopLeft, Dimension stateDistance, Graphics g)
{
	State firstState = lstStates.get(0);
	firstState.setCenter(new Point((int)(TopLeft.getX()+firstState.getRadius()), (int)(TopLeft.getY()+firstState.getRadius())));
	Point current = TopLeft;
	State stTemp;
	for(int index =1; index<lstStates.size();index++)
	{
		if(current.getX()>=(2 * TopLeft.getX()))
		{
			current.setX(TopLeft.getX());
			current.setY(current.getY()+ stateDistance.height);			
		}
		
		current.setX(current.getX()+stateDistance.width);

		stTemp = lstStates.get(index);
		stTemp.setCenter(current);
		
	}
}
public void setLocationOnScreen(int x, int y, boolean xConstant) {
		int fixIncrement = 20;
		int intStateCount = 1;
		for (State st : this.lstStates) {
			if (!xConstant) {
				st.setCenter(new Point(x + fixIncrement * (intStateCount), y
						+ fixIncrement * (intStateCount)));
			} else {
				st.setCenter(new Point(x, y + fixIncrement * (intStateCount)));
			}
			intStateCount++;
		}
	}
}