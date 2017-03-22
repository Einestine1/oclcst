/**
 * 
 */
package pk.com.rsoft.ga.multiobjective.jmetal;

import jmetal.core.Problem;
import jmetal.core.Solution;
import jmetal.core.SolutionType;
import jmetal.core.Variable;
import jmetal.util.JMException;

/**
 * @author Rehan
 *
 */
public class TestSequenceProblem extends Problem {

	/**
	 * 
	 */
	public TestSequenceProblem() {
//		super(new TestSequenceSolutionType());
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param solutionType
	 */
	public TestSequenceProblem(SolutionType solutionType) {
		super(solutionType);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see jmetal.core.Problem#evaluate(jmetal.core.Solution)
	 */
	@Override
	public void evaluate(Solution solution) throws JMException {
		// TODO Auto-generated method stub
		Variable [] vars = solution.getDecisionVariables();
	}

}
