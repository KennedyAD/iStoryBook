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

import javax.swing.ListCellRenderer;

import storybook.model.hbn.dao.SceneDAOImpl;
import storybook.model.hbn.entity.AbstractEntity;
import storybook.model.hbn.entity.Scene;
import storybook.ui.MainFrame;
import storybook.ui.combo.SceneListCellRenderer;
import storybook.ui.table.SbColumnFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class SceneEntityHandler.
 *
 * @author martin
 */
public class SceneEntityHandler extends AbstractEntityHandler {

	/**
	 * Instantiates a new scene entity handler.
	 *
	 * @param mainFrame the main frame
	 */
	public SceneEntityHandler(MainFrame mainFrame) {
		super(mainFrame, SbColumnFactory.getInstance().getSceneColumns());
	}

	/* (non-Javadoc)
	 * @see storybook.model.handler.AbstractEntityHandler#createNewEntity()
	 */
	@Override
	public AbstractEntity createNewEntity() {
		Scene scene = new Scene();
		return scene;
	}

	/* (non-Javadoc)
	 * @see storybook.model.handler.AbstractEntityHandler#getDAOClass()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> Class<T> getDAOClass() {
		return (Class<T>) SceneDAOImpl.class;
	}

	/* (non-Javadoc)
	 * @see storybook.model.handler.AbstractEntityHandler#getEntityClass()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> Class<T> getEntityClass() {
		return (Class<T>) Scene.class;
	}

	/* (non-Javadoc)
	 * @see storybook.model.handler.AbstractEntityHandler#getListCellRenderer()
	 */
	@Override
	public ListCellRenderer getListCellRenderer() {
		return new SceneListCellRenderer();
	}

	/* (non-Javadoc)
	 * @see storybook.model.handler.AbstractEntityHandler#newEntity(storybook.model.hbn.entity.AbstractEntity)
	 */
	@Override
	public AbstractEntity newEntity(AbstractEntity entity) {
		Scene scene = new Scene();
		Scene orig = (Scene) entity;
		if (orig.getStrand() != null) {
			scene.setStrand(orig.getStrand());
		}
		if (orig.getSceneTs() != null) {
			scene.setSceneTs(orig.getSceneTs());
		}
		if (orig.hasChapter()) {
			scene.setChapter(orig.getChapter());
		}
		return scene;
	}
}
