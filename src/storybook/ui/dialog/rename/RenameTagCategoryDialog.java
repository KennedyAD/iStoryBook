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

package storybook.ui.dialog.rename;

import java.util.List;

import org.hibernate.Session;

import storybook.controller.BookController;
import storybook.model.BookModel;
import storybook.model.hbn.dao.TagDAOImpl;
import storybook.model.hbn.entity.Tag;
import storybook.toolkit.I18N;
import storybook.ui.MainFrame;


// TODO: Auto-generated Javadoc
/**
 * The Class RenameTagCategoryDialog.
 */
public class RenameTagCategoryDialog extends AbstractRenameDialog {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4490669572082772796L;

	/**
	 * Instantiates a new rename tag category dialog.
	 *
	 * @param mainFrame the main frame
	 */
	public RenameTagCategoryDialog(MainFrame mainFrame) {
		super(mainFrame);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.rename.AbstractRenameDialog#getDlgTitle()
	 */
	@Override
	protected String getDlgTitle() {
		return I18N.getMsg("msg.tag.rename.category");
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.rename.AbstractRenameDialog#getList()
	 */
	@Override
	protected List<String> getList() {
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		TagDAOImpl dao = new TagDAOImpl(session);
		List<String> ret = dao.findCategories();
		model.commit();
		return ret;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.rename.AbstractRenameDialog#rename(java.lang.String, java.lang.String)
	 */
	@Override
	protected void rename(String oldValue, String newValue) {
		BookModel model = mainFrame.getBookModel();
		BookController ctrl = mainFrame.getBookController();
		Session session = model.beginTransaction();
		TagDAOImpl dao = new TagDAOImpl(session);
		List<Tag> tags = dao.findByCategory(oldValue);
		model.commit();
		for (Tag tag : tags) {
			tag.setCategory(newValue);
			ctrl.updateTag(tag);
		}
	}
}
