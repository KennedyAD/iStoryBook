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

import storybook.model.state.TimeStepState;
import storybook.toolkit.I18N;
import storybook.ui.interfaces.IRefreshable;

// TODO: Auto-generated Javadoc
/**
 * The Class TimeEventFormatLabel.
 *
 * @author jean
 */

public class TimeEventFormatLabel extends JLabel implements IRefreshable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6110693959696143825L;
	
	/** The state. */
	private TimeStepState state;

	/**
	 * Instantiates a new time event format label.
	 *
	 * @param state the state
	 */
	public TimeEventFormatLabel(TimeStepState state) {
		this(state, false);
	}

	/**
	 * Instantiates a new time event format label.
	 *
	 * @param state the state
	 * @param iconOnly the icon only
	 */
	public TimeEventFormatLabel(TimeStepState state, boolean iconOnly) {
		this.state = state;
		refresh();
	}

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public TimeStepState getState() {
		return state;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.interfaces.IRefreshable#refresh()
	 */
	@Override
	public void refresh() {
		setText(state.toString());
		setToolTipText(I18N.getMsgColon("msg.status") + " " + state);
	}

	/**
	 * Sets the state.
	 *
	 * @param state the new state
	 */
	public void setState(TimeStepState state) {
		this.state = state;
	}
}
