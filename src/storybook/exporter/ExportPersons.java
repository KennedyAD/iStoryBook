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
import storybook.model.hbn.dao.PersonDAOImpl;
import storybook.model.hbn.entity.Person;
import storybook.toolkit.I18N;

// TODO: Auto-generated Javadoc
/**
 * The Class ExportPersons.
 *
 * @author favdb
 */
public class ExportPersons {
	
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
	 * Instantiates a new export persons.
	 *
	 * @param m the m
	 */
	ExportPersons(Export m) {
		parent = m;
		headers = new ArrayList<>();
		headers.add(new ExportHeader(I18N.getMsg("msg.common.id"), 5));
		headers.add(new ExportHeader(I18N.getMsg("msg.dlg.person.abbr"), 5));
		headers.add(new ExportHeader(I18N.getMsg("msg.dlg.person.firstname"), 25));
		headers.add(new ExportHeader(I18N.getMsg("msg.dlg.person.lastname"), 25));
		headers.add(new ExportHeader(I18N.getMsg("msg.dlg.person.color"), 10));
		headers.add(new ExportHeader(I18N.getMsg("msg.dlg.person.occupation"), 30));
	}

	/**
	 * Debut.
	 *
	 * @param obj the obj
	 * @return the string
	 */
	public String debut(Person obj) {
		String rep = "Persons";
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
	public String get(Person obj) {
		if (obj != null) {
			return EntityUtil.getInfo(parent.mainFrame, obj);
		}
		String str = debut(obj);
		BookModel model = parent.mainFrame.getBookModel();
		Session session = model.beginTransaction();
		PersonDAOImpl dao = new PersonDAOImpl(session);
		List<Person> persons = dao.findAll();
		for (Person person : persons) {
			str += ligne(person, true, true);
		}
		model.commit();
		end();
		return str;
	}

	/**
	 * Ligne.
	 *
	 * @param obj the obj
	 * @param verbose the verbose
	 * @param list the list
	 * @return the string
	 */
	public String ligne(Person obj, boolean verbose, boolean list) {
		String body[] = { Long.toString(obj.getId()), obj.getAbbreviation(), obj.getFirstname(), obj.getLastname(),
				obj.getHTMLColor(), obj.getOccupation(), };
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
