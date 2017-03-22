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

import javax.swing.JTextArea;

import org.jgap.*;
import org.jgap.impl.*;
import org.jgap.util.*;

import pk.com.rsoft.classcontractstestbed.testsequences.TestTransition;
import pk.com.rsoft.classcontractstestbed.testsequences.TestSequence;
import pk.com.rsoft.testsequenceoptimization.ga.*;

/**
 * The Test Sequence Multi-Objective Optimization Fitness functions 
 * Represented by the This class holds both the functions. 
 */
public class TestSequenceMultiObjectiveExecuter {
  /** String containing the CVS revision. Read out via reflection!*/
  private final static String CVS_REVISION = "$Revision: 2.0 $";
  private final int noGenes = 25;
  /**
   * The total number of times we'll let the population evolve.
   */
  private static final int MAX_ALLOWED_EVOLUTIONS = 200;
  private static final int noIndividuals = 100;
  private static JTextArea textArea;
  private static ArrayList<StringBuilder> builder;
  static Configuration config = new DefaultConfiguration();
  Configuration conf;
  public TestSequenceMultiObjectiveExecuter(JTextArea area)
    {
        super();
        textArea = area;
        setBuilder(new ArrayList<StringBuilder>());
        // Start with a DefaultConfiguration, which comes setup with the
        // most common settings.
        // -------------------------------------------------------------
        conf = config;    
    }
  /**
   * Executes the genetic algorithm.
   *
   * @throws Exception
   *
   * @author Klaus Meffert
   * @since 2.6
   */
  public void execute()
      throws Exception {
    System.out.println("Evolving the problem please wait!");
    System.out.println("Dependig upon the size of the chromosome and number of evolutions this process may take several minutes!");
    System.out.println("Using " + noGenes + " Genes and " + MAX_ALLOWED_EVOLUTIONS + " Generations");
    Configuration.reset();
    conf.getNaturalSelectors(false).clear();
    BestChromosomesSelector selector = new BestChromosomesSelector(conf, 1.0d);
    selector.setDoubletteChromosomesAllowed(true);
    conf.addNaturalSelector(selector, false);
    conf.setFitnessEvaluator(new MOFitnessEvaluator());
    conf.setPreservFittestIndividual(false);
    conf.setKeepPopulationSizeConstant(false);
    
    BulkFitnessFunction myFunc =
    new TestSequenceMOGAFitnessFunction();
    conf.setBulkFitnessFunction(myFunc);
    Gene[] sampleGenes = new Gene[noGenes];



    StockRandomGenerator rGen = new StockRandomGenerator();

    for(int i = 0; i< noGenes; i++)
    {
        sampleGenes[i] = new TestSequenceGene(null, conf );
        sampleGenes[i].setToRandomValue(rGen);
    }
    
    System.out.println("Sample Genes are :");
    for(int i = 0; i<noGenes;i++)
    {
    	System.out.print(sampleGenes[i].getAllele()+",");
    }
    IChromosome sampleChromosome = new Chromosome(conf, sampleGenes);
    conf.setSampleChromosome(sampleChromosome);
    conf.setPopulationSize(noIndividuals);
    // Create random initial population of Chromosomes.
    // ------------------------------------------------
    Genotype population = new Genotype(conf, this.getPopulation(conf)) ;//Genotype.randomInitialGenotype(conf);
    System.out.println(population.getPopulation().getChromosomes().size());
    for (int i = 0; i < MAX_ALLOWED_EVOLUTIONS; i++) {
      population.evolve();
    }

    // Now we have at least one solution.
    // ----------------------------------
    List<IChromosome> chroms = population.getPopulation().getChromosomes();
    MOFitnessComparator comp = new MOFitnessComparator();
    // Remove all duplicate solutions.
    // -------------------------------
    Collections.sort(chroms, comp);
    int index = 0;
    while (index < chroms.size() - 1) {
      Chromosome aSolution1 = (Chromosome) chroms.get(index);
      Vector<Double> v1 = TestSequenceMOGAFitnessFunction.getVector(aSolution1);
      Double d1 = v1.get(0);
      Double d11 = v1.get(1);
      Chromosome aSolution2 = (Chromosome) chroms.get(index + 1);
      Vector<Double> v2 = TestSequenceMOGAFitnessFunction.getVector(aSolution2);
      Double d2 = v2.get(0);
      Double d22 = v2.get(1);
      if (d1==d2 & d11==d22) {
        chroms.remove(index);
        System.out.println("Removed a Chromosome!");
      }
      else {
        index++;
      }
    }

    Chromosome bestSolutionSoFar = null;
    for (int k = 0; k < chroms.size(); k++) {
      bestSolutionSoFar = (Chromosome) chroms.get(k);

      String s = "";
      Vector<Double>
          v = TestSequenceMOGAFitnessFunction.getVector(bestSolutionSoFar);
      for (int j = 0; j < 2; j++) {
        Double d = v.get(j);
        String t = NumberKit.niceDecimalNumber(d, 9);
        t = StringKit.fill(t, 15, ' ');
        s += t;
try
  {
  Thread.sleep(1);

  }catch (InterruptedException ie)
  {
	  System.out.println(ie.getMessage());
  }
        Vector v2 = TestSequenceMOGAFitnessFunction.getVector(bestSolutionSoFar);
        System.out.println("Fitness val by Coverage :"  + ((Double) v2.get(0)) +" Fitness val by Order : "  + ((Double) v2.get(1)) );
        this.setChromosome(bestSolutionSoFar);
      }
      System.out.println("Chromosome length " + bestSolutionSoFar.size());
      PrintChromosome(bestSolutionSoFar);
    }
    System.out.println(bestSolutionSoFar);
    
    PrintChromosome(bestSolutionSoFar);
//    conf.reset();
  }

  private void setChromosome(Chromosome bestSolutionSoFar) {
	// TODO Auto-generated method stub
}
  /**
   * @author Klaus Meffert
   * @since 2.6
   */
  @SuppressWarnings("rawtypes")
  public class MOFitnessComparator
  implements java.util.Comparator {
	  public int compare(final Object a_chrom1, final Object a_chrom2) {
		  List<?> v1 = ( (Chromosome) a_chrom1).getMultiObjectives();
		  List<?> v2 = ( (Chromosome) a_chrom2).getMultiObjectives();
		  int size = v1.size();
		  if (size != v2.size()) {
			  throw new RuntimeException("Size of objectives inconsistent!");
		  }
		  double fitnessVals1[] = new double[size];
		  double fitnessVals2[] = new double[size];		  
		  for (int i = 0; i < size; i++) {
			  fitnessVals1[i] = ( (Double) v1.get(i)).doubleValue();
			  fitnessVals2[i] = ( (Double) v2.get(i)).doubleValue();			  
		  }
		  int fitCount1 =0;
		  int fitCount2 =0;
		  for(int i = 0;i<size;i++)
		  {
			  if(fitnessVals1[i]>fitnessVals2[i])
			  {
				  fitCount1+=fitnessVals1[i];
			  }
			  else if(fitnessVals1[i]<fitnessVals2[i])
			  {
				  fitCount2+=fitnessVals2[i];
			  }
		  }
		  
		  if (fitCount1<fitCount2) 
		  {
			  return -1;
		  }
		  else if (fitCount1 > fitCount2) 
		  {
			  return 1;
		  }
		  else 
		  {
			  return 0;
		  }    
	  }
  }
  public static void PrintChromosome(IChromosome a_Chromosome)
  {
	  Gene genes[] = a_Chromosome.getGenes();

	  StringBuilder temp = new StringBuilder();
	  for(int index = 0;index<genes.length;index++)
	  {
		  temp.append((TestTransition)genes[index].getAllele()+",");
	  }
	  temp.append("\n");
	  getBuilder().add(temp);
  }
  
  private Population getPopulation(Configuration config) throws Exception
  {
	  Population population = new Population(config);
	  ArrayList<TestSequence> testSeqs = AFSMHolder.getFSM().getNTestSequences(noIndividuals);
	  Gene genes[] = new Gene[noGenes];
	  for(TestSequence seq: testSeqs)
	  {
		  ArrayList<TestSequenceGene> genesArray = TestSequenceGene.toTestSequenceGenes(seq, config);
		  for(int i=0;i<noGenes;i++)
		  {
			  genes[i]=genesArray.get(i);
		  }
		  population.addChromosome(new Chromosome(config, genes));
	  }
	  return population;
  }
/**
 * @param builder the builder to set
 */
public static void setBuilder(ArrayList<StringBuilder> builder) {
	TestSequenceMultiObjectiveExecuter.builder = builder;
}
/**
 * @return the builder
 */
public static ArrayList<StringBuilder> getBuilder() {
	return builder;
}
}
