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

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.swing.MigLayout;
import storybook.toolkit.I18N;
import storybook.toolkit.swing.SwingUtil;
import storybook.toolkit.swing.panel.BackgroundPanel;
import storybook.ui.MainFrame;
import storybook.ui.dialog.AbstractDialog;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractOptionsDialog.
 *
 * @author martin
 */

public abstract class AbstractOptionsDialog extends AbstractDialog implements ChangeListener {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4970850444163145408L;

	/** The zoom. */
	private boolean zoom;

	/** The zoom value. */
	protected int zoomValue;
	
	/** The zoom min value. */
	protected int zoomMinValue;
	
	/** The zoom max value. */
	protected int zoomMaxValue;

	/** The panel. */
	protected BackgroundPanel panel;

	/**
	 * Instantiates a new abstract options dialog.
	 *
	 * @param mainFrame the main frame
	 */
	public AbstractOptionsDialog(MainFrame mainFrame) {
		this(mainFrame, true);
	}

	/**
	 * Instantiates a new abstract options dialog.
	 *
	 * @param mainFrame the main frame
	 * @param hasZoom the has zoom
	 */
	public AbstractOptionsDialog(MainFrame mainFrame, boolean hasZoom) {
		super(mainFrame);
		this.zoom = hasZoom;
		internalInit();
		internalInitUi();
	}

	/**
	 * Gets the zoom max value.
	 *
	 * @return the zoom max value
	 */
	public int getZoomMaxValue() {
		return zoomMaxValue;
	}

	/**
	 * Gets the zoom min value.
	 *
	 * @return the zoom min value
	 */
	public int getZoomMinValue() {
		return zoomMinValue;
	}

	/**
	 * Internal init.
	 */
	private void internalInit() {
		zoomValue = 50;
		zoomMinValue = 0;
		zoomMaxValue = 100;
		init();
	}

	/**
	 * Internal init ui.
	 */
	private void internalInitUi() {
		setLayout(new MigLayout("flowy,fill,ins 0"));
		setPreferredSize(new Dimension(500, 300));
		setUndecorated(true);

		ImageIcon imgIcon = I18N.getImageIcon("icon.options");
		panel = new BackgroundPanel(imgIcon.getImage(), BackgroundPanel.ACTUAL);
		panel.setLayout(new MigLayout("fill,wrap 1,ins 20", "[]20[grow]"));
		panel.setBorder(SwingUtil.getBorderDefault());

		if (zoom) {
			// zoom
			JLabel lbZoom = new JLabel(I18N.getMsgColon("msg.common.zoom"));
			JSlider zoomSlider = new JSlider(SwingConstants.HORIZONTAL, zoomMinValue, zoomMaxValue, zoomValue);
			zoomSlider.setOpaque(false);
			zoomSlider.setMajorTickSpacing(10);
			zoomSlider.setMinorTickSpacing(5);
			zoomSlider.setPaintTicks(true);
			zoomSlider.addChangeListener(this);

			panel.add(lbZoom);
			panel.add(zoomSlider, "growx");
		}

		initUi();

		panel.add(getCloseButton(), "span 2,pushy 200,al left bottom");

		add(panel, "grow");
	}

	/**
	 * Checks if is zoom.
	 *
	 * @return true, if is zoom
	 */
	public boolean isZoom() {
		return zoom;
	}

	/**
	 * Sets the zoom.
	 *
	 * @param zoom the new zoom
	 */
	public void setZoom(boolean zoom) {
		this.zoom = zoom;
	}

	/**
	 * Sets the zoom max value.
	 *
	 * @param zoomMaxValue the new zoom max value
	 */
	public void setZoomMaxValue(int zoomMaxValue) {
		this.zoomMaxValue = zoomMaxValue;
	}

	/**
	 * Sets the zoom min value.
	 *
	 * @param zoomMinValue the new zoom min value
	 */
	public void setZoomMinValue(int zoomMinValue) {
		this.zoomMinValue = zoomMinValue;
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider slider = (JSlider) e.getSource();
		if (!slider.getValueIsAdjusting()) {
			int val = slider.getValue();
			zoom(val);
		}
	}

	/**
	 * Zoom.
	 *
	 * @param val the val
	 */
	protected void zoom(int val) {
	}
}
