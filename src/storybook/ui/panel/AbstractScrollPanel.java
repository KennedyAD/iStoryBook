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

@SuppressWarnings("serial")
public abstract class AbstractScrollPanel extends AbstractPanel implements MouseWheelListener {

	private class ScrollDownAction extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			scrollVertical(6, 1);
		}
	}
	private class ScrollToLeftAction extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			scrollHorizontal(6, -1);
		}
	}

	private class ScrollToRightAction extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			scrollHorizontal(6, 1);
		}
	}

	private class ScrollUpAction extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			scrollVertical(6, -1);
		}
	}

	private class ZoomInAction extends AbstractAction {
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

	private class ZoomOutAction extends AbstractAction {
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

	protected JPanel panel;

	protected JScrollPane scroller;

	public AbstractScrollPanel() {
	}

	public AbstractScrollPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	abstract protected int getMaxZoomValue();

	abstract protected int getMinZoomValue();

	abstract protected int getZoomValue();

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

	protected void scrollHorizontal(int amount, int rotation) {
		JScrollBar sb = scroller.getHorizontalScrollBar();
		int val = sb.getValue();
		sb.setValue(val + amount * rotation * sb.getUnitIncrement());
	}

	protected void scrollVertical(int amount, int rotation) {
		JScrollBar sb = scroller.getVerticalScrollBar();
		int val = sb.getValue();
		sb.setValue(val + amount * rotation * sb.getUnitIncrement());
	}

	abstract protected void setZoomValue(int val);
}
