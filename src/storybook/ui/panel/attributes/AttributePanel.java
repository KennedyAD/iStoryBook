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

package storybook.ui.panel.attributes;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.util.List;

import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import storybook.model.hbn.entity.Attribute;
import storybook.toolkit.swing.AutoCompleteComboBox;
import storybook.ui.panel.AbstractPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class AttributePanel.
 *
 * @author martin
 */

public class AttributePanel extends AbstractPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1001268984925884482L;
	
	/** The attribute. */
	private Attribute attribute;
	
	/** The combo. */
	private AutoCompleteComboBox combo;
	
	/** The tf value. */
	private JTextField tfValue;
	
	/** The keys. */
	private List<String> keys;

	/**
	 * Instantiates a new attribute panel.
	 *
	 * @param attribute the attribute
	 * @param keys the keys
	 */
	public AttributePanel(Attribute attribute, List<String> keys) {
		this.attribute = attribute;
		this.keys = keys;
		initAll();
	}

	/**
	 * Instantiates a new attribute panel.
	 *
	 * @param keys the keys
	 */
	public AttributePanel(List<String> keys) {
		this(null, keys);
	}

	/**
	 * Gets the attribute.
	 *
	 * @return the attribute
	 */
	public Attribute getAttribute() {
		String key = (String) combo.getJComboBox().getSelectedItem();
		String value = tfValue.getText();
		if (key.isEmpty() || value.isEmpty()) {
			return null;
		}
		return new Attribute(key, value);
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
	@SuppressWarnings("unchecked")
	public void initUi() {
		setLayout(new MigLayout("ins 0,flowx,fillx", "[][grow]", ""));

		combo = new AutoCompleteComboBox(false, false);
		combo.getJComboBox().addItem("");
		for (String key : keys) {
			combo.getJComboBox().addItem(key);
		}
		if (attribute != null) {
			combo.getJComboBox().setSelectedItem(attribute.getKey());
		}

		tfValue = new JTextField();
		if (attribute != null) {
			tfValue.setText(attribute.getValue());
		}
		tfValue.setPreferredSize(new Dimension(230, 20));

		// layout
		add(combo);
		add(tfValue, "growx");
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#modelPropertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void modelPropertyChange(PropertyChangeEvent evt) {
	}
}
