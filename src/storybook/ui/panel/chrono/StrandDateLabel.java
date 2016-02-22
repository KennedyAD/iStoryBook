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

package storybook.ui.panel.chrono;

import java.util.Date;

import storybook.model.hbn.entity.Strand;
import storybook.ui.interfaces.IRefreshable;


// TODO: Auto-generated Javadoc
/**
 * The Class StrandDateLabel.
 */
public class StrandDateLabel extends DateLabel implements IRefreshable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8282250838315049968L;
	
	/** The strand. */
	private Strand strand;

	/**
	 * Instantiates a new strand date label.
	 *
	 * @param strand the strand
	 * @param date the date
	 */
	public StrandDateLabel(Strand strand, Date date) {
		super(date);
		this.strand = strand;
		refresh();
	}

	/**
	 * Gets the strand.
	 *
	 * @return the strand
	 */
	public Strand getStrand() {
		return strand;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.interfaces.IRefreshable#refresh()
	 */
	@Override
	public final void refresh() {
		String text = getDateText();
		setText(text);
		setToolTipText("<html>" + text + "<br>" + strand);
	}

	/**
	 * Sets the strand.
	 *
	 * @param strand the new strand
	 */
	public void setStrand(Strand strand) {
		this.strand = strand;
	}
}
