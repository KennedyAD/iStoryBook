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

package storybook.ui.panel.manage.dnd;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;

import javax.accessibility.Accessible;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import storybook.SbApp;
import storybook.SbConstants;
import storybook.SbConstants.BookKey;
import storybook.action.EditEntityAction;
import storybook.model.EntityUtil;
import storybook.model.hbn.entity.Internal;
import storybook.model.hbn.entity.Scene;
import storybook.toolkit.BookUtil;
import storybook.toolkit.I18N;
import storybook.toolkit.html.HtmlUtil;
import storybook.toolkit.swing.ColorUtil;
import storybook.toolkit.swing.SwingUtil;
import storybook.ui.MainFrame;
import storybook.ui.interfaces.IRefreshable;
import storybook.ui.panel.AbstractScenePanel;


// TODO: Auto-generated Javadoc
/**
 * The Class ScenePanel.
 */
public class ScenePanel extends AbstractScenePanel implements MouseListener, Accessible, IRefreshable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7141680768286952990L;
	
	/** The Constant TYPE_NONE. */
	public final static int TYPE_NONE = 0;
	
	/** The Constant TYPE_UNASSIGNED. */
	public final static int TYPE_UNASSIGNED = 1;
	
	/** The Constant TYPE_MAKE_UNASSIGNED. */
	public final static int TYPE_MAKE_UNASSIGNED = 2;
	
	/** The Constant TYPE_BEGIN. */
	public final static int TYPE_BEGIN = 3;
	
	/** The Constant TYPE_NEXT. */
	public final static int TYPE_NEXT = 4;

	/** The type. */
	private int type = TYPE_NONE;
	
	/** The lb scene. */
	// private Scene scene;
	private JLabel lbScene;
	
	/** The text length. */
	private int textLength;

	/**
	 * Instantiates a new scene panel.
	 *
	 * @param mainFrame the main frame
	 * @param scene the scene
	 */
	public ScenePanel(MainFrame mainFrame, Scene scene) {
		this(mainFrame, scene, TYPE_NONE);
		SbApp.trace("ScenePanel(" + mainFrame.getName() + ", " + scene.getFullTitle() + ", " + type);
	}

	/**
	 * Instantiates a new scene panel.
	 *
	 * @param mainFrame the main frame
	 * @param scene the scene
	 * @param type the type
	 */
	public ScenePanel(MainFrame mainFrame, Scene scene, int type) {
		super(mainFrame, scene);
		SbApp.trace("ScenePanel(" + mainFrame.getName() + ", " + ((scene != null) ? scene.getFullTitle() : "null")
				+ ", " + type + ")");
		this.scene = scene;
		this.type = type;
		initAll();
		setFocusable(true);
		addMouseListener(this);
	}

	/**
	 * Gets the this.
	 *
	 * @return the this
	 */
	protected ScenePanel getThis() {
		return this;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#init()
	 */
	@Override
	public void init() {
		SbApp.trace("ScenePanel.init()");
		try {
			Internal internal = BookUtil.get(mainFrame, BookKey.MANAGE_ZOOM, SbConstants.DEFAULT_MANAGE_ZOOM);
			setZoomedSize(internal.getIntegerValue());
		} catch (Exception e) {
			setZoomedSize(SbConstants.DEFAULT_MANAGE_ZOOM);
		}
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#initUi()
	 */
	@Override
	public void initUi() {
		SbApp.trace("ScenePanel.initUI()");
		setOpaque(true);

		if (type == TYPE_MAKE_UNASSIGNED) {
			setBackground(ColorUtil.getNiceDarkGray());
			setMinimumSize(new Dimension(30, 25));
			return;
		}

		if (scene == null) {
			setBackground(ColorUtil.getNiceDarkGray());
			setMinimumSize(new Dimension(30, 15));
			return;
		}

		MigLayout layout = new MigLayout("flowx,ins 4", "", "");
		setLayout(layout);
		setBorder(SwingUtil.getBorderDefault());
		if (scene.getInformative()) {
			setBackground(Color.white);
		} else {
			Color clr = ColorUtil.getPastel2(scene.getStrand().getJColor());
			setBackground(clr);
		}
		setComponentPopupMenu(EntityUtil.createPopupMenu(mainFrame, scene));

		JLabel lbState = new JLabel(scene.getStatusIcon());

		JLabel lbInformational = new JLabel("");
		if (scene.getInformative()) {
			lbInformational.setIcon(I18N.getIcon("icon.small.info"));
		}

		lbScene = new JLabel();
		StringBuilder buf = new StringBuilder();
		buf.append(scene.getChapterSceneNo(true));
		String titleText = scene.getTitleText(true, textLength);
		buf.append(HtmlUtil.htmlToText(titleText));
		lbScene.setText("<html>" + buf.toString() + "</html>");

		// doesn't work, drag-n-drop runs into troubles
		// lbScene.setToolTipText(scene.getTitle());

		// layout
		JPanel panel = new JPanel(new MigLayout("flowy,ins 0"));
		panel.setOpaque(false);
		panel.add(lbState);
		panel.add(lbInformational);
		add(panel, "top");
		add(lbScene, "top,growx");
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractGradientPanel#modelPropertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void modelPropertyChange(PropertyChangeEvent evt) {
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent evt) {
		if (scene == null) {
			return;
		}
		requestFocusInWindow();
		if (evt.getClickCount() == 2) {
			EditEntityAction act = new EditEntityAction(mainFrame, scene, false);
			act.actionPerformed(null);
			return;
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		// if (type == TYPE_NONE || type == TYPE_UNASSIGNED) {
		// Color strandColor = scene.getStrand().getColor();
		// if (ColorUtil.isDark(strandColor)) {
		// lbScene.setForeground(Color.white);
		// }
		// setBackground(strandColor);
		// }
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		// if (type == TYPE_NONE || type == TYPE_UNASSIGNED) {
		// Color clr = ColorUtil.getPastel2(scene.getStrand().getColor());
		// setBackground(clr);
		// lbScene.setForeground(Color.black);
		// }
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

	/**
	 * Sets the zoomed size.
	 *
	 * @param zoomValue the new zoomed size
	 */
	private void setZoomedSize(int zoomValue) {
		textLength = 10 + zoomValue * 8;
	}
}
