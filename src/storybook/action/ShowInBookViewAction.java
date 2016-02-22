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

package storybook.action;

import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;

import storybook.SbConstants.ViewName;
import storybook.controller.BookController;
import storybook.model.hbn.entity.AbstractEntity;
import storybook.toolkit.I18N;
import storybook.ui.MainFrame;

// TODO: Auto-generated Javadoc
/**
 * The Class ShowInBookViewAction.
 *
 * @author martin
 */
public class ShowInBookViewAction extends AbstractEntityAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3012150858856504639L;

	/**
	 * Instantiates a new show in book view action.
	 *
	 * @param mainFrame the main frame
	 * @param entity the entity
	 */
	public ShowInBookViewAction(MainFrame mainFrame, AbstractEntity entity) {
		super(mainFrame, entity, I18N.getMsg("msg.show.in.book.view"), I18N.getIcon("icon.small.book.view"));
	}

	/** (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		mainFrame.showView(ViewName.BOOK);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				BookController ctrl = mainFrame.getBookController();
				ctrl.bookShowEntity(entity);
			}
		});
	}
}
