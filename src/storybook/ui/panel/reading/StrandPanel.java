package storybook.ui.panel.reading;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

import org.hibernate.Session;

import net.miginfocom.swing.MigLayout;
import storybook.controller.BookController;
import storybook.model.BookModel;
import storybook.model.hbn.dao.StrandDAOImpl;
import storybook.model.hbn.entity.Strand;
import storybook.toolkit.I18N;
import storybook.toolkit.swing.SwingUtil;
import storybook.ui.MainFrame;
import storybook.ui.panel.AbstractPanel;


// TODO: Auto-generated Javadoc
/**
 * The Class StrandPanel.
 */
public class StrandPanel extends AbstractPanel implements ItemListener {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1813889591695226194L;
	
	/** The main frame. */
	private MainFrame mainFrame;
	
	/** The book reading. */
	private ReadingPanel bookReading;
	
	/** The strand ids. */
	private HashSet<Long> strandIds;
	
	/** The cb list. */
	private List<JCheckBox> cbList;

	/**
	 * Instantiates a new strand panel.
	 *
	 * @param mainFrame the main frame
	 * @param booReading the boo reading
	 */
	public StrandPanel(MainFrame mainFrame, ReadingPanel booReading) {
		this.mainFrame = mainFrame;
		this.bookReading = booReading;
	}

	/**
	 * Adds the all strands.
	 */
	private void addAllStrands() {
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		StrandDAOImpl dao = new StrandDAOImpl(session);
		List<Strand> list = dao.findAll();
		for (Strand strand : list) {
			strandIds.add(strand.getId());
		}
	}

	/**
	 * Gets the select all action.
	 *
	 * @return the select all action
	 */
	private AbstractAction getSelectAllAction() {
		return new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 5363619007885680963L;

			@Override
			public void actionPerformed(ActionEvent e) {
				addAllStrands();
				for (JCheckBox cb : cbList) {
					cb.setSelected(true);
				}
				bookReading.refresh();
			}
		};
	}

	/**
	 * Gets the select none action.
	 *
	 * @return the select none action
	 */
	private AbstractAction getSelectNoneAction() {
		return new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -2033211094663867325L;

			@Override
			public void actionPerformed(ActionEvent e) {
				strandIds.clear();
				for (JCheckBox cb : cbList) {
					cb.setSelected(false);
				}
				bookReading.refresh();
			}
		};
	}

	/**
	 * Gets the strand ids.
	 *
	 * @return the strand ids
	 */
	public HashSet<Long> getStrandIds() {
		return strandIds;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#init()
	 */
	@Override
	public void init() {
		strandIds = new HashSet<Long>();
		cbList = new ArrayList<JCheckBox>();
		addAllStrands();
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#initUi()
	 */
	@Override
	public void initUi() {
		setLayout(new MigLayout("wrap"));
		setBackground(Color.white);
		setBorder(SwingUtil.getBorderDefault());

		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		StrandDAOImpl dao = new StrandDAOImpl(session);
		List<Strand> list = dao.findAll();
		for (Strand strand : list) {
			JCheckBox cb = new JCheckBox(strand.toString());
			cbList.add(cb);
			long id = strand.getId();
			if (strandIds.contains(id)) {
				cb.setSelected(true);
			}
			cb.setName(Long.toString(id));
			cb.setOpaque(false);
			cb.addItemListener(this);
			add(new JLabel(strand.getColorIcon()), "split 2");
			add(cb);
		}

		JButton btAll = new JButton(getSelectAllAction());
		btAll.setText(I18N.getMsg("msg.tree.show.all"));
		btAll.setName("all");
		btAll.setOpaque(false);
		add(btAll, "sg,gapy 20");

		JButton cbNone = new JButton(getSelectNoneAction());
		cbNone.setText(I18N.getMsg("msg.tree.show.none"));
		cbNone.setName("none");
		cbNone.setOpaque(false);
		add(cbNone, "sg");
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		try {
			JCheckBox cb = (JCheckBox) e.getSource();
			Long id = new Long(cb.getName());
			if (cb.isSelected()) {
				strandIds.add(id);
			} else {
				strandIds.remove(id);
			}
			bookReading.refresh();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#modelPropertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void modelPropertyChange(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();
		if (BookController.StrandProps.UPDATE.check(propName)) {
			refresh();
		}
	}
}
