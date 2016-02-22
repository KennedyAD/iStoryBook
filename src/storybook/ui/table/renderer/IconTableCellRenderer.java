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

package storybook.ui.table.renderer;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import net.miginfocom.swing.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class IconTableCellRenderer.
 *
 * @author martin
 */

public class IconTableCellRenderer extends DefaultTableCellRenderer {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5849002211621443183L;

	/**
	 * Instantiates a new icon table cell renderer.
	 */
	public IconTableCellRenderer() {
		super();
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.DefaultTableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (value == null) {
			return label;
		}
		JPanel panel = new JPanel(new MigLayout("insets 2, fill"));
		panel.setBackground(label.getBackground());
		panel.setOpaque(true);
		try {
			Icon icon = (Icon) value;
			JLabel lbColor = new JLabel(icon);
			panel.add(lbColor, "grow");
		} catch (Exception e) {
			panel.add(new JLabel(""));
		}
		return panel;
	}
}
