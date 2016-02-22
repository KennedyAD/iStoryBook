/*
Storybook: Scene-based software for novelists and authors.
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

package storybook.ui.dialog.file;

import javax.swing.JCheckBox;

import org.apache.commons.io.FileUtils;

import net.miginfocom.swing.MigLayout;
import storybook.toolkit.I18N;


// TODO: Auto-generated Javadoc
/**
 * The Class NewFileDialog.
 */
public class NewFileDialog extends AbstractFileDialog {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7528254155947530396L;
	
	/** The cb use html scenes. */
	private JCheckBox cbUseHtmlScenes;
	
	/** The cb use html descr. */
	private JCheckBox cbUseHtmlDescr;

	/**
	 * Instantiates a new new file dialog.
	 */
	public NewFileDialog() {
		super(null);
		setTitle(I18N.getMsg("msg.welcome.new.project"));
		setDir(FileUtils.getUserDirectoryPath());
	}

	/**
	 * Gets the use html descr.
	 *
	 * @return the use html descr
	 */
	public boolean getUseHtmlDescr() {
		return cbUseHtmlDescr.isSelected();
	}

	/**
	 * Gets the use html scenes.
	 *
	 * @return the use html scenes
	 */
	public boolean getUseHtmlScenes() {
		return cbUseHtmlScenes.isSelected();
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.file.AbstractFileDialog#initOptionsPanel()
	 */
	@Override
	protected void initOptionsPanel() {
		optionsPanel.setLayout(new MigLayout("wrap"));

		cbUseHtmlScenes = new JCheckBox();
		cbUseHtmlScenes.setText(I18N.getMsg("msg.document.preference.use.html.scenes"));
		cbUseHtmlScenes.setSelected(true);

		cbUseHtmlDescr = new JCheckBox();
		cbUseHtmlDescr.setText(I18N.getMsg("msg.document.preference.use.html.descr"));
		cbUseHtmlDescr.setSelected(true);

		optionsPanel.add(cbUseHtmlScenes);
		optionsPanel.add(cbUseHtmlDescr);
	}
}
