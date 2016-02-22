/*
Storybook: Scene-based software for novelists and authors.
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

package storybook.ui.dialog.file;

import storybook.model.DbFile;
import storybook.toolkit.I18N;
import storybook.ui.MainFrame;


// TODO: Auto-generated Javadoc
/**
 * The Class RenameFileDialog.
 */
public class RenameFileDialog extends AbstractFileDialog {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5345732922707327858L;

	/**
	 * Instantiates a new rename file dialog.
	 *
	 * @param mainFrame the main frame
	 */
	public RenameFileDialog(MainFrame mainFrame) {
		super(mainFrame);
		setTitle(I18N.getMsg("msg.common.project.rename"));
		setHideDir(true);
		DbFile dbFile = mainFrame.getDbFile();
		file = dbFile.getFile();
		setFilename(dbFile.getName());
		setDir(file.getParent());
	}
}
