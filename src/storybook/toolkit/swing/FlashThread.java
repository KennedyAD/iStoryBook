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

package storybook.toolkit.swing;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JLabel;

import storybook.toolkit.I18N;

// TODO: Auto-generated Javadoc
/**
 * The Class FlashThread.
 */
public class FlashThread extends Thread implements ActionListener {
	
	/** The Constant CN_FLASH_LABEL. */
	private static final String CN_FLASH_LABEL = "FLASH";

	/** The comp. */
	private JComponent comp;

	/** The remove. */
	private boolean remove = false;

	/**
	 * Instantiates a new flash thread.
	 *
	 * @param comp the comp
	 */
	public FlashThread(JComponent comp) {
		this.comp = comp;
	}

	/**
	 * Instantiates a new flash thread.
	 *
	 * @param comp the comp
	 * @param remove the remove
	 */
	public FlashThread(JComponent comp, boolean remove) {
		this.comp = comp;
		this.remove = remove;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		try {
			Component lb = SwingUtil.findComponentByName(comp, CN_FLASH_LABEL);
			if (lb == null) {
				return;
			}
			comp.remove(lb);
			comp.repaint();
			SwingUtil.flashEnded();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		try {
			if (remove) {
				return;
			}
			JLabel lb = new JLabel(I18N.getIcon("icon.medium.target"));
			lb.setName(CN_FLASH_LABEL);
			Dimension dim = comp.getSize();
			comp.add(lb, "pos " + dim.width / 2 + " " + dim.height / 2);
			comp.setComponentZOrder(lb, 0);
			comp.validate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
