package pk.com.rsoft.classcontractstestbed.models;
public class  CoinBox 
{
	private int curQtr=0;
	public int quantity = 0;
	private int totalQtrs=0;
	private boolean allowVend = false;
	
	public CoinBox()
	{
		this.curQtr = 0;
		this.allowVend = false;
		this.quantity = 0;
		this.totalQtrs=0;	
	}
	public void addDrink(int m)
	{
		if(m>0 && quantity==0)
		{
			this.quantity +=m;
		}
	}
	
	public void addQtr()
	{
		if(this.quantity>0)
		{
			this.curQtr+=1;
			totalQtrs+=1;
		}
		if(this.curQtr==1)
		{
			this.allowVend=true;	
		}

	}
	
	public void retQtrs()
	{
			this.curQtr =0;
			this.allowVend=false;
	}
	public void vend()
	{
			this.totalQtrs +=this.curQtr;
			this.curQtr=0;
			this.allowVend=false;
			this.quantity-=1;
	}
}