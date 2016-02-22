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

package storybook.toolkit.completer;

import java.awt.event.KeyEvent;

import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;

// TODO: Auto-generated Javadoc
/**
 * The Class AbbrCompleter.
 *
 * @author martin
 */
public class AbbrCompleter extends AbstractCompleter {

	/** The source comp1. */
	private JTextComponent sourceComp1;
	
	/** The source comp2. */
	private JTextComponent sourceComp2;
	
	/** The comp name1. */
	private String compName1;
	
	/** The comp name2. */
	private String compName2;

	/**
	 * Instantiates a new abbr completer.
	 *
	 * @param compName1 the comp name1
	 * @param compName2 the comp name2
	 */
	public AbbrCompleter(String compName1, String compName2) {
		this.compName1 = compName1;
		this.compName2 = compName2;
	}

	/* (non-Javadoc)
	 * @see storybook.toolkit.completer.AbstractCompleter#getCompletedText()
	 */
	@Override
	public String getCompletedText() {
		try {
			String fn = "";
			String ln = "";
			if (sourceComp1.getText().length() > 1) {
				fn = sourceComp1.getText(0, 2);
			}
			if (sourceComp2.getText().length() > 1) {
				ln = sourceComp2.getText(0, 2);
			}
			return fn + ln;
		} catch (BadLocationException e) {
			// ignore
		}
		return "";
	}

	/**
	 * Gets the comp name1.
	 *
	 * @return the comp name1
	 */
	public String getCompName1() {
		return compName1;
	}

	/**
	 * Gets the comp name2.
	 *
	 * @return the comp name2
	 */
	public String getCompName2() {
		return compName2;
	}

	/**
	 * Gets the source comp1.
	 *
	 * @return the source comp1
	 */
	public JTextComponent getSourceComp1() {
		return sourceComp1;
	}

	/**
	 * Gets the source comp2.
	 *
	 * @return the source comp2
	 */
	public JTextComponent getSourceComp2() {
		return sourceComp2;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
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
		comp.setText(getCompletedText());
	}

	/**
	 * Sets the source comp1.
	 *
	 * @param sourceComp1 the new source comp1
	 */
	public void setSourceComp1(JTextComponent sourceComp1) {
		this.sourceComp1 = sourceComp1;
	}

	/**
	 * Sets the source comp2.
	 *
	 * @param sourceComp2 the new source comp2
	 */
	public void setSourceComp2(JTextComponent sourceComp2) {
		this.sourceComp2 = sourceComp2;
	}

}
