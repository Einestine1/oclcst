/**
 * 
 */
package pk.com.rsoft.testsequenceoptimization.ga;

import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;

import org.jgap.RandomGenerator;

import pk.com.rsoft.classcontractstestbed.classcontract.CTContext;
import pk.com.rsoft.classcontractstestbed.testsequences.TestTransition;
import pk.com.rsoft.classcontractstestbed.util.graph.AbstractFSM;
import pk.com.rsoft.classcontractstestbed.util.graph.State;
import pk.com.rsoft.classcontractstestbed.util.graph.Transition;
import pk.com.rsoft.classcontractstestbed.util.parser.NumberPorcessor;

/**
 * @author HAMMAD
 *
 */
public final class AFSMHolder {
	public static AbstractFSM fsm;
	
	public static AbstractFSM getFSM()
	{
		if(fsm==null)
		{
			throw new NullPointerException("AFSMHolder: the staic FSM is not initilized, please use AFSMHolder.setFSM() first.");
		}
		return fsm;
	}
	public static void setFSM(AbstractFSM aFsm)
	{
		fsm = aFsm;
	}
	public static TestTransition getRandomMethod()
	{
		AbstractFSM anFSM = getFSM();
		int randomState = NumberPorcessor.getRandomInt(0, anFSM.getStatesList().size());
		State stRandomState = anFSM.getStatesList().get(randomState);	
		return createMethodCall(stRandomState.getRandomTransition());
	}
	public static State getRandomState()
	{
		AbstractFSM anFSM = getFSM();
		int randomState = NumberPorcessor.getRandomInt(0, anFSM.getStatesList().size());
		State stRandomState = anFSM.getStatesList().get(randomState);	
		return stRandomState;
		
	}
	public static TestTransition getRandomMethod(RandomGenerator a_numberGenerator)
	{
		AbstractFSM anFSM = getFSM();
		int randomState = Math.abs(a_numberGenerator.nextInt() % anFSM.getStatesList().size());
		State stRandomState = anFSM.getStatesList().get(randomState);	
		return createMethodCall(stRandomState.getRandomTransition());
	}
	public static State getRandomState(RandomGenerator a_numberGenerator)
	{
		AbstractFSM anFSM = getFSM();
		int randomState = Math.abs(a_numberGenerator.nextInt() % anFSM.getStatesList().size());
		State stRandomState = anFSM.getStatesList().get(randomState);	
		return stRandomState;
	}
	
	private static TestTransition createMethodCall(Transition trans)
	{
		return new TestTransition(trans.getMethod(), trans.getFromState(),trans.getToState());
	}
	public static CTContext getOCLContext()
	{
		return fsm.getContext();
	}

}
