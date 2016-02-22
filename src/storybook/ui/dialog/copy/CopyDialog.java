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
package storybook.ui.dialog.copy;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import org.hibernate.Session;

import net.miginfocom.swing.MigLayout;
import storybook.SbApp;
import storybook.SbConstants.BookKey;
import storybook.model.BookModel;
import storybook.model.EntityUtil;
import storybook.model.hbn.entity.AbstractEntity;
import storybook.model.hbn.entity.Attribute;
import storybook.toolkit.BookUtil;
import storybook.toolkit.I18N;
import storybook.toolkit.swing.SwingUtil;
import storybook.ui.MainFrame;
import storybook.ui.dialog.AbstractDialog;
import storybook.ui.edit.CbPanelDecorator;
import storybook.ui.edit.CheckBoxPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class CopyDialog.
 *
 * @author martin
 * @param <ELEMENT> the generic type
 */

public class CopyDialog<ELEMENT extends AbstractEntity> extends AbstractDialog
		implements ActionListener, CaretListener {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8732639478684951404L;

	/**
	 * The Class ProjectComboRenderer.
	 */
	class ProjectComboRenderer extends JLabel implements ListCellRenderer<MainFrame> {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 336144836913160735L;

		/**
		 * Instantiates a new project combo renderer.
		 */
		public ProjectComboRenderer() {
			super("");
			setOpaque(true);
		}

		/* (non-Javadoc)
		 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
		 */
		@Override
		public Component getListCellRendererComponent(JList<? extends MainFrame> list, MainFrame value, int index,
				boolean isSelected, boolean cellHasFocus) {
			if (value != null) {
				String title = BookUtil.get(value, BookKey.TITLE, "").getStringValue();
				if (title.isEmpty()) {
					title = value.getTitle();
				}
				setText(title);
			}
			return this;
		}
	}

	/** The project action. */
	private AbstractAction projectAction;
	
	/** The opened combo. */
	private JComboBox<MainFrame> openedCombo;
	
	/** The tabbed pane. */
	private JTabbedPane tabbedPane;

	/** The check. */
	private CheckBoxPanel check;

	/** The copier. */
	private AbstractCopier<ELEMENT> copier;

	/**
	 * Instantiates a new copy dialog.
	 *
	 * @param copier the copier
	 */
	public CopyDialog(AbstractCopier<ELEMENT> copier) {
		super(copier.getMainFrame());
		this.copier = copier;
		initAll();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

	}

	/**
	 * Adds the panel to tabbed.
	 *
	 * @param panel the panel
	 * @param I18NTitle the i18 n title
	 */
	protected void addPanelToTabbed(JPanel panel, String I18NTitle) {
		tabbedPane.addTab(I18N.getMsg(I18NTitle), panel);
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.CaretListener#caretUpdate(javax.swing.event.CaretEvent)
	 */
	@Override
	public void caretUpdate(CaretEvent e) {
	}

	/**
	 * Creates the destination panel.
	 *
	 * @return the j panel
	 */
	private JPanel createDestinationPanel() {
		MigLayout layout = new MigLayout("wrap 2", "", "[]10");
		JPanel panel = new JPanel(layout);
		panel.setBorder(BorderFactory.createTitledBorder(I18N.getMsg("msg.copy.destination")));

		// Opended projects
		JLabel lbopened = new JLabel(I18N.getMsgColon("msg.copy.opened.projects"));
		DefaultComboBoxModel<MainFrame> model = new DefaultComboBoxModel<MainFrame>();
//		for (MainFrame frame : SbApp.getInstance().getMainFrame()) {
//			if (frame != mainFrame) {
				model.addElement(mainFrame);
//			}
//		}
		openedCombo = new JComboBox<MainFrame>(model);
		openedCombo.setRenderer(new ProjectComboRenderer());

		JLabel lbOpenProject = new JLabel(" ");
		JButton openproject = new JButton();
		openproject.setAction(openProjectAction());
		openproject.setText(I18N.getMsgDot("msg.copy.open.project"));

		// layout
		panel.add(lbopened);
		panel.add(openedCombo);
		panel.add(lbOpenProject);
		panel.add(openproject);

		return panel;
	}

	/**
	 * Creates the entities panel.
	 *
	 * @return the j panel
	 */
	@SuppressWarnings("unchecked")
	private JPanel createEntitiesPanel() {
		MigLayout layout = new MigLayout();
		JPanel panel = new JPanel(layout);
		panel.setBorder(BorderFactory.createTitledBorder(I18N.getMsg("msg.copy.elements")));

		check = new CheckBoxPanel(mainFrame);
		JScrollPane scroller = new JScrollPane(check);
		SwingUtil.setUnitIncrement(scroller);
		SwingUtil.setMaxPreferredSize(scroller);
		panel.add(scroller, "grow");

		check.setAutoSelect(false);
		check.setEntityHandler(copier.getEntityHandler(mainFrame));
		CbPanelDecorator decorator = copier.getDecorator();
		if (decorator != null) {
			decorator.setPanel(check);
			check.setDecorator(decorator);
		}

		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		List<AbstractEntity> buf = new ArrayList<>();
		check.setEntityList((List<AbstractEntity>) copier.getAllElements(session, mainFrame));
		for (AbstractEntity entity : copier.getAllElements(session, mainFrame)) {
			check.addEntity(session, entity);
			buf.add(entity);
		}
		for (AbstractEntity entity : buf) {
			List<Attribute> attributes = EntityUtil.getEntityAttributes(mainFrame, entity);
			for (Attribute attr : attributes) {
				session = model.beginTransaction();
				session.refresh(attr);
				System.out.println(attr.getKey());
			}
		}
		check.initAll();

		return panel;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.AbstractDialog#getOkAction()
	 */
	@Override
	protected AbstractAction getOkAction() {
		return new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1890221357308041480L;

			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrame destination = (MainFrame) openedCombo.getSelectedItem();

				if (destination != null) {
					for (AbstractEntity elt : check.getSelectedEntities()) {
						copier.copy(destination, (ELEMENT) elt);
					}

					canceled = false;
					dispose();
				}
			}
		};
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.AbstractDialog#init()
	 */
	@Override
	public void init() {
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.AbstractDialog#initUi()
	 */
	@Override
	public void initUi() {
		super.initUi();
		setLayout(new MigLayout("wrap,fill", "", "[grow][]"));
		setTitle(I18N.getMsg("msg.copy.title"));
		setIconImage(I18N.getIconImage("icon.sb"));
		setPreferredSize(new Dimension(500, 600));

		JPanel panel = new JPanel(new MigLayout("flowy,fill"));
		panel.add(createDestinationPanel(), "growx");
		panel.add(createEntitiesPanel(), "growx");
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab(I18N.getMsg("msg.copy.global"), panel);

		// layout
		add(tabbedPane, "grow");
		add(getOkButton(), "split 2,sg,right");
		add(getCancelButton(), "sg");
	}

	/**
	 * Open project action.
	 *
	 * @return the abstract action
	 */
	public AbstractAction openProjectAction() {
		if (projectAction == null)
			projectAction = new AbstractAction() {
				/**
				 * 
				 */
				private static final long serialVersionUID = -5480124516688884632L;

				@Override
				public void actionPerformed(ActionEvent evt) {
					mainFrame.setWaitingCursor();
					SbApp.getInstance().openFile();

					// refresh combobox
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							DefaultComboBoxModel<MainFrame> model = (DefaultComboBoxModel<MainFrame>) openedCombo
									.getModel();
							// removing old data
							model.removeAllElements();

//							for (MainFrame frame : SbApp.getInstance().getMainFrames()) {
								System.out.println(mainFrame.getTitle());
//								if (mainFrame != mainFrame) {
									model.addElement(mainFrame);
//								}
//							}
							mainFrame.setDefaultCursor();
						}
					});
				}
			};
		return projectAction;
	}
}
