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

package storybook.toolkit.swing;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;

import javax.swing.AbstractAction;
import javax.swing.JComboBox;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import net.miginfocom.swing.MigLayout;
import storybook.ui.panel.AbstractPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class AutoCompleteComboBox.
 *
 * @author martin
 */

public class AutoCompleteComboBox extends AbstractPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8305243096977356996L;
	
	/** The combo. */
	private JComboBox combo;
	
	/** The bt clear. */
	private IconButton btClear;
	
	/** The set preferred size. */
	private boolean setPreferredSize = true;
	
	/** The add clear button. */
	private boolean addClearButton = true;

	/**
	 * Instantiates a new auto complete combo box.
	 */
	public AutoCompleteComboBox() {
		super();
		initAll();
	}

	/**
	 * Instantiates a new auto complete combo box.
	 *
	 * @param setPreferredSize the set preferred size
	 * @param addClearButton the add clear button
	 */
	public AutoCompleteComboBox(boolean setPreferredSize, boolean addClearButton) {
		super();
		this.setPreferredSize = setPreferredSize;
		this.addClearButton = addClearButton;
		initAll();
	}

	/**
	 * Gets the clear action.
	 *
	 * @return the clear action
	 */
	public AbstractAction getClearAction() {
		return new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 872934768303874904L;

			@Override
			public void actionPerformed(ActionEvent e) {
				combo.setSelectedItem("");
			}
		};
	}

	/**
	 * Gets the j combo box.
	 *
	 * @return the j combo box
	 */
	public JComboBox getJComboBox() {
		return combo;
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
		setLayout(new MigLayout("ins 0,flowx"));

		combo = new JComboBox();
		combo.setEditable(true);
		if (setPreferredSize) {
			combo.setPreferredSize(new Dimension(250, 26));
		}
		AutoCompleteDecorator.decorate(combo);

		if (addClearButton) {
			btClear = new IconButton("icon.small.clear", getClearAction());
			btClear.setFlat();
			btClear.setSize20x20();
		}

		// layout
		add(combo);
		if (addClearButton) {
			add(btClear);
		}
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#modelPropertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void modelPropertyChange(PropertyChangeEvent evt) {
		// not used
	}
}
