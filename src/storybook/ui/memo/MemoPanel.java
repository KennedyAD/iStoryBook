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
import net.infonode.docking.View;
import org.hibernate.Session;
import org.miginfocom.swing.MigLayout;
import storybook.SbConstants;
import storybook.controller.BookController;
import storybook.model.BookModel;
import storybook.model.DbFile;
import storybook.model.EntityUtil;
import storybook.model.hbn.dao.TagDAOImpl;
import storybook.model.hbn.entity.AbstractEntity;
import storybook.model.hbn.entity.Internal;
import storybook.model.hbn.entity.Scene;
import storybook.model.hbn.entity.Tag;
import storybook.toolkit.BookUtil;
import storybook.toolkit.I18N;
import storybook.toolkit.net.NetUtil;
import storybook.toolkit.swing.SwingUtil;
import storybook.ui.MainFrame;
import storybook.ui.memoria.EntityTypeCbItem;
import storybook.ui.panel.AbstractPanel;

/**
 *
 * @author favdb
 */
public class MemoPanel extends AbstractPanel implements ActionListener, ListSelectionListener, HyperlinkListener {
	private JComboBox memoCombo;
	private JButton btNew;
	private JButton btDelete;
	private JButton btEdit;
	private boolean processActionListener;
	private JPanel controlPanel;
	private JTextPane infoPanel;
	private Tag currentMemo;

	public MemoPanel(MainFrame mainFrame) {
		super(mainFrame);
	}
	@Override
	public void init() {
		/* disposition
		- liste deroulante memoList
		-- find all tag avec type == 20
		-- ajout dans la liste deroulante
		- bouton de modification du memo affiché btMod
		- bouton de creation d'un nouveau memo btDel
		- affichage du memo selectionne
		*/
	}

	@Override
	public void initUi() {
		MigLayout migLayout1 = new MigLayout("wrap,fill", "[]", "[][grow]");
		setLayout(migLayout1);
		setBackground(SwingUtil.getBackgroundColor());
		controlPanel = new JPanel();
		MigLayout migLayout2 = new MigLayout("flowx", "", "");
		controlPanel.setLayout(migLayout2);
		controlPanel.setOpaque(false);
		
		memoCombo = new JComboBox();
		memoCombo.setName(SbConstants.ComponentName.COMBO_ENTITIES.toString());
		memoCombo.setMaximumRowCount(15);
		if (memoCombo != null) {
			refreshMemoCombo();
		}
		add(controlPanel, "alignx center");
		controlPanel.removeAll();
		controlPanel.add(memoCombo, "gapafter 32");
		controlPanel.revalidate();
		controlPanel.repaint();
		memoCombo.addActionListener(this);
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

		if (BookController.CommonProps.SHOW_INFO.check(propName)) {
			if (newValue instanceof AbstractEntity) {
				currentMemo = (Tag) newValue;
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

	@Override
	public void hyperlinkUpdate(HyperlinkEvent e) {
		try {
			if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
				NetUtil.openBrowser(e.getURL().toString());
			}
		} catch (Exception exc) {
			System.err.println("InfoPanel.hyperlinkUpdate("+e.toString()+") Exception : "+exc.getMessage());
		}
	}

	private void refreshMemoCombo() {
		Object entityComboSelected = null;
		if (memoCombo != null) {
			entityComboSelected = memoCombo.getSelectedItem();
		}
		//entityCombo.removeAll();
		// faire la liste
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		TagDAOImpl dao=new TagDAOImpl(session);
		List<Tag> tags=dao.findAllMemo();
		model.commit();
		processActionListener = false;
		DefaultComboBoxModel combo = (DefaultComboBoxModel) memoCombo.getModel();
		combo.removeAllElements();
		for (Tag tag : tags) {
			combo.addElement(tag);
		}
		processActionListener = true;
		memoCombo.setSelectedItem(entityComboSelected);
		
		btNew = new JButton(I18N.getMsg("msg.common.new"));
		btNew.setIcon(I18N.getIcon("icon.small.new"));
		btNew.setName(SbConstants.ComponentName.BT_NEW.toString());
		btNew.addActionListener(this);
		
		btEdit = new JButton(I18N.getMsg("msg.common.edit"));
		btEdit.setIcon(I18N.getIcon("icon.small.edit"));
		btEdit.setName(SbConstants.ComponentName.BT_EDIT.toString());
		btEdit.addActionListener(this);
		btEdit.setEnabled(false);

	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if ((evt.getSource() == null) || (!processActionListener)) {
			return;
		}
		if (evt.getSource() instanceof JButton) {
			String buttonString = ((JButton) evt.getSource()).getName();
			if (SbConstants.ComponentName.BT_EDIT.check(buttonString)) {
				return;
			} else if (SbConstants.ComponentName.BT_NEW.check(buttonString)) {
				return;
			}
		}
		refreshMemo();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		refreshMemo();
	}

	private void refreshMemo() {
		infoPanel.setText(currentMemo.getNotes());
		infoPanel.setCaretPosition(0);
	}
	
}
