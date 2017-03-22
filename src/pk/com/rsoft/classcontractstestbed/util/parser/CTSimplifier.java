/**
 * 
 */
package pk.com.rsoft.classcontractstestbed.util.parser;

import java.util.ArrayList;
import pk.com.rsoft.classcontractstestbed.classcontract.CTContext;
import pk.com.rsoft.classcontractstestbed.classcontract.CTOperation;
import pk.com.rsoft.classcontractstestbed.util.graph.ClassVariable;

/**
 * @author Rehan
 *
 */
public class CTSimplifier {

	private CTContext theContext;
	/**
	 * 
	 */
	public CTSimplifier(CTContext ctx) {
		// TODO Auto-generated constructor stub
		if(ctx==null)
		{
			throw new NullPointerException("CTContext reff pased to CTSimplifier is not initialized !");
		}
		this.setContext(ctx);
	}
	
	public void Simplify(ClassVariable aVar)
	{
		ArrayList<Object> objList = new ArrayList<Object>();
		String temp ="";
		for(Object obj: aVar.getValues())
		{
			temp = obj.toString();
			if(containsOperator(temp))
			{//				objList.add(CTOp)
			}
		}
	}
	
	public void SimplifyList(ArrayList<ClassVariable> lstVars)
	{
		
	}

	/**
	 * @return the theContext
	 */
	public CTContext getContext() {
		return theContext;
	}

	/**
	 * @param theContext the theContext to set
	 */
	public void setContext(CTContext theContext) {
		this.theContext = theContext;
	}
	
	private boolean containsOperator(String str)
	{
		if(str.indexOf("+")<0 && str.indexOf("-")<0 && str.indexOf("/")<0 && str.indexOf("*")<0 )
		{
			return false;
		}
		return true;
	}
	private String simplifyVar(String strVarName , String strVarVal)
	{
		if(this.theContext==null)
		{
			//return strVarVal;
			throw new NullPointerException("CTContext is not initialized!");
		}
		else
		{
			for(CTOperation opt : this.theContext.getLstOperations())
			{
				return simplyfyVar(strVarName, strVarVal, opt);
			}
			return "";
		}
	}
	private String simplyfyVar(String strVarName, String strVarVal, CTOperation opt)
	{
//		if(opt.getPostConditions().con)
		return "";		
	}
}
