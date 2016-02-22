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

package storybook.toolkit.swing.panel;

import java.beans.PropertyChangeEvent;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import net.miginfocom.swing.MigLayout;
import storybook.toolkit.I18N;
import storybook.ui.MainFrame;
import storybook.ui.panel.AbstractPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class ViewsRadioButtonPanel.
 *
 * @author martin
 */

public class ViewsRadioButtonPanel extends AbstractPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 917994500388932662L;
	
	/** The rb chrono. */
	private JRadioButton rbChrono;
	
	/** The rb book. */
	private JRadioButton rbBook;
	
	/** The rb manage. */
	private JRadioButton rbManage;
	
	/** The show manage. */
	private boolean showManage;

	/**
	 * Instantiates a new views radio button panel.
	 *
	 * @param mainFrame the main frame
	 */
	public ViewsRadioButtonPanel(MainFrame mainFrame) {
		this(mainFrame, true);
	}

	/**
	 * Instantiates a new views radio button panel.
	 *
	 * @param mainFrame the main frame
	 * @param showManage the show manage
	 */
	public ViewsRadioButtonPanel(MainFrame mainFrame, boolean showManage) {
		super(mainFrame);
		this.showManage = showManage;
		initAll();
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#init()
	 */
	@Override
	public void init() {
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#initUi()
	 */
	@Override
	public void initUi() {
		setLayout(new MigLayout("ins 0,wrap 2", "[]", "[]"));

		JLabel lbChronoIcon = new JLabel(I18N.getIcon("icon.small.chrono.view"));
		rbChrono = new JRadioButton();
		rbChrono.setText(I18N.getMsg("msg.menu.view.chrono"));
		rbChrono.setSelected(true);
		JLabel lbBookIcon = new JLabel(I18N.getIcon("icon.small.book.view"));
		rbBook = new JRadioButton();
		rbBook.setText(I18N.getMsg("msg.menu.view.book"));
		JLabel lbManageIcon = new JLabel(I18N.getIcon("icon.small.manage.view"));
		if (showManage) {
			rbManage = new JRadioButton();
			rbManage.setText(I18N.getMsg("msg.menu.view.manage"));
		}
		ButtonGroup btGroup = new ButtonGroup();
		btGroup.add(rbChrono);
		btGroup.add(rbBook);
		if (showManage) {
			btGroup.add(rbManage);
		}

		// layout
		add(lbChronoIcon);
		add(rbChrono);
		add(lbBookIcon, "");
		add(rbBook, "");
		if (showManage) {
			add(lbManageIcon, "");
			add(rbManage, "");
		}
	}

	/**
	 * Checks if is book selected.
	 *
	 * @return true, if is book selected
	 */
	public boolean isBookSelected() {
		return rbBook.isSelected();
	}

	/**
	 * Checks if is chrono selected.
	 *
	 * @return true, if is chrono selected
	 */
	public boolean isChronoSelected() {
		return rbChrono.isSelected();
	}

	/**
	 * Checks if is manage selected.
	 *
	 * @return true, if is manage selected
	 */
	public boolean isManageSelected() {
		return rbManage.isSelected();
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#modelPropertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void modelPropertyChange(PropertyChangeEvent evt) {
	}
}
