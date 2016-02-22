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
 * The Class ManageOptionsDialog.
 *
 * @author martin
 */

public class ManageOptionsDialog extends AbstractOptionsDialog {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5375460022408774950L;

	/** The cn columns. */
	private final String CN_COLUMNS = "ColumnSlider";

	/** The columns. */
	private int columns;

	/**
	 * Instantiates a new manage options dialog.
	 *
	 * @param mainFrame the main frame
	 */
	public ManageOptionsDialog(MainFrame mainFrame) {
		super(mainFrame);
	}

	/**
	 * Instantiates a new manage options dialog.
	 *
	 * @param mainFrame the main frame
	 * @param hasZoom the has zoom
	 */
	public ManageOptionsDialog(MainFrame mainFrame, boolean hasZoom) {
		super(mainFrame, hasZoom);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.AbstractDialog#init()
	 */
	@Override
	public void init() {
		setZoomMinValue(SbConstants.MIN_MANAGE_ZOOM);
		setZoomMaxValue(SbConstants.MAX_MANAGE_ZOOM);
		try {
			Internal internal = BookUtil.get(mainFrame, BookKey.MANAGE_ZOOM, SbConstants.DEFAULT_MANAGE_ZOOM);
			zoomValue = internal.getIntegerValue();
			internal = BookUtil.get(mainFrame, BookKey.MANAGE_COLUMNS, SbConstants.DEFAULT_MANAGE_COLUMNS);
			columns = internal.getIntegerValue();
		} catch (Exception e) {
			e.printStackTrace();
			zoomValue = SbConstants.DEFAULT_MANAGE_ZOOM;
			columns = SbConstants.DEFAULT_MANAGE_COLUMNS;
		}
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.AbstractDialog#initUi()
	 */
	@Override
	public void initUi() {
		// columns
		JLabel lbColumns = new JLabel(I18N.getMsgColon("msg.common.columns"));
		JSlider slider = new JSlider(SwingConstants.HORIZONTAL, 1, 20, columns);
		slider.setName(CN_COLUMNS);
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(5);
		slider.setOpaque(false);
		slider.setPaintTicks(true);
		slider.addChangeListener(this);

		// layout
		panel.add(lbColumns);
		panel.add(slider, "growx");
	}

	/* (non-Javadoc)
	 * @see storybook.ui.options.AbstractOptionsDialog#stateChanged(javax.swing.event.ChangeEvent)
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		Component comp = (Component) e.getSource();
		if (CN_COLUMNS.equals(comp.getName())) {
			JSlider slider = (JSlider) e.getSource();
			if (!slider.getValueIsAdjusting()) {
				int val = slider.getValue();
				BookUtil.store(mainFrame, BookKey.MANAGE_COLUMNS, val);
				mainFrame.getBookController().manageSetColumns(val);
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
		BookUtil.store(mainFrame, BookKey.MANAGE_ZOOM, val);
		mainFrame.getBookController().manageSetZoom(val);
	}
}
