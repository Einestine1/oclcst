package pk.com.rsoft.classcontractstestbed;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import pk.com.rsoft.classcontractstestbed.util.graph.ClassVariable;
import pk.com.rsoft.classcontractstestbed.util.inequality.InEqualitySimplified;

import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class ClassVarDialog extends JDialog {

	private ArrayList<ClassVariable> lstClassVars;
	private final JPanel contentPanel = new JPanel();
	private JList jListClassVars;
	private JList jListClassVarValues;

	/**
	 * Create the dialog.
	 */
	public ClassVarDialog(String title,boolean model , ArrayList<ClassVariable> clsVarList) {
		setTitle(title);
		setModal(model);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		if(clsVarList==null)
		{
			this.lstClassVars = new ArrayList<ClassVariable>();
		}
		setBounds(100, 100, 761, 192);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		jListClassVars = new JList();
		jListClassVars.addListSelectionListener(new JListClassVarsListSelectionListener());
		jListClassVars.setBounds(109, 11, 241, 100);
		contentPanel.add(jListClassVars);
		
		JLabel lblIdentifiedVariables = new JLabel("Identified Variables ");
		lblIdentifiedVariables.setBounds(10, 12, 101, 23);
		contentPanel.add(lblIdentifiedVariables);
		
		jListClassVarValues = new JList();
		jListClassVarValues.setBounds(360, 11, 383, 100);
		contentPanel.add(jListClassVarValues);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new OkButtonActionListener());
				
				JButton btnAdd = new JButton("Add");
				buttonPane.add(btnAdd);
				
				JButton btnRemove = new JButton("Remove");
				btnRemove.addActionListener(new BtnRemoveActionListener());
				buttonPane.add(btnRemove);
				
				JButton btnUpdate = new JButton("Update");
				buttonPane.add(btnUpdate);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new CancelButtonActionListener());
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		loadClassVariables(clsVarList);
	}
	public void loadClassVariables(ArrayList<ClassVariable> theList)
	{
		DefaultListModel model = new DefaultListModel();
		for(ClassVariable clsVar : theList)
		{
			model.addElement(clsVar);
		}
		jListClassVars.setModel(model);
	}
	private void loadCalssVarValues(ClassVariable theVar)
	{
		DefaultListModel model = new DefaultListModel();
		
		for(InEqualitySimplified inq : theVar.getValues())
		{
			model.addElement(inq);
		}
		jListClassVarValues.setModel(model);
	}
	
	private class CancelButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ClassVarDialog.this.dispose();
		}
	}
	private class JListClassVarsListSelectionListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent lstSelectEvent) {
			ClassVarDialog.this.loadCalssVarValues((ClassVariable)jListClassVars.getSelectedValue());
		}
	}
	private class OkButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ClassVarDialog.this.dispose();
		}
	}
	private class BtnRemoveActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ClassVariable theVar = (ClassVariable)ClassVarDialog.this.jListClassVars.getSelectedValue();
			theVar.removeVaValue(jListClassVarValues.getSelectedValue());
			loadCalssVarValues(theVar);
		}
	}
}
