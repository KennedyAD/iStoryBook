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

package storybook.ui.dialog;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;

import storybook.toolkit.I18N;
import storybook.toolkit.swing.SwingUtil;
import storybook.ui.MainFrame;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractDialog.
 *
 * @author martin
 */

public abstract class AbstractDialog extends JDialog {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4607616589683708476L;
	
	/** The main frame. */
	protected MainFrame mainFrame;
	
	/** The parent. */
	protected JComponent parent;

	/** The canceled. */
	protected boolean canceled = false;

	/**
	 * Instantiates a new abstract dialog.
	 */
	public AbstractDialog() {
		this.mainFrame = null;
		this.parent = null;
	}

	/**
	 * Instantiates a new abstract dialog.
	 *
	 * @param parent the parent
	 */
	public AbstractDialog(JComponent parent) {
		this.parent = parent;
		this.mainFrame = null;
	}

	/**
	 * Instantiates a new abstract dialog.
	 *
	 * @param mainFrame the main frame
	 */
	public AbstractDialog(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.parent = null;
	}

	/**
	 * Gets the cancel action.
	 *
	 * @return the cancel action
	 */
	protected AbstractAction getCancelAction() {
		return new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 319611570268904313L;

			@Override
			public void actionPerformed(ActionEvent e) {
				canceled = true;
				dispose();
			}
		};
	}

	/**
	 * Gets the cancel button.
	 *
	 * @return the cancel button
	 */
	protected JButton getCancelButton() {
		AbstractAction act = getCancelAction();
		JButton bt = new JButton(act);
		bt.setText(I18N.getMsg("msg.common.cancel"));
		bt.setIcon(I18N.getIcon("icon.small.cancel"));
		SwingUtil.addEscAction(bt, act);
		return bt;
	}

	/**
	 * Gets the close button.
	 *
	 * @return the close button
	 */
	protected JButton getCloseButton() {
		JButton bt = new JButton(getOkAction());
		bt.setIcon(I18N.getIcon("icon.small.close"));
		bt.setText(I18N.getMsg("msg.common.close"));
		return bt;
	}

	/**
	 * Gets the ok action.
	 *
	 * @return the ok action
	 */
	protected AbstractAction getOkAction() {
		return new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -128459478086427802L;

			@Override
			public void actionPerformed(ActionEvent e) {
				canceled = false;
				dispose();
			}
		};
	}

	/**
	 * Gets the ok button.
	 *
	 * @return the ok button
	 */
	protected JButton getOkButton() {
		AbstractAction act = getOkAction();
		JButton bt = new JButton(act);
		bt.setText(I18N.getMsg("msg.common.ok"));
		bt.setIcon(I18N.getIcon("icon.small.ok"));
		SwingUtil.addEnterAction(bt, act);
		return bt;
	}

	/**
	 * Inits the.
	 */
	abstract public void init();

	/**
	 * Inits the all.
	 */
	public void initAll() {
		init();
		initUi();
	}

	/**
	 * Inits the ui.
	 */
	public void initUi() {
		if (mainFrame != null) {
			setIconImage(mainFrame.getIconImage());
		}
	}

	/**
	 * Checks if is canceled.
	 *
	 * @return true, if is canceled
	 */
	public boolean isCanceled() {
		return canceled;
	}
}
