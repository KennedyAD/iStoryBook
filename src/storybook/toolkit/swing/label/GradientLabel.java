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
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;

import storybook.toolkit.swing.ColorUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class GradientLabel.
 */
public class GradientLabel extends JLabel {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4102708597623810186L;
	
	/** The start bg color. */
	private Color startBgColor = Color.white;
	
	/** The end bg color. */
	private Color endBgColor = Color.black;
	
	/** The show bg gradient. */
	private boolean showBgGradient = true;

	/**
	 * Instantiates a new gradient label.
	 */
	public GradientLabel() {
		showBgGradient = false;
	}

	/**
	 * Instantiates a new gradient label.
	 *
	 * @param showBgGradient the show bg gradient
	 * @param startBgColor the start bg color
	 * @param endBgColor the end bg color
	 */
	public GradientLabel(boolean showBgGradient, Color startBgColor, Color endBgColor) {
		super();
		this.showBgGradient = showBgGradient;
		this.startBgColor = startBgColor;
		this.endBgColor = endBgColor;
	}

	/**
	 * Instantiates a new gradient label.
	 *
	 * @param text the text
	 */
	public GradientLabel(String text) {
		super(text);
		showBgGradient = false;
	}

	/**
	 * Instantiates a new gradient label.
	 *
	 * @param text the text
	 * @param horizontalAlignment the horizontal alignment
	 * @param showBgGradient the show bg gradient
	 * @param startBgColor the start bg color
	 * @param endBgColor the end bg color
	 */
	public GradientLabel(String text, int horizontalAlignment, boolean showBgGradient, Color startBgColor,
			Color endBgColor) {
		super(text, horizontalAlignment);
		this.showBgGradient = showBgGradient;
		this.startBgColor = startBgColor;
		this.endBgColor = endBgColor;
	}

	/**
	 * Gets the end bg color.
	 *
	 * @return the end bg color
	 */
	public Color getEndBgColor() {
		return endBgColor;
	}

	/**
	 * Gets the start bg color.
	 *
	 * @return the start bg color
	 */
	public Color getStartBgColor() {
		return startBgColor;
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		if (showBgGradient) {
			Graphics2D g2d = (Graphics2D) g;
			GradientPaint gradient = new GradientPaint(0, 0, startBgColor, this.getWidth(), this.getHeight(),
					ColorUtil.blend(Color.white, endBgColor));
			g2d.setPaint(gradient);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
		super.paintComponent(g);
	}
}
