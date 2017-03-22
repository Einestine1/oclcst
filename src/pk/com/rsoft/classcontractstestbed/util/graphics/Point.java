/**
 * 
 */
package pk.com.rsoft.classcontractstestbed.util.graphics;

import java.awt.Graphics;

/**
 * @author HAMMAD
 *
 */
public class Point implements Shape {
	private double x;
	private double y;
	private double z;

	/**
	 * 
	 */
	public Point(double x, double y) {
		// TODO Auto-generated constructor stub
		this(x,y,0);
	}
	public Point(double x,double y, double z)
	{
		this.setX(x);
		this.setY(y);
		this.setZ(z);
	}
	public Point()
	{
		this(0,0);
	}
	/* (non-Javadoc)
	 * @see pk.com.rsoft.classcontractstestbed.util.graphics.Shape#Draw(java.awt.Graphics)
	 */
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see pk.com.rsoft.classcontractstestbed.util.graphics.Shape#Move(int, int, java.awt.Graphics)
	 */
	@Override
	public void Move(int newX, int newY, Graphics g) {
		// TODO Auto-generated method stub

	}
	/**
	 * @param x the x to set
	 */
	public void setX(double x) {
		this.x = x;
	}
	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(double y) {
		this.y = y;
	}
	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}
	/**
	 * @return the z
	 */
	public double getZ() {
		return z;
	}
	/**
	 * @param z the z to set
	 */
	public void setZ(double z) {
		this.z = z;
	}
	public Point addPoint(Point pt)
	{
		this.x += pt.x;
		this.y += pt.y;
		return this;
	}
	public void addX(double x)
	{
		this.x+= x;
	}
	public void addY(double y)
	{
		this.y+= y;
	}
}
