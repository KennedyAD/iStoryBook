/*
Storybook: Scene-based software for novelists and authors.
Copyright (C) 2008-2009 Martin Mustun

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

package storybook.toolkit.swing.label;

import java.awt.Color;

import javax.swing.Icon;
import javax.swing.JLabel;

import storybook.toolkit.swing.ColorUtil;


// TODO: Auto-generated Javadoc
/**
 * The Class CleverLabel.
 */
public class CleverLabel extends JLabel {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2760117058780642566L;

	/**
	 * Instantiates a new clever label.
	 */
	public CleverLabel() {
		super();
	}

	/**
	 * Instantiates a new clever label.
	 *
	 * @param image the image
	 */
	public CleverLabel(Icon image) {
		super(image);
	}

	/**
	 * Instantiates a new clever label.
	 *
	 * @param image the image
	 * @param horizontalAlignment the horizontal alignment
	 */
	public CleverLabel(Icon image, int horizontalAlignment) {
		super(image, horizontalAlignment);
	}

	/**
	 * Instantiates a new clever label.
	 *
	 * @param text the text
	 */
	public CleverLabel(String text) {
		super(text);
	}

	/**
	 * Instantiates a new clever label.
	 *
	 * @param text the text
	 * @param icon the icon
	 * @param horizontalAlignment the horizontal alignment
	 */
	public CleverLabel(String text, Icon icon, int horizontalAlignment) {
		super(text, icon, horizontalAlignment);
	}

	/**
	 * Instantiates a new clever label.
	 *
	 * @param text the text
	 * @param horizontalAlignment the horizontal alignment
	 */
	public CleverLabel(String text, int horizontalAlignment) {
		super(text, horizontalAlignment);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setBackground(java.awt.Color)
	 */
	@Override
	public void setBackground(Color bg) {
		if (bg == null) {
			setOpaque(false);
			return;
		}
		super.setBackground(bg);
		setOpaque(true);
		if (ColorUtil.isDark(bg)) {
			setForeground(Color.white);
		}
	}
}
