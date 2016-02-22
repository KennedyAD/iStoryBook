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

package storybook.ui.options;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;

import storybook.SbConstants;
import storybook.SbConstants.BookKey;
import storybook.model.hbn.entity.Internal;
import storybook.toolkit.BookUtil;
import storybook.toolkit.I18N;
import storybook.ui.MainFrame;

// TODO: Auto-generated Javadoc
/**
 * The Class BookOptionsDialog.
 *
 * @author martin
 */

public class BookOptionsDialog extends AbstractOptionsDialog {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7958887312609797125L;

	/** The cn height factor. */
	private final String CN_HEIGHT_FACTOR = "HeightFactorSlider";

	/** The height factor. */
	private int heightFactor;

	/**
	 * Instantiates a new book options dialog.
	 *
	 * @param mainFrame the main frame
	 */
	public BookOptionsDialog(MainFrame mainFrame) {
		super(mainFrame);
	}

	/**
	 * Instantiates a new book options dialog.
	 *
	 * @param mainFrame the main frame
	 * @param hasZoom the has zoom
	 */
	public BookOptionsDialog(MainFrame mainFrame, boolean hasZoom) {
		super(mainFrame, hasZoom);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.AbstractDialog#init()
	 */
	@Override
	public void init() {
		setZoomMinValue(SbConstants.MIN_BOOK_ZOOM);
		setZoomMaxValue(SbConstants.MAX_BOOK_ZOOM);
		try {
			Internal internal = BookUtil.get(mainFrame, BookKey.BOOK_ZOOM, SbConstants.DEFAULT_BOOK_ZOOM);
			zoomValue = internal.getIntegerValue();
			internal = BookUtil.get(mainFrame, BookKey.BOOK_HEIGHT_FACTOR, SbConstants.DEFAULT_BOOK_HEIGHT_FACTOR);
			heightFactor = internal.getIntegerValue();
		} catch (Exception e) {
			e.printStackTrace();
			zoomValue = SbConstants.DEFAULT_BOOK_ZOOM;
			heightFactor = SbConstants.DEFAULT_BOOK_HEIGHT_FACTOR;
		}
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.AbstractDialog#initUi()
	 */
	@Override
	public void initUi() {
		// height factor
		JLabel lbHeightFactor = new JLabel(I18N.getMsgColon("msg.common.height.factor"));
		JSlider slider = new JSlider(SwingConstants.HORIZONTAL, 10, 20, heightFactor);
		slider.setName(CN_HEIGHT_FACTOR);
		slider.setMajorTickSpacing(5);
		slider.setMinorTickSpacing(1);
		slider.setOpaque(false);
		slider.setPaintTicks(true);
		slider.addChangeListener(this);

		// layout
		panel.add(lbHeightFactor);
		panel.add(slider, "growx");
	}

	/* (non-Javadoc)
	 * @see storybook.ui.options.AbstractOptionsDialog#stateChanged(javax.swing.event.ChangeEvent)
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		Component comp = (Component) e.getSource();
		if (CN_HEIGHT_FACTOR.equals(comp.getName())) {
			JSlider slider = (JSlider) e.getSource();
			if (!slider.getValueIsAdjusting()) {
				int val = slider.getValue();
				mainFrame.getBookController().bookSetHeightFactor(val);
				BookUtil.store(mainFrame, BookKey.BOOK_HEIGHT_FACTOR, val);
				return;
			}
		}
		super.stateChanged(e);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.options.AbstractOptionsDialog#zoom(int)
	 */
	@Override
	protected void zoom(int val) {
		BookUtil.store(mainFrame, BookKey.BOOK_ZOOM, val);
		mainFrame.getBookController().bookSetZoom(val);
	}
}
