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

package storybook.toolkit.swing.panel;

import java.awt.Color;
import java.beans.PropertyChangeEvent;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import net.miginfocom.swing.MigLayout;
import storybook.toolkit.I18N;
import storybook.toolkit.swing.SwingUtil;
import storybook.ui.panel.AbstractPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class PlainTextEditor.
 *
 * @author martin
 */

public class PlainTextEditor extends AbstractPanel implements CaretListener {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4475598625157816883L;
	
	/** The ta. */
	private JTextArea ta;
	
	/** The max length. */
	private int maxLength;
	
	/** The lb message. */
	private JLabel lbMessage;

	/**
	 * Instantiates a new plain text editor.
	 */
	public PlainTextEditor() {
		super();
		initAll();
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.CaretListener#caretUpdate(javax.swing.event.CaretEvent)
	 */
	@Override
	public void caretUpdate(CaretEvent e) {
		if (maxLength > 0) {
			int len = maxLength - getText().length() - 1;
			if (len < 0) {
				lbMessage.setForeground(Color.red);
			} else {
				lbMessage.setForeground(Color.black);
			}
			lbMessage.setText(I18N.getMsg("msg.editor.letters.left", len));
		}
	}

	/**
	 * Gets the max length.
	 *
	 * @return the max length
	 */
	public int getMaxLength() {
		return maxLength;
	}

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return ta.getText();
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#init()
	 */
	@Override
	public void init() {
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#initUi()
	 */
	@Override
	public void initUi() {
		setLayout(new MigLayout("wrap,fill"));

		ta = new JTextArea(10, 20);
		ta.setLineWrap(true);
		ta.setWrapStyleWord(true);
		ta.addCaretListener(this);

		JScrollPane scroller = new JScrollPane(ta);
		SwingUtil.setMaxPreferredSize(scroller);

		lbMessage = new JLabel(" ");

		// layout
		add(scroller);
		add(lbMessage);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#modelPropertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void modelPropertyChange(PropertyChangeEvent evt) {
	}

	/**
	 * Sets the max length.
	 *
	 * @param maxLength the new max length
	 */
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	/**
	 * Sets the text.
	 *
	 * @param txt the new text
	 */
	public void setText(String txt) {
		ta.setText(txt);
		ta.setCaretPosition(0);
	}
}
