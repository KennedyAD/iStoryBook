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

package storybook.ui.edit;

import javax.swing.JCheckBox;

import storybook.model.hbn.entity.AbstractEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class CbPanelDecorator.
 *
 * @author martin
 */
abstract public class CbPanelDecorator {

	/** The panel. */
	protected CheckBoxPanel panel;

	/**
	 * Instantiates a new cb panel decorator.
	 */
	public CbPanelDecorator() {
	}

	/**
	 * Decorate after entity.
	 *
	 * @param entity the entity
	 */
	abstract public void decorateAfterEntity(AbstractEntity entity);

	/**
	 * Decorate before entity.
	 *
	 * @param entity the entity
	 */
	abstract public void decorateBeforeEntity(AbstractEntity entity);

	/**
	 * Decorate before first entity.
	 */
	abstract public void decorateBeforeFirstEntity();

	/**
	 * Decorate entity.
	 *
	 * @param cb the cb
	 * @param entity the entity
	 */
	abstract public void decorateEntity(JCheckBox cb, AbstractEntity entity);

	/**
	 * Gets the panel.
	 *
	 * @return the panel
	 */
	public CheckBoxPanel getPanel() {
		return panel;
	}

	/**
	 * Sets the panel.
	 *
	 * @param panel the new panel
	 */
	public void setPanel(CheckBoxPanel panel) {
		this.panel = panel;
	}
}
