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
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;

import storybook.model.hbn.entity.Strand;
import storybook.toolkit.I18N;
import storybook.toolkit.ViewUtil;
import storybook.ui.panel.AbstractPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class ScrollToStrandDateAction.
 *
 * @author martin
 */
public class ScrollToStrandDateAction implements ActionListener {

	/** The found. */
	private boolean found;
	
	/** The container. */
	private AbstractPanel container;
	
	/** The panel. */
	private JPanel panel;
	
	/** The strand. */
	private Strand strand;
	
	/** The date. */
	private Date date;
	
	/** The lb warning. */
	private JLabel lbWarning;

	/**
	 * Instantiates a new scroll to strand date action.
	 *
	 * @param container the container
	 * @param panel the panel
	 * @param strand the strand
	 * @param date the date
	 * @param lbWarning the lb warning
	 */
	public ScrollToStrandDateAction(AbstractPanel container, JPanel panel, Strand strand, Date date, JLabel lbWarning) {
		this.container = container;
		this.panel = panel;
		this.strand = strand;
		this.date = date;
		this.lbWarning = lbWarning;
	}

	/** (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		found = ViewUtil.scrollToStrandDate(container, panel, strand, date);
		if (!found) {
			lbWarning.setText(I18N.getMsg("msg.dlg.navigation.date.not.found"));
		} else {
			lbWarning.setText(" ");
		}
	}

	/**
	 * Checks if is found.
	 *
	 * @return true, if is found
	 */
	public boolean isFound() {
		return found;
	}
}
