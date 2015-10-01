/*
Storybook: Open Source software for novelists and authors.
Copyright (C) 2015 FaVdB

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
import storybook.model.hbn.dao.GroupDAOImpl;
import storybook.model.hbn.entity.AbstractEntity;
import storybook.model.hbn.entity.Group;
import storybook.ui.MainFrame;

/**
 * @author martin
 *
 */
@SuppressWarnings("serial")
public class GroupTable extends AbstractTable {

	public GroupTable(MainFrame mainFrame) {
		super(mainFrame);
	}

	@Override
	public void init() {
		columns = SbColumnFactory.getInstance().getGroupColumns();
	}

	@Override
	protected void modelPropertyChangeLocal(PropertyChangeEvent evt) {
		try {
			String propName = evt.getPropertyName();
			if (BookController.GroupProps.INIT.check(propName)) {
				initTableModel(evt);
			} else if (BookController.GroupProps.UPDATE.check(propName)) {
				updateEntity(evt);
			} else if (BookController.GroupProps.NEW.check(propName)) {
				newEntity(evt);
			} else if (BookController.GroupProps.DELETE.check(propName)) {
				deleteEntity(evt);
			}
		} catch (Exception e) {
		}
	}

	@Override
	protected void sendSetEntityToEdit(int row) {
		if (row == -1) {
			return;
		}
		Group r = (Group) getEntityFromRow(row);
		ctrl.setGroupToEdit(r);
		mainFrame.showView(ViewName.EDITOR);
	}

	@Override
	protected void sendSetNewEntityToEdit(AbstractEntity entity) {
		ctrl.setGroupToEdit((Group) entity);
		mainFrame.showView(ViewName.EDITOR);
	}

	@Override
	protected synchronized void sendDeleteEntity(int row) {
		Group r = (Group) getEntityFromRow(row);
		ctrl.deleteGroup(r);
	}

	@Override
	protected synchronized void sendDeleteEntities(int[] rows) {
		ArrayList<Long> ids = new ArrayList<Long>();
		for (int row : rows) {
			Group r = (Group) getEntityFromRow(row);
			ids.add(r.getId());
		}
		ctrl.deleteMultiGroups(ids);
	}

	@Override
	protected AbstractEntity getEntity(Long id) {
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		GroupDAOImpl dao = new GroupDAOImpl(session);
		Group r = dao.find(id);
		model.commit();
		return r;
	}

	@Override
	protected AbstractEntity getNewEntity() {
		return new Group();
	}

	@Override
	public String getTableName() {
		return("Group");
	}
}
