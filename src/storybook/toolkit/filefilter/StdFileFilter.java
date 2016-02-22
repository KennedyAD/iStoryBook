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
package storybook.toolkit.filefilter;

import java.io.File;

// TODO: Auto-generated Javadoc
/**
 * The Class StdFileFilter.
 *
 * @author favdb
 */
public class StdFileFilter extends javax.swing.filechooser.FileFilter {
	
	/** The ext. */
	String ext = "";
	
	/** The desc. */
	String desc = "";

	/**
	 * Instantiates a new std file filter.
	 *
	 * @param extension the extension
	 * @param description the description
	 */
	public StdFileFilter(String extension, String description) {
		if (extension.indexOf(".") == -1) {
			extension = "." + extension;
		}
		this.ext = extension;
		this.desc = description;
	}

	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
	 */
	@Override
	public boolean accept(File file) {
		if (file.isDirectory()) {
			return true;
		}
		String filename = file.getName();
		return filename.endsWith(ext);
	}

	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#getDescription()
	 */
	@Override
	public String getDescription() {
		return (desc + " (" + ext + ")");
	}

}
