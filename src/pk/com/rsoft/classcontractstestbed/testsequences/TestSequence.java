/**
 * 
 */
package pk.com.rsoft.classcontractstestbed.testsequences;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author HAMMAD
 *
 */
public class TestSequence implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<TestTransition> sequence;
	public TestSequence()
	{
		this(new ArrayList<TestTransition>());
	}
	public TestSequence(ArrayList<TestTransition> theSequence)
	{
		this.sequence = theSequence;
	}
	
	public ArrayList<TestTransition> getSequence()
	{
		return sequence;
	}
	
	public void addToSequence(TestTransition chunck)
	{
		this.sequence.add(chunck);
	}
	
	public void addToSequence(ArrayList<TestTransition> theBuilder)
	{
		this.sequence.addAll((theBuilder));
	}
	@Override
	public String toString()
	{
		StringBuilder retStr = new StringBuilder();
		for(TestTransition call: this.sequence)
		{
			retStr.append(call.toString());
			retStr.append(",");
		}
		return retStr.toString();
	}
	public ArrayList<TestTransition> getMethodCalls()
	{
		return this.sequence;
	}
}
