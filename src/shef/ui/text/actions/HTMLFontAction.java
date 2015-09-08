/*
 * Created on Jan 18, 2006
 *
 */
package shef.ui.text.actions;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;

import javax.swing.JEditorPane;
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.HTMLDocument;

import shef.ui.text.CompoundUndoManager;
import shef.ui.text.HTMLUtils;
import shef.ui.text.dialogs.HTMLFontDialog;

import storybook.toolkit.I18N;

/**
 * Action which edits an HTML font
 *
 * @author Bob Tantlinger
 *
 */
public class HTMLFontAction extends HTMLTextEditAction {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public HTMLFontAction() {
		super(I18N.getMsg("shef.font_"));         //$NON-NLS-1$
	}

	@Override
	protected void sourceEditPerformed(ActionEvent e, JEditorPane editor) {
		HTMLFontDialog d = createDialog(editor);
		d.setLocationRelativeTo(d.getParent());
		d.setVisible(true);
		if (!d.hasUserCancelled()) {
			editor.requestFocusInWindow();
			editor.replaceSelection(d.getHTML());
		}
	}

	@Override
	protected void wysiwygEditPerformed(ActionEvent e, JEditorPane editor) {
		HTMLDocument doc = (HTMLDocument) editor.getDocument();
		Element chElem = doc.getCharacterElement(editor.getCaretPosition());
		AttributeSet sas = chElem.getAttributes();

		HTMLFontDialog d = createDialog(editor);
		d.setBold(sas.containsAttribute(StyleConstants.Bold, Boolean.TRUE));
		d.setItalic(sas.containsAttribute(StyleConstants.Italic, Boolean.TRUE));
		d.setUnderline(sas.containsAttribute(StyleConstants.Underline, Boolean.TRUE));

		Object o = sas.getAttribute(StyleConstants.FontFamily);
		if (o != null) {
			d.setFontName(o.toString());
		}
		o = sas.getAttribute(StyleConstants.FontSize);
		if (o != null) {
			try {
				d.setFontSize(Integer.parseInt(o.toString()));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		d.setLocationRelativeTo(d.getParent());
		d.setVisible(true);
		if (!d.hasUserCancelled()) {
			MutableAttributeSet tagAttrs = new SimpleAttributeSet();
			tagAttrs.addAttribute(StyleConstants.FontFamily, d.getFontName());
			tagAttrs.addAttribute(StyleConstants.FontSize, d.getFontSize());
			tagAttrs.addAttribute(StyleConstants.Bold, d.isBold());
			tagAttrs.addAttribute(StyleConstants.Italic, d.isItalic());
			tagAttrs.addAttribute(StyleConstants.Underline, d.isUnderline());

			CompoundUndoManager.beginCompoundEdit(editor.getDocument());
			HTMLUtils.setCharacterAttributes(editor, tagAttrs);
			CompoundUndoManager.endCompoundEdit(editor.getDocument());
		}
	}

	private HTMLFontDialog createDialog(JTextComponent ed) {
		Window w = SwingUtilities.getWindowAncestor(ed);
		String t = ""; //$NON-NLS-1$
		if (ed.getSelectedText() != null) {
			t = ed.getSelectedText();
		}
		HTMLFontDialog d = null;
		if (w != null && w instanceof Frame) {
			d = new HTMLFontDialog((Frame) w, t);
		} else if (w != null && w instanceof Dialog) {
			d = new HTMLFontDialog((Dialog) w, t);
		}

		return d;
	}

}
