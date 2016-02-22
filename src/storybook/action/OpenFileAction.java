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

import javax.swing.AbstractAction;

import storybook.SbApp;
import storybook.model.DbFile;

// TODO: Auto-generated Javadoc
/**
 * The Class OpenFileAction.
 *
 * @author martin
 */
public class OpenFileAction extends AbstractAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6937182424034701171L;
	
	/** The db file. */
	private DbFile dbFile;

	/**
	 * Instantiates a new open file action.
	 *
	 * @param name the name
	 * @param dbFile the db file
	 */
	public OpenFileAction(String name, DbFile dbFile) {
		super(name);
		this.dbFile = dbFile;
	}

	/** (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		SbApp app = SbApp.getInstance();
		app.openFile(dbFile);
	}

	/**
	 * Gets the db file.
	 *
	 * @return the db file
	 */
	public DbFile getDbFile() {
		return dbFile;
	}
}
