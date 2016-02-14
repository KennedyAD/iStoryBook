package storybook.toolkit.filefilter;

import java.io.File;

public class HtmlFileFilter extends javax.swing.filechooser.FileFilter {
	@Override
	public boolean accept(File file) {
		if (file.isDirectory()) {
			return true;
		}
		String filename = file.getName();
		return filename.endsWith(".html") || filename.endsWith(".htm");
	}

	@Override
	public String getDescription() {
		return "HTML Files (*.html *.htm)";
	}
}
