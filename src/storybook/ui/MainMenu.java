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
package storybook.ui;

import javax.swing.Action;
import javax.swing.JOptionPane;

import com.sun.jaf.ui.ActionManager;

import storybook.SbApp;
import storybook.SbConstants;
import storybook.SbConstants.ViewName;
import storybook.exporter.BookExporter;
import storybook.exporter.DlgExport;
import storybook.model.hbn.entity.AbstractEntity;
import storybook.model.hbn.entity.Category;
import storybook.model.hbn.entity.Chapter;
import storybook.model.hbn.entity.Gender;
import storybook.model.hbn.entity.Idea;
import storybook.model.hbn.entity.Internal;
import storybook.model.hbn.entity.Item;
import storybook.model.hbn.entity.ItemLink;
import storybook.model.hbn.entity.Location;
import storybook.model.hbn.entity.Memo;
import storybook.model.hbn.entity.Part;
import storybook.model.hbn.entity.Person;
import storybook.model.hbn.entity.Relationship;
import storybook.model.hbn.entity.Scene;
import storybook.model.hbn.entity.Strand;
import storybook.model.hbn.entity.Tag;
import storybook.model.hbn.entity.TagLink;
import storybook.toolkit.BookUtil;
import storybook.toolkit.DockingWindowUtil;
import storybook.toolkit.I18N;
import storybook.toolkit.TextTransfer;
import storybook.toolkit.net.NetUtil;
import storybook.toolkit.net.Updater;
import storybook.toolkit.swing.SwingUtil;
import storybook.ui.dialog.AboutDialog;
import storybook.ui.dialog.BookPropertiesDialog;
import storybook.ui.dialog.ChaptersOrderDialog;
import storybook.ui.dialog.CreateChaptersDialog;
import storybook.ui.dialog.FoiDialog;
import storybook.ui.dialog.ManageLayoutsDialog;
import storybook.ui.dialog.PreferencesDialog;
import storybook.ui.dialog.copy.IdeaCopier;
import storybook.ui.dialog.copy.ItemCopier;
import storybook.ui.dialog.copy.LocationCopier;
import storybook.ui.dialog.copy.PersonCopier;
import storybook.ui.dialog.rename.RenameCityDialog;
import storybook.ui.dialog.rename.RenameCountryDialog;
import storybook.ui.dialog.rename.RenameItemCategoryDialog;
import storybook.ui.dialog.rename.RenameTagCategoryDialog;

/**
 *
 * @author favdb
 */
@SuppressWarnings("serial")
public class MainMenu extends javax.swing.JFrame {

	private MainFrame mainFrame;

	/**
	 * @param args
	 *            the command line arguments
	 */
	// public static void main(String args[]) {
	// /* Set the Nimbus look and feel */
	// //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code
	// (optional) ">
	// /* If Nimbus (introduced in Java SE 6) is not available, stay with the
	// default look and feel.
	// * For details see
	// http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
	// */
	// try {
	// for (javax.swing.UIManager.LookAndFeelInfo info :
	// javax.swing.UIManager.getInstalledLookAndFeels()) {
	// if ("Nimbus".equals(info.getName())) {
	// javax.swing.UIManager.setLookAndFeel(info.getClassName());
	// break;
	// }
	// }
	// } catch (ClassNotFoundException ex) {
	// java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE,
	// null, ex);
	// } catch (InstantiationException ex) {
	// java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE,
	// null, ex);
	// } catch (IllegalAccessException ex) {
	// java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE,
	// null, ex);
	// } catch (javax.swing.UnsupportedLookAndFeelException ex) {
	// java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE,
	// null, ex);
	// }
	// //</editor-fold>
	//
	// /* Create and display the form */
	// java.awt.EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// new MainMenu().setVisible(true);
	// }
	// });
	// }

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btFileNew;

	private javax.swing.JButton btFileOpen;

	private javax.swing.JButton btFileSave;

	private javax.swing.JButton btIdea;

	private javax.swing.JButton btManageScene;

	private javax.swing.JButton btNewChapter;

	private javax.swing.JButton btNewItem;

	private javax.swing.JButton btNewLocation;

	private javax.swing.JButton btNewPerson;

	private javax.swing.JButton btNewScene;

	private javax.swing.JButton btNewTag;

	private javax.swing.JButton btNextPart;

	private javax.swing.JButton btPreviousPart;

	private javax.swing.JButton btTabChapter;

	private javax.swing.JButton btTabItem;

	private javax.swing.JButton btTabItemLink;

	private javax.swing.JButton btTabLocation;

	private javax.swing.JButton btTabPerson;

	private javax.swing.JButton btTabRelationship;

	private javax.swing.JButton btTabScene;

	private javax.swing.JButton btTabTag;

	private javax.swing.JButton btTabTagLink;

	private javax.swing.JButton btViewBook;

	private javax.swing.JButton btViewChrono;

	private javax.swing.JButton btViewMemoria;

	private javax.swing.JButton btViewReading;

	private javax.swing.JMenuItem chartGantt;

	private javax.swing.JMenuItem chartOccurrenceOfLocations;

	private javax.swing.JMenuItem chartOccurrenceOfPersons;

	private javax.swing.JMenuItem chartPersonsByDate;

	private javax.swing.JMenuItem chartPersonsByScene;

	private javax.swing.JMenuItem chartStrandsByDate;

	private javax.swing.JMenuItem chartWIWW;

	private javax.swing.JMenuItem chartsAttributes;

	private javax.swing.JMenuItem devTest;

	private javax.swing.JMenuItem editCopyBlurb;

	private javax.swing.JMenuItem editCopyBook;

	private javax.swing.JMenuItem editCopyIdeas;

	private javax.swing.JMenuItem editCopyItems;

	private javax.swing.JMenuItem editCopyLocations;

	private javax.swing.JMenuItem editCopyPersons;

	private javax.swing.JMenuItem fileClose;

	private javax.swing.JMenuItem fileExit;

	private javax.swing.JMenuItem fileExport;

	private javax.swing.JMenuItem fileImport;

	private javax.swing.JMenuItem fileNew;

	private javax.swing.JMenuItem fileOpen;

	public javax.swing.JMenu fileOpenRecent;

	private javax.swing.JMenuItem filePrint;

	private javax.swing.JMenuItem fileProperties;

	private javax.swing.JMenuItem fileRename;

	private javax.swing.JMenuItem fileSave;

	private javax.swing.JMenuItem fileSaveAs;

	private javax.swing.JMenuItem helpAbout;

	private javax.swing.JMenuItem helpCheckUpdates;

	private javax.swing.JMenuItem helpDoc;

	private javax.swing.JMenuItem helpFaq;

	private javax.swing.JMenuItem helpHome;

	private javax.swing.JMenuItem helpReportBug;

	private javax.swing.JCheckBoxMenuItem helpTrace;

	private javax.swing.JMenuItem jChaptersOrder;

	private javax.swing.JMenu jMenu1;

	private javax.swing.JMenuItem jMenuItem1;

	private javax.swing.JPopupMenu.Separator jSeparator1;

	private javax.swing.JPopupMenu.Separator jSeparator10;

	private javax.swing.JPopupMenu.Separator jSeparator11;

	private javax.swing.JPopupMenu.Separator jSeparator12;

	private javax.swing.JPopupMenu.Separator jSeparator13;

	private javax.swing.JPopupMenu.Separator jSeparator15;

	private javax.swing.JPopupMenu.Separator jSeparator16;

	private javax.swing.JPopupMenu.Separator jSeparator18;

	private javax.swing.JToolBar.Separator jSeparator19;

	private javax.swing.JToolBar.Separator jSeparator20;

	private javax.swing.JToolBar.Separator jSeparator21;

	private javax.swing.JToolBar.Separator jSeparator22;

	private javax.swing.JToolBar.Separator jSeparator23;

	private javax.swing.JPopupMenu.Separator jSeparator4;

	private javax.swing.JPopupMenu.Separator jSeparator5;

	private javax.swing.JPopupMenu.Separator jSeparator6;

	private javax.swing.JPopupMenu.Separator jSeparator7;

	private javax.swing.JPopupMenu.Separator jSeparator8;

	private javax.swing.JPopupMenu.Separator jSeparator9;

	public javax.swing.JMenuBar menuBar;

	private javax.swing.JMenu menuCharts;

	private javax.swing.JMenu menuEdit;

	private javax.swing.JMenu menuFile;

	private javax.swing.JMenu menuHelp;

	private javax.swing.JMenu menuNewEntity;

	public javax.swing.JMenu menuParts;

	private javax.swing.JMenu menuPrimaryObjects;

	private javax.swing.JMenu menuSecondaryObjects;

	private javax.swing.JMenu menuView;

	private javax.swing.JMenu menuWindow;

	private javax.swing.JMenuItem newCategory;

	private javax.swing.JMenuItem newChapter;

	private javax.swing.JMenuItem newChapters;

	private javax.swing.JMenuItem newFOI;

	private javax.swing.JMenuItem newGender;

	private javax.swing.JMenuItem newIdea;

	private javax.swing.JMenuItem newItem;

	private javax.swing.JMenuItem newItemLink;

	private javax.swing.JMenuItem newLocation;

	private javax.swing.JMenuItem newMemo;

	private javax.swing.JMenuItem newPart;

	private javax.swing.JMenuItem newPerson;

	private javax.swing.JMenuItem newRelationships;

	private javax.swing.JMenuItem newScene;

	private javax.swing.JMenuItem newStrand;

	private javax.swing.JMenuItem newTag;

	private javax.swing.JMenuItem newTagLink;

	public javax.swing.JMenuItem partNext;

	public javax.swing.JMenuItem partPrevious;

	private javax.swing.JMenuItem renameCity;

	private javax.swing.JMenuItem renameCountry;

	private javax.swing.JMenuItem renameItemCategory;

	private javax.swing.JMenuItem renameTagCategory;

	private javax.swing.JPopupMenu.Separator separatorFile1;

	private javax.swing.JPopupMenu.Separator separatorFile2;

	private javax.swing.JPopupMenu.Separator separatorFile3;

	private javax.swing.JMenuItem tabCategory;

	private javax.swing.JMenuItem tabChapter;

	private javax.swing.JMenuItem tabGender;

	private javax.swing.JMenuItem tabIdea;

	private javax.swing.JMenuItem tabItem;

	private javax.swing.JMenuItem tabItemLink;

	private javax.swing.JMenuItem tabLocation;

	private javax.swing.JMenuItem tabPart;

	private javax.swing.JMenuItem tabPerson;

	private javax.swing.JMenuItem tabRelationship;
	private javax.swing.JMenuItem tabScene;
	private javax.swing.JMenuItem tabStrand;
	private javax.swing.JMenuItem tabTag;
	private javax.swing.JMenuItem tabTagLink;
	public javax.swing.JToolBar toolBar;
	private javax.swing.JMenuItem toolsPlan;
	private javax.swing.JMenuItem toolsTaskList;
	private javax.swing.JMenuItem vueBook;
	private javax.swing.JMenuItem vueChrono;
	private javax.swing.JMenuItem vueEditor;
	private javax.swing.JMenuItem vueInfo;
	private javax.swing.JMenuItem vueInfo1;
	private javax.swing.JMenuItem vueManageScene;
	private javax.swing.JMenuItem vueMemoria;
	private javax.swing.JMenuItem vueNavigation;
	private javax.swing.JMenuItem vueReading;
	private javax.swing.JMenuItem vueTree;
	private javax.swing.JMenuItem windowBook;
	private javax.swing.JMenuItem windowChrono;
	private javax.swing.JMenuItem windowDefaultLayout;
	public javax.swing.JMenu windowLoadLayout;
	private javax.swing.JMenuItem windowManage;
	private javax.swing.JMenuItem windowManageLayouts;
	private javax.swing.JMenuItem windowPersonsAndLocations;
	private javax.swing.JMenuItem windowPreferences;
	private javax.swing.JMenuItem windowReading;
	private javax.swing.JMenuItem windowRefresh;
	private javax.swing.JMenuItem windowResetLayout;
	private javax.swing.JMenuItem windowSaveLayout;
	private javax.swing.JMenuItem windowTagsAndItems;
	// End of variables declaration//GEN-END:variables
	/**
	 * Creates new form MainMenu
	 */
	public MainMenu() {
		initComponents();
	}
	public MainMenu(MainFrame main) {
		initComponents();
		mainFrame = main;
		if (SbApp.getI18nFile() != null)
			translateMenu();
		helpTrace.setSelected(SbApp.getTrace());
		if (SbApp.isDevTest() == false)
			devTest.setVisible(false);
	}
	private void btFileNewActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btFileNewActionPerformed
		this.fileNewActionPerformed(evt);
	}// GEN-LAST:event_btFileNewActionPerformed
	private void btFileOpenActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btFileOpenActionPerformed
		this.fileOpenActionPerformed(evt);
	}// GEN-LAST:event_btFileOpenActionPerformed
	private void btFileSaveActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btFileSaveActionPerformed
		this.fileSaveActionPerformed(evt);
	}// GEN-LAST:event_btFileSaveActionPerformed
	private void btIdeaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btIdeaActionPerformed
		this.newIdeaActionPerformed(evt);
	}// GEN-LAST:event_btIdeaActionPerformed
	private void btManageSceneActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btManageSceneActionPerformed
		this.vueManageSceneActionPerformed(evt);
	}// GEN-LAST:event_btManageSceneActionPerformed
	private void btNewChapterActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btNewChapterActionPerformed
		this.newChapterActionPerformed(evt);
	}// GEN-LAST:event_btNewChapterActionPerformed
	private void btNewItemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btNewItemActionPerformed
		this.newItemActionPerformed(evt);
	}// GEN-LAST:event_btNewItemActionPerformed
	private void btNewLocationActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btNewLocationActionPerformed
		this.newLocationActionPerformed(evt);
	}// GEN-LAST:event_btNewLocationActionPerformed
	private void btNewPersonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btNewPersonActionPerformed
		this.newPersonActionPerformed(evt);
	}// GEN-LAST:event_btNewPersonActionPerformed
	private void btNewSceneActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btNewSceneActionPerformed
		this.newSceneActionPerformed(evt);
	}// GEN-LAST:event_btNewSceneActionPerformed
	private void btNewTagActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btNewTagActionPerformed
		this.newTagActionPerformed(evt);
	}// GEN-LAST:event_btNewTagActionPerformed
	private void btNextPartActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btNextPartActionPerformed
		this.partNextActionPerformed(evt);
	}// GEN-LAST:event_btNextPartActionPerformed
	private void btPreviousPartActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btPreviousPartActionPerformed
		this.partPreviousActionPerformed(evt);
	}// GEN-LAST:event_btPreviousPartActionPerformed
	private void btTabChapterActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btTabChapterActionPerformed
		this.tabChapterActionPerformed(evt);
	}// GEN-LAST:event_btTabChapterActionPerformed
	private void btTabItemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btTabItemActionPerformed
		this.tabItemActionPerformed(evt);
	}// GEN-LAST:event_btTabItemActionPerformed
	private void btTabItemLinkActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btTabItemLinkActionPerformed
		this.tabItemLinkActionPerformed(evt);
	}// GEN-LAST:event_btTabItemLinkActionPerformed
	private void btTabLocationActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btTabLocationActionPerformed
		this.tabLocationActionPerformed(evt);
	}// GEN-LAST:event_btTabLocationActionPerformed
	private void btTabPersonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btTabPersonActionPerformed
		this.tabPersonActionPerformed(evt);
	}// GEN-LAST:event_btTabPersonActionPerformed
	private void btTabRelationshipActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btTabRelationshipActionPerformed
		mainFrame.showAndFocus(ViewName.RELATIONSHIPS);
	}// GEN-LAST:event_btTabRelationshipActionPerformed
	private void btTabSceneActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btTabSceneActionPerformed
		this.tabSceneActionPerformed(evt);
	}// GEN-LAST:event_btTabSceneActionPerformed
	private void btTabTagActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btTabTagActionPerformed
		this.tabTagActionPerformed(evt);
	}// GEN-LAST:event_btTabTagActionPerformed
	private void btTabTagLinkActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btTabTagLinkActionPerformed
		this.tabTagLinkActionPerformed(evt);
	}// GEN-LAST:event_btTabTagLinkActionPerformed
	private void btViewBookActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btViewBookActionPerformed
		this.vueBookActionPerformed(evt);
	}// GEN-LAST:event_btViewBookActionPerformed
	private void btViewChronoActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btViewChronoActionPerformed
		this.vueChronoActionPerformed(evt);
	}// GEN-LAST:event_btViewChronoActionPerformed
	private void btViewMemoriaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btViewMemoriaActionPerformed
		this.vueMemoriaActionPerformed(evt);
	}// GEN-LAST:event_btViewMemoriaActionPerformed
	private void btViewReadingActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btViewReadingActionPerformed
		this.vueReadingActionPerformed(evt);
	}// GEN-LAST:event_btViewReadingActionPerformed
	private void chartGanttActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_chartGanttActionPerformed
		mainFrame.showAndFocus(ViewName.CHART_GANTT);
	}// GEN-LAST:event_chartGanttActionPerformed
	private void chartOccurrenceOfLocationsActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_chartOccurrenceOfLocationsActionPerformed
		mainFrame.showAndFocus(ViewName.CHART_OCCURRENCE_OF_LOCATIONS);
	}// GEN-LAST:event_chartOccurrenceOfLocationsActionPerformed
	private void chartOccurrenceOfPersonsActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_chartOccurrenceOfPersonsActionPerformed
		mainFrame.showAndFocus(ViewName.CHART_OCCURRENCE_OF_PERSONS);
	}// GEN-LAST:event_chartOccurrenceOfPersonsActionPerformed
	private void chartPersonsByDateActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_chartPersonsByDateActionPerformed
		mainFrame.showAndFocus(ViewName.CHART_PERSONS_BY_DATE);
	}// GEN-LAST:event_chartPersonsByDateActionPerformed
	private void chartPersonsBySceneActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_chartPersonsBySceneActionPerformed
		mainFrame.showAndFocus(ViewName.CHART_PERSONS_BY_SCENE);
	}// GEN-LAST:event_chartPersonsBySceneActionPerformed
	private void chartsAttributesActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_chartsAttributesActionPerformed
		mainFrame.showAndFocus(ViewName.ATTRIBUTES);
	}// GEN-LAST:event_chartsAttributesActionPerformed
	private void chartStrandsByDateActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_chartStrandsByDateActionPerformed
		mainFrame.showAndFocus(ViewName.CHART_STRANDS_BY_DATE);
	}// GEN-LAST:event_chartStrandsByDateActionPerformed
	private void chartWIWWActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_chartWIWWActionPerformed
		mainFrame.showAndFocus(ViewName.CHART_WiWW);
	}// GEN-LAST:event_chartWIWWActionPerformed
	private void devTestActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_devTestActionPerformed
		// add your code to test here, don't delete this line

		// end of your adding code, don't delete this line
	}// GEN-LAST:event_devTestActionPerformed
	private void editCopyBlurbActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_editCopyBlurbActionPerformed
		Internal internal = BookUtil.get(mainFrame, SbConstants.BookKey.BLURB, "");
		TextTransfer tf = new TextTransfer();
		tf.setClipboardContents(internal.getStringValue() + "\n");
	}// GEN-LAST:event_editCopyBlurbActionPerformed
	private void editCopyBookActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_editCopyBookActionPerformed
		BookExporter exp = new BookExporter(mainFrame);
		exp.setExportForOpenOffice(false);
		exp.exportToClipboard();
	}// GEN-LAST:event_editCopyBookActionPerformed
	private void editCopyIdeasActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_editCopyIdeasActionPerformed
		IdeaCopier copier = new IdeaCopier(mainFrame);
		copier.showDialog();
	}// GEN-LAST:event_editCopyIdeasActionPerformed
	private void editCopyItemsActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_editCopyItemsActionPerformed
		ItemCopier copier = new ItemCopier(mainFrame);
		copier.showDialog();
	}// GEN-LAST:event_editCopyItemsActionPerformed
	private void editCopyLocationsActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_editCopyLocationsActionPerformed
		LocationCopier copier = new LocationCopier(mainFrame);
		copier.showDialog();
	}// GEN-LAST:event_editCopyLocationsActionPerformed
	private void editCopyPersonsActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_editCopyPersonsActionPerformed
		PersonCopier copier = new PersonCopier(mainFrame);
		copier.showDialog();
	}// GEN-LAST:event_editCopyPersonsActionPerformed
	private void fileCloseActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_fileCloseActionPerformed
		mainFrame.close(true);
	}// GEN-LAST:event_fileCloseActionPerformed
	private void fileExitActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_fileExitActionPerformed
		SbApp.getInstance().exit();
	}// GEN-LAST:event_fileExitActionPerformed
	private void fileExportActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_fileExportActionPerformed
		DlgExport export = new DlgExport(mainFrame);
		export.setVisible(true);

	}// GEN-LAST:event_fileExportActionPerformed
	private void fileExportBookActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_fileExportBookActionPerformed
		mainFrame.getSbActionManager().getActionHandler().handleFileExportBook();
	}// GEN-LAST:event_fileExportBookActionPerformed
	private void fileImportActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_fileImportActionPerformed
		JOptionPane.showMessageDialog(null, I18N.getMsg("msg.common.not.available"), "Import...",
				JOptionPane.ERROR_MESSAGE);
		/*
		 * DlgImport dlg = new DlgImport(mainFrame);
		 * SwingUtil.showModalDialog(dlg, mainFrame);
		 */
	}// GEN-LAST:event_fileImportActionPerformed
	private void fileNewActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_fileNewActionPerformed
		SbApp.getInstance().createNewFile();
	}// GEN-LAST:event_fileNewActionPerformed
	private void fileOpenActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_fileOpenActionPerformed
		mainFrame.setWaitingCursor();
		SbApp.getInstance().openFile();
		mainFrame.setDefaultCursor();
	}// GEN-LAST:event_fileOpenActionPerformed
	private void filePrintActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_filePrintActionPerformed
		// TODO filePrint
	}// GEN-LAST:event_filePrintActionPerformed
	private void filePropertiesActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_filePropertiesActionPerformed
		BookPropertiesDialog dlg = new BookPropertiesDialog(mainFrame);
		SwingUtil.showModalDialog(dlg, mainFrame);
	}// GEN-LAST:event_filePropertiesActionPerformed
	private void fileRenameActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_fileRenameActionPerformed
		mainFrame.getSbActionManager().getActionHandler().handleFileRename();
	}// GEN-LAST:event_fileRenameActionPerformed
	private void fileSaveActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_fileSaveActionPerformed
		mainFrame.getSbActionManager().getActionHandler().handleFileSave();
	}// GEN-LAST:event_fileSaveActionPerformed
	private void fileSaveAsActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_fileSaveAsActionPerformed
		mainFrame.getSbActionManager().getActionHandler().handleFileSaveAs();
	}// GEN-LAST:event_fileSaveAsActionPerformed
	private void helpAboutActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_helpAboutActionPerformed
		AboutDialog dlg = new AboutDialog(mainFrame);
		SwingUtil.showModalDialog(dlg, mainFrame);
	}// GEN-LAST:event_helpAboutActionPerformed
	private void helpCheckUpdatesActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_helpCheckUpdatesActionPerformed
		if (Updater.checkForUpdate()) {
			JOptionPane.showMessageDialog(mainFrame, I18N.getMsg("msg.update.no.text"),
					I18N.getMsg("msg.update.no.title"), JOptionPane.INFORMATION_MESSAGE);
		}
	}// GEN-LAST:event_helpCheckUpdatesActionPerformed
	private void helpDocActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_helpDocActionPerformed
		NetUtil.openBrowser(SbConstants.URL.DOC.toString());
	}// GEN-LAST:event_helpDocActionPerformed
	private void helpFaqActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_helpFaqActionPerformed
		NetUtil.openBrowser(SbConstants.URL.FAQ.toString());
	}// GEN-LAST:event_helpFaqActionPerformed
	private void helpHomeActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_helpHomeActionPerformed
		NetUtil.openBrowser(SbConstants.URL.HOMEPAGE.toString());
	}// GEN-LAST:event_helpHomeActionPerformed
	private void helpReportBugActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_helpReportBugActionPerformed
		NetUtil.openBrowser(SbConstants.URL.REPORTBUG.toString());
	}// GEN-LAST:event_helpReportBugActionPerformed
	private void helpTraceActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_helpTraceActionPerformed
		if (SbApp.getTrace()) {
			SbApp.setTrace(false);
		} else {
			SbApp.setTrace(true);
		}
		helpTrace.setSelected(SbApp.getTrace());
	}// GEN-LAST:event_helpTraceActionPerformed
	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jMenuItem1 = new javax.swing.JMenuItem();
		toolBar = new javax.swing.JToolBar();
		btFileNew = new javax.swing.JButton();
		btFileOpen = new javax.swing.JButton();
		btFileSave = new javax.swing.JButton();
		jSeparator19 = new javax.swing.JToolBar.Separator();
		btNewScene = new javax.swing.JButton();
		btNewChapter = new javax.swing.JButton();
		btNewPerson = new javax.swing.JButton();
		btNewLocation = new javax.swing.JButton();
		btNewItem = new javax.swing.JButton();
		btNewTag = new javax.swing.JButton();
		jSeparator20 = new javax.swing.JToolBar.Separator();
		btTabScene = new javax.swing.JButton();
		btTabChapter = new javax.swing.JButton();
		btTabPerson = new javax.swing.JButton();
		btTabRelationship = new javax.swing.JButton();
		btTabLocation = new javax.swing.JButton();
		btTabItem = new javax.swing.JButton();
		btTabItemLink = new javax.swing.JButton();
		btTabTag = new javax.swing.JButton();
		btTabTagLink = new javax.swing.JButton();
		jSeparator21 = new javax.swing.JToolBar.Separator();
		btViewChrono = new javax.swing.JButton();
		btViewBook = new javax.swing.JButton();
		btManageScene = new javax.swing.JButton();
		btViewReading = new javax.swing.JButton();
		btViewMemoria = new javax.swing.JButton();
		jSeparator22 = new javax.swing.JToolBar.Separator();
		btPreviousPart = new javax.swing.JButton();
		btNextPart = new javax.swing.JButton();
		jSeparator23 = new javax.swing.JToolBar.Separator();
		btIdea = new javax.swing.JButton();
		menuBar = new javax.swing.JMenuBar();
		menuFile = new javax.swing.JMenu();
		fileNew = new javax.swing.JMenuItem();
		fileOpen = new javax.swing.JMenuItem();
		fileOpenRecent = new javax.swing.JMenu();
		fileSave = new javax.swing.JMenuItem();
		fileSaveAs = new javax.swing.JMenuItem();
		fileRename = new javax.swing.JMenuItem();
		fileClose = new javax.swing.JMenuItem();
		separatorFile1 = new javax.swing.JPopupMenu.Separator();
		fileProperties = new javax.swing.JMenuItem();
		separatorFile2 = new javax.swing.JPopupMenu.Separator();
		fileImport = new javax.swing.JMenuItem();
		fileExport = new javax.swing.JMenuItem();
		filePrint = new javax.swing.JMenuItem();
		separatorFile3 = new javax.swing.JPopupMenu.Separator();
		fileExit = new javax.swing.JMenuItem();
		menuEdit = new javax.swing.JMenu();
		editCopyBook = new javax.swing.JMenuItem();
		editCopyBlurb = new javax.swing.JMenuItem();
		jMenu1 = new javax.swing.JMenu();
		editCopyPersons = new javax.swing.JMenuItem();
		editCopyLocations = new javax.swing.JMenuItem();
		editCopyItems = new javax.swing.JMenuItem();
		editCopyIdeas = new javax.swing.JMenuItem();
		jSeparator1 = new javax.swing.JPopupMenu.Separator();
		windowPreferences = new javax.swing.JMenuItem();
		menuNewEntity = new javax.swing.JMenu();
		newScene = new javax.swing.JMenuItem();
		newChapter = new javax.swing.JMenuItem();
		newChapters = new javax.swing.JMenuItem();
		newPart = new javax.swing.JMenuItem();
		newStrand = new javax.swing.JMenuItem();
		jSeparator4 = new javax.swing.JPopupMenu.Separator();
		newPerson = new javax.swing.JMenuItem();
		newRelationships = new javax.swing.JMenuItem();
		newCategory = new javax.swing.JMenuItem();
		newGender = new javax.swing.JMenuItem();
		jSeparator5 = new javax.swing.JPopupMenu.Separator();
		newLocation = new javax.swing.JMenuItem();
		jSeparator6 = new javax.swing.JPopupMenu.Separator();
		newTag = new javax.swing.JMenuItem();
		newTagLink = new javax.swing.JMenuItem();
		jSeparator7 = new javax.swing.JPopupMenu.Separator();
		newItem = new javax.swing.JMenuItem();
		newItemLink = new javax.swing.JMenuItem();
		jSeparator8 = new javax.swing.JPopupMenu.Separator();
		newFOI = new javax.swing.JMenuItem();
		newIdea = new javax.swing.JMenuItem();
		newMemo = new javax.swing.JMenuItem();
		menuPrimaryObjects = new javax.swing.JMenu();
		tabScene = new javax.swing.JMenuItem();
		tabChapter = new javax.swing.JMenuItem();
		jChaptersOrder = new javax.swing.JMenuItem();
		tabPart = new javax.swing.JMenuItem();
		tabStrand = new javax.swing.JMenuItem();
		jSeparator9 = new javax.swing.JPopupMenu.Separator();
		tabPerson = new javax.swing.JMenuItem();
		tabRelationship = new javax.swing.JMenuItem();
		tabCategory = new javax.swing.JMenuItem();
		tabGender = new javax.swing.JMenuItem();
		jSeparator10 = new javax.swing.JPopupMenu.Separator();
		tabLocation = new javax.swing.JMenuItem();
		renameCity = new javax.swing.JMenuItem();
		renameCountry = new javax.swing.JMenuItem();
		menuSecondaryObjects = new javax.swing.JMenu();
		tabTag = new javax.swing.JMenuItem();
		tabTagLink = new javax.swing.JMenuItem();
		renameTagCategory = new javax.swing.JMenuItem();
		jSeparator11 = new javax.swing.JPopupMenu.Separator();
		tabItem = new javax.swing.JMenuItem();
		tabItemLink = new javax.swing.JMenuItem();
		renameItemCategory = new javax.swing.JMenuItem();
		tabIdea = new javax.swing.JMenuItem();
		menuView = new javax.swing.JMenu();
		vueChrono = new javax.swing.JMenuItem();
		vueBook = new javax.swing.JMenuItem();
		vueReading = new javax.swing.JMenuItem();
		vueManageScene = new javax.swing.JMenuItem();
		vueMemoria = new javax.swing.JMenuItem();
		jSeparator12 = new javax.swing.JPopupMenu.Separator();
		vueEditor = new javax.swing.JMenuItem();
		toolsTaskList = new javax.swing.JMenuItem();
		toolsPlan = new javax.swing.JMenuItem();
		vueTree = new javax.swing.JMenuItem();
		vueInfo = new javax.swing.JMenuItem();
		vueNavigation = new javax.swing.JMenuItem();
		vueInfo1 = new javax.swing.JMenuItem();
		menuParts = new javax.swing.JMenu();
		partPrevious = new javax.swing.JMenuItem();
		partNext = new javax.swing.JMenuItem();
		menuCharts = new javax.swing.JMenu();
		chartsAttributes = new javax.swing.JMenuItem();
		chartPersonsByDate = new javax.swing.JMenuItem();
		chartPersonsByScene = new javax.swing.JMenuItem();
		chartWIWW = new javax.swing.JMenuItem();
		chartStrandsByDate = new javax.swing.JMenuItem();
		jSeparator13 = new javax.swing.JPopupMenu.Separator();
		chartOccurrenceOfPersons = new javax.swing.JMenuItem();
		chartOccurrenceOfLocations = new javax.swing.JMenuItem();
		chartGantt = new javax.swing.JMenuItem();
		menuWindow = new javax.swing.JMenu();
		windowLoadLayout = new javax.swing.JMenu();
		windowSaveLayout = new javax.swing.JMenuItem();
		windowDefaultLayout = new javax.swing.JMenuItem();
		windowManageLayouts = new javax.swing.JMenuItem();
		jSeparator15 = new javax.swing.JPopupMenu.Separator();
		windowPersonsAndLocations = new javax.swing.JMenuItem();
		windowTagsAndItems = new javax.swing.JMenuItem();
		windowChrono = new javax.swing.JMenuItem();
		windowBook = new javax.swing.JMenuItem();
		windowManage = new javax.swing.JMenuItem();
		windowReading = new javax.swing.JMenuItem();
		jSeparator16 = new javax.swing.JPopupMenu.Separator();
		windowResetLayout = new javax.swing.JMenuItem();
		windowRefresh = new javax.swing.JMenuItem();
		menuHelp = new javax.swing.JMenu();
		helpDoc = new javax.swing.JMenuItem();
		helpFaq = new javax.swing.JMenuItem();
		helpHome = new javax.swing.JMenuItem();
		helpReportBug = new javax.swing.JMenuItem();
		helpAbout = new javax.swing.JMenuItem();
		jSeparator18 = new javax.swing.JPopupMenu.Separator();
		helpCheckUpdates = new javax.swing.JMenuItem();
		helpTrace = new javax.swing.JCheckBoxMenuItem();
		devTest = new javax.swing.JMenuItem();

		jMenuItem1.setText("jMenuItem1");

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		toolBar.setFloatable(false);
		toolBar.setRollover(true);
		toolBar.setName("MainToolbar"); // NOI18N

		btFileNew.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/file-new.png"))); // NOI18N
		java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("storybook/msg/messages"); // NOI18N
		btFileNew.setToolTipText(bundle.getString("msg.file.new")); // NOI18N
		btFileNew.setFocusable(false);
		btFileNew.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btFileNew.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btFileNew.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btFileNewActionPerformed(evt);
			}
		});
		toolBar.add(btFileNew);

		btFileOpen.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/file-open.png"))); // NOI18N
		btFileOpen.setToolTipText(bundle.getString("msg.file.open")); // NOI18N
		btFileOpen.setFocusable(false);
		btFileOpen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btFileOpen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btFileOpen.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btFileOpenActionPerformed(evt);
			}
		});
		toolBar.add(btFileOpen);

		btFileSave.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/file-save.png"))); // NOI18N
		btFileSave.setToolTipText(bundle.getString("msg.file.save")); // NOI18N
		btFileSave.setFocusable(false);
		btFileSave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btFileSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btFileSave.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btFileSaveActionPerformed(evt);
			}
		});
		toolBar.add(btFileSave);
		toolBar.add(jSeparator19);

		btNewScene.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/scene.png"))); // NOI18N
		btNewScene.setToolTipText(bundle.getString("msg.common.scene")); // NOI18N
		btNewScene.setFocusable(false);
		btNewScene.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btNewScene.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btNewScene.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btNewSceneActionPerformed(evt);
			}
		});
		toolBar.add(btNewScene);

		btNewChapter.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/chapter.png"))); // NOI18N
		btNewChapter.setToolTipText(bundle.getString("msg.common.chapter")); // NOI18N
		btNewChapter.setFocusable(false);
		btNewChapter.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btNewChapter.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btNewChapter.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btNewChapterActionPerformed(evt);
			}
		});
		toolBar.add(btNewChapter);

		btNewPerson.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/person.png"))); // NOI18N
		btNewPerson.setToolTipText(bundle.getString("msg.common.person")); // NOI18N
		btNewPerson.setFocusable(false);
		btNewPerson.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btNewPerson.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btNewPerson.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btNewPersonActionPerformed(evt);
			}
		});
		toolBar.add(btNewPerson);

		btNewLocation.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/location.png"))); // NOI18N
		btNewLocation.setToolTipText(bundle.getString("msg.common.location")); // NOI18N
		btNewLocation.setFocusable(false);
		btNewLocation.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btNewLocation.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btNewLocation.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btNewLocationActionPerformed(evt);
			}
		});
		toolBar.add(btNewLocation);

		btNewItem.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/item.png"))); // NOI18N
		btNewItem.setToolTipText(bundle.getString("msg.common.item")); // NOI18N
		btNewItem.setFocusable(false);
		btNewItem.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btNewItem.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btNewItem.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btNewItemActionPerformed(evt);
			}
		});
		toolBar.add(btNewItem);

		btNewTag.setIcon(new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/tag.png"))); // NOI18N
		btNewTag.setToolTipText(bundle.getString("msg.common.tag")); // NOI18N
		btNewTag.setFocusable(false);
		btNewTag.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btNewTag.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btNewTag.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btNewTagActionPerformed(evt);
			}
		});
		toolBar.add(btNewTag);
		toolBar.add(jSeparator20);

		btTabScene.setIcon(new javax.swing.ImageIcon(
				getClass().getResource("/storybook/resources/icons/16x32/manage_scenes.png"))); // NOI18N
		btTabScene.setToolTipText(bundle.getString("msg.common.scenes")); // NOI18N
		btTabScene.setFocusable(false);
		btTabScene.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btTabScene.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btTabScene.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btTabSceneActionPerformed(evt);
			}
		});
		toolBar.add(btTabScene);

		btTabChapter.setIcon(new javax.swing.ImageIcon(
				getClass().getResource("/storybook/resources/icons/16x32/manage_chapters.png"))); // NOI18N
		btTabChapter.setToolTipText(bundle.getString("msg.common.chapters")); // NOI18N
		btTabChapter.setFocusable(false);
		btTabChapter.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btTabChapter.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btTabChapter.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btTabChapterActionPerformed(evt);
			}
		});
		toolBar.add(btTabChapter);

		btTabPerson.setIcon(new javax.swing.ImageIcon(
				getClass().getResource("/storybook/resources/icons/16x32/manage_persons.png"))); // NOI18N
		btTabPerson.setToolTipText(bundle.getString("msg.common.persons")); // NOI18N
		btTabPerson.setFocusable(false);
		btTabPerson.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btTabPerson.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btTabPerson.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btTabPersonActionPerformed(evt);
			}
		});
		toolBar.add(btTabPerson);

		btTabRelationship.setIcon(new javax.swing.ImageIcon(
				getClass().getResource("/storybook/resources/icons/16x32/manage_relationships.png"))); // NOI18N
		btTabRelationship.setToolTipText(bundle.getString("msg.relationship")); // NOI18N
		btTabRelationship.setFocusable(false);
		btTabRelationship.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btTabRelationship.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btTabRelationship.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btTabRelationshipActionPerformed(evt);
			}
		});
		toolBar.add(btTabRelationship);

		btTabLocation.setIcon(new javax.swing.ImageIcon(
				getClass().getResource("/storybook/resources/icons/16x32/manage_locations.png"))); // NOI18N
		btTabLocation.setToolTipText(bundle.getString("msg.common.locations")); // NOI18N
		btTabLocation.setFocusable(false);
		btTabLocation.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btTabLocation.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btTabLocation.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btTabLocationActionPerformed(evt);
			}
		});
		toolBar.add(btTabLocation);

		btTabItem.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x32/manage_items.png"))); // NOI18N
		btTabItem.setToolTipText(bundle.getString("msg.common.items")); // NOI18N
		btTabItem.setFocusable(false);
		btTabItem.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btTabItem.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btTabItem.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btTabItemActionPerformed(evt);
			}
		});
		toolBar.add(btTabItem);

		btTabItemLink.setIcon(new javax.swing.ImageIcon(
				getClass().getResource("/storybook/resources/icons/16x32/manage_item_links.png"))); // NOI18N
		btTabItemLink.setToolTipText(bundle.getString("msg.common.item.links")); // NOI18N
		btTabItemLink.setFocusable(false);
		btTabItemLink.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btTabItemLink.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btTabItemLink.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btTabItemLinkActionPerformed(evt);
			}
		});
		toolBar.add(btTabItemLink);

		btTabTag.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x32/manage_tags.png"))); // NOI18N
		btTabTag.setToolTipText(bundle.getString("msg.common.tags")); // NOI18N
		btTabTag.setFocusable(false);
		btTabTag.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btTabTag.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btTabTag.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btTabTagActionPerformed(evt);
			}
		});
		toolBar.add(btTabTag);

		btTabTagLink.setIcon(new javax.swing.ImageIcon(
				getClass().getResource("/storybook/resources/icons/16x32/manage_tag_links.png"))); // NOI18N
		btTabTagLink.setToolTipText(bundle.getString("msg.common.tags.links")); // NOI18N
		btTabTagLink.setFocusable(false);
		btTabTagLink.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btTabTagLink.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btTabTagLink.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btTabTagLinkActionPerformed(evt);
			}
		});
		toolBar.add(btTabTagLink);
		toolBar.add(jSeparator21);

		btViewChrono.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/chrono_view.png"))); // NOI18N
		btViewChrono.setToolTipText(bundle.getString("msg.menu.view.chrono")); // NOI18N
		btViewChrono.setFocusable(false);
		btViewChrono.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btViewChrono.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btViewChrono.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btViewChronoActionPerformed(evt);
			}
		});
		toolBar.add(btViewChrono);

		btViewBook.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/book_view.png"))); // NOI18N
		btViewBook.setToolTipText(bundle.getString("msg.menu.view.book")); // NOI18N
		btViewBook.setFocusable(false);
		btViewBook.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btViewBook.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btViewBook.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btViewBookActionPerformed(evt);
			}
		});
		toolBar.add(btViewBook);

		btManageScene.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/manage_view.png"))); // NOI18N
		btManageScene.setToolTipText(bundle.getString("msg.menu.view.manage")); // NOI18N
		btManageScene.setFocusable(false);
		btManageScene.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btManageScene.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btManageScene.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btManageSceneActionPerformed(evt);
			}
		});
		toolBar.add(btManageScene);

		btViewReading.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/reading.png"))); // NOI18N
		btViewReading.setToolTipText(bundle.getString("msg.menu.view.reading")); // NOI18N
		btViewReading.setFocusable(false);
		btViewReading.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btViewReading.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btViewReading.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btViewReadingActionPerformed(evt);
			}
		});
		toolBar.add(btViewReading);

		btViewMemoria.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/memoria.png"))); // NOI18N
		btViewMemoria.setToolTipText(bundle.getString("msg.menu.view.pov")); // NOI18N
		btViewMemoria.setFocusable(false);
		btViewMemoria.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btViewMemoria.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btViewMemoria.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btViewMemoriaActionPerformed(evt);
			}
		});
		toolBar.add(btViewMemoria);
		toolBar.add(jSeparator22);

		btPreviousPart.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/arrowleft.png"))); // NOI18N
		btPreviousPart.setToolTipText(bundle.getString("msg.common.part.previous")); // NOI18N
		btPreviousPart.setFocusable(false);
		btPreviousPart.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btPreviousPart.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btPreviousPart.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btPreviousPartActionPerformed(evt);
			}
		});
		toolBar.add(btPreviousPart);

		btNextPart.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/arrowright.png"))); // NOI18N
		btNextPart.setToolTipText(bundle.getString("msg.common.part.next")); // NOI18N
		btNextPart.setFocusable(false);
		btNextPart.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btNextPart.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btNextPart.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btNextPartActionPerformed(evt);
			}
		});
		toolBar.add(btNextPart);
		toolBar.add(jSeparator23);

		btIdea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/idea.png"))); // NOI18N
		btIdea.setToolTipText(bundle.getString("msg.common.idea")); // NOI18N
		btIdea.setFocusable(false);
		btIdea.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btIdea.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btIdea.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btIdeaActionPerformed(evt);
			}
		});
		toolBar.add(btIdea);

		menuBar.setName("file-menu-command"); // NOI18N

		menuFile.setMnemonic(java.util.ResourceBundle.getBundle("storybook/msg/messages")
				.getString("msg.menu.file.mnemonic").charAt(0));
		menuFile.setText(bundle.getString("msg.menu.file")); // NOI18N

		fileNew.setAccelerator(
				javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
		fileNew.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/file-new.png"))); // NOI18N
		fileNew.setMnemonic('N');
		fileNew.setText(bundle.getString("msg.file.new")); // NOI18N
		fileNew.setActionCommand("new-command");
		fileNew.setName("new-command"); // NOI18N
		fileNew.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				fileNewActionPerformed(evt);
			}
		});
		menuFile.add(fileNew);

		fileOpen.setAccelerator(
				javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
		fileOpen.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/file-open.png"))); // NOI18N
		fileOpen.setText(bundle.getString("msg.file.open")); // NOI18N
		fileOpen.setActionCommand("open-command");
		fileOpen.setName("open-command"); // NOI18N
		fileOpen.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				fileOpenActionPerformed(evt);
			}
		});
		menuFile.add(fileOpen);

		fileOpenRecent.setText(bundle.getString("msg.file.open.recent")); // NOI18N
		fileOpenRecent.setActionCommand("recent-menu-command");
		fileOpenRecent.setName("recent-menu-command"); // NOI18N
		menuFile.add(fileOpenRecent);

		fileSave.setAccelerator(
				javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
		fileSave.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/file-save.png"))); // NOI18N
		fileSave.setMnemonic('S');
		fileSave.setText(bundle.getString("msg.file.save")); // NOI18N
		fileSave.setActionCommand("save-command");
		fileSave.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				fileSaveActionPerformed(evt);
			}
		});
		menuFile.add(fileSave);

		fileSaveAs.setAccelerator(
				javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
		fileSaveAs.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/file-save-as.png"))); // NOI18N
		fileSaveAs.setMnemonic('A');
		fileSaveAs.setText(bundle.getString("msg.file.save.as")); // NOI18N
		fileSaveAs.setActionCommand("export-book-command");
		fileSaveAs.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				fileSaveAsActionPerformed(evt);
			}
		});
		menuFile.add(fileSaveAs);

		fileRename.setAccelerator(
				javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
		fileRename.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/rename.png"))); // NOI18N
		fileRename.setText(bundle.getString("msg.common.rename")); // NOI18N
		fileRename.setActionCommand("rename-command");
		fileRename.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				fileRenameActionPerformed(evt);
			}
		});
		menuFile.add(fileRename);

		fileClose.setAccelerator(
				javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.CTRL_MASK));
		fileClose.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/close.png"))); // NOI18N
		fileClose.setText(bundle.getString("msg.file.close")); // NOI18N
		fileClose.setActionCommand("close-command");
		fileClose.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				fileCloseActionPerformed(evt);
			}
		});
		menuFile.add(fileClose);
		menuFile.add(separatorFile1);

		fileProperties.setText(bundle.getString("msg.file.properties")); // NOI18N
		fileProperties.setActionCommand("document-preferences-command");
		fileProperties.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				filePropertiesActionPerformed(evt);
			}
		});
		menuFile.add(fileProperties);
		menuFile.add(separatorFile2);

		fileImport.setText(bundle.getString("msg.file.import")); // NOI18N
		fileImport.setActionCommand("fileImport");
		fileImport.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				fileImportActionPerformed(evt);
			}
		});
		menuFile.add(fileImport);

		fileExport.setAccelerator(
				javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
		fileExport.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/export.png"))); // NOI18N
		fileExport.setText(bundle.getString("msg.file.export")); // NOI18N
		fileExport.setActionCommand("export-command");
		fileExport.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				fileExportActionPerformed(evt);
			}
		});
		menuFile.add(fileExport);

		filePrint.setAccelerator(
				javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
		filePrint.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/print.png"))); // NOI18N
		filePrint.setText(bundle.getString("msg.file.print")); // NOI18N
		filePrint.setActionCommand("print-command");
		filePrint.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				filePrintActionPerformed(evt);
			}
		});
		menuFile.add(filePrint);
		menuFile.add(separatorFile3);

		fileExit.setAccelerator(
				javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
		fileExit.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/exit.png"))); // NOI18N
		fileExit.setMnemonic('X');
		fileExit.setText(bundle.getString("msg.common.exit")); // NOI18N
		fileExit.setActionCommand("exit-command");
		fileExit.setName("exit-command"); // NOI18N
		fileExit.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				fileExitActionPerformed(evt);
			}
		});
		menuFile.add(fileExit);

		menuBar.add(menuFile);

		menuEdit.setMnemonic(java.util.ResourceBundle.getBundle("storybook/msg/messages")
				.getString("msg.common.edit.mnemonic").charAt(0));
		menuEdit.setText(bundle.getString("msg.common.edit")); // NOI18N
		menuEdit.setName("edit-menu-command"); // NOI18N

		editCopyBook.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/edit-copy.png"))); // NOI18N
		editCopyBook.setMnemonic('C');
		editCopyBook.setText(bundle.getString("msg.book.copy")); // NOI18N
		editCopyBook.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editCopyBookActionPerformed(evt);
			}
		});
		menuEdit.add(editCopyBook);

		editCopyBlurb.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/edit-copy.png"))); // NOI18N
		editCopyBlurb.setMnemonic('P');
		editCopyBlurb.setText(bundle.getString("msg.blurb.copy")); // NOI18N
		editCopyBlurb.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editCopyBlurbActionPerformed(evt);
			}
		});
		menuEdit.add(editCopyBlurb);

		jMenu1.setText(bundle.getString("msg.copy.menu")); // NOI18N

		editCopyPersons.setText(bundle.getString("msg.common.persons")); // NOI18N
		editCopyPersons.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editCopyPersonsActionPerformed(evt);
			}
		});
		jMenu1.add(editCopyPersons);

		editCopyLocations.setText(bundle.getString("msg.common.locations")); // NOI18N
		editCopyLocations.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editCopyLocationsActionPerformed(evt);
			}
		});
		jMenu1.add(editCopyLocations);

		editCopyItems.setText(bundle.getString("msg.common.items")); // NOI18N
		editCopyItems.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editCopyItemsActionPerformed(evt);
			}
		});
		jMenu1.add(editCopyItems);

		editCopyIdeas.setText(bundle.getString("msg.common.ideas")); // NOI18N
		editCopyIdeas.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editCopyIdeasActionPerformed(evt);
			}
		});
		jMenu1.add(editCopyIdeas);

		menuEdit.add(jMenu1);
		menuEdit.add(jSeparator1);

		windowPreferences.setText(bundle.getString("msg.dlg.preference.title")); // NOI18N
		windowPreferences.setName("preferences-command"); // NOI18N
		windowPreferences.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				windowPreferencesActionPerformed(evt);
			}
		});
		menuEdit.add(windowPreferences);

		menuBar.add(menuEdit);

		menuNewEntity.setText(bundle.getString("msg.common.new.object")); // NOI18N
		menuNewEntity.setName("new-entity-menu-command"); // NOI18N

		newScene.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S,
				java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
		newScene.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/scene.png"))); // NOI18N
		newScene.setMnemonic('S');
		newScene.setText(bundle.getString("msg.common.scene")); // NOI18N
		newScene.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				newSceneActionPerformed(evt);
			}
		});
		menuNewEntity.add(newScene);

		newChapter.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C,
				java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
		newChapter.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/chapter.png"))); // NOI18N
		newChapter.setMnemonic('C');
		newChapter.setText(bundle.getString("msg.common.chapter")); // NOI18N
		newChapter.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				newChapterActionPerformed(evt);
			}
		});
		menuNewEntity.add(newChapter);

		newChapters.setMnemonic('M');
		newChapters.setText(bundle.getString("msg.generate.chapters")); // NOI18N
		newChapters.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				newChaptersActionPerformed(evt);
			}
		});
		menuNewEntity.add(newChapters);

		newPart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/part.png"))); // NOI18N
		newPart.setMnemonic('P');
		newPart.setText(bundle.getString("msg.common.part")); // NOI18N
		newPart.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				newPartActionPerformed(evt);
			}
		});
		menuNewEntity.add(newPart);

		newStrand.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/strand.png"))); // NOI18N
		newStrand.setMnemonic('S');
		newStrand.setText(bundle.getString("msg.common.strand")); // NOI18N
		newStrand.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				newStrandActionPerformed(evt);
			}
		});
		menuNewEntity.add(newStrand);
		menuNewEntity.add(jSeparator4);

		newPerson.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P,
				java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
		newPerson.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/person.png"))); // NOI18N
		newPerson.setMnemonic('P');
		newPerson.setText(bundle.getString("msg.common.person")); // NOI18N
		newPerson.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				newPersonActionPerformed(evt);
			}
		});
		menuNewEntity.add(newPerson);

		newRelationships.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/relationship.png"))); // NOI18N
		newRelationships.setText(bundle.getString("msg.new.relationship")); // NOI18N
		newRelationships.setToolTipText(bundle.getString("msg.relationship")); // NOI18N
		newRelationships.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				newRelationshipsActionPerformed(evt);
			}
		});
		menuNewEntity.add(newRelationships);

		newCategory.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/category.png"))); // NOI18N
		newCategory.setText(bundle.getString("msg.persons.category")); // NOI18N
		newCategory.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				newCategoryActionPerformed(evt);
			}
		});
		menuNewEntity.add(newCategory);

		newGender.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/gender.png"))); // NOI18N
		newGender.setText(bundle.getString("msg.common.genders")); // NOI18N
		newGender.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				newGenderActionPerformed(evt);
			}
		});
		menuNewEntity.add(newGender);
		menuNewEntity.add(jSeparator5);

		newLocation.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L,
				java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
		newLocation.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/location.png"))); // NOI18N
		newLocation.setText(bundle.getString("msg.common.location")); // NOI18N
		newLocation.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				newLocationActionPerformed(evt);
			}
		});
		menuNewEntity.add(newLocation);
		menuNewEntity.add(jSeparator6);

		newTag.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T,
				java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
		newTag.setIcon(new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/tag.png"))); // NOI18N
		newTag.setText(bundle.getString("msg.tag")); // NOI18N
		newTag.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				newTagActionPerformed(evt);
			}
		});
		menuNewEntity.add(newTag);

		newTagLink.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/link.png"))); // NOI18N
		newTagLink.setText(bundle.getString("msg.tag.link")); // NOI18N
		newTagLink.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				newTagLinkActionPerformed(evt);
			}
		});
		menuNewEntity.add(newTagLink);
		menuNewEntity.add(jSeparator7);

		newItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I,
				java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
		newItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/item.png"))); // NOI18N
		newItem.setText(bundle.getString("msg.item")); // NOI18N
		newItem.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				newItemActionPerformed(evt);
			}
		});
		menuNewEntity.add(newItem);

		newItemLink.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/link.png"))); // NOI18N
		newItemLink.setText(bundle.getString("msg.item.link")); // NOI18N
		newItemLink.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				newItemLinkActionPerformed(evt);
			}
		});
		menuNewEntity.add(newItemLink);
		menuNewEntity.add(jSeparator8);

		newFOI.setAccelerator(
				javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
		newFOI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/bulb.png"))); // NOI18N
		newFOI.setText(bundle.getString("msg.foi.title")); // NOI18N
		newFOI.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				newFOIActionPerformed(evt);
			}
		});
		menuNewEntity.add(newFOI);

		newIdea.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F,
				java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
		newIdea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/bulb.png"))); // NOI18N
		newIdea.setText(bundle.getString("msg.idea.table.idea")); // NOI18N
		newIdea.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				newIdeaActionPerformed(evt);
			}
		});
		menuNewEntity.add(newIdea);

		newMemo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T,
				java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
		newMemo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/memo.png"))); // NOI18N
		newMemo.setText(bundle.getString("msg.memo")); // NOI18N
		newMemo.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				newMemoActionPerformed(evt);
			}
		});
		menuNewEntity.add(newMemo);

		menuBar.add(menuNewEntity);

		menuPrimaryObjects.setMnemonic(java.util.ResourceBundle.getBundle("storybook/msg/messages")
				.getString("msg.common.primary.objects.mnemonic").charAt(0));
		menuPrimaryObjects.setText(bundle.getString("msg.common.primary.objects")); // NOI18N
		menuPrimaryObjects.setName("main-entities-menu-command"); // NOI18N

		tabScene.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S,
				java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
		tabScene.setIcon(new javax.swing.ImageIcon(
				getClass().getResource("/storybook/resources/icons/16x32/manage_scenes.png"))); // NOI18N
		tabScene.setMnemonic('S');
		tabScene.setText(bundle.getString("msg.common.scene")); // NOI18N
		tabScene.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tabSceneActionPerformed(evt);
			}
		});
		menuPrimaryObjects.add(tabScene);

		tabChapter.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C,
				java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
		tabChapter.setIcon(new javax.swing.ImageIcon(
				getClass().getResource("/storybook/resources/icons/16x32/manage_chapters.png"))); // NOI18N
		tabChapter.setMnemonic('C');
		tabChapter.setText(bundle.getString("msg.common.chapter")); // NOI18N
		tabChapter.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tabChapterActionPerformed(evt);
			}
		});
		menuPrimaryObjects.add(tabChapter);

		jChaptersOrder.setText(bundle.getString("msg.menu.chapters.order")); // NOI18N
		jChaptersOrder.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jChaptersOrderActionPerformed(evt);
			}
		});
		menuPrimaryObjects.add(jChaptersOrder);

		tabPart.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x32/manage_parts.png"))); // NOI18N
		tabPart.setMnemonic('P');
		tabPart.setText(bundle.getString("msg.common.part")); // NOI18N
		tabPart.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tabPartActionPerformed(evt);
			}
		});
		menuPrimaryObjects.add(tabPart);

		tabStrand.setIcon(new javax.swing.ImageIcon(
				getClass().getResource("/storybook/resources/icons/16x32/manage_strands.png"))); // NOI18N
		tabStrand.setMnemonic('S');
		tabStrand.setText(bundle.getString("msg.common.strand")); // NOI18N
		tabStrand.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tabStrandActionPerformed(evt);
			}
		});
		menuPrimaryObjects.add(tabStrand);
		menuPrimaryObjects.add(jSeparator9);

		tabPerson.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P,
				java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
		tabPerson.setIcon(new javax.swing.ImageIcon(
				getClass().getResource("/storybook/resources/icons/16x32/manage_persons.png"))); // NOI18N
		tabPerson.setMnemonic('P');
		tabPerson.setText(bundle.getString("msg.common.person")); // NOI18N
		tabPerson.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tabPersonActionPerformed(evt);
			}
		});
		menuPrimaryObjects.add(tabPerson);

		tabRelationship.setIcon(new javax.swing.ImageIcon(
				getClass().getResource("/storybook/resources/icons/16x32/manage_relationships.png"))); // NOI18N
		tabRelationship.setText(bundle.getString("msg.relationship")); // NOI18N
		tabRelationship.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tabRelationshipActionPerformed(evt);
			}
		});
		menuPrimaryObjects.add(tabRelationship);

		tabCategory.setIcon(new javax.swing.ImageIcon(
				getClass().getResource("/storybook/resources/icons/16x32/manage_categories.png"))); // NOI18N
		tabCategory.setText(bundle.getString("msg.persons.category")); // NOI18N
		tabCategory.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tabCategoryActionPerformed(evt);
			}
		});
		menuPrimaryObjects.add(tabCategory);

		tabGender.setIcon(new javax.swing.ImageIcon(
				getClass().getResource("/storybook/resources/icons/16x32/manage_genders.png"))); // NOI18N
		tabGender.setText(bundle.getString("msg.common.genders")); // NOI18N
		tabGender.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tabGenderActionPerformed(evt);
			}
		});
		menuPrimaryObjects.add(tabGender);
		menuPrimaryObjects.add(jSeparator10);

		tabLocation.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L,
				java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
		tabLocation.setIcon(new javax.swing.ImageIcon(
				getClass().getResource("/storybook/resources/icons/16x32/manage_locations.png"))); // NOI18N
		tabLocation.setText(bundle.getString("msg.common.location")); // NOI18N
		tabLocation.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tabLocationActionPerformed(evt);
			}
		});
		menuPrimaryObjects.add(tabLocation);

		renameCity.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/rename.png"))); // NOI18N
		renameCity.setText(bundle.getString("msg.location.rename.city")); // NOI18N
		renameCity.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				renameCityActionPerformed(evt);
			}
		});
		menuPrimaryObjects.add(renameCity);

		renameCountry.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/rename.png"))); // NOI18N
		renameCountry.setText(bundle.getString("msg.location.rename.country")); // NOI18N
		renameCountry.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				renameCountryActionPerformed(evt);
			}
		});
		menuPrimaryObjects.add(renameCountry);

		menuBar.add(menuPrimaryObjects);

		menuSecondaryObjects.setMnemonic(java.util.ResourceBundle.getBundle("storybook/msg/messages")
				.getString("msg.common.secondary.objects.mnemonic").charAt(0));
		menuSecondaryObjects.setText(bundle.getString("msg.common.secondary.objects")); // NOI18N
		menuSecondaryObjects.setName("secondary-entities-menu-command"); // NOI18N

		tabTag.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T,
				java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
		tabTag.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x32/manage_tags.png"))); // NOI18N
		tabTag.setText(bundle.getString("msg.tag")); // NOI18N
		tabTag.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tabTagActionPerformed(evt);
			}
		});
		menuSecondaryObjects.add(tabTag);

		tabTagLink.setIcon(new javax.swing.ImageIcon(
				getClass().getResource("/storybook/resources/icons/16x32/manage_tag_links.png"))); // NOI18N
		tabTagLink.setText(bundle.getString("msg.tag.link")); // NOI18N
		tabTagLink.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tabTagLinkActionPerformed(evt);
			}
		});
		menuSecondaryObjects.add(tabTagLink);

		renameTagCategory.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/rename.png"))); // NOI18N
		renameTagCategory.setText(bundle.getString("msg.tag.rename.category")); // NOI18N
		renameTagCategory.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				renameTagCategoryActionPerformed(evt);
			}
		});
		menuSecondaryObjects.add(renameTagCategory);
		menuSecondaryObjects.add(jSeparator11);

		tabItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O,
				java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
		tabItem.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x32/manage_items.png"))); // NOI18N
		tabItem.setText(bundle.getString("msg.item")); // NOI18N
		tabItem.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tabItemActionPerformed(evt);
			}
		});
		menuSecondaryObjects.add(tabItem);

		tabItemLink.setIcon(new javax.swing.ImageIcon(
				getClass().getResource("/storybook/resources/icons/16x32/manage_item_links.png"))); // NOI18N
		tabItemLink.setText(bundle.getString("msg.item.link")); // NOI18N
		tabItemLink.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tabItemLinkActionPerformed(evt);
			}
		});
		menuSecondaryObjects.add(tabItemLink);

		renameItemCategory.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/rename.png"))); // NOI18N
		renameItemCategory.setText(bundle.getString("msg.item.rename.category")); // NOI18N
		renameItemCategory.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				renameItemCategoryActionPerformed(evt);
			}
		});
		menuSecondaryObjects.add(renameItemCategory);

		tabIdea.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I,
				java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
		tabIdea.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x32/manage_ideas.png"))); // NOI18N
		tabIdea.setText(bundle.getString("msg.idea.table.idea")); // NOI18N
		tabIdea.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tabIdeaActionPerformed(evt);
			}
		});
		menuSecondaryObjects.add(tabIdea);

		menuBar.add(menuSecondaryObjects);

		menuView.setMnemonic(java.util.ResourceBundle.getBundle("storybook/msg/messages")
				.getString("msg.menu.view.mnemonic").charAt(0));
		menuView.setText(bundle.getString("msg.menu.view")); // NOI18N
		menuView.setName("view-menu-command"); // NOI18N

		vueChrono.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/chrono_view.png"))); // NOI18N
		vueChrono.setText(bundle.getString("msg.menu.view.chrono")); // NOI18N
		vueChrono.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				vueChronoActionPerformed(evt);
			}
		});
		menuView.add(vueChrono);

		vueBook.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/book_view.png"))); // NOI18N
		vueBook.setText(bundle.getString("msg.menu.view.book")); // NOI18N
		vueBook.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				vueBookActionPerformed(evt);
			}
		});
		menuView.add(vueBook);

		vueReading.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/reading.png"))); // NOI18N
		vueReading.setText(bundle.getString("msg.menu.view.reading")); // NOI18N
		vueReading.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				vueReadingActionPerformed(evt);
			}
		});
		menuView.add(vueReading);

		vueManageScene.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/manage_view.png"))); // NOI18N
		vueManageScene.setText(bundle.getString("msg.menu.view.manage")); // NOI18N
		vueManageScene.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				vueManageSceneActionPerformed(evt);
			}
		});
		menuView.add(vueManageScene);

		vueMemoria.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/memoria.png"))); // NOI18N
		vueMemoria.setText(bundle.getString("msg.menu.view.pov")); // NOI18N
		vueMemoria.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				vueMemoriaActionPerformed(evt);
			}
		});
		menuView.add(vueMemoria);
		menuView.add(jSeparator12);

		vueEditor.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/edit.png"))); // NOI18N
		vueEditor.setText(bundle.getString("msg.common.editor")); // NOI18N
		vueEditor.setEnabled(false);
		vueEditor.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				vueEditorActionPerformed(evt);
			}
		});
		menuView.add(vueEditor);

		toolsTaskList.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/tasklist.png"))); // NOI18N
		toolsTaskList.setText(bundle.getString("msg.tasklist.title")); // NOI18N
		toolsTaskList.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				toolsTaskListActionPerformed(evt);
			}
		});
		menuView.add(toolsTaskList);

		toolsPlan.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/plan.png"))); // NOI18N
		toolsPlan.setText(bundle.getString("msg.menu.view.plan")); // NOI18N
		toolsPlan.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				toolsPlanActionPerformed(evt);
			}
		});
		menuView.add(toolsPlan);

		vueTree.setIcon(new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/tree.png"))); // NOI18N
		vueTree.setText(bundle.getString("msg.common.tree")); // NOI18N
		vueTree.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				vueTreeActionPerformed(evt);
			}
		});
		menuView.add(vueTree);

		vueInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/info.png"))); // NOI18N
		vueInfo.setText(bundle.getString("msg.info.title")); // NOI18N
		vueInfo.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				vueInfoActionPerformed(evt);
			}
		});
		menuView.add(vueInfo);

		vueNavigation.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/compass.png"))); // NOI18N
		vueNavigation.setText(bundle.getString("msg.common.navigation")); // NOI18N
		vueNavigation.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				vueNavigationActionPerformed(evt);
			}
		});
		menuView.add(vueNavigation);

		vueInfo1.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/memo.png"))); // NOI18N
		vueInfo1.setText(bundle.getString("msg.memo")); // NOI18N
		vueInfo1.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				vueInfo1ActionPerformed(evt);
			}
		});
		menuView.add(vueInfo1);

		menuBar.add(menuView);

		menuParts.setText(bundle.getString("msg.menu.parts")); // NOI18N
		menuParts.setName("parts-menu-command"); // NOI18N

		partPrevious.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/arrowleft.png"))); // NOI18N
		partPrevious.setText(bundle.getString("msg.common.part.previous")); // NOI18N
		partPrevious.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				partPreviousActionPerformed(evt);
			}
		});
		menuParts.add(partPrevious);

		partNext.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/arrowright.png"))); // NOI18N
		partNext.setText(bundle.getString("msg.common.part.next")); // NOI18N
		partNext.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				partNextActionPerformed(evt);
			}
		});
		menuParts.add(partNext);

		menuBar.add(menuParts);

		menuCharts.setMnemonic(java.util.ResourceBundle.getBundle("storybook/msg/messages")
				.getString("msg.menu.charts.mnemonic").charAt(0));
		menuCharts.setText(bundle.getString("msg.menu.charts")); // NOI18N
		menuCharts.setName("charts-menu-command"); // NOI18N

		chartsAttributes.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/columns.png"))); // NOI18N
		chartsAttributes.setText(bundle.getString("msg.attribute.list")); // NOI18N
		chartsAttributes.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				chartsAttributesActionPerformed(evt);
			}
		});
		menuCharts.add(chartsAttributes);

		chartPersonsByDate.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/chart.png"))); // NOI18N
		chartPersonsByDate.setText(bundle.getString("msg.menu.tools.charts.overall.character.date")); // NOI18N
		chartPersonsByDate.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				chartPersonsByDateActionPerformed(evt);
			}
		});
		menuCharts.add(chartPersonsByDate);

		chartPersonsByScene.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/chart.png"))); // NOI18N
		chartPersonsByScene.setText(bundle.getString("msg.menu.tools.charts.part.character.scene")); // NOI18N
		chartPersonsByScene.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				chartPersonsBySceneActionPerformed(evt);
			}
		});
		menuCharts.add(chartPersonsByScene);

		chartWIWW.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/chart.png"))); // NOI18N
		chartWIWW.setText(bundle.getString("msg.menu.tools.charts.overall.whoIsWhereWhen")); // NOI18N
		chartWIWW.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				chartWIWWActionPerformed(evt);
			}
		});
		menuCharts.add(chartWIWW);

		chartStrandsByDate.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/chart.png"))); // NOI18N
		chartStrandsByDate.setText(bundle.getString("msg.menu.tools.charts.overall.strand.date")); // NOI18N
		chartStrandsByDate.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				chartStrandsByDateActionPerformed(evt);
			}
		});
		menuCharts.add(chartStrandsByDate);
		menuCharts.add(jSeparator13);

		chartOccurrenceOfPersons.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/chart.png"))); // NOI18N
		chartOccurrenceOfPersons.setText(bundle.getString("msg.menu.tools.charts.overall.character.occurrence")); // NOI18N
		chartOccurrenceOfPersons.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				chartOccurrenceOfPersonsActionPerformed(evt);
			}
		});
		menuCharts.add(chartOccurrenceOfPersons);

		chartOccurrenceOfLocations.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/chart.png"))); // NOI18N
		chartOccurrenceOfLocations.setText(bundle.getString("msg.menu.tools.charts.overall.location.occurrence")); // NOI18N
		chartOccurrenceOfLocations.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				chartOccurrenceOfLocationsActionPerformed(evt);
			}
		});
		menuCharts.add(chartOccurrenceOfLocations);

		chartGantt.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/storybook/resources/icons/16x16/chart.png"))); // NOI18N
		chartGantt.setText(bundle.getString("msg.chart.gantt.characters.title")); // NOI18N
		chartGantt.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				chartGanttActionPerformed(evt);
			}
		});
		menuCharts.add(chartGantt);

		menuBar.add(menuCharts);

		menuWindow.setMnemonic(
				java.util.ResourceBundle.getBundle("storybook/msg/messages").getString("msg.common.window").charAt(0));
		menuWindow.setText(bundle.getString("msg.common.window")); // NOI18N
		menuWindow.setName("window-menu-command"); // NOI18N

		windowLoadLayout.setText(bundle.getString("msg.docking.load.layout")); // NOI18N
		menuWindow.add(windowLoadLayout);

		windowSaveLayout.setText(bundle.getString("msg.docking.save.layout")); // NOI18N
		windowSaveLayout.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				windowSaveLayoutActionPerformed(evt);
			}
		});
		menuWindow.add(windowSaveLayout);

		windowDefaultLayout.setText(bundle.getString("msg.layout.default")); // NOI18N
		windowDefaultLayout.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				windowDefaultLayoutActionPerformed(evt);
			}
		});
		menuWindow.add(windowDefaultLayout);

		windowManageLayouts.setText(bundle.getString("msg.docking.manage.layouts")); // NOI18N
		windowManageLayouts.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				windowManageLayoutsActionPerformed(evt);
			}
		});
		menuWindow.add(windowManageLayouts);
		menuWindow.add(jSeparator15);

		windowPersonsAndLocations.setText(bundle.getString("msg.layout.persons.locations")); // NOI18N
		windowPersonsAndLocations.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				windowPersonsAndLocationsActionPerformed(evt);
			}
		});
		menuWindow.add(windowPersonsAndLocations);

		windowTagsAndItems.setText(bundle.getString("msg.layout.tags.items")); // NOI18N
		windowTagsAndItems.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				windowTagsAndItemsActionPerformed(evt);
			}
		});
		menuWindow.add(windowTagsAndItems);

		windowChrono.setText(bundle.getString("msg.layout.chrono.only")); // NOI18N
		windowChrono.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				windowChronoActionPerformed(evt);
			}
		});
		menuWindow.add(windowChrono);

		windowBook.setText(bundle.getString("msg.layout.book.only")); // NOI18N
		windowBook.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				windowBookActionPerformed(evt);
			}
		});
		menuWindow.add(windowBook);

		windowManage.setText(bundle.getString("msg.layout.manage.only")); // NOI18N
		windowManage.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				windowManageActionPerformed(evt);
			}
		});
		menuWindow.add(windowManage);

		windowReading.setText(bundle.getString("msg.layout.reading.only")); // NOI18N
		windowReading.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				windowReadingActionPerformed(evt);
			}
		});
		menuWindow.add(windowReading);
		menuWindow.add(jSeparator16);

		windowResetLayout.setText(bundle.getString("msg.docking.reset.layout")); // NOI18N
		windowResetLayout.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				windowResetLayoutActionPerformed(evt);
			}
		});
		menuWindow.add(windowResetLayout);

		windowRefresh.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
		windowRefresh.setText(bundle.getString("msg.common.refresh")); // NOI18N
		windowRefresh.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				windowRefreshActionPerformed(evt);
			}
		});
		menuWindow.add(windowRefresh);

		menuBar.add(menuWindow);

		menuHelp.setMnemonic(java.util.ResourceBundle.getBundle("storybook/msg/messages")
				.getString("msg.menu.help.mnemonic").charAt(0));
		menuHelp.setText(bundle.getString("msg.menu.help")); // NOI18N
		menuHelp.setName("help-menu"); // NOI18N

		helpDoc.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
		helpDoc.setMnemonic('D');
		helpDoc.setText(bundle.getString("msg.menu.help.doc")); // NOI18N
		helpDoc.setName("doc-command"); // NOI18N
		helpDoc.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				helpDocActionPerformed(evt);
			}
		});
		menuHelp.add(helpDoc);

		helpFaq.setMnemonic('F');
		helpFaq.setText(bundle.getString("msg.menu.help.faq")); // NOI18N
		helpFaq.setName("faq-command"); // NOI18N
		helpFaq.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				helpFaqActionPerformed(evt);
			}
		});
		menuHelp.add(helpFaq);

		helpHome.setMnemonic('H');
		helpHome.setText(bundle.getString("msg.menu.help.homepage")); // NOI18N
		helpHome.setName("homepage-command"); // NOI18N
		helpHome.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				helpHomeActionPerformed(evt);
			}
		});
		menuHelp.add(helpHome);

		helpReportBug.setMnemonic('B');
		helpReportBug.setText(bundle.getString("msg.menu.help.bug")); // NOI18N
		helpReportBug.setName("report-bug-command"); // NOI18N
		helpReportBug.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				helpReportBugActionPerformed(evt);
			}
		});
		menuHelp.add(helpReportBug);

		helpAbout.setMnemonic('A');
		helpAbout.setText(bundle.getString("msg.menu.help.about")); // NOI18N
		helpAbout.setName("about-command"); // NOI18N
		helpAbout.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				helpAboutActionPerformed(evt);
			}
		});
		menuHelp.add(helpAbout);
		menuHelp.add(jSeparator18);

		helpCheckUpdates.setMnemonic('U');
		helpCheckUpdates.setText(bundle.getString("msg.menu.help.update")); // NOI18N
		helpCheckUpdates.setName("check-update-command"); // NOI18N
		helpCheckUpdates.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				helpCheckUpdatesActionPerformed(evt);
			}
		});
		menuHelp.add(helpCheckUpdates);

		helpTrace.setMnemonic('T');
		helpTrace.setText(bundle.getString("msg.menu.help.trace")); // NOI18N
		helpTrace.setName("trace-command"); // NOI18N
		helpTrace.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				helpTraceActionPerformed(evt);
			}
		});
		menuHelp.add(helpTrace);

		devTest.setText("Dev-test");
		devTest.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				devTestActionPerformed(evt);
			}
		});
		menuHelp.add(devTest);

		menuBar.add(menuHelp);

		setJMenuBar(menuBar);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				toolBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								layout.createSequentialGroup()
										.addComponent(toolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(0, 256, Short.MAX_VALUE)));

		pack();
	}// </editor-fold>//GEN-END:initComponents
	private void jChaptersOrderActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jChaptersOrderActionPerformed
		ChaptersOrderDialog dlg = new ChaptersOrderDialog(mainFrame);
		SwingUtil.showModalDialog(dlg, mainFrame);
	}// GEN-LAST:event_jChaptersOrderActionPerformed
	private void newCategoryActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_newCategoryActionPerformed
		newEntity(new Category());
	}// GEN-LAST:event_newCategoryActionPerformed
	private void newChapterActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_newChapterActionPerformed
		newEntity(new Chapter());
	}// GEN-LAST:event_newChapterActionPerformed
	private void newChaptersActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_newChaptersActionPerformed
		CreateChaptersDialog dlg = new CreateChaptersDialog(mainFrame);
		SwingUtil.showModalDialog(dlg, mainFrame);
	}// GEN-LAST:event_newChaptersActionPerformed
	public void newEntity(AbstractEntity entity) {
		SbApp.trace("MainMenu.newEntity(" + entity.getClass().getName() + ")");
		/*
		 * BookController ctrl = mainFrame.getBookController();
		 * ctrl.setEntityToEdit(entity);
		 * mainFrame.showView(SbConstants.ViewName.EDITOR);
		 */
		// EditorDlg dlg=new EditorDlg(mainFrame,entity);
		// dlg.setVisible(true);
		mainFrame.showEditorAsDialog(entity);
	}
	private void newFOIActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_newFOIActionPerformed
		FoiDialog dlg = new FoiDialog(mainFrame);
		SwingUtil.showModalDialog(dlg, mainFrame);
	}// GEN-LAST:event_newFOIActionPerformed
	private void newGenderActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_newGenderActionPerformed
		newEntity(new Gender());
	}// GEN-LAST:event_newGenderActionPerformed
	private void newIdeaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_newIdeaActionPerformed
		newEntity(new Idea());
	}// GEN-LAST:event_newIdeaActionPerformed
	private void newItemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_newItemActionPerformed
		newEntity(new Item());
	}// GEN-LAST:event_newItemActionPerformed
	private void newItemLinkActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_newItemLinkActionPerformed
		newEntity(new ItemLink());
	}// GEN-LAST:event_newItemLinkActionPerformed
	private void newLocationActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_newLocationActionPerformed
		newEntity(new Location());
	}// GEN-LAST:event_newLocationActionPerformed
	private void newMemoActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_newMemoActionPerformed
		newEntity(new Memo());
	}// GEN-LAST:event_newMemoActionPerformed
	private void newPartActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_newPartActionPerformed
		newEntity(new Part());
	}// GEN-LAST:event_newPartActionPerformed
	private void newPersonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_newPersonActionPerformed
		newEntity(new Person());
	}// GEN-LAST:event_newPersonActionPerformed
	private void newRelationshipsActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_newRelationshipsActionPerformed
		newEntity(new Relationship());
	}// GEN-LAST:event_newRelationshipsActionPerformed
	private void newSceneActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_newSceneActionPerformed
		newEntity(new Scene());
	}// GEN-LAST:event_newSceneActionPerformed
	private void newStrandActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_newStrandActionPerformed
		newEntity(new Strand());
	}// GEN-LAST:event_newStrandActionPerformed
	private void newTagActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_newTagActionPerformed
		newEntity(new Tag());
	}// GEN-LAST:event_newTagActionPerformed
	private void newTagLinkActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_newTagLinkActionPerformed
		newEntity(new TagLink());
	}// GEN-LAST:event_newTagLinkActionPerformed
	private void partNextActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_partNextActionPerformed
		mainFrame.getSbActionManager().getActionHandler().handleNextPart();
	}// GEN-LAST:event_partNextActionPerformed
	private void partPreviousActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_partPreviousActionPerformed
		mainFrame.getSbActionManager().getActionHandler().handlePreviousPart();
	}// GEN-LAST:event_partPreviousActionPerformed
	private void renameCityActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_renameCityActionPerformed
		RenameCityDialog dlg = new RenameCityDialog(mainFrame);
		ActionManager actMngr = mainFrame.getSbActionManager().getActionManager();
		Action act = actMngr.getAction("rename-city-command");
		Object obj = act.getValue(SbConstants.ActionKey.CATEGORY.toString());
		if (obj != null) {
			dlg.setSelectedItem(obj);
		}
		SwingUtil.showModalDialog(dlg, mainFrame);
		act.putValue(SbConstants.ActionKey.CATEGORY.toString(), null);
	}// GEN-LAST:event_renameCityActionPerformed
	private void renameCountryActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_renameCountryActionPerformed
		RenameCountryDialog dlg = new RenameCountryDialog(mainFrame);
		ActionManager actMngr = mainFrame.getSbActionManager().getActionManager();
		Action act = actMngr.getAction("rename-country-command");
		Object obj = act.getValue(SbConstants.ActionKey.CATEGORY.toString());
		if (obj != null) {
			dlg.setSelectedItem(obj);
		}
		SwingUtil.showModalDialog(dlg, mainFrame);
		act.putValue(SbConstants.ActionKey.CATEGORY.toString(), null);
	}// GEN-LAST:event_renameCountryActionPerformed
	private void renameItemCategoryActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_renameItemCategoryActionPerformed
		RenameItemCategoryDialog dlg = new RenameItemCategoryDialog(mainFrame);
		ActionManager actMngr = mainFrame.getSbActionManager().getActionManager();
		Action act = actMngr.getAction("rename-item-category-command");
		Object obj = act.getValue(SbConstants.ActionKey.CATEGORY.toString());
		if (obj != null) {
			dlg.setSelectedItem(obj);
		}
		SwingUtil.showModalDialog(dlg, mainFrame);
		act.putValue(SbConstants.ActionKey.CATEGORY.toString(), null);
	}// GEN-LAST:event_renameItemCategoryActionPerformed
	private void renameTagCategoryActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_renameTagCategoryActionPerformed
		RenameTagCategoryDialog dlg = new RenameTagCategoryDialog(mainFrame);
		ActionManager actMngr = mainFrame.getSbActionManager().getActionManager();
		Action act = actMngr.getAction("rename-tag-category-command");
		Object obj = act.getValue(SbConstants.ActionKey.CATEGORY.toString());
		if (obj != null) {
			dlg.setSelectedItem(obj);
		}
		SwingUtil.showModalDialog(dlg, mainFrame);
		act.putValue(SbConstants.ActionKey.CATEGORY.toString(), null);
	}// GEN-LAST:event_renameTagCategoryActionPerformed
	public void setMenuForBlank() {
		// hide menus from MenuBar
		javax.swing.JMenu menus[] = { menuNewEntity, menuParts, menuPrimaryObjects, menuSecondaryObjects, menuCharts,
				menuCharts, menuView, menuWindow };
		for (javax.swing.JMenu m : menus) {
			m.setVisible(false);
		}
		javax.swing.JMenuItem[] submenus = { editCopyBlurb, editCopyBook, editCopyPersons, editCopyLocations,
				editCopyItems, fileClose, fileExport, filePrint, fileProperties, fileRename, fileSave, fileSaveAs,
				fileExport, fileImport };
		for (javax.swing.JMenuItem m : submenus) {
			m.setVisible(false);
		}
		separatorFile1.setVisible(false);
		separatorFile2.setVisible(false);
		separatorFile3.setVisible(false);
		// hide button from toolBar
		javax.swing.JButton button[] = { btFileSave, btManageScene, btNewChapter, btNewItem, btNewLocation, btNewPerson,
				btNewScene, btNewTag, btNextPart, btPreviousPart, btTabChapter, btTabItem, btTabItemLink, btTabLocation,
				btTabPerson, btTabRelationship, btTabScene, btTabTag, btTabTagLink, btViewBook, btViewChrono,
				btViewMemoria, btViewReading, btIdea };
		for (javax.swing.JButton bt : button) {
			bt.setVisible(false);
		}
		if (SbApp.isDevTest() == false)
			devTest.setVisible(false);
	}
	private void tabCategoryActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_tabCategoryActionPerformed
		mainFrame.showAndFocus(SbConstants.ViewName.CATEGORIES);
	}// GEN-LAST:event_tabCategoryActionPerformed
	private void tabChapterActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_tabChapterActionPerformed
		mainFrame.showAndFocus(SbConstants.ViewName.CHAPTERS);
	}// GEN-LAST:event_tabChapterActionPerformed
	private void tabGenderActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_tabGenderActionPerformed
		mainFrame.showAndFocus(SbConstants.ViewName.GENDERS);
	}// GEN-LAST:event_tabGenderActionPerformed
	private void tabIdeaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_tabIdeaActionPerformed
		mainFrame.showAndFocus(SbConstants.ViewName.IDEAS);
		// mainFrame.getBookController().showTaskList();
	}// GEN-LAST:event_tabIdeaActionPerformed
	private void tabItemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_tabItemActionPerformed
		mainFrame.showAndFocus(SbConstants.ViewName.ITEMS);
	}// GEN-LAST:event_tabItemActionPerformed
	private void tabItemLinkActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_tabItemLinkActionPerformed
		mainFrame.showAndFocus(SbConstants.ViewName.ITEMLINKS);
	}// GEN-LAST:event_tabItemLinkActionPerformed
	private void tabLocationActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_tabLocationActionPerformed
		mainFrame.showAndFocus(SbConstants.ViewName.LOCATIONS);
	}// GEN-LAST:event_tabLocationActionPerformed
	private void tabPartActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_tabPartActionPerformed
		mainFrame.showAndFocus(SbConstants.ViewName.PARTS);
	}// GEN-LAST:event_tabPartActionPerformed
	private void tabPersonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_tabPersonActionPerformed
		mainFrame.showAndFocus(SbConstants.ViewName.PERSONS);
	}// GEN-LAST:event_tabPersonActionPerformed
	private void tabRelationshipActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_tabRelationshipActionPerformed
		mainFrame.showAndFocus(ViewName.RELATIONSHIPS);
	}// GEN-LAST:event_tabRelationshipActionPerformed
	private void tabSceneActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_tabSceneActionPerformed
		mainFrame.showAndFocus(SbConstants.ViewName.SCENES);
	}// GEN-LAST:event_tabSceneActionPerformed
	private void tabStrandActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_tabStrandActionPerformed
		mainFrame.showAndFocus(SbConstants.ViewName.STRANDS);
	}// GEN-LAST:event_tabStrandActionPerformed
	private void tabTagActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_tabTagActionPerformed
		mainFrame.showAndFocus(SbConstants.ViewName.TAGS);
	}// GEN-LAST:event_tabTagActionPerformed
	private void tabTagLinkActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_tabTagLinkActionPerformed
		mainFrame.showAndFocus(SbConstants.ViewName.TAGLINKS);
	}// GEN-LAST:event_tabTagLinkActionPerformed
	private void toolsPlanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_toolsPlanActionPerformed
		mainFrame.showAndFocus(SbConstants.ViewName.PLAN);
	}// GEN-LAST:event_toolsPlanActionPerformed
	private void toolsTaskListActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_toolsTaskListActionPerformed
		mainFrame.showAndFocus(ViewName.SCENES);
		mainFrame.getBookController().showTaskList();
	}// GEN-LAST:event_toolsTaskListActionPerformed
	private void toolsUnicodeActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_toolsUnicodeActionPerformed
		mainFrame.showUnicodeDialog();
	}// GEN-LAST:event_toolsUnicodeActionPerformed
	private void translateMenu() {
		chartGantt.setText(I18N.getMsg("msg.menu.gantt"));
		chartOccurrenceOfLocations.setText(I18N.getMsg("msg.menu.tools.charts.overall.location.occurrence"));
		chartOccurrenceOfPersons.setText(I18N.getMsg("msg.menu.tools.charts.overall.character.occurrence"));
		chartPersonsByDate.setText(I18N.getMsg("msg.menu.tools.charts.overall.character.date"));
		chartPersonsByScene.setText(I18N.getMsg("msg.menu.tools.charts.part.character.scene"));
		chartStrandsByDate.setText(I18N.getMsg("msg.menu.tools.charts.overall.strand.date"));
		chartWIWW.setText(I18N.getMsg("msg.menu.tools.charts.overall.whoIsWhereWhen"));
		chartsAttributes.setText(I18N.getMsg("msg.attribute.list"));
		editCopyBlurb.setText(I18N.getMsg("msg.blurb.copy"));
		editCopyBook.setText(I18N.getMsg("msg.book.copy"));
		editCopyPersons.setText(I18N.getMsg("msg.dlg.copypersons.title"));
		editCopyLocations.setText(I18N.getMsg("msg.dlg.copylocations.title"));
		editCopyItems.setText(I18N.getMsg("msg.dlg.copyitems.title"));
		fileClose.setText(I18N.getMsg("msg.file.close"));
		fileExit.setText(I18N.getMsg("msg.common.exit"));
		fileExport.setText(I18N.getMsg("msg.file.export"));
		fileImport.setText(I18N.getMsg("msg.file.import"));
		fileNew.setText(I18N.getMsg("msg.file.new"));
		fileOpen.setText(I18N.getMsg("msg.file.open"));
		fileOpenRecent.setText(I18N.getMsg("msg.file.open.recent"));
		filePrint.setText(I18N.getMsg("msg.file.print"));
		fileProperties.setText(I18N.getMsg("msg.file.properties"));
		fileRename.setText(I18N.getMsg("msg.common.rename"));
		fileSave.setText(I18N.getMsg("msg.file.save"));
		fileSaveAs.setText(I18N.getMsg("msg.file.save.as"));
		helpAbout.setText(I18N.getMsg("msg.menu.help.about"));
		helpCheckUpdates.setText(I18N.getMsg("msg.menu.help.update"));
		helpDoc.setText(I18N.getMsg("msg.menu.help.doc"));
		helpFaq.setText(I18N.getMsg("msg.menu.help.faq"));
		helpHome.setText(I18N.getMsg("msg.menu.help.homepage"));
		helpReportBug.setText(I18N.getMsg("msg.menu.help.bug"));
		menuCharts.setText(I18N.getMsg("msg.menu.charts"));
		menuEdit.setText(I18N.getMsg("msg.common.edit"));
		menuFile.setText(I18N.getMsg("msg.menu.file"));
		menuHelp.setText(I18N.getMsg("msg.menu.help"));
		menuNewEntity.setText(I18N.getMsg("msg.common.new.object"));
		menuParts.setText(I18N.getMsg("msg.menu.parts"));
		menuPrimaryObjects.setText(I18N.getMsg("msg.common.primary.objects"));
		menuSecondaryObjects.setText(I18N.getMsg("msg.common.secondary.objects"));
		menuView.setText(I18N.getMsg("msg.menu.view"));
		menuWindow.setText(I18N.getMsg("msg.common.window"));
		newCategory.setText(I18N.getMsg("msg.persons.category"));
		newChapter.setText(I18N.getMsg("msg.common.chapter"));
		newChapters.setText(I18N.getMsg("msg.generate.chapters"));
		newFOI.setText(I18N.getMsg("msg.foi.title"));
		newGender.setText(I18N.getMsg("msg.common.genders"));
		newRelationships.setText(I18N.getMsg("msg.common.relationship"));
		newIdea.setText(I18N.getMsg("msg.idea.table.idea"));
		newItem.setText(I18N.getMsg("msg.item"));
		newItemLink.setText(I18N.getMsg("msg.item.link"));
		newLocation.setText(I18N.getMsg("msg.common.location"));
		newPart.setText(I18N.getMsg("msg.common.part"));
		newPerson.setText(I18N.getMsg("msg.common.person"));
		newScene.setText(I18N.getMsg("msg.common.scene"));
		newStrand.setText(I18N.getMsg("msg.common.strand"));
		newTag.setText(I18N.getMsg("msg.tag"));
		newTagLink.setText(I18N.getMsg("msg.tag.link"));
		partNext.setText(I18N.getMsg("msg.common.part.next"));
		partPrevious.setText(I18N.getMsg("msg.common.part.previous"));
		renameCity.setText(I18N.getMsg("msg.location.rename.city"));
		renameCountry.setText(I18N.getMsg("msg.location.rename.country"));
		renameItemCategory.setText(I18N.getMsg("msg.item.rename.category"));
		renameTagCategory.setText(I18N.getMsg("msg.tag.rename.category"));
		tabCategory.setText(I18N.getMsg("msg.persons.category"));
		tabChapter.setText(I18N.getMsg("msg.common.chapter"));
		tabGender.setText(I18N.getMsg("msg.common.genders"));
		tabIdea.setText(I18N.getMsg("msg.idea.table.idea"));
		tabItem.setText(I18N.getMsg("msg.item"));
		tabItemLink.setText(I18N.getMsg("msg.item.link"));
		tabLocation.setText(I18N.getMsg("msg.common.location"));
		tabPart.setText(I18N.getMsg("msg.common.part"));
		tabPerson.setText(I18N.getMsg("msg.common.person"));
		tabRelationship.setText(I18N.getMsg("msg.relationship"));
		tabScene.setText(I18N.getMsg("msg.common.scene"));
		tabStrand.setText(I18N.getMsg("msg.common.strand"));
		tabTag.setText(I18N.getMsg("msg.tag"));
		tabTagLink.setText(I18N.getMsg("msg.tag.link"));
		toolsPlan.setText(I18N.getMsg("msg.menu.view.plan"));
		toolsTaskList.setText(I18N.getMsg("msg.tasklist.title"));
		vueBook.setText(I18N.getMsg("msg.menu.view.book"));
		vueChrono.setText(I18N.getMsg("msg.menu.view.chrono"));
		vueEditor.setText(I18N.getMsg("msg.common.editor"));
		vueInfo.setText(I18N.getMsg("msg.info.title"));
		vueManageScene.setText(I18N.getMsg("msg.menu.view.manage"));
		vueMemoria.setText(I18N.getMsg("msg.menu.view.pov"));
		vueNavigation.setText(I18N.getMsg("msg.common.navigation"));
		vueReading.setText(I18N.getMsg("msg.menu.view.reading"));
		vueTree.setText(I18N.getMsg("msg.common.tree"));
		windowBook.setText(I18N.getMsg("msg.layout.book.only"));
		windowChrono.setText(I18N.getMsg("msg.layout.chrono.only"));
		windowDefaultLayout.setText(I18N.getMsg("msg.layout.default"));
		windowLoadLayout.setText(I18N.getMsg("msg.docking.load.layout"));
		windowManage.setText(I18N.getMsg("msg.layout.manage.only"));
		windowManageLayouts.setText(I18N.getMsg("msg.docking.manage.layouts"));
		windowPersonsAndLocations.setText(I18N.getMsg("msg.layout.persons.locations"));
		windowPreferences.setText(I18N.getMsg("msg.dlg.preference.title"));
		windowReading.setText(I18N.getMsg("msg.layout.reading.only"));
		windowRefresh.setText(I18N.getMsg("msg.common.refresh"));
		windowResetLayout.setText(I18N.getMsg("msg.docking.reset.layout"));
		windowSaveLayout.setText(I18N.getMsg("msg.docking.save.layout"));
		windowTagsAndItems.setText(I18N.getMsg("msg.layout.tags.items"));
		if (SbApp.isDevTest() == false)
			devTest.setVisible(false);
	}
	private void vueBookActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_vueBookActionPerformed
		mainFrame.showAndFocus(SbConstants.ViewName.BOOK);
	}// GEN-LAST:event_vueBookActionPerformed
	private void vueChronoActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_vueChronoActionPerformed
		mainFrame.showAndFocus(SbConstants.ViewName.CHRONO);
	}// GEN-LAST:event_vueChronoActionPerformed
	private void vueEditorActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_vueEditorActionPerformed
		// mainFrame.showAndFocus(SbConstants.ViewName.EDITOR);
	}// GEN-LAST:event_vueEditorActionPerformed
	private void vueInfo1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_vueInfo1ActionPerformed
		mainFrame.showAndFocus(SbConstants.ViewName.MEMOS);
	}// GEN-LAST:event_vueInfo1ActionPerformed
	private void vueInfoActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_vueInfoActionPerformed
		mainFrame.showAndFocus(SbConstants.ViewName.INFO);
	}// GEN-LAST:event_vueInfoActionPerformed
	private void vueManageSceneActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_vueManageSceneActionPerformed
		mainFrame.showAndFocus(SbConstants.ViewName.MANAGE);
	}// GEN-LAST:event_vueManageSceneActionPerformed
	private void vueMemoriaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_vueMemoriaActionPerformed
		mainFrame.showAndFocus(SbConstants.ViewName.MEMORIA);
	}// GEN-LAST:event_vueMemoriaActionPerformed
	private void vueNavigationActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_vueNavigationActionPerformed
		mainFrame.showAndFocus(SbConstants.ViewName.NAVIGATION);
	}// GEN-LAST:event_vueNavigationActionPerformed
	private void vueReadingActionPerformed(java.awt.event.ActionEvent evt) {
		mainFrame.showAndFocus(SbConstants.ViewName.READING);
	}
	private void vueTreeActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_vueTreeActionPerformed
		mainFrame.showAndFocus(SbConstants.ViewName.TREE);
	}// GEN-LAST:event_vueTreeActionPerformed
	private void windowBookActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_windowBookActionPerformed
		DockingWindowUtil.setLayout(mainFrame, DockingWindowUtil.BOOK_ONLY_LAYOUT);
	}// GEN-LAST:event_windowBookActionPerformed
	private void windowChronoActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_windowChronoActionPerformed
		DockingWindowUtil.setLayout(mainFrame, DockingWindowUtil.CHRONO_ONLY_LAYOUT);
	}// GEN-LAST:event_windowChronoActionPerformed
	private void windowDefaultLayoutActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_windowDefaultLayoutActionPerformed
		DockingWindowUtil.setLayout(mainFrame, DockingWindowUtil.DEFAULT_LAYOUT);
	}// GEN-LAST:event_windowDefaultLayoutActionPerformed
	private void windowManageActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_windowManageActionPerformed
		DockingWindowUtil.setLayout(mainFrame, DockingWindowUtil.MANAGE_ONLY_LAYOUT);
	}// GEN-LAST:event_windowManageActionPerformed
	private void windowManageLayoutsActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_windowManageLayoutsActionPerformed
		ManageLayoutsDialog dlg = new ManageLayoutsDialog(mainFrame);
		SwingUtil.showModalDialog(dlg, mainFrame);
	}// GEN-LAST:event_windowManageLayoutsActionPerformed
	private void windowPersonsAndLocationsActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_windowPersonsAndLocationsActionPerformed
		DockingWindowUtil.setLayout(mainFrame, DockingWindowUtil.PERSONS_LOCATIONS_LAYOUT);
	}// GEN-LAST:event_windowPersonsAndLocationsActionPerformed
	private void windowPlanActionPerformed(java.awt.event.ActionEvent evt) {
		mainFrame.showAndFocus(SbConstants.ViewName.PLAN);
	}
	private void windowPreferencesActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_windowPreferencesActionPerformed
		PreferencesDialog dlg = new PreferencesDialog();
		SwingUtil.showModalDialog(dlg, mainFrame);
	}// GEN-LAST:event_windowPreferencesActionPerformed
	private void windowReadingActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_windowReadingActionPerformed
		DockingWindowUtil.setLayout(mainFrame, DockingWindowUtil.READING_ONLY_LAYOUT);
	}// GEN-LAST:event_windowReadingActionPerformed
	private void windowRefreshActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_windowRefreshActionPerformed
		mainFrame.refresh();
	}// GEN-LAST:event_windowRefreshActionPerformed
	private void windowResetLayoutActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_windowResetLayoutActionPerformed
		SwingUtil.setWaitingCursor(mainFrame);
		mainFrame.setDefaultLayout();
		SwingUtil.setDefaultCursor(mainFrame);
	}// GEN-LAST:event_windowResetLayoutActionPerformed

	private void windowSaveLayoutActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_windowSaveLayoutActionPerformed
		String name = JOptionPane.showInputDialog(mainFrame, I18N.getMsgColon("msg.common.enter.name"),
				I18N.getMsg("msg.docking.save.layout"), JOptionPane.PLAIN_MESSAGE);
		if (name != null) {
			DockingWindowUtil.saveLayout(mainFrame, name);
		}
	}// GEN-LAST:event_windowSaveLayoutActionPerformed

	private void windowTagsAndItemsActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_windowTagsAndItemsActionPerformed
		DockingWindowUtil.setLayout(mainFrame, DockingWindowUtil.TAGS_ITEMS_LAYOUT);
	}// GEN-LAST:event_windowTagsAndItemsActionPerformed

	private void windowTimeEventActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_vueReadingActionPerformed
		mainFrame.showAndFocus(SbConstants.ViewName.TIMEEVENT);
	}// GEN-LAST:event_vueReadingActionPerformed
}
