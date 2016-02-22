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

import storybook.model.hbn.dao.TagDAOImpl;
import storybook.model.hbn.entity.AbstractEntity;
import storybook.model.hbn.entity.Tag;
import storybook.ui.MainFrame;
import storybook.ui.table.SbColumnFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class TagEntityHandler.
 *
 * @author martin
 */
public class TagEntityHandler extends AbstractEntityHandler {

	/**
	 * Instantiates a new tag entity handler.
	 *
	 * @param mainFrame the main frame
	 */
	public TagEntityHandler(MainFrame mainFrame) {
		super(mainFrame, SbColumnFactory.getInstance().getTagColumns());
	}

	/* (non-Javadoc)
	 * @see storybook.model.handler.AbstractEntityHandler#createNewEntity()
	 */
	@Override
	public AbstractEntity createNewEntity() {
		Tag tag = new Tag();
		return tag;
	}

	/* (non-Javadoc)
	 * @see storybook.model.handler.AbstractEntityHandler#getDAOClass()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> Class<T> getDAOClass() {
		return (Class<T>) TagDAOImpl.class;
	}

	/* (non-Javadoc)
	 * @see storybook.model.handler.AbstractEntityHandler#getEntityClass()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> Class<T> getEntityClass() {
		return (Class<T>) Tag.class;
	}
}
