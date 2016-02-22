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

package storybook.ui.panel.book;

import java.beans.PropertyChangeEvent;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import storybook.model.EntityUtil;
import storybook.model.hbn.entity.Scene;
import storybook.ui.MainFrame;
import storybook.ui.panel.AbstractScenePanel;


// TODO: Auto-generated Javadoc
/**
 * The Class BookScenePanel.
 */
public class BookScenePanel extends AbstractScenePanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2474582404856543422L;
	
	/** The cmd panel. */
	private JPanel cmdPanel;
	
	/** The text panel. */
	private BookTextPanel textPanel;
	
	/** The info panel. */
	private BookInfoPanel infoPanel;

	/**
	 * Instantiates a new book scene panel.
	 *
	 * @param mainFrame the main frame
	 * @param scene the scene
	 */
	public BookScenePanel(MainFrame mainFrame, Scene scene) {
		super(mainFrame, scene);
		init();
		initUi();
	}

	/**
	 * Creates the command panel.
	 *
	 * @return the j panel
	 */
	private JPanel createCommandPanel() {
		JPanel panel = new JPanel(new MigLayout("flowy,insets 0"));
		panel.setOpaque(false);

		// layout
		panel.add(getEditButton());
		panel.add(getDeleteButton());
		panel.add(getNewButton());

		return panel;
	}

	/**
	 * Gets the this.
	 *
	 * @return the this
	 */
	protected BookScenePanel getThis() {
		return this;
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
		refresh();
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractGradientPanel#modelPropertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void modelPropertyChange(PropertyChangeEvent evt) {
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractGradientPanel#refresh()
	 */
	@Override
	public void refresh() {
		MigLayout layout = new MigLayout("wrap 3,fill", "[]", "[top]");
		setLayout(layout);
		setOpaque(false);
		setComponentPopupMenu(EntityUtil.createPopupMenu(mainFrame, scene));

		removeAll();

		// info panel
		infoPanel = new BookInfoPanel(mainFrame, scene);

		// text panel
		textPanel = new BookTextPanel(mainFrame, scene);

		// command panel
		cmdPanel = createCommandPanel();

		// layout
		add(infoPanel, "w 250,h 250");
		add(textPanel, "grow,gap 10");
		add(cmdPanel);

		revalidate();
		repaint();
	}
}
