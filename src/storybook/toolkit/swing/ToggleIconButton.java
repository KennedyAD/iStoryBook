/*
Storybook: Scene-based software for novelists and authors.
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

package storybook.toolkit.swing;

import java.awt.Dimension;

import javax.swing.Action;
import javax.swing.JToggleButton;

import storybook.toolkit.I18N;


// TODO: Auto-generated Javadoc
/**
 * The Class ToggleIconButton.
 */
public class ToggleIconButton extends JToggleButton {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1063050077127536039L;

	/**
	 * Instantiates a new toggle icon button.
	 */
	public ToggleIconButton() {
		super();
	}

	/**
	 * Instantiates a new toggle icon button.
	 *
	 * @param action the action
	 */
	public ToggleIconButton(Action action) {
		this(null, null, action);
	}

	/**
	 * Instantiates a new toggle icon button.
	 *
	 * @param resourceIcon the resource icon
	 */
	public ToggleIconButton(String resourceIcon) {
		this(resourceIcon, null);
	}

	/**
	 * Instantiates a new toggle icon button.
	 *
	 * @param resourceIcon the resource icon
	 * @param action the action
	 */
	public ToggleIconButton(String resourceIcon, Action action) {
		this(resourceIcon, null, action);
	}

	/**
	 * Instantiates a new toggle icon button.
	 *
	 * @param resourceIcon the resource icon
	 * @param resourceToolTip the resource tool tip
	 * @param action the action
	 */
	public ToggleIconButton(String resourceIcon, String resourceToolTip, Action action) {
		if (action != null) {
			setAction(action);
		}
		if (resourceIcon != null) {
			setIcon(I18N.getIcon(resourceIcon));
		}
		if (resourceToolTip != null) {
			setToolTipText(I18N.getMsg((resourceToolTip)));
		}
	}

	/**
	 * Sets the control button.
	 */
	public void setControlButton() {
		this.setSize16x16();
		this.setNoBorder();
	}

	/**
	 * Sets the icon.
	 *
	 * @param resourceIcon the new icon
	 */
	public void setIcon(String resourceIcon) {
		if (resourceIcon != null) {
			setIcon(I18N.getIcon(resourceIcon));
		}
	}

	/**
	 * Sets the no border.
	 */
	public void setNoBorder() {
		this.setBorder(null);
	}

	/**
	 * Sets the size16x16.
	 */
	public void setSize16x16() {
		SwingUtil.setForcedSize(this, new Dimension(16, 16));
	}

	/**
	 * Sets the size20x20.
	 */
	public void setSize20x20() {
		SwingUtil.setForcedSize(this, new Dimension(20, 20));
	}

	/**
	 * Sets the size22x22.
	 */
	public void setSize22x22() {
		SwingUtil.setForcedSize(this, new Dimension(22, 22));
	}

	/**
	 * Sets the size32x20.
	 */
	public void setSize32x20() {
		SwingUtil.setForcedSize(this, new Dimension(32, 20));
	}

	/**
	 * Sets the size72x72.
	 */
	public void setSize72x72() {
		SwingUtil.setForcedSize(this, new Dimension(72, 72));
	}
}
