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

package storybook.toolkit;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.net.URL;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.apple.eawt.AboutHandler;
import com.apple.eawt.AppEvent.AboutEvent;
import com.apple.eawt.AppEvent.OpenFilesEvent;
import com.apple.eawt.AppEvent.PreferencesEvent;
import com.apple.eawt.AppEvent.PrintFilesEvent;
import com.apple.eawt.AppEvent.QuitEvent;
import com.apple.eawt.Application;
import com.apple.eawt.OpenFilesHandler;
import com.apple.eawt.PreferencesHandler;
import com.apple.eawt.PrintFilesHandler;
import com.apple.eawt.QuitHandler;
import com.apple.eawt.QuitResponse;

import storybook.SbApp;
import storybook.SbConstants.PreferenceKey;
import storybook.model.hbn.entity.Preference;
import storybook.toolkit.swing.SwingUtil;
import storybook.ui.MainFrame;
import storybook.ui.dialog.AboutDialog;
import storybook.ui.dialog.PreferencesDialog;

// TODO: Auto-generated Javadoc
/**
 * This class is keeping the com.apple.eawt isolated from rest of program and is
 * instantiated and called through reflection so other platforms do not get
 * runtime error caused by classes only residing in Mac JRE the com.apple.eawt
 * are only stubs in this project (in orange-extensions.jar) and scope is
 * "provided" in maven This way it compiles on all platforms but only used on
 * Mac where the jar is provided by JRE
 */

public class MacAdapter implements OpenFilesHandler, PreferencesHandler, QuitHandler, PrintFilesHandler, AboutHandler {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(MacAdapter.class);

	/**
	 * Register application.
	 *
	 * @param sbView
	 *            the sb view
	 */
	public static void registerApplication(SbApp sbView) {
		new MacAdapter(sbView);
	}

	/**
	 * Instantiates a new adapter.
	 *
	 * @param sbView
	 *            the sb view
	 */
	private MacAdapter(SbApp sbView) {

		System.setProperty("apple.laf.useScreenMenuBar", "true");
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "iStorybook");
		System.setProperty("apple.awt.application.name", "iStorybook");

		System.setProperty("apple.awt.graphics.UseQuartz", "true");
		System.setProperty("apple.awt.fileDialogForDirectories", "true");
		System.setProperty("apple.awt.brushMetalLook", "true");

		System.setProperty("apple.awt.showGrowBox", "true");
		System.setProperty("apple.awt.window.position.forceSafeCreation", "true");
		System.setProperty("apple.awt.window.position.forceSafeProgrammaticPositioning", "true");
		System.setProperty("apple.awt.window.position.forceSafeUserPositioning", "true");

		Application.getApplication().setOpenFileHandler(this);
		Application.getApplication().setPrintFileHandler(this);
		Application.getApplication().setQuitHandler(this);
		Application.getApplication().setPreferencesHandler(this);
		Application.getApplication().setAboutHandler(this);
		;

		// Set dock icon if not launched from a bundle otherwise rely on
		// -Xdock
		// No way I can find of setting docking name rely on -Xdock vm commands when in bundle

		URL url = SbApp.class.getClassLoader().getResource("oStorybook-icon.png");
		Image image = Toolkit.getDefaultToolkit().getImage(url);
		Application.getApplication().setDockIconImage(image);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.apple.eawt.AboutHandler#handleAbout(com.apple.eawt.AppEvent.
	 * AboutEvent)
	 */
	@Override
	public void handleAbout(AboutEvent arg0) {
		AboutDialog dlg = new AboutDialog(null);
		SwingUtil.showModalDialog(dlg, null);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.apple.eawt.PreferencesHandler#handlePreferences(com.apple.eawt.
	 * AppEvent.PreferencesEvent)
	 */
	public void handlePreferences(PreferencesEvent e) {
		PreferencesDialog dlg = new PreferencesDialog();
		SwingUtil.showModalDialog(dlg, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.apple.eawt.QuitHandler#handleQuitRequestWith(com.apple.eawt.AppEvent.
	 * QuitEvent, com.apple.eawt.QuitResponse)
	 */
	public void handleQuitRequestWith(QuitEvent e, QuitResponse response) {
		SbApp.trace("SbApp.exit()");
		Preference pref = PrefUtil.get(PreferenceKey.CONFIRM_EXIT, true);
		if (pref.getBooleanValue()) {
			int n = JOptionPane.showConfirmDialog(null, I18N.getMsg("msg.mainframe.want.exit"),
					I18N.getMsg("msg.common.exit"), JOptionPane.YES_NO_OPTION);
			if (n == JOptionPane.NO_OPTION || n == JOptionPane.CLOSED_OPTION) {
				return;
			}
			SbApp.getMainFrame().getSbActionManager().getActionHandler();
		}
		System.exit(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.apple.eawt.OpenFilesHandler#openFiles(com.apple.eawt.AppEvent.
	 * OpenFilesEvent)
	 */
	public void openFiles(OpenFilesEvent event) {
		SbApp.trace("openFiles");
		List<File> files = event.getFiles();
		SbApp.trace("files" + files);
		if (files != null && files.size() > 0) {
			SbApp.trace("files.size()" + files.size());
			SbApp.trace("files.get(0).getAbsolutePath()" + files.get(0).getAbsolutePath());
			File firstFile = new File(files.get(0).getAbsolutePath());
			SbApp.openAlignmentFile(firstFile);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.apple.eawt.PrintFilesHandler#printFiles(com.apple.eawt.AppEvent.
	 * PrintFilesEvent)
	 */
	public void printFiles(PrintFilesEvent e) {

	}

}