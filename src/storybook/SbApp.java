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

package storybook;

import java.awt.Component;
import java.awt.Font;
import java.awt.HeadlessException;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.plaf.FontUIResource;

import org.apache.commons.io.FileUtils;

import storybook.SbConstants.BookKey;
import storybook.SbConstants.PreferenceKey;
import storybook.controller.PreferenceController;
import storybook.model.DbFile;
import storybook.model.PreferenceModel;
import storybook.model.hbn.entity.Preference;
import storybook.model.oldModel.ModelMigration;
import storybook.toolkit.BookUtil;
import storybook.toolkit.I18N;
import storybook.toolkit.OSNativeUtils;
import storybook.toolkit.PrefUtil;
import storybook.toolkit.net.Updater;
import storybook.toolkit.swing.SwingUtil;
import storybook.ui.MainFrame;
import storybook.ui.dialog.ExceptionDialog;
import storybook.ui.dialog.FirstStartDialog;
import storybook.ui.dialog.PostModelUpdateDialog;
import storybook.ui.dialog.file.NewFileDialog;

// TODO: Auto-generated Javadoc
/**
 * The Class SbApp.
 */
public class SbApp extends Component {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3654051279782138653L;

	/** The b trace. */
	private static boolean bTrace;

	/** The b trace hibernate. */
	private static boolean bTraceHibernate;

	/** The instance. */
	private static SbApp instance;

	/** The i18n file. */
	private static String i18nFile;

	/** The b dev test. */
	private static boolean bDevTest;

	/**
	 * Error.
	 *
	 * @param txt
	 *            the txt
	 * @param e
	 *            the e
	 */
	public static void error(String txt, Exception e) {
		System.err.println(txt + " Exception:" + e.getMessage());
	}

	/**
	 * Gets the i18n file.
	 *
	 * @return the i18n file
	 */
	public static String getI18nFile() {
		return i18nFile;
	}

	/**
	 * Gets the single instance of SbApp.
	 *
	 * @return single instance of SbApp
	 */
	/*
	 * suppression de l'appel du garbage collector public void runGC(){
	 * System.gc(); }
	 */
	public static SbApp getInstance() {
		if (instance == null) {
			instance = new SbApp();
		}
		return instance;
	}

	/**
	 * Gets the trace.
	 *
	 * @return the trace
	 */
	public static boolean getTrace() {
		return bTrace;
	}

	/**
	 * Gets the trace hibernate.
	 *
	 * @return the trace hibernate
	 */
	public static boolean getTraceHibernate() {
		return bTraceHibernate;
	}

	/**
	 * Checks if is dev test.
	 *
	 * @return true, if is dev test
	 */
	public static boolean isDevTest() {
		return bDevTest;
	}

	/**
	 * Lock instance.
	 *
	 * @param lockFile
	 *            the lock file
	 * @return true, if successful
	 */
	private static boolean lockInstance(final String lockFile) {
		try {
			final File file = new File(lockFile);
			final RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
			final FileLock fileLock = randomAccessFile.getChannel().tryLock();
			if (fileLock != null) {
				Runtime.getRuntime().addShutdownHook(new Thread() {
					@Override
					public void run() {
						try {
							fileLock.release();
							randomAccessFile.close();
							file.delete();
						} catch (IOException e) {
							System.err.println("Unable to remove lock file: " + lockFile + "->" + e.getMessage());
						}
					}
				});
				return true;
			}
		} catch (IOException e) {
			System.err.println("Unable to create and/or lock file: " + lockFile + "->" + e.getMessage());
		}
		return false;
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		// Create Application Adapter (only needed for OsX and register
		// as listener of Application events (storybook.toolkit.MacAdapter)
		if (OSNativeUtils.isMac()) {

			boolean regAdapterOK = OSNativeUtils.registerMacAdapter(instance);
			trace("boolean regAdapterOK =" + regAdapterOK);
		}

		String tempDir = System.getProperty("java.io.tmpdir");
		String fn = tempDir + File.separator + "storybook.lck";
		if (args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				if ("--trace".equalsIgnoreCase(args[i])) {
					SbApp.bTrace = true;
					System.out.println("Storybook execution in trace mode");
				}
				if ("--hibernate".equalsIgnoreCase(args[i])) {
					SbApp.bTraceHibernate = true;
					System.out.println("Hibernate in trace mode");
				}
				if ("--dev".equalsIgnoreCase(args[i])) {
					SbApp.bDevTest = true;
					System.out.println("Development test");
				}
				if ("--msg".equalsIgnoreCase(args[i])) {
					File f = new File(args[i + 1] + ".properties");
					if (!f.exists()) {
						System.out.println("Msg test file not exists : " + args[i + 1]);
					} else {
						SbApp.i18nFile = args[i + 1];
						System.out.println("Msg test file is : " + SbApp.i18nFile);
					}
				}
			}
		}
		if (!lockInstance(fn)) {
			Object[] options = { I18N.getMsg("msg.running.remove"), I18N.getMsg("msg.common.cancel") };
			int n = JOptionPane.showOptionDialog(null, I18N.getMsg("msg.running.msg"), I18N.getMsg("msg.running.title"),
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
			if (n == 0) {
				File file = new File(fn);
				if (file.exists() && file.canWrite() && !file.delete()) {
					JOptionPane.showMessageDialog(null, "Delete failed",
							"File\n" + file.getAbsolutePath() + "\ncould not be deleted.", JOptionPane.ERROR_MESSAGE);
				}
			}
			return;
		}

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				SbApp app = SbApp.getInstance();
				app.init();
			}
		});
	}

	/**
	 * Sets the i18n file.
	 *
	 * @param file
	 *            the new i18n file
	 */
	public static void setI18nFile(String file) {
		i18nFile = file;
	}

	/**
	 * Sets the trace.
	 *
	 * @param b
	 *            the new trace
	 */
	public static void setTrace(boolean b) {
		bTrace = b;
		System.out.println((b ? "Enter" : "Exit") + " trace mode");
	}

	/**
	 * Trace.
	 *
	 * @param msg
	 *            the msg
	 */
	public static void trace(String msg) {
		if (bTrace) {
			System.out.println(msg);
		}
	}

	/** The preference model. */
	private PreferenceModel preferenceModel;

	/** The preference controller. */
	private PreferenceController preferenceController;

	/** The main frame. */
	private static MainFrame mainFrame;

	/** The default font. */
	private Font defaultFont;

	/**
	 * Instantiates a new sb app.
	 */
	private SbApp() {
		SbApp.mainFrame = new MainFrame();
	}

	/**
	 * Check if already opened.
	 *
	 * @param dbName
	 *            the db name
	 * @return true, if successful
	 */
	private boolean checkIfAlreadyOpened(String dbName) {
		trace("SbApp.checkIfAlreadyOpened(" + dbName + ")");
		if (mainFrame.getViewFactory() != null) {
			if (getMainFrame().getDbFile().getDbName().equals(dbName)) {
				getMainFrame().setVisible(true);
				return true;
			}
		}
		return false;
	}

	/**
	 * Clear recent files.
	 */
	public void clearRecentFiles() {
		trace("SbApp.clearRecentFiles()");
		PrefUtil.setDbFileList(new ArrayList<DbFile>());
		reloadMenuBars();
	}

	/**
	 * Creates a new file.
	 */
	public void createNewFile() {
		trace("SbApp.createNewFile()");
		try {
			NewFileDialog dlg = new NewFileDialog();
			SwingUtil.showModalDialog(dlg, null);
			if (dlg.isCanceled()) {
				return;
			}
			DbFile dbFile = new DbFile(dlg.getFile());
			String dbName = dbFile.getDbName();
			if (dbName == null) {
				return;
			}
			removeMainFrame(mainFrame);
			mainFrame = new MainFrame();
			mainFrame.init(dbFile);
			mainFrame.getBookModel().initEntites();
			BookUtil.store(mainFrame, BookKey.USE_HTML_SCENES, dlg.getUseHtmlScenes());
			BookUtil.store(mainFrame, BookKey.USE_HTML_DESCR, dlg.getUseHtmlDescr());
			BookUtil.store(mainFrame, BookKey.BOOK_CREATION_DATE, new SimpleDateFormat("dd/MM/yy").format(new Date()));
			mainFrame.initUi();
			mainFrame.getBookController().fireAgain();
			updateFilePref(dbFile);
			setDefaultCursor();
		} catch (Exception e) {
			error("SbApp.createNewFile()", e);
		}
	}

	/**
	 * Exit.
	 */
	public void exit() {
		trace("SbApp.exit()");
		Preference pref = PrefUtil.get(PreferenceKey.CONFIRM_EXIT, true);
		if (pref.getBooleanValue()) {
			int n = JOptionPane.showConfirmDialog(null, I18N.getMsg("msg.mainframe.want.exit"),
					I18N.getMsg("msg.common.exit"), JOptionPane.YES_NO_OPTION);
			if (n == JOptionPane.NO_OPTION || n == JOptionPane.CLOSED_OPTION) {
				return;
			}
		}
		saveAll();
		System.exit(0);
	}

	/**
	 * Gets the default font.
	 *
	 * @return the default font
	 */
	public Font getDefaultFont() {
		return this.defaultFont;
	}

	/**
	 * Gets the preference controller.
	 *
	 * @return the preference controller
	 */
	public PreferenceController getPreferenceController() {
		return preferenceController;
	}

	/**
	 * Gets the preference model.
	 *
	 * @return the preference model
	 */
	public PreferenceModel getPreferenceModel() {
		return preferenceModel;
	}

	/**
	 * Inits the.
	 */
	private void init() {
		trace("SbApp.init()");
		try {
			// preference model and controller
			trace("-->PreferenceController()");
			preferenceController = new PreferenceController();
			trace("-->PreferenceModel()");
			preferenceModel = new PreferenceModel(mainFrame);
			trace("-->PreferenceModel.attachModel()");
			preferenceController.attachModel(preferenceModel);
			trace("-->PreferenceController.attachView()");
			preferenceController.attachView(this);
			trace("-->initI18N()");
			initI18N();
			restoreDefaultFont();
			// first start dialog
			Preference prefFirstStart = PrefUtil.get(PreferenceKey.FIRST_START_4, true);
			if (prefFirstStart.getBooleanValue()) {
				FirstStartDialog dlg = new FirstStartDialog();
				SwingUtil.showModalDialog(dlg, null);
				PrefUtil.set(PreferenceKey.FIRST_START_4, false);
			}

			Preference pref = PrefUtil.get(PreferenceKey.OPEN_LAST_FILE, false);
			if (pref.getBooleanValue()) {
				Preference pref2 = PrefUtil.get(PreferenceKey.LAST_OPEN_FILE, "");
				DbFile dbFile = new DbFile(pref2.getStringValue());
				trace("SbApp.init(): loading... " + dbFile);
				openFile(dbFile);
			}
			Preference pref2 = PrefUtil.get(PreferenceKey.LAST_OPEN_FILE, "");
			DbFile dbFile = new DbFile(pref2.getStringValue());
			trace("SbApp.init(): loading default db. " + dbFile);
			openFile(dbFile);

			/*
			 * abandon de l'appel au garbarge collector, utilisation non
			 * recommandée Timer t1 = new Timer(10000, new ActionListener() {
			 * 
			 * @Override public void actionPerformed(ActionEvent e) { runGC(); }
			 * }); t1.start();
			 */

		} catch (Exception e) {
			error("SbApp.init()", e);
			// ExceptionDialog dlg = new ExceptionDialog(e);
			// SwingUtil.showModalDialog(dlg, null);
		}
	}

	/**
	 * Inits the i18 n.
	 */
	public void initI18N() {
		trace("SbApp.initI18N()");
		String localeStr = PrefUtil.get(PreferenceKey.LANG, SbConstants.DEFAULT_LANG).getStringValue();
		SbConstants.Language lang = SbConstants.Language.valueOf(localeStr);
		Locale locale = lang.getLocale();
		setLocale(locale);
		I18N.initResourceBundles(getLocale());
	}

	/**
	 * Model property change.
	 *
	 * @param evt
	 *            the evt
	 */
	public void modelPropertyChange(PropertyChangeEvent evt) {
		// works, but currently not used
		// may be used for entity copying between files
		// String propName = evt.getPropertyName();
		// Object newValue = evt.getNewValue();
		// Object oldValue = evt.getOldValue();
	}

	/**
	 * Open file.
	 *
	 * @return true, if successful
	 */
	public boolean openFile() {
		trace("SbApp.openFile()");
		final DbFile dbFile = BookUtil.openDocumentDialog();
		return dbFile != null && openFile(dbFile);
	}

	/**
	 * Open file.
	 *
	 * @param dbFile
	 *            the db file
	 * @return true, if successful
	 */
	public boolean openFile(final DbFile dbFile) {
		trace("SbApp.openFile(" + dbFile.getDbName() + ")");
		try {
			// file doesn't exist
			if (!dbFile.getFile().exists()) {
				String txt = I18N.getMsg("msg.dlg.project.not.exits.text", dbFile.getFile().getPath());
				JOptionPane.showMessageDialog(null, txt, I18N.getMsg("msg.dlg.project.not.exits.title"),
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
			// file is read-only
			if (!dbFile.getFile().canWrite()) {
				String txt = I18N.getMsg("msg.error.db.read.only", dbFile.getFile().getPath());
				JOptionPane.showMessageDialog(null, txt, I18N.getMsg("msg.common.warning"), JOptionPane.ERROR_MESSAGE);
				return false;
			}
			// file already opened
			String dbName = dbFile.getDbName();
			if (checkIfAlreadyOpened(dbName)) {
				return true;
			}
			// model update from Storybook 3.x to 4.0
			final ModelMigration oldPersMngr = ModelMigration.getInstance();
			oldPersMngr.open(dbFile);
			try {
				if (!oldPersMngr.checkAndAlterModel()) {
					oldPersMngr.closeConnection();
					return false;
				}
			} catch (Exception e) {
				oldPersMngr.closeConnection();
				SbApp.error("SbApp.openFile(" + dbFile.getDbName() + ")", e);
				ExceptionDialog dlg = new ExceptionDialog(e);
				SwingUtil.showModalDialog(dlg, null);
				return false;
			}
			oldPersMngr.closeConnection();
			setWaitCursor();
			I18N.getMsg("msg.common.loading", dbFile.getName());

			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					try {
						if (mainFrame.getViewFactory() != null) {
							removeMainFrame(mainFrame);
							mainFrame = new MainFrame();
						}
						mainFrame.init(dbFile);
						mainFrame.initUi();
						updateFilePref(dbFile);
						reloadMenuBars();
						setDefaultCursor();
						if (oldPersMngr.hasAlteredDbModel()) {
							PostModelUpdateDialog dlg2 = new PostModelUpdateDialog(mainFrame);
							SwingUtil.showModalDialog(dlg2, mainFrame);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			});
		} catch (HeadlessException e) {
		}
		return true;
	}

	/**
	 * Refresh.
	 */
	public void refresh() {
		trace("SbApp.refresh()");
		int width = getMainFrame().getWidth();
		int height = getMainFrame().getHeight();
		boolean maximized = getMainFrame().isMaximized();
		getMainFrame().getSbActionManager().reloadMenuToolbar();
		getMainFrame().setSize(width, height);
		if (maximized) {
			getMainFrame().setMaximized();
		}
		getMainFrame().refresh();
	}

	/**
	 * Reload menu bars.
	 */
	public void reloadMenuBars() {
		getMainFrame().getSbActionManager().reloadMenuToolbar();
	}

	/**
	 * Reload status bars.
	 */
	public void reloadStatusBars() {
		getMainFrame().refreshStatusBar();
	}

	/**
	 * Removes the main frame.
	 *
	 * @param mainFrame
	 *            the main frame
	 */
	public void removeMainFrame(MainFrame mainFrame) {
		trace("SbApp.removeMainFrame(" + mainFrame.getName() + ")");
		mainFrame.saveAllTableDesign();
		mainFrame.remove(mainFrame);
	}

	/**
	 * Rename file.
	 *
	 * @param mainFrame
	 *            the main frame
	 * @param outFile
	 *            the out file
	 */
	public void renameFile(final MainFrame mainFrame, File outFile) {
		trace("SbApp.renameFile(" + mainFrame.getName() + "," + outFile.getAbsolutePath() + ")");
		try {
			File inFile = mainFrame.getDbFile().getFile();
			mainFrame.close(false);
			FileUtils.copyFile(inFile, outFile);
			inFile.delete();
			DbFile dbFile = new DbFile(outFile);
			openFile(dbFile);
		} catch (IOException e) {
			error("SbApp.renameFile(" + mainFrame.getName() + "," + outFile.getName() + ")", e);
		}
	}

	/**
	 * Reset ui font.
	 */
	public void resetUiFont() {
		if (defaultFont == null) {
			return;
		}
		SwingUtil.setUIFont(new FontUIResource(defaultFont.getName(), defaultFont.getStyle(), defaultFont.getSize()));
	}

	/**
	 * Restore default font.
	 */
	public void restoreDefaultFont() {
		Preference pref = PrefUtil.get(PreferenceKey.DEFAULT_FONT_NAME, SbConstants.DEFAULT_FONT_NAME);
		String name = SbConstants.DEFAULT_FONT_NAME;
		if (pref != null && !pref.getStringValue().isEmpty()) {
			name = pref.getStringValue();
		}
		pref = PrefUtil.get(PreferenceKey.DEFAULT_FONT_STYLE, SbConstants.DEFAULT_FONT_STYLE);
		int style = 0;
		if (pref != null) {
			style = pref.getIntegerValue();
		}
		pref = PrefUtil.get(PreferenceKey.DEFAULT_FONT_SIZE, SbConstants.DEFAULT_FONT_SIZE);
		int size = 0;
		if (pref != null) {
			size = pref.getIntegerValue();
		}
		// set default font
		setDefaultFont(new Font(name, style, size));
	}

	/**
	 * Save all.
	 */
	public void saveAll() {
		trace("SbApp.saveAll()");
		getMainFrame().getSbActionManager().getActionHandler();// .handleFileSave();
	}

	/**
	 * Sets the default cursor.
	 */
	public void setDefaultCursor() {
		SwingUtil.setDefaultCursor(getMainFrame());
	}

	/**
	 * Sets the default font.
	 *
	 * @param font
	 *            the new default font
	 */
	public void setDefaultFont(Font font) {
		if (font == null) {
			return;
		}
		defaultFont = font;
		resetUiFont();
		PrefUtil.set(PreferenceKey.DEFAULT_FONT_NAME, font.getName());
		PrefUtil.set(PreferenceKey.DEFAULT_FONT_SIZE, font.getSize());
		PrefUtil.set(PreferenceKey.DEFAULT_FONT_STYLE, font.getStyle());
	}

	/**
	 * Sets the wait cursor.
	 */
	public void setWaitCursor() {
		SwingUtil.setWaitingCursor(getMainFrame());
	}

	/**
	 * Update file pref.
	 *
	 * @param dbFile
	 *            the db file
	 */
	private void updateFilePref(DbFile dbFile) {
		trace("SbApp.updateFilePref(" + dbFile.getDbName() + ")");
		// save last open directory and file
		File file = dbFile.getFile();
		PrefUtil.set(PreferenceKey.LAST_OPEN_DIR, file.getParent());
		PrefUtil.set(PreferenceKey.LAST_OPEN_FILE, file.getPath());
		PrefUtil.set(PreferenceKey.DEFAULT_FILE, file.getPath());

		// save recent files
		List<DbFile> list = PrefUtil.getDbFileList();
		if (!list.contains(dbFile)) {
			list.add(dbFile);
		}
		// check recent files and remove non-existing entries
		Iterator<DbFile> it = list.iterator();
		while (it.hasNext()) {
			DbFile dbFile2 = it.next();
			if (!dbFile2.getFile().exists()) {
				it.remove();
			}
		}
		PrefUtil.setDbFileList(list);
		reloadMenuBars();
	}

	/**
	 * Do mac open file.
	 *
	 * @param aFile
	 *            the a file
	 */
	public static void doMacOpenFile(File aFile) {

	}

	/**
	 * Gets the main frame.
	 *
	 * @return the main frame
	 */
	public static MainFrame getMainFrame() {
		return mainFrame;
	}

	/**
	 * Open alignment file.
	 *
	 * @param firstFile
	 *            the first file
	 */
	public static void openAlignmentFile(File firstFile) {

	}

}
