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
package storybook.exporter;

import storybook.ui.MainFrame;

// TODO: Auto-generated Javadoc
/**
 * The Class InfoExporter.
 *
 * @author martin
 */
public class InfoExporter extends AbstractExporter {

	/** The text. */
	private String text = "";

	/**
	 * Instantiates a new info exporter.
	 *
	 * @param m the m
	 */
	public InfoExporter(MainFrame m) {
		super(m, true);
	}

	/** (non-Javadoc)
	 * @see storybook.exporter.AbstractExporter#getContent()
	 */
	@Override
	public StringBuffer getContent() {
		return new StringBuffer(this.text);
	}

	/**
	 * Sets the content.
	 *
	 * @param s the new content
	 */
	public void setContent(String s) {
		this.text = s;
	}
}