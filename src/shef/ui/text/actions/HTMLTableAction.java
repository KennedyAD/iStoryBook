/*
 * Created on Feb 26, 2005
 *
 */
package shef.ui.text.actions;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.Action;
import javax.swing.JEditorPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;

import shef.ui.UIUtils;
import shef.ui.text.CompoundUndoManager;
import shef.ui.text.HTMLUtils;
import shef.ui.text.dialogs.NewTableDialog;
import storybook.toolkit.I18N;

/**
 * Action which shows a dialog to insert an HTML table
 *
 * @author Bob Tantlinger
 *
 */
public class HTMLTableAction extends HTMLTextEditAction {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public HTMLTableAction() {
		super(I18N.getMsg("shef.table_"));
		putValue(MNEMONIC_KEY, (int) I18N.getMnemonic("shef.table_"));

		putValue(SMALL_ICON, UIUtils.getIcon(UIUtils.X16, "table.png"));
		putValue(Action.SHORT_DESCRIPTION, getValue(Action.NAME));
	}

	@Override
	protected void sourceEditPerformed(ActionEvent e, JEditorPane editor) {
		NewTableDialog dlg = createNewTableDialog(editor);
		if (dlg == null) {
			return;
		}
		dlg.setLocationRelativeTo(dlg.getParent());
		dlg.setVisible(true);
		if (dlg.hasUserCancelled()) {
			return;
		}

		editor.replaceSelection(dlg.getHTML());
	}

	@Override
	protected void wysiwygEditPerformed(ActionEvent e, JEditorPane editor) {
		NewTableDialog dlg = createNewTableDialog(editor);
		if (dlg == null) {
			return;
		}
		dlg.setLocationRelativeTo(dlg.getParent());
		dlg.setVisible(true);
		if (dlg.hasUserCancelled()) {
			return;
		}

		HTMLDocument document = (HTMLDocument) editor.getDocument();
		String html = dlg.getHTML();

		Element elem = document.getParagraphElement(editor.getCaretPosition());
		CompoundUndoManager.beginCompoundEdit(document);
		try {
			if (HTMLUtils.isElementEmpty(elem)) {
				document.setOuterHTML(elem, html);
			} else if (elem.getName().equals("p-implied")) {
				document.insertAfterEnd(elem, html);
			} else {
				HTMLUtils.insertHTML(html, HTML.Tag.TABLE, editor);
			}
		} catch (BadLocationException | IOException ex) {
			ex.printStackTrace();
		}
		CompoundUndoManager.endCompoundEdit(document);
	}

	/**
	 * Creates the dialog
	 *
	 * @param ed
	 * @return the dialog
	 */
	private NewTableDialog createNewTableDialog(JTextComponent ed) {
		Window w = SwingUtilities.getWindowAncestor(ed);
		NewTableDialog d = null;
		if (w != null && w instanceof Frame) {
			d = new NewTableDialog((Frame) w);
		} else if (w != null && w instanceof Dialog) {
			d = new NewTableDialog((Dialog) w);
		}

		return d;
	}
}
