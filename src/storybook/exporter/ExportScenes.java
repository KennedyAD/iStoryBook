/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package storybook.exporter;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import storybook.model.BookModel;
import storybook.model.EntityUtil;
import storybook.model.hbn.dao.SceneDAOImpl;
import storybook.model.hbn.entity.Scene;
import storybook.toolkit.I18N;

// TODO: Auto-generated Javadoc
/**
 * The Class ExportScenes.
 *
 * @author favdb
 */
public class ExportScenes {
	
	/** The parent. */
	private final Export parent;
	
	/** The pdf. */
	private ExportPDF pdf;
	
	/** The html. */
	private ExportHtml html;
	
	/** The csv. */
	private ExportCsv csv;
	
	/** The txt. */
	private ExportTxt txt;
	
	/** The odf. */
	private ExportOdf odf;
	
	/** The headers. */
	private List<ExportHeader> headers;

	/**
	 * Instantiates a new export scenes.
	 *
	 * @param m the m
	 */
	ExportScenes(Export m) {
		parent = m;
		headers = new ArrayList<>();
		headers.add(new ExportHeader(I18N.getMsg("msg.common.id"), 5));
		headers.add(new ExportHeader(I18N.getMsg("msg.dlg.scene.scene.no"), 15));
		headers.add(new ExportHeader(I18N.getMsg("msg.common.chapter"), 5));
		headers.add(new ExportHeader(I18N.getMsg("msg.common.title"), 35));
		headers.add(new ExportHeader(I18N.getMsg("msg.status"), 15));
		headers.add(new ExportHeader(I18N.getMsg("msg.common.strand"), 10));
		headers.add(new ExportHeader(I18N.getMsg("msg.common.date"), 25));
	}

	/**
	 * Debut.
	 *
	 * @param obj the obj
	 * @return the string
	 */
	public String debut(Scene obj) {
		String rep = "Chapters";
		switch (parent.format) {
		case "pdf":
			pdf = new ExportPDF(parent, rep, parent.file.getAbsolutePath(), headers, parent.author);
			pdf.open();
			break;
		case "html":
			html = new ExportHtml(parent, rep, parent.file.getAbsolutePath(), headers, parent.author);
			html.open(true);
			break;
		case "csv":
			csv = new ExportCsv(parent, rep, parent.file.getAbsolutePath(), headers, parent.author);
			csv.open();
			break;
		case "txt":
			txt = new ExportTxt(parent, rep, parent.file.getAbsolutePath(), headers, parent.author);
			txt.open();
			break;
		case "odf":
			odf = new ExportOdf(parent, rep, parent.file.getAbsolutePath(), headers, parent.author);
			odf.open();
			break;
		}
		return "";
	}

	/**
	 * End.
	 */
	public void end() {
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
		case "odf":
			odf.close();
			break;
		}
	}

	/**
	 * Gets the.
	 *
	 * @param obj the obj
	 * @return the string
	 */
	public String get(Scene obj) {
		if (obj != null) {
			return EntityUtil.getInfo(parent.mainFrame, obj);
		}
		debut(obj);
		BookModel model = parent.mainFrame.getBookModel();
		Session session = model.beginTransaction();
		SceneDAOImpl dao = new SceneDAOImpl(session);
		List<Scene> scenes = dao.findAll();
		for (Scene scene : scenes) {
			ligne(scene, true, true);
		}
		model.commit();
		end();
		return "";
	}

	/**
	 * Ligne.
	 *
	 * @param scene the scene
	 * @param verbose the verbose
	 * @param list the list
	 * @return the string
	 */
	public String ligne(Scene scene, boolean verbose, boolean list) {
		String chapter = "";
		if (scene == null) {
			return "";
		}
		if (scene.hasChapter()) {
			chapter = scene.getChapter().getChapternoStr();
		}
		Integer x = scene.getSceneno();
		String xs;
		if (x != null) {
			xs = Integer.toString(x);
		} else {
			xs = "";
		}
		String body[] = { Long.toString(scene.getId()), xs, chapter, scene.getTitle(),
				scene.getSceneState().getName(), scene.getStrand().getAbbr(), scene.getDateStrShort() };
		switch (parent.format) {
		case "pdf":
			pdf.writeRow(body);
			break;
		case "html":
			html.writeRow(body);
			break;
		case "csv":
			csv.writeRow(body);
			break;
		case "txt":
			txt.writeRow(body);
			break;
		case "odf":
			odf.writeRow(body);
			break;
		}
		return "";
	}

}
