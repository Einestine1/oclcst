/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pk.com.rsoft.classcontractstestbed.util.graph;

import java.util.ArrayList;
import pk.com.rsoft.classcontractstestbed.classcontract.CTClassVariable;

/**
 *
 * @author Hammad
 */
class CTAbstractState {
    @SuppressWarnings("rawtypes")
	private ArrayList<CTClassVariable> stateVariables;
    @SuppressWarnings("rawtypes")
	public CTAbstractState()
    {
        setStateVariables(new ArrayList<CTClassVariable>());
    }
	/**
	 * @return the stateVariables
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList<CTClassVariable> getStateVariables() {
		return stateVariables;
	}
	/**
	 * @param stateVariables the stateVariables to set
	 */
	@SuppressWarnings("rawtypes")
	public void setStateVariables(ArrayList<CTClassVariable> stateVariables) {
		this.stateVariables = stateVariables;
	}

}
