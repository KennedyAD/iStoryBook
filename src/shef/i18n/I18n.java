/*
 * Created on Nov 6, 2007
 */
package shef.i18n;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

/**
 * @author Bob Tantlinger
 *
 */
public class I18n {

	private static final File LANG_PACK_DIR = new File(System.getProperty("user.dir"), "languages");
	private static final String DEFAULT_BUNDLE_NAME = "storybook.shef.i18n.I18N";

	private static final String MNEM_POSTFIX = ".mnemonic";

	public static final Properties BUNDLE_PROPS = new Properties();
	public static final Map<String, I18n> I18NS = new HashMap<>();

	public static Locale locale = Locale.getDefault();

	private ResourceBundle bundle;
	private final String _package;

	private I18n(String _package) {
		this._package = _package;
	}

	public String str(String key) {
		try {
			if (bundle == null) {
				bundle = createBundle(getLocale());
			}

			return bundle.getString(key);
		} catch (Exception ex) {
			return '!' + key + '!';
		}
	}

	public String str(String key, Locale locale) {
		try {
			return createBundle(locale).getString(key);
		} catch (Exception ex) {
			return '!' + key + '!';
		}
	}

	public char mnem(String key) {
		String s = str(key + MNEM_POSTFIX);
		if (s != null && s.length() > 0) {
			return s.charAt(0);
		}
		return '!';
	}

	public char mnem(String key, Locale loc) {
		String s = str(key + MNEM_POSTFIX, loc);
		if (s != null && s.length() > 0) {
			return s.charAt(0);
		}
		return '!';
	}

	private ResourceBundle createBundle(Locale loc) {
		//return ResourceBundle.getBundle("shef.i18n.messages", loc); //just return default
		return ResourceBundle.getBundle("shef.i18n.messages", Locale.getDefault());
	}

	public static I18n getInstance(String _package) {
		//I18n i18n = (I18n) I18NS.get(_package);
		I18n i18n = new I18n("shef.i18n.I18n");

		return i18n;
	}

	public static void setLocale(Locale loc) {
		locale = loc;
		for (I18n i18n : I18NS.values()) {
			i18n.bundle = null; //reset so bundle with new locale gets created...
		}
	}

	public static void setLocale(String locStr) throws IllegalArgumentException {
		Locale loc = localeFromString(locStr);
		if (loc == null) {
			throw new IllegalArgumentException("The locale " + locStr + " was not properly formatted");
		}
		setLocale(loc);
	}

	public static Locale getLocale() {
		if (locale == null) {
			Locale.getDefault();
		}
		return locale;
	}

	/**
	 * Converts slashes to dots in a pathname
	 *
	 * @param path
	 * @return
	 */
	private static String slashesToDots(String path) {
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(path, "/");
		while (st.hasMoreTokens()) {
			sb.append(".");
			sb.append(st.nextToken());
		}

		if (sb.toString().startsWith(".")) {
			sb.deleteCharAt(0);
		}

		return sb.toString();
	}

	private static String createBundleName(String bundlePackage, String bundleName) {
		StringBuilder sb = new StringBuilder(slashesToDots(bundlePackage));
		if (!sb.toString().endsWith(".")) {
			sb.append('.');
		}
		sb.append(bundleName);
		return sb.toString();
	}

	public static void setBundleForPackage(String _package, String bundle) {
		if (bundle == null) {
			BUNDLE_PROPS.remove(_package);
		} else {
			BUNDLE_PROPS.setProperty(_package, bundle);
		}

		I18n i18n = (I18n) I18NS.get(_package);
		if (i18n != null) {
			i18n.bundle = null; //reset to null so the bundle is recreated for the new name
		}
	}

	public static String getBundleForPackage(String _package) {
		String bun = BUNDLE_PROPS.getProperty(_package);
		if (bun == null)//use default        
		{
			bun = createBundleName(_package, DEFAULT_BUNDLE_NAME);
		}

		return bun;
	}

	public static File getLanguagePackDirectory() {
		return LANG_PACK_DIR;
	}

	public static File[] getAvailableLanguagePacks() {
		Locale[] locs = getAvailableLanguagePackLocales();
		List<File> packs = new ArrayList<>();

		for (Locale loc : locs) {
			String name = loc.toString() + ".zip";
			File f = new File(LANG_PACK_DIR, name);
			if (f.isFile() && f.canRead()) {
				packs.add(f);
			}
		}

		return (File[]) packs.toArray(new File[packs.size()]);
	}

	public static Locale[] getAvailableLanguagePackLocales() {
		List<Locale> locs = new ArrayList<>();
		File dir = getLanguagePackDirectory();
		if (dir.isDirectory() && dir.canRead()) {
			File[] packs = dir.listFiles(new ZipFileFilter());
			if (packs != null) {
				for (File pack : packs) {
					String name = pack.getName();
					int p = name.lastIndexOf(".");
					if (p != -1) {
						String locStr = name.substring(0, p);
						Locale loc = localeFromString(locStr);
						if (loc != null) {
							locs.add(loc);
						}
					}
				}
			}
		}

		return (Locale[]) locs.toArray(new Locale[locs.size()]);
	}

	public static Locale localeFromString(String locStr) {
		String[] parts = locStr.split("_");
		if (parts.length > 0 || parts.length <= 3) {
			String lang = parts[0];
			String country = (parts.length > 1) ? parts[1] : "";
			String varient = (parts.length > 2) ? parts[2] : "";
			return new Locale(lang, country, varient);
		}

		return null;
	}

	private static class ZipFileFilter implements FileFilter {

		@Override
		public boolean accept(File f) {
			if (f.isFile() && f.canRead()) {
				String name = f.getName().toLowerCase();
				if (name.endsWith(".zip")) {
					return true;
				}
			}
			return false;
		}
	}
}
