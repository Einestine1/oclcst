/**
 * 
 */
package pk.com.rsoft.classcontractstestbed.classcontract;

import java.awt.Graphics;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import pk.com.rsoft.classcontractstestbed.util.graph.AbstractFSM;
import pk.com.rsoft.classcontractstestbed.util.graph.State;

/**
 * @author HAMMAD
 *
 */
@SuppressWarnings("serial")
public final class DrawingPane extends JFrame {
	AbstractFSM fsm;
	/**
	 * @throws HeadlessException
	 */
	public DrawingPane(AbstractFSM f) {
		this.fsm = f;
		this.add(new DrawingPanel(f));
		// TODO Auto-generated constructor stub
	}
	class DrawingPanel extends JPanel{
		AbstractFSM fsm;
		public DrawingPanel(AbstractFSM fsm)
		{
			this.fsm=fsm;
		}
		public void draw(Graphics g)
		{
//			System.out.println("Size of state arry is "+fsm.getStatesList().size());
			for(State st :fsm.getStatesList())
			{
				st.draw(g);
			}
		}
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		draw(g);
		}
	}
}
