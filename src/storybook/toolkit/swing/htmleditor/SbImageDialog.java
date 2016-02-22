/*
Storybook: Open Source software for novelists and authors.
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

package storybook.toolkit.swing.htmleditor;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import storybook.toolkit.I18N;
import storybook.toolkit.swing.IconButton;
import storybook.ui.dialog.AbstractDialog;

// TODO: Auto-generated Javadoc
/**
 * The Class SbImageDialog.
 *
 * @author martin
 */


public class SbImageDialog extends AbstractDialog {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6094601789589584333L;

	/**
	 * The Class CalcHeightAction.
	 */
	private class CalcHeightAction extends AbstractAction {
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 2620327030176497807L;

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			int n = Integer.parseInt(tfWidth.getText());
			String val = Integer.toString((int) (n / ratio));
			tfHeight.setText(val);
		}
	}
	
	/**
	 * The Class CalcWidthAction.
	 */
	private class CalcWidthAction extends AbstractAction {
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 4781843594074589979L;

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			int n = Integer.parseInt(tfHeight.getText());
			String val = Integer.toString((int) (n * ratio));
			tfWidth.setText(val);
		}
	}
	
	/**
	 * The Class ChooseImageFileAction.
	 */
	private class ChooseImageFileAction extends AbstractAction {
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = -9102119100079830850L;

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showDialog(getThis(), "OK");
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				try {
					File file = fc.getSelectedFile();
					tfFilename.setText(file.toURI().toURL().toString());
					BufferedImage img = ImageIO.read(file);
					imageWidth = img.getWidth();
					imageHeight = img.getHeight();
					tfWidth.setText(Integer.toString(imageWidth));
					tfHeight.setText(Integer.toString(imageHeight));
					ratio = (double) imageWidth / (double) imageHeight;
				} catch (Exception e1) {
				}
			}
		}
	}
	
	/** The tf filename. */
	private JTextField tfFilename;
	
	/** The tf width. */
	private JTextField tfWidth;
	
	/** The tf height. */
	private JTextField tfHeight;

	/** The ratio. */
	private double ratio = 1.0;

	/** The image width. */
	private int imageWidth = 0;

	/** The image height. */
	private int imageHeight = 0;

	/**
	 * Instantiates a new sb image dialog.
	 *
	 * @param parent the parent
	 */
	public SbImageDialog(JComponent parent) {
		super(parent);
		initAll();
	}

	/**
	 * Gets the html.
	 *
	 * @return the html
	 */
	public String getHTML() {
		String fn = tfFilename.getText();
		String w = tfWidth.getText();
		String h = tfHeight.getText();
		return "<img src='" + fn + "' width='" + w + "' height='" + h + "'>";
	}

	/**
	 * Gets the this.
	 *
	 * @return the this
	 */
	protected SbImageDialog getThis() {
		return this;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.AbstractDialog#init()
	 */
	@Override
	public void init() {
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.AbstractDialog#initUi()
	 */
	@Override
	public void initUi() {
		setLayout(new MigLayout("wrap 2,fill", "[][]", ""));
		setTitle(I18N.getMsg("msg.editor.insert.image"));

		JLabel lbFilename = new JLabel(I18N.getMsgColon("msg.file.info.filename"));
		tfFilename = new JTextField();
		tfFilename.setColumns(20);
		IconButton btChooseFile = new IconButton("icon.small.open", new ChooseImageFileAction());
		btChooseFile.setSize32x20();

		JLabel lbWidth = new JLabel(I18N.getMsgColon("msg.common.width"));

		tfWidth = new JTextField();
		tfWidth.setColumns(10);

		IconButton btCalcWidth = new IconButton("icon.small.calc", new CalcWidthAction());
		btCalcWidth.setSize20x20();

		JLabel lbHeight = new JLabel(I18N.getMsgColon("msg.common.height"));
		tfHeight = new JTextField();
		tfHeight.setColumns(10);

		IconButton btCalcHeight = new IconButton("icon.small.calc", new CalcHeightAction());
		btCalcHeight.setSize20x20();

		// layout
		add(lbFilename);
		add(tfFilename, "grow,split 2");
		add(btChooseFile);
		add(lbWidth);
		add(tfWidth, "split 2");
		add(btCalcWidth);
		add(lbHeight);
		add(tfHeight, "split 2");
		add(btCalcHeight);
		add(getOkButton(), "gaptop 20,span,right,split 2,sg");
		add(getCancelButton(), "sg");
	}
}
