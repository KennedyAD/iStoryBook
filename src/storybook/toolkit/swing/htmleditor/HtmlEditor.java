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
package storybook.toolkit.swing.htmleditor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.undo.UndoManager;

import org.bushe.swing.action.ActionList;
import org.bushe.swing.action.ActionManager;
import org.bushe.swing.action.ActionUIFactory;

import com.inet.jortho.SpellChecker;

import net.atlanticbb.tantlinger.ui.DefaultAction;
import net.atlanticbb.tantlinger.ui.UIUtils;
import net.atlanticbb.tantlinger.ui.text.CompoundUndoManager;
import net.atlanticbb.tantlinger.ui.text.Entities;
import net.atlanticbb.tantlinger.ui.text.HTMLUtils;
import net.atlanticbb.tantlinger.ui.text.IndentationFilter;
import net.atlanticbb.tantlinger.ui.text.SourceCodeEditor;
import net.atlanticbb.tantlinger.ui.text.WysiwygHTMLEditorKit;
import net.atlanticbb.tantlinger.ui.text.actions.ClearStylesAction;
import net.atlanticbb.tantlinger.ui.text.actions.FindReplaceAction;
import net.atlanticbb.tantlinger.ui.text.actions.HTMLEditorActionFactory;
import net.atlanticbb.tantlinger.ui.text.actions.HTMLElementPropertiesAction;
import net.atlanticbb.tantlinger.ui.text.actions.HTMLFontAction;
import net.atlanticbb.tantlinger.ui.text.actions.HTMLFontColorAction;
import net.atlanticbb.tantlinger.ui.text.actions.HTMLHorizontalRuleAction;
import net.atlanticbb.tantlinger.ui.text.actions.HTMLImageAction;
import net.atlanticbb.tantlinger.ui.text.actions.HTMLInlineAction;
import net.atlanticbb.tantlinger.ui.text.actions.HTMLLineBreakAction;
import net.atlanticbb.tantlinger.ui.text.actions.HTMLLinkAction;
import net.atlanticbb.tantlinger.ui.text.actions.HTMLTableAction;
import net.atlanticbb.tantlinger.ui.text.actions.HTMLTextEditAction;
import net.atlanticbb.tantlinger.ui.text.actions.SpecialCharAction;
import net.miginfocom.swing.MigLayout;
import novaworx.syntax.SyntaxFactory;
import novaworx.textpane.SyntaxDocument;
import novaworx.textpane.SyntaxGutter;
import novaworx.textpane.SyntaxGutterBase;
import storybook.SbConstants.PreferenceKey;
import storybook.SbConstants.Spelling;
import storybook.model.hbn.entity.Preference;
import storybook.toolkit.I18N;
import storybook.toolkit.PrefUtil;
import storybook.toolkit.html.HtmlUtil;

// TODO: Auto-generated Javadoc
/**
 * Based on HTMLEditorPane by SHEF / Bob Tantlinger.<br>
 * http://shef.sourceforge.net
 *
 * @author martin
 * @author Bob Tantlinger
 */

public class HtmlEditor extends JPanel {

	// private static final I18n i18n =
	// I18n.getInstance("storybook.toolkit.shef.shef");

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7078736567838557917L;

	/**
	 * The Class CaretHandler.
	 */
	private class CaretHandler implements CaretListener {
		
		/* (non-Javadoc)
		 * @see javax.swing.event.CaretListener#caretUpdate(javax.swing.event.CaretEvent)
		 */
		@Override
		public void caretUpdate(CaretEvent e) {
			if (maxLength > 0) {
				int len = maxLength - getText().length() - 1;
				if (len < 0) {
					lbMessage.setForeground(Color.red);
				} else {
					lbMessage.setForeground(Color.black);
				}
				lbMessage.setText(I18N.getMsg("msg.editor.letters.left", len));
			}
			updateState();
		}
	}

	/**
	 * The Class ChangeTabAction.
	 */
	private class ChangeTabAction extends DefaultAction {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;
		
		/** The tab. */
		int tab;

		/**
		 * Instantiates a new change tab action.
		 *
		 * @param tab the tab
		 */
		public ChangeTabAction(int tab) {
			super((tab == 0) ? I18N.getMsg("shef.rich_text") : I18N.getMsg("shef.source"));
			this.tab = tab;
			putValue(ActionManager.BUTTON_TYPE, ActionManager.BUTTON_TYPE_VALUE_RADIO);
		}

		/* (non-Javadoc)
		 * @see org.bushe.swing.action.BasicAction#contextChanged()
		 */
		@Override
		protected void contextChanged() {
			setSelected(tabs.getSelectedIndex() == tab);
		}

		/* (non-Javadoc)
		 * @see org.bushe.swing.action.BasicAction#execute(java.awt.event.ActionEvent)
		 */
		@Override
		protected void execute(ActionEvent e) {
			tabs.setSelectedIndex(tab);
			setSelected(true);
		}
	}
	
	/**
	 * The Class FocusHandler.
	 */
	private class FocusHandler implements FocusListener {

		/* (non-Javadoc)
		 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
		 */
		@Override
		public void focusGained(FocusEvent e) {
			if (e.getComponent() instanceof JEditorPane) {
				JEditorPane ed = (JEditorPane) e.getComponent();
				CompoundUndoManager.updateUndo(ed.getDocument());
				focusedEditor = ed;

				updateState();
				// updateEnabledStates();
			}
		}

		/* (non-Javadoc)
		 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
		 */
		@Override
		public void focusLost(FocusEvent e) {

			if (e.getComponent() instanceof JEditorPane) {
				// focusedEditor = null;
				// wysiwygUpdated();
			}
		}
	}

	/**
	 * The Class FontChangeHandler.
	 */
	private class FontChangeHandler implements ActionListener {

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == fontFamilyCombo && focusedEditor == wysEditor) {
				// MutableAttributeSet tagAttrs = new SimpleAttributeSet();
				HTMLDocument document = (HTMLDocument) focusedEditor.getDocument();
				CompoundUndoManager.beginCompoundEdit(document);

				if (fontFamilyCombo.getSelectedIndex() != 0) {
					HTMLUtils.setFontFamily(wysEditor, fontFamilyCombo.getSelectedItem().toString());
				} else {
					HTMLUtils.setFontFamily(wysEditor, null);
				}
				CompoundUndoManager.endCompoundEdit(document);
			}
		}

		/**
		 * Item state changed.
		 *
		 * @param e the e
		 */
		public void itemStateChanged(ItemEvent e) {
		}
	}
	
	/**
	 * The Class ParagraphComboHandler.
	 */
	private class ParagraphComboHandler implements ActionListener {

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == paragraphCombo) {
				Action a = (Action) (paragraphCombo.getSelectedItem());
				a.actionPerformed(e);
			}
		}
	}
	
	/**
	 * The Class ParagraphComboRenderer.
	 */
	private class ParagraphComboRenderer extends DefaultListCellRenderer {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/* (non-Javadoc)
		 * @see javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
		 */
		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			if (value instanceof Action) {
				value = ((Action) value).getValue(Action.NAME);
			}

			return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		}
	}
	
	/**
	 * The Class PopupHandler.
	 */
	private class PopupHandler extends MouseAdapter {

		/**
		 * Check for popup trigger.
		 *
		 * @param e the e
		 */
		private void checkForPopupTrigger(MouseEvent e) {
			if (e.isPopupTrigger()) {
				JPopupMenu p;
				if (e.getSource() == wysEditor) {
					p = wysPopupMenu;
				} else if (e.getSource() == srcEditor) {
					p = srcPopupMenu;
				} else {
					return;
				}
				p.show(e.getComponent(), e.getX(), e.getY());
			}
		}

		/* (non-Javadoc)
		 * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
		 */
		@Override
		public void mousePressed(MouseEvent e) {
			checkForPopupTrigger(e);
		}

		/* (non-Javadoc)
		 * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseReleased(MouseEvent e) {
			checkForPopupTrigger(e);
		}
	}
	
	/**
	 * The Class TextChangedHandler.
	 */
	private class TextChangedHandler implements DocumentListener {

		/* (non-Javadoc)
		 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
		 */
		@Override
		public void changedUpdate(DocumentEvent e) {
			textChanged();
		}

		/* (non-Javadoc)
		 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
		 */
		@Override
		public void insertUpdate(DocumentEvent e) {
			textChanged();
		}

		/* (non-Javadoc)
		 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
		 */
		@Override
		public void removeUpdate(DocumentEvent e) {
			textChanged();
		}

		/**
		 * Text changed.
		 */
		private void textChanged() {
			if (tabs.getSelectedIndex() == 0) {
				isWysTextChanged = true;
			}
		}
	}
	
	/** The Constant INVALID_TAGS. */
	private static final String INVALID_TAGS[] = { "html", "head", "body", "title" };
	
	/** The max length. */
	private int maxLength = -1;
	
	/** The show full toolbar. */
	private boolean showFullToolbar = true;

	/** The wys editor. */
	private JEditorPane wysEditor;
	
	/** The src editor. */
	private SourceCodeEditor srcEditor;
	
	/** The focused editor. */
	private JEditorPane focusedEditor;

	/** The font family combo. */
	private JComboBox<String> fontFamilyCombo;

	/** The paragraph combo. */
	private JComboBox<String> paragraphCombo;

	/** The tabs. */
	private JTabbedPane tabs;
	
	/** The format tool bar. */
	// private JMenuBar menuBar;
	private JToolBar formatToolBar;
	
	/** The lb message. */
	private JLabel lbMessage;
	
	/** The edit menu. */
	private JMenu editMenu;
	
	/** The format menu. */
	private JMenu formatMenu;
	
	/** The insert menu. */
	private JMenu insertMenu;

	/** The src popup menu. */
	private JPopupMenu wysPopupMenu, srcPopupMenu;

	/** The action list. */
	private ActionList actionList;

	/** The focus handler. */
	private final FocusListener focusHandler = new FocusHandler();

	/** The text changed handler. */
	private final DocumentListener textChangedHandler = new TextChangedHandler();

	/** The font change handler. */
	private final ActionListener fontChangeHandler = new FontChangeHandler();

	/** The paragraph combo handler. */
	private final ActionListener paragraphComboHandler = new ParagraphComboHandler();

	/** The caret handler. */
	private final CaretListener caretHandler = new CaretHandler();

	/** The popup handler. */
	private final MouseListener popupHandler = new PopupHandler();

	/** The is wys text changed. */
	private boolean isWysTextChanged;

	/**
	 * Instantiates a new html editor.
	 */
	public HtmlEditor() {
		initUI();
	}

	/**
	 * Instantiates a new html editor.
	 *
	 * @param showFullToolbar the show full toolbar
	 */
	public HtmlEditor(boolean showFullToolbar) {
		this.showFullToolbar = showFullToolbar;
		initUI();
	}

	/**
	 * Adds the to tool bar.
	 *
	 * @param toolbar the toolbar
	 * @param act the act
	 */
	private void addToToolBar(JToolBar toolbar, Action act) {
		addToToolBar(toolbar, act, "");
	}

	/**
	 * Adds the to tool bar.
	 *
	 * @param toolbar the toolbar
	 * @param act the act
	 * @param options the options
	 */
	private void addToToolBar(JToolBar toolbar, Action act, String options) {
		AbstractButton button = ActionUIFactory.getInstance().createButton(act);
		configToolbarButton(button);
		toolbar.add(button, options);
	}

	/**
	 * Config toolbar button.
	 *
	 * @param button the button
	 */
	private void configToolbarButton(AbstractButton button) {
		button.setText(null);
		button.setMnemonic(0);
		button.setMargin(new Insets(1, 1, 1, 1));
		button.setMaximumSize(new Dimension(22, 22));
		button.setMinimumSize(new Dimension(22, 22));
		button.setPreferredSize(new Dimension(22, 22));
		button.setFocusable(false);
		button.setFocusPainted(false);
		// button.setBorder(plainBorder);
		Action a = button.getAction();
		if (a != null) {
			button.setToolTipText(a.getValue(Action.NAME).toString());
		}
	}

	/**
	 * Creates the editor actions.
	 */
	private void createEditorActions() {
		actionList = new ActionList("editor-actions");

		ActionList paraActions = new ActionList("paraActions");
		ActionList fontSizeActions = new ActionList("fontSizeActions");
		ActionList editActions = HTMLEditorActionFactory.createEditActionList();
		Action objectPropertiesAction = new HTMLElementPropertiesAction();

		// create popup menu
		wysPopupMenu = ActionUIFactory.getInstance().createPopupMenu(editActions);
		wysPopupMenu.addSeparator();
		wysPopupMenu.add(objectPropertiesAction);
		wysPopupMenu.addSeparator();

		// spell checker
		Preference pref = PrefUtil.get(PreferenceKey.SPELLING, Spelling.none.toString());
		Spelling spelling = Spelling.valueOf(pref.getStringValue());
		if (Spelling.none != spelling) {
			wysPopupMenu.add(SpellChecker.createCheckerMenu());
			wysPopupMenu.add(SpellChecker.createLanguagesMenu());
			wysPopupMenu.addSeparator();
		}

		// open URL
		wysPopupMenu.add(new OpenUrlAction(wysEditor));

		srcPopupMenu = ActionUIFactory.getInstance().createPopupMenu(editActions);

		new JMenu(I18N.getMsg("shef.file"));

		// create edit menu
		ActionList lst = new ActionList("edits");
		Action act = new ChangeTabAction(0);
		lst.add(act);
		act = new ChangeTabAction(1);
		lst.add(act);
		lst.add(null);// separator
		lst.addAll(editActions);
		lst.add(null);
		lst.add(new FindReplaceAction(false));
		actionList.addAll(lst);
		editMenu = ActionUIFactory.getInstance().createMenu(lst);
		editMenu.setText(I18N.getMsg("shef.edit"));

		// create format menu
		formatMenu = new JMenu(I18N.getMsg("shef.format"));
		lst = HTMLEditorActionFactory.createFontSizeActionList();
		// HTMLEditorActionFactory.createInlineActionList();
		actionList.addAll(lst);
		formatMenu.add(createMenu(lst, I18N.getMsg("shef.size")));
		fontSizeActions.addAll(lst);

		lst = HTMLEditorActionFactory.createInlineActionList();
		actionList.addAll(lst);
		formatMenu.add(createMenu(lst, I18N.getMsg("shef.style")));

		act = new HTMLFontColorAction();
		actionList.add(act);
		formatMenu.add(act);

		act = new HTMLFontAction();
		actionList.add(act);
		formatMenu.add(act);

		act = new ClearStylesAction();
		actionList.add(act);
		formatMenu.add(act);
		formatMenu.addSeparator();

		lst = HTMLEditorActionFactory.createBlockElementActionList();
		actionList.addAll(lst);
		formatMenu.add(createMenu(lst, I18N.getMsg("shef.paragraph")));
		paraActions.addAll(lst);

		lst = HTMLEditorActionFactory.createListElementActionList();
		actionList.addAll(lst);
		formatMenu.add(createMenu(lst, I18N.getMsg("shef.list")));
		formatMenu.addSeparator();
		paraActions.addAll(lst);

		lst = HTMLEditorActionFactory.createAlignActionList();
		actionList.addAll(lst);
		formatMenu.add(createMenu(lst, I18N.getMsg("shef.align")));

		JMenu tableMenu = new JMenu(I18N.getMsg("shef.table"));
		lst = HTMLEditorActionFactory.createInsertTableElementActionList();
		actionList.addAll(lst);
		tableMenu.add(createMenu(lst, I18N.getMsg("shef.insert")));

		lst = HTMLEditorActionFactory.createDeleteTableElementActionList();
		actionList.addAll(lst);
		tableMenu.add(createMenu(lst, I18N.getMsg("shef.delete")));
		formatMenu.add(tableMenu);
		formatMenu.addSeparator();

		actionList.add(objectPropertiesAction);
		formatMenu.add(objectPropertiesAction);

		// create insert menu
		insertMenu = new JMenu(I18N.getMsg("shef.insert"));
		act = new HTMLLinkAction();
		actionList.add(act);
		insertMenu.add(act);

		act = new HTMLImageAction();
		actionList.add(act);
		insertMenu.add(act);

		act = new HTMLTableAction();
		actionList.add(act);
		insertMenu.add(act);
		insertMenu.addSeparator();

		act = new HTMLLineBreakAction();
		actionList.add(act);
		insertMenu.add(act);

		act = new HTMLHorizontalRuleAction();
		actionList.add(act);
		insertMenu.add(act);

		act = new SpecialCharAction();
		actionList.add(act);
		insertMenu.add(act);

		createFormatToolBar(paraActions, fontSizeActions);
	}

	/**
	 * Creates the editor tabs.
	 */
	private void createEditorTabs() {
		tabs = new JTabbedPane(SwingConstants.BOTTOM);
		wysEditor = createWysiwygEditor();
		srcEditor = createSourceEditor();

		tabs.addTab("Edit", new JScrollPane(wysEditor));

		JScrollPane scrollPane = new JScrollPane(srcEditor);
		SyntaxGutter gutter = new SyntaxGutter(srcEditor);
		SyntaxGutterBase gutterBase = new SyntaxGutterBase(gutter);
		scrollPane.setRowHeaderView(gutter);
		scrollPane.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, gutterBase);

		tabs.addTab("HTML", scrollPane);
		tabs.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				updateEditView();
			}
		});
	}

	/**
	 * Creates the format tool bar.
	 *
	 * @param blockActs the block acts
	 * @param fontSizeActs the font size acts
	 */
	@SuppressWarnings("unchecked")
	private void createFormatToolBar(ActionList blockActs, ActionList fontSizeActs) {
		formatToolBar = new JToolBar();
		formatToolBar.setFloatable(false);
		formatToolBar.setFocusable(false);
		formatToolBar.setLayout(new MigLayout("ins 0,flowx"));
		formatToolBar.setOpaque(false);
		Font comboFont = new Font("Dialog", Font.PLAIN, 11);

		// paragraphs
		PropertyChangeListener propLst = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals("selected")) {
					if (evt.getNewValue().equals(Boolean.TRUE)) {
						paragraphCombo.removeActionListener(paragraphComboHandler);
						paragraphCombo.setSelectedItem(evt.getSource());
						paragraphCombo.addActionListener(paragraphComboHandler);
					}
				}
			}
		};
		for (Iterator it = blockActs.iterator(); it.hasNext();) {
			Object o = it.next();
			if (o instanceof DefaultAction) {
				((DefaultAction) o).addPropertyChangeListener(propLst);
			}
		}
		paragraphCombo = new JComboBox(toArray(blockActs));
		paragraphCombo.setFont(comboFont);
		paragraphCombo.addActionListener(paragraphComboHandler);
		paragraphCombo.setRenderer(new ParagraphComboRenderer());
		if (showFullToolbar) {
			formatToolBar.add(paragraphCombo, "split 6");
			formatToolBar.addSeparator();
		}

		// fonts
		Vector<String> fonts = new Vector<String>();
		fonts.add("Default");
		fonts.add("serif");
		fonts.add("sans-serif");
		fonts.add("monospaced");
		GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
		fonts.addAll(Arrays.asList(gEnv.getAvailableFontFamilyNames()));

		fontFamilyCombo = new JComboBox<String>(fonts);
		fontFamilyCombo.setFont(comboFont);
		fontFamilyCombo.addActionListener(fontChangeHandler);
		formatToolBar.add(fontFamilyCombo);
		formatToolBar.addSeparator();

		final JButton fontSizeButton = new JButton(UIUtils.getIcon(UIUtils.X16, "fontsize.png"));
		final JPopupMenu sizePopup = ActionUIFactory.getInstance().createPopupMenu(fontSizeActs);
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sizePopup.show(fontSizeButton, 0, fontSizeButton.getHeight());
			}
		};
		fontSizeButton.addActionListener(al);
		configToolbarButton(fontSizeButton);
		if (showFullToolbar) {
			formatToolBar.add(fontSizeButton);
		}

		Action act;
		act = new HTMLFontColorAction();
		actionList.add(act);
		if (showFullToolbar) {
			addToToolBar(formatToolBar, act);
			formatToolBar.addSeparator();
		}

		act = new HTMLInlineAction(HTMLInlineAction.BOLD);
		act.putValue(ActionManager.BUTTON_TYPE, ActionManager.BUTTON_TYPE_VALUE_TOGGLE);
		actionList.add(act);
		String opts = "";
		if (showFullToolbar) {
			opts = "newline,split 20";
		}
		addToToolBar(formatToolBar, act, opts);

		act = new HTMLInlineAction(HTMLInlineAction.ITALIC);
		act.putValue(ActionManager.BUTTON_TYPE, ActionManager.BUTTON_TYPE_VALUE_TOGGLE);
		actionList.add(act);
		addToToolBar(formatToolBar, act);

		act = new HTMLInlineAction(HTMLInlineAction.UNDERLINE);
		act.putValue(ActionManager.BUTTON_TYPE, ActionManager.BUTTON_TYPE_VALUE_TOGGLE);
		actionList.add(act);
		addToToolBar(formatToolBar, act);

		formatToolBar.addSeparator();

		List alst = HTMLEditorActionFactory.createListElementActionList();
		for (Iterator it = alst.iterator(); it.hasNext();) {
			act = (Action) it.next();
			act.putValue(ActionManager.BUTTON_TYPE, ActionManager.BUTTON_TYPE_VALUE_TOGGLE);
			actionList.add(act);
			if (showFullToolbar) {
				addToToolBar(formatToolBar, act);
			}
		}

		if (showFullToolbar) {
			formatToolBar.addSeparator();
		}

		alst = HTMLEditorActionFactory.createAlignActionList();
		for (Iterator it = alst.iterator(); it.hasNext();) {
			act = (Action) it.next();
			act.putValue(ActionManager.BUTTON_TYPE, ActionManager.BUTTON_TYPE_VALUE_TOGGLE);
			actionList.add(act);
			addToToolBar(formatToolBar, act);
		}

		if (showFullToolbar) {
			formatToolBar.addSeparator();
		}

		if (showFullToolbar) {
			act = new HTMLLinkAction();
			actionList.add(act);
			addToToolBar(formatToolBar, act);

			act = new SbHTMLImageAction();
			actionList.add(act);
			addToToolBar(formatToolBar, act);

			act = new HTMLTableAction();
			actionList.add(act);
			addToToolBar(formatToolBar, act);
		}
	}

	/**
	 * Creates the menu.
	 *
	 * @param lst the lst
	 * @param menuName the menu name
	 * @return the j menu
	 */
	private JMenu createMenu(ActionList lst, String menuName) {
		JMenu m = ActionUIFactory.getInstance().createMenu(lst);
		m.setText(menuName);
		return m;
	}

	/**
	 * Creates the source editor.
	 *
	 * @return the source code editor
	 */
	private SourceCodeEditor createSourceEditor() {
		SourceCodeEditor ed = new SourceCodeEditor();
		SyntaxDocument doc = new SyntaxDocument();
		doc.setSyntax(SyntaxFactory.getSyntax("html"));
		CompoundUndoManager cuh = new CompoundUndoManager(doc, new UndoManager());

		doc.addUndoableEditListener(cuh);
		doc.setDocumentFilter(new IndentationFilter());
		doc.addDocumentListener(textChangedHandler);
		ed.setDocument(doc);
		ed.addFocusListener(focusHandler);
		ed.addCaretListener(caretHandler);
		ed.addMouseListener(popupHandler);

		return ed;
	}

	/**
	 * Creates the wysiwyg editor.
	 *
	 * @return the j editor pane
	 */
	private JEditorPane createWysiwygEditor() {
		JEditorPane ed = new JEditorPane();

		ed.setEditorKitForContentType("text/html", new WysiwygHTMLEditorKit());
		ed.setContentType("text/html");
		insertHTML(ed, "<p></p>", 0);

		ed.addCaretListener(caretHandler);
		ed.addFocusListener(focusHandler);
		// spell checker, must be before the popup handler
		Preference pref = PrefUtil.get(PreferenceKey.SPELLING, Spelling.none.toString());
		Spelling spelling = Spelling.valueOf(pref.getStringValue());
		if (Spelling.none != spelling) {
			SpellChecker.register(ed);
		}
		ed.addMouseListener(popupHandler);

		HTMLDocument document = (HTMLDocument) ed.getDocument();
		CompoundUndoManager cuh = new CompoundUndoManager(document, new UndoManager());
		document.addUndoableEditListener(cuh);
		document.addDocumentListener(textChangedHandler);

		return ed;
	}

	/**
	 * De indent.
	 *
	 * @param html the html
	 * @return the string
	 */
	/*
	 * *******************************************************************
	 * Methods for dealing with HTML between wysiwyg and source editors
	 * *****************************************************************
	 */
	private String deIndent(String html) {
		String ws = "\n    ";
		StringBuilder sb = new StringBuilder(html);
		while (sb.indexOf(ws) != -1) {
			int s = sb.indexOf(ws);
			int e = s + ws.length();
			sb.delete(s, e);
			sb.insert(s, "\n");
		}
		return sb.toString();
	}

	/**
	 * Delete occurance.
	 *
	 * @param text the text
	 * @param word the word
	 * @return the string
	 */
	private String deleteOccurance(String text, String word) {
		StringBuilder sb = new StringBuilder(text);
		int p;
		while ((p = sb.toString().toLowerCase().indexOf(word.toLowerCase())) != -1) {
			sb.delete(p, p + word.length());
		}
		return sb.toString();
	}

	/**
	 * Gets the edits the menu.
	 *
	 * @return the edits the menu
	 */
	public JMenu getEditMenu() {
		return editMenu;
	}

	/**
	 * Gets the format menu.
	 *
	 * @return the format menu
	 */
	public JMenu getFormatMenu() {
		return formatMenu;
	}

	/**
	 * Gets the insert menu.
	 *
	 * @return the insert menu
	 */
	public JMenu getInsertMenu() {
		return insertMenu;
	}

	/**
	 * Gets the max length.
	 *
	 * @return the max length
	 */
	public int getMaxLength() {
		return maxLength;
	}

	/**
	 * Gets the show simple toolbar.
	 *
	 * @return the show simple toolbar
	 */
	public boolean getShowSimpleToolbar() {
		return showFullToolbar;
	}

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		String topText = "";
		// return only body content
		try {
			if (tabs.getSelectedIndex() == 0) {
				HTMLDocument doc = (HTMLDocument) wysEditor.getDocument();
				// HTMLWriter htmlWriter = new HtmlBodyWriter(writer, doc);
				// htmlWriter.write();
				// StringWriter writer = new StringWriter();
				// topText = writer.toString();
				topText = HtmlUtil.getContent(doc);
				topText = removeInvalidTags(topText);
			} else {
				topText = removeInvalidTags(srcEditor.getText());
				topText = deIndent(removeInvalidTags(topText));
				topText = Entities.HTML40.unescapeUnknownEntities(topText);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return topText;
		// OLD
		// String topText;
		// if (tabs.getSelectedIndex() == 0) {
		// topText = removeInvalidTags(wysEditor.getText());
		// } else {
		// topText = removeInvalidTags(srcEditor.getText());
		// topText = deIndent(removeInvalidTags(topText));
		// topText = Entities.HTML40.unescapeUnknownEntities(topText);
		// }
		// return topText;
	}

	/**
	 * Inits the ui.
	 */
	private void initUI() {
		createEditorTabs();
		createEditorActions();
		// old
		// setLayout(new BorderLayout());
		// add(formatToolBar, BorderLayout.NORTH);
		// add(tabs, BorderLayout.CENTER);
		setLayout(new MigLayout("fill,wrap,ins 0", "", "[][grow][]"));
		add(formatToolBar);
		lbMessage = new JLabel("", SwingConstants.RIGHT);
		add(lbMessage, "shrink, pos null null 100% 100%");

		add(tabs, "grow, id tabs");
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				wysEditor.requestFocusInWindow();
			}
		});
	}

	/**
	 * Insert html.
	 *
	 * @param editor the editor
	 * @param html the html
	 * @param location the location
	 */
	// inserts html into the wysiwyg editor
	private void insertHTML(JEditorPane editor, String html, int location) {
		try {
			HTMLEditorKit kit = (HTMLEditorKit) editor.getEditorKit();
			Document doc = editor.getDocument();
			StringReader reader = new StringReader(HTMLUtils.jEditorPaneizeHTML(html));
			kit.read(reader, doc, location);
		} catch (IOException | BadLocationException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Removes the invalid tags.
	 *
	 * @param html the html
	 * @return the string
	 */
	private String removeInvalidTags(String html) {
		for (String invalid_tag : INVALID_TAGS) {
			html = deleteOccurance(html, '<' + invalid_tag + '>');
			html = deleteOccurance(html, "</" + invalid_tag + '>');
		}
		return html.trim();
	}

	/**
	 * Sets the caret position.
	 *
	 * @param pos the new caret position
	 */
	public void setCaretPosition(int pos) {
		if (tabs.getSelectedIndex() == 0) {
			wysEditor.setCaretPosition(pos);
			wysEditor.requestFocusInWindow();
		} else if (tabs.getSelectedIndex() == 1) {
			srcEditor.setCaretPosition(pos);
			srcEditor.requestFocusInWindow();
		}
	}

	/**
	 * Sets the max length.
	 *
	 * @param maxLength the new max length
	 */
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	/**
	 * Sets the selected tab.
	 *
	 * @param i the new selected tab
	 */
	public void setSelectedTab(int i) {
		tabs.setSelectedIndex(i);
	}

	/**
	 * Sets the text.
	 *
	 * @param text the new text
	 */
	public void setText(String text) {
		String topText = removeInvalidTags(text);
		if (tabs.getSelectedIndex() == 0) {
			wysEditor.setText("");
			insertHTML(wysEditor, topText, 0);
			CompoundUndoManager.discardAllEdits(wysEditor.getDocument());
		} else {
			{
				String t = deIndent(removeInvalidTags(topText));
				t = Entities.HTML40.unescapeUnknownEntities(t);
				srcEditor.setText(t);
			}
			CompoundUndoManager.discardAllEdits(srcEditor.getDocument());
		}
	}

	/**
	 * Converts an action list to an array. Any of the null "separators" or sub
	 * ActionLists are omitted from the array.
	 *
	 * @param lst the lst
	 * @return the action[]
	 */
	@SuppressWarnings("unchecked")
	private Action[] toArray(ActionList lst) {
		List acts = new ArrayList<Object>();
		for (Iterator it = lst.iterator(); it.hasNext();) {
			Object v = it.next();
			if (v != null && v instanceof Action) {
				acts.add(v);
			}
		}

		return (Action[]) acts.toArray(new Action[acts.size()]);
	}

	/**
	 * Update edit view.
	 */
	// called when changing tabs
	private void updateEditView() {
		if (tabs.getSelectedIndex() == 0) {
			String topText = removeInvalidTags(srcEditor.getText());
			wysEditor.setText("");
			insertHTML(wysEditor, topText, 0);
			CompoundUndoManager.discardAllEdits(wysEditor.getDocument());
		} else {
			String topText = removeInvalidTags(wysEditor.getText());
			if (isWysTextChanged || srcEditor.getText().equals("")) {
				String t = deIndent(removeInvalidTags(topText));
				t = Entities.HTML40.unescapeUnknownEntities(t);
				srcEditor.setText(t);
			}
			CompoundUndoManager.discardAllEdits(srcEditor.getDocument());
		}
		isWysTextChanged = false;
		paragraphCombo.setEnabled(tabs.getSelectedIndex() == 0);
		fontFamilyCombo.setEnabled(tabs.getSelectedIndex() == 0);
		updateState();
	}

	/**
	 * Update state.
	 */
	private void updateState() {
		if (focusedEditor == wysEditor) {
			fontFamilyCombo.removeActionListener(fontChangeHandler);
			String fontName = HTMLUtils.getFontFamily(wysEditor);
			if (fontName == null) {
				fontFamilyCombo.setSelectedIndex(0);
			} else {
				fontFamilyCombo.setSelectedItem(fontName);
			}
			fontFamilyCombo.addActionListener(fontChangeHandler);
		}
		actionList.putContextValueForAll(HTMLTextEditAction.EDITOR, focusedEditor);
		actionList.updateEnabledForAll();
	}
}
