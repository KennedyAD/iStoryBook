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

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import storybook.SbApp;
import storybook.model.hbn.entity.AbstractEntity;
import storybook.ui.MainFrame;

// TODO: Auto-generated Javadoc
/**
 * The Class IOUtil.
 *
 * @author martin
 */
public class IOUtil {

	/**
	 * Cleanup filename.
	 *
	 * @param paramString the param string
	 * @return the string
	 */
	public static String cleanupFilename(String paramString) {
		String str = paramString.replaceAll("[\\/:*?\"<>|]", "");
		str = str.replaceAll("\\\\", "");
		return str;
	}

	/**
	 * Gets the entity file name for export.
	 *
	 * @param paramMainFrame the param main frame
	 * @param paramString the param string
	 * @param paramAbstractEntity the param abstract entity
	 * @return the entity file name for export
	 */
	public static String getEntityFileNameForExport(MainFrame paramMainFrame, String paramString,
			AbstractEntity paramAbstractEntity) {
		String str1 = "";
		try {
			String str2 = paramMainFrame.getDbFile().getName();
			if (paramAbstractEntity == null) {
				str1 = str2 + " (" + I18N.getMsg("msg.book.info") + ")";
			} else {
				str1 = str2 + " (" + paramString + ") - " + paramAbstractEntity.toString();
			}
			str1 = cleanupFilename(str1);
			str1 = str1.replaceAll("\\[", "(");
			str1 = str1.replaceAll("\\]", ")");
			str1 = str1.substring(0, 50);
		} catch (Exception localException) {
		}
		return str1;
	}

	/**
	 * Read file as string.
	 *
	 * @param filePath the file path
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static String readFileAsString(String filePath) throws java.io.IOException {
		byte[] buffer = new byte[(int) new File(filePath).length()];
		BufferedInputStream f = null;
		try {
			f = new BufferedInputStream(new FileInputStream(filePath));
			f.read(buffer);
		} finally {
			if (f != null) {
				try {
					f.close();
				} catch (IOException e) {
					SbApp.error("IOUtil.readFileAsString(" + filePath + ")", e);
				}
			}
		}
		return new String(buffer);
	}

	/**
	 * String to input stream.
	 *
	 * @param str the str
	 * @return the input stream
	 */
	public static InputStream stringToInputStream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}
}
