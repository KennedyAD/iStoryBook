
package storybook.exporter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.itextpdf.text.Font;

import storybook.SbApp;

// TODO: Auto-generated Javadoc
/**
 * The Class ExportTxt.
 *
 * @author favdb
 */
class ExportTxt {

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
	 * Instantiates a new export txt.
	 *
	 * @param parent the parent
	 * @param report the report
	 * @param fileName the file name
	 * @param headers the headers
	 * @param author the author
	 */
	ExportTxt(Export parent, String report, String fileName, List<ExportHeader> headers, String author) {
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
			SbApp.error("ExportTxt.close()", ex);
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
				SbApp.error("ExportTxt.open()", ex);
			}
			if (headers == null) {
				return;
			}
			String str = "";
			for (ExportHeader header : headers) {
				str += header.getName() + "\t";
			}
			str += "\n";
			outStream.write(str, 0, str.length());
			outStream.flush();
		} catch (IOException ex) {
			SbApp.error("ExportTxt.open()", ex);
		}
	}

	/**
	 * Write row.
	 *
	 * @param body the body
	 */
	public void writeRow(String[] body) {
		try {
			String str = "", tab = "\t";
			if (!parent.parent.paramExport.txtTab) {
				tab = parent.parent.paramExport.txtSeparator;
			}
			for (String s : body) {
				str += ("".equals(s) ? " " : s) + tab;
			}
			str += "\n";
			outStream.write(str, 0, str.length());
			outStream.flush();
		} catch (IOException ex) {
			SbApp.error("ExportTxt.writeRow(...)", ex);
		}
	}

	/**
	 * Write scene.
	 *
	 * @param text the text
	 */
	void writeScene(String text) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
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
			SbApp.error("ExportTxt.writeText(" + str + ")", ex);
		}
	}

}
