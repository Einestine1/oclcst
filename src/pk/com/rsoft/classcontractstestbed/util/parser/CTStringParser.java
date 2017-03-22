/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pk.com.rsoft.classcontractstestbed.util.parser;

/**
 *
 * @author Hammad
 */
public class CTStringParser {
    private CTStringParser(){};
    public static String extractNameFromQuotes(String strVal)
    {
        return strVal.subSequence(strVal.indexOf("\"") + 1, strVal.length() - 2).toString();
    }
    public static String removeOperator(String strVal)
    {
    	return strVal.substring(strVal.lastIndexOf('=')+1).trim();
    	//return strVal;
    }
    public static String getRightOf(String strVal, String strPattern)
    {
//    	System.out.println(strVal.substring(strVal.indexOf(strPattern)+1).trim());
    	return strVal.substring(strVal.indexOf(strPattern)+1).trim();
    }
    public static String getLeftOf(String strVal, String strPattern)
    {
    	return strVal.substring(0,strVal.indexOf(strPattern));
    }
}
