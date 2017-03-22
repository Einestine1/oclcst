/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pk.com.rsoft.classcontractstestbed.classcontract;

/**
 *
 * @author Hammad
 */
public enum CTVariableType {
   INTEGER,BOOLEAN,REAL,STRING,OTHER;
public static String getDafaultValue(CTVariableType type)
{
	switch(type)
	{
	case BOOLEAN:
		return String.valueOf(false);
	case INTEGER:
		return String.valueOf(0);
	case REAL:
		return String.valueOf(0.0);
	case OTHER:
	case STRING:
	default:
		return String.valueOf(null);		
	}
}
}
