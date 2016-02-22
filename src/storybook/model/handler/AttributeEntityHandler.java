/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package storybook.model.handler;

import javax.swing.ListCellRenderer;

import storybook.model.hbn.dao.AttributeDAOImpl;
import storybook.model.hbn.entity.AbstractEntity;
import storybook.model.hbn.entity.Attribute;
import storybook.ui.MainFrame;
import storybook.ui.combo.AttributeListCellRenderer;
import storybook.ui.table.SbColumnFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class AttributeEntityHandler.
 *
 * @author favdb
 */
public class AttributeEntityHandler extends AbstractEntityHandler {

	/**
	 * Instantiates a new attribute entity handler.
	 *
	 * @param mainFrame the main frame
	 */
	public AttributeEntityHandler(MainFrame mainFrame) {
		super(mainFrame, SbColumnFactory.getInstance().getGenderColumns());
	}

	/* (non-Javadoc)
	 * @see storybook.model.handler.AbstractEntityHandler#createNewEntity()
	 */
	@Override
	public AbstractEntity createNewEntity() {
		Attribute attribute = new Attribute();
		return (attribute);
	}

	/* (non-Javadoc)
	 * @see storybook.model.handler.AbstractEntityHandler#getDAOClass()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> Class<T> getDAOClass() {
		return (Class<T>) AttributeDAOImpl.class;
	}

	/* (non-Javadoc)
	 * @see storybook.model.handler.AbstractEntityHandler#getEntityClass()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> Class<T> getEntityClass() {
		return (Class<T>) Attribute.class;
	}

	/* (non-Javadoc)
	 * @see storybook.model.handler.AbstractEntityHandler#getListCellRenderer()
	 */
	@Override
	public ListCellRenderer getListCellRenderer() {
		return new AttributeListCellRenderer();
	}

}
