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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import storybook.SbConstants;
/*
 *
 * @author favdb
 */
import storybook.model.hbn.entity.Internal;
import storybook.toolkit.BookUtil;
import storybook.toolkit.EnvUtil;
import storybook.toolkit.I18N;
import storybook.toolkit.filefilter.HtmlFileFilter;
import storybook.toolkit.filefilter.TextFileFilter;
import storybook.toolkit.html.HtmlUtil;
import storybook.ui.MainFrame;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractExporter.
 */
public abstract class AbstractExporter {

	/** The file name. */
	private String fileName;
	
	/** The only html export. */
	private boolean onlyHtmlExport;
	
	/** The main frame. */
	protected MainFrame mainFrame;

	/**
	 * Instantiates a new abstract exporter.
	 *
	 * @param m the m
	 */
	public AbstractExporter(MainFrame m) {
		this(m, false);
	}

	/**
	 * Instantiates a new abstract exporter.
	 *
	 * @param m the m
	 * @param b the b
	 */
	public AbstractExporter(MainFrame m, boolean b) {
		this.mainFrame = m;
		this.onlyHtmlExport = b;
		this.fileName = "";
	}

	/**
	 * Export to html file.
	 *
	 * @return true, if successful
	 */
	public boolean exportToHtmlFile() {
		boolean bool = BookUtil.isUseHtmlScenes(this.mainFrame);
		if (this.onlyHtmlExport) {
			bool = true;
		}
		Internal internal = BookUtil.get(this.mainFrame, SbConstants.BookKey.EXPORT_DIRECTORY,
				EnvUtil.getDefaultExportDir(this.mainFrame));
		File file1 = new File(internal.getStringValue());
		JFileChooser chooser = new JFileChooser(file1);
		chooser.setApproveButtonText(I18N.getMsg("msg.common.export"));
		chooser.setSelectedFile(new File(getFileName()));
		if (bool) {
			chooser.setFileFilter(new HtmlFileFilter());
		} else {
			chooser.setFileFilter(new TextFileFilter());
		}
		int i = chooser.showOpenDialog(this.mainFrame);
		if (i == 1) {
			return false;
		}
		File outFile = chooser.getSelectedFile();
		if (bool) {
			if (!outFile.getName().endsWith(".html") || outFile.getName().endsWith(".htm")) {
				outFile = new File(outFile.getPath() + ".html");
			}
		} else if (!outFile.getName().endsWith(".txt")) {
			outFile = new File(outFile.getPath() + ".txt");
		}
		StringBuffer buffer = getContent();
		try {
			try (BufferedWriter outStream = new BufferedWriter(new FileWriter(outFile))) {
				String str = buffer.toString();
				outStream.write(str);
			}
		} catch (IOException e) {
			return false;
		}
		JOptionPane.showMessageDialog(this.mainFrame,
				I18N.getMsg("msg.common.export.success") + "\n" + outFile.getAbsolutePath(),
				I18N.getMsg("msg.common.export"), 1);
		return true;
	}

	/**
	 * Export to txt file.
	 *
	 * @return true, if successful
	 */
	public boolean exportToTxtFile() {
		Internal internal = BookUtil.get(this.mainFrame, SbConstants.BookKey.EXPORT_DIRECTORY,
				EnvUtil.getDefaultExportDir(this.mainFrame));
		File file1 = new File(internal.getStringValue());
		JFileChooser chooser = new JFileChooser(file1);
		chooser.setApproveButtonText(I18N.getMsg("msg.common.export"));
		chooser.setSelectedFile(new File(getFileName()));
		chooser.setFileFilter(new TextFileFilter());
		int i = chooser.showOpenDialog(this.mainFrame);
		if (i == 1) {
			return false;
		}
		File outFile = chooser.getSelectedFile();
		if (!outFile.getName().endsWith(".txt")) {
			outFile = new File(outFile.getPath() + ".txt");
		}
		StringBuffer buffer = getContent();
		try {
			try (BufferedWriter outStream = new BufferedWriter(new FileWriter(outFile))) {
				String str = buffer.toString();
				outStream.write(HtmlUtil.htmlToText(str, true));
			}
		} catch (IOException e) {
			return false;
		}
		JOptionPane.showMessageDialog(this.mainFrame,
				I18N.getMsg("msg.common.export.success") + "\n" + outFile.getAbsolutePath(),
				I18N.getMsg("msg.common.export"), 1);
		return true;
	}

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public abstract StringBuffer getContent();

	/**
	 * Gets the file name.
	 *
	 * @return the file name
	 */
	public String getFileName() {
		return this.fileName;
	}

	/**
	 * Sets the file name.
	 *
	 * @param s the new file name
	 */
	public void setFileName(String s) {
		this.fileName = s;
	}
}