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
import java.awt.Image;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.channels.FileLock;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import org.apache.commons.io.FileUtils;

import com.apple.eawt.AboutHandler;
import com.apple.eawt.AppEvent.AboutEvent;
import com.apple.eawt.AppEvent.PreferencesEvent;
import com.apple.eawt.AppEvent.QuitEvent;

import com.apple.eawt.Application;
import com.apple.eawt.PreferencesHandler;
import com.apple.eawt.QuitHandler;
import com.apple.eawt.QuitResponse;


import storybook.SbConstants.BookKey;
import storybook.SbConstants.PreferenceKey;
import storybook.controller.PreferenceController;
import storybook.model.DbFile;
import storybook.model.PreferenceModel;
import storybook.model.hbn.entity.Preference;
import storybook.model.oldModel.ModelMigration;
import storybook.toolkit.BookUtil;
import storybook.toolkit.I18N;
import storybook.toolkit.PrefUtil;
import storybook.toolkit.net.Updater;
import storybook.toolkit.swing.SwingUtil;
import storybook.ui.MainFrame;
import storybook.ui.dialog.ExceptionDialog;
import storybook.ui.dialog.FirstStartDialog;
import storybook.ui.dialog.PostModelUpdateDialog;
import storybook.ui.dialog.SplashDialog;
import storybook.ui.dialog.file.NewFileDialog;

public class SbApp extends Component {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3654051279782138653L;
	private static boolean bTrace = false;
	private static boolean bTraceHibernate = false;

	private static SbApp instance;
	private static String i18nFile;
	private static boolean bDevTest = false;

	public static void error(String txt, Exception e) {
		System.err.println(txt + " Exception:" + e.getMessage());
	}

	public static String getI18nFile() {
		return (i18nFile);
	}

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

	public static boolean getTrace() {
		return (bTrace);
	}

	public static boolean getTraceHibernate() {
		return (bTraceHibernate);
	}

	public static boolean isDevTest() {
		return (bDevTest);
	}

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
	 * Sets various <code>System</code> properties.
	 */
	private static void initSystemProperties() {
		// if (isMacOSX()) {
		// Change Mac OS X application menu name
		String classPackage = SbApp.class.getName();
		classPackage = classPackage.substring(0, classPackage.lastIndexOf("."));
		// ResourceBundle resource = ResourceBundle.getBundle(classPackage + "."
		// + "package");
		// String applicationName =
		// resource.getString("SweetHome3D.applicationName");
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "iStorybook");
		System.setProperty("apple.awt.application.name", "iStorybook");
		// Use Mac OS X screen menu bar for frames menu bar
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		// Force the use of Quartz under Mac OS X for better Java 2D rendering
		// performance
		System.setProperty("apple.awt.graphics.UseQuartz", "true");
		

		System.setProperty("apple.awt.fileDialogForDirectories", "true");

		System.setProperty("apple.awt.brushMetalLook", "true");

		System.setProperty("apple.awt.showGrowBox", "true");
		System.setProperty("apple.awt.window.position.forceSafeCreation", "true");
		System.setProperty("apple.awt.window.position.forceSafeProgrammaticPositioning", "true");
		System.setProperty("apple.awt.window.position.forceSafeUserPositioning", "true");
		
		if (System.getProperty("dragAndDropWithoutTransferHandler") == null
		/* && isJavaVersionBetween("1.7", "1.8.0_40") */) {
			System.setProperty("dragAndDropWithoutTransferHandler", "true");

		}
		
//		Set dock icon if launched programmatically otherwise rely on 
//		-Xdock no way of setting docking name rely on -X vm commands
		try {
		    Class util = Class.forName("com.apple.eawt.Application");
		    Method getApplication = util.getMethod("getApplication", new Class[0]);
		    Object application = getApplication.invoke(util);
		    Class params[] = new Class[1];
		    params[0] = Image.class;
		    Method setDockIconImage = util.getMethod("setDockIconImage", params);
		    URL url = SbApp.class.getClassLoader().getResource("oStorybook-icon.png");
		    Image image = Toolkit.getDefaultToolkit().getImage(url);
		    setDockIconImage.invoke(application, image);
		} catch (ClassNotFoundException e) {
		    // log exception
		} catch (NoSuchMethodException e) {
		    // log exception
		} catch (InvocationTargetException e) {
		    // log exception
		} catch (IllegalAccessException e) {
		    // log exception
		}

		// }
		// Request to use system proxies to access to the Internet
		if (System.getProperty("java.net.useSystemProxies") == null) {
			System.setProperty("java.net.useSystemProxies", "true");
		}
	}

	private static void customizeForMac() {

		try {

			System.setProperty("com.apple.mrj.application.apple.menu.about.name", "iStorybook");

			System.setProperty("apple.awt.application.name", "iStorybook");

			System.setProperty("apple.laf.useScreenMenuBar", "true");

			System.setProperty("apple.awt.graphics.UseQuartz", "true");

			System.setProperty("apple.awt.fileDialogForDirectories", "true");

			System.setProperty("apple.awt.brushMetalLook", "true");

			System.setProperty("apple.awt.showGrowBox", "true");
			System.setProperty("apple.awt.window.position.forceSafeCreation", "true");
			System.setProperty("apple.awt.window.position.forceSafeProgrammaticPositioning", "true");
			System.setProperty("apple.awt.window.position.forceSafeUserPositioning", "true");

			UIManager.put("TitledBorder.border", UIManager.getBorder("TitledBorder.aquaVariant"));

			Application macOSXApplication = Application.getApplication();

			macOSXApplication.setAboutHandler(new AboutHandler() {
				public void handleAbout(AboutEvent evt) {
					// JOptionPane.showMessageDialog(MainFrame,"iStorybook "
					// /*+ MedSavantProgramInformation.getVersion() + " "+
					// MedSavantProgramInformation.getReleaseType()+
					// * "\nCreated by ");
					// */);
				}
			});
			macOSXApplication.setPreferencesHandler(new PreferencesHandler() {
				@Override
				public void handlePreferences(PreferencesEvent pe) {
					// handlePrefs();
				}
			});

			macOSXApplication.setQuitHandler(new QuitHandler() {
				@Override
				public void handleQuitRequestWith(QuitEvent evt, QuitResponse resp) {
					// exit();
					resp.cancelQuit();
				}
			});
		} catch (Throwable x) {
			System.err.println(
					"Warning: requires Java for Mac OS X 10.6 Update 3 (or later).\nPlease check Software Update for the latest version.");
		}
	}

	public static void main(String[] args) {

		initSystemProperties();

		String tempDir = System.getProperty("java.io.tmpdir");
		String fn = tempDir + File.separator + "storybook.lck";
		if (args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				if (args[i].equalsIgnoreCase("--trace")) {
					SbApp.bTrace = true;
					System.out.println("Storybook execution in trace mode");
				}
				if (args[i].equalsIgnoreCase("--hibernate")) {
					SbApp.bTraceHibernate = true;
					System.out.println("Hibernate in trace mode");
				}
				if (args[i].equalsIgnoreCase("--dev")) {
					SbApp.bDevTest = true;
					System.out.println("Development test");
				}
				if (args[i].equalsIgnoreCase("--msg")) {
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
				if (file.exists() && file.canWrite()) {
					if (!file.delete()) {
						JOptionPane.showMessageDialog(null, "Delete failed",
								"File\n" + file.getAbsolutePath() + "\ncould not be deleted.",
								JOptionPane.ERROR_MESSAGE);
					}
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

	public static void setI18nFile(String file) {
		i18nFile = file;
	}

	public static void setTrace(boolean b) {
		bTrace = b;
		System.out.println((b ? "Enter" : "Exit") + " trace mode");
	}

	public static void trace(String msg) {
		if (bTrace) {
			System.out.println(msg);
		}
	}

	private PreferenceModel preferenceModel;

	private PreferenceController preferenceController;

	private final List<MainFrame> mainFrames;

	private Font defaultFont;

	private SbApp() {
		mainFrames = new ArrayList<>();
	}

	public void addMainFrame(MainFrame mainFrame) {
		trace("SbApp.addMainFrame(" + mainFrame.getName() + ")");
		mainFrames.add(mainFrame);
	}

	private boolean checkIfAlreadyOpened(String dbName) {
		trace("SbApp.checkIfAlreadyOpened(" + dbName + ")");
		for (MainFrame mainFrame : mainFrames) {
			if (mainFrame.isBlank()) {
				continue;
			}
			if (mainFrame.getDbFile().getDbName().equals(dbName)) {
				mainFrame.setVisible(true);
				return true;
			}
		}
		return false;
	}

	public void clearRecentFiles() {
		trace("SbApp.clearRecentFiles()");
		PrefUtil.setDbFileList(new ArrayList<DbFile>());
		reloadMenuBars();
	}

	public void closeBlank() {
		trace("SbApp.closeBlank()");
		for (MainFrame mainFrame : mainFrames) {
			if (mainFrame.isBlank()) {
				mainFrames.remove(mainFrame);
				mainFrame.dispose();
			}
		}
	}

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
			final MainFrame newMainFrame = new MainFrame();
			newMainFrame.init(dbFile);
			newMainFrame.getBookModel().initEntites();
			BookUtil.store(newMainFrame, BookKey.USE_HTML_SCENES, dlg.getUseHtmlScenes());
			BookUtil.store(newMainFrame, BookKey.USE_HTML_DESCR, dlg.getUseHtmlDescr());
			BookUtil.store(newMainFrame, BookKey.BOOK_CREATION_DATE,
					new SimpleDateFormat("dd/MM/yy").format(new Date()));
			newMainFrame.initUi();
			newMainFrame.getBookController().fireAgain();
			addMainFrame(newMainFrame);
			closeBlank();
			updateFilePref(dbFile);
			setDefaultCursor();
		} catch (Exception e) {
			error("SbApp.createNewFile()", e);
		}
	}

	public void exit() {
		trace("SbApp.exit()");
		if (mainFrames.size() > 0) {
			Preference pref = PrefUtil.get(PreferenceKey.CONFIRM_EXIT, true);
			if (pref.getBooleanValue()) {
				int n = JOptionPane.showConfirmDialog(null, I18N.getMsg("msg.mainframe.want.exit"),
						I18N.getMsg("msg.common.exit"), JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.NO_OPTION || n == JOptionPane.CLOSED_OPTION) {
					return;
				}
			}
			saveAll();
		}
		System.exit(0);
	}

	public Font getDefaultFont() {
		return this.defaultFont;
	}

	public List<MainFrame> getMainFrames() {
		return mainFrames;
	}

	public PreferenceController getPreferenceController() {
		return preferenceController;
	}

	public PreferenceModel getPreferenceModel() {
		return preferenceModel;
	}

	private void init() {
		// trace("SbApp.init()");

		// ADK SplashDialog dlgStart = new SplashDialog("oStorybook init");
		try {
			MainFrame mainFrame = new MainFrame();
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
			// trace("-->SwingUtil.setLookAndFeel()");
			// ADK Destroys loading menu bar into app bar on mac
			// SwingUtil.setLookAndFeel();
			restoreDefaultFont();
			// first start dialog
			Preference prefFirstStart = PrefUtil.get(PreferenceKey.FIRST_START_4, true);
			if (prefFirstStart.getBooleanValue()) {
				FirstStartDialog dlg = new FirstStartDialog();
				SwingUtil.showModalDialog(dlg, null);
				PrefUtil.set(PreferenceKey.FIRST_START_4, false);
			}

			Preference pref = PrefUtil.get(PreferenceKey.OPEN_LAST_FILE, false);
			boolean fileHasBeenOpened = false;
			if (pref.getBooleanValue()) {
				Preference pref2 = PrefUtil.get(PreferenceKey.LAST_OPEN_FILE, "");
				DbFile dbFile = new DbFile(pref2.getStringValue());
				trace("SbApp.init(): loading... " + dbFile);
				fileHasBeenOpened = openFile(dbFile);
			}
			if (fileHasBeenOpened) {
				// check for updates
				Updater.checkForUpdate();
				// dlgStart.dispose();
				return;
			}
			
			createNewFile() ;

			// check for updates
			Updater.checkForUpdate();
			/*
			 * abandon de l'appel au garbarge collector, utilisation non
			 * recommandée Timer t1 = new Timer(10000, new ActionListener() {
			 * 
			 * @Override public void actionPerformed(ActionEvent e) { runGC(); }
			 * }); t1.start();
			 */

		} catch (Exception e) {
			error("SbApp.init()", e);
			// dlgStart.dispose();
			ExceptionDialog dlg = new ExceptionDialog(e);
			SwingUtil.showModalDialog(dlg, null);
		}
		// dlgStart.dispose();
	}

	public void initI18N() {
		trace("SbApp.initI18N()");
		String localeStr = PrefUtil.get(PreferenceKey.LANG, SbConstants.DEFAULT_LANG).getStringValue();
		SbConstants.Language lang = SbConstants.Language.valueOf(localeStr);
		Locale locale = lang.getLocale();
		setLocale(locale);
		I18N.initResourceBundles(getLocale());
	}

	public void modelPropertyChange(PropertyChangeEvent evt) {
		// works, but currently not used
		// may be used for entity copying between files
		// String propName = evt.getPropertyName();
		// Object newValue = evt.getNewValue();
		// Object oldValue = evt.getOldValue();
	}

	public boolean openFile() {
		trace("SbApp.openFile()");
		final DbFile dbFile = BookUtil.openDocumentDialog();
		if (dbFile == null) {
			return false;
		}
		return openFile(dbFile);
	}

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
			// final HourglassSplash dlg = new HourglassSplash(text);
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					try {
						MainFrame newMainFrame = new MainFrame();
						newMainFrame.init(dbFile);
						newMainFrame.initUi();
						addMainFrame(newMainFrame);
						closeBlank();
						updateFilePref(dbFile);
						reloadMenuBars();
						setDefaultCursor();
						// dlg.dispose();

						if (oldPersMngr.hasAlteredDbModel()) {
							PostModelUpdateDialog dlg2 = new PostModelUpdateDialog(newMainFrame);
							SwingUtil.showModalDialog(dlg2, newMainFrame);
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

	public void refresh() {
		trace("SbApp.refresh()");
		for (MainFrame mainFrame : mainFrames) {
			int width = mainFrame.getWidth();
			int height = mainFrame.getHeight();
			boolean maximized = mainFrame.isMaximized();
			mainFrame.getSbActionManager().reloadMenuToolbar();
			mainFrame.setSize(width, height);
			if (maximized) {
				mainFrame.setMaximized();
			}
			mainFrame.refresh();
		}
	}

	public void reloadMenuBars() {
		for (MainFrame mainFrame : mainFrames) {
			mainFrame.getSbActionManager().reloadMenuToolbar();
		}
	}

	public void reloadStatusBars() {
		for (MainFrame mainFrame : mainFrames) {
			mainFrame.refreshStatusBar();
		}
	}

	public void removeMainFrame(MainFrame mainFrame) {
		trace("SbApp.removeMainFrame(" + mainFrame.getName() + ")");
		for (MainFrame m : mainFrames)
			m.saveAllTableDesign();
		mainFrames.remove(mainFrame);
	}

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

	public void resetUiFont() {
		if (defaultFont == null) {
			return;
		}
		SwingUtil.setUIFont(new FontUIResource(defaultFont.getName(), defaultFont.getStyle(), defaultFont.getSize()));
	}

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

	public void saveAll() {
		trace("SbApp.saveAll()");
		for (MainFrame mainFrame : mainFrames) {
			mainFrame.getSbActionManager().getActionHandler().handleFileSave();
		}
	}

	public void setDefaultCursor() {
		for (MainFrame mainFrame : mainFrames) {
			SwingUtil.setDefaultCursor(mainFrame);
		}
	}

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

	public void setWaitCursor() {
		for (MainFrame mainFrame : mainFrames) {
			SwingUtil.setWaitingCursor(mainFrame);
		}
	}

	private void updateFilePref(DbFile dbFile) {
		trace("SbApp.updateFilePref(" + dbFile.getDbName() + ")");
		// save last open directory and file
		File file = dbFile.getFile();
		PrefUtil.set(PreferenceKey.LAST_OPEN_DIR, file.getParent());
		PrefUtil.set(PreferenceKey.LAST_OPEN_FILE, file.getPath());
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

}
