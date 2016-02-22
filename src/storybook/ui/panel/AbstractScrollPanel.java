package storybook.ui.panel;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

import storybook.ui.MainFrame;


// TODO: Auto-generated Javadoc
/**
 * The Class AbstractScrollPanel.
 */
public abstract class AbstractScrollPanel extends AbstractPanel implements MouseWheelListener {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7437538481070036215L;

	/**
	 * The Class ScrollDownAction.
	 */
	private class ScrollDownAction extends AbstractAction {
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 2695115491871590211L;

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			scrollVertical(6, 1);
		}
	}
	
	/**
	 * The Class ScrollToLeftAction.
	 */
	private class ScrollToLeftAction extends AbstractAction {
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = -5242480275476406144L;

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			scrollHorizontal(6, -1);
		}
	}

	/**
	 * The Class ScrollToRightAction.
	 */
	private class ScrollToRightAction extends AbstractAction {
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1046329178436472512L;

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			scrollHorizontal(6, 1);
		}
	}

	/**
	 * The Class ScrollUpAction.
	 */
	private class ScrollUpAction extends AbstractAction {
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 3147648501616756307L;

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			scrollVertical(6, -1);
		}
	}

	/**
	 * The Class ZoomInAction.
	 */
	private class ZoomInAction extends AbstractAction {
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 5900505697218489919L;

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			int val = getZoomValue();
			val += 2;
			if (val > getMaxZoomValue()) {
				val = getMaxZoomValue();
			}
			setZoomValue(val);
		}
	}

	/**
	 * The Class ZoomOutAction.
	 */
	private class ZoomOutAction extends AbstractAction {
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 6023216052002355947L;

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			int val = getZoomValue();
			val -= 2;
			if (val < getMinZoomValue()) {
				val = getMinZoomValue();
			}
			setZoomValue(val);
		}
	}

	/** The panel. */
	protected JPanel panel;

	/** The scroller. */
	protected JScrollPane scroller;

	/**
	 * Instantiates a new abstract scroll panel.
	 */
	public AbstractScrollPanel() {
	}

	/**
	 * Instantiates a new abstract scroll panel.
	 *
	 * @param mainFrame the main frame
	 */
	public AbstractScrollPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	/**
	 * Gets the max zoom value.
	 *
	 * @return the max zoom value
	 */
	abstract protected int getMaxZoomValue();

	/**
	 * Gets the min zoom value.
	 *
	 * @return the min zoom value
	 */
	abstract protected int getMinZoomValue();

	/**
	 * Gets the zoom value.
	 *
	 * @return the zoom value
	 */
	abstract protected int getZoomValue();

	/* (non-Javadoc)
	 * @see java.awt.event.MouseWheelListener#mouseWheelMoved(java.awt.event.MouseWheelEvent)
	 */
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
			int modifiers = e.getModifiers();
			if ((modifiers & InputEvent.CTRL_MASK) == InputEvent.CTRL_MASK) {
				scrollHorizontal(e.getScrollAmount(), e.getWheelRotation());
				// turn vertical scrolling into horizontal
				return;
			}
			scrollVertical(e.getScrollAmount(), e.getWheelRotation());
		}
	}

	/**
	 * Register keyboard action.
	 */
	protected void registerKeyboardAction() {
		registerKeyboardAction(new ScrollToRightAction(), KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, Event.ALT_MASK),
				WHEN_IN_FOCUSED_WINDOW);
		registerKeyboardAction(new ScrollToLeftAction(), KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, Event.ALT_MASK),
				WHEN_IN_FOCUSED_WINDOW);
		registerKeyboardAction(new ScrollUpAction(), KeyStroke.getKeyStroke(KeyEvent.VK_UP, Event.ALT_MASK),
				WHEN_IN_FOCUSED_WINDOW);
		registerKeyboardAction(new ScrollDownAction(), KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, Event.ALT_MASK),
				WHEN_IN_FOCUSED_WINDOW);
		registerKeyboardAction(new ZoomInAction(), KeyStroke.getKeyStroke(KeyEvent.VK_ADD, Event.CTRL_MASK),
				WHEN_IN_FOCUSED_WINDOW);
		registerKeyboardAction(new ZoomOutAction(), KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT, Event.CTRL_MASK),
				WHEN_IN_FOCUSED_WINDOW);
	}

	/**
	 * Scroll horizontal.
	 *
	 * @param amount the amount
	 * @param rotation the rotation
	 */
	protected void scrollHorizontal(int amount, int rotation) {
		JScrollBar sb = scroller.getHorizontalScrollBar();
		int val = sb.getValue();
		sb.setValue(val + amount * rotation * sb.getUnitIncrement());
	}

	/**
	 * Scroll vertical.
	 *
	 * @param amount the amount
	 * @param rotation the rotation
	 */
	protected void scrollVertical(int amount, int rotation) {
		JScrollBar sb = scroller.getVerticalScrollBar();
		int val = sb.getValue();
		sb.setValue(val + amount * rotation * sb.getUnitIncrement());
	}

	/**
	 * Sets the zoom value.
	 *
	 * @param val the new zoom value
	 */
	abstract protected void setZoomValue(int val);
}
