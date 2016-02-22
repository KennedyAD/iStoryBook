package storybook.ui.chart;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableColumn;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.Session;

import storybook.model.BookModel;
import storybook.model.hbn.dao.LocationDAOImpl;
import storybook.model.hbn.dao.PersonDAOImpl;
import storybook.model.hbn.dao.SceneDAOImpl;
import storybook.model.hbn.entity.Location;
import storybook.model.hbn.entity.Part;
import storybook.model.hbn.entity.Person;
import storybook.toolkit.I18N;
import storybook.toolkit.swing.FontUtil;
import storybook.toolkit.swing.ReadOnlyTable;
import storybook.toolkit.swing.SwingUtil;
import storybook.toolkit.swing.table.FixedColumnScrollPane;
import storybook.ui.MainFrame;
import storybook.ui.chart.legend.PersonsLegendPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class WiWWChart.
 */
public class WiWWChart extends AbstractPersonsChart implements ChangeListener {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3431322982261406936L;
	
	/** The found characters. */
	private Set<Person> foundCharacters;
	
	/** The table. */
	private JTable table;
	
	/** The col slider. */
	private JSlider colSlider;
	
	/** The col width. */
	private int colWidth = 50;

	/**
	 * Instantiates a new wi ww chart.
	 *
	 * @param paramMainFrame the param main frame
	 */
	public WiWWChart(MainFrame paramMainFrame) {
		super(paramMainFrame, "msg.report.person.location.time.title");
		this.partRelated = true;
		this.needsFullRefresh = true;
	}

	/**
	 * Creates the table.
	 *
	 * @return the j table
	 */
	@SuppressWarnings("unchecked")
	private JTable createTable() {
		Part part = this.mainFrame.getCurrentPart();
		BookModel documentModel = this.mainFrame.getBookModel();
		Session session = documentModel.beginTransaction();
		PersonDAOImpl personDAO = new PersonDAOImpl(session);
		List<Person> persons = personDAO.findByCategories(this.selectedCategories);
		SceneDAOImpl sceneDAO = new SceneDAOImpl(session);
		List<Date> dates = sceneDAO.findDistinctDates(part);
		LocationDAOImpl locationDAO = new LocationDAOImpl(session);
		List<Location> locations = locationDAO.findAll();
		documentModel.commit();
		Object[] arrayOfObject1 = ArrayUtils.addAll(new Object[] { I18N.getMsg("msg.common.location"), "" },
				dates.toArray());
		this.foundCharacters.clear();
		ArrayList localArrayList = new ArrayList();
		Iterator<Location> locationsIterator = locations.iterator();
		while (locationsIterator.hasNext()) {
			Location location = locationsIterator.next();
			Object[] localObject2 = new Object[arrayOfObject1.length];
			int j = 0;
			localObject2[(j++)] = location.getName();
			localObject2[(j++)] = location.getCountryCity();
			int m = 0;
			Iterator<Date> datesIterator = dates.iterator();
			while (datesIterator.hasNext()) {
				Date localDate = datesIterator.next();
				WiWWContainer localWiWWContainer = new WiWWContainer(this.mainFrame, localDate, location, persons);
				localObject2[j] = localWiWWContainer;
				if (localWiWWContainer.isFound()) {
					this.foundCharacters.addAll(localWiWWContainer.getCharacterList());
					m = 1;
				}
				j++;
			}
			if (m != 0) {
				localArrayList.add(localObject2);
			}
		}
		// Object[] localObject1 = new Object[localArrayList.size()][];
		// int i = 0;
		/*
		 * Obfuscator ? Object localObject2 = localArrayList.iterator(); while
		 * (((Iterator) localObject2).hasNext()) { Object[] arrayOfObject2 =
		 * (Object[]) ((Iterator) localObject2).next(); localObject1[(i++)] =
		 * arrayOfObject2; }
		 */
		ReadOnlyTable jTable = new ReadOnlyTable((Object[][]) localArrayList.toArray(new Object[0][]), arrayOfObject1);
		for (int k = 2; k < jTable.getColumnCount(); k++) {
			TableColumn localTableColumn = jTable.getColumnModel().getColumn(k);
			localTableColumn.setPreferredWidth(120);
			localTableColumn.setCellRenderer(new WiWWTableCellRenderer());
		}
		jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jTable.getTableHeader().setReorderingAllowed(false);
		return jTable;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.chart.AbstractPersonsChart#initChart()
	 */
	@Override
	protected void initChart() {
		super.initChart();
		this.foundCharacters = new TreeSet<Person>();
	}

	/* (non-Javadoc)
	 * @see storybook.ui.chart.AbstractChartPanel#initChartUi()
	 */
	@Override
	protected void initChartUi() {
		JLabel localJLabel = new JLabel(this.chartTitle);
		localJLabel.setFont(FontUtil.getBoldFont());
		this.table = createTable();
		FixedColumnScrollPane localFixedColumnScrollPane = new FixedColumnScrollPane(this.table, 2);
		localFixedColumnScrollPane.getRowHeader().setPreferredSize(new Dimension(300, 20));
		this.panel.add(localJLabel, "center");
		this.panel.add(localFixedColumnScrollPane, "grow, h pref-40");
		this.panel.add(new PersonsLegendPanel(this.mainFrame, this.foundCharacters), "gap push");
	}

	/* (non-Javadoc)
	 * @see storybook.ui.chart.AbstractPersonsChart#initOptionsUi()
	 */
	@Override
	protected void initOptionsUi() {
		super.initOptionsUi();
		JLabel localJLabel = new JLabel(I18N.getIcon("icon.small.size"));
		this.colSlider = SwingUtil.createSafeSlider(0, 5, 300, this.colWidth);
		this.colSlider.setMinorTickSpacing(1);
		this.colSlider.setMajorTickSpacing(2);
		this.colSlider.setSnapToTicks(false);
		this.colSlider.addChangeListener(this);
		this.colSlider.setOpaque(false);
		this.optionsPanel.add(localJLabel, "gap push,right");
		this.optionsPanel.add(this.colSlider);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#refresh()
	 */
	@Override
	public void refresh() {
		this.colWidth = this.colSlider.getValue();
		super.refresh();
		this.colSlider.setValue(this.colWidth);
		setTableColumnWidth();
	}

	/**
	 * Sets the table column width.
	 */
	private void setTableColumnWidth() {
		this.colWidth = this.colSlider.getValue();
		for (int i = 0; i < this.table.getColumnCount(); i++) {
			TableColumn column = this.table.getColumnModel().getColumn(i);
			column.setPreferredWidth(this.colWidth);
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	@Override
	public void stateChanged(ChangeEvent paramChangeEvent) {
		setTableColumnWidth();
	}
}