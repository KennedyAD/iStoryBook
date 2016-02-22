/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package storybook.ui.table;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

import org.hibernate.Session;

import storybook.controller.BookController;
import storybook.model.BookModel;
import storybook.model.hbn.dao.AttributeDAOImpl;
import storybook.model.hbn.entity.AbstractEntity;
import storybook.model.hbn.entity.Attribute;
import storybook.ui.MainFrame;

// TODO: Auto-generated Javadoc
/**
 * The Class AttributeTable.
 *
 * @author favdb
 */
public class AttributeTable extends AbstractTable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6726506515160952698L;

	/**
	 * Instantiates a new attribute table.
	 *
	 * @param main the main
	 */
	public AttributeTable(MainFrame main) {
		super(main);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#getEntity(java.lang.Long)
	 */
	@Override
	protected AbstractEntity getEntity(Long id) {
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		AttributeDAOImpl dao = new AttributeDAOImpl(session);
		Attribute entity = dao.find(id);
		model.commit();
		return entity;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#getNewEntity()
	 */
	@Override
	protected AbstractEntity getNewEntity() {
		return new Attribute();
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#getTableName()
	 */
	@Override
	public String getTableName() {
		return ("Attribute");
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#init()
	 */
	@Override
	public void init() {
		columns = SbColumnFactory.getInstance().getAttributeColumns();
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#modelPropertyChangeLocal(java.beans.PropertyChangeEvent)
	 */
	@Override
	protected void modelPropertyChangeLocal(PropertyChangeEvent evt) {
		try {
			String propName = evt.getPropertyName();
			if (BookController.AttributeProps.INIT.check(propName)) {
				initTableModel(evt);
			} else if (BookController.AttributeProps.UPDATE.check(propName)) {
				updateEntity(evt);
			} else if (BookController.AttributeProps.NEW.check(propName)) {
				newEntity(evt);
			} else if (BookController.AttributeProps.DELETE.check(propName)) {
				deleteEntity(evt);
			}
		} catch (Exception e) {
		}
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#sendDeleteEntities(int[])
	 */
	@Override
	protected void sendDeleteEntities(int[] rows) {
		ArrayList<Long> ids = new ArrayList<Long>();
		for (int row : rows) {
			Attribute entity = (Attribute) getEntityFromRow(row);
			ids.add(entity.getId());
		}
		ctrl.deleteMultiGenders(ids);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#sendDeleteEntity(int)
	 */
	@Override
	protected void sendDeleteEntity(int row) {
		Attribute entity = (Attribute) getEntityFromRow(row);
		ctrl.deleteAttribute(entity);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#sendSetEntityToEdit(int)
	 */
	@Override
	protected void sendSetEntityToEdit(int row) {
		if (row == -1) {
			return;
		}
		Attribute attribute = (Attribute) getEntityFromRow(row);
		// ctrl.setAttributeToEdit(attribute);
		// mainFrame.showView(ViewName.EDITOR);
		mainFrame.showEditorAsDialog(attribute);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.table.AbstractTable#sendSetNewEntityToEdit(storybook.model.hbn.entity.AbstractEntity)
	 */
	@Override
	protected void sendSetNewEntityToEdit(AbstractEntity entity) {
		// ctrl.setAttributeToEdit((Attribute) entity);
		// mainFrame.showView(ViewName.EDITOR);
		mainFrame.showEditorAsDialog(entity);
	}
}
