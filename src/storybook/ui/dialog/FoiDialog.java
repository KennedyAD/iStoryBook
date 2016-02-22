/*
Storybook: Open Source software for novelists and authors.
Copyright (C) 2008 - 2012 Martin Mustun

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

package storybook.ui.dialog;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;
import storybook.model.hbn.entity.Idea;
import storybook.toolkit.I18N;
import storybook.toolkit.swing.SwingUtil;
import storybook.toolkit.swing.htmleditor.HtmlEditor;
import storybook.ui.MainFrame;

// TODO: Auto-generated Javadoc
/**
 * The Class FoiDialog.
 *
 * @author martin
 */

public class FoiDialog extends AbstractDialog {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8912966178461715793L;
	
	/** The idea editor. */
	private HtmlEditor ideaEditor;

	/**
	 * Instantiates a new foi dialog.
	 *
	 * @param mainFrame the main frame
	 */
	public FoiDialog(MainFrame mainFrame) {
		super(mainFrame);
		initAll();
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.AbstractDialog#getOkAction()
	 */
	@Override
	protected AbstractAction getOkAction() {
		return new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 6838532279784248404L;

			@Override
			public void actionPerformed(ActionEvent e) {
				canceled = false;
				dispose();
				Idea idea = new Idea();
				idea.setStatus(0);
				idea.setCategory(I18N.getMsg("msg.foi.title"));
				idea.setNotes(ideaEditor.getText());
				mainFrame.getBookController().newIdea(idea);
			}
		};
	}

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return ideaEditor.getText();
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.AbstractDialog#init()
	 */
	@Override
	public void init() {
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.AbstractDialog#initUi()
	 */
	@Override
	public void initUi() {
		super.initUi();
		setLayout(new MigLayout("wrap,fill", "", ""));
		setTitle(I18N.getMsg("msg.foi.title"));
		setIconImage(I18N.getIconImage("icon.small.bulb"));
		setPreferredSize(new Dimension(450, 400));

		JLabel lbTitle = new JLabel(I18N.getMsgColon("msg.foi.enter.new"));

		ideaEditor = new HtmlEditor();
		SwingUtil.setMaxPreferredSize(ideaEditor);

		// layout
		add(lbTitle);
		add(ideaEditor, "grow");
		add(getOkButton(), "split 2,sg,right");
		add(getCancelButton(), "sg");
	}
}
