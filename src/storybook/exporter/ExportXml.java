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
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import storybook.SbApp;
import storybook.SbConstants;
import storybook.toolkit.BookUtil;
import storybook.toolkit.TextUtil;

// TODO: Auto-generated Javadoc
/**
 * Export de toutes les informations au format DocBook.xml étendu l'extension
 * est spécifique à oStorybook, elle consiste à ajouter en fin de fichier les
 * annexes suivantes: - fiche de personnage - fiche de relation entre deux
 * personnages - fiche d'objet - fiche de lieu - fiche d'étiquette - fiche
 * d'attribut - fiche de catégorie - fiche de genre - fiche de partie - fiche de
 * Trame d'histoire - fiche de TimeEvent
 *
 * @author favdb
 */
public class ExportXml {

	/** The report. */
	String report;
	
	/** The file name. */
	String fileName = "";
	
	/** The out stream. */
	BufferedWriter outStream;
	
	/** The author. */
	String author;
	
	/** The parent. */
	private final Export parent;
	
	/** The is opened. */
	public boolean isOpened;

	/**
	 * Instantiates a new export xml.
	 *
	 * @param parent the parent
	 * @param report the report
	 * @param fileName the file name
	 * @param headers the headers
	 * @param author the author
	 */
	ExportXml(Export parent, String report, String fileName, List<ExportHeader> headers, String author) {
		this.parent = parent;
		this.report = report;
		this.fileName = fileName;
		this.author = author;
		this.isOpened = false;
	}

	/**
	 * Close.
	 */
	public void close() {
		try {
			String str = "";
			str += "</book>\n";
			// str += "</xml>";
			outStream.write(str, 0, str.length());
			outStream.flush();
			outStream.close();
			isOpened = false;
		} catch (IOException ex) {
			SbApp.error("ExportXml.close()", ex);
		}
	}

	/**
	 * End chapter.
	 */
	void endChapter() {
		writeText("</chapter>\n");
	}

	/**
	 * End part.
	 */
	void endPart() {
		writeText("</part>\n");
	}

	/**
	 * Open.
	 */
	public void open() {
		try {
			outStream = new BufferedWriter(new FileWriter(fileName));
			String str = "<?xml version='1.0'?>\n";
			str += "<!DOCTYPE book PUBLIC \"-//OASIS//DTD DocBook V5.0//EN\" ";
			str += "\"http://www.oasis-open.org/docbook/xml/5.0/docbook.dtd\">\n";
			str += "<book>\n";
			str += "<info>\n";
			str += "<title>" + BookUtil.get(parent.mainFrame, SbConstants.BookKey.TITLE, "").getStringValue()
					+ "</title>\n";
			str += "</info>\n";
			outStream.write(str, 0, str.length());
			outStream.flush();
			isOpened = true;
		} catch (IOException ex) {
			SbApp.error("ExportXml.open()", ex);
		}
	}

	/**
	 * To text.
	 *
	 * @param inTxt the in txt
	 * @return the string
	 */
	String toText(String inTxt) {
		String outTxt = inTxt.replaceAll("    <div>\n" + "      \n" + "    </div>", "");
		return outTxt.replaceAll("    <p>\n" + "      \n" + "    </p>", "");
	}

	/**
	 * Write chapter.
	 *
	 * @param str the str
	 */
	void writeChapter(String str) {
		writeText("<chapter>\n<title>" + str + "</title>\n");
	}

	/**
	 * Write part.
	 *
	 * @param str the str
	 */
	void writePart(String str) {
		writeText("<part>\n<title>" + str + "</title>\n");
	}

	/**
	 * Write scene.
	 *
	 * @param str the str
	 */
	void writeScene(String str) {
		writeText("<para>\n" + toText(str) + "</para>\n");
	}

	/**
	 * Write text.
	 *
	 * @param str the str
	 */
	public void writeText(String str) {
		if ("".equals(str)) {
			return;
		}
		SbApp.trace("ExportXml.writeText(" + TextUtil.truncateString(str, 32) + ")");
		try {
			String s = str;
			outStream.write(s, 0, s.length());
			outStream.flush();
		} catch (IOException ex) {
			SbApp.error("ExportXml.writeText(" + str + ")", ex);
		}
	}
}
