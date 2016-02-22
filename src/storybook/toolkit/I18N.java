/*
 Storybook: Scene-based software for novelists and authors.
 Copyright (C) 2008 - 2011 Martin Mustun

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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

import storybook.SbApp;

// TODO: Auto-generated Javadoc
/**
 * The Class I18N.
 */
public class I18N {

	/** The Constant TIME_FORMAT. */
	public final static String TIME_FORMAT = "HH:mm:ss";
	
	/** The icon resource bundle. */
	// public final static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static ResourceBundle iconResourceBundle = null;
	
	/** The message resource bundle. */
	private static ResourceBundle messageResourceBundle = null;

	/**
	 * Creates the image icon.
	 *
	 * @param c the c
	 * @param path the path
	 * @return the image icon
	 */
	public static ImageIcon createImageIcon(Class<?> c, String path) {
		java.net.URL imgURL = c.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	/**
	 * Gets the country language.
	 *
	 * @param locale the locale
	 * @return the country language
	 */
	public static String getCountryLanguage(Locale locale) {
		return locale.getLanguage() + "_" + locale.getCountry();
	}

	/**
	 * Gets the date time.
	 *
	 * @param date the date
	 * @return the date time
	 */
	public static String getDateTime(Date date) {
		return DateUtil.simpleDateTimeToString(date);
	}

	/**
	 * Gets the date time formatter.
	 *
	 * @return the date time formatter
	 */
	public static DateFormat getDateTimeFormatter() {
		return DateFormat.getDateTimeInstance();
	}

	/**
	 * Gets the ext msg.
	 *
	 * @param resourceKey the resource key
	 * @return the ext msg
	 */
	public static String getExtMsg(String resourceKey) {
		File f = new File(SbApp.getI18nFile() + ".properties");
		if (!f.exists()) {
			SbApp.setI18nFile("");
			return (getMessageResourceBundle().getString(resourceKey));
		}
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(SbApp.getI18nFile() + ".properties");
			prop.load(input);
			input.close();
			return (prop.getProperty(resourceKey));
		} catch (IOException ex) {
			System.out.println("default msg " + resourceKey);
			return (getMessageResourceBundle().getString(resourceKey));
		}
	}

	/**
	 * Gets the icon.
	 *
	 * @param resourceKey the resource key
	 * @return the icon
	 */
	public static Icon getIcon(String resourceKey) {
		return getImageIcon(resourceKey);
	}

	/**
	 * Gets the icon external.
	 *
	 * @param filename the filename
	 * @return the icon external
	 */
	public static Icon getIconExternal(String filename) {
		ImageIcon iconext = new ImageIcon(filename);
		return (iconext);
	}

	/**
	 * Gets the icon image.
	 *
	 * @param resourceKey the resource key
	 * @return the icon image
	 */
	public static Image getIconImage(String resourceKey) {
		ImageIcon icon = (ImageIcon) I18N.getIcon(resourceKey);
		return icon.getImage();
	}

	/**
	 * Gets the icon label.
	 *
	 * @param resourceKey the resource key
	 * @return the icon label
	 */
	public static JLabel getIconLabel(String resourceKey) {
		return new JLabel(getIcon(resourceKey));
	}

	/**
	 * Gets the icon resource bundle.
	 *
	 * @return the icon resource bundle
	 */
	public static final ResourceBundle getIconResourceBundle() {
		if (iconResourceBundle == null) {
			iconResourceBundle = ResourceBundle.getBundle("storybook.resources.icons.icons", Locale.getDefault());
		}
		return iconResourceBundle;
	}

	/**
	 * Gets the image icon.
	 *
	 * @param resourceKey the resource key
	 * @return the image icon
	 */
	public static ImageIcon getImageIcon(String resourceKey) {
		ResourceBundle rb = getIconResourceBundle();
		String name = rb.getString(resourceKey);
		ImageIcon icon = createImageIcon(SbApp.class, name);
		return icon;
	}

	/**
	 * Gets the long date formatter.
	 *
	 * @return the long date formatter
	 */
	public static DateFormat getLongDateFormatter() {
		return DateFormat.getDateInstance(DateFormat.LONG);
	}

	/**
	 * Gets the medium date formatter.
	 *
	 * @return the medium date formatter
	 */
	public static DateFormat getMediumDateFormatter() {
		return DateFormat.getDateInstance(DateFormat.MEDIUM);
	}

	/**
	 * Gets the message resource bundle.
	 *
	 * @return the message resource bundle
	 */
	public static final ResourceBundle getMessageResourceBundle() {
		if (messageResourceBundle == null) {
			messageResourceBundle = ResourceBundle.getBundle("storybook.msg.messages", Locale.getDefault());
		}
		return messageResourceBundle;
	}

	/**
	 * Gets the mnemonic.
	 *
	 * @param key the key
	 * @return the mnemonic
	 */
	public static char getMnemonic(String key) {
		String s = getMsg(key + ".mnemonic");
		if (s != null && s.length() > 0) {
			return s.charAt(0);
		}
		return '!';
	}

	/**
	 * Gets the msg.
	 *
	 * @param resourceKey the resource key
	 * @return the msg
	 */
	public static String getMsg(String resourceKey) {
		if (SbApp.getI18nFile() != null) {
			return (getExtMsg(resourceKey));
		}
		ResourceBundle rb = getMessageResourceBundle();
		try {
			return rb.getString(resourceKey);
		} catch (Exception ex) {
			return '!' + resourceKey + '!';
		}
	}

	/**
	 * Gets the msg.
	 *
	 * @param resourceKey the resource key
	 * @param required the required
	 * @return the msg
	 */
	public static String getMsg(String resourceKey, boolean required) {
		ResourceBundle rb = getMessageResourceBundle();
		StringBuilder buf = new StringBuilder();
		if (required) {
			buf.append('*');
		}
		buf.append(rb.getString(resourceKey));
		return buf.toString();
	}

	/**
	 * Gets the msg.
	 *
	 * @param resourceKey the resource key
	 * @param arg the arg
	 * @return the msg
	 */
	public static final String getMsg(String resourceKey, Object arg) {
		Object[] args = new Object[] { arg };
		return getMsg(resourceKey, args);
	}

	/**
	 * Gets the msg.
	 *
	 * @param resourceKey the resource key
	 * @param args the args
	 * @return the msg
	 */
	public static final String getMsg(String resourceKey, Object[] args) {
		MessageFormat formatter = new MessageFormat("");
		formatter.setLocale(Locale.getDefault());
		String pattern = getMsg(resourceKey);// getMessageResourceBundle().getString(resourceKey);
		formatter.applyPattern(pattern);
		return formatter.format(args);
	}

	/**
	 * Gets the msg colon.
	 *
	 * @param resourceKey the resource key
	 * @return the msg colon
	 */
	public static String getMsgColon(String resourceKey) {
		return getMsgColon(resourceKey, false);
	}

	/**
	 * Gets the msg colon.
	 *
	 * @param resourceKey the resource key
	 * @param required the required
	 * @return the msg colon
	 */
	public static String getMsgColon(String resourceKey, boolean required) {
		ResourceBundle rb = getMessageResourceBundle();
		StringBuilder buf = new StringBuilder();
		if (required) {
			buf.append('*');
		}
		buf.append(rb.getString(resourceKey));
		buf.append(':');
		return buf.toString();
	}

	/**
	 * Gets the msg dot.
	 *
	 * @param resourceKey the resource key
	 * @return the msg dot
	 */
	public static String getMsgDot(String resourceKey) {
		return getMsg(resourceKey) + "...";
	}

	/**
	 * Gets the short date formatter.
	 *
	 * @return the short date formatter
	 */
	public static DateFormat getShortDateFormatter() {
		return DateFormat.getDateInstance(DateFormat.SHORT);
	}

	/**
	 * Inits the resource bundles.
	 *
	 * @param locale the locale
	 */
	public static final void initResourceBundles(Locale locale) {
		ResourceBundle.clearCache();
		messageResourceBundle = null;
		Locale.setDefault(locale);
		UIManager.getDefaults().setDefaultLocale(locale);
		SbApp.getInstance().setLocale(locale);
	}

	/**
	 * Checks if is english.
	 *
	 * @return true, if is english
	 */
	public static boolean isEnglish() {
		Locale locale = Locale.getDefault();
		Locale de = new Locale("en", "US");
		return locale.equals(de);
	}

	/**
	 * Sets the mnemonic.
	 *
	 * @param menu the menu
	 * @param englishKey the english key
	 */
	public static final void setMnemonic(JMenu menu, int englishKey) {
		setMnemonic(menu, englishKey, englishKey);
	}

	/**
	 * Sets the mnemonic.
	 *
	 * @param menu the menu
	 * @param englishKey the english key
	 * @param germanKey the german key
	 */
	public static final void setMnemonic(JMenu menu, int englishKey, int germanKey) {
		if (Locale.getDefault() == Locale.GERMANY) {
			menu.setMnemonic(germanKey);
		} else {
			menu.setMnemonic(englishKey);
		}
	}

	/**
	 * Sets the mnemonic.
	 *
	 * @param menuItem the menu item
	 * @param englishKey the english key
	 */
	public static final void setMnemonic(JMenuItem menuItem, int englishKey) {
		setMnemonic(menuItem, englishKey, englishKey);
	}

	/**
	 * Sets the mnemonic.
	 *
	 * @param menuItem the menu item
	 * @param englishKey the english key
	 * @param germanKey the german key
	 */
	public static final void setMnemonic(JMenuItem menuItem, int englishKey, int germanKey) {
		if (Locale.getDefault() == Locale.GERMANY) {
			menuItem.setMnemonic(germanKey);
		} else {
			menuItem.setMnemonic(englishKey);
		}
	}
}
