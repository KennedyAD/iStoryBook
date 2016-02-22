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

package storybook.toolkit.swing.label;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.JLabel;

// TODO: Auto-generated Javadoc
/**
 * The Class DateLabel.
 *
 * @author martin
 */

public class DateLabel extends JLabel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2300497707610027896L;
	
	/** The date. */
	private Date date;

	/**
	 * Instantiates a new date label.
	 */
	public DateLabel() {
		super();
		setOpaque(true);
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate(Date date) {
		this.date = date;
		DateFormat formatter = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
		setText(formatter.format(this.date));
		setOpaque(true);
	}
}
