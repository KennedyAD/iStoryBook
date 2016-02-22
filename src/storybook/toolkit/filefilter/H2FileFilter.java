package storybook.toolkit.filefilter;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import org.apache.commons.io.FilenameUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class H2FileFilter.
 */
public class H2FileFilter extends FileFilter {

	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
	 */
	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}

		String name = f.getName().toLowerCase();
		String ext = FilenameUtils.getExtension(name);
		if (ext != null) {
			if (!ext.equals("db")) {
				return false;
			}
			if (name.contains(".h2") || name.contains(".mv") || name.contains(".data")) {
				return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#getDescription()
	 */
	@Override
	public String getDescription() {
		return "H2 Database File";
	}

}
