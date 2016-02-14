/*
 Storybook: Open Source software for novelists and authors.
 Copyright (C) 2008 - 2012 Martin Mustun

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
/* vérification OK */

package storybook.action;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import org.apache.commons.io.FileUtils;
import org.hibernate.Session;

import com.sun.jaf.ui.ActionManager;

import net.infonode.docking.View;
import storybook.SbApp;
import storybook.SbConstants;
import storybook.SbConstants.BookKey;
import storybook.SbConstants.ViewName;
import storybook.model.BookModel;
import storybook.model.DbFile;
import storybook.model.EntityUtil;
import storybook.model.hbn.dao.PartDAOImpl;
import storybook.model.hbn.entity.Internal;
import storybook.model.hbn.entity.Part;
import storybook.toolkit.BookUtil;
import storybook.toolkit.EnvUtil;
import storybook.toolkit.I18N;
import storybook.toolkit.odt.ODTUtils;
import storybook.toolkit.swing.SwingUtil;
import storybook.ui.MainFrame;
import storybook.ui.dialog.BookPropertiesDialog;
import storybook.ui.dialog.CreateChaptersDialog;
import storybook.ui.dialog.PreferencesDialog;
import storybook.ui.dialog.WaitDialog;
import storybook.ui.dialog.file.ExportBookFileDialog;
import storybook.ui.dialog.file.RenameFileDialog;
import storybook.ui.dialog.file.SaveAsFileDialog;
import storybook.ui.dialog.rename.RenameCityDialog;
import storybook.ui.dialog.rename.RenameCountryDialog;
import storybook.ui.dialog.rename.RenameItemCategoryDialog;
import storybook.ui.dialog.rename.RenameTagCategoryDialog;

/**
 * @author martin
 *
 */
public class ActionHandler {

	private final MainFrame mainFrame;

	public ActionHandler(MainFrame mainframe) {
		mainFrame = mainframe;
	}

	/*
	 * public void handleCheckUpdate() {//new OK if (Updater.checkForUpdate()) {
	 * JOptionPane.showMessageDialog(mainFrame,
	 * I18N.getMsg("msg.update.no.text"), I18N.getMsg("msg.update.no.title"),
	 * JOptionPane.INFORMATION_MESSAGE); } }
	 */

	public void handleBookProperties() {// new OK
		BookPropertiesDialog dlg = new BookPropertiesDialog(mainFrame);
		SwingUtil.showModalDialog(dlg, mainFrame);
	}

	public void handleChangePart(Part part) {
		mainFrame.setWaitingCursor();
		Part currentPart = mainFrame.getCurrentPart();
		if (currentPart.getId().equals(part.getId())) {
			// same part
			return;
		}
		mainFrame.setCurrentPart(part);
		mainFrame.setTitle();
		mainFrame.getBookController().changePart(part);
		mainFrame.setDefaultCursor();
	}

	public void handleClose() {
		mainFrame.close(true);
	}

	public void handleCreateChapters() {
		CreateChaptersDialog dlg = new CreateChaptersDialog(mainFrame);
		SwingUtil.showModalDialog(dlg, mainFrame);
	}

	public void handleDummy() {
		SbApp.trace("ActionHandler.handleDummy(): ");
		/*
		 * try { SbView view = mainFrame.getView(SbConstants.ViewName.EDITOR);
		 * mainFrame.getViewFactory().unloadView(view); } catch (Exception ex) {
		 * SbApp.error("ActionHandler.handleDummy()",ex); }
		 */
	}

	public void handleDumpAttachedViews() {
		mainFrame.getBookController().printAttachedViews();
	}
	/*
	 * suppression du garbage collector public void handleRunGC() {
	 * SwingUtil.printMemoryUsage(); System.out.println(
	 * "ActionHandler.handleRunGC(): running GC...");
	 * SbApp.getInstance().runGC(); SwingUtil.printMemoryUsage(); }
	 */

	public void handleFileExportBook() {
		ExportBookFileDialog dlg = new ExportBookFileDialog(mainFrame);
		SwingUtil.showModalDialog(dlg, mainFrame);
		if (dlg.isCanceled()) {
			return;
		}
		File outFile = dlg.getFile();
		String sceneSeparator = dlg.getSceneSeparator();
		ODTUtils.createBookFile(mainFrame, outFile, sceneSeparator);
	}

	public void handleFileRename() {
		RenameFileDialog dlg = new RenameFileDialog(mainFrame);
		dlg.setForceDbExtension(false);
		dlg.setDefaultDBExt(mainFrame.getDbFile().getExt());
		SwingUtil.showModalDialog(dlg, mainFrame);
		if (dlg.isCanceled()) {
			return;
		}
		File outFile = dlg.getFile();
		SbApp.getInstance().renameFile(mainFrame, outFile);
	}

	public void handleFileSave() {// new OK
		WaitDialog dlg = new WaitDialog(mainFrame, I18N.getMsg("msg.file.saving"));
		Timer timer = new Timer(500, new DisposeDialogAction(dlg));
		timer.setRepeats(false);
		timer.start();
		SwingUtil.showModalDialog(dlg, mainFrame);
	}

	public void handleFileSaveAs() {
		SaveAsFileDialog dlg = new SaveAsFileDialog(mainFrame);
		dlg.setForceDbExtension(false);
		dlg.setDefaultDBExt(mainFrame.getDbFile().getExt());
		SwingUtil.showModalDialog(dlg, mainFrame);
		if (dlg.isCanceled()) {
			return;
		}
		File outFile = dlg.getFile();
		File inFile = mainFrame.getDbFile().getFile();
		mainFrame.close(false);
		try {
			FileUtils.copyFile(inFile, outFile);
		} catch (IOException ioe) {
			System.err.println("ActionHandler.handleSaveAs() IOex : " + ioe.getMessage());
		}
		DbFile dbFile = new DbFile(outFile);
		SbApp.getInstance().openFile(dbFile);
	}

	public void handleHtml2Text() {
		int n = SwingUtil.showBetaDialog(mainFrame);
		if (n == JOptionPane.NO_OPTION || n == JOptionPane.CLOSED_OPTION) {
			return;
		}
		mainFrame.setWaitingCursor();
		EntityUtil.convertHtmlToPlainText(mainFrame);
		mainFrame.refresh();
		mainFrame.setDefaultCursor();
	}

	public void handleNextPart() {
		Part currentPart = mainFrame.getCurrentPart();
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		PartDAOImpl dao = new PartDAOImpl(session);
		List<Part> parts = dao.findAll();
		int index = parts.indexOf(currentPart);
		if (index == parts.size() - 1) {
			// already last part
			return;
		}
		++index;
		handleChangePart(parts.get(index));
	}

	public void handleOpenExportFolder() {
		try {
			Internal internal = BookUtil.get(mainFrame, BookKey.EXPORT_DIRECTORY,
					EnvUtil.getDefaultExportDir(mainFrame));
			Desktop.getDesktop().open(new File(internal.getStringValue()));
		} catch (IOException | Error ex) {
			SbApp.error("ActionHandler.handleExportDir()", (Exception) ex);
		}
	}

	public void handlePreferences() {// new OK
		PreferencesDialog dlg = new PreferencesDialog();
		SwingUtil.showModalDialog(dlg, mainFrame);
	}

	public void handlePreviousPart() {
		Part currentPart = mainFrame.getCurrentPart();
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		PartDAOImpl dao = new PartDAOImpl(session);
		List<Part> parts = dao.findAll();
		int index = parts.indexOf(currentPart);
		if (index == 0) {
			// already first part
			return;
		}
		--index;
		handleChangePart(parts.get(index));
	}

	public void handleRecentClear() {
		SbApp.getInstance().clearRecentFiles();
	}

	public void handleRenameCity() {
		RenameCityDialog dlg = new RenameCityDialog(mainFrame);
		ActionManager actMngr = mainFrame.getSbActionManager().getActionManager();
		Action act = actMngr.getAction("rename-city-command");
		Object obj = act.getValue(SbConstants.ActionKey.CATEGORY.toString());
		if (obj != null) {
			dlg.setSelectedItem(obj);
		}
		SwingUtil.showModalDialog(dlg, mainFrame);
		act.putValue(SbConstants.ActionKey.CATEGORY.toString(), null);
	}

	public void handleRenameCountry() {
		RenameCountryDialog dlg = new RenameCountryDialog(mainFrame);
		ActionManager actMngr = mainFrame.getSbActionManager().getActionManager();
		Action act = actMngr.getAction("rename-country-command");
		Object obj = act.getValue(SbConstants.ActionKey.CATEGORY.toString());
		if (obj != null) {
			dlg.setSelectedItem(obj);
		}
		SwingUtil.showModalDialog(dlg, mainFrame);
		act.putValue(SbConstants.ActionKey.CATEGORY.toString(), null);
	}

	public void handleRenameItemCategory() {
		RenameItemCategoryDialog dlg = new RenameItemCategoryDialog(mainFrame);
		ActionManager actMngr = mainFrame.getSbActionManager().getActionManager();
		Action act = actMngr.getAction("rename-item-category-command");
		Object obj = act.getValue(SbConstants.ActionKey.CATEGORY.toString());
		if (obj != null) {
			dlg.setSelectedItem(obj);
		}
		SwingUtil.showModalDialog(dlg, mainFrame);
		act.putValue(SbConstants.ActionKey.CATEGORY.toString(), null);
	}

	public void handleRenameTagCategory() {
		RenameTagCategoryDialog dlg = new RenameTagCategoryDialog(mainFrame);
		ActionManager actMngr = mainFrame.getSbActionManager().getActionManager();
		Action act = actMngr.getAction("rename-tag-category-command");
		Object obj = act.getValue(SbConstants.ActionKey.CATEGORY.toString());
		if (obj != null) {
			dlg.setSelectedItem(obj);
		}
		SwingUtil.showModalDialog(dlg, mainFrame);
		act.putValue(SbConstants.ActionKey.CATEGORY.toString(), null);
	}

	public void handleShowAttributesView() {
		showAndFocus(ViewName.ATTRIBUTES);
	}

	public void handleShowBookView() {// new OK
		showAndFocus(ViewName.BOOK);
	}

	public void handleShowCategories() {
		showAndFocus(ViewName.CATEGORIES);
	}

	public void handleShowChapters() {
		showAndFocus(ViewName.CHAPTERS);
	}

	public void handleShowChronoView() {// new OK
		showAndFocus(ViewName.CHRONO);
	}

	public void handleShowEditor() {
		mainFrame.showEditor();
	}

	public void handleShowGenders() {
		showAndFocus(ViewName.GENDERS);
	}

	public void handleShowIdeas() {
		showAndFocus(ViewName.IDEAS);
	}

	public void handleShowInfo() {
		showAndFocus(ViewName.INFO);
	}

	public void handleShowInternals() {
		showAndFocus(ViewName.INTERNALS);
	}

	public void handleShowItemLinks() {
		showAndFocus(ViewName.ITEMLINKS);
	}

	public void handleShowItems() {
		showAndFocus(ViewName.ITEMS);
	}

	public void handleShowLocations() {
		showAndFocus(ViewName.LOCATIONS);
	}

	public void handleShowManageView() {// new OK
		showAndFocus(ViewName.MANAGE);
	}

	public void handleShowMemo() {
		showAndFocus(ViewName.MEMOS);
	}

	public void handleShowMemoria() {
		showAndFocus(ViewName.MEMORIA);
	}

	public void handleShowNavigation() {
		showAndFocus(ViewName.NAVIGATION);
	}

	public void handleShowParts() {
		showAndFocus(ViewName.PARTS);
	}

	public void handleShowPersons() {
		showAndFocus(ViewName.PERSONS);
	}

	public void handleShowReadingView() {// new OK
		showAndFocus(ViewName.READING);
	}

	public void handleShowScenes() {
		showAndFocus(ViewName.SCENES);
	}

	public void handleShowStrands() {
		showAndFocus(ViewName.STRANDS);
	}

	public void handleShowTagLinks() {
		showAndFocus(ViewName.TAGLINKS);
	}

	public void handleShowTags() {
		showAndFocus(ViewName.TAGS);
	}

	public void handleShowTree() {
		showAndFocus(ViewName.TREE);
	}

	public void handleText2Html() {
		int n = SwingUtil.showBetaDialog(mainFrame);
		if (n == JOptionPane.NO_OPTION || n == JOptionPane.CLOSED_OPTION) {
			return;
		}
		mainFrame.setWaitingCursor();
		EntityUtil.convertPlainTextToHtml(mainFrame);
		mainFrame.refresh();
		mainFrame.setDefaultCursor();
	}

	public void handleViewStatus(boolean selected) {
	}

	private void showAndFocus(ViewName viewName) {
		View view = mainFrame.getView(viewName);
		view.restore();
		view.restoreFocus();
	}

	/*
	 * public void handleCopyBookText() {//new OK BookExporter exp = new
	 * BookExporter(mainFrame); exp.setExportForOpenOffice(false);
	 * exp.exportToClipboard(); }
	 * 
	 * public void handleCopyBlurb() {//new OK Internal internal =
	 * BookUtil.get(mainFrame,BookKey.BLURB, ""); StringSelection selection =
	 * new StringSelection(internal.getStringValue() + "\n"); Clipboard clbrd =
	 * Toolkit.getDefaultToolkit().getSystemClipboard();
	 * clbrd.setContents(selection, selection); }
	 */

}
