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

package storybook.toolkit.swing;

import java.awt.Font;

// TODO: Auto-generated Javadoc
/**
 * The Class FontUtil.
 *
 * @author martin
 */
public class FontUtil {

	/** The default font. */
	private static Font defaultFont;

	/**
	 * Gets the bold font.
	 *
	 * @return the bold font
	 */
	public static Font getBoldFont() {
		return new Font(defaultFont.getName(), Font.BOLD, defaultFont.getSize());
	}

	/**
	 * Gets the default font.
	 *
	 * @return the default font
	 */
	public static Font getDefaultFont() {
		return defaultFont;
	}

	/**
	 * Sets the default font.
	 *
	 * @param font the new default font
	 */
	public static void setDefaultFont(Font font) {
		if (font == null) {
			return;
		}
		SwingUtil.setUIFont(new javax.swing.plaf.FontUIResource(font.getName(), font.getStyle(), font.getSize()));
		defaultFont = font;
	}
}
