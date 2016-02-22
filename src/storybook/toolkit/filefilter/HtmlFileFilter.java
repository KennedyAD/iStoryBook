package storybook.toolkit.filefilter;

import java.io.File;

// TODO: Auto-generated Javadoc
/**
 * The Class HtmlFileFilter.
 */
public class HtmlFileFilter extends javax.swing.filechooser.FileFilter {
	
	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
	 */
	@Override
	public boolean accept(File file) {
		if (file.isDirectory()) {
			return true;
		}
		String filename = file.getName();
		return filename.endsWith(".html") || filename.endsWith(".htm");
	}

	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#getDescription()
	 */
	@Override
	public String getDescription() {
		return "HTML Files (*.html *.htm)";
	}
}
