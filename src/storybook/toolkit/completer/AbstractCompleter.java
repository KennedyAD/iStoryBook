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

package storybook.toolkit.completer;

import java.awt.event.KeyListener;

import javax.swing.text.JTextComponent;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractCompleter.
 *
 * @author martin
 */
public abstract class AbstractCompleter implements KeyListener {

	/** The comp. */
	protected JTextComponent comp;

	/**
	 * Gets the comp.
	 *
	 * @return the comp
	 */
	public JTextComponent getComp() {
		return comp;
	}

	/**
	 * Gets the completed text.
	 *
	 * @return the completed text
	 */
	public abstract String getCompletedText();

	/**
	 * Sets the comp.
	 *
	 * @param comp the new comp
	 */
	public void setComp(JTextComponent comp) {
		this.comp = comp;
	}
}
