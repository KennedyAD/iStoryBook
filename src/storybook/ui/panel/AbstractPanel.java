package storybook.ui.panel;

import java.beans.PropertyChangeEvent;

import javax.swing.JPanel;

import storybook.ui.MainFrame;
import storybook.ui.interfaces.IPaintable;
import storybook.ui.interfaces.IRefreshable;

@SuppressWarnings("serial")
public abstract class AbstractPanel extends JPanel implements IRefreshable, IPaintable {

	protected MainFrame mainFrame;

	public AbstractPanel() {
	}

	public AbstractPanel(MainFrame mainframe) {
		mainFrame = mainframe;
	}

	public MainFrame getMainFrame() {
		return mainFrame;
	}

	@Override
	public abstract void init();

	public void initAll() {
		init();
		initUi();
	}

	@Override
	public abstract void initUi();

	public abstract void modelPropertyChange(PropertyChangeEvent evt);

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
