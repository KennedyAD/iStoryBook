/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package storybook.exporter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.itextpdf.text.Font;

import storybook.SbApp;

// TODO: Auto-generated Javadoc
/**
 * The Class ExportCsv.
 *
 * @author favdb
 */
public class ExportCsv {

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
	
	/** The parent. */
	private final Export parent;

	/**
	 * Instantiates a new export csv.
	 *
	 * @param parent the parent
	 * @param report the report
	 * @param fileName the file name
	 * @param headers the headers
	 * @param author the author
	 */
	ExportCsv(Export parent, String report, String fileName, List<ExportHeader> headers, String author) {
		this.parent = parent;
		this.report = report;
		this.fileName = fileName;
		this.headers = headers;
		this.author = author;
	}

	/**
	 * Close.
	 */
	public void close() {
		try {
			outStream.flush();
			outStream.close();
		} catch (IOException ex) {
			SbApp.error("ExportCsv.close()", ex);
		}
	}

	/**
	 * Open.
	 */
	public void open() {
		try {
			try {
				outStream = new BufferedWriter(new FileWriter(fileName));
			} catch (IOException ex) {
				SbApp.error("ExportCsv.open()", ex);
			}
			if (headers == null) {
				return;
			}
			String str = "";
			for (ExportHeader header : headers) {
				str += "\"" + header.getName() + "\";";
			}
			str += "\n";
			outStream.write(str, 0, str.length());
			outStream.flush();
		} catch (IOException ex) {
			SbApp.error("ExportCsv.open()", ex);
		}
	}

	/**
	 * Write row.
	 *
	 * @param body the body
	 */
	public void writeRow(String[] body) {
		try {
			String str = "";
			String quotes = "'", comma = ";";
			if (parent.parent.paramExport.csvDoubleQuotes) {
				quotes = "\"";
			}
			if (parent.parent.paramExport.csvNoQuotes) {
				quotes = "";
			}
			if (parent.parent.paramExport.csvComma) {
				quotes = ",";
			}
			for (String s : body) {
				str += quotes + ("".equals(s) ? " " : s) + quotes + comma;
			}
			str += "\n";
			outStream.write(str, 0, str.length());
			outStream.flush();
		} catch (IOException ex) {
			SbApp.error("ExportCsv.writeRow()", ex);
		}
	}

	/**
	 * Write text.
	 *
	 * @param str the str
	 */
	void writeText(String str) {
		try {
			outStream.write(str + "\n", 0, str.length() + 1);
			outStream.flush();
		} catch (IOException ex) {
			SbApp.error("ExportCsv.writeText(" + str + ")", ex);
		}
	}

}
