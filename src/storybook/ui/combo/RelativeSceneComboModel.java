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

package storybook.ui.combo;

import java.util.List;

import javax.swing.DefaultComboBoxModel;

import org.hibernate.Session;

import storybook.model.BookModel;
import storybook.model.hbn.dao.SceneDAOImpl;
import storybook.model.hbn.entity.Scene;
import storybook.ui.MainFrame;

// TODO: Auto-generated Javadoc
/**
 * The Class RelativeSceneComboModel.
 *
 * @author martin
 */

public class RelativeSceneComboModel extends DefaultComboBoxModel<Object> implements IRefreshableComboModel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8655029076861853208L;
	
	/** The main frame. */
	private MainFrame mainFrame;

	/**
	 * Instantiates a new relative scene combo model.
	 */
	public RelativeSceneComboModel() {
	}

	/* (non-Javadoc)
	 * @see storybook.ui.combo.IRefreshableComboModel#getMainFrame()
	 */
	@Override
	public MainFrame getMainFrame() {
		return mainFrame;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.interfaces.IRefreshable#refresh()
	 */
	@Override
	public void refresh() {
		if (mainFrame == null) {
			return;
		}
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		SceneDAOImpl dao = new SceneDAOImpl(session);
		List<Scene> scenes = dao.findAll();
		for (Scene scene : scenes) {
			addElement(scene);
		}
		model.commit();
	}

	/* (non-Javadoc)
	 * @see storybook.ui.combo.IRefreshableComboModel#setMainFrame(storybook.ui.MainFrame)
	 */
	@Override
	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	/* (non-Javadoc)
	 * @see javax.swing.DefaultComboBoxModel#setSelectedItem(java.lang.Object)
	 */
	@Override
	public void setSelectedItem(Object obj) {
		Scene scene;
		if (obj instanceof Long) {
			BookModel model = mainFrame.getBookModel();
			Session session = model.beginTransaction();
			scene = (Scene) session.get(Scene.class, (Long) obj);
			model.commit();
		} else {
			scene = (Scene) obj;
		}
		super.setSelectedItem(scene);
	}
}
