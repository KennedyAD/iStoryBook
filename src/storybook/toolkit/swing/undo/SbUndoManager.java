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

import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.JTextComponent;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.CompoundEdit;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;


// TODO: Auto-generated Javadoc
/**
 * The Class SbUndoManager.
 */
public class SbUndoManager extends UndoManager implements UndoableEditListener {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1684198282721071485L;

	/**
	 * The Class SbCompoundEdit.
	 */
	private class SbCompoundEdit extends CompoundEdit {
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = -7132641862624605455L;

		/* (non-Javadoc)
		 * @see javax.swing.undo.CompoundEdit#isInProgress()
		 */
		@Override
		public boolean isInProgress() {
			return false;
		}

		/* (non-Javadoc)
		 * @see javax.swing.undo.CompoundEdit#undo()
		 */
		@Override
		public void undo() throws CannotUndoException {
			if (compoundEdit != null) {
				compoundEdit.end();
			}
			super.undo();
			compoundEdit = null;
		}
	}
	
	/** The compound edit. */
	public SbCompoundEdit compoundEdit = null;
	
	/** The text component. */
	private JTextComponent textComponent;

	/** The group end. */
	private boolean groupEnd = false;

	/**
	 * Instantiates a new sb undo manager.
	 *
	 * @param editor the editor
	 */
	public SbUndoManager(JTextComponent editor) {
		this.textComponent = editor;
		editor.getDocument().addUndoableEditListener(this);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.UndoManager#canRedo()
	 */
	@Override
	public synchronized boolean canRedo() {
		if (compoundEdit != null) {
			return true;
		}
		return super.canRedo();
	}

	/**
	 * Creates the compound edit.
	 *
	 * @param edit the edit
	 * @return the sb compound edit
	 */
	private SbCompoundEdit createCompoundEdit(UndoableEdit edit) {
		SbCompoundEdit ce = new SbCompoundEdit();
		ce.addEdit(edit);
		addEdit(ce);
		return ce;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.UndoManager#discardAllEdits()
	 */
	@Override
	public synchronized void discardAllEdits() {
		super.discardAllEdits();
		groupEnd = false;
		compoundEdit = null;
	}

	/**
	 * End group.
	 */
	public void endGroup() {
		groupEnd = true;
	}

	/**
	 * Gets the editor.
	 *
	 * @return the editor
	 */
	public JTextComponent getEditor() {
		return textComponent;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.UndoManager#undoableEditHappened(javax.swing.event.UndoableEditEvent)
	 */
	@Override
	public void undoableEditHappened(UndoableEditEvent evt) {
		if (compoundEdit == null) {
			compoundEdit = createCompoundEdit(evt.getEdit());
			return;
		}

		// group ended?
		if (!groupEnd) {
			compoundEdit.addEdit(evt.getEdit());
			return;
		}

		// new group
		groupEnd = false;
		compoundEdit.end();
		compoundEdit = createCompoundEdit(evt.getEdit());
	}
}
