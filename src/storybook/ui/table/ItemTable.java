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
import java.util.ArrayList;

import org.hibernate.Session;

import storybook.controller.BookController;
import storybook.model.BookModel;
import storybook.model.hbn.dao.ItemDAOImpl;
import storybook.model.hbn.entity.AbstractEntity;
import storybook.model.hbn.entity.Item;
import storybook.ui.MainFrame;

// TODO: Auto-generated Javadoc
/**
 * The Class ItemTable.
 *
 * @author martin
 */

public class ItemTable extends AbstractTable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -410935909301749736L;

	/**
	 * Instantiates a new item table.
	 *
	 * @param mainFrame the main frame
	 */
	public ItemTable(MainFrame mainFrame) {
		super(mainFrame);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#getEntity(java.lang.Long)
	 */
	@Override
	protected AbstractEntity getEntity(Long id) {
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		ItemDAOImpl dao = new ItemDAOImpl(session);
		Item item = dao.find(id);
		model.commit();
		return item;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#getNewEntity()
	 */
	@Override
	protected AbstractEntity getNewEntity() {
		return new Item();
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#getTableName()
	 */
	@Override
	public String getTableName() {
		return ("Item");
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#init()
	 */
	@Override
	public void init() {
		columns = SbColumnFactory.getInstance().getItemColumns();
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#modelPropertyChangeLocal(java.beans.PropertyChangeEvent)
	 */
	@Override
	protected void modelPropertyChangeLocal(PropertyChangeEvent evt) {
		try {
			String propName = evt.getPropertyName();
			if (BookController.ItemProps.INIT.check(propName)) {
				initTableModel(evt);
			} else if (BookController.ItemProps.UPDATE.check(propName)) {
				updateEntity(evt);
			} else if (BookController.ItemProps.NEW.check(propName)) {
				newEntity(evt);
			} else if (BookController.ItemProps.DELETE.check(propName)) {
				deleteEntity(evt);
			}
		} catch (Exception e) {
		}
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#sendDeleteEntities(int[])
	 */
	@Override
	protected synchronized void sendDeleteEntities(int[] rows) {
		ArrayList<Long> ids = new ArrayList<Long>();
		for (int row : rows) {
			Item item = (Item) getEntityFromRow(row);
			ids.add(item.getId());
		}
		ctrl.deleteMultiItems(ids);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#sendDeleteEntity(int)
	 */
	@Override
	protected synchronized void sendDeleteEntity(int row) {
		Item item = (Item) getEntityFromRow(row);
		ctrl.deleteItem(item);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#sendSetEntityToEdit(int)
	 */
	@Override
	protected void sendSetEntityToEdit(int row) {
		if (row == -1) {
			return;
		}
		Item item = (Item) getEntityFromRow(row);
		// ctrl.setItemToEdit(item);
		// mainFrame.showView(ViewName.EDITOR);
		mainFrame.showEditorAsDialog(item);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#sendSetNewEntityToEdit(storybook.model.hbn.entity.AbstractEntity)
	 */
	@Override
	protected void sendSetNewEntityToEdit(AbstractEntity entity) {
		// ctrl.setItemToEdit((Item) entity);
		// mainFrame.showView(ViewName.EDITOR);
		mainFrame.showEditorAsDialog(entity);
	}
}
