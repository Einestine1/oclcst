package pk.com.rsoft.ga.multiobjective;

import java.util.*;

import org.jgap.*;
import pk.com.rsoft.classcontractstestbed.testsequences.TestTransition;
import pk.com.rsoft.classcontractstestbed.util.graph.State;
import pk.com.rsoft.testsequenceoptimization.ga.*;

/**
 * Fitness function for the test sequence problem.
 * 
 * @author Rehan Farooq
 */
public class TestSequenceMOGAFitnessFunction extends BulkFitnessFunction {

	private static final long serialVersionUID = 1L;

	public void evaluate(Population a_subject) {
		Iterator<IChromosome> it = a_subject.getChromosomes().iterator();
		while (it.hasNext()) {
			IChromosome a_chrom1 = it.next();
			// Evaluate values to fill vector of multiobjectives with.
// -------------------------------------------------------
List<Double> l = new Vector<Double>();
l = CalculateFitness(a_chrom1);
((Chromosome) a_chrom1).setMultiObjectives(l);// Set fitness value
											// for the Chrosome
	}
}

public static Vector<Double> getVector(IChromosome a_chrom) {
	List<?> moList = ((Chromosome) a_chrom).getMultiObjectives();
	Vector<Double> retVector = new Vector<Double>();
	retVector.add((Double) moList.get(0));
	retVector.add((Double) moList.get(1));
	return retVector;
}

@Override
public Object clone() {
	return new TestSequenceMOGAFitnessFunction();
}

public Vector<Double> CalculateFitness(IChromosome a_Chromosome) {
	Vector<Double> v = new Vector<Double>();
	v.add(getFitnessByTransitionOrder(a_Chromosome));// Fitness by validity
											// is in
// location indexed 0
v.add(calculateFitnessByCoverage(a_Chromosome));// Fitness by Coverage
										// is in location
										// indexed 1
	return v;
}

private Double calculateFitnessByCoverage(IChromosome a_Chromosome) {
	double retVal = 0;
	State from;
	State to;

	Gene[] genes = a_Chromosome.getGenes();
	if (((TestTransition) genes[1].getAllele()).getFromState()
			.isStartState()) {
		retVal += 20;
	}
	int occuranceCount = 0;
	for (State state : AFSMHolder.getFSM().getStatesList()) {
		occuranceCount = 0;

		for (int index = 0; index < genes.length; index++) {
			from = ((TestTransition) genes[index].getAllele())
					.getFromState();
			to = ((TestTransition) genes[index].getAllele()).getToState();
			if (state.isSameAs(from)) {
				occuranceCount++;
			}
			if (state.isSameAs(to)) {
				occuranceCount++;
			}
		}
		if (occuranceCount == 1 || occuranceCount == 2) {
			retVal += 3 * occuranceCount;
		} else if (occuranceCount == 0 || occuranceCount > 2) {
			retVal -= 10;
		}

	}

	return new Double(retVal);
}

private Double getFitnessByTransitionOrder(IChromosome a_Chromosom) {

	double retVal = 0;
	Gene[] genes = a_Chromosom.getGenes();

	if (((TestTransition) genes[0].getAllele()).getFromState()
			.isStartState()) {
		retVal += 20;
	}
	State next = ((TestTransition) genes[0].getAllele()).getToState();
	for (int index = 1; index < genes.length; index++) {
		// next.isSameAs(((TestTransition)genes[index].getAllele()).getFromState())
			if (next.isReachable(((TestTransition) genes[index].getAllele())
					.getToState())) {
				retVal += 5;
			} else {
				retVal -= 5;
			}
			next = ((TestTransition) genes[index].getAllele()).getToState();
		}
		return new Double(retVal);

	}
}
