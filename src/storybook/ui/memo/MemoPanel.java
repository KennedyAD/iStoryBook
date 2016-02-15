/*
 * Copyright (C) 2015 favdb
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package storybook.ui.memo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.hibernate.Session;

import net.infonode.docking.View;
import net.miginfocom.swing.MigLayout;
import storybook.SbApp;
import storybook.SbConstants;
import storybook.controller.BookController;
import storybook.model.BookModel;
import storybook.model.hbn.dao.MemoDAOImpl;
import storybook.model.hbn.entity.AbstractEntity;
import storybook.model.hbn.entity.Memo;
import storybook.toolkit.I18N;
import storybook.toolkit.net.NetUtil;
import storybook.toolkit.swing.SwingUtil;
import storybook.ui.MainFrame;
import storybook.ui.panel.AbstractPanel;

/**
 *
 * @author favdb
 */
public class MemoPanel extends AbstractPanel implements ActionListener, ListSelectionListener, HyperlinkListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 460674309102964462L;
	private JComboBox<Memo> memoCombo;// liste deroulante des memos
	private JButton btNew;// bouton nouveau
	private JButton btDelete;// bouton supprimer
	private JButton btEdit;// bouton modifier
	private boolean processActionListener;// listener à ignorer
	private JPanel controlPanel;// le panneau de controle contient la liste
								// deroulante et les boutons
	private JTextPane infoPanel;// le panneau d'information
	private Memo currentMemo;// le memo actuellement affiché

	public MemoPanel(MainFrame mainFrame) {
		super(mainFrame);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if ((evt.getSource() == null) || (!processActionListener)) {
			return;
		}
		SbApp.trace("MemoPanel.actionPerformed(" + evt.toString() + ")");
		if (evt.getSource() instanceof JButton) {
			String buttonString = ((JButton) evt.getSource()).getName();
			if (SbConstants.ComponentName.BT_EDIT.check(buttonString)) {
				mainFrame.showEditorAsDialog((Memo) memoCombo.getSelectedItem());
				return;
			} else if (SbConstants.ComponentName.BT_NEW.check(buttonString)) {
				mainFrame.showEditorAsDialog(new Memo());
				return;
			} else if (SbConstants.ComponentName.BT_DELETE.check(buttonString)) {
				return;
			}
		}
		currentMemo = (Memo) memoCombo.getSelectedItem();
		refreshMemo();
	}

	@Override
	public void hyperlinkUpdate(HyperlinkEvent e) {
		SbApp.trace("MemoPanel.hyperlinkIpdate(...)");
		try {
			if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
				NetUtil.openBrowser(e.getURL().toString());
			}
		} catch (Exception exc) {
			System.err.println("InfoPanel.hyperlinkUpdate(" + e.toString() + ") Exception : " + exc.getMessage());
		}
	}

	@Override
	public void init() {
		SbApp.trace("MemoPanel.init()");
		/*
		 * disposition - liste deroulante memoCombo btEdit btDelete btNew -
		 * affichage du memo selectionne
		 */
		currentMemo = null;
	}

	@Override
	public void initUi() {
		SbApp.trace("MemoPanel.initUi()");
		MigLayout migLayout1 = new MigLayout("wrap,fill", "[]", "[][grow]");
		setLayout(migLayout1);
		setBackground(SwingUtil.getBackgroundColor());
		controlPanel = new JPanel();
		MigLayout migLayout2 = new MigLayout("flowx", "", "");
		controlPanel.setLayout(migLayout2);
		controlPanel.setOpaque(false);
		refreshControlPanel();

		add(controlPanel, "alignx center");

		infoPanel = new JTextPane();
		infoPanel.setEditable(false);
		infoPanel.setOpaque(true);
		infoPanel.setContentType("text/html");
		infoPanel.addHyperlinkListener(this);
		JScrollPane scroller = new JScrollPane(infoPanel);
		SwingUtil.setMaxPreferredSize(scroller);
		add(scroller);
	}

	@Override
	public void modelPropertyChange(PropertyChangeEvent evt) {
		SbApp.trace("MemoPanel.modelPropertyChange(..)");
		Object newValue = evt.getNewValue();
		String propName = evt.getPropertyName();

		if (BookController.CommonProps.REFRESH.check(propName)) {
			View newView = (View) newValue;
			View view = (View) getParent().getParent();
			if (view == newView) {
				refresh();
			}
			return;
		}

		if (BookController.CommonProps.SHOW_MEMO.check(propName)) {
			if (newValue instanceof AbstractEntity) {
				currentMemo = (Memo) newValue;
				if (currentMemo.isTransient()) {
					return;
				}
				BookModel model = mainFrame.getBookModel();
				Session session = model.beginTransaction();
				session.refresh(currentMemo);
				model.commit();
				refreshMemo();
				return;
			}
		}

		if (currentMemo != null && newValue instanceof AbstractEntity) {
			AbstractEntity updatedEntity = (AbstractEntity) newValue;
			if (updatedEntity.getId().equals(currentMemo.getId())) {
				refreshMemo();
			}
		}
	}

	private void refreshControlPanel() {
		SbApp.trace("MemoPanel.refreshControlPanel()");
		memoCombo = new JComboBox<Memo>();
		memoCombo.setName(SbConstants.ComponentName.COMBO_ENTITIES.toString());
		memoCombo.setMaximumRowCount(15);
		if (memoCombo != null) {
			refreshMemoCombo();
		}
		add(controlPanel, "alignx center");
		controlPanel.removeAll();
		controlPanel.add(memoCombo, "gapafter 32");
		memoCombo.addActionListener(this);

		btNew = new JButton(/* I18N.getMsg("msg.common.new") */);
		btNew.setIcon(I18N.getIcon("icon.small.new"));
		btNew.setName(SbConstants.ComponentName.BT_NEW.toString());
		btNew.addActionListener(this);
		controlPanel.add(btNew);

		btEdit = new JButton(/* I18N.getMsg("msg.common.edit") */);
		btEdit.setIcon(I18N.getIcon("icon.small.edit"));
		btEdit.setName(SbConstants.ComponentName.BT_EDIT.toString());
		btEdit.addActionListener(this);
		btEdit.setEnabled(false);
		controlPanel.add(btEdit);

		btDelete = new JButton(/* I18N.getMsg("msg.common.delete") */);
		btDelete.setIcon(I18N.getIcon("icon.small.delete"));
		btDelete.setName(SbConstants.ComponentName.BT_DELETE.toString());
		btDelete.addActionListener(this);
		btDelete.setEnabled(false);
		controlPanel.add(btDelete);

		controlPanel.revalidate();
		controlPanel.repaint();
	}

	private void refreshMemo() {
		SbApp.trace("MemoPanel.refreshMemo(" + (currentMemo != null ? currentMemo.toString() : "null") + ")");
		if (currentMemo == null) {
			infoPanel.setText("");
			btEdit.setEnabled(false);
			btDelete.setEnabled(false);
		} else {
			infoPanel.setText(currentMemo.getNotes());
			btEdit.setEnabled(true);
			btDelete.setEnabled(true);
		}
		infoPanel.setCaretPosition(0);
	}

	private void refreshMemoCombo() {
		SbApp.trace("MemoPanel.refreshMemoCombo()");
		Object entityComboSelected = null;
		if (memoCombo != null) {
			entityComboSelected = memoCombo.getSelectedItem();
		}

		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		MemoDAOImpl dao = new MemoDAOImpl(session);
		List<Memo> memos = dao.findAll();
		model.commit();
		processActionListener = false;
		DefaultComboBoxModel<Memo> combo = (DefaultComboBoxModel<Memo>) memoCombo.getModel();
		combo.removeAllElements();
		for (Memo memo : memos) {
			combo.addElement(memo);
		}
		processActionListener = true;
		memoCombo.setSelectedItem(entityComboSelected);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		SbApp.trace("MemoPanel.valueChanged(...)");
		currentMemo = (Memo) memoCombo.getSelectedItem();
		refreshMemo();
	}

}
