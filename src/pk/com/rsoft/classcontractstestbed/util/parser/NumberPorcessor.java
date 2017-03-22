package pk.com.rsoft.classcontractstestbed.util.parser;

import pk.com.rsoft.classcontractstestbed.util.graphics.Shape;

public final class NumberPorcessor {

	private NumberPorcessor() {

	}
	public static final boolean isNumeric(String strVal)
	{
		if (strVal.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {  
            return true;  
        } else {  
            return false;  
        }  			
	}
	public static int getRandomInt(int start, int end) {
		return (int)(start + Math.random()*end);
	}

	public static String padeZeros(String val,int padeLength,boolean before)
	{
		String retStr = val;
		if(before)
		{
			for(int i=0;i<padeLength;i++)
			{
				retStr="0"+retStr;
			}
		}
		else
		{
			for(int i=0;i<padeLength;i++)
			{
				retStr+="0";
			}
			
		}
		
		return retStr;
	}
}
