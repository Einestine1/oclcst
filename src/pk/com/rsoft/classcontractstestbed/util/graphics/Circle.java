/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pk.com.rsoft.classcontractstestbed.util.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

/**
 *
 * @author Hammad
 */
public class Circle implements Shape , Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double radius;
    private int x;
    private int y;
    private Color c;
    public Circle(int x, int y, double radious,Color c)
    {
        this.x = x;
        this.y = y;
        this.radius = radious;
        this.c = c;
    }
    /**
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * @param radius the radius to set
     */
    public void setRadius(double radius) {
        this.radius = radius;
        if(radius<0)
        {
            this.radius = -radius;
        }
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        if(x>=0)
        {
            this.x = x;
        }
        else
        {
            this.x =0;
        }
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
       if(y>=0)
       {
            this.y = y;           
       }
       else
       {
           this.y=0;
       }

    }

    /**
     * @return the c
     */
    public Color getC() {
        return c;
    }

    /**
     * @param c the c to set
     */
    public void setColor(Color c) {
        this.c = c;
    }
    @Override 
    public void draw(Graphics g)
    {
        g.setColor(c);
        g.drawOval((int)(x-radius), (int)(y-radius), (int)(radius*2), (int)(radius *2));
    }
	@Override
	public void Move(int newX, int newY, Graphics g) {
		setX(newX);
		setY(newY);
		draw(g);
	}
	public Point getCenter()
	{
		return new Point(this.x,this.y);
	}
	public void setCenter(Point pt)
	{
		this.setX((int)pt.getX());
		this.setY((int)pt.getY());
	}
}
