/**
 * 
 */
package pk.com.rsoft.classcontractstestbed.util.graphics;

import java.awt.Graphics;

import pk.com.rsoft.classcontractstestbed.util.parser.NumberPorcessor;

/**
 * @author Rehan
 *
 */
public interface Shape {

	public abstract void draw(Graphics g);
	public abstract void Move(int newX, int newY,Graphics g);
//	public int getRandomInt(int start, int end);
}
