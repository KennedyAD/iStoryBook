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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.InputMap;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.ToolBarUI;
import javax.swing.plaf.basic.BasicToolBarUI;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import net.atlanticbb.tantlinger.ui.text.WysiwygHTMLEditorKit;
import net.miginfocom.swing.MigLayout;
import storybook.SbConstants.Language;
import storybook.SbConstants.LookAndFeel;
import storybook.SbConstants.PreferenceKey;
import storybook.SbConstants.Spelling;
import storybook.model.hbn.entity.Preference;
import storybook.toolkit.BookUtil;
import storybook.toolkit.I18N;
import storybook.toolkit.PrefUtil;
import storybook.toolkit.swing.undo.UndoableTextArea;
import storybook.ui.MainFrame;

// TODO: Auto-generated Javadoc
/**
 * The Class SwingUtil.
 */
public class SwingUtil {

	/** The flash is running. */
	private static Boolean flashIsRunning = false;

	/**
	 * Adds the copy paste to popup menu.
	 *
	 * @param menu the menu
	 * @param comp the comp
	 */
	public static void addCopyPasteToPopupMenu(JPopupMenu menu, JComponent comp) {
		HashMap<Object, Action> actions = SwingUtil.createActionTable((JTextComponent) comp);
		Action cutAction = actions.get(DefaultEditorKit.cutAction);
		JMenuItem miCut = new JMenuItem(cutAction);
		miCut.setText(I18N.getMsg("msg.common.cut"));
		miCut.setIcon(I18N.getIcon("icon.small.cut"));
		menu.add(miCut);
		Action copyAction = actions.get(DefaultEditorKit.copyAction);
		JMenuItem miCopy = new JMenuItem(copyAction);
		miCopy.setText(I18N.getMsg("msg.common.copy"));
		miCopy.setIcon(I18N.getIcon("icon.small.copy"));
		menu.add(miCopy);
		Action pasteAction = actions.get(DefaultEditorKit.pasteAction);
		JMenuItem miPaste = new JMenuItem(pasteAction);
		miPaste.setText(I18N.getMsg("msg.common.paste"));
		miPaste.setIcon(I18N.getIcon("icon.small.paste"));
		menu.add(miPaste);
	}

	/**
	 * Adds the copy to popup menu.
	 *
	 * @param menu the menu
	 * @param comp the comp
	 */
	public static void addCopyToPopupMenu(JPopupMenu menu, JComponent comp) {
		HashMap<Object, Action> actions = SwingUtil.createActionTable((JTextComponent) comp);
		Action copyAction = actions.get(DefaultEditorKit.copyAction);
		JMenuItem miCopy = new JMenuItem(copyAction);
		miCopy.setText(I18N.getMsg("msg.common.copy"));
		miCopy.setIcon(I18N.getIcon("icon.small.copy"));
		menu.add(miCopy);
	}

	/**
	 * Adds the ctrl enter action.
	 *
	 * @param comp the comp
	 * @param action the action
	 */
	public static void addCtrlEnterAction(JComponent comp, AbstractAction action) {
		InputMap inputMap = comp.getInputMap();
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_DOWN_MASK), action);
	}

	/**
	 * Adds the enter action.
	 *
	 * @param comp the comp
	 * @param action the action
	 */
	public static void addEnterAction(JComponent comp, Action action) {
		comp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), action);
		comp.getActionMap().put(action, action);
	}

	/**
	 * Adds the esc action.
	 *
	 * @param comp the comp
	 * @param action the action
	 */
	public static void addEscAction(JComponent comp, Action action) {
		comp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), action);
		comp.getActionMap().put(action, action);
	}

	/**
	 * Creates the action table.
	 *
	 * @param textComponent the text component
	 * @return the hash map
	 */
	public static HashMap<Object, Action> createActionTable(JTextComponent textComponent) {
		HashMap<Object, Action> actions = new HashMap<>();
		Action[] actionsArray = textComponent.getActions();
		for (int i = 0; i < actionsArray.length; i++) {
			Action a = actionsArray[i];
			actions.put(a.getValue(Action.NAME), a);
		}
		return actions;
	}

	/**
	 * Creates the date format.
	 *
	 * @return the j combo box
	 */
	public static JComboBox<String> createDateFormat() {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		model.addElement("dd-MM-yyyy");
		model.addElement("MM-dd-yyyy");
		model.addElement("dd/MM/yyyy");
		return new JComboBox<String>(model);
	}

	/**
	 * Creates the horizontal line label.
	 *
	 * @return the j label
	 */
	public static JLabel createHorizontalLineLabel() {
		MatteBorder mb = new MatteBorder(0, 0, 1, 0, Color.black);
		JLabel label = new JLabel();
		label.setBorder(mb);
		return label;
	}

	/**
	 * Creates the language combo.
	 *
	 * @return the j combo box
	 */
	public static JComboBox<String> createLanguageCombo() {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		for (Language lang : Language.values()) {
			model.addElement(lang.getI18N());
		}
		return new JComboBox<String>(model);
	}

	/**
	 * Creates the menu bar spacer.
	 *
	 * @return the j panel
	 */
	public static JPanel createMenuBarSpacer() {
		return createMenuBarSpacer(false);
	}

	/**
	 * Creates the menu bar spacer.
	 *
	 * @param linie the linie
	 * @return the j panel
	 */
	public static JPanel createMenuBarSpacer(boolean linie) {
		MigLayout layout = new MigLayout("insets 0", "[1]");
		JPanel panel = new JPanel(layout);
		panel.setOpaque(false);
		JLabel label = new JLabel(" ");
		if (linie) {
			Border border = BorderFactory.createMatteBorder(0, 1, 0, 0, Color.gray);
			label.setBorder(border);
			panel.add(label, "center");
		}
		panel.setMaximumSize(new Dimension(2, 10));
		return panel;
	}

	/**
	 * Creates the notes panel.
	 *
	 * @param taNotes the ta notes
	 * @return the j panel
	 */
	public static JPanel createNotesPanel(JTextArea taNotes) {
		MigLayout layout = new MigLayout("fill", "", "[top]");
		JPanel panel = new JPanel(layout);
		taNotes.setLineWrap(true);
		taNotes.setWrapStyleWord(true);
		taNotes.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		JScrollPane scroller = new JScrollPane(taNotes);
		scroller.setPreferredSize(new Dimension(400, 400));
		panel.add(scroller, "grow");
		return panel;
	}

	/**
	 * Creates a JSlider and ensure the given value is between min and max.
	 *
	 * @param orientation
	 *            the orientation of the slider
	 * @param min
	 *            the minimum value of the slider
	 * @param max
	 *            the maximum value of the slider
	 * @param value
	 *            the initial value of the slider
	 * @return the JSlider
	 */
	public static JSlider createSafeSlider(int orientation, int min, int max, int value) {
		if (value < min)
			value = min;
		else if (value > max)
			value = max;
		return new JSlider(orientation, min, max, value);
	}

	/**
	 * Creates the spelling combo.
	 *
	 * @return the j combo box
	 */
	public static JComboBox<String> createSpellingCombo() {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		for (Spelling spelling : Spelling.values()) {
			if (isLanguageOK(spelling.name()))
				model.addElement(spelling.getI18N());
		}
		return new JComboBox<String>(model);
	}

	/**
	 * Creates the text component.
	 *
	 * @param mainFrame the main frame
	 * @return the j text component
	 */
	public static JTextComponent createTextComponent(MainFrame mainFrame) {
		JTextComponent tc;
		if (BookUtil.isUseHtmlScenes(mainFrame)) {
			tc = new JEditorPane();
			JEditorPane ep = (JEditorPane) tc;
			ep.setEditorKitForContentType("text/html", new WysiwygHTMLEditorKit());
			ep.setContentType("text/html");
		} else {
			tc = new UndoableTextArea();
			UndoableTextArea ta = (UndoableTextArea) tc;
			ta.setLineWrap(true);
			ta.setWrapStyleWord(true);
			ta.getUndoManager().discardAllEdits();
		}
		return tc;
	}

	/**
	 * Creates the timestamp label.
	 *
	 * @return the j label
	 */
	public static JLabel createTimestampLabel() {
		Date date = new Date();
		String dateStr = FastDateFormat.getDateInstance(FastDateFormat.MEDIUM).format(date);
		String timeStr = FastDateFormat.getTimeInstance(FastDateFormat.MEDIUM).format(date);
		return new JLabel(dateStr + " - " + timeStr);
	}

	/**
	 * Creates the vertical line label.
	 *
	 * @return the j label
	 */
	public static JLabel createVerticalLineLabel() {
		MatteBorder mb = new MatteBorder(0, 1, 0, 0, Color.black);
		JLabel label = new JLabel();
		label.setBorder(mb);
		return label;
	}

	/**
	 * Enables or disables all children of the given container.
	 *
	 * @param container
	 *            the container
	 * @param enable
	 *            if true, the components are enabled, otherwise they are
	 *            disabled
	 */
	public static void enableContainerChildren(Container container, boolean enable) {
		if (container == null)
			return;
		for (Component comp : container.getComponents()) {
			try {
				comp.setEnabled(enable);
				((JComponent) comp).setOpaque(enable);
				if (comp instanceof Container)
					enableContainerChildren((Container) comp, enable);
			} catch (ClassCastException e) {
				// ignore component
				continue;
			}
		}
	}

	/**
	 * Expand rectangle.
	 *
	 * @param rect the rect
	 */
	public static void expandRectangle(Rectangle rect) {
		Point p = rect.getLocation();
		p.translate(-5, -5);
		rect.setLocation(p);
		rect.grow(10, 10);
	}

	/**
	 * Find component by name.
	 *
	 * @param rootComponent the root component
	 * @param name the name
	 * @return the component
	 */
	public static Component findComponentByName(Component rootComponent, String name) {
		if (rootComponent.getName() != null)
			if (rootComponent.getName().equals(name))
				return rootComponent;
		if (rootComponent instanceof Container) {
			Component[] components = ((Container) rootComponent).getComponents();
			for (int i = 0; i < components.length; ++i) {
				Component comp = findComponentByName(components[i], name);
				if (comp != null)
					return comp;
			}
		}
		return null;
	}

	/**
	 * Find components by class.
	 *
	 * @param rootComponent the root component
	 * @param cname the cname
	 * @param res the res
	 * @return the list
	 */
	public static List<Component> findComponentsByClass(Container rootComponent, Class<? extends Component> cname,
			List<Component> res) {
		if (rootComponent instanceof Container) {
			Component[] components = rootComponent.getComponents();
			for (Component comp : components) {
				if (cname.isInstance(comp))
					res.add(comp);
				if (comp instanceof Container)
					findComponentsByClass((Container) comp, cname, res);
			}
		}
		return res;
	}

	/**
	 * Find components name starts with.
	 *
	 * @param rootComponent the root component
	 * @param startsWith the starts with
	 * @param res the res
	 * @return the list
	 */
	public static List<Component> findComponentsNameStartsWith(Container rootComponent, String startsWith,
			List<Component> res) {
		if (rootComponent instanceof Container) {
			Component[] components = rootComponent.getComponents();
			for (Component comp : components) {
				String name = comp.getName();
				if (name != null && name.startsWith(startsWith))
					res.add(comp);
				if (comp instanceof Container)
					findComponentsNameStartsWith((Container) comp, startsWith, res);
			}
		}
		return res;
	}

	/**
	 * Find parent.
	 *
	 * @param c the c
	 * @return the component
	 */
	public static Component findParent(Component c) {
		if (c == null)
			return null;
		Component parent = c.getParent();
		Component comp = null;
		while (parent != null) {
			comp = parent;
			parent = comp.getParent();
		}
		return comp;
	}

	/**
	 * Find parent j frame.
	 *
	 * @param c the c
	 * @return the j frame
	 */
	public static JFrame findParentJFrame(JComponent c) {
		if (c == null)
			return null;
		Component parent = c.getParent();
		while (!(parent instanceof JFrame) && (parent != null)) {
			parent = parent.getParent();
		}
		return (JFrame) parent;
	}

	/**
	 * Find parent j tabbed pane.
	 *
	 * @param c the c
	 * @return the j tabbed pane
	 */
	public static JTabbedPane findParentJTabbedPane(JComponent c) {
		if (c == null)
			return null;
		Component parent = c.getParent();
		while (!(parent instanceof JTabbedPane) && (parent != null)) {
			parent = parent.getParent();
		}
		return (JTabbedPane) parent;
	}

	/**
	 * Flashes the given {@link Component} for 250 milliseconds.
	 *
	 * @param comp
	 *            the component to flash
	 */
	public static void flashComponent(JComponent comp) {
		synchronized (flashIsRunning) {
			if (flashIsRunning)
				return;
			flashIsRunning = true;
			FlashThread flash = new FlashThread(comp);
			SwingUtilities.invokeLater(flash);
			FlashThread flash2 = new FlashThread(comp, true);
			Timer timer = new Timer(1000, flash2);
			timer.setRepeats(false);
			timer.start();
		}
	}

	/**
	 * Flash ended.
	 */
	public static void flashEnded() {
		synchronized (flashIsRunning) {
			flashIsRunning = false;
		}
	}

	/**
	 * Float tool bar.
	 *
	 * @param tb the tb
	 * @param p the p
	 */
	public static void floatToolBar(JToolBar tb, Point p) {
		ToolBarUI tbUI = tb.getUI();
		if (tbUI instanceof BasicToolBarUI)
			((BasicToolBarUI) tbUI).setFloating(false, p);
	}

	/**
	 * Force revalidate.
	 *
	 * @param comp the comp
	 */
	public static void forceRevalidate(Component comp) {
		comp.invalidate();
		comp.validate();
		comp.repaint();
	}

	/**
	 * Formate component infos to print.
	 *
	 * @param comp the comp
	 * @return the string
	 */
	public static String formateComponentInfosToPrint(Component comp) {
		StringBuilder buf = new StringBuilder();
		buf.append(comp.getClass().getSimpleName());
		buf.append(" [");
		buf.append(comp.getName());
		if (comp instanceof JLabel)
			buf.append(",\"").append(((JLabel) comp).getText()).append("\"");
		buf.append(",");
		buf.append(comp.getClass().getName());
		buf.append(",");
		buf.append(comp.isVisible() ? "visible" : "not visible");
		buf.append(",");
		buf.append(comp.isValid() ? "valid" : "invalid");
		buf.append("]");
		return buf.toString();
	}

	/**
	 * Gets the background color.
	 *
	 * @return the background color
	 */
	public static Color getBackgroundColor() {
		return Color.white;
	}

	/**
	 * Gets the border.
	 *
	 * @param clr the clr
	 * @return the border
	 */
	public static Border getBorder(Color clr) {
		return BorderFactory.createLineBorder(clr, 1);
	}

	/**
	 * Gets the border.
	 *
	 * @param clr the clr
	 * @param thickness the thickness
	 * @return the border
	 */
	public static Border getBorder(Color clr, int thickness) {
		return BorderFactory.createLineBorder(clr, thickness);
	}

	/**
	 * Gets the border blue.
	 *
	 * @return the border blue
	 */
	public static Border getBorderBlue() {
		return getBorderBlue(1);
	}

	/**
	 * Gets the border blue.
	 *
	 * @param thickness the thickness
	 * @return the border blue
	 */
	public static Border getBorderBlue(int thickness) {
		return BorderFactory.createLineBorder(Color.blue, thickness);
	}

	/**
	 * Gets the border default.
	 *
	 * @return the border default
	 */
	public static Border getBorderDefault() {
		return getBorderDefault(1);
	}

	/**
	 * Gets the border default.
	 *
	 * @param thickness the thickness
	 * @return the border default
	 */
	public static Border getBorderDefault(int thickness) {
		return BorderFactory.createLineBorder(Color.black, thickness);
	}

	/**
	 * Gets the border dot.
	 *
	 * @return the border dot
	 */
	public static Border getBorderDot() {
		return new DotBorder();
	}

	/**
	 * Gets the border etched.
	 *
	 * @return the border etched
	 */
	public static Border getBorderEtched() {
		return BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
	}

	/**
	 * Gets the border gray.
	 *
	 * @return the border gray
	 */
	public static Border getBorderGray() {
		return BorderFactory.createLineBorder(Color.gray);
	}

	/**
	 * Gets the border light gray.
	 *
	 * @return the border light gray
	 */
	public static Border getBorderLightGray() {
		return BorderFactory.createLineBorder(Color.lightGray);
	}

	/**
	 * Gets the border red.
	 *
	 * @return the border red
	 */
	public static Border getBorderRed() {
		return BorderFactory.createLineBorder(Color.red);
	}

	/**
	 * Gets the component index.
	 *
	 * @param container the container
	 * @param component the component
	 * @return the component index
	 */
	public static int getComponentIndex(Container container, Component component) {
		int index = 0;
		for (Component comp : container.getComponents()) {
			if (comp == component)
				return index;
			++index;
		}
		return -1;
	}

	/**
	 * Gets the compound border.
	 *
	 * @param text the text
	 * @return the compound border
	 */
	public static CompoundBorder getCompoundBorder(String text) {
		return BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(text),
				BorderFactory.createEmptyBorder(5, 5, 5, 5));
	}

	/**
	 * Gets the day name.
	 *
	 * @param date the date
	 * @return the day name
	 */
	public static String getDayName(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
		return sdf.format(date);
	}

	/**
	 * Gets the default storke.
	 *
	 * @return the default storke
	 */
	public static Stroke getDefaultStorke() {
		return new BasicStroke();
	}

	/**
	 * Gets the dot stroke.
	 *
	 * @return the dot stroke
	 */
	public static Stroke getDotStroke() {
		int w = 1;
		float[] dash = { 1, 3 };
		float dash_phase = 1;
		return new BasicStroke(w, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10, dash, dash_phase);
	}

	/**
	 * Gets the dot stroke2.
	 *
	 * @return the dot stroke2
	 */
	public static Stroke getDotStroke2() {
		int w = 1;
		float[] dash = { 6, 3 };
		float dash_phase = 2;
		return new BasicStroke(w, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10, dash, dash_phase);
	}

	/**
	 * Gets the font bold.
	 *
	 * @param size the size
	 * @return the font bold
	 */
	public static Font getFontBold(int size) {
		return new Font("Dialog", Font.BOLD, size);
	}

	/**
	 * Gets the frame.
	 *
	 * @param frameName the frame name
	 * @return the frame
	 */
	public static Frame getFrame(String frameName) {
		Frame[] frames = Frame.getFrames();
		for (Frame frame : frames) {
			if (frame.getName().equals(frameName))
				return frame;
		}
		return null;
	}

	/**
	 * Gets the human readable byte count.
	 *
	 * @param bytes the bytes
	 * @return the human readable byte count
	 */
	public static String getHumanReadableByteCount(long bytes) {
		return getHumanReadableByteCount(bytes, true);
	}

	/**
	 * Gets the human readable byte count.
	 *
	 * @param bytes the bytes
	 * @param si the si
	 * @return the human readable byte count
	 */
	public static String getHumanReadableByteCount(long bytes, boolean si) {
		int unit = si ? 1000 : 1024;
		if (bytes < unit)
			return bytes + " B";
		int exp = (int) (Math.log(bytes) / Math.log(unit));
		String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
		return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}

	/**
	 * Gets the key stroke copy.
	 *
	 * @return the key stroke copy
	 */
	public static KeyStroke getKeyStrokeCopy() {
		return KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK, false);
	}

	/**
	 * Gets the key stroke delete.
	 *
	 * @return the key stroke delete
	 */
	public static KeyStroke getKeyStrokeDelete() {
		return KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0, false);
	}

	/**
	 * Gets the key stroke enter.
	 *
	 * @return the key stroke enter
	 */
	public static KeyStroke getKeyStrokeEnter() {
		return KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false);
	}

	/**
	 * Gets the key stroke insert.
	 *
	 * @return the key stroke insert
	 */
	public static KeyStroke getKeyStrokeInsert() {
		return KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, 0, false);
	}

	/**
	 * Gets the memory free.
	 *
	 * @return the memory free
	 */
	public static long getMemoryFree() {
		return Runtime.getRuntime().freeMemory();
	}

	/**
	 * Gets the memory max.
	 *
	 * @return the memory max
	 */
	public static long getMemoryMax() {
		return Runtime.getRuntime().maxMemory();
	}

	/**
	 * Gets the memory total.
	 *
	 * @return the memory total
	 */
	public static long getMemoryTotal() {
		return Runtime.getRuntime().totalMemory();
	}

	/**
	 * Gets the memory usage hr.
	 *
	 * @return the memory usage hr
	 */
	public static String getMemoryUsageHr() {
		return "Memory Usage (used/free/total/max): " + getHumanReadableByteCount(getMemoryUsed()) + " / "
				+ getHumanReadableByteCount(getMemoryFree()) + " / " + getHumanReadableByteCount(getMemoryTotal())
				+ " / " + getHumanReadableByteCount(getMemoryMax());
	}

	/**
	 * Gets the memory usage simple hr.
	 *
	 * @return the memory usage simple hr
	 */
	public static String getMemoryUsageSimpleHr() {
		return getHumanReadableByteCount(getMemoryUsed()) + " / " + getHumanReadableByteCount(getMemoryMax());
	}

	/**
	 * Gets the memory used.
	 *
	 * @return the memory used
	 */
	public static long getMemoryUsed() {
		return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
	}

	/**
	 * Gets the nice font name.
	 *
	 * @param font the font
	 * @return the nice font name
	 */
	public static String getNiceFontName(Font font) {
		if (font == null)
			return "";
		StringBuilder buf = new StringBuilder();
		buf.append(font.getName());
		buf.append(", ");
		switch (font.getStyle()) {
		case Font.BOLD:
			buf.append("bold");
			break;
		case Font.ITALIC:
			buf.append("italic");
			break;
		case Font.PLAIN:
			buf.append("plain");
			break;
		}
		buf.append(", ");
		buf.append(font.getSize());
		return buf.toString();
	}

	/**
	 * Gets the preferred height.
	 *
	 * @param comp the comp
	 * @return the preferred height
	 */
	public static int getPreferredHeight(Component comp) {
		return new Double(comp.getPreferredSize().getHeight()).intValue();
	}

	/**
	 * Gets the preferred width.
	 *
	 * @param comp the comp
	 * @return the preferred width
	 */
	public static int getPreferredWidth(Component comp) {
		return new Double(comp.getPreferredSize().getWidth()).intValue();
	}

	/**
	 * Gets the dimension of the screen.
	 *
	 * @return the dimension of the screen
	 */
	public static Dimension getScreenSize() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		return d;
	}

	/**
	 * Gets the shorten string.
	 *
	 * @param str the str
	 * @param length the length
	 * @return the shorten string
	 */
	public static String getShortenString(String str, int length) {
		if (str.length() > length)
			return StringUtils.left(str, length) + " ...";
		return str;
	}

	/**
	 * Gets the storke.
	 *
	 * @return the storke
	 */
	public static Stroke getStorke() {
		return new BasicStroke(1);
	}

	/**
	 * Gets the table background color.
	 *
	 * @return the table background color
	 */
	public static Color getTableBackgroundColor() {
		return getTableBackgroundColor(false);
	}

	/**
	 * Gets the table background color.
	 *
	 * @param colored the colored
	 * @return the table background color
	 */
	public static Color getTableBackgroundColor(boolean colored) {
		if (colored)
			return new Color(0xf4f4f4);
		return UIManager.getColor("Table.background");
	}

	/**
	 * Gets the table header color.
	 *
	 * @return the table header color
	 */
	public static Color getTableHeaderColor() {
		return UIManager.getColor("TableHeader.background");
	}

	/**
	 * Gets the table selection background color.
	 *
	 * @return the table selection background color
	 */
	public static Color getTableSelectionBackgroundColor() {
		return UIManager.getColor("Table.selectionBackground");
	}

	/**
	 * Gets a text file chooser. Only files with the extension ".txt" and
	 * directories are shown.
	 *
	 * @return the file chooser
	 */
	public static JFileChooser getTextFileChooser() {
		final JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
		chooser.setFileFilter(filter);
		return chooser;
	}

	/**
	 * Gets the timestamp.
	 *
	 * @param date the date
	 * @return the timestamp
	 */
	public static String getTimestamp(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
		return sdf.format(date);
	}

	/**
	 * Checks if is language ok.
	 *
	 * @param x the x
	 * @return true, if is language ok
	 */
	public static boolean isLanguageOK(String x) {
		boolean rc = false;
		if (x.contentEquals("none"))
			return (true);
		File f = new File("languagetool" + File.separator + "rules" + File.separator + x.substring(0, x.indexOf("_")));
		if (f.isDirectory())
			rc = true;
		return (rc);
	}

	/**
	 * Prints the component hierarchy.
	 *
	 * @param rootComponent the root component
	 * @return the component
	 */
	public static Component printComponentHierarchy(Component rootComponent) {
		return printComponentHierarchy(rootComponent, -1);
	}

	/**
	 * Prints the component hierarchy.
	 *
	 * @param rootComponent the root component
	 * @param level the level
	 * @return the component
	 */
	private static Component printComponentHierarchy(Component rootComponent, int level) {
		++level;
		System.out
				.println(StringUtils.repeat("    ", level) + level + ":" + formateComponentInfosToPrint(rootComponent));
		if (rootComponent instanceof Container) {
			Component[] components = ((Container) rootComponent).getComponents();
			for (int i = 0; i < components.length; ++i) {
				Component comp = printComponentHierarchy(components[i], level);
				if (comp != null)
					return comp;
			}
		}
		return null;
	}

	/**
	 * Prints the memory usage.
	 */
	public static void printMemoryUsage() {
		System.out.println(getMemoryUsageHr());
	}

	/**
	 * Prints the ui defaults.
	 */
	public static void printUIDefaults() {
		UIDefaults uiDefaults = UIManager.getDefaults();
		Enumeration<Object> e = uiDefaults.keys();
		while (e.hasMoreElements()) {
			Object key = e.nextElement();
			Object val = uiDefaults.get(key);
			System.out.println("[" + key.toString() + "]:[" + (null != val ? val.toString() : "(null)") + "]");
		}
	}

	/**
	 * Replace component.
	 *
	 * @param cont the cont
	 * @param comp1 the comp1
	 * @param comp2 the comp2
	 */
	public static void replaceComponent(Container cont, Component comp1, Component comp2) {
		replaceComponent(cont, comp1, comp2, "");
	}

	/**
	 * Replace component.
	 *
	 * @param cont the cont
	 * @param comp1 the comp1
	 * @param comp2 the comp2
	 * @param constraints the constraints
	 */
	public static void replaceComponent(Container cont, Component comp1, Component comp2, String constraints) {
		int index = -1;
		int i = 0;
		for (Component comp : cont.getComponents()) {
			if (comp.equals(comp1)) {
				index = i;
				break;
			}
			++i;
		}
		// cont.setIgnoreRepaint(true);
		cont.remove(comp1);
		cont.add(comp2, constraints, index);
		cont.validate();
		// cont.setIgnoreRepaint(false);
		cont.repaint();
	}

	/**
	 * Replace component.
	 *
	 * @param cont the cont
	 * @param index the index
	 * @param comp the comp
	 */
	public static void replaceComponent(Container cont, int index, Component comp) {
		cont.remove(index);
		// cont.validate();
		cont.add(comp, index);
		cont.validate();
		cont.repaint();
	}

	/**
	 * Select all text in a {@link JComponent} if it is a {@link JTextField} or
	 * {@link JTextArea}.
	 *
	 * @param comp
	 *            the component
	 */
	public static void selectAllText(JComponent comp) {
		if (comp instanceof JTextField) {
			JTextField tf = (JTextField) comp;
			tf.setSelectionStart(0);
			tf.setSelectionEnd(tf.getText().length());
		} else if (comp instanceof JTextArea) {
			JTextArea ta = (JTextArea) comp;
			ta.setSelectionStart(0);
			ta.setSelectionEnd(ta.getText().length());
		}
	}

	/**
	 * Sets the accelerator.
	 *
	 * @param menuItem the menu item
	 * @param key the key
	 * @param mask the mask
	 */
	public static void setAccelerator(JMenuItem menuItem, int key, int mask) {
		menuItem.setAccelerator(KeyStroke.getKeyStroke(key, mask));
	}

	/**
	 * Sets the default cursor.
	 *
	 * @param comp the new default cursor
	 */
	public static void setDefaultCursor(Component comp) {
		comp.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * Sets the forced size.
	 *
	 * @param comp the comp
	 * @param dim the dim
	 */
	public static void setForcedSize(Component comp, Dimension dim) {
		comp.setMinimumSize(dim);
		comp.setPreferredSize(dim);
		comp.setMaximumSize(dim);
	}

	/**
	 * Sets the look and feel.
	 */
	public static void setLookAndFeel() {
		try {
			// get saved look and feel
			Preference pref = PrefUtil.get(PreferenceKey.LAF, LookAndFeel.cross.name());
			LookAndFeel laf = LookAndFeel.valueOf(pref.getStringValue());
			setLookAndFeel(laf);
		} catch (Exception e) {
			setLookAndFeel(LookAndFeel.cross);
		}
	}

	/**
	 * Sets the look and feel.
	 *
	 * @param lookAndFeel the new look and feel
	 */
	public static void setLookAndFeel(LookAndFeel lookAndFeel) {
		try {
			String lafClassName = UIManager.getCrossPlatformLookAndFeelClassName();
			switch (lookAndFeel) {
			case cross:
				lafClassName = UIManager.getCrossPlatformLookAndFeelClassName();
				break;
			// case system:
			// lafClassName = UIManager.getSystemLookAndFeelClassName();
			// break;
			default:
				lafClassName = UIManager.getCrossPlatformLookAndFeelClassName();
				break;
			}
			PrefUtil.set(PreferenceKey.LAF, lookAndFeel.name());
			UIManager.setLookAndFeel(lafClassName);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
		}
	}

	/**
	 * Sets the max preferred size.
	 *
	 * @param comp the new max preferred size
	 */
	public static void setMaxPreferredSize(JComponent comp) {
		comp.setPreferredSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
	}

	/**
	 * Sets the max width.
	 *
	 * @param comp the comp
	 * @param width the width
	 */
	public static void setMaxWidth(JComponent comp, int width) {
		comp.setMaximumSize(new Dimension(width, Short.MAX_VALUE));
	}

	/**
	 * Sets the UI font.
	 *
	 * @param f the new UI font
	 */
	public static void setUIFont(javax.swing.plaf.FontUIResource f) {
		Enumeration<Object> keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof javax.swing.plaf.FontUIResource)
				UIManager.put(key, f);
		}
	}

	/**
	 * Sets the unit increment.
	 *
	 * @param scroller the new unit increment
	 */
	public static void setUnitIncrement(JScrollPane scroller) {
		scroller.getVerticalScrollBar().setUnitIncrement(20);
		scroller.getHorizontalScrollBar().setUnitIncrement(20);
	}

	/**
	 * Sets the waiting cursor.
	 *
	 * @param comp the new waiting cursor
	 */
	public static void setWaitingCursor(Component comp) {
		comp.setCursor(new Cursor(Cursor.WAIT_CURSOR));
	}

	/**
	 * Show beta dialog.
	 *
	 * @param mainFrame the main frame
	 * @return the int
	 */
	public static int showBetaDialog(MainFrame mainFrame) {
		int n = JOptionPane.showConfirmDialog(mainFrame, I18N.getMsg("msg.common.beta"),
				I18N.getMsg("msg.common.question"), JOptionPane.YES_NO_OPTION);
		return n;
	}

	/**
	 * Show dialog.
	 *
	 * @param dlg the dlg
	 * @param parent the parent
	 */
	public static void showDialog(JDialog dlg, Component parent) {
		showDialog(dlg, parent, true);
	}

	/**
	 * Show dialog.
	 *
	 * @param dlg the dlg
	 * @param parent the parent
	 * @param resizable the resizable
	 */
	public static void showDialog(JDialog dlg, Component parent, boolean resizable) {
		dlg.setResizable(resizable);
		dlg.pack();
		dlg.setLocationRelativeTo(parent);
		dlg.setVisible(true);
	}

	/**
	 * Show frame.
	 *
	 * @param frame the frame
	 * @param parent the parent
	 */
	public static void showFrame(JFrame frame, JFrame parent) {
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setLocation(parent.getX() + (parent.getWidth() - frame.getWidth()) / 2,
				parent.getY() + (parent.getHeight() - frame.getHeight()) / 2);
		frame.setVisible(true);
	}

	/**
	 * Show modal dialog.
	 *
	 * @param dlg the dlg
	 * @param parent the parent
	 */
	public static void showModalDialog(JDialog dlg, Component parent) {
		showModalDialog(dlg, parent, true);
	}

	/**
	 * Show modal dialog.
	 *
	 * @param dlg the dlg
	 * @param parent the parent
	 * @param resizable the resizable
	 */
	public static void showModalDialog(JDialog dlg, Component parent, boolean resizable) {
		dlg.setResizable(resizable);
		dlg.setModal(true);
		dlg.pack();
		dlg.setLocationRelativeTo(parent);
		dlg.setVisible(true);
	}

	/**
	 * Unfloat tool bar.
	 *
	 * @param tb the tb
	 */
	public static void unfloatToolBar(JToolBar tb) {
		ToolBarUI tbUI = tb.getUI();
		if (tbUI instanceof BasicToolBarUI)
			((BasicToolBarUI) tbUI).setFloating(false, null);
	}
}
