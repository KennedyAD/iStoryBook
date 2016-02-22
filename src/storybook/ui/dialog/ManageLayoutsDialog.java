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

package storybook.ui.dialog;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.hibernate.Session;

import net.miginfocom.swing.MigLayout;
import storybook.SbApp;
import storybook.SbConstants;
import storybook.SbConstants.PreferenceKey;
import storybook.model.PreferenceModel;
import storybook.model.hbn.dao.PreferenceDAOImpl;
import storybook.model.hbn.entity.Preference;
import storybook.toolkit.DockingWindowUtil;
import storybook.toolkit.I18N;
import storybook.toolkit.PrefUtil;
import storybook.toolkit.swing.SwingUtil;
import storybook.ui.MainFrame;

// TODO: Auto-generated Javadoc
/**
 * The Class ManageLayoutsDialog.
 *
 * @author martin
 */

public class ManageLayoutsDialog extends AbstractDialog {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2815245137577934302L;

	/**
	 * The Class NamePanel.
	 */
	private class NamePanel extends JPanel {
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = -107645165884561619L;
		
		/** The tf layout name. */
		private JTextField tfLayoutName;
		
		/** The cb delete. */
		private JCheckBox cbDelete;
		
		/** The orig key. */
		private String origKey;
		
		/** The orig name. */
		private String origName;

		/**
		 * Instantiates a new name panel.
		 *
		 * @param preference the preference
		 * @param number the number
		 */
		public NamePanel(Preference preference, int number) {
			origKey = preference.getKey();
			setLayout(new MigLayout("flowx,ins 0,fill", "[][grow][]"));
			JLabel lb = new JLabel(I18N.getMsg("msg.docking.layout", number) + ":");
			tfLayoutName = new JTextField(20);
			origName = preference.getStringValue();
			tfLayoutName.setText(origName);
			cbDelete = new JCheckBox();
			add(lb);
			add(tfLayoutName, "growx");
			add(cbDelete);
		}

		/**
		 * Gets the layout name.
		 *
		 * @return the layout name
		 */
		public String getLayoutName() {
			return tfLayoutName.getText();
		}

		/**
		 * Gets the orig key.
		 *
		 * @return the orig key
		 */
		public String getOrigKey() {
			return origKey;
		}

		/**
		 * Checks for changed.
		 *
		 * @return true, if successful
		 */
		public boolean hasChanged() {
			return !origName.equals(tfLayoutName.getText());
		}

		/**
		 * Checks if is selected for delete.
		 *
		 * @return true, if is selected for delete
		 */
		public boolean isSelectedForDelete() {
			return cbDelete.isSelected();
		}
	}

	/** The name panels. */
	private List<NamePanel> namePanels;

	/**
	 * Instantiates a new manage layouts dialog.
	 *
	 * @param mainFrame the main frame
	 */
	public ManageLayoutsDialog(MainFrame mainFrame) {
		super(mainFrame);
		initAll();
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
			private static final long serialVersionUID = -7416163973195407256L;

			@Override
			public void actionPerformed(ActionEvent evt) {
				renameOrDeleteLayouts();
				getThis().dispose();
			}
		};
	}

	/**
	 * Gets the this.
	 *
	 * @return the this
	 */
	private ManageLayoutsDialog getThis() {
		return this;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.AbstractDialog#init()
	 */
	@Override
	public void init() {
		namePanels = new ArrayList<ManageLayoutsDialog.NamePanel>();
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.AbstractDialog#initUi()
	 */
	@Override
	public void initUi() {
		super.initUi();
		setLayout(new MigLayout("wrap,fill", "", "[grow][]"));
		setPreferredSize(new Dimension(400, 300));
		setTitle(I18N.getMsg("msg.docking.manage.layouts"));

		JPanel panel = new JPanel();
		panel.setLayout(new MigLayout("wrap,fillx", "[grow]"));
		panel.add(new JLabel(I18N.getMsg("msg.common.delete")), "span,right");

		PreferenceModel model = SbApp.getInstance().getPreferenceModel();
		Session session = model.beginTransaction();
		PreferenceDAOImpl dao = new PreferenceDAOImpl(session);
		List<Preference> preferences = dao.findAll();
		int i = 1;
		for (Preference pref : preferences) {
			if (pref.getKey().startsWith(PreferenceKey.DOCKING_LAYOUT.toString())) {
				if (SbConstants.BookKey.LAST_USED_LAYOUT.toString().equals(pref.getStringValue())) {
					continue;
				}
				NamePanel namePanel = new NamePanel(pref, i);
				panel.add(namePanel, "growx");
				namePanels.add(namePanel);
				++i;
			}
		}
		model.commit();

		JScrollPane scroller = new JScrollPane(panel);
		SwingUtil.setUnitIncrement(scroller);
		add(scroller, "grow");
		add(getOkButton(), "span,split 2,sg,right");
		add(getCancelButton(), "sg");
	}

	/**
	 * Rename or delete layouts.
	 */
	private void renameOrDeleteLayouts() {
		for (NamePanel namePanel : namePanels) {
			String key = namePanel.getOrigKey();
			if (namePanel.isSelectedForDelete()) {
				PrefUtil.delete(key);
			} else if (namePanel.hasChanged()) {
				Preference pref = PrefUtil.get(key, null);
				byte[] ba = pref.getBinValue();
				String name = namePanel.getLayoutName();
				String newKey = DockingWindowUtil.getStrKey(name);
				PrefUtil.set(newKey, name);
				PrefUtil.set(newKey, ba);
				PrefUtil.delete(key);
			}
		}
		SbApp.getInstance().reloadMenuBars();
		SbApp.getInstance().reloadStatusBars();
	}
}
