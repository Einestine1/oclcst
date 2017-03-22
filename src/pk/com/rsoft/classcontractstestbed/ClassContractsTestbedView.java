/*
 * ClassContractsTestbedView.java
 */

package pk.com.rsoft.classcontractstestbed;

import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.DefaultTreeModel;
import pk.com.rsoft.classcontractstestbed.classcontract.CTContext;
import pk.com.rsoft.classcontractstestbed.DrawingPane;
import pk.com.rsoft.classcontractstestbed.testsequences.TestSequence;
import pk.com.rsoft.classcontractstestbed.util.graph.AbstractFSM;
import pk.com.rsoft.classcontractstestbed.util.io.ObjectIO;
import pk.com.rsoft.classcontractstestbed.util.parser.OCLClassContractsParser;
import pk.com.rsoft.ga.multiobjective.TestSequenceMultiObjectiveExecuter;
import pk.com.rsoft.testsequenceoptimization.ga.AFSMHolder;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.InvalidObjectException;

import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 * The application's main frame.
 */
public class ClassContractsTestbedView extends FrameView implements ActionListener {
    private pk.com.rsoft.classcontractstestbed.util.parser.OCLClassContractsParser p;
    public ClassContractsTestbedView(SingleFrameApplication app) {
        super(app);
        p = new OCLClassContractsParser();
        initComponents();
        this.jTextArea1.setText("Load and Parser an OCL document using File menu's Parse OCL Option!");
        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = ClassContractsTestbedApp.getApplication().getMainFrame();
            aboutBox = new ClassContractsTestbedAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        ClassContractsTestbedApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N

        jScrollPane1.setName("jScrollPane1");

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(pk.com.rsoft.classcontractstestbed.ClassContractsTestbedApp.class).getContext().getResourceMap(ClassContractsTestbedView.class);
        jTextArea1.setText(resourceMap.getString("jtOutput.text")); // NOI18N
        jTextArea1.setName("jtOutput"); // NOI18N
        jScrollPane2.setViewportView(jTextArea1);

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText("Test Cases After Muti Objective Genetic Optimization"); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        javax.swing.GroupLayout gl_mainPanel = new javax.swing.GroupLayout(mainPanel);
        gl_mainPanel.setHorizontalGroup(
        	gl_mainPanel.createParallelGroup(Alignment.LEADING)
        		.addComponent(jScrollPane2, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
        		.addGroup(gl_mainPanel.createSequentialGroup()
        			.addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 406, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(36, Short.MAX_VALUE))
        		.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
        );
        gl_mainPanel.setVerticalGroup(
        	gl_mainPanel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_mainPanel.createSequentialGroup()
        			.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 299, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE))
        );
        mainPanel.setLayout(gl_mainPanel);
        
        txtrUseFileparseOcl = new JTextArea();
        txtrUseFileparseOcl.setText("Use File>Parse OCL ");
        txtrUseFileparseOcl.setRows(5);
        txtrUseFileparseOcl.setName("jtOutput");
        txtrUseFileparseOcl.setColumns(20);
        jScrollPane1.setViewportView(txtrUseFileparseOcl);
        
        JLabel lblTestCasesFrom = new JLabel();
        lblTestCasesFrom.setText("Test Cases from Exhaustive Search");
        lblTestCasesFrom.setName("jLabel1");
        lblTestCasesFrom.setFont(new Font("Tahoma", Font.BOLD, 12));
        jScrollPane1.setColumnHeaderView(lblTestCasesFrom);

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(pk.com.rsoft.classcontractstestbed.ClassContractsTestbedApp.class).getContext().getActionMap(ClassContractsTestbedView.class, this);
        jMenuItem1 = new javax.swing.JMenuItem();
        
                jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.SHIFT_MASK));
                jMenuItem1.setText(resourceMap.getString("jMenuItem1.text")); // NOI18N
                jMenuItem1.setName("jMenuItem1"); // NOI18N
                jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jMenuItem1ActionPerformed(evt);
                    }
                });
                fileMenu.add(jMenuItem1);
        
        mntmParseOcl = new JMenuItem();
        mntmParseOcl.addActionListener(this);
        mntmParseOcl.setText("Parse OCL & Execute MOGA");
        mntmParseOcl.setName("jMenuItem1");
        fileMenu.add(mntmParseOcl);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);
        
        mnAbstractFsm = new JMenu();
        menuBar.add(mnAbstractFsm);
        mnAbstractFsm.setText("Abstract FSM");
        
        mntmBuild = new JMenuItem();
        mntmBuild.addActionListener(this);
        
        mnConfiguration = new JMenu();
        mnAbstractFsm.add(mnConfiguration);
        mnConfiguration.setText("Configuration");
        
        mnStates = new JMenu();
        mnConfiguration.add(mnStates);
        mnStates.setText("States");
        
        menuItem_2 = new JMenuItem();
        menuItem_2.addActionListener(this);
        menuItem_2.setText("State Varaibles...");
        menuItem_2.setName("jMenuItem1");
        mnStates.add(menuItem_2);
        
        menuItem_3 = new JMenuItem();
        menuItem_3.addActionListener(this);
        menuItem_3.setText("States...");
        menuItem_3.setName("exitMenuItem");
        mnStates.add(menuItem_3);
        
        mntmStates = new JMenuItem();
        mntmStates.addActionListener(this);
        mntmStates.setText("MO GA");
        mntmStates.setName("exitMenuItem");
        mnConfiguration.add(mntmStates);
        mntmBuild.setText("Build");
        mntmBuild.setName("jMenuItem1");
        mnAbstractFsm.add(mntmBuild);
        
        mntmView = new JMenuItem();
        mntmView.addActionListener(this);
        mntmView.setText("View...");
        mntmView.setName("viewMenuItem");
        mnAbstractFsm.add(mntmView);
        
        execute = new JMenuItem();
        execute.addActionListener(this);
        
        menuItemSave = new JMenuItem();
        menuItemSave.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		try {
					ObjectIO.toFile(AFSMHolder.getFSM(), "F:\\Rehan\\AFSM.fsm");
				} catch (InvalidObjectException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        });
        menuItemSave.setText("Save");
        menuItemSave.setName("jMenuItem1");
        mnAbstractFsm.add(menuItemSave);
        
        menuItemLoad = new JMenuItem();
        menuItemLoad.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		AFSMHolder.setFSM((AbstractFSM)ObjectIO.fromFile("F:\\Rehan\\AFSM.fsm"));
        	}
        });
        menuItemLoad.setText("Load");
        menuItemLoad.setName("jMenuItem1");
        mnAbstractFsm.add(menuItemLoad);
        execute.setText("Execute MOGA");
        execute.setName("viewMenuItem");
        mnAbstractFsm.add(execute);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout gl_statusPanel = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(gl_statusPanel);
        gl_statusPanel.setHorizontalGroup(
            gl_statusPanel.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 589, Short.MAX_VALUE)
            .addGroup(gl_statusPanel.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 419, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        gl_statusPanel.setVerticalGroup(
            gl_statusPanel.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gl_statusPanel.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(gl_statusPanel.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
       try{
           FileFilter filter1 = new ExtensionFileFilter("OCL", new String[] { "OCL" });

           //Create a file chooser
            final JFileChooser fc = new JFileChooser();
            fc.setFileFilter(filter1);
            //In response to a button click:
            int status = fc.showOpenDialog(this.getComponent());
            if (status == JFileChooser.APPROVE_OPTION)
            {
                this.jTextArea1.setText("");
                if (p.getContext()!=null) p.getContext().ClearLists();
                p.ParserOCL(fc.getSelectedFile().getPath());
                ct = p.getContext();

                JTree tree = new JTree(new DefaultTreeModel(p.getTree()));
                ParseTree theTree = new ParseTree(tree);
                theTree.setVisible(true);
                
//                StringBuilder strBuilder = new StringBuilder();               
//                this.jTree1.setModel();
//            sFsm = null;
//            ct = null;
//            System.gc();
            }


       }
       catch(Exception e)
       {
           e.printStackTrace();
       }

    }//GEN-LAST:event_jMenuItem1ActionPerformed

private void buildAFSM()
{
	if(ct==null)
	{
		JOptionPane.showMessageDialog(null, "Please load an OCL file containing class contract first.");
		return;
	}

	if(sFsm==null)
	{
		sFsm = new AbstractFSM(ct);		
	}

    if(sFsm.buildAFSM())
    {    
    	JOptionPane.showMessageDialog(null, "Abstract FSM was built successfully. It can be viewed using view menu option.");
    }
    else
    {
    	JOptionPane.showMessageDialog(null, "Failed to build Abstract FSM!");
    	sFsm = null;
    }
}
private void showAFSM()
{
    DrawingPane pane = new DrawingPane(sFsm);
    pane.setSize(500,500);
    pane.setVisible(true);

}

private void generateExhaustiveSequences()
{
    sFsm.generateTestSequences();
    StringBuilder builder = new StringBuilder();
    builder.append("Test Sequences from exhaustive search of FSM :\n\n");
    for(TestSequence seq: sFsm.getGeneratedSequences())
    {
    	builder.append(seq.toString());
    	builder.append("\n");
    }
    this.txtrUseFileparseOcl.setText(builder.toString());
    this.txtrUseFileparseOcl.setSelectionStart(0);
    this.txtrUseFileparseOcl.setSelectionEnd(5);
    
	
}
private void buildAndShowAFSM()
    {
    	buildAFSM();
    	generateExhaustiveSequences();
        executeMOGA();
    }
private void executeMOGA()
{
	if(sFsm==null)
	{
		sFsm = AFSMHolder.getFSM();
		if(sFsm==null)
		{
			JOptionPane.showMessageDialog(null, "Abstract FSM is not initialized please build it first using build menu option");
			return;
		}
	}
    AFSMHolder.setFSM(sFsm);
//    generateExhaustiveSequences();
    sFsm.generateTestSequences();
    JOptionPane.showMessageDialog(null, "Going to Run MO GA on generated test sequences, this may take a while.\nPlease wait after clicking Ok.");
    TestSequenceMultiObjectiveExecuter exm = new TestSequenceMultiObjectiveExecuter(this.txtrUseFileparseOcl);
    try {
		exm.execute();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	

    StringBuilder strBuilder = new StringBuilder();
    for(StringBuilder strB: TestSequenceMultiObjectiveExecuter.getBuilder())
	{
		strBuilder.append(strB.toString());
	}

	showAFSM();
	this.jTextArea1.setText(strBuilder.toString());

}
//    private void TestInequalities()
//    {
//    	InEqualitySimplified first,second,result;
//    	first = new InEqualitySimplified("a > 10", VariableType.INTEGER);
//    	second = new InEqualitySimplified("b >= 6", VariableType.INTEGER);
//    	System.out.println("First Inequality --> " + first);
//    	System.out.println("Second Inequality --> " + second);
//    	result = first.add(second).add(first);
//    	System.out.println("Sum is -->" + result);
//
//    	result = result.add(100);
//    	result = result.sub(3);
//    	System.out.println("Sum 100 is -->" + result);
//    	
//    	
//    	first = new InEqualitySimplified("a = true", VariableType.BOOLEAN);
//    	second = new InEqualitySimplified("b = false", VariableType.BOOLEAN);
//    	System.out.println("First Inequality --> " + first);
//    	System.out.println("Second Inequality --> " + second);
//    	result = first.or(second);
//    	System.out.println("Sum is -->" + result);
//    	
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;
    private JMenu mnAbstractFsm;
    private JMenuItem mntmBuild;
    private JMenuItem mntmView;
    private JMenu mnConfiguration;
    private JMenuItem mntmStates;
    private JMenu mnStates;
    private JMenuItem menuItem_2;
    private JMenuItem menuItem_3;
    private CTContext ct;
    private JTextArea txtrUseFileparseOcl;
    private JMenuItem mntmParseOcl;
    private AbstractFSM sFsm;
    private JMenuItem execute;
    private JMenuItem menuItemSave;
    private JMenuItem menuItemLoad;
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==ClassContractsTestbedView.this.mntmBuild)
		{
			buildAFSM();			
			
		}
		else if(e.getSource()==ClassContractsTestbedView.this.mntmParseOcl)
		{
			jMenuItem1ActionPerformed(e);
			buildAndShowAFSM();
		}
		else if(e.getSource()==ClassContractsTestbedView.this.mntmView)
		{
			if(sFsm==null)
			{
				sFsm = AFSMHolder.getFSM();
				if(sFsm==null)
				{
					JOptionPane.showMessageDialog(null, "Abstract FSM is not initialized, please build FSM using build menu option.");
					return;	
				}
				
			}
			showAFSM();
		}
		else if(e.getSource()==ClassContractsTestbedView.this.execute)
		{
			executeMOGA();
		}
		else
		{
			JOptionPane.showMessageDialog(null, "This Feature is still under implementation.");
			if(e.getSource()==ClassContractsTestbedView.this.menuItem_3)	
			{
				try{
				DlgSelectState dlgState = new DlgSelectState(null, true, AFSMHolder.getFSM().getStatesList());
				
				dlgState.setVisible(true);
				}catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null, "No active AFSM found, please parse OCL and Build AFS first.");
				}
			}
			else if(e.getSource()==ClassContractsTestbedView.this.menuItem_2)	
			{
				try{
					sFsm.EditClassVariables();
				
//				dlgState.setVisible(true);
				}catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null, "No active AFSM found, please parse OCL and Build AFS first.");
				}
			}

		
		}
	}
}
