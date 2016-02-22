/*
Storybook: Scene-based software for novelists and authors.
Copyright (C) 2008 - 2011 Martin Mustun

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
import javax.swing.JDialog;

// TODO: Auto-generated Javadoc
/**
 * The Class DisposeDialogAction.
 */
public class DisposeDialogAction extends AbstractAction {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7155093606555095607L;
	
	/** The dlg. */
	private JDialog dlg;

	/**
	 * Instantiates a new dispose dialog action.
	 *
	 * @param dlg the dlg
	 */
	public DisposeDialogAction(JDialog dlg) {
		this.dlg = dlg;
	}

	/** (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		dlg.requestFocus();
		dlg.requestFocusInWindow();
		dlg.dispose();
	}
}
