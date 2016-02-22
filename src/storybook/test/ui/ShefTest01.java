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

package storybook.test.ui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import net.miginfocom.swing.MigLayout;
import storybook.toolkit.swing.htmleditor.HtmlEditor;

// TODO: Auto-generated Javadoc
/**
 * The Class ShefTest01.
 *
 * @author martin
 */

public class ShefTest01 extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1525984786302906111L;
	
	/** The instance. */
	private static ShefTest01 instance;

	/**
	 * Gets the single instance of ShefTest01.
	 *
	 * @return single instance of ShefTest01
	 */
	public static ShefTest01 getInstance() {
		if (instance == null) {
			instance = new ShefTest01();
		}
		return instance;
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				ShefTest01.getInstance().init();
			}
		});
	}

	/**
	 * Inits the.
	 */
	private void init() {
		initUi();
	}

	/**
	 * Inits the ui.
	 */
	private void initUi() {
		setLayout(new MigLayout("fill"));
		setTitle("ShefTest01");
		setPreferredSize(new Dimension(600, 400));
		setLocation(400, 200);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		HtmlEditor editor = new HtmlEditor();
		add(editor, "grow");

		pack();
		setVisible(true);
	}
}
