package storybook.ui.panel;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;

import storybook.toolkit.swing.ColorUtil;
import storybook.ui.MainFrame;


// TODO: Auto-generated Javadoc
/**
 * The Class AbstractGradientPanel.
 */
public abstract class AbstractGradientPanel extends AbstractPanel {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8346175756412132634L;
	
	/** The trace. */
	private boolean trace = false;
	
	/** The start bg color. */
	private Color startBgColor = Color.white;
	
	/** The end bg color. */
	private Color endBgColor = Color.black;
	
	/** The show bg gradient. */
	private boolean showBgGradient = true;

	// protected MainFrame mainFrame;

	/**
	 * Instantiates a new abstract gradient panel.
	 */
	public AbstractGradientPanel() {
		showBgGradient = false;
		if (trace) {
			System.out.println("AbstractGradientPanel()");
		}
	}

	/**
	 * Instantiates a new abstract gradient panel.
	 *
	 * @param mainFrame the main frame
	 */
	public AbstractGradientPanel(MainFrame mainFrame) {
		this();
		this.mainFrame = mainFrame;
		if (trace) {
			System.out.println("AbstractGradientPanel(" + mainFrame.getName() + ")");
		}
	}

	/**
	 * Instantiates a new abstract gradient panel.
	 *
	 * @param mainFrame the main frame
	 * @param showBgGradient the show bg gradient
	 * @param startBgColor the start bg color
	 * @param endBgColor the end bg color
	 */
	public AbstractGradientPanel(MainFrame mainFrame, boolean showBgGradient, Color startBgColor, Color endBgColor) {
		this(mainFrame);
		this.showBgGradient = showBgGradient;
		this.startBgColor = startBgColor;
		this.endBgColor = endBgColor;
		if (trace) {
			System.out.println("AbstractGradientPanel(" + mainFrame.getName() + "," + showBgGradient + ","
					+ ColorUtil.getColorString(startBgColor) + "," + ColorUtil.getColorString(endBgColor) + ")");
		}
	}

	/**
	 * Gets the end bg color.
	 *
	 * @return the end bg color
	 */
	public Color getEndBgColor() {
		return endBgColor;
	}

	/**
	 * Gets the start bg color.
	 *
	 * @return the start bg color
	 */
	public Color getStartBgColor() {
		return startBgColor;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#modelPropertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public abstract void modelPropertyChange(PropertyChangeEvent evt);

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		if (showBgGradient) {
			Graphics2D g2d = (Graphics2D) g;
			GradientPaint gradient = new GradientPaint(0, 0, startBgColor, this.getWidth(), this.getHeight(),
					ColorUtil.blend(Color.white, endBgColor));
			g2d.setPaint(gradient);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
		} else {
			super.paintComponent(g);
		}
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#refresh()
	 */
	@Override
	public void refresh() {
		removeAll();
		init();
		initUi();
		invalidate();
		validate();
		repaint();
	}

	/**
	 * Sets the end bg color.
	 *
	 * @param endBgColor the new end bg color
	 */
	public void setEndBgColor(Color endBgColor) {
		this.endBgColor = endBgColor;
	}

	/**
	 * Sets the start bg color.
	 *
	 * @param startBgColor the new start bg color
	 */
	public void setStartBgColor(Color startBgColor) {
		this.startBgColor = startBgColor;
	}
}
