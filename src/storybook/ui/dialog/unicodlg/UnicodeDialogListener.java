package storybook.ui.dialog.unicodlg;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving unicodeDialog events.
 * The class that is interested in processing a unicodeDialog
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addUnicodeDialogListener<code> method. When
 * the unicodeDialog event occurs, that object's appropriate
 * method is invoked.
 *
 * @see UnicodeDialogEvent
 */
public interface UnicodeDialogListener {

	/**
	 * Used when a character has been chosen.
	 * 
	 * @param character
	 *            chosen
	 */
	public void characterSelected(String character);
}
