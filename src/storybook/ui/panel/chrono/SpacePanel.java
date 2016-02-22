/*
Storybook: Scene-based software for novelists and authors.
Copyright (C) 2008 - 2011 Martin Mustun

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

package storybook.ui.panel.chrono;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.sql.Timestamp;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;

import net.miginfocom.swing.MigLayout;
import storybook.controller.BookController;
import storybook.model.EntityUtil;
import storybook.model.hbn.entity.Scene;
import storybook.model.hbn.entity.Strand;
import storybook.toolkit.I18N;
import storybook.toolkit.swing.IconButton;
import storybook.ui.MainFrame;
import storybook.ui.panel.AbstractPanel;


// TODO: Auto-generated Javadoc
/**
 * The Class SpacePanel.
 */
public class SpacePanel extends AbstractPanel implements MouseListener {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1396690420308099014L;
	
	/** The strand. */
	private Strand strand = null;
	
	/** The date. */
	private Date date = null;
	
	/** The new scene action. */
	private AbstractAction newSceneAction;

	/**
	 * Instantiates a new space panel.
	 *
	 * @param mainFrame the main frame
	 * @param strand the strand
	 * @param date the date
	 */
	public SpacePanel(MainFrame mainFrame, Strand strand, Date date) {
		super(mainFrame);
		this.strand = strand;
		if (date instanceof Timestamp) {
			this.date = new Date(date.getTime());
		} else {
			this.date = date;
		}
		refresh();
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Gets the new action.
	 *
	 * @return the new action
	 */
	private AbstractAction getNewAction() {
		if (newSceneAction == null) {
			newSceneAction = new AbstractAction() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 601244899023520643L;

				@Override
				public void actionPerformed(ActionEvent e) {
					BookController ctrl = mainFrame.getBookController();
					Scene scene = new Scene();
					scene.setStrand(strand);
					scene.setDate(date);
					ctrl.setSceneToEdit(scene);
					// mainFrame.showView(ViewName.EDITOR);
					mainFrame.showEditorAsDialog(scene);
				}
			};
		}
		return newSceneAction;
	}

	/**
	 * Gets the strand.
	 *
	 * @return the strand
	 */
	public Strand getStrand() {
		return strand;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#init()
	 */
	@Override
	public void init() {
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#initUi()
	 */
	@Override
	public void initUi() {
		setFocusable(true);
		setMinimumSize(new Dimension(150, 150));
		addMouseListener(this);

		Color color = Color.gray;
		if (strand != null) {
			color = strand.getJColor();
		}
		MigLayout layout = new MigLayout("fill", "[center]", "[center]");
		setLayout(layout);
		setBorder(BorderFactory.createLineBorder(color, 2));
		setOpaque(false);

		IconButton btNewScene = new IconButton("icon.small.plus", getNewAction());
		btNewScene.setFlat();
		btNewScene.setToolTipText(I18N.getMsg("msg.space.panel.add.new"));
		add(btNewScene, "ax center");
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#modelPropertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void modelPropertyChange(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();
		if (BookController.StrandProps.UPDATE.check(propName)) {
			EntityUtil.refresh(mainFrame, strand);
			refresh();
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			newSceneAction.actionPerformed(null);
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
