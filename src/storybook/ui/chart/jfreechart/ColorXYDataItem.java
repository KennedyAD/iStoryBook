/*
 * SbApp: Open Source software for novelists and authors.
 * Original idea 2008 - 2012 Martin Mustun
 * Copyrigth (C) Favdb
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */
package storybook.ui.chart.jfreechart;

import java.awt.Color;

import org.jfree.data.xy.XYDataItem;

// TODO: Auto-generated Javadoc
/**
 * The Class ColorXYDataItem.
 */
public class ColorXYDataItem extends XYDataItem {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2152519481856361383L;
	
	/** The color. */
	private Color color;

	/**
	 * Instantiates a new color xy data item.
	 *
	 * @param paramDouble1 the param double1
	 * @param paramDouble2 the param double2
	 * @param paramColor the param color
	 */
	public ColorXYDataItem(double paramDouble1, double paramDouble2, Color paramColor) {
		super(paramDouble1, paramDouble2);
		this.color = paramColor;
	}

	/**
	 * Instantiates a new color xy data item.
	 *
	 * @param p1 the p1
	 * @param p2 the p2
	 * @param color the color
	 */
	public ColorXYDataItem(Number p1, Number p2, Color color) {
		super(p1, p2);
		this.color = color;
	}

	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public Color getColor() {
		return this.color;
	}
}