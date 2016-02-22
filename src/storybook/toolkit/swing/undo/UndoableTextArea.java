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

package storybook.toolkit.swing.undo;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


// TODO: Auto-generated Javadoc
/**
 * The Class UndoableTextArea.
 */
public class UndoableTextArea extends JTextArea implements UndoableComponent, KeyListener, DocumentListener {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4486225358798268957L;

	/** The Constant LIMIT. */
	private static final int LIMIT = 100;

	/** The undo. */
	private SbUndoManager undo;
	
	/** The undo action. */
	private AbstractAction undoAction;
	
	/** The redo action. */
	private AbstractAction redoAction;

	/**
	 * Instantiates a new undoable text area.
	 */
	public UndoableTextArea() {
		super();
		addKeyListener(this);
		undo = new SbUndoManager(this);
		undo.setLimit(LIMIT);
		undoAction = new UndoAction(undo);
		redoAction = new RedoAction(undo);
		// add key strokes
		InputMap inputMap = getInputMap();
		inputMap.put(KeyStroke.getKeyStroke("control Z"), undoAction);
		inputMap.put(KeyStroke.getKeyStroke("control Y"), redoAction);
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void changedUpdate(DocumentEvent e) {
		undo.endGroup();
	}

	/* (non-Javadoc)
	 * @see storybook.toolkit.swing.undo.UndoableComponent#getRedoAction()
	 */
	@Override
	public AbstractAction getRedoAction() {
		return redoAction;
	}

	/* (non-Javadoc)
	 * @see storybook.toolkit.swing.undo.UndoableComponent#getUndoAction()
	 */
	@Override
	public AbstractAction getUndoAction() {
		return undoAction;
	}

	/* (non-Javadoc)
	 * @see storybook.toolkit.swing.undo.UndoableComponent#getUndoManager()
	 */
	@Override
	public SbUndoManager getUndoManager() {
		return undo;
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void insertUpdate(DocumentEvent e) {
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int ch = e.getKeyCode();
		if (ch == KeyEvent.VK_SPACE || ch == KeyEvent.VK_ENTER) {
			undo.endGroup();
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent e) {
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void removeUpdate(DocumentEvent e) {
	}
}
