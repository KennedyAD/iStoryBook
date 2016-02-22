/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package storybook.ui.chart;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.Layer;

import storybook.model.BookModel;
import storybook.model.hbn.dao.SceneDAOImpl;
import storybook.model.hbn.dao.StrandDAOImpl;
import storybook.model.hbn.entity.Part;
import storybook.model.hbn.entity.Strand;
import storybook.toolkit.swing.ColorUtil;
import storybook.ui.MainFrame;
import storybook.ui.chart.jfreechart.ChartUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class StrandsByDateChart.
 */
public class StrandsByDateChart extends AbstractChartPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8304872642266527724L;
	
	/** The chart panel. */
	private ChartPanel chartPanel;
	
	/** The average. */
	private double average;

	/**
	 * Instantiates a new strands by date chart.
	 *
	 * @param mainFrame the main frame
	 */
	public StrandsByDateChart(MainFrame mainFrame) {
		super(mainFrame, "msg.menu.tools.charts.overall.character.date");
		this.partRelated = true;
		this.needsFullRefresh = true;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
	}

	/**
	 * Creates the chart.
	 *
	 * @param setCategory the set category
	 * @return the j free chart
	 */
	private JFreeChart createChart(CategoryDataset setCategory) {
		JFreeChart chart = ChartFactory.createBarChart(this.chartTitle, "", "", setCategory, PlotOrientation.VERTICAL,
				true, true, false);
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.addRangeMarker(ChartUtil.getAverageMarker(this.average), Layer.FOREGROUND);
		BookModel model = this.mainFrame.getBookModel();
		Session session = model.beginTransaction();
		StrandDAOImpl daoStrand = new StrandDAOImpl(session);
		List strands = daoStrand.findAll();
		model.commit();
		Color[] colors = new Color[strands.size()];
		int i = 0;
		Object iObject = strands.iterator();
		while (((Iterator) iObject).hasNext()) {
			Strand strand = (Strand) ((Iterator) iObject).next();
			colors[i] = ColorUtil.darker(strand.getJColor(), 0.25D);
			i++;
		}
		iObject = plot.getRenderer();
		for (int j = 0; j < setCategory.getRowCount(); j++) {
			Color color = colors[(j % colors.length)];
			((BarRenderer) iObject).setSeriesPaint(j, color);
		}
		return chart;
	}

	/**
	 * Creates the dataset.
	 *
	 * @return the category dataset
	 */
	private CategoryDataset createDataset() {
		DefaultCategoryDataset setCategory = new DefaultCategoryDataset();
		try {
			Part part = this.mainFrame.getCurrentPart();
			BookModel model = this.mainFrame.getBookModel();
			Session session = model.beginTransaction();
			StrandDAOImpl daoStrand = new StrandDAOImpl(session);
			List strands = daoStrand.findAll();
			SceneDAOImpl daoScene = new SceneDAOImpl(session);
			List scenes = daoScene.findDistinctDates(part);
			double d = 0.0D;
			Iterator iStrand = strands.iterator();
			while (iStrand.hasNext()) {
				Strand strand = (Strand) iStrand.next();
				Iterator iScene = scenes.iterator();
				while (iScene.hasNext()) {
					Date date = (Date) iScene.next();
					long l = daoStrand.countByDate(date, strand);
					setCategory.addValue(l, strand, date);
					d += l;
				}
			}
			model.commit();
			this.average = (d / (strands.size() + scenes.size()));
		} catch (Exception exc) {
			System.err.println("StrandsByDateChart.createDataset() Exception : " + exc.getMessage());
		}
		return setCategory;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.chart.AbstractChartPanel#initChart()
	 */
	@Override
	protected void initChart() {
		CategoryDataset setCategory = createDataset();
		JFreeChart chart = createChart(setCategory);
		this.chartPanel = new ChartPanel(chart);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.chart.AbstractChartPanel#initChartUi()
	 */
	@Override
	protected void initChartUi() {
		this.panel.add(this.chartPanel, "grow");
	}

	/* (non-Javadoc)
	 * @see storybook.ui.chart.AbstractChartPanel#initOptionsUi()
	 */
	@Override
	protected void initOptionsUi() {
	}
}