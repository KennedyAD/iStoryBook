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

package storybook.toolkit.swing;

import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

// TODO: Auto-generated Javadoc
/**
 * Provides a read-only table.
 *
 * @author martin
 *
 */

public class ReadOnlyTable extends JTable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7143750968829579008L;

	/**
	 * Instantiates a new read only table.
	 */
	public ReadOnlyTable() {
		super();
	}

	/**
	 * Instantiates a new read only table.
	 *
	 * @param numRows the num rows
	 * @param numColumns the num columns
	 */
	public ReadOnlyTable(int numRows, int numColumns) {
		super(numRows, numColumns);
	}

	/**
	 * Instantiates a new read only table.
	 *
	 * @param rowData the row data
	 * @param columnNames the column names
	 */
	public ReadOnlyTable(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
	}

	/**
	 * Instantiates a new read only table.
	 *
	 * @param dm the dm
	 */
	public ReadOnlyTable(TableModel dm) {
		super(dm);
	}

	/**
	 * Instantiates a new read only table.
	 *
	 * @param dm the dm
	 * @param cm the cm
	 */
	public ReadOnlyTable(TableModel dm, TableColumnModel cm) {
		super(dm, cm);
	}

	/**
	 * Instantiates a new read only table.
	 *
	 * @param dm the dm
	 * @param cm the cm
	 * @param sm the sm
	 */
	public ReadOnlyTable(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
		super(dm, cm, sm);
	}

	/**
	 * Instantiates a new read only table.
	 *
	 * @param rowData the row data
	 * @param columnNames the column names
	 */
	public ReadOnlyTable(Vector<?> rowData, Vector<?> columnNames) {
		super(rowData, columnNames);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JTable#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int row, int column) {
		// return super.isCellEditable(row, column);
		return false;
	}
}
