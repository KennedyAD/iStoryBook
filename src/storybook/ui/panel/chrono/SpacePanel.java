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

@SuppressWarnings("serial")
public class SpacePanel extends AbstractPanel implements MouseListener {

	private Strand strand = null;
	private Date date = null;
	private AbstractAction newSceneAction;

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

	public Date getDate() {
		return date;
	}

	private AbstractAction getNewAction() {
		if (newSceneAction == null) {
			newSceneAction = new AbstractAction() {
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

	public Strand getStrand() {
		return strand;
	}

	@Override
	public void init() {
	}

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

	@Override
	public void modelPropertyChange(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();
		if (BookController.StrandProps.UPDATE.check(propName)) {
			EntityUtil.refresh(mainFrame, strand);
			refresh();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			newSceneAction.actionPerformed(null);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
