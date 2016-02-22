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

package storybook.toolkit.swing.verifier;

import javax.swing.JComponent;
import javax.swing.JTextField;

import storybook.toolkit.I18N;

// TODO: Auto-generated Javadoc
/**
 * The Class IntegerVerifier.
 */
public class IntegerVerifier extends AbstractInputVerifier {

	/** The only positive. */
	private boolean onlyPositive;

	/**
	 * Instantiates a new integer verifier.
	 */
	public IntegerVerifier() {
		this(false);
	}

	/**
	 * Instantiates a new integer verifier.
	 *
	 * @param onlyPositive the only positive
	 */
	public IntegerVerifier(boolean onlyPositive) {
		super(false);
		this.onlyPositive = onlyPositive;
	}

	/**
	 * Instantiates a new integer verifier.
	 *
	 * @param onlyPositiveNumbers the only positive numbers
	 * @param acceptEmty the accept emty
	 */
	public IntegerVerifier(boolean onlyPositiveNumbers, boolean acceptEmty) {
		super(acceptEmty);
		this.onlyPositive = onlyPositiveNumbers;
	}

	/* (non-Javadoc)
	 * @see storybook.toolkit.swing.verifier.AbstractInputVerifier#isNumber()
	 */
	@Override
	public boolean isNumber() {
		return true;
	}

	/* (non-Javadoc)
	 * @see storybook.toolkit.swing.verifier.AbstractInputVerifier#verify(javax.swing.JComponent)
	 */
	@Override
	public boolean verify(JComponent comp) {
		if (super.verify(comp)) {
			return true;
		}
		if (comp instanceof JTextField) {
			JTextField tf = (JTextField) comp;
			try {
				int i = Integer.parseInt(tf.getText());
				if (onlyPositive) {
					if (i < 0) {
						throw new NumberFormatException(I18N.getMsg("msg.verifier.integer.positive"));
					}
				}
				return true;
			} catch (NumberFormatException e) {
				setErrorText(I18N.getMsg("msg.verifier.wrong.format") + " " + e.getLocalizedMessage());
			}
		}
		return false;
	}
}
