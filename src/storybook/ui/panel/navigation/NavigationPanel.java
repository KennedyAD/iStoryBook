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

package storybook.ui.panel.navigation;

import java.beans.PropertyChangeEvent;

import javax.swing.JTabbedPane;

import net.infonode.docking.View;
import net.miginfocom.swing.MigLayout;
import storybook.controller.BookController;
import storybook.toolkit.I18N;
import storybook.ui.MainFrame;
import storybook.ui.panel.AbstractPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class NavigationPanel.
 *
 * @author martin
 */

public class NavigationPanel extends AbstractPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1339626475159591609L;
	
	/** The tabbed pane. */
	private JTabbedPane tabbedPane;

	/**
	 * Instantiates a new navigation panel.
	 *
	 * @param mainFrame the main frame
	 */
	public NavigationPanel(MainFrame mainFrame) {
		super(mainFrame);
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
		setLayout(new MigLayout("wrap,fill,ins 0"));

		tabbedPane = new JTabbedPane();
		tabbedPane.addTab(I18N.getMsg("msg.menu.navigate.goto.chapter"), new FindChapterPanel(mainFrame));
		tabbedPane.addTab(I18N.getMsg("msg.menu.navigate.goto.date"), new FindDatePanel(mainFrame));
		add(tabbedPane, "grow");
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#modelPropertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void modelPropertyChange(PropertyChangeEvent evt) {
		// Object oldValue = evt.getOldValue();
		Object newValue = evt.getNewValue();
		String propName = evt.getPropertyName();

		if (BookController.CommonProps.REFRESH.check(propName)) {
			View newView = (View) newValue;
			View view = (View) getParent().getParent();
			if (view == newView) {
				refresh();
			}
			return;
		}

		if (propName.startsWith("Edit") || propName.startsWith("Init")) {
			return;
		}

		if (propName.contains("Scene") || propName.contains("Chapter") || propName.contains("Strand")) {
			refresh();
			return;
		}
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#refresh()
	 */
	@Override
	public void refresh() {
		int index = tabbedPane.getSelectedIndex();
		super.refresh();
		tabbedPane.setSelectedIndex(index);
	}
}
