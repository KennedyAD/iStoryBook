package storybook.toolkit.filefilter;

import java.io.File;

// TODO: Auto-generated Javadoc
/**
 * The Class TextFileFilter.
 */
public class TextFileFilter extends javax.swing.filechooser.FileFilter {
	
	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
	 */
	@Override
	public boolean accept(File file) {
		if (file.isDirectory()) {
			return true;
		}
		String filename = file.getName();
		return filename.endsWith(".txt");
	}

	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#getDescription()
	 */
	@Override
	public String getDescription() {
		return "Text File (*.txt)";
	}
}
