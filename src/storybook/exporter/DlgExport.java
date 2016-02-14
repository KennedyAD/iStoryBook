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

package storybook.exporter;

import java.awt.event.ItemEvent;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;

import storybook.SbConstants;
import storybook.model.hbn.entity.Internal;
import storybook.toolkit.BookUtil;
import storybook.toolkit.EnvUtil;
import storybook.toolkit.H2_Script;
import storybook.toolkit.I18N;
import storybook.toolkit.swing.SwingUtil;
import storybook.ui.MainFrame;

/**
 *
 * @author favdb
 */
public class DlgExport extends javax.swing.JDialog {
	private static String[] formats = { "csv", "txt", "html", "pdf", "odf", "xml", "sql" };
	/**
	 * @param args
	 *            the command line arguments
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
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(DlgExport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>

		/* Create and display the dialog */
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				DlgExport dialog = new DlgExport(new javax.swing.JFrame(), true);
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
	public MainFrame mainFrame;
	public List<ExportData> exports;

	public ParamExport paramExport;

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btClose;

	private javax.swing.JButton btExport;

	private javax.swing.JButton btFolder;

	private javax.swing.JButton btOptions;

	private javax.swing.JComboBox cbFormat;

	private javax.swing.JComboBox cbReport;

	private javax.swing.JLabel jLabel1;

	private javax.swing.JLabel jLabel2;

	private javax.swing.JLabel jLabel3;

	private javax.swing.JPanel jPanel1;

	private javax.swing.JScrollPane jScrollPane2;

	private javax.swing.JTextField txFolder;
	private javax.swing.JTextPane txOptions;
	// End of variables declaration//GEN-END:variables
	/**
	 * Creates new form dlgExportPrint
	 * 
	 * @param parent
	 *            : parent frame
	 * @param modal
	 *            : true or false
	 */
	public DlgExport(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		initUI();
	}
	public DlgExport(MainFrame parent) {
		super(parent, true);
		exports = new ArrayList<>();
		exports.add(new ExportData("summary", "msg.export.book.summary"));
		exports.add(new ExportData("part", "msg.export.part.list"));
		exports.add(new ExportData("chapter", "msg.export.chapter.list"));
		exports.add(new ExportData("scene", "msg.export.scene.list"));
		exports.add(new ExportData("person", "msg.export.person.list"));
		exports.add(new ExportData("location", "msg.export.location.list"));
		exports.add(new ExportData("tag", "msg.export.tag.list"));
		exports.add(new ExportData("item", "msg.export.item.list"));
		exports.add(new ExportData("idea", "msg.export.idea.list"));
		exports.add(new ExportData("all", "msg.export.all.list"));
		exports.add(new ExportData("book", "msg.export.book.text"));
		exports.add(new ExportData("sql", "msg.export.sql"));
		initComponents();
		mainFrame = parent;
		initUI();
	}
	private void btCloseActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btCloseActionPerformed
		BookUtil.store(mainFrame, SbConstants.BookKey.EXPORT_DIRECTORY, txFolder.getText());
		dispose();
	}// GEN-LAST:event_btCloseActionPerformed
	private void btExportActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btExportActionPerformed
		String format = cbFormat.getSelectedItem().toString();
		if (format.equals("sql")) {
			doExportSQL();
			return;
		}
		Export exp = new Export(this, format, 12);
		ExportData report = (ExportData) cbReport.getSelectedItem();
		SwingUtil.setWaitingCursor(this);
		String str = "";
		if (report.getExportName().equals("all")) {
			for (int i = 1; i < cbReport.getItemCount() - 1; i++) {
				ExportData data = (ExportData) cbReport.getItemAt(i);
				str = exp.fill(txFolder.getText(), data, null, true);
			}
			if ("html".equals(format))
				exp.createHtmlIndex(txFolder.getText());
		} else
			str = exp.fill(txFolder.getText(), report, null, false);
		SwingUtil.setDefaultCursor(this);
		if (str.isEmpty())
			return;
		dispose();
	}// GEN-LAST:event_btExportActionPerformed
	private void btFolderActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btFolderActionPerformed
		JFileChooser chooser = new JFileChooser(txFolder.getText());
		chooser.setFileSelectionMode(1);
		int i = chooser.showOpenDialog(this);
		if (i != 0)
			return;
		File file = chooser.getSelectedFile();
		txFolder.setText(file.getAbsolutePath());
	}// GEN-LAST:event_btFolderActionPerformed
	private void btOptionsActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btOptionsActionPerformed
		DlgExportOptions expo = new DlgExportOptions(this, true);
		expo.setVisible(true);
	}// GEN-LAST:event_btOptionsActionPerformed
	private void cbFormatItemStateChanged(java.awt.event.ItemEvent evt) {// GEN-FIRST:event_cbFormatItemStateChanged
		if (evt.getStateChange() == ItemEvent.SELECTED) {
			setOptions(evt.getItem().toString());
		}
	}// GEN-LAST:event_cbFormatItemStateChanged
	private void cbReportActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbReportActionPerformed
		ExportData report = (ExportData) cbReport.getSelectedItem();
		if (report.getExportName().contains("sql")) {
			cbFormat.setSelectedItem("sql");
			return;
		}
	}// GEN-LAST:event_cbReportActionPerformed
	private void cbReportItemStateChanged(java.awt.event.ItemEvent evt) {// GEN-FIRST:event_cbReportItemStateChanged
		if (evt.getStateChange() == ItemEvent.SELECTED) {
			if (evt.getItem().toString().equals(I18N.getMsg("msg.export.book.text")))
				setOptions(cbFormat.getSelectedItem().toString());
		}
	}// GEN-LAST:event_cbReportItemStateChanged
	private void doExportSQL() {
		String url = "jdbc:h2:" + mainFrame.getDbFile().getDbName();
		String file = /* txFolder.getText()+File.separator+ */mainFrame.getDbFile().getDbName() + ".sql";
		System.out.println("export to " + file);
		try {
			H2_Script.process(url, "sa", "", file, "", "");
			JOptionPane.showMessageDialog(this, "Export SQL is OK.", "SQL export", JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException ex) {
			System.err.println("export SQL exception=" + ex.getMessage());
			JOptionPane.showMessageDialog(this, "An error occured during export SQL, please report this error.",
					"SQL export error", JOptionPane.ERROR_MESSAGE);
		}
	}
	private String getBookOptions() {
		String str = "";
		if (cbReport.getSelectedItem().toString().equals(I18N.getMsg("msg.export.book.text"))) {
			str += "<p>" + I18N.getMsg("msg.export.book.htmloption") + " ";
			if (paramExport.htmlBookMulti)
				str += I18N.getMsg("msg.export.book.htmloption.multifile");
			else
				str += I18N.getMsg("msg.export.book.htmloption.onefile");
			str += "</p>";
			str += "<ul>";
			if (paramExport.isExportChapterNumbers)
				str += "<li>" + I18N.getMsg("msg.export.chapter.numbers") + "</li>";
			if (paramExport.isExportChapterNumbersRoman)
				str += "<li>" + I18N.getMsg("msg.export.roman.numerals") + "</li>";
			if (paramExport.isExportChapterTitles)
				str += "<li>" + I18N.getMsg("msg.export.chapter.titles") + "</li>";
			if (paramExport.isExportChapterDatesLocs)
				str += "<li>" + I18N.getMsg("msg.export.chapter.dates.locations") + "</li>";
			if (paramExport.isExportSceneTitles)
				str += "<li>" + I18N.getMsg("msg.export.scene.titles") + "</li>";
			if (paramExport.isExportSceneSeparator)
				str += "<li>" + I18N.getMsg("msg.export.scene.separator") + "</li>";
			str += "</ul>";
		}
		return (str);
	}

	private String getOptionsCsv() {
		String str = "";
		str += "<p>" + I18N.getMsg("msg.export.current_options_for") + " CSV :</p>";
		str += "<ul>";
		if (paramExport.csvNoQuotes)
			str += "<li>" + I18N.getMsg("msg.export.not_quoted") + "</li>";
		else
			str += "<li>" + I18N.getMsg("msg.export.csv_quoted_by",
					(paramExport.csvSingleQuotes ? I18N.getMsg("msg.export.options.csv.quoted.single")
							: I18N.getMsg("msg.export.options.csv.quoted.double")))
					+ "</li>";
		str += "<li>" + I18N.getMsg("msg.export.csv_separator_is") + " "
				+ (paramExport.csvComma ? I18N.getMsg("msg.export.options.csv.separate.comma")
						: I18N.getMsg("msg.export.options.csv.separate.semicolon"))
				+ "</li>";
		str += "</ul>";
		str += I18N.getMsg("msg.export.csv_not_book");
		str += getBookOptions();
		return (str);
	}

	private String getOptionsHtml() {
		String str = "";
		str += "<p>" + I18N.getMsg("msg.export.current_options_for") + " HTML :</p>";
		str += "<ul>";
		str += "<li>" + I18N.getMsg("msg.export.html_use_css") + " : "
				+ (paramExport.htmlUseCss ? I18N.getMsg("msg.common.yes") : I18N.getMsg("msg.common.no")) + "</li>";
		if (paramExport.htmlUseCss)
			str += "<li>" + I18N.getMsg("msg.export.html_css_file") + " : " + paramExport.htmlCssFile + "</li>";
		str += "</ul>";
		str += "<p>" + I18N.getMsg("msg.export.html_add_index") + "</p>";
		str += getBookOptions();
		return (str);
	}

	private String getOptionsOdf() {
		String str = "";
		str += "<p>" + I18N.getMsg("msg.export.current_options_for") + " ODF :</p>";
		str += "<ul>";
		str += "<li>" + I18N.getMsg("msg.export.odf.no_options") + "</li>";
		str += "</ul>";
		str += getBookOptions();
		return (str);
	}

	private String getOptionsPdf() {
		String str = "";
		str += "<p>" + I18N.getMsg("msg.export.current_options_for") + " PDF :</p>";
		str += "<ul>";
		str += "<li>" + I18N.getMsg("msg.export.options.pdf.pagesize", paramExport.pdfPageSize) + "</li>";
		str += "<li>" + I18N.getMsg("msg.export.options.pdf.orientation") + " : "
				+ (paramExport.pdfLandscape ? I18N.getMsg("msg.export.options.pdf.orientation.landscape")
						: I18N.getMsg("msg.export.options.pdf.orientation.portrait"))
				+ "</li>";
		str += "</ul>";
		str += getBookOptions();
		return (str);
	}

	private String getOptionsTxt() {
		String str = "";
		str += "<p>" + I18N.getMsg("msg.export.current_options_for") + " TXT :</p>";
		str += "<ul>";
		str += "<li>" + I18N.getMsg("msg.export.txt_separator",
				(paramExport.txtTab ? "'tab'" : "'" + paramExport.txtSeparator + "'")) + "</li>";
		str += "<li>" + I18N.getMsg("msg.export.txt_EOL") + "</li>";
		str += "</ul>";
		str += getBookOptions();
		return (str);
	}

	private String getOptionsXml() {
		String str = "";
		str += "<p>" + I18N.getMsg("msg.export.current_options_for") + " XML DocBook :</p>";
		str += "<ul>";
		str += "<li>" + I18N.getMsg("msg.export.xml.no_options") + "</li>";
		str += "</ul>";
		str += getBookOptions();
		return (str);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jLabel1 = new javax.swing.JLabel();
		txFolder = new javax.swing.JTextField();
		btFolder = new javax.swing.JButton();
		jLabel2 = new javax.swing.JLabel();
		cbReport = new javax.swing.JComboBox();
		jLabel3 = new javax.swing.JLabel();
		cbFormat = new javax.swing.JComboBox();
		btExport = new javax.swing.JButton();
		btClose = new javax.swing.JButton();
		jPanel1 = new javax.swing.JPanel();
		jScrollPane2 = new javax.swing.JScrollPane();
		txOptions = new javax.swing.JTextPane();
		btOptions = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("storybook/msg/messages"); // NOI18N
		setTitle(bundle.getString("msg.dlg.export.title")); // NOI18N
		setResizable(false);

		jLabel1.setText(bundle.getString("msg.dlg.export.folder")); // NOI18N

		btFolder.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/open.png"))); // NOI18N
		btFolder.setText(bundle.getString("msg.common.choose.folder")); // NOI18N
		btFolder.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btFolderActionPerformed(evt);
			}
		});

		jLabel2.setText(bundle.getString("msg.dlg.export.type")); // NOI18N

		cbReport.setMaximumRowCount(12);
		cbReport.addItemListener(new java.awt.event.ItemListener() {
			@Override
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				cbReportItemStateChanged(evt);
			}
		});
		cbReport.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cbReportActionPerformed(evt);
			}
		});

		jLabel3.setText(bundle.getString("msg.dlg.export.format")); // NOI18N

		cbFormat.setSelectedItem("pdf");
		cbFormat.addItemListener(new java.awt.event.ItemListener() {
			@Override
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				cbFormatItemStateChanged(evt);
			}
		});

		btExport.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/export.png"))); // NOI18N
		btExport.setText(bundle.getString("msg.common.export")); // NOI18N
		btExport.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btExportActionPerformed(evt);
			}
		});

		btClose.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/close.png"))); // NOI18N
		btClose.setText(bundle.getString("msg.common.cancel")); // NOI18N
		btClose.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btCloseActionPerformed(evt);
			}
		});

		jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("msg.common.options"))); // NOI18N

		txOptions.setContentType("text/html"); // NOI18N
		jScrollPane2.setViewportView(txOptions);

		btOptions.setText(bundle.getString("msg.common.change")); // NOI18N
		btOptions.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btOptionsActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
								.addComponent(btOptions, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addContainerGap()));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup()
						.addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(btOptions)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								layout.createSequentialGroup().addContainerGap()
										.addGroup(
												layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addGroup(layout.createSequentialGroup().addComponent(jLabel1)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(txFolder)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(btFolder))
						.addGroup(layout.createSequentialGroup().addComponent(jLabel2)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(cbReport, javax.swing.GroupLayout.PREFERRED_SIZE, 246,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jLabel3)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(cbFormat, javax.swing.GroupLayout.PREFERRED_SIZE, 76,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(0, 46, Short.MAX_VALUE))
						.addGroup(layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE)
								.addComponent(btClose, javax.swing.GroupLayout.PREFERRED_SIZE, 181,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(btExport, javax.swing.GroupLayout.PREFERRED_SIZE, 198,
										javax.swing.GroupLayout.PREFERRED_SIZE))).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
								.addComponent(txFolder, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jLabel1).addComponent(btFolder))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel2)
						.addComponent(cbReport, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jLabel3).addComponent(cbFormat, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(btExport)
						.addComponent(btClose)).addContainerGap()));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	@SuppressWarnings("unchecked")
	private void initUI() {
		paramExport = new ParamExport(mainFrame);
		paramExport.load();
		Internal internal = BookUtil.get(mainFrame, SbConstants.BookKey.EXPORT_DIRECTORY,
				EnvUtil.getDefaultExportDir(mainFrame));
		txFolder.setText(internal.getStringValue());
		if (txFolder.getText().isEmpty())
			txFolder.setText(FileUtils.getUserDirectoryPath());
		cbReport.removeAllItems();
		for (ExportData export : exports) {
			cbReport.addItem(export);
		}
		cbReport.setSelectedIndex(0);
		cbFormat.removeAllItems();
		for (String zformat : formats) {
			cbFormat.addItem(zformat);
		}
		cbFormat.setSelectedItem("html");
	}

	private void setOptions(String f) {
		String str = "<html><body>";
		switch (f) {
		case "pdf":
			str += getOptionsPdf();
			break;
		case "html":
			str += getOptionsHtml();
			break;
		case "csv":
			str += getOptionsCsv();
			break;
		case "txt":
			str += getOptionsTxt();
			break;
		case "odf":
			str += getOptionsOdf();
			break;
		case "xml":
			str += getOptionsXml();
			break;
		}
		str += "</body></html>";
		txOptions.setText(str);
	}

}
