package storybook.toolkit.swing.verifier;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

// TODO: Auto-generated Javadoc
/**
 * The Class DocumentSizeFilter.
 */
public class DocumentSizeFilter extends DocumentFilter {
	
	/** The max characters. */
	int maxCharacters;

	/**
	 * Instantiates a new document size filter.
	 *
	 * @param maxChars the max chars
	 */
	public DocumentSizeFilter(int maxChars) {
		maxCharacters = maxChars;
	}

	/* (non-Javadoc)
	 * @see javax.swing.text.DocumentFilter#insertString(javax.swing.text.DocumentFilter.FilterBypass, int, java.lang.String, javax.swing.text.AttributeSet)
	 */
	@Override
	public void insertString(FilterBypass fb, int offs, String str, AttributeSet a) throws BadLocationException {
		if ((fb.getDocument().getLength() + str.length()) <= maxCharacters) {
			super.insertString(fb, offs, str, a);
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.text.DocumentFilter#replace(javax.swing.text.DocumentFilter.FilterBypass, int, int, java.lang.String, javax.swing.text.AttributeSet)
	 */
	@Override
	public void replace(FilterBypass fb, int offs, int length, String str, AttributeSet a) throws BadLocationException {
		if ((fb.getDocument().getLength() + str.length() - length) <= maxCharacters) {
			super.replace(fb, offs, length, str, a);
		}
	}
}
