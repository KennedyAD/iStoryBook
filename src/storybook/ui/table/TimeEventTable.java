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

package storybook.ui.table;

import java.beans.PropertyChangeEvent;
import java.util.List;

import org.hibernate.Session;

import storybook.SbApp;
import storybook.SbConstants.ClientPropertyName;
import storybook.controller.BookController;
import storybook.model.BookModel;
import storybook.model.hbn.dao.TimeEventDAOImpl;
import storybook.model.hbn.entity.AbstractEntity;
import storybook.model.hbn.entity.TimeEvent;
import storybook.ui.MainFrame;

// TODO: Auto-generated Javadoc
/**
 * The Class TimeEventTable.
 *
 * @author martin
 */


public class TimeEventTable extends AbstractTable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8907777245846212074L;

	/**
	 * Instantiates a new time event table.
	 *
	 * @param mainFrame the main frame
	 */
	public TimeEventTable(MainFrame mainFrame) {
		super(mainFrame);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#getAllEntities()
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	protected List<AbstractEntity> getAllEntities() {
		SbApp.trace("getAllEntities()");
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		TimeEventDAOImpl dao = new TimeEventDAOImpl(session);
		List<?> ret;
		ret = dao.findAll();
		model.commit();
		return (List<AbstractEntity>) ret;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#getEntity(java.lang.Long)
	 */
	@Override
	protected AbstractEntity getEntity(Long id) {
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		TimeEventDAOImpl dao = new TimeEventDAOImpl(session);
		TimeEvent scene = dao.find(id);
		model.commit();
		return scene;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#getNewEntity()
	 */
	@Override
	protected AbstractEntity getNewEntity() {
		return new TimeEvent();
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#getTableName()
	 */
	@Override
	public String getTableName() {
		return ("TimeEvent");
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#init()
	 */
	@Override
	public void init() {
		columns = SbColumnFactory.getInstance().getTimeEventColumns();
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#initTableModel(java.beans.PropertyChangeEvent)
	 */
	@Override
	protected void initTableModel(PropertyChangeEvent evt) {
		SbApp.trace("AbstractTable.initTableModel(evt)");
		table.putClientProperty(ClientPropertyName.MAIN_FRAME.toString(), mainFrame);
		// clear table
		for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
			tableModel.removeRow(i);
		}
		// fill in data
		try {
			List<AbstractEntity> entities = getAllEntities();

			for (AbstractEntity entity : entities) {
				List<Object> cols = getRow(entity);
				tableModel.addRow(cols.toArray());
			}
		} catch (ClassCastException e) {
		}
		table.packAll();
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#modelPropertyChangeLocal(java.beans.PropertyChangeEvent)
	 */
	@Override
	protected void modelPropertyChangeLocal(PropertyChangeEvent evt) {
		SbApp.trace("SceneTable.modelPropertyChangeLocal(" + evt.getPropertyName() + ")");
		try {
			String propName = evt.getPropertyName();
			// Object newValue = evt.getNewValue();
			// Object oldValue = evt.getOldValue();
			if (BookController.TimeEventProps.INIT.check(propName)) {
				initTableModel(evt);
				return;
			}
			if (BookController.TimeEventProps.UPDATE.check(propName)) {
				updateEntity(evt);
				return;
			}
			if (BookController.TimeEventProps.NEW.check(propName)) {
				newEntity(evt);
				return;
			}
			if (BookController.TimeEventProps.DELETE.check(propName)) {
				deleteEntity(evt);
				return;
			}
		} catch (Exception e) {
		}
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#sendDeleteEntities(int[])
	 */
	@Override
	protected synchronized void sendDeleteEntities(int[] rows) {
		for (int row : rows) {
			TimeEvent scene = (TimeEvent) getEntityFromRow(row);
			ctrl.deleteTimeEvent(scene);
		}
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#sendDeleteEntity(int)
	 */
	@Override
	protected synchronized void sendDeleteEntity(int row) {
		TimeEvent scene = (TimeEvent) getEntityFromRow(row);
		ctrl.deleteTimeEvent(scene);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#sendSetEntityToEdit(int)
	 */
	@Override
	protected void sendSetEntityToEdit(int row) {
		SbApp.trace("TimeEventTable.sendSetEntityToEdit(" + row + ")");
		if (row == -1) {
			return;
		}
		TimeEvent event = (TimeEvent) getEntityFromRow(row);
		// ctrl.setTimeEventToEdit(event);
		// mainFrame.showView(ViewName.EDITOR);
		mainFrame.showEditorAsDialog(event);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#sendSetNewEntityToEdit(storybook.model.hbn.entity.AbstractEntity)
	 */
	@Override
	protected void sendSetNewEntityToEdit(AbstractEntity entity) {
		// ctrl.setTimeEventToEdit((TimeEvent) entity);
		// mainFrame.showView(ViewName.EDITOR);
		mainFrame.showEditorAsDialog(entity);
	}
}
