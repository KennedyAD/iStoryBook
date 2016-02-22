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

import storybook.SbConstants.ViewName;
import storybook.controller.BookController;
import storybook.model.BookModel;
import storybook.model.hbn.dao.CategoryDAOImpl;
import storybook.model.hbn.entity.AbstractEntity;
import storybook.model.hbn.entity.Category;
import storybook.ui.MainFrame;
import storybook.ui.SbView;

// TODO: Auto-generated Javadoc
/**
 * The Class CategoryTable.
 *
 * @author martin
 */

public class CategoryTable extends AbstractTable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1676022203228592868L;

	/**
	 * Instantiates a new category table.
	 *
	 * @param mainFrame the main frame
	 */
	public CategoryTable(MainFrame mainFrame) {
		super(mainFrame);
		hasOrder = true;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#getEntity(java.lang.Long)
	 */
	@Override
	protected AbstractEntity getEntity(Long id) {
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		CategoryDAOImpl dao = new CategoryDAOImpl(session);
		Category category = dao.find(id);
		model.commit();
		return category;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#getNewEntity()
	 */
	@Override
	protected AbstractEntity getNewEntity() {
		return new Category();
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#getTableName()
	 */
	@Override
	public String getTableName() {
		return ("Category");
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#init()
	 */
	@Override
	public void init() {
		columns = SbColumnFactory.getInstance().getCategoryColumns();
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#modelPropertyChangeLocal(java.beans.PropertyChangeEvent)
	 */
	@Override
	protected void modelPropertyChangeLocal(PropertyChangeEvent evt) {
		try {
			String propName = evt.getPropertyName();
			if (BookController.CategoryProps.INIT.check(propName)) {
				initTableModel(evt);
			} else if (BookController.CategoryProps.UPDATE.check(propName)) {
				updateEntity(evt);
			} else if (BookController.CategoryProps.NEW.check(propName)) {
				newEntity(evt);
			} else if (BookController.CategoryProps.DELETE.check(propName)) {
				deleteEntity(evt);
			} else if (BookController.CategoryProps.ORDER_UP.check(propName)) {
				orderUpEntity(evt);
			} else if (BookController.CategoryProps.ORDER_DOWN.check(propName)) {
				orderDownEntity(evt);
			}
		} catch (Exception e) {
		}
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#orderDownEntity(java.beans.PropertyChangeEvent)
	 */
	@Override
	protected void orderDownEntity(PropertyChangeEvent evt) {
		AbstractEntity entity = (AbstractEntity) evt.getNewValue();
		Category category = (Category) entity;

		BookModel model = mainFrame.getBookModel();

		Session session = model.beginTransaction();
		CategoryDAOImpl dao = new CategoryDAOImpl(session);
		dao.orderCategories();
		model.commit();

		session = model.beginTransaction();
		dao = new CategoryDAOImpl(session);
		dao.orderDownCategory(category);
		model.commit();

		SbView view = mainFrame.getView(ViewName.CATEGORIES);
		mainFrame.getBookController().refresh(view);

		sortByColumn(2);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#orderUpEntity(java.beans.PropertyChangeEvent)
	 */
	@Override
	protected void orderUpEntity(PropertyChangeEvent evt) {
		AbstractEntity entity = (AbstractEntity) evt.getNewValue();
		Category category = (Category) entity;

		BookModel model = mainFrame.getBookModel();

		Session session = model.beginTransaction();
		CategoryDAOImpl dao = new CategoryDAOImpl(session);
		dao.orderCategories();
		model.commit();

		session = model.beginTransaction();
		dao = new CategoryDAOImpl(session);
		dao.orderUpCatgory(category);
		model.commit();

		SbView view = mainFrame.getView(ViewName.CATEGORIES);
		mainFrame.getBookController().refresh(view);

		sortByColumn(2);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#sendDeleteEntities(int[])
	 */
	@Override
	protected synchronized void sendDeleteEntities(int[] rows) {
		ArrayList<Long> ids = new ArrayList<Long>();
		for (int row : rows) {
			Category category = (Category) getEntityFromRow(row);
			ids.add(category.getId());
		}
		ctrl.deleteMultiCategories(ids);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#sendDeleteEntity(int)
	 */
	@Override
	protected synchronized void sendDeleteEntity(int row) {
		Category category = (Category) getEntityFromRow(row);
		ctrl.deleteCategory(category);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#sendOrderDownEntity(int)
	 */
	@Override
	protected void sendOrderDownEntity(int row) {
		if (row == -1) {
			return;
		}
		Category category = (Category) getEntityFromRow(row);
		ctrl.orderDownCategory(category);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#sendOrderUpEntity(int)
	 */
	@Override
	protected void sendOrderUpEntity(int row) {
		if (row == -1) {
			return;
		}
		Category category = (Category) getEntityFromRow(row);
		ctrl.orderUpCategory(category);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#sendSetEntityToEdit(int)
	 */
	@Override
	protected void sendSetEntityToEdit(int row) {
		if (row == -1) {
			return;
		}
		Category category = (Category) getEntityFromRow(row);
		// ctrl.setCategoryToEdit(category);
		// mainFrame.showView(ViewName.EDITOR);
		mainFrame.showEditorAsDialog(category);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#sendSetNewEntityToEdit(storybook.model.hbn.entity.AbstractEntity)
	 */
	@Override
	protected void sendSetNewEntityToEdit(AbstractEntity entity) {
		// ctrl.setCategoryToEdit((Category) entity);
		// mainFrame.showView(ViewName.EDITOR);
		mainFrame.showEditorAsDialog(entity);
	}
}
