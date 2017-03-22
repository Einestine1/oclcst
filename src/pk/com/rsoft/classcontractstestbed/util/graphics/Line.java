/**
 * 
 */
package pk.com.rsoft.classcontractstestbed.util.graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 * @author HAMMAD
 *
 */
public class Line implements Shape {
	private Point startPoint;
	private Point endPoint;
	@Override
	public void draw(Graphics g) {
//		g.drawLine((int)startPoint.getX(), (int)startPoint.getY(), (int)endPoint.getX(), (int)endPoint.getY());
//		if(startPoint.getX()< endPoint.getX()&&startPoint.getY()< endPoint.getY())
//		{
//			g.drawLine((int)endPoint.getX(), (int)endPoint.getY(), (int)endPoint.getX()-10, (int)endPoint.getY()-10);
//			g.drawLine((int)endPoint.getX(), (int)endPoint.getY(), (int)endPoint.getX()-10, (int)endPoint.getY()+10);					
//		}
//		else
//		{
//			g.drawLine((int)startPoint.getX(), (int)startPoint.getY(), (int)startPoint.getX()+10, (int)startPoint.getY()+10);
//			g.drawLine((int)startPoint.getX(), (int)startPoint.getY(), (int)startPoint.getX()+10, (int)startPoint.getY()-10);								
//		}
	}
	public Line(Point start, Point end)
	{
		this.setStartPoint(start);
		this.setEndPoint(end);
	}
	public Line(int x1, int y1, int x2, int y2)
	{
		this(new Point(x1,y1),new Point(x2,y2));
	}
	public Line()
	{
		this(0,0,100,100);
	}
	/* (non-Javadoc)
	 * @see pk.com.rsoft.classcontractstestbed.util.graphics.Shape#Move(int, int, java.awt.Graphics)
	 */
	@Override
	public void Move(int newX, int newY, Graphics g) {
	}
	/**
	 * @param startPoint the startPoint to set
	 */
	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}
	/**
	 * @return the startPoint
	 */
	public Point getStartPoint() {
		return startPoint;
	}
	/**
	 * @param endPoint the endPoint to set
	 */
	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}
	/**
	 * @return the endPoint
	 */
	public Point getEndPoint() {
		return endPoint;
	}
    protected void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
    	final int ARR_SIZE = 4;
    	Graphics2D g = (Graphics2D) g1.create();

        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx*dx + dy*dy);
        AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        g.setTransform(at);

        // Draw horizontal arrow starting in (0, 0)
        g.drawLine(0, 0, (int) len, 0);
        g.fillPolygon(new int[] {len, len-ARR_SIZE, len-ARR_SIZE, len},
                      new int[] {0, -ARR_SIZE, ARR_SIZE, 0}, 4);
    }
    protected void drawArrow(Graphics g, Point pt1,Point pt2)
    {
    	drawArrow(g, (int)pt1.getX(), (int) pt1.getY(), (int) pt2.getX(), (int) pt2.getY());
    }
    
    public Point getMidPoint()
    {
    	int x1 = (int)startPoint.getX();
    	int y1 = (int)startPoint.getY();

    	int x2 = (int) endPoint.getX();
    	int y2 = (int) endPoint.getY();
    	
    	Point retPt = new Point();
    	
    	if(x1>x2)
    	{
    		retPt.setX(x2 + (x1 - x2)/2);
    	}
    	else
    	{
    		retPt.setX(x1 + (x2 - x1)/2);
    	}
    	
    	
    	if(y1>y2)
    	{
    		retPt.setY(y2 + (y1 - y2)/2);    		
    	}
    	else
    	{
    		retPt.setY(y1 + (y2 - y1)/2);
    	}
    	return retPt;
    	
    }
}
