package storybook.toolkit.filefilter;

import java.io.File;

// TODO: Auto-generated Javadoc
/**
 * The Class PngFileFilter.
 */
public class PngFileFilter extends javax.swing.filechooser.FileFilter {
	
	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
	 */
	@Override
	public boolean accept(File file) {
		if (file.isDirectory()) {
			return true;
		}
		String filename = file.getName();
		return filename.endsWith(".png");
	}

	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#getDescription()
	 */
	@Override
	public String getDescription() {
		return "*.png";
	}
}
