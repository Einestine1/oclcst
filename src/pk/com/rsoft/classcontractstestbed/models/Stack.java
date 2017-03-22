/**
 * 
 */
package pk.com.rsoft.classcontractstestbed.models;

/**
 * @author Rehan
 *
 */
public class Stack {
	int Vals[];
	private int Top;
	private boolean isEmpty; 
	public Stack()
	{
		this.Top=0;
		this.isEmpty = true;
		this.Vals = new int[10];
	}
	public void push(int val)
	{
		this.Vals[Top++]=val;
	}
	public int pop()
	{
		return this.Vals[Top--];
	}
	public boolean isEmpty()
	{
		return isEmpty;
	}
}

