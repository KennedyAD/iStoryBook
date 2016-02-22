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
import storybook.model.hbn.dao.StrandDAOImpl;
import storybook.model.hbn.entity.AbstractEntity;
import storybook.model.hbn.entity.Strand;
import storybook.ui.MainFrame;
import storybook.ui.SbView;

// TODO: Auto-generated Javadoc
/**
 * The Class StrandTable.
 *
 * @author martin
 */

public class StrandTable extends AbstractTable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5943077124787904842L;

	/**
	 * Instantiates a new strand table.
	 *
	 * @param mainFrame the main frame
	 */
	public StrandTable(MainFrame mainFrame) {
		super(mainFrame);
		allowMultiDelete = false;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#getEntity(java.lang.Long)
	 */
	@Override
	protected AbstractEntity getEntity(Long id) {
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		StrandDAOImpl dao = new StrandDAOImpl(session);
		Strand strand = dao.find(id);
		model.commit();
		return strand;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#getNewEntity()
	 */
	@Override
	protected AbstractEntity getNewEntity() {
		return new Strand();
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#getTableName()
	 */
	@Override
	public String getTableName() {
		return ("Strand");
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#init()
	 */
	@Override
	public void init() {
		columns = SbColumnFactory.getInstance().getStrandColumns();
		hasOrder = true;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#modelPropertyChangeLocal(java.beans.PropertyChangeEvent)
	 */
	@Override
	protected void modelPropertyChangeLocal(PropertyChangeEvent evt) {
		try {
			String propName = evt.getPropertyName();
			if (BookController.StrandProps.INIT.check(propName)) {
				initTableModel(evt);
			} else if (BookController.StrandProps.UPDATE.check(propName)) {
				updateEntity(evt);
			} else if (BookController.StrandProps.NEW.check(propName)) {
				newEntity(evt);
			} else if (BookController.StrandProps.DELETE.check(propName)) {
				deleteEntity(evt);
			} else if (BookController.StrandProps.ORDER_UP.check(propName)) {
				orderUpEntity(evt);
			} else if (BookController.StrandProps.ORDER_DOWN.check(propName)) {
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
		Strand strand = (Strand) entity;

		BookModel model = mainFrame.getBookModel();

		Session session = model.beginTransaction();
		StrandDAOImpl dao = new StrandDAOImpl(session);
		dao.orderStrands();
		model.commit();

		session = model.beginTransaction();
		dao = new StrandDAOImpl(session);
		dao.orderDownStrand(strand);
		model.commit();

		SbView view = mainFrame.getView(ViewName.STRANDS);
		mainFrame.getBookController().refresh(view);

		sortByColumn(4);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#orderUpEntity(java.beans.PropertyChangeEvent)
	 */
	@Override
	protected void orderUpEntity(PropertyChangeEvent evt) {
		AbstractEntity entity = (AbstractEntity) evt.getNewValue();
		Strand strand = (Strand) entity;

		BookModel model = mainFrame.getBookModel();

		Session session = model.beginTransaction();
		StrandDAOImpl dao = new StrandDAOImpl(session);
		dao.orderStrands();
		model.commit();

		session = model.beginTransaction();
		dao = new StrandDAOImpl(session);
		dao.orderUpStrand(strand);
		model.commit();

		SbView view = mainFrame.getView(ViewName.STRANDS);
		mainFrame.getBookController().refresh(view);

		sortByColumn(4);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#sendDeleteEntities(int[])
	 */
	@Override
	protected synchronized void sendDeleteEntities(int[] rows) {
		ArrayList<Long> ids = new ArrayList<Long>();
		for (int row : rows) {
			Strand strand = (Strand) getEntityFromRow(row);
			ids.add(strand.getId());
		}
		ctrl.deleteMultiStrands(ids);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#sendDeleteEntity(int)
	 */
	@Override
	protected synchronized void sendDeleteEntity(int row) {
		Strand strand = (Strand) getEntityFromRow(row);
		ctrl.deleteStrand(strand);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#sendOrderDownEntity(int)
	 */
	@Override
	protected void sendOrderDownEntity(int row) {
		if (row == -1) {
			return;
		}
		Strand strand = (Strand) getEntityFromRow(row);
		ctrl.orderDownStrand(strand);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#sendOrderUpEntity(int)
	 */
	@Override
	protected void sendOrderUpEntity(int row) {
		if (row == -1) {
			return;
		}
		Strand strand = (Strand) getEntityFromRow(row);
		ctrl.orderUpStrand(strand);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#sendSetEntityToEdit(int)
	 */
	@Override
	protected void sendSetEntityToEdit(int row) {
		if (row == -1) {
			return;
		}
		Strand strand = (Strand) getEntityFromRow(row);
		// ctrl.setStrandToEdit(strand);
		// mainFrame.showView(ViewName.EDITOR);
		mainFrame.showEditorAsDialog(strand);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#sendSetNewEntityToEdit(storybook.model.hbn.entity.AbstractEntity)
	 */
	@Override
	protected void sendSetNewEntityToEdit(AbstractEntity entity) {
		// ctrl.setStrandToEdit((Strand) entity);
		// mainFrame.showView(ViewName.EDITOR);
		mainFrame.showEditorAsDialog(entity);
	}
}
