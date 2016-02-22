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

package storybook.ui.dialog.rename;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;
import storybook.toolkit.I18N;
import storybook.toolkit.swing.SwingUtil;
import storybook.ui.MainFrame;
import storybook.ui.dialog.AbstractDialog;


// TODO: Auto-generated Javadoc
/**
 * The Class AbstractRenameDialog.
 */
abstract public class AbstractRenameDialog extends AbstractDialog implements ActionListener {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7481223379226051389L;
	
	/** The tf new name. */
	private JTextField tfNewName;
	
	/** The combo. */
	// protected MainFrame mainFrame;
	protected JComboBox<String> combo;

	/**
	 * Instantiates a new abstract rename dialog.
	 *
	 * @param mainFrame the main frame
	 */
	public AbstractRenameDialog(MainFrame mainFrame) {
		super(mainFrame);
		this.mainFrame = mainFrame;
		initAll();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox<String> cb = (JComboBox<String>) e.getSource();
		String val = (String) cb.getSelectedItem();
		tfNewName.setText(val);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				tfNewName.requestFocusInWindow();
				tfNewName.selectAll();
			}
		});
	}

	/**
	 * Creates the category combo.
	 *
	 * @param list the list
	 * @return the j combo box
	 */
	private JComboBox<String> createCategoryCombo(List<String> list) {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		for (String category : list) {
			model.addElement(category);
		}
		JComboBox<String> cob = new JComboBox<String>();
		cob.setModel(model);
		return cob;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.AbstractDialog#getCancelAction()
	 */
	@Override
	protected AbstractAction getCancelAction() {
		return new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -4028918884414175835L;

			@Override
			public void actionPerformed(ActionEvent evt) {
				getThis().dispose();
			}
		};
	}

	/**
	 * Gets the dlg title.
	 *
	 * @return the dlg title
	 */
	abstract protected String getDlgTitle();

	/**
	 * Gets the list.
	 *
	 * @return the list
	 */
	abstract protected List<String> getList();

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.AbstractDialog#getOkAction()
	 */
	@Override
	protected AbstractAction getOkAction() {
		return new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -3838335759831382616L;

			@Override
			public void actionPerformed(ActionEvent evt) {
				String oldValue = (String) combo.getSelectedItem();
				String newValue = tfNewName.getText();
				rename(oldValue, newValue);
				getThis().dispose();
			}
		};
	}

	/**
	 * Gets the this.
	 *
	 * @return the this
	 */
	private AbstractRenameDialog getThis() {
		return this;
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
		MigLayout layout = new MigLayout("wrap 4", "[]", "[]20[]");
		setLayout(layout);

		setTitle(getDlgTitle());

		List<String> list = getList();
		combo = createCategoryCombo(list);
		combo.addActionListener(this);

		JLabel lbRename = new JLabel(I18N.getMsg("msg.rename.rename"));
		JLabel lbTo = new JLabel(I18N.getMsg("msg.rename.to"));
		tfNewName = new JTextField(20);
		String val = (String) combo.getSelectedItem();
		tfNewName.setText(val);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				tfNewName.requestFocusInWindow();
				tfNewName.selectAll();
			}
		});

		// OK button
		JButton btOk = new JButton();
		btOk.setAction(getOkAction());
		SwingUtil.addEnterAction(btOk, getOkAction());
		btOk.setText(I18N.getMsg("msg.common.ok"));
		btOk.setIcon(I18N.getIcon("icon.small.ok"));

		// cancel button
		JButton btCancel = new JButton();
		btCancel.setAction(getCancelAction());
		SwingUtil.addEscAction(btCancel, getCancelAction());
		btCancel.setText(I18N.getMsg("msg.common.cancel"));
		btCancel.setIcon(I18N.getIcon("icon.small.close"));

		add(lbRename);
		add(combo);
		add(lbTo);
		add(tfNewName);
		add(btOk, "sg,span,split 2,right");
		add(btCancel, "sg");
	}

	/**
	 * Rename.
	 *
	 * @param oldValue the old value
	 * @param newValue the new value
	 */
	abstract protected void rename(String oldValue, String newValue);

	/**
	 * Sets the selected item.
	 *
	 * @param obj the new selected item
	 */
	public void setSelectedItem(Object obj) {
		combo.setSelectedItem(obj);
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(String value) {
		combo.setSelectedItem(value);
	}
}
