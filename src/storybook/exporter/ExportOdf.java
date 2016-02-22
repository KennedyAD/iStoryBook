/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package storybook.exporter;

import java.io.BufferedWriter;
import java.util.List;

import com.itextpdf.text.Font;

// TODO: Auto-generated Javadoc
/**
 * The Class ExportOdf.
 *
 * @author favdb
 */
class ExportOdf {
	// TODO ExportOdf

	/** The report. */
	String report;
	
	/** The file name. */
	String fileName = "";
	
	/** The headers. */
	List<ExportHeader> headers;
	
	/** The font body. */
	Font fontHeader, fontBody;
	
	/** The out stream. */
	BufferedWriter outStream;
	
	/** The author. */
	String author;

	/**
	 * Instantiates a new export odf.
	 *
	 * @param parent the parent
	 * @param report the report
	 * @param fileName the file name
	 * @param headers the headers
	 * @param author the author
	 */
	ExportOdf(Export parent, String report, String fileName, List<ExportHeader> headers, String author) {
		this.report = report;
		this.fileName = fileName;
		this.headers = headers;
		this.author = author;
	}

	/**
	 * Close.
	 */
	public void close() {
	}

	/**
	 * Open.
	 */
	public void open() {
	}

	/**
	 * Write chapter.
	 *
	 * @param chapter the chapter
	 */
	void writeChapter(String chapter) {

	}

	/**
	 * Write part.
	 *
	 * @param part the part
	 */
	void writePart(String part) {

	}

	/**
	 * Write row.
	 *
	 * @param data the data
	 */
	public void writeRow(String data) {
	}

	/**
	 * Write row.
	 *
	 * @param body the body
	 */
	void writeRow(String[] body) {
	}

	/**
	 * Write scene.
	 *
	 * @param scene the scene
	 */
	void writeScene(String scene) {

	}

	/**
	 * Write text.
	 *
	 * @param str the str
	 */
	void writeText(String str) {
	}

}
