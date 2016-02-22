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

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractState.
 *
 * @author martin
 */
public class AbstractState {

	/** The number. */
	protected Integer number;
	
	/** The name. */
	protected String name;
	
	/** The icon. */
	protected Icon icon;

	/**
	 * Instantiates a new abstract state.
	 */
	public AbstractState() {
		super();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// if (!super.equals(obj)) {
		// return false;
		// }
		if (!(obj instanceof AbstractState)) {
			return false;
		}
		AbstractState test = (AbstractState) obj;
		boolean ret = true;
		ret = ret && number.equals(test.number);
		return ret;
	}

	/**
	 * Gets the icon.
	 *
	 * @return the icon
	 */
	public Icon getIcon() {
		return icon;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the number.
	 *
	 * @return the number
	 */
	public Integer getNumber() {
		return number;
	}

	/**
	 * Gets the tool tip.
	 *
	 * @return the tool tip
	 */
	public String getToolTip() {
		return toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = super.hashCode();
		hash = hash * 31 + number.hashCode();
		return hash;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}
}
