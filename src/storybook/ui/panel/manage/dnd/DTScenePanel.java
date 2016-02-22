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

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import storybook.SbApp;
import storybook.model.hbn.entity.Scene;
import storybook.ui.MainFrame;


// TODO: Auto-generated Javadoc
/**
 * The Class DTScenePanel.
 */
public class DTScenePanel extends ScenePanel implements MouseMotionListener {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4212133801662362621L;
	
	/** The first mouse event. */
	private MouseEvent firstMouseEvent = null;
	
	/** The previous number. */
	private int previousNumber = 0;

	/**
	 * Instantiates a new DT scene panel.
	 *
	 * @param mainFrame the main frame
	 * @param type the type
	 */
	public DTScenePanel(MainFrame mainFrame, int type) {
		this(mainFrame, null, type);
		SbApp.trace("DTScenePanel_type(" + mainFrame.getName() + "," + type + ")");
	}

	/**
	 * Instantiates a new DT scene panel.
	 *
	 * @param mainFrame the main frame
	 * @param scene the scene
	 */
	public DTScenePanel(MainFrame mainFrame, Scene scene) {
		this(mainFrame, scene, TYPE_NONE);
		SbApp.trace("DTScenePanel_scene(" + mainFrame.getName() + "," + scene.getFullTitle() + ")");
	}

	/**
	 * Instantiates a new DT scene panel.
	 *
	 * @param mainFrame the main frame
	 * @param scene the scene
	 * @param type the type
	 */
	public DTScenePanel(MainFrame mainFrame, Scene scene, int type) {
		super(mainFrame, scene, type);
		SbApp.trace("DTScenePanel_full(" + mainFrame.getName() + "," + ((scene != null) ? scene.getFullTitle() : "null")
				+ "," + type + ")");
		addMouseMotionListener(this);
		setAutoscrolls(true);
	}

	/**
	 * Gets the previous number.
	 *
	 * @return the previous number
	 */
	public int getPreviousNumber() {
		return previousNumber;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		if (getScene() == null) {
			return;
		}

		if (firstMouseEvent != null) {
			e.consume();

			// if they are holding down the control key, COPY rather than MOVE
			// int ctrlMask = InputEvent.CTRL_DOWN_MASK;
			// int action = ((e.getModifiersEx() & ctrlMask) == ctrlMask) ?
			// TransferHandler.COPY
			// : TransferHandler.MOVE;
			int action = TransferHandler.MOVE;

			int dx = Math.abs(e.getX() - firstMouseEvent.getX());
			int dy = Math.abs(e.getY() - firstMouseEvent.getY());
			// arbitrarily define a 5-pixel shift as the
			// official beginning of a drag
			if (dx > 5 || dy > 5) {
				// this is a drag, not a click
				JComponent comp = (JComponent) e.getSource();
				TransferHandler handler = comp.getTransferHandler();
				// tell the transfer handler to initiate the drag
				handler.exportAsDrag(comp, firstMouseEvent, action);
				firstMouseEvent = null;
			}
			return;
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.manage.dnd.ScenePanel#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		if (getScene() == null) {
			return;
		}
		firstMouseEvent = e;
		e.consume();
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.manage.dnd.ScenePanel#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		firstMouseEvent = null;
	}

	/**
	 * Sets the previous number.
	 *
	 * @param previousNumber the new previous number
	 */
	public void setPreviousNumber(int previousNumber) {
		this.previousNumber = previousNumber;
	}
}
