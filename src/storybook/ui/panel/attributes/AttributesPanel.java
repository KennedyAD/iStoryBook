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

package storybook.ui.panel.attributes;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;

import org.hibernate.Session;

import net.miginfocom.swing.MigLayout;
import storybook.model.BookModel;
import storybook.model.hbn.dao.AttributeDAOImpl;
import storybook.model.hbn.entity.Attribute;
import storybook.toolkit.I18N;
import storybook.toolkit.swing.IconButton;
import storybook.ui.MainFrame;
import storybook.ui.panel.AbstractPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class AttributesPanel.
 *
 * @author martin
 */

public class AttributesPanel extends AbstractPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3362227249416174850L;

	/**
	 * The Class RemoveAction.
	 */
	class RemoveAction extends AbstractAction {
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = -1888784236625706866L;
		
		/** The bt. */
		private JButton bt;
		
		/** The panel. */
		private AttributePanel panel;

		/**
		 * Instantiates a new removes the action.
		 *
		 * @param bt the bt
		 * @param panel the panel
		 */
		public RemoveAction(JButton bt, AttributePanel panel) {
			this.bt = bt;
			this.panel = panel;
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			attrPanels.remove(panel);
			remove(bt);
			remove(panel);
			revalidate();
			repaint();
		}
	}
	
	/** The attributes. */
	private List<Attribute> attributes;
	
	/** The attr panels. */
	private List<AttributePanel> attrPanels;

	/** The keys. */
	private List<String> keys;

	/**
	 * Instantiates a new attributes panel.
	 *
	 * @param mainFrame the main frame
	 */
	public AttributesPanel(MainFrame mainFrame) {
		super(mainFrame);
	}

	/**
	 * Gets the adds the action.
	 *
	 * @return the adds the action
	 */
	public AbstractAction getAddAction() {
		return new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 926881721042296161L;

			@Override
			public void actionPerformed(ActionEvent e) {
				AttributePanel panel = new AttributePanel(keys);
				attrPanels.add(panel);
				add(panel, getComponentCount() - 1);
				add(getRemoveButton(panel), getComponentCount() - 1);
				revalidate();
				repaint();
			}
		};
	}

	/**
	 * Gets the attributes.
	 *
	 * @return the attributes
	 */
	public List<Attribute> getAttributes() {
		List<Attribute> attributes = new ArrayList<Attribute>();
		for (AttributePanel panel : attrPanels) {
			Attribute attr = panel.getAttribute();
			if (attr == null) {
				continue;
			}
			attributes.add(attr);
		}
		return attributes;
	}

	/**
	 * Gets the removes the button.
	 *
	 * @param panel the panel
	 * @return the removes the button
	 */
	private IconButton getRemoveButton(AttributePanel panel) {
		IconButton bt = new IconButton();
		RemoveAction act = new RemoveAction(bt, panel);
		bt.setAction(act);
		bt.setIcon(I18N.getIcon("icon.small.minus"));
		bt.setSize20x20();
		bt.setFlat();
		return bt;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#init()
	 */
	@Override
	public void init() {
		attrPanels = new ArrayList<AttributePanel>();
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#initUi()
	 */
	@Override
	public void initUi() {
		setLayout(new MigLayout("wrap 2,fillx", "[grow][]", ""));

		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		AttributeDAOImpl dao = new AttributeDAOImpl(session);
		keys = dao.findKeys();

		for (Attribute attribute : attributes) {
			session.refresh(attribute);
			AttributePanel panel = new AttributePanel(attribute, keys);
			attrPanels.add(panel);
			add(panel);
			add(getRemoveButton(panel));
		}

		AttributePanel newAttrPanel = new AttributePanel(keys);
		attrPanels.add(newAttrPanel);
		add(newAttrPanel);
		add(getRemoveButton(newAttrPanel));

		model.commit();

		IconButton btAdd = new IconButton(getAddAction());
		btAdd.setText(I18N.getMsg("msg.common.add"));
		btAdd.setIcon(I18N.getIcon("icon.small.plus"));
		add(btAdd, "newline,span,gap 0 0 10 0");
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#modelPropertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void modelPropertyChange(PropertyChangeEvent evt) {
	}

	/**
	 * Sets the attributes.
	 *
	 * @param attributes the new attributes
	 */
	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}
}
