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
package storybook.ui.dialog.copy;

import java.util.List;

import org.hibernate.Session;

import storybook.controller.BookController;
import storybook.model.EntityUtil;
import storybook.model.handler.AbstractEntityHandler;
import storybook.model.hbn.entity.AbstractEntity;
import storybook.toolkit.swing.SwingUtil;
import storybook.ui.MainFrame;
import storybook.ui.edit.CbPanelDecorator;

/**
 * @author martin
 *
 */
public abstract class AbstractCopier<ELEMENT extends AbstractEntity> {

    private MainFrame mainFrame;

	public AbstractCopier(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	public void showDialog() {
		SwingUtil.showModalDialog(new CopyDialog<ELEMENT>(this), mainFrame);
	}
	
	public MainFrame getMainFrame() {
		return mainFrame;
	}

	@SuppressWarnings("unchecked")
	protected ELEMENT copy(MainFrame destination, ELEMENT elt) {

		AbstractEntityHandler entityHandler = getEntityHandler(destination);
		AbstractEntity newElt = entityHandler.createNewEntity();
		BookController destCtrl = destination.getBookController();
		EntityUtil.copyEntityProperties(destination, elt, newElt);
		prepareTransfer(mainFrame, destination, (ELEMENT)elt, (ELEMENT)newElt);
		destCtrl.newEntity(newElt);
		copySpecialInformation(mainFrame, destination, elt, (ELEMENT)newElt);
		return (ELEMENT) newElt;
	}
	
	protected abstract void prepareTransfer(MainFrame origin, MainFrame destination, ELEMENT originElt, ELEMENT destElt);

	protected abstract void copySpecialInformation(MainFrame origin, MainFrame destination, ELEMENT originElt, ELEMENT destElt);
	
	protected abstract List<ELEMENT> getAllElements(Session session, MainFrame origin);
	
	protected abstract CbPanelDecorator getDecorator();
	
	protected abstract AbstractEntityHandler getEntityHandler(MainFrame mainFrame);
}


