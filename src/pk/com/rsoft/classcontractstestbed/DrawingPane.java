/**
 * 
 */
package pk.com.rsoft.classcontractstestbed;

import java.awt.Graphics;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import pk.com.rsoft.classcontractstestbed.util.graph.AbstractFSM;
import pk.com.rsoft.classcontractstestbed.util.graph.State;
import pk.com.rsoft.classcontractstestbed.util.graph.Transition;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author HAMMAD
 *
 */
@SuppressWarnings("serial")
public final class DrawingPane extends JFrame {
	final AbstractFSM fsm;
	/**
	 * @throws HeadlessException
	 */
	public DrawingPane(final AbstractFSM f) {
		this.fsm = f;

//		getContentPane().setLayout(null);
		getContentPane().add(new DrawingPanel(f));		
		f.setLocationOnScreen(200,300,true);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnAfsm = new JMenu("AFSM");
		menuBar.add(mnAfsm);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
//					for(State s: DrawingPane.this.fsm.getStatesList())
//					{
                            DrawingPane.this.saveAFSM(DrawingPane.this.fsm, "d:\\fout.xml");
//                    }
				} catch (Exception e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(DrawingPane.this, e.getMessage());
				}
			}
		});
		mnAfsm.add(mntmSave);
		
		JMenuItem mntmLoad = new JMenuItem("Load...");
		mntmLoad.setActionCommand("Load");
		mnAfsm.add(mntmLoad);
                mntmLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                             JOptionPane.showMessageDialog(null,DrawingPane.this.loadAFSM("d:\\fout.xml"));
                        }
		});
		JMenuItem mntmEdit = new JMenuItem("Edit");
                mntmEdit.setActionCommand("Edit");
		mntmEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                        DlgSelectState dlgStateSelect = new DlgSelectState(DrawingPane.this, true, f.getStatesList());                            
                        dlgStateSelect.setVisible(true);
                        }
		});
		mnAfsm.add(mntmEdit);
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
			this.fsm.draw(g);
		}
@Override
public void paint(Graphics g) {
	// TODO Auto-generated method stub
	super.paint(g);
	draw(g);
}
	}
public void saveAFSM(AbstractFSM fsm,String fnameWithPath)
{
	AbstractFSM.toFile(fsm, fnameWithPath);
}
public AbstractFSM loadAFSM(String fnameWithPath)
{
	return AbstractFSM.fromFile(fnameWithPath);
}
}
class EvenListner implements ActionListener{

    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        if(command.equals("Edit"))
        {
            
        }
    }
}