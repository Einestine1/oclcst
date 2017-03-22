/**
 * 
 */
package pk.com.rsoft.classcontractstestbed.util.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.JOptionPane;

import pk.com.rsoft.classcontractstestbed.util.graph.AbstractFSM;

/**
 * @author Hammad
 *
 */
public final class ObjectIO {

	/**
	 * 
	 */
	private ObjectIO() {
		// TODO Auto-generated constructor stub
	}
	public static Object fromFile(String fnameWithPath)
	{
	    FileInputStream fin = null;
	    ObjectInputStream objIn = null;
	    try{
	        fin = new FileInputStream(fnameWithPath);
	        objIn = new ObjectInputStream(fin);
	       
	        return  objIn.readObject();
	    }
	    catch(Exception ex)
	    {
	        JOptionPane.showMessageDialog(null,ex);
		    return null;	
	    }
	    finally
	    {
	    	try {
				objIn.close();
		    	fin.close();
	    	} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    }
	}

	public static <T> boolean toFile(T obj,String fnameWithPath) throws InvalidObjectException
	{
		if(!(obj instanceof Serializable))
		{
			throw new InvalidObjectException(obj.getClass()+" is not serializable.");
		}
	    FileOutputStream fout = null;
	    ObjectOutputStream oout = null;
	    try{
	        fout = new FileOutputStream(fnameWithPath);
	        oout = new ObjectOutputStream(fout);
	        oout.writeObject(obj);
	    }
	    catch(Exception ex)
	    {
	        JOptionPane.showMessageDialog(null,ex.toString());
	        return false;
	    }
	    return true;
	}	
	
	
}
