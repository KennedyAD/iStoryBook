/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package storybook.ui.panel.attributes;

import java.awt.Graphics;
import java.awt.event.MouseWheelListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import org.hibernate.Session;

import net.infonode.docking.View;
import net.miginfocom.swing.MigLayout;
import storybook.SbConstants;
import storybook.controller.BookController;
import storybook.model.BookModel;
import storybook.model.hbn.dao.AttributeDAOImpl;
import storybook.model.hbn.dao.PersonDAOImpl;
import storybook.model.hbn.entity.Attribute;
import storybook.model.hbn.entity.Internal;
import storybook.model.hbn.entity.Person;
import storybook.toolkit.BookUtil;
import storybook.toolkit.I18N;
import storybook.toolkit.swing.SwingUtil;
import storybook.ui.MainFrame;
import storybook.ui.panel.AbstractScrollPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class AttributesViewPanel.
 *
 * @author favdb
 */
public class AttributesViewPanel extends AbstractScrollPanel implements Printable, MouseWheelListener {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1186732686192834029L;
	
	/** The attribute pane. */
	private JTextPane attributePane;

	/**
	 * Instantiates a new attributes view panel.
	 *
	 * @param mainFrame the main frame
	 */
	public AttributesViewPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	// liste des attributs, par attribut liste des différentes valeurs, par
	// valeur liste des personnages
	// exemple :
	// <titre>Liste des attributs<titre>
	// <sous-titre>Attribut 1>
	// - valeur 1 : lsite des personnages
	// - valeur 2 : liste des personnages
	// etc
	// pourrait se faire sous forme HTML?
	/**
	 * Gets the attributes.
	 *
	 * @return the attributes
	 */
	// pas de bouton nécessaire
	private String getAttributes() {
		String html = "<html><body><h1>" + I18N.getMsg("msg.attribute.list") + "</h1>\n";
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		AttributeDAOImpl dao = new AttributeDAOImpl(session);
		List<String> attributes = dao.findKeys();

		for (String attribute : attributes) {
			html += "<p><b>" + attribute + " : </b><br>\n";
			html += getValues(attribute) + "</p>";
		}
		html += "</body><html>";
		return (html);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractScrollPanel#getMaxZoomValue()
	 */
	@Override
	protected int getMaxZoomValue() {
		return SbConstants.MAX_CHRONO_ZOOM;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractScrollPanel#getMinZoomValue()
	 */
	@Override
	protected int getMinZoomValue() {
		return SbConstants.MIN_CHRONO_ZOOM;
	}

	/**
	 * Gets the values.
	 *
	 * @param attribute the attribute
	 * @return the values
	 */
	private String getValues(String attribute) {
		String html = "";
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		PersonDAOImpl dao = new PersonDAOImpl(session);
		List<Person> persons = dao.findAll();
		List<String> values = new ArrayList<>();
		// constitution de la liste des valeurs
		for (Person person : persons) {
			if (!person.getAttributes().isEmpty()) {
				for (Attribute attr : person.getAttributes()) {
					if (attribute.equals(attr.getKey())) {
						if (!values.contains(attr.getValue()))
							values.add(attr.getValue());
					}
				}
			}
		}
		// finalisation de la liste
		for (String v : values) {
			int i = 0;
			html += "- " + v + " : ";
			for (Person person : persons) {
				for (Attribute attr : person.getAttributes()) {
					if (!person.getAttributes().isEmpty()) {
						if (attribute.equals(attr.getKey())) {
							if (v.equals(attr.getValue())) {
								if (i > 0)
									html += ", ";
								html += person.getAbbr();
								i++;
							}
						}
					}
				}
			}
			html += "<br>\n";
		}
		model.commit();
		return (html);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractScrollPanel#getZoomValue()
	 */
	@Override
	protected int getZoomValue() {
		Internal internal = BookUtil.get(mainFrame, SbConstants.BookKey.CHRONO_ZOOM, SbConstants.DEFAULT_CHRONO_ZOOM);
		return internal.getIntegerValue();
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#init()
	 */
	@Override
	public void init() {
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#initUi()
	 */
	@Override
	public void initUi() {
		setLayout(new MigLayout("flowy, ins 0"));
		MigLayout layout = new MigLayout("flowy", "[grow,left]", "");
		panel = new JPanel(layout);
		attributePane = new JTextPane();
		attributePane.setEditable(false);
		attributePane.setOpaque(true);
		attributePane.setContentType("text/html");
		panel.add(attributePane);
		panel.setBackground(SwingUtil.getBackgroundColor());
		scroller = new JScrollPane(panel);
		SwingUtil.setUnitIncrement(scroller);
		SwingUtil.setMaxPreferredSize(scroller);
		add(scroller, "grow");

		refresh();
		// revalidate();
		// repaint();
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#modelPropertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void modelPropertyChange(PropertyChangeEvent evt) {
		Object newValue = evt.getNewValue();
		String propName = evt.getPropertyName();
		if (BookController.CommonProps.REFRESH.check(propName)) {
			View newView = (View) newValue;
			View view = (View) getParent().getParent();
			if (view == newView) {
				refresh();
			}
			return;
		}
		if (BookController.CommonProps.SHOW_INFO.check(propName)) {
			refresh();
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.print.Printable#print(java.awt.Graphics, java.awt.print.PageFormat, int)
	 */
	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		// TODO print
		return Printable.NO_SUCH_PAGE;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#refresh()
	 */
	@Override
	public void refresh() {
		attributePane.setText(getAttributes());
		attributePane.setCaretPosition(0);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractScrollPanel#setZoomValue(int)
	 */
	@Override
	protected void setZoomValue(int val) {
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
}
