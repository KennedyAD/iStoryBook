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
/* vérification OK */

package storybook.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import storybook.model.hbn.entity.Part;

// TODO: Auto-generated Javadoc
/**
 * The Class ChangePartAction.
 *
 * @author martin
 */
public class ChangePartAction extends AbstractAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1594185453102914870L;
	
	/** The action handler. */
	private final ActionHandler actionHandler;
	
	/** The part. */
	private final Part part;

	/**
	 * Instantiates a new change part action.
	 *
	 * @param name the name
	 * @param actionHandler the action handler
	 * @param part the part
	 */
	public ChangePartAction(String name, ActionHandler actionHandler, Part part) {
		super(name);
		this.actionHandler = actionHandler;
		this.part = part;
	}

	/** (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		actionHandler.handleChangePart(part);
	}

	/**
	 * Gets the action handler.
	 *
	 * @return the action handler
	 */
	public ActionHandler getActionHandler() {
		return actionHandler;
	}

	/**
	 * Gets the part.
	 *
	 * @return the part
	 */
	public Part getPart() {
		return part;
	}
}
