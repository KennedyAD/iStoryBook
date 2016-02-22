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

package storybook.ui.label;

import javax.swing.JLabel;

import storybook.model.state.SceneState;
import storybook.toolkit.I18N;
import storybook.ui.interfaces.IRefreshable;

// TODO: Auto-generated Javadoc
/**
 * The Class SceneStateLabel.
 *
 * @author martin
 */

public class SceneStateLabel extends JLabel implements IRefreshable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7308695876398053960L;
	
	/** The state. */
	private SceneState state;
	
	/** The icon only. */
	private boolean iconOnly;

	/**
	 * Instantiates a new scene state label.
	 *
	 * @param state the state
	 */
	public SceneStateLabel(SceneState state) {
		this(state, false);
	}

	/**
	 * Instantiates a new scene state label.
	 *
	 * @param state the state
	 * @param iconOnly the icon only
	 */
	public SceneStateLabel(SceneState state, boolean iconOnly) {
		this.state = state;
		this.iconOnly = iconOnly;
		refresh();
	}

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public SceneState getState() {
		return state;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.interfaces.IRefreshable#refresh()
	 */
	@Override
	public void refresh() {
		if (!iconOnly) {
			setText(state.toString());
		}
		setIcon(state.getIcon());
		setToolTipText(I18N.getMsgColon("msg.status") + " " + state);
	}

	/**
	 * Sets the state.
	 *
	 * @param state the new state
	 */
	public void setState(SceneState state) {
		this.state = state;
	}
}
