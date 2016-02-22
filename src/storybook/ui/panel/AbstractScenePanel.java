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

package storybook.ui.panel;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import storybook.action.DeleteEntityAction;
import storybook.action.EditEntityAction;
import storybook.model.hbn.entity.Scene;
import storybook.toolkit.I18N;
import storybook.toolkit.swing.IconButton;
import storybook.ui.MainFrame;


// TODO: Auto-generated Javadoc
/**
 * The Class AbstractScenePanel.
 */
abstract public class AbstractScenePanel extends AbstractGradientPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8205175656654586333L;

	/** The scene. */
	protected Scene scene;

	/** The new action. */
	protected AbstractAction newAction;

	/** The bt new. */
	protected IconButton btNew;
	
	/** The bt edit. */
	protected IconButton btEdit;
	
	/** The bt delete. */
	protected IconButton btDelete;

	/**
	 * Instantiates a new abstract scene panel.
	 *
	 * @param mainFrame the main frame
	 * @param scene the scene
	 */
	public AbstractScenePanel(MainFrame mainFrame, Scene scene) {
		super(mainFrame);
		this.scene = scene;
	}

	/**
	 * Instantiates a new abstract scene panel.
	 *
	 * @param mainFrame the main frame
	 * @param scene the scene
	 * @param showBgGradient the show bg gradient
	 * @param startBgcolor the start bgcolor
	 * @param endBgColor the end bg color
	 */
	public AbstractScenePanel(MainFrame mainFrame, Scene scene, boolean showBgGradient, Color startBgcolor,
			Color endBgColor) {
		super(mainFrame, showBgGradient, scene.getInformative() ? Color.white : startBgcolor,
				scene.getInformative() ? Color.white : endBgColor);
		this.scene = scene;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof AbstractScenePanel)) {
			return false;
		}
		AbstractScenePanel asp = (AbstractScenePanel) other;
		if (asp.getScene() == null || scene == null) {
			return false;
		}
		return asp.getScene().getId().equals(scene.getId());
	}

	/**
	 * Gets the delete button.
	 *
	 * @return the delete button
	 */
	protected IconButton getDeleteButton() {
		if (btDelete != null) {
			return btDelete;
		}
		btDelete = new IconButton("icon.small.delete", new DeleteEntityAction(mainFrame, scene));
		btDelete.setText("");
		btDelete.setSize32x20();
		btDelete.setToolTipText(I18N.getMsg("msg.common.delete"));
		return btDelete;
	}

	/**
	 * Gets the edits the button.
	 *
	 * @return the edits the button
	 */
	protected IconButton getEditButton() {
		if (btEdit != null) {
			return btEdit;
		}
		btEdit = new IconButton("icon.small.edit", new EditEntityAction(mainFrame, scene, false));
		btEdit.setText("");
		btEdit.setSize32x20();
		btEdit.setToolTipText(I18N.getMsg("msg.common.edit"));
		return btEdit;
	}

	/**
	 * Gets the new action.
	 *
	 * @return the new action
	 */
	protected AbstractAction getNewAction() {
		if (newAction == null) {
			newAction = new AbstractAction() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 2360418889876520475L;

				@Override
				public void actionPerformed(ActionEvent e) {
					// BookController ctrl = mainFrame.getBookController();
					Scene newScene = new Scene();
					newScene.setStrand(scene.getStrand());
					newScene.setSceneTs(scene.getSceneTs());
					if (scene.hasChapter()) {
						newScene.setChapter(scene.getChapter());
					}
					// ctrl.setSceneToEdit(newScene);
					mainFrame.showEditorAsDialog(newScene);
				}
			};
		}
		return newAction;
	}

	/**
	 * Gets the new button.
	 *
	 * @return the new button
	 */
	protected IconButton getNewButton() {
		if (btNew != null) {
			return btNew;
		}
		btNew = new IconButton("icon.small.new", getNewAction());
		btNew.setSize32x20();
		btNew.setToolTipText(I18N.getMsg("msg.common.new"));
		return btNew;
	}

	/**
	 * Gets the scene.
	 *
	 * @return the scene
	 */
	public Scene getScene() {
		return scene;
	}

	/**
	 * Sets the scene.
	 *
	 * @param scene the new scene
	 */
	public void setScene(Scene scene) {
		this.scene = scene;
	}
}
