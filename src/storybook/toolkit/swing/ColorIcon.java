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

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

// TODO: Auto-generated Javadoc
/**
 * The Class ColorIcon.
 *
 * @author martin
 */
public class ColorIcon implements Icon {

	/** The height. */
	private int height = 8;
	
	/** The width. */
	private int width = 8;
	
	/** The color. */
	private Color color = null;

	/**
	 * Instantiates a new color icon.
	 *
	 * @param c the c
	 */
	public ColorIcon(Color c) {
		this.color = c;
	}

	/**
	 * Instantiates a new color icon.
	 *
	 * @param c the c
	 * @param height the height
	 * @param width the width
	 */
	public ColorIcon(Color c, int height, int width) {
		this.color = c;
		this.height = height;
		this.width = width;
	}

	/**
	 * Gets the icon height.
	 *
	 * @return the icon height
	 * @see javax.swing.Icon#getIconHeight()
	 */
	@Override
	public int getIconHeight() {
		return height;
	}

	/**
	 * Gets the icon width.
	 *
	 * @return the icon width
	 * @see javax.swing.Icon#getIconWidth()
	 */
	@Override
	public int getIconWidth() {
		return width;
	}

	/**
	 * Paint icon.
	 *
	 * @param c the c
	 * @param g the g
	 * @param x the x
	 * @param y the y
	 * @see javax.swing.Icon#paintIcon(Component, Graphics, int, int)
	 */
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		g.setColor(color);
		g.drawRect(x, y, width, height);
		g.fillRect(x, y, width, height);
	}

}
