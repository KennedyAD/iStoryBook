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

package storybook.toolkit.html;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.ByteArrayInputStream;
import java.io.IOException;

// TODO: Auto-generated Javadoc
/**
 * The Class HtmlSelection.
 *
 * @author martin
 */
public class HtmlSelection implements Transferable, ClipboardOwner {

	/** The html flavor. */
	public static DataFlavor htmlFlavor;
	
	/** The supported flavors. */
	private DataFlavor[] supportedFlavors = { htmlFlavor };
	
	/** The html text. */
	private String htmlText;

	/**
	 * Instantiates a new html selection.
	 *
	 * @param htmlText the html text
	 */
	public HtmlSelection(String htmlText) {
		this.htmlText = htmlText;
		htmlFlavor = new DataFlavor("text/html", "HTML");
	}

	/* (non-Javadoc)
	 * @see java.awt.datatransfer.Transferable#getTransferData(java.awt.datatransfer.DataFlavor)
	 */
	@Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		if (flavor.equals(htmlFlavor)) {
			return new ByteArrayInputStream(htmlText.getBytes());
		} else {
			throw new UnsupportedFlavorException(htmlFlavor);
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.datatransfer.Transferable#getTransferDataFlavors()
	 */
	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return supportedFlavors;
	}

	/* (non-Javadoc)
	 * @see java.awt.datatransfer.Transferable#isDataFlavorSupported(java.awt.datatransfer.DataFlavor)
	 */
	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return flavor.equals(htmlFlavor);
	}

	/* (non-Javadoc)
	 * @see java.awt.datatransfer.ClipboardOwner#lostOwnership(java.awt.datatransfer.Clipboard, java.awt.datatransfer.Transferable)
	 */
	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
		// System.out.println("HtmlSelection.lostOwnership(): ");
	}
}
