package storybook.toolkit.swing;

import java.awt.Color;

import javax.swing.BoundedRangeModel;
import javax.swing.JProgressBar;


// TODO: Auto-generated Javadoc
/**
 * The Class CircleProgressBar.
 */
public class CircleProgressBar extends JProgressBar {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6698145299241906290L;
	
	/** The ui. */
	private ProgressCircleUI ui;

	/**
	 * Instantiates a new circle progress bar.
	 */
	public CircleProgressBar() {
		super();
		initUI();
	}

	/**
	 * Instantiates a new circle progress bar.
	 *
	 * @param arg0 the arg0
	 */
	public CircleProgressBar(BoundedRangeModel arg0) {
		super(arg0);
		initUI();
	}

	/**
	 * Instantiates a new circle progress bar.
	 *
	 * @param arg0 the arg0
	 */
	public CircleProgressBar(int arg0) {
		super(arg0);
		initUI();
	}

	/**
	 * Instantiates a new circle progress bar.
	 *
	 * @param arg0 the arg0
	 * @param arg1 the arg1
	 */
	public CircleProgressBar(int arg0, int arg1) {
		super(arg0, arg1);
		initUI();
	}

	/**
	 * Instantiates a new circle progress bar.
	 *
	 * @param arg0 the arg0
	 * @param arg1 the arg1
	 * @param arg2 the arg2
	 */
	public CircleProgressBar(int arg0, int arg1, int arg2) {
		super(arg0, arg1, arg2);
		initUI();
	}

	/**
	 * Get back color.
	 * 
	 * @return the backColor
	 */
	public Color getBackColor() {
		return ui.getBackColor();
	}

	/**
	 * Get bar thickness.
	 * 
	 * @return bar thickness
	 */
	public double getBarThickness() {
		return ui.getBarThickness();
	}

	/**
	 * Inits the ui.
	 */
	private void initUI() {
		setStringPainted(true);
		ui = new ProgressCircleUI();
		setUI(ui);
	}

	/**
	 * Set back color.
	 * 
	 * @param backColor
	 *            the backColor to set
	 */
	public void setBackColor(Color backColor) {
		ui.setBackColor(backColor);
	}

	/**
	 * Set bar thickness.
	 * 
	 * @param barThickness
	 *            bar thickness between 0.0 and 1.0
	 */
	public void setBarThickness(double barThickness) {
		ui.setBarThickness(barThickness);
	}

}
