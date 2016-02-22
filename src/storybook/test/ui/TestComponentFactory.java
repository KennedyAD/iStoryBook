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

import javax.swing.JComponent;
import javax.swing.JLabel;

import net.infonode.docking.View;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating TestComponent objects.
 *
 * @author martin
 */
public class TestComponentFactory {

	/** The instance. */
	private static TestComponentFactory instance;

	/** The counter. */
	private static int counter = 0;

	/**
	 * Gets the single instance of TestComponentFactory.
	 *
	 * @return single instance of TestComponentFactory
	 */
	public static TestComponentFactory getInstance() {
		if (instance == null) {
			instance = new TestComponentFactory();
		}
		return instance;
	}
	
	/** The view0. */
	private View view0;

	/** The view1. */
	private View view1;

	/**
	 * Instantiates a new test component factory.
	 */
	private TestComponentFactory() {
	}

	/**
	 * Gets the component.
	 *
	 * @param view the view
	 * @return the component
	 */
	public JComponent getComponent(View view) {
		if (view == view0) {
			return new JLabel("comp 1: " + (counter++));
		}
		if (view == view1) {
			return new JLabel("comp 2: " + (counter++));
		}
		return new JLabel("error");
	}

	/**
	 * Gets the view0.
	 *
	 * @return the view0
	 */
	public View getView0() {
		return view0;
	}

	/**
	 * Gets the view1.
	 *
	 * @return the view1
	 */
	public View getView1() {
		return view1;
	}

	/**
	 * Sets the view0.
	 *
	 * @param view0 the new view0
	 */
	public void setView0(View view0) {
		this.view0 = view0;
	}

	/**
	 * Sets the view1.
	 *
	 * @param view1 the new view1
	 */
	public void setView1(View view1) {
		this.view1 = view1;
	}
}
