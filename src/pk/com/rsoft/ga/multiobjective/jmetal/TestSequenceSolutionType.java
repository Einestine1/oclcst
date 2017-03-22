/**
 * 
 */
package pk.com.rsoft.ga.multiobjective.jmetal;

import pk.com.rsoft.classcontractstestbed.util.graph.AbstractFSM;
import pk.com.rsoft.testsequenceoptimization.ga.AFSMHolder;
import jmetal.core.Problem;
import jmetal.core.SolutionType;
import jmetal.core.Variable;

/**
 * @author Rehan
 *
 */
public class TestSequenceSolutionType extends SolutionType {
	int sequenceLength;
	/**
	 * @param problem
	 */
	public TestSequenceSolutionType(Problem problem, int seqLength) {
		super(problem);
		sequenceLength = seqLength;
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see jmetal.core.SolutionType#createVariables()
	 */
	@Override
	public Variable[] createVariables() throws ClassNotFoundException {
		// TODO Auto-generated method stub
		Variable [] variables = new Variable[sequenceLength];
		for(int index=0;index<variables.length;index++)
		{
//			variables[index] = AFSMHolder.getRandomMethod();
		}
		return variables;
	}

}
