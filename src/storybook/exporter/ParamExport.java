/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storybook.exporter;

import storybook.SbConstants;
import storybook.toolkit.BookUtil;
import storybook.ui.MainFrame;

// TODO: Auto-generated Javadoc
/**
 * The Class ParamExport.
 *
 * @author favdb
 */
public class ParamExport {

	/** The main frame. */
	MainFrame mainFrame;
	
	/** The csv no quotes. */
	boolean csvSingleQuotes, csvDoubleQuotes, csvNoQuotes;
	
	/** The csv comma. */
	boolean csvComma;
	
	/** The txt tab. */
	boolean txtTab;
	
	/** The txt separator. */
	String txtSeparator;
	
	/** The html use css. */
	boolean htmlUseCss;
	
	/** The html css file. */
	String htmlCssFile;
	
	/** The is export chapter numbers. */
	boolean isExportChapterNumbers;
	
	/** The is export chapter numbers roman. */
	boolean isExportChapterNumbersRoman;
	
	/** The is export chapter titles. */
	boolean isExportChapterTitles;
	
	/** The is export chapter dates locs. */
	boolean isExportChapterDatesLocs;
	
	/** The is export scene titles. */
	boolean isExportSceneTitles;
	
	/** The is export scene separator. */
	boolean isExportSceneSeparator;
	
	/** The html book multi. */
	boolean htmlBookMulti;
	
	/** The pdf page size. */
	String pdfPageSize;
	
	/** The pdf landscape. */
	boolean pdfLandscape;

	/**
	 * Instantiates a new param export.
	 *
	 * @param m the m
	 */
	ParamExport(MainFrame m) {
		mainFrame = m;
	}

	/**
	 * Load.
	 */
	void load() {
		String x = BookUtil.get(mainFrame, SbConstants.BookKey.CSV_QUOTES, "010").getStringValue();
		csvSingleQuotes = false;
		csvDoubleQuotes = true;
		csvNoQuotes = false;
		if (!"".equals(x)) {
			if ("1".equals(x.substring(0, 1))) {
				csvSingleQuotes = true;
			} else if ("1".equals(x.substring(1, 2))) {
				csvDoubleQuotes = true;
			} else if ("1".equals(x.substring(2))) {
				csvNoQuotes = true;
			}
		}
		csvComma = BookUtil.get(mainFrame, SbConstants.BookKey.CSV_COMMA, false).getBooleanValue();
		txtTab = BookUtil.get(mainFrame, SbConstants.BookKey.TXT_TAB, true).getBooleanValue();
		if (!txtTab) {
			txtSeparator = BookUtil.get(mainFrame, SbConstants.BookKey.TXT_OTHER, "").getStringValue();
		} else {
			txtSeparator = "";
		}
		htmlUseCss = BookUtil.get(mainFrame, SbConstants.BookKey.HTML_USE_CSS, false).getBooleanValue();
		if (htmlUseCss) {
			htmlCssFile = BookUtil.get(mainFrame, SbConstants.BookKey.HTML_CSS_FILE, "").getStringValue();
		} else {
			htmlCssFile = "";
		}
		htmlBookMulti = BookUtil.get(mainFrame, SbConstants.BookKey.HTML_BOOK_MULTI, false).getBooleanValue();
		isExportChapterNumbers = BookUtil.get(mainFrame, SbConstants.BookKey.EXPORT_CHAPTER_NUMBERS, false)
				.getBooleanValue();
		isExportChapterNumbersRoman = BookUtil.get(mainFrame, SbConstants.BookKey.EXPORT_ROMAN_NUMERALS, false)
				.getBooleanValue();
		isExportChapterTitles = BookUtil.get(mainFrame, SbConstants.BookKey.EXPORT_CHAPTER_TITLES, false)
				.getBooleanValue();
		isExportChapterDatesLocs = BookUtil.get(mainFrame, SbConstants.BookKey.EXPORT_CHAPTER_DATES_LOCATIONS, false)
				.getBooleanValue();
		isExportSceneTitles = BookUtil.get(mainFrame, SbConstants.BookKey.EXPORT_SCENE_TITLES, false).getBooleanValue();
		isExportSceneSeparator = BookUtil.get(mainFrame, SbConstants.BookKey.EXPORT_SCENE_SEPARATOR, false)
				.getBooleanValue();
		pdfPageSize = BookUtil.get(mainFrame, SbConstants.BookKey.PDF_PAGE_SIZE, "A4").getStringValue();
		pdfLandscape = BookUtil.get(mainFrame, SbConstants.BookKey.PDF_LANDSCAPE, false).getBooleanValue();
	}

	/**
	 * Save.
	 */
	void save() {
		String x = (csvSingleQuotes ? "1" : "0") + (csvDoubleQuotes ? "1" : "0") + (csvNoQuotes ? "1" : "0");
		BookUtil.store(mainFrame, SbConstants.BookKey.CSV_QUOTES.toString(), x);
		BookUtil.store(mainFrame, SbConstants.BookKey.CSV_COMMA.toString(), csvComma);
		BookUtil.store(mainFrame, SbConstants.BookKey.TXT_TAB.toString(), txtTab);
		BookUtil.store(mainFrame, SbConstants.BookKey.TXT_OTHER.toString(), txtSeparator);
		BookUtil.store(mainFrame, SbConstants.BookKey.HTML_USE_CSS.toString(), htmlUseCss);
		BookUtil.store(mainFrame, SbConstants.BookKey.HTML_CSS_FILE.toString(), htmlCssFile);
		BookUtil.store(mainFrame, SbConstants.BookKey.HTML_BOOK_MULTI.toString(), htmlBookMulti);
		BookUtil.store(mainFrame, SbConstants.BookKey.EXPORT_CHAPTER_NUMBERS.toString(), isExportChapterNumbers);
		BookUtil.store(mainFrame, SbConstants.BookKey.EXPORT_ROMAN_NUMERALS.toString(), isExportChapterNumbersRoman);
		BookUtil.store(mainFrame, SbConstants.BookKey.EXPORT_CHAPTER_TITLES.toString(), isExportChapterTitles);
		BookUtil.store(mainFrame, SbConstants.BookKey.EXPORT_CHAPTER_DATES_LOCATIONS.toString(),
				isExportChapterDatesLocs);
		BookUtil.store(mainFrame, SbConstants.BookKey.EXPORT_SCENE_TITLES.toString(), isExportSceneTitles);
		BookUtil.store(mainFrame, SbConstants.BookKey.EXPORT_SCENE_SEPARATOR.toString(), isExportSceneSeparator);
		BookUtil.store(mainFrame, SbConstants.BookKey.PDF_PAGE_SIZE.toString(), pdfPageSize);
		BookUtil.store(mainFrame, SbConstants.BookKey.PDF_LANDSCAPE.toString(), pdfLandscape);
	}

}
