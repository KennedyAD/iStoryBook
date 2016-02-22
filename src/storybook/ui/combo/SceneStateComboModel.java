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

package storybook.ui.combo;

import javax.swing.DefaultComboBoxModel;

import storybook.model.state.AbstractState;
import storybook.model.state.SceneStateModel;

// TODO: Auto-generated Javadoc
/**
 * The Class SceneStateComboModel.
 *
 * @author martin
 */

public class SceneStateComboModel extends DefaultComboBoxModel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -9156749898684036323L;

	/**
	 * Instantiates a new scene state combo model.
	 */
	public SceneStateComboModel() {
		this(true);
	}

	/**
	 * Instantiates a new scene state combo model.
	 *
	 * @param addPseudoStates the add pseudo states
	 */
	@SuppressWarnings("unchecked")
	public SceneStateComboModel(boolean addPseudoStates) {
		SceneStateModel model = new SceneStateModel(addPseudoStates);
		for (AbstractState state : model.getStates()) {
			addElement(state);
		}
	}
}
