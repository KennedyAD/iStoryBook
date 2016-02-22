/*
Storybook: Open Source software for novelists and authors.
Copyright (C) 2013 - 2015 FaVdB

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package storybook.importer;

import static storybook.toolkit.BookUtil.getHomeDir;

import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;

import storybook.SbConstants;
import storybook.model.hbn.entity.Preference;
import storybook.toolkit.PrefUtil;
import storybook.toolkit.filefilter.H2FileFilter;
import storybook.ui.MainFrame;

// TODO: Auto-generated Javadoc
/**
 * The Class DlgImport.
 *
 * @author favdb
 */
public class DlgImport extends javax.swing.JDialog {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8233035167528894640L;
	
	/**
	 * The main method.
	 *
	 * @param args            the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed" desc=" Look and feel setting
		// code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.
		 * html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(DlgImport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(DlgImport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(DlgImport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(DlgImport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>

		/* Create and display the dialog */
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				DlgImport dialog = new DlgImport(new javax.swing.JFrame(), true);
				dialog.addWindowListener(new java.awt.event.WindowAdapter() {
					@Override
					public void windowClosing(java.awt.event.WindowEvent e) {
						System.exit(0);
					}
				});
				dialog.setVisible(true);
			}
		});
	}

	/** The main frame. */
	MainFrame mainFrame;

	/** The bt close. */
	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btClose;

	/** The bt file. */
	private javax.swing.JButton btFile;

	/** The bt import. */
	private javax.swing.JButton btImport;

	/** The j label1. */
	private javax.swing.JLabel jLabel1;

	/** The j label2. */
	private javax.swing.JLabel jLabel2;

	/** The j label3. */
	private javax.swing.JLabel jLabel3;

	/** The j scroll pane1. */
	private javax.swing.JScrollPane jScrollPane1;
	
	/** The j scroll pane2. */
	private javax.swing.JScrollPane jScrollPane2;
	
	/** The lst items. */
	private javax.swing.JList lstItems;
	
	/** The lst persons. */
	private javax.swing.JList lstPersons;
	
	/** The tx file. */
	private javax.swing.JTextField txFile;
	// End of variables declaration//GEN-END:variables
	/**
	 * Creates new form DlgImport.
	 *
	 * @param parent the parent
	 * @param modal the modal
	 */
	public DlgImport(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		init();
	}
	
	/**
	 * Instantiates a new dlg import.
	 *
	 * @param mainFrame the main frame
	 */
	public DlgImport(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		initComponents();
		init();
	}
	
	/**
	 * Bt close action performed.
	 *
	 * @param evt the evt
	 */
	private void btCloseActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btCloseActionPerformed
		dispose();
	}// GEN-LAST:event_btCloseActionPerformed
	
	/**
	 * Bt file action performed.
	 *
	 * @param evt the evt
	 */
	private void btFileActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btFileActionPerformed
		JFileChooser chooser = new JFileChooser(txFile.getText());
		Preference pref = PrefUtil.get(SbConstants.PreferenceKey.LAST_OPEN_DIR, getHomeDir());
		chooser.setCurrentDirectory(new File(pref.getStringValue()));
		H2FileFilter filter = new H2FileFilter();
		chooser.addChoosableFileFilter(filter);
		chooser.setFileFilter(filter);
		int i = chooser.showOpenDialog(this);
		if (i != 0)
			return;
		File file = chooser.getSelectedFile();
		txFile.setText(file.getAbsolutePath());
		loadLists();
	}// GEN-LAST:event_btFileActionPerformed
	
	/**
	 * Bt import action performed.
	 *
	 * @param evt the evt
	 */
	private void btImportActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btImportActionPerformed
		doImport();
		dispose();
	}// GEN-LAST:event_btImportActionPerformed
	
	/**
	 * Do import.
	 */
	private void doImport() {

	}

	/**
	 * Inits the.
	 */
	private void init() {
		new DefaultListModel<>();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jLabel1 = new javax.swing.JLabel();
		txFile = new javax.swing.JTextField();
		btFile = new javax.swing.JButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		lstPersons = new javax.swing.JList();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jScrollPane2 = new javax.swing.JScrollPane();
		lstItems = new javax.swing.JList();
		btClose = new javax.swing.JButton();
		btImport = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("storybook/msg/messages"); // NOI18N
		setTitle(bundle.getString("msg.common.import")); // NOI18N

		jLabel1.setText(bundle.getString("dlg.import.file")); // NOI18N

		btFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/open.png"))); // NOI18N
		btFile.setText(bundle.getString("dlg.import.file.button")); // NOI18N
		btFile.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btFileActionPerformed(evt);
			}
		});

		jScrollPane1.setViewportView(lstPersons);

		jLabel2.setText(bundle.getString("msg.common.persons")); // NOI18N

		jLabel3.setText(bundle.getString("msg.common.items")); // NOI18N

		jScrollPane2.setViewportView(lstItems);

		btClose.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/close.png"))); // NOI18N
		btClose.setText(bundle.getString("msg.common.cancel")); // NOI18N
		btClose.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btCloseActionPerformed(evt);
			}
		});

		btImport.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/export.png"))); // NOI18N
		btImport.setText(bundle.getString("msg.common.import")); // NOI18N
		btImport.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btImportActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								layout.createSequentialGroup().addContainerGap()
										.addGroup(
												layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(layout.createSequentialGroup().addComponent(jLabel1)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(txFile)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(btFile))
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
								layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE)
										.addComponent(btClose, javax.swing.GroupLayout.PREFERRED_SIZE, 118,
												javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(btImport, javax.swing.GroupLayout.PREFERRED_SIZE, 117,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(layout.createSequentialGroup().addComponent(jLabel2).addGap(0, 0,
												Short.MAX_VALUE))
										.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 249,
												Short.MAX_VALUE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 270,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jLabel3)))).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
								.addComponent(txFile, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jLabel1).addComponent(btFile))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel2)
						.addComponent(jLabel3))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
						.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
						.addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(btImport)
						.addComponent(btClose))
				.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	/**
	 * Load items list.
	 */
	private void loadItemsList() {
		new DefaultListModel<>();
	}

	/**
	 * Load lists.
	 */
	private void loadLists() {
		loadPersonsList();
		loadItemsList();
		btImport.setEnabled(false);
		if ((lstPersons.getComponentCount() > 0) || (lstItems.getComponentCount() > 0))
			btImport.setEnabled(true);
	}

	/**
	 * Load persons list.
	 */
	private void loadPersonsList() {
		new DefaultListModel<>();
	}
}
