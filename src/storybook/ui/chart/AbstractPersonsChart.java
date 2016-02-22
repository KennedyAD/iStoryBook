/*
 * SbApp: Open Source software for novelists and authors.
 * Original idea 2008 - 2012 Martin Mustun
 * Copyrigth (C) Favdb
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */
package storybook.ui.chart;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

import storybook.SbConstants;
import storybook.model.EntityUtil;
import storybook.model.hbn.entity.Category;
import storybook.toolkit.I18N;
import storybook.ui.MainFrame;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractPersonsChart.
 */
public abstract class AbstractPersonsChart extends AbstractChartPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -560287079314516405L;
	
	/** The category cb list. */
	protected List<JCheckBox> categoryCbList;
	
	/** The selected categories. */
	protected List<Category> selectedCategories;

	/**
	 * Instantiates a new abstract persons chart.
	 *
	 * @param paramMainFrame the param main frame
	 * @param paramString the param string
	 */
	public AbstractPersonsChart(MainFrame paramMainFrame, String paramString) {
		super(paramMainFrame, paramString);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		updateSelectedCategories();
		refreshChart();
	}

	/* (non-Javadoc)
	 * @see storybook.ui.chart.AbstractChartPanel#initChart()
	 */
	@Override
	protected void initChart() {
		this.categoryCbList = EntityUtil.createCategoryCheckBoxes(this.mainFrame, this);
		this.selectedCategories = new ArrayList<Category>();
		updateSelectedCategories();
	}

	/* (non-Javadoc)
	 * @see storybook.ui.chart.AbstractChartPanel#initOptionsUi()
	 */
	@Override
	protected void initOptionsUi() {
		JLabel localJLabel = new JLabel(I18N.getMsgColon("msg.common.categories"));
		this.optionsPanel.add(localJLabel, "split " + this.categoryCbList.size() + 1);
		Iterator localIterator = this.categoryCbList.iterator();
		while (localIterator.hasNext()) {
			JCheckBox localJCheckBox = (JCheckBox) localIterator.next();
			this.optionsPanel.add(localJCheckBox);
		}
	}

	/**
	 * Update selected categories.
	 */
	private void updateSelectedCategories() {
		this.selectedCategories.clear();
		Iterator localIterator = this.categoryCbList.iterator();
		while (localIterator.hasNext()) {
			JCheckBox localJCheckBox = (JCheckBox) localIterator.next();
			if (localJCheckBox.isSelected()) {
				Category localCategory = (Category) localJCheckBox
						.getClientProperty(SbConstants.ComponentName.CB_CATEGORY);
				this.selectedCategories.add(localCategory);
			}
		}
	}
}