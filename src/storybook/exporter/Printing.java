/*
 * SbApp: Open Source software for novelists and authors.
 * Original idea 2008 - 2012 Martin Mustun
 * Copyrigth (C) Favdb
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */
package storybook.exporter;

import java.awt.print.PrinterException;
import java.text.MessageFormat;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import storybook.toolkit.I18N;
import storybook.ui.MainFrame;

// TODO: Auto-generated Javadoc
/**
 * The Class Printing.
 *
 * @author favdb
 */
public class Printing {

	/**
	 * The Class PrintingTask.
	 */
	private class PrintingTask extends SwingWorker<Object, Object> {
		
		/** The header format. */
		private final MessageFormat headerFormat;
		
		/** The footer format. */
		private final MessageFormat footerFormat;
		
		/** The interactive. */
		private final boolean interactive;
		
		/** The complete. */
		private volatile boolean complete;
		
		/** The message. */
		private volatile String message;

		/**
		 * Instantiates a new printing task.
		 *
		 * @param header the header
		 * @param footer the footer
		 * @param interactive the interactive
		 */
		public PrintingTask(MessageFormat header, MessageFormat footer, boolean interactive) {
			this.headerFormat = header;
			this.footerFormat = footer;
			this.interactive = interactive;
		}

		/** (non-Javadoc)
		 * @see javax.swing.SwingWorker#doInBackground()
		 */
		@Override
		protected Object doInBackground() {
			try {
				complete = guideText.print(headerFormat, footerFormat, true, null, null, interactive);
				message = I18N.getMsg("msg.printing") + " "
						+ (complete ? I18N.getMsg("msg.printing.complete") : I18N.getMsg("msg.printing.canceled"));
			} catch (PrinterException ex) {
				message = I18N.getMsg("msg.printing.error");
			} catch (SecurityException ex) {
				message = I18N.getMsg("msg.printing.security");
			}
			return null;
		}

		/** (non-Javadoc)
		 * @see javax.swing.SwingWorker#done()
		 */
		@Override
		protected void done() {
			message(!complete, message);
		}
	}
	
	/** The main frame. */
	MainFrame mainFrame;
	
	/** The interactive. */
	private boolean background, interactive;
	
	/** The footer field. */
	private String headerField, footerField;

	/** The guide text. */
	private javax.swing.JEditorPane guideText;

	/**
	 * Instantiates a new printing.
	 *
	 * @param m the m
	 */
	public Printing(MainFrame m) {
		mainFrame = m;
		interactive = false;
		background = false;
	}

	/**
	 * Creates the format.
	 *
	 * @param source the source
	 * @return the message format
	 */
	private MessageFormat createFormat(String source) {
		if (source != null && source.length() > 0) {
			try {
				return new MessageFormat(source);
			} catch (IllegalArgumentException e) {
				error(I18N.getMsg("msg.printing.formaterror"));
			}
		}
		return null;
	}

	/**
	 * Do print.
	 */
	public void doPrint() {
		MessageFormat header = createFormat(headerField);
		MessageFormat footer = createFormat(footerField);
		PrintingTask task = new PrintingTask(header, footer, interactive);
		if (background) {
			task.execute();
		} else {
			task.run();
		}
	}

	/**
	 * Error.
	 *
	 * @param msg the msg
	 */
	private void error(String msg) {
		message(true, msg);
	}

	/**
	 * Inits the.
	 *
	 * @param str the str
	 */
	public void init(String str) {
		guideText = new javax.swing.JEditorPane();
		guideText.setContentType("text/html");
		guideText.setEditable(false);
		guideText.setOpaque(true);
		guideText.setText(str);
	}

	/**
	 * Message.
	 *
	 * @param error the error
	 * @param msg the msg
	 */
	private void message(boolean error, String msg) {
		int type = error ? JOptionPane.ERROR_MESSAGE : JOptionPane.INFORMATION_MESSAGE;
		JOptionPane.showMessageDialog(mainFrame, msg, I18N.getMsg("msg.printing"), type);
	}

	/**
	 * Sets the background.
	 *
	 * @param b the new background
	 */
	public void setBackground(boolean b) {
		background = b;
	}

	/**
	 * Sets the footer.
	 *
	 * @param str the new footer
	 */
	public void setFooter(String str) {
		footerField = str;
	}

	/**
	 * Sets the header.
	 *
	 * @param str the new header
	 */
	public void setHeader(String str) {
		headerField = str;
	}

	/**
	 * Sets the interactive.
	 *
	 * @param b the new interactive
	 */
	public void setInteractive(boolean b) {
		interactive = b;
	}

}
