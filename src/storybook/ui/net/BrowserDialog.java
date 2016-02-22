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

package storybook.ui.net;

import java.awt.Color;

import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;
import storybook.toolkit.I18N;
import storybook.ui.dialog.AbstractDialog;

// TODO: Auto-generated Javadoc
/**
 * The Class BrowserDialog.
 *
 * @author martin
 */

public class BrowserDialog extends AbstractDialog {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2256237515056369048L;
	
	/** The url. */
	private String url;
	
	/** The title. */
	private String title;
	
	/** The width. */
	private int width;
	
	/** The height. */
	private int height;

	/**
	 * Instantiates a new browser dialog.
	 *
	 * @param url the url
	 */
	public BrowserDialog(String url) {
		this(url, I18N.getMsg("BrowserDialog"), 500, 300);
	}

	/**
	 * Instantiates a new browser dialog.
	 *
	 * @param url the url
	 * @param title the title
	 * @param width the width
	 * @param height the height
	 */
	public BrowserDialog(String url, String title, int width, int height) {
		super();
		this.url = url;
		this.title = title;
		this.width = width;
		this.height = height;
		initAll();
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
		setLayout(new MigLayout("wrap,fill,ins 10"));
		setTitle(title);
		setBackground(Color.white);

		BrowserPanel panel = new BrowserPanel(url, width, height);

		// layout
		add(new JScrollPane(panel), "grow");
		add(getCloseButton(), "right,sg");
	}
}
