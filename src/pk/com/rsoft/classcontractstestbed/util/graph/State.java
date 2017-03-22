/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pk.com.rsoft.classcontractstestbed.util.graph;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import pk.com.rsoft.classcontractstestbed.util.graphics.Circle;
import pk.com.rsoft.classcontractstestbed.util.inequality.InEqualitySimplified;
import pk.com.rsoft.classcontractstestbed.util.inequality.InequationSolver;
import pk.com.rsoft.classcontractstestbed.util.parser.NumberPorcessor;
/**
 *
 * @author Hammad
 */
public class State extends Circle implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String strName;
    private ArrayList<Transition> arTransitions;
    private boolean isStartState=false;
    private boolean isProcessed =false;
    private static int radious=50;
    
    private static final int defaultX = 50;
    private static Color color = Color.RED;
    private ArrayList<InEqualitySimplified> lstCurrentValues;
    
    private static int count =1;
    private int stateNumber;
    public State(String strName, boolean isStartState)
    {    	
    	super(defaultX,defaultX,radious,color);
    	this.strName = strName;
        this.isStartState= isStartState;
        this.arTransitions = new ArrayList<Transition>();
        setCurrentValues(new ArrayList<InEqualitySimplified>());
        setStateNumber(count++);
    }
    public State(boolean isStartState)
    {
    	this("S",isStartState);
    }
    /**
     * @return the strName
     */
    public String getStrName() {
        return strName;
    }

    /**
     * @param strName the strName to set
     */
    public void setStrName(String strName) {
        this.strName = strName;
    }

    /**
     * @return the arTransitions
     */
    public ArrayList<Transition> getArNextStates() {
        return arTransitions;
    }

    /**
     * @param arTransitions the arTransitions to set
     */
    public void setArNextStates(ArrayList<Transition> arNextStates) {
        this.arTransitions = arNextStates;
    }

    /**
     * @return the isStartState
     */
    public boolean isStartState() {
        return isStartState;
    }

    /**
     * @param isStartState the isStartState to set
     */
    public void setIsStartState(boolean isStartState) {
        this.isStartState = isStartState;
    }
    public void addTransition(Transition next)
    {
    	if(!alreadyExists(next))
    	{
    		this.arTransitions.add(next);   
    	}
    }
    public Transition getNextState(int index)
    {
        return arTransitions.get(index);
    }
    public int getTransitionCount()
    {
        return arTransitions.size();
    }

    @Override
    public boolean equals(Object o)
    {
        boolean retVal = true;
        if(o instanceof State)
        {
            for(InEqualitySimplified v:  lstCurrentValues)
            {
                if (!((State) o).getCurrentValues().contains(v))
                {
                    retVal = false;
                }
            }
        }
        return retVal;
    }
public boolean isSameAs(State s)
{
	boolean retVal = true;
		for(InEqualitySimplified val1: this.getCurrentValues())
		{
			for(InEqualitySimplified val2: s.getCurrentValues())
			{
				if(val1.getVariableName().trim().equals(val2.getVariableName().trim()) &&( !InequationSolver.isTheSame(val1, val2)|| s.getStateNumber()!=this.getStateNumber()) )
				{
					retVal= false;
				}


			}

		}
		return retVal;
}
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (this.strName != null ? this.strName.hashCode() : 0);
        hash = 71 * hash + (this.arTransitions != null ? this.arTransitions.hashCode() : 0);
        hash = 71 * hash + (this.isStartState ? 1 : 0);
        hash = 71 * hash + (this.lstCurrentValues != null ? this.lstCurrentValues.hashCode() : 0);
        return hash;
    }

    public void addStateVariable(InEqualitySimplified stateVar)
    {
        this.lstCurrentValues.add(stateVar);
    }
    public InEqualitySimplified getStateVariableAt(int index)
    {
        return lstCurrentValues.get(index);
    }
    /**
	 * @param lstCurrentValues the lstCurrentValues to set
	 */
	public void setCurrentValues(ArrayList<InEqualitySimplified> lstCurrentValues) {
		this.lstCurrentValues = lstCurrentValues;
	}
	/**
	 * @return the lstCurrentValues
	 */
	public ArrayList<InEqualitySimplified> getCurrentValues() {
		return lstCurrentValues;
	}
	public InEqualitySimplified removeStateVariable(int index)
    {
        return lstCurrentValues.remove(index);
    }
    @Override
    public String toString()
    {
        String retVal = strName+"("+ getStateNumber() + ") : [";
        for(InEqualitySimplified var : this.lstCurrentValues)
        {
        	   if(var!=null)
        	   {
        		   retVal += "(" + var.toString() +")" + "";
        	   }
        }
        retVal+="**";
        for(Transition trans : this.arTransitions)
        {
     	   retVal += trans.toString() +"**";
        }

        retVal+="]";
        return retVal;
    }
public InEqualitySimplified getVarValue(String strNam)
{
	for(InEqualitySimplified inq: this.lstCurrentValues)
	{
		if(inq.getVariableName().trim().equals(strNam))
		{
			return inq;
		}
	}
	return null;
}
public void setProcessed()
{
	this.isProcessed=true;
}
public void setUnprocessed()
{
	this.isProcessed=false;
}

public boolean isProcessed()
{
	return isProcessed;
}

private boolean alreadyExists(Transition trans)
{
	for(Transition t: this.arTransitions)
	{
		if(t.getFromState().isSameAs(trans.getFromState())&& t.getToState().isSameAs(trans.getToState()))
		{
			if(t.getMethod().getCTOperationName().trim().equals(trans.getMethod().getCTOperationName().trim()))
				return true;
		}
	}
	return false;
}
public boolean hasTransiton(Transition t)
{
	for(Transition trans: this.arTransitions)
	{
		if(trans.isSameAs(t))
		{
			return true;
		}
	}
	return false;
}
@Override
public void draw(Graphics g)
{
	super.draw(g);
	int startX = (int)(this.getX()- getRadius());//(int) (this.getX() + this.getRadius());
	int startY = this.getY()+100;//(int) (this.getY() + this.getRadius());
	int theFactor = 10;
	
//	for(InEqualitySimplified inq: this.lstCurrentValues)
//	{
//		g.drawString(inq.toString(), startX, startY);
//		startY += theFactor;
//	}
	
	for(Transition trans: this.arTransitions)
	{
//		trans.setFromState(this);
		trans.draw(g);
	}
	if(isStartState())
	{
		g.drawString("S", getX(), getY());
	}
}
public static void resetStaticState()
{
    radious=50;
    color = Color.RED;	
}
@Override 
public void setX(int x)
{
	super.setX(x);
	if(arTransitions==null)
	{
		return ;
	}

	for(Transition trans: this.arTransitions)
	{
		trans.getStartPoint().setX(getX());
	}
}
public void setY(int y)
{
	super.setY(y);
	if(arTransitions==null)
	{
		return ;
	}
	for(Transition trans: this.arTransitions)
	{
		trans.getStartPoint().setY(getY());
	}
	
}
/**
 * @param count the count to set
 */
public static void setCount(int count) {
	State.count = count;
}
/**
 * @return the count
 */
public static int getCount() {
	return count;
}
/**
 * @param stateNumber the stateNumber to set
 */
public void setStateNumber(int stateNumber) {
	this.stateNumber = stateNumber;
}
/**
 * @return the stateNumber
 */
public int getStateNumber() {
	return stateNumber;
}

public String toBooleanString()
{
	String str = Integer.toBinaryString(getStateNumber());
	return str;
}

public boolean isReachable(State st)
{
	for(Transition trans: this.arTransitions)
	{
		if(trans.getToState().getStateNumber()==st.getStateNumber())
		{
			return true;
		}
	}
	return false;
}
public Transition getRandomTransition()
{
	return this.arTransitions.get(NumberPorcessor.getRandomInt(0, arTransitions.size()));
}
/**
 * @return the radious
 */
public static int getRadious() {
	return radious;
}
/**
 * @param radious the radious to set
 */
public static void setRadious(int radious) {
	State.radious = radious;
}
}