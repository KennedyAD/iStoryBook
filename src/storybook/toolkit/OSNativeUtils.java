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

import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import storybook.SbApp;

// TODO: Auto-generated Javadoc
/**
 * The Class OSNativeUtils.
 */
public class OSNativeUtils {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(OSNativeUtils.class);

	/** The cached content scale factor. */
	private static float cachedContentScaleFactor = -1; // 1 indicates a regular
														// mac display. 2=
														// retina

	/**
	 * Count stop codons key accelerator.
	 *
	 * @return the key stroke
	 */
	public static KeyStroke countStopCodonsKeyAccelerator() {
		if (isMac()) {
			return KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.META_DOWN_MASK | InputEvent.ALT_DOWN_MASK);
		} else {
			return KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK);
		}
	}

	/**
	 * Dec reading frame key accelerator.
	 *
	 * @return the key stroke
	 */
	public static KeyStroke decReadingFrameKeyAccelerator() {
		if (isMac()) {
			return KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, InputEvent.SHIFT_DOWN_MASK);
		} else {
			return KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, InputEvent.SHIFT_DOWN_MASK);
		}
	}

	/**
	 * Called internally by installGtkPopupBugWorkaround to fix the thickness of
	 * a GTK style field by setting it to a minimum value of 1.
	 * 
	 * @param style
	 *            The GTK style object.
	 * @param fieldName
	 *            The field name.
	 * @throws Exception
	 *             When reflection fails.
	 */
	private static void fixGtkThickness(Object style, String fieldName) throws Exception {
		Field field = style.getClass().getDeclaredField(fieldName);
		boolean accessible = field.isAccessible();
		field.setAccessible(true);
		field.setInt(style, Math.max(1, field.getInt(style)));
		field.setAccessible(accessible);
	}

	/**
	 * Gets the adds the or remove excludes key accelerator.
	 *
	 * @return the adds the or remove excludes key accelerator
	 */
	public static KeyStroke getAddOrRemoveExcludesKeyAccelerator() {
		if (isMac()) {
			return KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.META_DOWN_MASK);
		} else {
			return KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK);
		}
	}

	/**
	 * Gets the clear key accelerator.
	 *
	 * @return the clear key accelerator
	 */
	public static KeyStroke getClearKeyAccelerator() {
		if (isMac()) {
			return KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0);
		} else {
			return KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0);
		}
	}

	/**
	 * Gets the close win key accelerator.
	 *
	 * @return the close win key accelerator
	 */
	public static KeyStroke getCloseWinKeyAccelerator() {
		if (isMac()) {
			return KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.META_DOWN_MASK);
		} else {
			return KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK);
		}
	}

	/**
	 * Gets the copy key accelerator.
	 *
	 * @return the copy key accelerator
	 */
	public static KeyStroke getCopyKeyAccelerator() {
		if (isMac()) {
			return KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.META_DOWN_MASK | InputEvent.ALT_DOWN_MASK);
		} else {
			return KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK);
		}
	}

	/**
	 * Gets the copy selection as fasta key accelerator.
	 *
	 * @return the copy selection as fasta key accelerator
	 */
	public static KeyStroke getCopySelectionAsFastaKeyAccelerator() {
		if (isMac()) {
			return KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.META_DOWN_MASK);
		} else {
			return KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK);
		}
	}

	/**
	 * Gets the decrease font size key accelerator.
	 *
	 * @return the decrease font size key accelerator
	 */
	public static KeyStroke getDecreaseFontSizeKeyAccelerator() {
		if (isMac()) {
			return KeyStroke.getKeyStroke('-');// , InputEvent.META_DOWN_MASK);
												// // Command and - is not
												// working on mac
		} else {
			return KeyStroke.getKeyStroke('-');// , InputEvent.CTRL_DOWN_MASK);
		}
	}

	/**
	 * Gets the delete gap move left key accelerator.
	 *
	 * @return the delete gap move left key accelerator
	 */
	public static KeyStroke getDeleteGapMoveLeftKeyAccelerator() {
		if (isMac()) {
			return KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0);
		} else {
			return KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0);
		}
	}

	/**
	 * Gets the delete gap move right key accelerator.
	 *
	 * @return the delete gap move right key accelerator
	 */
	public static KeyStroke getDeleteGapMoveRightKeyAccelerator() {
		if (isMac()) {
			return KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, InputEvent.SHIFT_DOWN_MASK);
		} else {
			return KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, InputEvent.SHIFT_DOWN_MASK);
		}
	}

	/**
	 * Gets the delete key accelerator.
	 *
	 * @return the delete key accelerator
	 */
	public static KeyStroke getDeleteKeyAccelerator() {
		if (isMac()) {
			return KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, InputEvent.META_DOWN_MASK);
		} else {
			return KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, InputEvent.CTRL_DOWN_MASK);
		}
	}

	/**
	 * Gets the expand selection down key accelerator.
	 *
	 * @return the expand selection down key accelerator
	 */
	public static KeyStroke getExpandSelectionDownKeyAccelerator() {
		if (isMac()) {
			return KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, InputEvent.SHIFT_DOWN_MASK);
		} else {
			return KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, InputEvent.SHIFT_DOWN_MASK);
		}
	}

	/**
	 * Gets the focus next win key accelerator.
	 *
	 * @return the focus next win key accelerator
	 */
	public static KeyStroke getFocusNextWinKeyAccelerator() {
		if (isMac()) {
			return KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.META_DOWN_MASK);
		} else {
			return KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK);
		}
	}

	/**
	 * Called internally by installGtkPopupBugWorkaround. Returns a specific GTK
	 * style object.
	 * 
	 * @param styleFactory
	 *            The GTK style factory.
	 * @param component
	 *            The target component of the style.
	 * @param regionName
	 *            The name of the target region of the style.
	 * @return The GTK style.
	 * @throws Exception
	 *             When reflection fails.
	 */
	private static Object getGtkStyle(Object styleFactory, JComponent component, String regionName) throws Exception {
		// Create the region object
		Class<?> regionClass = Class.forName("javax.swing.plaf.synth.Region");
		Field field = regionClass.getField(regionName);
		Object region = field.get(regionClass);

		// Get and return the style
		Class<?> styleFactoryClass = styleFactory.getClass();
		Method method = styleFactoryClass.getMethod("getStyle", new Class<?>[] { JComponent.class, regionClass });
		boolean accessible = method.isAccessible();
		method.setAccessible(true);
		Object style = method.invoke(styleFactory, component, region);
		method.setAccessible(accessible);
		return style;
	}

	/**
	 * Gets the high dpi scale factor.
	 *
	 * @return the high dpi scale factor
	 */
	public static float getHighDPIScaleFactor() {

		if (cachedContentScaleFactor == -1) {
			Object obj = Toolkit.getDefaultToolkit().getDesktopProperty("apple.awt.contentScaleFactor");
			if (obj instanceof Float) {
				Float f = (Float) obj;
				cachedContentScaleFactor = f;
			} else {
				cachedContentScaleFactor = 1;
			}
		}
		return cachedContentScaleFactor;

	}

	/**
	 * Gets the increase font size key accelerator.
	 *
	 * @return the increase font size key accelerator
	 */
	public static KeyStroke getIncreaseFontSizeKeyAccelerator() {
		if (isMac()) {
			return KeyStroke.getKeyStroke('+');// , InputEvent.META_DOWN_MASK);
												// // Command and + is not
												// working on mac
		} else {
			return KeyStroke.getKeyStroke('+');// , InputEvent.CTRL_DOWN_MASK);
		}
	}

	/**
	 * Gets the insert gap move left key accelerator.
	 *
	 * @return the insert gap move left key accelerator
	 */
	public static KeyStroke getInsertGapMoveLeftKeyAccelerator() {
		if (isMac()) {
			return KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, InputEvent.SHIFT_DOWN_MASK);
		} else {
			return KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, InputEvent.SHIFT_DOWN_MASK);
		}
	}

	/**
	 * Gets the insert gap move right key accelerator.
	 *
	 * @return the insert gap move right key accelerator
	 */
	public static KeyStroke getInsertGapMoveRightKeyAccelerator() {
		if (isMac()) {
			return KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0);
		} else {
			return KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0);
		}
	}

	/**
	 * Gets the java version as double.
	 *
	 * @return the java version as double
	 */
	/*
	 * 
	 * 1.6.0_25 is returned as 6.25
	 * 
	 */
	private static double getJavaVersionAsDouble() {
		double version = 0;
		try {
			// java version "";
			String javaVersion = System.getProperty("java.version").toLowerCase();
			String[] splitted = StringUtils.split(javaVersion, "._-");
			String major = splitted[1];
			String minor = splitted[3];
			String versionString = major + "." + minor;
			version = Double.parseDouble(versionString);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return version;

	}

	/**
	 * Gets the mouse wheel zoom modifier mask.
	 *
	 * @return the mouse wheel zoom modifier mask
	 */
	public static int getMouseWheelZoomModifierMask() {
		if (isMac()) {
			return InputEvent.META_DOWN_MASK;
		} else {
			return InputEvent.CTRL_DOWN_MASK;
		}
	}
	/*
	 * public static KeyStroke getDecreaseFontSizeKeyAccelerator() { return
	 * KeyStroke.getKeyStroke('-'); }
	 * 
	 * public static KeyStroke getIncreaseFontSizeKeyAccelerator() { return
	 * KeyStroke.getKeyStroke('+'); }
	 */

	/**
	 * Gets the move selected left key accelerator.
	 *
	 * @return the move selected left key accelerator
	 */
	public static KeyStroke getMoveSelectedLeftKeyAccelerator() {
		if (isMac()) {
			return KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, InputEvent.META_DOWN_MASK);
		} else {
			return KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, InputEvent.CTRL_DOWN_MASK);
		}
	}

	/**
	 * Gets the move selected right key accelerator.
	 *
	 * @return the move selected right key accelerator
	 */
	public static KeyStroke getMoveSelectedRightKeyAccelerator() {
		if (isMac()) {
			return KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, InputEvent.META_DOWN_MASK);
		} else {
			return KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, InputEvent.CTRL_DOWN_MASK);
		}
	}

	/**
	 * Gets the move selection down key accelerator.
	 *
	 * @return the move selection down key accelerator
	 */
	public static KeyStroke getMoveSelectionDownKeyAccelerator() {
		if (isMac()) {
			return KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, InputEvent.META_DOWN_MASK);
		} else {
			return KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, InputEvent.CTRL_DOWN_MASK);
		}
	}

	/**
	 * Gets the move selection up key accelerator.
	 *
	 * @return the move selection up key accelerator
	 */
	public static KeyStroke getMoveSelectionUpKeyAccelerator() {
		if (isMac()) {
			return KeyStroke.getKeyStroke(KeyEvent.VK_UP, InputEvent.META_DOWN_MASK);
		} else {
			return KeyStroke.getKeyStroke(KeyEvent.VK_UP, InputEvent.CTRL_DOWN_MASK);
		}
	}

	/**
	 * Gets the new file accelerator.
	 *
	 * @return the new file accelerator
	 */
	public static KeyStroke getNewFileAccelerator() {
		if (isMac()) {
			return KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.META_DOWN_MASK);
		} else {
			return KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK);
		}
	}

	/**
	 * Gets the open file accelerator.
	 *
	 * @return the open file accelerator
	 */
	public static KeyStroke getOpenFileAccelerator() {
		if (isMac()) {
			return KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.META_DOWN_MASK);
		} else {
			return KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK);
		}
	}

	/**
	 * Gets the paste key accelerator.
	 *
	 * @return the paste key accelerator
	 */
	public static KeyStroke getPasteKeyAccelerator() {
		if (isMac()) {
			return KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.META_DOWN_MASK);
		} else {
			return KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK);
		}
	}

	/**
	 * Gets the prints the accelerator.
	 *
	 * @return the prints the accelerator
	 */
	public static KeyStroke getPrintAccelerator() {
		if (isMac()) {
			return KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.META_DOWN_MASK);
		} else {
			return KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK);
		}
	}

	/**
	 * Gets the redo key accelerator.
	 *
	 * @return the redo key accelerator
	 */
	public static KeyStroke getRedoKeyAccelerator() {
		if (isMac()) {
			return KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.META_DOWN_MASK);
		} else {
			return KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK);
		}
	}

	/**
	 * Gets the reload key accelerator.
	 *
	 * @return the reload key accelerator
	 */
	public static KeyStroke getReloadKeyAccelerator() {
		return KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK);
	}

	/**
	 * Gets the rename key accelerator.
	 *
	 * @return the rename key accelerator
	 */
	public static KeyStroke getRenameKeyAccelerator() {
		if (isMac()) {
			return KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
		} else {
			return KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
		}
	}

	/**
	 * Gets the save file accelerator.
	 *
	 * @return the save file accelerator
	 */
	public static KeyStroke getSaveFileAccelerator() {
		if (isMac()) {
			return KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.META_DOWN_MASK);
		} else {
			return KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK);
		}
	}

	/**
	 * Gets the select all key accelerator.
	 *
	 * @return the select all key accelerator
	 */
	public static KeyStroke getSelectAllKeyAccelerator() {
		if (isMac()) {
			return KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.META_DOWN_MASK);
		} else {
			return KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK);
		}
	}

	/**
	 * Gets the selection expand left key accelerator.
	 *
	 * @return the selection expand left key accelerator
	 */
	public static KeyStroke getSelectionExpandLeftKeyAccelerator() {
		if (isMac()) {
			return KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, InputEvent.ALT_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK);
		} else {
			return KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, InputEvent.ALT_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK);
		}
	}

	/**
	 * Gets the selection expand right key accelerator.
	 *
	 * @return the selection expand right key accelerator
	 */
	public static KeyStroke getSelectionExpandRightKeyAccelerator() {
		if (isMac()) {
			return KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, InputEvent.ALT_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK);
		} else {
			return KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, InputEvent.ALT_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK);
		}
	}

	/**
	 * Gets the standard command modifier key name.
	 *
	 * @return the standard command modifier key name
	 */
	public static String getStandardCommandModifierKeyName() {
		if (isMac()) {
			return "⌘";
		} else {
			return "Ctrl";
		}
	}

	/**
	 * Gets the toggle ignore gap in translation key accelerator.
	 *
	 * @return the toggle ignore gap in translation key accelerator
	 */
	public static KeyStroke getToggleIgnoreGapInTranslationKeyAccelerator() {
		if (isMac()) {
			return KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.META_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK);
		} else {
			return KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK);
		}
	}

	/**
	 * Gets the toggle translate show aa code key accelerator.
	 *
	 * @return the toggle translate show aa code key accelerator
	 */
	public static KeyStroke getToggleTranslateShowAACodeKeyAccelerator() {
		if (isMac()) {
			return KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.SHIFT_DOWN_MASK | InputEvent.ALT_DOWN_MASK);
		} else {
			return KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.SHIFT_DOWN_MASK | InputEvent.ALT_DOWN_MASK);
		}
	}

	/**
	 * Gets the toggle translate show both key accelerator.
	 *
	 * @return the toggle translate show both key accelerator
	 */
	public static KeyStroke getToggleTranslateShowBothKeyAccelerator() {
		if (isMac()) {
			return KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.ALT_DOWN_MASK);
		} else {
			return KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.ALT_DOWN_MASK);
		}
	}

	/**
	 * Gets the toggle translation key accelerator.
	 *
	 * @return the toggle translation key accelerator
	 */
	public static KeyStroke getToggleTranslationKeyAccelerator() {
		if (isMac()) {
			return KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.META_DOWN_MASK);
		} else {
			return KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK);
		}
	}

	/**
	 * Gets the toggle translation one pos key accelerator.
	 *
	 * @return the toggle translation one pos key accelerator
	 */
	public static KeyStroke getToggleTranslationOnePosKeyAccelerator() {
		if (isMac()) {
			return KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.META_DOWN_MASK | InputEvent.ALT_DOWN_MASK);
		} else {
			return KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK);
		}
	}

	/**
	 * Gets the undo key accelerator.
	 *
	 * @return the undo key accelerator
	 */
	public static KeyStroke getUndoKeyAccelerator() {
		if (isMac()) {
			return KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.META_DOWN_MASK);
		} else {
			return KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK);
		}
	}

	/**
	 * Inc reading frame key accelerator.
	 *
	 * @return the key stroke
	 */
	public static KeyStroke incReadingFrameKeyAccelerator() {
		if (isMac()) {
			return KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, InputEvent.SHIFT_DOWN_MASK);
		} else {
			return KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, InputEvent.SHIFT_DOWN_MASK);
		}
	}

	/**
	 * Swing menus are looking pretty bad on Linux when the GTK LaF is used (See
	 * bug #6925412). It will most likely never be fixed soon so this method
	 * provides a workaround for it. It uses reflection to change the GTK style
	 * objects of Swing so popup menu borders have a minimum thickness of 1 and
	 * menu separators have a minimum vertical thickness of 1.
	 */

	public static final void installGtkPopupBugWorkaround() {
		// Get current look-and-feel implementation class
		LookAndFeel laf = UIManager.getLookAndFeel();
		Class<?> lafClass = laf.getClass();

		// Do nothing when not using the problematic LaF
		if (!lafClass.getName().equals("com.sun.java.swing.plaf.gtk.GTKLookAndFeel"))
			return;

		// We do reflection from here on. Failure is silently ignored. The
		// workaround is simply not installed when something goes wrong here
		try {
			// Access the GTK style factory
			Field field = lafClass.getDeclaredField("styleFactory");
			boolean accessible = field.isAccessible();
			field.setAccessible(true);
			Object styleFactory = field.get(laf);
			field.setAccessible(accessible);

			// Fix the horizontal and vertical thickness of popup menu style
			Object style = getGtkStyle(styleFactory, new JPopupMenu(), "POPUP_MENU");
			fixGtkThickness(style, "yThickness");
			fixGtkThickness(style, "xThickness");

			// Fix the vertical thickness of the popup menu separator style
			style = getGtkStyle(styleFactory, new JSeparator(), "POPUP_MENU_SEPARATOR");
			fixGtkThickness(style, "yThickness");
		} catch (Exception e) {
			// Silently ignored. Workaround can't be applied.
		}
	}

	/**
	 * Checks if is 32 bit os.
	 *
	 * @return true, if is 32 bit os
	 */
	public static boolean is32BitOS() {
		String os = System.getProperty("os.arch").toLowerCase();
		return (os.indexOf("32") >= 0);
	}

	/**
	 * Checks if is 64 bit os.
	 *
	 * @return true, if is 64 bit os
	 */
	public static boolean is64BitOS() {
		String os = System.getProperty("os.arch").toLowerCase();
		return (os.indexOf("64") >= 0);
	}

	/**
	 * Checks if is anything but mac.
	 *
	 * @return true, if is anything but mac
	 */
	public static boolean isAnythingButMac() {
		return !isMac();
	}

	/**
	 * Checks if is linux or unix.
	 *
	 * @return true, if is linux or unix
	 */
	public static boolean isLinuxOrUnix() {

		String os = System.getProperty("os.name").toLowerCase();
		// linux or unix
		return (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0);

	}

	/**
	 * Checks if is mac.
	 *
	 * @return true, if is mac
	 */
	public static boolean isMac() {

		String os = System.getProperty("os.name").toLowerCase();
		// Mac
		return (os.indexOf("mac") >= 0);

	}

	/**
	 * Checks if is power pc.
	 *
	 * @return true, if is power pc
	 */
	public static boolean isPowerPC() {
		String os = System.getProperty("os.arch").toLowerCase();
		return (os.indexOf("powerpc") >= 0);
	}

	/**
	 * Checks if is running defect j filechooser jvm.
	 *
	 * @return true, if is running defect j filechooser jvm
	 */
	public static boolean isRunningDefectJFilechooserJVM() {
		logger.info("java ver" + getJavaVersionAsDouble());
		String javaVersion = System.getProperty("java.version").toLowerCase();
		if (isWindows() && getJavaVersionAsDouble() >= 7) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if is solaris.
	 *
	 * @return true, if is solaris
	 */
	public static boolean isSolaris() {

		String os = System.getProperty("os.name").toLowerCase();
		// Solaris
		return (os.indexOf("sunos") >= 0);

	}

	/**
	 * Checks if is windows.
	 *
	 * @return true, if is windows
	 */
	public static boolean isWindows() {

		String os = System.getProperty("os.name").toLowerCase();
		// windows
		return (os.indexOf("win") >= 0);

	}

	/**
	 * Register mac adapter.
	 *
	 * @param aliView
	 *            the ali view
	 * @return true, if successful
	 */
	/*
	 * 
	 * Register through reflection and other OS wont be affected
	 * 
	 */
	public static boolean registerMacAdapter(SbApp aliView) {

		boolean registerOK = false;
		if (isMac()) {
			logger.info("register Mac Adapter");
			try {
				Class macAdapter = Class.forName("storybook.toolkit.MacAdapter");

				if (macAdapter != null) {

					Class[] defArgs = { SbApp.class };
					Method regAppMethod = macAdapter.getDeclaredMethod("registerApplication", defArgs);

					if (regAppMethod != null) {
						Object[] args = { aliView };
						regAppMethod.invoke(macAdapter, args);
						registerOK = true;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} catch (Error err) {
				err.printStackTrace();
			}
		}
		return registerOK;
	}

}
