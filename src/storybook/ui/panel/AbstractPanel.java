package storybook.ui.panel;

import java.beans.PropertyChangeEvent;

import javax.swing.JPanel;

import storybook.ui.MainFrame;
import storybook.ui.interfaces.IPaintable;
import storybook.ui.interfaces.IRefreshable;


// TODO: Auto-generated Javadoc
/**
 * The Class AbstractPanel.
 */
public abstract class AbstractPanel extends JPanel implements IRefreshable, IPaintable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7603002294986752656L;
	
	/** The main frame. */
	protected MainFrame mainFrame;

	/**
	 * Instantiates a new abstract panel.
	 */
	public AbstractPanel() {
	}

	/**
	 * Instantiates a new abstract panel.
	 *
	 * @param mainframe the mainframe
	 */
	public AbstractPanel(MainFrame mainframe) {
		mainFrame = mainframe;
	}

	/**
	 * Gets the main frame.
	 *
	 * @return the main frame
	 */
	public MainFrame getMainFrame() {
		return mainFrame;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.interfaces.IPaintable#init()
	 */
	@Override
	public abstract void init();

	/**
	 * Inits the all.
	 */
	public void initAll() {
		init();
		initUi();
	}

	/* (non-Javadoc)
	 * @see storybook.ui.interfaces.IPaintable#initUi()
	 */
	@Override
	public abstract void initUi();

	/**
	 * Model property change.
	 *
	 * @param evt the evt
	 */
	public abstract void modelPropertyChange(PropertyChangeEvent evt);

	/* (non-Javadoc)
	 * @see storybook.ui.interfaces.IRefreshable#refresh()
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
}
