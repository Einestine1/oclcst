/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pk.com.rsoft.testsequenceoptimization.ga;

import java.io.Serializable;
import java.util.ArrayList;

import org.jgap.BaseGene;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import org.jgap.RandomGenerator;
import org.jgap.UnsupportedRepresentationException;

import pk.com.rsoft.classcontractstestbed.testsequences.TestTransition;
import pk.com.rsoft.classcontractstestbed.testsequences.TestSequence;
import pk.com.rsoft.classcontractstestbed.util.graph.AbstractFSM;


/**
 *
 * @author Hammad
 */
public class TestSequenceGene extends BaseGene implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TestTransition theCall;

    public TestSequenceGene(TestTransition theCall, Configuration config) throws InvalidConfigurationException
    {
        super(config);
        this.theCall = theCall;
    }
    @Override
    protected Object getInternalValue() {
        return theCall;
    }

    @Override
    protected Gene newGeneInternal() {
        try {
            return new TestSequenceGene(AFSMHolder.getRandomMethod(), getConfiguration());
        } catch (InvalidConfigurationException ex) {
            throw new IllegalStateException(ex.getMessage());
        }
    }

    public void setAllele(Object a_newValue) {
        this.theCall = (TestTransition) a_newValue;
    }
    @Override
    public Object getAllele( )
    {
        return this.theCall;
    }

    public String getPersistentRepresentation() throws UnsupportedOperationException {
        try {
            return theCall.toBooleanString();
        } catch (InvalidConfigurationException ex) {
            throw new IllegalStateException(ex.getMessage());
        }
    }

    public void setValueFromPersistentRepresentation(String a_representation) throws UnsupportedOperationException, UnsupportedRepresentationException {
        String [] parts = a_representation.split(",");
        if(parts.length<1 || parts.length>4)
        {
            throw new IllegalStateException("Invalid persistant representation :" + a_representation);
        }
//        this.theCall = new TestTransition(Double.parseDouble(xPart[1]), Double.parseDouble(yPart[1]), Double.parseDouble(zPart[1]), Boolean.valueOf(parts[3]), s);
    }

    public void setToRandomValue(RandomGenerator a_numberGenerator) {
//        Size s = theCall.getProblemSize();
    	  theCall = AFSMHolder.getRandomMethod(a_numberGenerator);
//        theCall = new TestTransition(a_numberGenerator.nextInt((int)s.getWidth()), a_numberGenerator.nextInt((int)s.getHeight()), a_numberGenerator.nextInt((int)s.getLength()), theCall.getHECNStatus(), s);
    }

    public void applyMutation(int index, double a_percentage) {
        setToRandomValue(getConfiguration().getRandomGenerator());
    }

    public int compareTo(Object o) {
       if(o instanceof TestSequenceGene)
       {
            return theCall.compareTo(((TestSequenceGene)o).theCall);
       }
       else
       {
           return -1;
       }
    }
    @Override
    public String toString()
    {
        try {
            return theCall.toBooleanString();
        } catch (InvalidConfigurationException ex) {
           throw new IllegalStateException(ex.getMessage());        }
    }

	public static ArrayList<TestSequenceGene> toTestSequenceGenes(TestSequence sequence,Configuration config) throws InvalidConfigurationException
	{
		ArrayList<TestSequenceGene> retList  = new ArrayList<TestSequenceGene>();
		for(TestTransition call: sequence.getMethodCalls())
		{
			retList.add(new TestSequenceGene(call, config));
		}
		return retList;
	}
}
