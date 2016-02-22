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

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractInputVerifier.
 */
public abstract class AbstractInputVerifier extends InputVerifier {

	/**
	 * The Enum ErrorState.
	 */
	public enum ErrorState {
		
		/** The ok. */
		OK, 
 /** The warning. */
 WARNING, 
 /** The error. */
 ERROR
	}

	/** The error text. */
	private String errorText;
	
	/** The error state. */
	private ErrorState errorState = ErrorState.ERROR;
	
	/** The accept empty. */
	private final boolean acceptEmpty;
	
	/** The check only on new entities. */
	private boolean checkOnlyOnNewEntities;

	/**
	 * Instantiates a new abstract input verifier.
	 */
	public AbstractInputVerifier() {
		this(false);
	}

	/**
	 * Instantiates a new abstract input verifier.
	 *
	 * @param acceptEmpty the accept empty
	 */
	public AbstractInputVerifier(boolean acceptEmpty) {
		this.acceptEmpty = acceptEmpty;
	}

	/**
	 * Gets the error state.
	 *
	 * @return the error state
	 */
	public ErrorState getErrorState() {
		return errorState;
	}

	/**
	 * Gets the error text.
	 *
	 * @return the error text
	 */
	public String getErrorText() {
		return errorText;
	}

	/**
	 * Checks if is check only on new entities.
	 *
	 * @return true, if is check only on new entities
	 */
	public boolean isCheckOnlyOnNewEntities() {
		return checkOnlyOnNewEntities;
	}

	/**
	 * Checks if is mandatory.
	 *
	 * @return true, if is mandatory
	 */
	public boolean isMandatory() {
		return !acceptEmpty;
	}

	/**
	 * Checks if is number.
	 *
	 * @return true, if is number
	 */
	public boolean isNumber() {
		return false;
	}

	/**
	 * Sets the check only on new entities.
	 *
	 * @param checkOnlyOnNewEntities the new check only on new entities
	 */
	public void setCheckOnlyOnNewEntities(boolean checkOnlyOnNewEntities) {
		this.checkOnlyOnNewEntities = checkOnlyOnNewEntities;
	}

	/**
	 * Sets the error state.
	 *
	 * @param errorState the new error state
	 */
	public void setErrorState(ErrorState errorState) {
		this.errorState = errorState;
	}

	/**
	 * Sets the error text.
	 *
	 * @param errorText the new error text
	 */
	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

	/* (non-Javadoc)
	 * @see javax.swing.InputVerifier#shouldYieldFocus(javax.swing.JComponent)
	 */
	@Override
	public boolean shouldYieldFocus(JComponent input) {
		return true;
	}

	/* (non-Javadoc)
	 * @see javax.swing.InputVerifier#verify(javax.swing.JComponent)
	 */
	@Override
	public boolean verify(JComponent comp) {
		if (comp instanceof JTextComponent) {
			if (((JTextComponent) comp).getText().trim().isEmpty() && acceptEmpty) {
				setErrorState(ErrorState.OK);
				return true;
			} else {
				return false;
			}
		}
		return true;
	}
}
