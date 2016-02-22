/*
 * SbApp: Open Source software for novelists and authors.
 * Original idea 2008 - 2012 Martin Mustun
 * Copyrigth (C) Favdb
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */
package storybook.ui.dialog;

import java.util.List;

import storybook.model.EntityUtil;
import storybook.model.hbn.entity.AbstractEntity;
import storybook.toolkit.I18N;
import storybook.toolkit.html.HtmlUtil;
import storybook.ui.MainFrame;

// TODO: Auto-generated Javadoc
/**
 * The Class dlgConfirmDelete.
 *
 * @author favdb
 */
public class dlgConfirmDelete extends javax.swing.JDialog {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3862870810273755292L;

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
			java.util.logging.Logger.getLogger(dlgConfirmDelete.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(dlgConfirmDelete.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(dlgConfirmDelete.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(dlgConfirmDelete.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		}
		// </editor-fold>

		/* Create and display the dialog */
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				dlgConfirmDelete dialog = new dlgConfirmDelete(new javax.swing.JFrame(), true);
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
	
	/** The canceled. */
	private boolean canceled;

	/** The main frame. */
	MainFrame mainFrame;

	/** The b cancel. */
	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton bCancel;

	/** The b ok. */
	private javax.swing.JButton bOk;

	/** The j label1. */
	private javax.swing.JLabel jLabel1;

	/** The j scroll pane1. */
	private javax.swing.JScrollPane jScrollPane1;

	/** The tp entity. */
	private javax.swing.JTextPane tpEntity;
	// End of variables declaration//GEN-END:variables

	/**
	 * Test form dlgConfirmDelete.
	 *
	 * @param parent the parent
	 * @param b the b
	 */
	public dlgConfirmDelete(javax.swing.JFrame parent, boolean b) {
		super(parent, b);
		initComponents();
	}

	/**
	 * Create form dlgConfirmDelete.
	 *
	 * @param parent the parent
	 * @param entity the entity
	 */
	public dlgConfirmDelete(MainFrame parent, AbstractEntity entity) {
		super(parent, true);
		initComponents();
		canceled = false;
		mainFrame = parent;
		tpEntity.setText(EntityUtil.getDeleteInfo(mainFrame, entity));
		tpEntity.setCaretPosition(0);
	}
	
	/**
	 * Instantiates a new dlg confirm delete.
	 *
	 * @param parent the parent
	 * @param entities the entities
	 */
	public dlgConfirmDelete(MainFrame parent, List<AbstractEntity> entities) {
		super(parent, true);
		initComponents();
		canceled = false;
		mainFrame = parent;
		jLabel1.setText(I18N.getMsg("msg.common.multi.delete.question"));
		StringBuilder buf = new StringBuilder();
		buf.append(HtmlUtil.getHeadWithCSS());
		for (AbstractEntity entity : entities) {
			buf.append("<p style='margin-bottom:10px'>\n").append(EntityUtil.getEntityFullTitle(entity))
					.append("</p>\n").append("<p style=''>\n").append(EntityUtil.getDeleteInfo(mainFrame, entity))
					.append("</p>\n").append("<hr style='margin:10px'>\n");
		}
		tpEntity.setText(buf.toString());
		tpEntity.setCaretPosition(0);
	}
	
	/**
	 * B cancel action performed.
	 *
	 * @param evt the evt
	 */
	private void bCancelActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bCancelActionPerformed
		canceled = true;
		dispose();
	}// GEN-LAST:event_bCancelActionPerformed
	
	/**
	 * B ok action performed.
	 *
	 * @param evt the evt
	 */
	private void bOkActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bOkActionPerformed
		canceled = false;
		dispose();
	}// GEN-LAST:event_bOkActionPerformed
	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jLabel1 = new javax.swing.JLabel();
		jScrollPane1 = new javax.swing.JScrollPane();
		tpEntity = new javax.swing.JTextPane();
		bOk = new javax.swing.JButton();
		bCancel = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("storybook/msg/messages"); // NOI18N
		setTitle(bundle.getString("msg.common.warning")); // NOI18N

		jLabel1.setText(bundle.getString("msg.common.delete.question")); // NOI18N

		tpEntity.setEditable(false);
		tpEntity.setContentType("text/html"); // NOI18N
		jScrollPane1.setViewportView(tpEntity);

		bOk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/ok.png"))); // NOI18N
		bOk.setText(bundle.getString("msg.common.delete")); // NOI18N
		bOk.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bOkActionPerformed(evt);
			}
		});

		bCancel.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/error.png"))); // NOI18N
		bCancel.setText(bundle.getString("msg.common.cancel")); // NOI18N
		bCancel.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bCancelActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addComponent(jLabel1).addGap(0, 148,
										Short.MAX_VALUE))
								.addComponent(jScrollPane1).addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
										layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(bOk)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
												.addComponent(bCancel)))
						.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jLabel1)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(bOk).addComponent(bCancel))
						.addContainerGap()));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	/**
	 * Checks if is canceled.
	 *
	 * @return true, if is canceled
	 */
	public boolean isCanceled() {
		return canceled;
	}
}
