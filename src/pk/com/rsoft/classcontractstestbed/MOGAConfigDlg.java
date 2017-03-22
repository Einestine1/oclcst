package pk.com.rsoft.classcontractstestbed;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.SpinnerNumberModel;

public class MOGAConfigDlg extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			MOGAConfigDlg dialog = new MOGAConfigDlg();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public MOGAConfigDlg() {
		setTitle("MOGA Parameters");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JSpinner spinner = new JSpinner();
			spinner.setModel(new SpinnerNumberModel(10, 5, 100, 1));
			spinner.setBounds(342, 12, 67, 20);
			contentPanel.add(spinner);
		}
		
		JLabel lblLimitOnNumber = new JLabel("Limit on Number of Test Sequences (>=5)");
		lblLimitOnNumber.setBounds(25, 14, 256, 14);
		contentPanel.add(lblLimitOnNumber);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(10, 3, 100, 1));
		spinner.setBounds(342, 58, 67, 20);
		contentPanel.add(spinner);
		
		JLabel lblRequiredLenghtOf = new JLabel("Required Lenght of Test Sequences (between 3 and 100)");
		lblRequiredLenghtOf.setBounds(25, 60, 305, 14);
		contentPanel.add(lblRequiredLenghtOf);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
