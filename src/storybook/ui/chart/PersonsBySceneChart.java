/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package storybook.ui.chart;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableColumn;

import org.hibernate.Session;

import storybook.model.BookModel;
import storybook.model.hbn.dao.PersonDAOImpl;
import storybook.model.hbn.dao.SceneDAOImpl;
import storybook.model.hbn.entity.Part;
import storybook.model.hbn.entity.Person;
import storybook.model.hbn.entity.Scene;
import storybook.toolkit.I18N;
import storybook.toolkit.html.HtmlUtil;
import storybook.toolkit.swing.ColorUtil;
import storybook.toolkit.swing.FontUtil;
import storybook.toolkit.swing.ReadOnlyTable;
import storybook.toolkit.swing.SwingUtil;
import storybook.toolkit.swing.table.ColorTableCellRenderer;
import storybook.toolkit.swing.table.FixedColumnScrollPane;
import storybook.toolkit.swing.table.HeaderTableCellRenderer;
import storybook.toolkit.swing.table.ToolTipHeader;
import storybook.ui.MainFrame;
import storybook.ui.chart.legend.StrandsLegendPanel;

public class PersonsBySceneChart extends AbstractPersonsChart implements ChangeListener {

	private JTable table;
	private JSlider colSlider;
	private JCheckBox cbShowUnusedPersons;
	private int colWidth = 50;

	public PersonsBySceneChart(MainFrame paramMainFrame) {
		super(paramMainFrame, "msg.report.person.scene.title");
		this.partRelated = true;
	}

	@SuppressWarnings("unchecked")
	private JTable createTable() {
		Part part = mainFrame.getCurrentPart();
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		PersonDAOImpl personDAO = new PersonDAOImpl(session);
		List persons = personDAO.findByCategories(selectedCategories);
		SceneDAOImpl sceneDAO = new SceneDAOImpl(session);
		List scenes = sceneDAO.findByPart(part);
		model.commit();
		String[] string1 = new String[scenes.size() + 1];
		string1[0] = "";
		int i = 1;
		Object scenesIterator = scenes.iterator();
		while (((Iterator) scenesIterator).hasNext()) {
			Scene scene = (Scene) ((Iterator) scenesIterator).next();
			string1[i] = scene.getChapterSceneNo(false);
			i++;
		}
		scenesIterator = new ArrayList();
		String[] string2 = new String[scenes.size() + 1];
		Iterator personsIterator = persons.iterator();
		Object localObject6;
		while (personsIterator.hasNext()) {
			Person person = (Person) personsIterator.next();
			int j = 0;
			Object[] string3 = new Object[scenes.size() + 1];
			string3[(j++)] = person.getAbbreviation();
			int n = 0;
			localObject6 = scenes.iterator();
			while (((Iterator) localObject6).hasNext()) {
				Scene localScene = (Scene) ((Iterator) localObject6).next();
				if (localScene.getPersons().contains(person)) {
					n = 1;
					string3[j] = ColorUtil.darker(localScene.getStrand().getJColor(), 0.05D);
				} else {
					string3[j] = null;
				}
				string2[j] = HtmlUtil.wrapIntoTable(localScene.getTitleText(true, 500));
				j++;
			}
			if ((cbShowUnusedPersons == null) || (cbShowUnusedPersons.isSelected()) || (n != 0)) {
				((List) scenesIterator).add(string3);
			}
		}
		Object[][] localObject31 = new Object[((List) scenesIterator).size()][];
		i = 0;
		Iterator localObject4 = ((List) scenesIterator).iterator();
		while (localObject4.hasNext()) {
			Object[] arrayOfObject1 = (Object[]) localObject4.next();
			localObject31[(i++)] = arrayOfObject1;
		}
		JTable ntable = new ReadOnlyTable(localObject31, string1);
		if (ntable.getModel().getRowCount() == 0) {
			return ntable;
		}
		ntable.getColumnModel().getColumn(0).setPreferredWidth(200);
		ntable.getColumnModel().getColumn(0).setCellRenderer(new HeaderTableCellRenderer());
		for (int k = 1; k < ntable.getColumnCount(); k++) {
			int m = ntable.getColumnModel().getColumn(k).getModelIndex();
			Object localObject5 = ntable.getModel().getValueAt(0, m);
			localObject6 = ntable.getColumnModel().getColumn(k);
			if ((localObject5 == null) || ((localObject5 instanceof Color))) {
				((TableColumn) localObject6).setPreferredWidth(colWidth);
				((TableColumn) localObject6).setCellRenderer(new ColorTableCellRenderer(false));
			}
		}
		ntable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		ntable.getTableHeader().setReorderingAllowed(false);
		ToolTipHeader localToolTipHeader = new ToolTipHeader(ntable.getColumnModel());
		localToolTipHeader.setToolTipStrings(string2);
		localToolTipHeader.setToolTipText("Default ToolTip TEXT");
		ntable.setTableHeader(localToolTipHeader);
		return ntable;
	}

	@Override
	protected void initChartUi() {
		JLabel localJLabel = new JLabel(this.chartTitle);
		localJLabel.setFont(FontUtil.getBoldFont());
		this.table = createTable();
		FixedColumnScrollPane localFixedColumnScrollPane = new FixedColumnScrollPane(this.table, 1);
		localFixedColumnScrollPane.getRowHeader().setPreferredSize(new Dimension(200, 20));
		this.panel.add(localJLabel, "center");
		this.panel.add(localFixedColumnScrollPane, "grow, h pref-20");
		this.panel.add(new StrandsLegendPanel(this.mainFrame), "gap push");
	}

	@Override
	protected void initOptionsUi() {
		super.initOptionsUi();
		this.cbShowUnusedPersons = new JCheckBox();
		this.cbShowUnusedPersons.setSelected(true);
		this.cbShowUnusedPersons.setText(I18N.getMsg("msg.chart.common.unused.characters"));
		this.cbShowUnusedPersons.setOpaque(false);
		this.cbShowUnusedPersons.addActionListener(this);
		JLabel localJLabel = new JLabel(I18N.getIcon("icon.small.size"));
		this.colSlider = SwingUtil.createSafeSlider(0, 5, 200, this.colWidth);
		this.colSlider.setMinorTickSpacing(1);
		this.colSlider.setMajorTickSpacing(2);
		this.colSlider.setSnapToTicks(false);
		this.colSlider.addChangeListener(this);
		this.colSlider.setOpaque(false);
		this.optionsPanel.add(this.cbShowUnusedPersons, "right,gap push");
		this.optionsPanel.add(localJLabel, "gap 20");
		this.optionsPanel.add(this.colSlider);
	}

	@Override
	public void refresh() {
		this.colWidth = this.colSlider.getValue();
		super.refresh();
		this.colSlider.setValue(this.colWidth);
		setTableColumnWidth();
	}

	private void setTableColumnWidth() {
		this.colWidth = this.colSlider.getValue();
		for (int i = 0; i < this.table.getColumnCount(); i++) {
			TableColumn localTableColumn = this.table.getColumnModel().getColumn(i);
			localTableColumn.setPreferredWidth(this.colWidth);
		}
	}

	@Override
	public void stateChanged(ChangeEvent paramChangeEvent) {
		setTableColumnWidth();
	}
}