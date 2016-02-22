/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package storybook.exporter;

import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;

import storybook.SbApp;
import storybook.model.BookModel;
import storybook.model.hbn.dao.ChapterDAOImpl;
import storybook.model.hbn.dao.PartDAOImpl;
import storybook.model.hbn.dao.SceneDAOImpl;
import storybook.model.hbn.entity.Chapter;
import storybook.model.hbn.entity.Part;
import storybook.model.hbn.entity.Scene;
import storybook.toolkit.BookUtil;
import storybook.toolkit.DateUtil;
import storybook.toolkit.I18N;
import storybook.toolkit.LangUtil;
import storybook.toolkit.TextTransfer;
import storybook.ui.MainFrame;

// TODO: Auto-generated Javadoc
/**
 * The Class BookExporter.
 *
 * @author favdb
 */
public class BookExporter extends AbstractExporter {

	/** The is use html scenes. */
	boolean isUseHtmlScenes;
	
	/** The is export chapter numbers. */
	boolean isExportChapterNumbers;
	
	/** The is export roman numerals. */
	boolean isExportRomanNumerals;
	
	/** The is export chapter titles. */
	boolean isExportChapterTitles;
	
	/** The is export chapter dat loc. */
	boolean isExportChapterDatLoc;
	
	/** The is export scene title. */
	boolean isExportSceneTitle;
	
	/** The is export scene separator. */
	boolean isExportSceneSeparator;
	
	/** The is export part titles. */
	boolean isExportPartTitles;
	
	/** The t html. */
	boolean tHtml = true;
	
	/** The is book html multi. */
	boolean isBookHtmlMulti;

	/** The export for open office. */
	private boolean exportForOpenOffice;
	
	/** The export only current part. */
	private boolean exportOnlyCurrentPart;
	
	/** The export table of contents link. */
	private boolean exportTableOfContentsLink;
	
	/** The strand ids to export. */
	private HashSet<Long> strandIdsToExport;
	
	/** The e h3. */
	private final String bH1 = "<h1>", eH1 = "</h1>\n\n", bH2 = "<h2>", eH2 = "</h2>\n", bH3 = "<h3>", eH3 = "</h3>\n";

	/**
	 * Instantiates a new book exporter.
	 *
	 * @param m the m
	 */
	public BookExporter(MainFrame m) {
		super(m);
		setFileName(m.getDbFile().getName());
		getParam();
		SbApp.trace("BookExporter(" + m.getName() + ")");
	}

	/**
	 * Export to clipboard.
	 *
	 * @return true, if successful
	 */
	public boolean exportToClipboard() {
		SbApp.trace("BookExporter.exportToClipboard()");
		try {
			StringBuffer str = getContent();
			TextTransfer tf = new TextTransfer();
			tf.setClipboardContents(str.toString());
			JOptionPane.showMessageDialog(this.mainFrame, I18N.getMsg("msg.book.copy.confirmation"),
					I18N.getMsg("msg.copied.title"), 1);
		} catch (HeadlessException exc) {
			return false;
		}
		return true;
	}

	/**
	 * Gets the chapter as html.
	 *
	 * @param chapter the chapter
	 * @return the chapter as html
	 */
	public String getChapterAsHtml(Chapter chapter) { // strict chapter without
														// dates and locations
														// with scenes
		String buf = "<a name='" + chapter.getChapternoStr() + "'>";
		buf += bH2;
		if (isExportChapterNumbers) {
			if (isExportRomanNumerals) {
				buf += LangUtil.intToRoman(chapter.getChapterno());
			} else {
				buf += Integer.toString(chapter.getChapterno());
			}
			if (isExportChapterTitles) {
				buf += ": " + chapter.getTitle();
			}
		} else if (isExportChapterTitles) {
			buf += chapter.getTitle();
		}
		buf += eH2;
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		SceneDAOImpl SceneDAO = new SceneDAOImpl(session);
		List<Scene> scenes = SceneDAO.findByChapter(chapter);
		for (Scene scene : scenes) {
			buf += getSceneAsHtml(scene);
		}
		model.commit();
		return buf;
	}

	/**
	 * Gets the chapter as html.
	 *
	 * @param chapter the chapter
	 * @param ChapterDAO the chapter dao
	 * @return the chapter as html
	 */
	public String getChapterAsHtml(Chapter chapter, ChapterDAOImpl ChapterDAO) {
		String buf = "<a name='" + chapter.getChapternoStr() + "'>";
		buf += bH2;
		if (isExportChapterNumbers) {
			if (isExportRomanNumerals) {
				buf += LangUtil.intToRoman(chapter.getChapterno());
			} else {
				buf += Integer.toString(chapter.getChapterno());
			}
			if (isExportChapterTitles) {
				buf += ": " + chapter.getTitle();
			}
		} else if (isExportChapterTitles) {
			buf += chapter.getTitle();
		}
		buf += eH2 + "</a>";
		if (isExportChapterDatLoc) {
			buf += bH3;
			buf += DateUtil.getNiceDates(ChapterDAO.findDates(chapter));
			if (!((List) ChapterDAO.findLocations(chapter)).isEmpty()) {
				buf += ": " + StringUtils.join(ChapterDAO.findLocations(chapter), ", ");
			}
			buf += eH3;
		}
		return buf;
	}

	/**
	 * Gets the chapter as txt.
	 *
	 * @param chapter the chapter
	 * @param ChapterDAO the chapter dao
	 * @return the chapter as txt
	 */
	public String getChapterAsTxt(Chapter chapter, ChapterDAOImpl ChapterDAO) {
		String buf = "";
		buf += chapter.getChapternoStr() + "\n";
		if (isExportChapterNumbers) {
			if (isExportRomanNumerals) {
				buf += LangUtil.intToRoman(chapter.getChapterno());
			} else {
				buf += chapter.getChapternoStr();
			}
		}
		if (isExportChapterTitles) {
			buf += ": " + chapter.getTitle();
		}
		buf += "\n";
		if (isExportChapterDatLoc) {
			buf += DateUtil.getNiceDates(ChapterDAO.findDates(chapter));
			if (!((List) ChapterDAO.findLocations(chapter)).isEmpty()) {
				buf += ": " + StringUtils.join(ChapterDAO.findLocations(chapter), ", ");
			}
		}
		return buf;
	}

	/** (non-Javadoc)
	 * @see storybook.exporter.AbstractExporter#getContent()
	 */
	@Override
	public StringBuffer getContent() {
		// warning : getContent ne retourne que le contenu du body en mode HTML
		SbApp.trace("BookExporter.getContent()");
		Part Part1 = mainFrame.getCurrentPart();
		StringBuffer buf = new StringBuffer();
		getParam();
		try {
			BookModel model = mainFrame.getBookModel();
			Session session = model.beginTransaction();
			PartDAOImpl PartDAO = new PartDAOImpl(session);
			ChapterDAOImpl ChapterDAO = new ChapterDAOImpl(session);
			SceneDAOImpl SceneDAO = new SceneDAOImpl(session);
			List<Part> listParts;
			if (exportOnlyCurrentPart) {
				listParts = new ArrayList<>();
				listParts.add(Part1);
			} else {
				listParts = PartDAO.findAll();
			}
			if (tHtml) {// export en HTML
				for (Part part : listParts) {
					buf.append(getPartAsHtml(part));
					List<Chapter> chapters = ChapterDAO.findAll(part);
					for (Chapter chapter : chapters) {
						buf.append(getChapterAsHtml(chapter, ChapterDAO));
						List<Scene> scenes = SceneDAO.findByChapter(chapter);
						for (Scene scene : scenes) {
							buf.append(getSceneAsHtml(scene));
						}
					}
				} // fin export HTML
			} else {// export en TXT
				for (Part part : listParts) {
					buf.append(getPartAsTxt(part));
					List<Chapter> chapters = ChapterDAO.findAll(part);
					for (Chapter chapter : chapters) {
						buf.append(getChapterAsTxt(chapter, ChapterDAO));
						List<Scene> scenes = SceneDAO.findByChapter(chapter);
						for (Scene scene : scenes) {
							buf.append(getSceneAsTxt(scene));
						}
					}
				}
			} // fin export TXT
			model.commit();
		} catch (Exception exc) {
			SbApp.error("BookExport.getContent()", exc);
		}
		SbApp.trace("getContent returns bufsize=" + buf.length());
		return buf;
	}

	/**
	 * Gets the param.
	 *
	 * @return the param
	 */
	private void getParam() {
		isUseHtmlScenes = BookUtil.isUseHtmlScenes(mainFrame);
		isExportChapterNumbers = BookUtil.isExportChapterNumbers(mainFrame);
		isExportRomanNumerals = BookUtil.isExportRomanNumerals(mainFrame);
		isExportChapterTitles = BookUtil.isExportChapterTitles(mainFrame);
		isExportChapterDatLoc = BookUtil.isExportChapterDatesLocations(mainFrame);
		isExportSceneTitle = BookUtil.isExportSceneTitle(mainFrame);
		isExportSceneSeparator = BookUtil.isExportSceneSeparator(mainFrame);
		isExportPartTitles = BookUtil.isExportPartTitles(mainFrame);

		tHtml = isUseHtmlScenes || !exportForOpenOffice != !true; // buf.append(getHtmlHead());
		isBookHtmlMulti = BookUtil.isExportBookHtmlMulti(mainFrame);
	}

	/**
	 * Gets the part as html.
	 *
	 * @param part the part
	 * @return the part as html
	 */
	public String getPartAsHtml(Part part) {
		String buf = "";
		if (isExportPartTitles) {
			buf = bH1 + I18N.getMsg("msg.common.part") + ": " + part.getNumber() + eH1;
		}
		return buf;
	}

	/**
	 * Gets the part as txt.
	 *
	 * @param part the part
	 * @return the part as txt
	 */
	public String getPartAsTxt(Part part) {
		String buf = "";
		if (isExportPartTitles) {
			buf += I18N.getMsg("msg.common.part") + ": " + part.getNumber();
		}
		return buf;
	}

	/**
	 * Gets the scene as html.
	 *
	 * @param scene the scene
	 * @return the scene as html
	 */
	public String getSceneAsHtml(Scene scene) {
		String buf = "";
		if (strandIdsToExport != null) {
			long l = scene.getStrand().getId();
			if (!strandIdsToExport.contains(l)) {
				return "";
			}
		}
		if (!scene.getInformative()) {
			if (isExportSceneTitle) {
				buf += scene.getTitle();
			}
			buf += scene.getText() + "\n";
			if (isExportSceneSeparator) {
				buf += "<p style=\"text-align:center\">.oOo.</p>";
			}
		}
		if (exportTableOfContentsLink) {
			buf += "<p style='font-size:8px;text-align:left;'><a href='#toc'>" + I18N.getMsg("msg.table.of.contents")
					+ "</a></p>";
		}
		return buf;
	}

	/**
	 * Gets the scene as txt.
	 *
	 * @param scene the scene
	 * @return the scene as txt
	 */
	public String getSceneAsTxt(Scene scene) {
		String buf = "";
		boolean bx = true;
		if (strandIdsToExport != null) {
			long l = scene.getStrand().getId();
			if (!strandIdsToExport.contains(l)) {
				return "";
			}
		}
		if (bx && !scene.getInformative()) {
			if (isExportSceneTitle) {
				buf += scene.getTitle();
			}
			String str = scene.getText();
			buf += str + "\n";
		}
		return buf;
	}

	/**
	 * Gets the strand ids to export.
	 *
	 * @return the strand ids to export
	 */
	public HashSet<Long> getStrandIdsToExport() {
		return strandIdsToExport;
	}

	/**
	 * Checks if is export for open office.
	 *
	 * @return true, if is export for open office
	 */
	public boolean isExportForOpenOffice() {
		return exportForOpenOffice;
	}

	/**
	 * Checks if is export only current part.
	 *
	 * @return true, if is export only current part
	 */
	public boolean isExportOnlyCurrentPart() {
		return exportOnlyCurrentPart;
	}

	/**
	 * Checks if is export table of contents link.
	 *
	 * @return true, if is export table of contents link
	 */
	public boolean isExportTableOfContentsLink() {
		return exportTableOfContentsLink;
	}

	/**
	 * Sets the export for open office.
	 *
	 * @param b the new export for open office
	 */
	public void setExportForOpenOffice(boolean b) {
		exportForOpenOffice = b;
	}

	/**
	 * Sets the export only current part.
	 *
	 * @param b the new export only current part
	 */
	public void setExportOnlyCurrentPart(boolean b) {
		exportOnlyCurrentPart = b;
	}

	/**
	 * Sets the export table of contents link.
	 *
	 * @param b the new export table of contents link
	 */
	public void setExportTableOfContentsLink(boolean b) {
		exportTableOfContentsLink = b;
	}

	/**
	 * Sets the strand ids to export.
	 *
	 * @param p the new strand ids to export
	 */
	public void setStrandIdsToExport(HashSet<Long> p) {
		strandIdsToExport = p;
	}
}
