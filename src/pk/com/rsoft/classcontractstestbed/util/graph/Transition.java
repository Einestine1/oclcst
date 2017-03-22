/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pk.com.rsoft.classcontractstestbed.util.graph;

import java.awt.Graphics;
import java.io.Serializable;

import pk.com.rsoft.classcontractstestbed.classcontract.CTOperation;
import pk.com.rsoft.classcontractstestbed.util.graphics.Line;

/**
 *
 * @author Hammad
 */
public class Transition extends Line implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private State fromState;
    private State toState;
    private String strTitle;
    private CTOperation theMethod;
    
    private Transition(String strTitle, State from, State to)
    {   
    	super(from.getCenter(),to.getCenter());
    	this.setFromState(from);
    	this.setToState(to);
    	this.setStrTitle(strTitle);

    }
    public Transition(State from, State to,CTOperation op)
    {
    	this(op.getCTOperationName()+op.getMethodNumber()+op.getSignature(), from,to);
    	this.theMethod = op;
    }
	/**
	 * @param fromState the fromState to set
	 */
	public void setFromState(State fromState) {
		setStartPoint(fromState.getCenter());
		this.fromState = fromState;
	}
	/**
	 * @return the fromState
	 */
	public State getFromState() {
		return fromState;
	}
	/**
	 * @param toState the toState to set
	 */
	public void setToState(State toState) {
		setEndPoint(toState.getCenter());
		this.toState = toState;
	}
	/**
	 * @return the toState
	 */
	public State getToState() {
		return toState;
	}
	/**
	 * @param strTitle the strTitle to set
	 */
	public void setStrTitle(String strTitle) {
		this.strTitle = strTitle;
	}
	/**
	 * @return the strTitle
	 */
	public String getStrTitle() {
		return strTitle;
	}
	
	public String toString()
	{
		return this.theMethod.getCTOperationName();
	}
	@Override
	public void draw(Graphics g)
	{
//		System.out.println(fromState.getStrName()+"("+fromState.getStateNumber()+") --> " + toState.getStrName()+"("+toState.getStateNumber()+")");
		super.drawArrow(g, fromState.getCenter(),toState.getCenter());
		g.drawString(this.toString(),(int)getMidPoint().getX(), (int)getMidPoint().getY());
	}
	public boolean isSameAs(Transition t)
	{
		if(this.fromState.isSameAs(t.fromState) && this.toState.isSameAs(t.toState))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	/**
	 * @param theMethod the theMethod to set
	 */
	public void setMethod(CTOperation theMethod) {
		this.theMethod = theMethod;
	}
	/**
	 * @return the theMethod
	 */
	public CTOperation getMethod() {
		return theMethod;
	}

}
