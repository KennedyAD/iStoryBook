/*
 * Created on Feb 25, 2005
 *
 */
package shef.ui.text.actions;

import java.awt.Event;
import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JEditorPane;
import javax.swing.KeyStroke;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import shef.ui.UIUtils;
import storybook.toolkit.I18N;

/**
 *
 */
public class HTMLLineBreakAction extends HTMLTextEditAction {
	//private final String RES = TBGlobals.RESOURCES;

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public HTMLLineBreakAction() {
		super(I18N.getMsg("shef.line_break"));
		putValue(MNEMONIC_KEY, (int) I18N.getMnemonic("shef.line_break"));
		putValue(SMALL_ICON, UIUtils.getIcon(UIUtils.X16, "br.png"));
		putValue(ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(Event.ENTER, Event.SHIFT_MASK));
		putValue(Action.SHORT_DESCRIPTION, getValue(Action.NAME));
	}

	@Override
	protected void sourceEditPerformed(ActionEvent e, JEditorPane editor) {
		editor.replaceSelection("<br>\n");
	}

	@Override
	protected void wysiwygEditPerformed(ActionEvent e, JEditorPane editor) {
		HTMLDocument document = (HTMLDocument) editor.getDocument();
		int pos = editor.getCaretPosition();

		String elName
				= document
				.getParagraphElement(pos)
				.getName();
		/*
		 * if ((elName.toUpperCase().equals("PRE")) ||
		 * (elName.toUpperCase().equals("P-IMPLIED"))) {
		 * editor.replaceSelection("\r"); return;
		 */
		HTML.Tag tag = HTML.getTag(elName);
		if (elName.toUpperCase().equals("P-IMPLIED")) {
			tag = HTML.Tag.IMPLIED;
		}

		HTMLEditorKit.InsertHTMLTextAction hta
				= new HTMLEditorKit.InsertHTMLTextAction(
						"insertBR",
						"<br>",
						tag,
						HTML.Tag.BR);
		hta.actionPerformed(e);
	}
}
