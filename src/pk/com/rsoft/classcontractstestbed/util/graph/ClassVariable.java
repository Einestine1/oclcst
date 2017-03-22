/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pk.com.rsoft.classcontractstestbed.util.graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

import pk.com.rsoft.classcontractstestbed.classcontract.CTContext;
import pk.com.rsoft.classcontractstestbed.classcontract.CTVariableType;
import pk.com.rsoft.classcontractstestbed.util.inequality.InEqualitySimplified;
import pk.com.rsoft.classcontractstestbed.util.inequality.InequalitySolver;
import pk.com.rsoft.classcontractstestbed.util.inequality.VariableType;

/**
 *
 * @author Hammad
 */
public class ClassVariable implements Serializable{
    private String name;
    private String value;
    private ArrayList<InEqualitySimplified> varValues;
    private CTVariableType type;
    public ClassVariable(String name, CTVariableType type)
    {
    	this.name = name;
    	this.value = null;
    	this.type = type;
    	this.varValues = new ArrayList<InEqualitySimplified>();
    	
    }
    public ClassVariable(String name,String val)
    {
        this.name = name;
        this.value = val;
        type = CTVariableType.OTHER;
        this.varValues = new ArrayList<InEqualitySimplified>();
        this.varValues.add(new InEqualitySimplified(name +" = " + val, VariableType.OBJECT));

    }
    public ClassVariable(String name,String val, CTVariableType varType)
    {
        this.name = name;
        this.value = val;
        type = varType;
        this.varValues = new ArrayList<InEqualitySimplified>();
//        this.varValues.add(new InEqualitySimplified(name +" = " + val, VariableType.OBJECT));
//        System.out.println("Name is " + name);
        this.varValues.add(new InEqualitySimplified(name +" = " + val, InequalitySolver.getVariableType(varType)));

    }
    
    public ClassVariable(String name,String val, String varType)
    {
        this.name = name;
        this.value = val;
//            this.value = this.value.replace("Inv", "");
        setType(varType);
        this.varValues = new ArrayList<InEqualitySimplified>();
        
//        this.varValues.add(new InEqualitySimplified(name +" = " + val, VariableType.OBJECT ));
        this.varValues.add(new InEqualitySimplified(name +" = " + val,InequalitySolver.getVariableType(varType)));

    }
    
    

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
    @Override
    public String toString()
    {
        return ("Name: " +name + " , Type: "  +type+" , values: ("+this.varValues.toString()+")").replace("Inv", "");
    }

    /**
     * @return the type
     */
    public CTVariableType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(CTVariableType type) {
        this.type = type;
    }

    public void setType(String strType)
    {
                //this.strType = strType;
        if(strType.trim().equalsIgnoreCase("INTEGER"))
        {
            this.setType(CTVariableType.INTEGER);
        }
        else if(strType.trim().equalsIgnoreCase("REAL"))
        {
            this.setType(CTVariableType.REAL);
        }
        else if(strType.trim().equalsIgnoreCase("BOOLEAN"))
        {
            this.setType(CTVariableType.BOOLEAN);
        }
        else if(strType.trim().equalsIgnoreCase("STRING"))
        {
            this.setType(CTVariableType.STRING);
        }
        else
        {
            this.setType(CTVariableType.OTHER);
        }

    }

    /**
     * @return the varValues
     */
    public ArrayList<InEqualitySimplified> getValues() {
        return varValues;
    }

    /**
     * @param varValues the varValues to set
     */
    public void setVarValues(ArrayList<InEqualitySimplified> varValues) {
        this.varValues = varValues;
    }
    public void addValue(InEqualitySimplified obj)
    {
        this.varValues.add(obj);
    }
    public Object getValueAt(int index)
    {
        return this.varValues.get(index);
    }
    /**
     * @param ctx
     */
    public void simplify(CTContext ctx)
    {
    }
 public void removeDuplicates()
 {
	 HashSet<InEqualitySimplified> hs = new HashSet<InEqualitySimplified>();
	 hs.addAll(varValues);
	 varValues.clear();
	 varValues.addAll(hs);

 }
 
 public void removeVaValue(Object valToRemove)
 {
	 this.varValues.remove(valToRemove);
 }
}
