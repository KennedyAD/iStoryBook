/*
Storybook: Open Source software for novelists and authors.
Copyright (C) 2008 - 2012 Martin Mustun, 2015 FaVdB

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

package storybook.ui;

import java.awt.Component;
import java.util.Objects;

import javax.swing.Icon;
import javax.swing.JComponent;

import net.infonode.docking.View;

// TODO: Auto-generated Javadoc
/**
 * The Class SbView.
 *
 * @author martin
 */

public class SbView extends View {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4420161275236942105L;

	/** The counter. */
	private static int counter = -1;

	/** The loaded. */
	private boolean loaded;
	
	/** The number. */
	private Integer number;

	/**
	 * Instantiates a new sb view.
	 *
	 * @param title the title
	 */
	public SbView(String title) {
		this(title, null, null);
	}

	/**
	 * Instantiates a new sb view.
	 *
	 * @param title the title
	 * @param comp the comp
	 */
	public SbView(String title, Component comp) {
		this(title, null, comp);
	}

	/**
	 * Instantiates a new sb view.
	 *
	 * @param title the title
	 * @param icon the icon
	 */
	public SbView(String title, Icon icon) {
		this(title, icon, null);
	}

	/**
	 * Instantiates a new sb view.
	 *
	 * @param title the title
	 * @param icon the icon
	 * @param comp the comp
	 */
	public SbView(String title, Icon icon, Component comp) {
		super(title, icon, comp);
		loaded = comp != null;
		number = counter++;
	}

	/**
	 * Clever restore focus.
	 */
	public void cleverRestoreFocus() {
		setVisible(true);
		if (!isMinimized()) {
			restore();
		}
		restoreFocus();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (obj.getClass() != this.getClass())) {
			return false;
		}
		SbView test = (SbView) obj;
		return Objects.equals(number, test.number);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 7;
		hash = hash * 31 + number.hashCode();
		return hash;
	}

	/**
	 * Checks if is loaded.
	 *
	 * @return true, if is loaded
	 */
	public boolean isLoaded() {
		return loaded;
	}

	/**
	 * Checks if is window showing.
	 *
	 * @return true, if is window showing
	 */
	public boolean isWindowShowing() {
		return getRootWindow() != null;
	}

	/**
	 * Load.
	 *
	 * @param comp the comp
	 */
	public void load(JComponent comp) {
		super.setComponent(comp);
		loaded = true;
	}

	/* (non-Javadoc)
	 * @see net.infonode.docking.View#toString()
	 */
	@Override
	public String toString() {
		return "View " + number + ": " + getTitle();
	}

	/**
	 * Unload.
	 */
	public void unload() {
		super.setComponent(null);
		loaded = false;
	}
}
