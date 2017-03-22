package pk.com.rsoft.classcontractstestbed;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;

public class ParseTree extends JFrame {

	private JPanel contentPane;
	private JTree tree;

	/**
	 * Create the frame.
	 */
	public ParseTree(JTree theTree) {
		setTitle("OCL Parse Tree");
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		tree = theTree;
		contentPane.add(tree, BorderLayout.CENTER);
	}

	public JTree getTree() {
		return tree;
	}
	public void setTree(JTree aTree)
	{
		this.tree= aTree;
	}
	
}
