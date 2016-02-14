/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package storybook.exporter;

import java.io.BufferedWriter;
import java.util.List;

import com.itextpdf.text.Font;

/**
 *
 * @author favdb
 */
class ExportOdf {
	// TODO ExportOdf

	String report;
	String fileName = "";
	List<ExportHeader> headers;
	Font fontHeader, fontBody;
	BufferedWriter outStream;
	String author;

	ExportOdf(Export parent, String report, String fileName, List<ExportHeader> headers, String author) {
		this.report = report;
		this.fileName = fileName;
		this.headers = headers;
		this.author = author;
	}

	public void close() {
	}

	public void open() {
	}

	void writeChapter(String chapter) {

	}

	void writePart(String part) {

	}

	public void writeRow(String data) {
	}

	void writeRow(String[] body) {
	}

	void writeScene(String scene) {

	}

	void writeText(String str) {
	}

}
