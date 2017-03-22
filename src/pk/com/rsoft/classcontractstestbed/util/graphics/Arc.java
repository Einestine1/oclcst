/**
 * 
 */
package pk.com.rsoft.classcontractstestbed.util.graphics;

import java.awt.Color;
import java.awt.Graphics;

/**
 * @author Rehan
 *
 */
public class Arc implements Shape {

	private int delta;
	private int theta;
	private int height;
	private int width;
	private int y;
	private int x;
	private Color c;
	/**
	 * 
	 */

	public Arc(int x, int y, int width, int height, int theta, int delta,Color c) {
		this.setX(x);
		this.setY(x);
		this.setWidth(width);
		this.setHeight(height);
		this.setTheta(theta);
		this.setDelta(delta);
		this.setColor(c);
		//		drawAr ()
//		fillArc (int X, int Y, int Width, int Height, int Theta, int Delta)		
	}

	@Override
	public void draw(Graphics g) {
		g.drawArc (getX(), getY(), getWidth(), getHeight(), getTheta(), getDelta());	
		
	}
	@Override
	public void Move(int newX, int newY, Graphics g) {
		this.setX(newX);
		this.setY(newY);
		g.setColor(getColor());
		draw(g);		
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param theta the theta to set
	 */
	public void setTheta(int theta) {
		this.theta = theta;
	}

	/**
	 * @return the theta
	 */
	public int getTheta() {
		return theta;
	}

	/**
	 * @param delta the delta to set
	 */
	public void setDelta(int delta) {
		this.delta = delta;
	}

	/**
	 * @return the delta
	 */
	public int getDelta() {
		return delta;
	}

	/**
	 * @param c the c to set
	 */
	public void setColor(Color c) {
		this.c = c;
	}

	/**
	 * @return the c
	 */
	public Color getColor() {
		return c;
	}

}
