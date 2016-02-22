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

package storybook.model.state;

import javax.swing.Icon;

import storybook.toolkit.I18N;

// TODO: Auto-generated Javadoc
/**
 * The Class SceneState.
 *
 * @author martin
 */
public class SceneState extends AbstractState {

	/**
	 * Instantiates a new scene state.
	 *
	 * @param number the number
	 * @param name the name
	 */
	public SceneState(Integer number, String name) {
		this(number, name, null);
	}

	/**
	 * Instantiates a new scene state.
	 *
	 * @param number the number
	 * @param name the name
	 * @param icon the icon
	 */
	public SceneState(Integer number, String name, Icon icon) {
		super();
		this.number = number;
		this.name = name;
		this.icon = icon;
	}

	/* (non-Javadoc)
	 * @see storybook.model.state.AbstractState#getToolTip()
	 */
	@Override
	public String getToolTip() {
		return I18N.getMsgColon("msg.status") + " " + this;
	}
}
