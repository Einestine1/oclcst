/*
 * This file is part of JGAP.
 *
 * JGAP offers a dual license model containing the LGPL as well as the MPL.
 *
 * For licensing information please see the file license.txt included with JGAP
 * or have a look at the top of class org.jgap.Chromosome which representatively
 * includes the JGAP license policy applicable for any file delivered with JGAP.
 */
package pk.com.rsoft.ga.multiobjective;

import java.util.*;
import org.jgap.*;

import pk.com.rsoft.testsequenceoptimization.ga.TestSequenceGene;


/**
 * Fitness evaluator for multi objectives example. Determines which of two
 * vectors with multiobjective values is fitter. In our example, the fitter
 * vector is the one for which the sum of the values is smaller.
 *
 * @author Klaus Meffert
 * @since 2.6
 */
public class MOFitnessEvaluator
    implements FitnessEvaluator {
  /** String containing the CVS revision. Read out via reflection!*/
  private final static String CVS_REVISION = "$Revision: 1.4 $";

  /**
   * Not to be called in multi-objectives context! Instead, oOther method below
   * applies for multi-objectives.
   *
   * @param a_fitness_value1 ignored
   * @param a_fitness_value2 ignored
   * @return always a RuntimeException
   *
   * @author Klaus Meffert
   * @since 2.6
   */
  public boolean isFitter(final double a_fitness_value1,
                          final double a_fitness_value2) {
    throw new RuntimeException("Not supported for multi-objectives!");
  }

  public boolean isFitter(IChromosome a_chrom1, IChromosome a_chrom2) {
    Vector<?> v1 = TestSequenceMOGAFitnessFunction.getVector(a_chrom1);
    Vector<?> v2 = TestSequenceMOGAFitnessFunction.getVector(a_chrom2);

  Double fitnessByValidity1 = (Double) v1.get(0);
  Double fitnessByCoverage1 = (Double) v1.get(1);

  Double fitnessByValidity2 = (Double) v2.get(0);
  Double fitnessByCoverage2 = (Double) v2.get(1);

  if(fitnessByCoverage1>fitnessByCoverage2 && fitnessByValidity1>fitnessByValidity2)
  {
      return true;
  }
  else
  {
      return false;
  }
  }
}
