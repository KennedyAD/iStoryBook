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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import net.miginfocom.swing.MigLayout;
import storybook.toolkit.swing.panel.GradientPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class DnDTest01.
 *
 * @author martin
 */

public class DnDTest01 extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1641076762976180821L;
	
	/** The instance. */
	private static DnDTest01 instance;

	/**
	 * Gets the single instance of DnDTest01.
	 *
	 * @return single instance of DnDTest01
	 */
	public static DnDTest01 getInstance() {
		if (instance == null) {
			instance = new DnDTest01();
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
				DnDTest01.getInstance().init();
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
		setLayout(new MigLayout());
		setTitle("IDWTest01");
		setPreferredSize(new Dimension(600, 400));
		setLocation(400, 200);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		GradientPanel panel = new GradientPanel();
		panel.setPreferredSize(new Dimension(1000, 1000));
		panel.setForeground(Color.white);
		panel.setBackground(Color.blue);
		// panel.setFocusable(true);
		// panel.setAutoscrolls(true);
		JScrollPane scroller = new JScrollPane(panel);

		MouseMotionListener doScrollRectToVisible = new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				System.out.println("DnDTest01.initUi().new MouseMotionAdapter() {...}.mouseDragged(): ");
				Rectangle r = new Rectangle(e.getX(), e.getY(), 1, 1);
				((JPanel) e.getSource()).scrollRectToVisible(r);
			}
		};
		panel.addMouseMotionListener(doScrollRectToVisible);

		add(scroller);
		pack();
		setVisible(true);
	}
}
