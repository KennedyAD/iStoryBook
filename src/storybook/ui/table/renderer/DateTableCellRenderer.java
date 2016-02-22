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

package storybook.ui.table.renderer;

import java.util.Date;

import javax.swing.table.DefaultTableCellRenderer;

import storybook.toolkit.I18N;

// TODO: Auto-generated Javadoc
/**
 * The Class DateTableCellRenderer.
 *
 * @author martin
 */

public class DateTableCellRenderer extends DefaultTableCellRenderer {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4844389177759617435L;

	/**
	 * Instantiates a new date table cell renderer.
	 */
	public DateTableCellRenderer() {
		super();
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.DefaultTableCellRenderer#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(Object value) {
		try {
			if (value instanceof Date) {
				setText(I18N.getDateTime((Date) value));
			} else {
				setText("");
			}
		} catch (Exception e) {
			setText("");
		}
	}
}
