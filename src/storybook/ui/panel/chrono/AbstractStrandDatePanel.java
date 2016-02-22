/*
Storybook: Open Source software for novelists and authors.
Copyright (C) 2008 - 2012 Martin Mustun

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package storybook.ui.panel.chrono;

import java.beans.PropertyChangeEvent;
import java.util.Date;

import org.hibernate.Session;

import storybook.controller.BookController;
import storybook.model.BookModel;
import storybook.model.hbn.dao.SceneDAOImpl;
import storybook.model.hbn.entity.Scene;
import storybook.model.hbn.entity.Strand;
import storybook.ui.MainFrame;
import storybook.ui.panel.AbstractPanel;


// TODO: Auto-generated Javadoc
/**
 * The Class AbstractStrandDatePanel.
 */
public abstract class AbstractStrandDatePanel extends AbstractPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 168205137858187946L;
	
	/** The strand. */
	protected Strand strand;
	
	/** The date. */
	protected Date date;

	/**
	 * Instantiates a new abstract strand date panel.
	 *
	 * @param mainFrame the main frame
	 * @param strand the strand
	 * @param date the date
	 */
	public AbstractStrandDatePanel(MainFrame mainFrame, Strand strand, Date date) {
		super(mainFrame);
		this.strand = strand;
		this.date = date;
		init();
		initUi();
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Gets the scene.
	 *
	 * @param id the id
	 * @return the scene
	 */
	public Scene getScene(Long id) {
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		SceneDAOImpl sceneDao = new SceneDAOImpl(session);
		Scene scene = sceneDao.find(id);
		model.commit();
		return (scene);
	}

	/**
	 * Gets the strand.
	 *
	 * @return the strand
	 */
	public Strand getStrand() {
		return strand;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#modelPropertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void modelPropertyChange(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();
		if (BookController.SceneProps.NEW.check(propName) || BookController.SceneProps.DELETE.check(propName)) {
			refresh();
			if (getParent() != null) {
				getParent().validate();
			} else {
				validate();
			}
		}
	}
}
