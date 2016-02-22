/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storybook.exporter;

import storybook.SbConstants;
import storybook.toolkit.BookUtil;
import storybook.toolkit.I18N;

// TODO: Auto-generated Javadoc
/**
 * The Class ExportBookSummary.
 *
 * @author favdb
 */
public class ExportBookSummary {

	/** The parent. */
	private final Export parent;
	
	/** The html. */
	private ExportHtml html;
	
	/** The pdf. */
	private ExportPDF pdf;
	
	/** The csv. */
	private ExportCsv csv;
	
	/** The txt. */
	private ExportTxt txt;
	
	/** The odf. */
	private ExportOdf odf;

	/**
	 * Instantiates a new export book summary.
	 *
	 * @param m the m
	 */
	ExportBookSummary(Export m) {
		parent = m;
	}

	/**
	 * Debut.
	 *
	 * @return the string
	 */
	public String debut() {
		String str = "";
		switch (parent.format) {
		case "html":
			html.open(true);
			break;
		case "csv":
			csv.open();
			break;// no header
		case "txt":
			txt.open();
			break;// no header
		case "pdf":
			pdf.open();
			break;
		case "odf":
			odf.open();
			break;
		}
		return str;
	}

	/**
	 * Fin.
	 */
	private void fin() {
		switch (parent.format) {
		case "html":
			html.close(true);
			break;
		case "pdf":
			pdf.close();
			break;
		case "csv":
			csv.close();
			break;
		case "txt":
			txt.close();
			break;
		}
	}

	/**
	 * Gets the.
	 *
	 * @return the string
	 */
	public String get() {
		switch (parent.format) {
		case "html":
			html = new ExportHtml(parent, "BookSummary", parent.file.getAbsolutePath(), null, parent.author);
			break;
		case "csv":
			csv = new ExportCsv(parent, "BookSummary", parent.file.getAbsolutePath(), null, parent.author);
			break;
		case "txt":
			txt = new ExportTxt(parent, "BookSummary", parent.file.getAbsolutePath(), null, parent.author);
			break;
		case "pdf":
			pdf = new ExportPDF(parent, "BookSummary", parent.file.getAbsolutePath(), null, parent.author);
			break;
		case "odf":
			odf = new ExportOdf(parent, "BookSummary", parent.file.getAbsolutePath(), null, parent.author);
			break;
		}
		String str = debut();
		ligne(I18N.getMsgColon("msg.common.title"),
				BookUtil.get(parent.mainFrame, SbConstants.BookKey.TITLE, "").getStringValue());
		ligne(I18N.getMsgColon("msg.common.subtitle"),
				BookUtil.get(parent.mainFrame, SbConstants.BookKey.SUBTITLE, "").getStringValue());
		ligne(I18N.getMsgColon("msg.common.author_s"),
				BookUtil.get(parent.mainFrame, SbConstants.BookKey.AUTHOR, "").getStringValue());
		ligne(I18N.getMsgColon("msg.common.copyright"),
				BookUtil.get(parent.mainFrame, SbConstants.BookKey.COPYRIGHT, "").getStringValue());
		ligne(I18N.getMsgColon("msg.common.notes"),
				BookUtil.get(parent.mainFrame, SbConstants.BookKey.NOTES, "").getStringValue());
		ligne(I18N.getMsgColon("msg.common.blurb"),
				BookUtil.get(parent.mainFrame, SbConstants.BookKey.BLURB, "").getStringValue());
		fin();
		return str;
	}

	/**
	 * Ligne.
	 *
	 * @param lib the lib
	 * @param data the data
	 */
	private void ligne(String lib, String data) {
		String str = lib + " " + data;
		switch (parent.format) {
		case "html":
			str = "<b>" + lib + "</b> " + data;
			html.writeText(str, true);
			break;
		case "csv":
			csv.writeText(str);
			break;
		case "txt":
			txt.writeText(str);
			break;
		case "pdf":
			pdf.writeText(str);
			break;
		case "odf":
			odf.writeText(str);
			break;
		}
	}
}
