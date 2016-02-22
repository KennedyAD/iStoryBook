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

import java.util.Vector;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractStateModel.
 *
 * @author martin
 */
public abstract class AbstractStateModel {

	/** The states. */
	protected Vector<AbstractState> states;

	/**
	 * Instantiates a new abstract state model.
	 */
	public AbstractStateModel() {
		states = new Vector<AbstractState>();
	}

	/**
	 * Find by number.
	 *
	 * @param statusNumber the status number
	 * @return the abstract state
	 */
	public AbstractState findByNumber(Integer statusNumber) {
		for (int i = 0; i < states.size(); ++i) {
			AbstractState obj = states.get(i);
			if (obj.getNumber().equals(statusNumber)) {
				return obj;
			}
		}
		return states.get(0);
	}

	/**
	 * Gets the states.
	 *
	 * @return the states
	 */
	public Vector<AbstractState> getStates() {
		return states;
	}
}
