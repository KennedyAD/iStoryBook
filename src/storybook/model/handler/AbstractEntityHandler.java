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

package storybook.model.handler;

import java.util.Vector;

import javax.swing.ListCellRenderer;

import storybook.model.hbn.dao.SbGenericDAOImpl;
import storybook.model.hbn.entity.AbstractEntity;
import storybook.ui.MainFrame;
import storybook.ui.table.SbColumn;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractEntityHandler.
 *
 * @author martin
 */
public abstract class AbstractEntityHandler {

	/** The main frame. */
	protected MainFrame mainFrame;
	
	/** The columns. */
	protected Vector<SbColumn> columns;

	/**
	 * Instantiates a new abstract entity handler.
	 *
	 * @param mainFrame the main frame
	 * @param columns the columns
	 */
	public AbstractEntityHandler(MainFrame mainFrame, Vector<SbColumn> columns) {
		this.mainFrame = mainFrame;
		this.columns = columns;
	}

	/**
	 * Creates the dao.
	 *
	 * @return the sb generic dao impl
	 */
	public SbGenericDAOImpl<?, ?> createDAO() {
		try {
			return (SbGenericDAOImpl<?, ?>) getDAOClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			System.err.println("AbstractEntityHandler.createDAO() Exception:" + e.getMessage());
		}
		return null;
	}

	/**
	 * Creates the new entity.
	 *
	 * @return the abstract entity
	 */
	public abstract AbstractEntity createNewEntity();

	/**
	 * Gets the columns.
	 *
	 * @return the columns
	 */
	public Vector<SbColumn> getColumns() {
		return columns;
	}

	/**
	 * Gets the DAO class.
	 *
	 * @param <T> the generic type
	 * @return the DAO class
	 */
	public abstract <T> Class<T> getDAOClass();

	/**
	 * Gets the entity class.
	 *
	 * @param <T> the generic type
	 * @return the entity class
	 */
	public abstract <T> Class<T> getEntityClass();

	/**
	 * Gets the list cell renderer.
	 *
	 * @return the list cell renderer
	 */
	public ListCellRenderer getListCellRenderer() {
		return null;
	}

	/**
	 * Checks for list cell renderer.
	 *
	 * @return true, if successful
	 */
	public boolean hasListCellRenderer() {
		return getListCellRenderer() != null;
	}

	/**
	 * New entity.
	 *
	 * @param entity the entity
	 * @return the abstract entity
	 */
	public AbstractEntity newEntity(AbstractEntity entity) {
		return createNewEntity();
	}
}
