/*
 Storybook: Open Source software for novelists and authors.
 Copyright (C) 2008 - 2012 Martin Mustun

 SbApp.getMainFrame() program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 SbApp.getMainFrame() program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with SbApp.getMainFrame() program.  If not, see <http://www.gnu.org/licenses/>.
 */
package storybook.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Window;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;

import org.hibernate.Session;

import net.infonode.docking.DockingWindow;
import net.infonode.docking.DockingWindowAdapter;
import net.infonode.docking.RootWindow;
import net.infonode.docking.SplitWindow;
import net.infonode.docking.TabWindow;
import net.infonode.docking.View;
import net.infonode.docking.ViewSerializer;
import net.infonode.docking.properties.RootWindowProperties;
import net.infonode.docking.theme.DockingWindowsTheme;
import net.infonode.docking.theme.ShapedGradientDockingTheme;
import net.infonode.docking.util.DockingUtil;
import net.infonode.docking.util.MixedViewHandler;
import net.infonode.docking.util.StringViewMap;
import net.infonode.util.Direction;

import net.miginfocom.swing.MigLayout;

import storybook.SbApp;
import storybook.SbConstants;
import storybook.SbConstants.BookKey;
import storybook.SbConstants.PreferenceKey;
import storybook.SbConstants.Storybook;
import storybook.SbConstants.ViewName;
import storybook.action.ActionHandler;
import storybook.action.SbActionManager;
import storybook.controller.BookController;
import storybook.model.BlankModel;
import storybook.model.BookModel;
import storybook.model.DbFile;
import storybook.model.hbn.dao.PartDAOImpl;
import storybook.model.hbn.entity.AbstractEntity;
import storybook.model.hbn.entity.Internal;
import storybook.model.hbn.entity.Part;
import storybook.toolkit.BookUtil;
import storybook.toolkit.DockingWindowUtil;
import storybook.toolkit.FileDrop;
import storybook.toolkit.I18N;
import storybook.toolkit.PrefUtil;
import storybook.toolkit.SpellCheckerUtil;
import storybook.toolkit.swing.FontUtil;
import storybook.toolkit.swing.SwingUtil;
import storybook.ui.dialog.unicodlg.UnicodeDialog;
import storybook.ui.edit.EntityEditor;
import storybook.ui.interfaces.IPaintable;

// TODO: Auto-generated Javadoc
/**
 * The Class MainFrame.
 *
 * @author martin
 */

public class MainFrame extends JFrame implements IPaintable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2237084857136835926L;

	/**
	 * The Class DynamicView.
	 */
	private static class DynamicView extends View {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = -4014546941197479192L;

		/** The id. */
		private final int id;

		/**
		 * Instantiates a new dynamic view.
		 *
		 * @param title
		 *            the title
		 * @param icon
		 *            the icon
		 * @param component
		 *            the component
		 * @param id
		 *            the id
		 */
		DynamicView(String title, Icon icon, Component component, int id) {
			super(title, icon, component);
			this.id = id;
		}

		/**
		 * Gets the id.
		 *
		 * @return the id
		 */
		public int getId() {
			return id;
		}
	}

	/**
	 * The Class MainDockingWindowAdapter.
	 */
	private class MainDockingWindowAdapter extends DockingWindowAdapter {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * net.infonode.docking.DockingWindowAdapter#windowAdded(net.infonode.
		 * docking.DockingWindow, net.infonode.docking.DockingWindow)
		 */
		@Override
		public void windowAdded(DockingWindow addedToWindow, DockingWindow addedWindow) {
			SbApp.trace("MainDockingWindowAdapter.windowAdded(" + addedToWindow.getName() + ", " + addedWindow.getName()
					+ ")");
			if (addedWindow != null && addedWindow instanceof SbView) {
				SbView view = (SbView) addedWindow;
				if (!view.isLoaded()) {
					viewFactory.loadView(view);
					bookController.attachView(view.getComponent());
					bookModel.fireAgain(view);
				}
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * net.infonode.docking.DockingWindowAdapter#windowClosed(net.infonode.
		 * docking.DockingWindow)
		 */
		@Override
		public void windowClosed(DockingWindow window) {
			SbApp.trace("MainDockingWindowAdapter.windowClosed(" + window.getName() + ")");
			if (window != null && window instanceof SbView) {
				SbView view = (SbView) window;
				/*
				 * suppression editorView if
				 * (ViewName.EDITOR.toString().equals(view.getName())) { //
				 * don't detach / unload the editor return; }
				 */
				if (!view.isLoaded()) {
					return;
				}
				bookController.detachView(view.getComponent());
				viewFactory.unloadView(view);
				/*
				 * suppression du garbage collector SbApp.getInstance().runGC();
				 */
			}
		}
	}

	/**
	 * The Class MainFrameWindowAdaptor.
	 */
	private class MainFrameWindowAdaptor extends WindowAdapter {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.
		 * WindowEvent)
		 */
		@Override
		public void windowClosing(WindowEvent evt) {
			SbApp app = SbApp.getInstance();
			app.exit();
		}
	}

	/**
	 * Creates the dummy view component.
	 *
	 * @param text
	 *            the text
	 * @return the j component
	 */
	private static JComponent createDummyViewComponent(String text) {
		StringBuilder sb = new StringBuilder();
		for (int j = 0; j < 100; j++) {
			sb.append(text).append(". SbApp.getMainFrame() is line ").append(j).append("\n");
		}
		return new JScrollPane(new JTextArea(sb.toString()));
	}

	/** The book model. */
	private BookModel bookModel;

	/** The book controller. */
	private BookController bookController;

	/** The sb action manager. */
	private SbActionManager sbActionManager;

	/** The view factory. */
	private ViewFactory viewFactory;

	/** The main tool bar. */
	private JToolBar mainToolBar;

	/** The root window. */
	private RootWindow rootWindow;

	/** The status bar. */
	private StatusBarPanel statusBar;

	/** The dynamic views. */
	private HashMap<Integer, JComponent> dynamicViews = new HashMap<Integer, JComponent>();

	/** The db file. */
	private DbFile dbFile;

	/** The current part. */
	private Part currentPart;

	/** The Editor modless. */
	private boolean EditorModless;

	/** The unicode dialog. */
	private UnicodeDialog unicodeDialog;

	/**
	 * Instantiates a new main frame.
	 */
	public MainFrame() {
		FontUtil.setDefaultFont(new Font("Arial", Font.PLAIN, 12));
	}

	/**
	 * Close.
	 *
	 * @param
	 * 
	 */
	public void close() {

		// save dimension, location, maximized
		Dimension dim = getSize();
		PrefUtil.set(PreferenceKey.SIZE_WIDTH, dim.width);
		PrefUtil.set(PreferenceKey.SIZE_HEIGHT, dim.height);
		PrefUtil.set(PreferenceKey.POS_X, getLocationOnScreen().x);
		PrefUtil.set(PreferenceKey.POS_Y, getLocationOnScreen().y);
		PrefUtil.set(PreferenceKey.MAXIMIZED, isMaximized());
		// save layout
		DockingWindowUtil.saveLayout(SbApp.getMainFrame(), SbConstants.BookKey.LAST_USED_LAYOUT.toString());
		// save last used part
		BookUtil.store(SbApp.getMainFrame(), BookKey.LAST_USED_PART.toString(),
				((int) (long) getCurrentPart().getId()));

		bookModel.closeSession();
		SbApp app = SbApp.getInstance();
		app.removeMainFrame(SbApp.getMainFrame());
		dispose();
	}

	/**
	 * Close view.
	 *
	 * @param viewName
	 *            the view name
	 */
	public void closeView(ViewName viewName) {
		SbApp.trace("MainFrame.closeView(" + viewName.name() + ")");
		SbView view = getView(viewName);
		view.close();
	}

	/**
	 * Gets the action controller.
	 *
	 * @return the action controller
	 */
	public ActionHandler getActionController() {
		return sbActionManager.getActionController();
	}

	/**
	 * Gets the book controller.
	 *
	 * @return the book controller
	 */
	public BookController getBookController() {
		return bookController;
	}

	/**
	 * Gets the book model.
	 *
	 * @return the book model
	 */
	public BookModel getBookModel() {
		return bookModel;
	}

	/**
	 * Gets the current part.
	 *
	 * @return the current part
	 */
	public Part getCurrentPart() {
		try {
			Session session = bookModel.beginTransaction();
			if (currentPart == null) {
				PartDAOImpl dao = new PartDAOImpl(session);
				currentPart = dao.findFirst();
			} else {
				session.refresh(currentPart);
			}
			bookModel.commit();
			return currentPart;
		} catch (NullPointerException e) {
		}
		return null;
	}

	/**
	 * Gets the db file.
	 *
	 * @return the db file
	 */
	public DbFile getDbFile() {
		return dbFile;
	}

	/**
	 * Gets the dynamic view.
	 *
	 * @param id
	 *            the id
	 * @return the dynamic view
	 */
	private View getDynamicView(int id) {
		View view = (View) dynamicViews.get(new Integer(id));
		if (view == null) {
			view = new DynamicView("Dynamic View " + id, null, createDummyViewComponent("Dynamic View " + id), id);
		}
		return view;
	}

	/**
	 * Gets the main tool bar.
	 *
	 * @return the main tool bar
	 */
	public JToolBar getMainToolBar() {
		return mainToolBar;
	}

	/**
	 * Gets the root window.
	 *
	 * @return the root window
	 */
	public RootWindow getRootWindow() {
		return rootWindow;
	}

	/**
	 * Gets the sb action manager.
	 *
	 * @return the sb action manager
	 */
	public SbActionManager getSbActionManager() {
		return sbActionManager;
	}

	/**
	 * Gets the SbApp.getMainFrame().
	 *
	 * @return the SbApp.getMainFrame()
	 */
	private MainFrame getThis() {
		return SbApp.getMainFrame();
	}

	/**
	 * Gets the view.
	 *
	 * @param viewName
	 *            the view name
	 * @return the view
	 */
	public SbView getView(String viewName) {
		return viewFactory.getView(viewName);
	}

	/**
	 * Gets the view.
	 *
	 * @param viewName
	 *            the view name
	 * @return the view
	 */
	public SbView getView(ViewName viewName) {
		return viewFactory.getView(viewName);
	}

	/**
	 * Gets the view factory.
	 *
	 * @return the view factory
	 */
	public ViewFactory getViewFactory() {
		return viewFactory;
	}

	/**
	 * Checks for current part.
	 *
	 * @return true, if successful
	 */
	public boolean hasCurrentPart() {
		return currentPart != null;
	}

	/**
	 * Hide editor.
	 */
	public void hideEditor() {
		/*
		 * Timer timer = new Timer(200, new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) {
		 */
		// View editorView = getView(ViewName.EDITOR);
		// if (!editorView.isShowing()) {
		// return;
		// }
		/*
		 * if (editorView.isMinimized()) { WindowBar bar =
		 * rootWindow.getWindowBar(Direction.RIGHT); bar.setSelectedTab(-1); }
		 * else {
		 */
		// editorView.close();
		/* } */
		/*
		 * } }); timer.setRepeats(false); timer.start();
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see storybook.ui.interfaces.IPaintable#init()
	 */
	@Override
	public void init() {
		SbApp.trace("MainFrame.init()");
		dbFile = null;
		viewFactory = new ViewFactory(SbApp.getMainFrame());
		sbActionManager = new SbActionManager(SbApp.getMainFrame());
		sbActionManager.init();
		bookController = new BookController(SbApp.getMainFrame());
		BlankModel model = new BlankModel(SbApp.getMainFrame());
		bookController.attachModel(model);
		setIconImage(I18N.getIconImage("icon.sb"));
		addWindowListener(new MainFrameWindowAdaptor());
	}

	/**
	 * Inits the.
	 *
	 * @param dbF
	 *            the db f
	 */
	public void init(DbFile dbF) {
		SbApp.trace("MainFrame.init(" + dbF.getDbName() + ")");
		try {
			new FileDrop(SbApp.getMainFrame(), new FileDrop.Listener() {

				@Override
				public void filesDropped(java.io.File[] files, DropTargetDropEvent evt) {
	
						try {
							// Accept copy drops
							evt.acceptDrop(DnDConstants.ACTION_NONE);

							// Get the transfer which can provide the dropped item
							// data
							Transferable transferable = evt.getTransferable();

							// Get the data formats of the dropped item
							DataFlavor[] flavors = transferable.getTransferDataFlavors();

							// Loop through the flavors
							for (DataFlavor flavor : flavors) {
								// If the drop items are files
								if (flavor.isFlavorJavaFileListType()) {
									SbApp.trace("D&D " + files[0].getCanonicalPath() + "\n");
									DbFile dbFile = new DbFile(files[0].getCanonicalPath());
									SbApp.getInstance().openFile(dbFile);
								} else {
							//		evt.rejectDrop();
								}
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				
					// Inform that the drop is complete
					evt.dropComplete(true);
				}

			});
			
            // Call OS X specific FullScreenUtilities.setWindowCanFullScreen(homeFrame, true) by reflection 

	        try {
	            Class.forName("com.apple.eawt.FullScreenUtilities").
	                getMethod("setWindowCanFullScreen", new Class<?> [] {Window.class, boolean.class}).
	                invoke(null, SbApp.getMainFrame(), true);
	          } catch (Exception ex) {
	            // Full screen mode is not supported
	          }

			SbApp.getMainFrame().dbFile = dbF;
			viewFactory = new ViewFactory(SbApp.getMainFrame());
			viewFactory.setInitialisation();
			sbActionManager = new SbActionManager(SbApp.getMainFrame());
			sbActionManager.init();
			// model and controller
			bookController = new BookController(SbApp.getMainFrame());
			bookModel = new BookModel(SbApp.getMainFrame());
			if (!dbF.getDbName().isEmpty()) {
				bookModel.initSession(dbF.getDbName());
			}
			bookController.attachModel(bookModel);
			// Google maps
			// Preference pref = PrefUtil.get(PreferenceKey.GOOGLE_MAPS_URL,
			// SbConstants.DEFAULT_GOOGLE_MAPS_URL);
			// NetUtil.setGoogleMapUrl(pref.getStringValue());
			// spell checker
			SpellCheckerUtil.registerDictionaries();
			// listener
			addWindowListener(new MainFrameWindowAdaptor());
			viewFactory.resetInitialisation();
		} catch (Exception e) {
			SbApp.error("MainFrame.init(" + dbF.getName() + ")", e);
		}
	}

	/**
	 * Inits the after pack.
	 */
	private void initAfterPack() {
		unicodeDialog = new UnicodeDialog(SbApp.getMainFrame());
		SbView scenesView = getView(ViewName.SCENES);
		SbView locationsView = getView(ViewName.LOCATIONS);
		SbView personsView = getView(ViewName.PERSONS);
		SbView chronoView = getView(ViewName.CHRONO);
		SbView treeView = getView(ViewName.TREE);
		SbView quickInfoView = getView(ViewName.INFO);
		SbView navigationView = getView(ViewName.NAVIGATION);
		// add docking window adapter to all views (except editor)
		MainDockingWindowAdapter dockingAdapter = new MainDockingWindowAdapter();
		for (int i = 0; i < viewFactory.getViewMap().getViewCount(); ++i) {
			View view = viewFactory.getViewMap().getViewAtIndex(i);
			/*
			 * if (view.getName().equals(ViewName.EDITOR.toString())) {
			 * continue; }
			 */
			view.addListener(dockingAdapter);
		}
		// load initially shown views here
		SbView[] views2 = { scenesView, personsView, locationsView, chronoView, treeView, quickInfoView,
				navigationView };
		for (SbView view : views2) {
			viewFactory.loadView(view);
			bookController.attachView(view.getComponent());
			bookModel.fireAgain(view);
		}
		quickInfoView.restoreFocus();
		chronoView.restoreFocus();
	}

	/**
	 * Inits the blank ui.
	 */
	// public void initBlankUi() {
	// dbFile = null;
	// setTitle(Storybook.PRODUCT_FULL_NAME.toString());
	// Toolkit toolkit = Toolkit.getDefaultToolkit();
	// Dimension screenSize = toolkit.getScreenSize();
	// setLocation(screenSize.width / 2 - 450, screenSize.height / 2 - 320);
	// setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	// SbApp.getInstance().resetUiFont();
	// sbActionManager.reloadMenuToolbar();
	// BlankPanel blankPanel = new BlankPanel(SbApp.getMainFrame());
	// blankPanel.initAll();
	// add(blankPanel);
	// pack();
	// setVisible(true);
	// }

	/**
	 * Inits the root window.
	 */
	private void initRootWindow() {
		SbApp.trace("MainFrame.initRootWindow()");
		StringViewMap viewMap = viewFactory.getViewMap();
		MixedViewHandler handler = new MixedViewHandler(viewMap, new ViewSerializer() {
			@Override
			public View readView(ObjectInputStream in) throws IOException {
				return getDynamicView(in.readInt());
			}

			@Override
			public void writeView(View view, ObjectOutputStream out) throws IOException {
				out.writeInt(((DynamicView) view).getId());
			}
		});
		rootWindow = DockingUtil.createRootWindow(viewMap, handler, true);
		rootWindow.setName("rootWindow");
		rootWindow.setPreferredSize(new Dimension(4096, 2048));
		// suppression du editorView
		// SbView editorView = viewFactory.getEditorView();
		// bookController.attachView(editorView.getComponent());
		// set theme
		DockingWindowsTheme currentTheme = new ShapedGradientDockingTheme();
		RootWindowProperties properties = new RootWindowProperties();
		properties.addSuperObject(currentTheme.getRootWindowProperties());
		// Our properties object is the super object of the root window
		// properties object, so all property values of the
		// theme and in our property object will be used by the root window
		rootWindow.getRootWindowProperties().addSuperObject(properties);
		rootWindow.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see storybook.ui.interfaces.IPaintable#initUi()
	 */
	@Override
	public void initUi() {
		SbApp.trace(">>> MainFrame.initUi()");
		setLayout(new MigLayout("flowy,fill,ins 0,gap 0", "", "[grow]"));
		setIconImage(I18N.getIconImage("icon.sb"));
		setTitle();
		restoreDimension();
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		SbApp.getInstance().resetUiFont();
		sbActionManager.reloadMenuToolbar();
		initRootWindow();
		setDefaultLayout();
		SbApp.trace("add(rootWindow, \"grow\");");
		add(rootWindow, "grow");
		SbApp.trace("statusBar = new StatusBarPanel(SbApp.getMainFrame());");
		statusBar = new StatusBarPanel(SbApp.getMainFrame());
		SbApp.trace("add(statusBar, \"growx\");");
		add(statusBar, "growx");
		SbApp.trace("bookController.attachView(statusBar);");
		bookController.attachView(statusBar);
		SbApp.trace("pack();");
		pack();
		setVisible(true);
		initAfterPack();
		JMenuBar menubar = getJMenuBar();
		bookController.detachView(menubar);
		bookController.attachView(menubar);
		// load last used layout
		DockingWindowUtil.loadLayout(SbApp.getMainFrame(), SbConstants.BookKey.LAST_USED_LAYOUT.toString());
		// always hide the editor
		hideEditor();
		// restore last used part
		try {
			Internal internal = BookUtil.get(SbApp.getMainFrame(), BookKey.LAST_USED_PART.toString(), 1);
			Part part = null;
			if (internal != null && internal.getIntegerValue() != null) {
				Session session = bookModel.beginTransaction();
				PartDAOImpl dao = new PartDAOImpl(session);
				part = dao.find((long) internal.getIntegerValue());
				bookModel.commit();
				if (part == null) {
					part = getCurrentPart();
				}
			} else {
				part = getCurrentPart();
			}
			sbActionManager.getActionHandler().handleChangePart(part);
		} catch (Exception e) {
			SbApp.trace("exiting try in MainFrame.initUi()");
		}
		// bookController.attachView(SbApp.getMainFrame());
		SbApp.trace("<<< MainFrame.initUi()");
	}

	/**
	 * Checks if is blank.
	 *
	 * @return true, if is blank
	 */
	public boolean isBlank() {
		return dbFile == null;
	}

	/**
	 * Checks if is maximized.
	 *
	 * @return true, if is maximized
	 */
	public boolean isMaximized() {
		return (getExtendedState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH;
	}

	/**
	 * Refresh.
	 */
	public void refresh() {
		setWaitingCursor();
		for (int i = 0; i < viewFactory.getViewMap().getViewCount(); ++i) {
			SbView view = (SbView) viewFactory.getViewMap().getViewAtIndex(i);
			getBookController().refresh(view);
		}
		setDefaultCursor();
	}

	/**
	 * Refresh status bar.
	 */
	public void refreshStatusBar() {
		statusBar.refresh();
	}

	/**
	 * Restore dimension.
	 */
	private void restoreDimension() {
		int w = PrefUtil.get(PreferenceKey.SIZE_WIDTH, SbConstants.DEFAULT_SIZE_WIDTH).getIntegerValue();
		int h = PrefUtil.get(PreferenceKey.SIZE_HEIGHT, SbConstants.DEFAULT_SIZE_HEIGHT).getIntegerValue();
		setPreferredSize(new Dimension(w, h));
		int x = PrefUtil.get(PreferenceKey.POS_X, SbConstants.DEFAULT_POS_X).getIntegerValue();
		int y = PrefUtil.get(PreferenceKey.POS_Y, SbConstants.DEFAULT_POS_Y).getIntegerValue();

		// Do not put it out of screen
		Dimension size = SbApp.getMainFrame().getToolkit().getScreenSize();
		if ((x >= 0) && (y >= 0) && (x < size.width) && (y < size.height)) {
			setLocation(x, y);
		}
		boolean maximized = PrefUtil.get(PreferenceKey.MAXIMIZED, false).getBooleanValue();
		if (maximized) {
			setMaximized();
		}
	}

	/**
	 * Save all table design.
	 */
	public void saveAllTableDesign() {
		viewFactory.saveAllTableDesign();
	}

	/**
	 * Sets the current part.
	 *
	 * @param currentPart
	 *            the new current part
	 */
	public void setCurrentPart(Part currentPart) {
		if (currentPart != null) {
			SbApp.getMainFrame().currentPart = currentPart;
		}
	}

	/**
	 * Sets the default cursor.
	 */
	public void setDefaultCursor() {
		SwingUtil.setDefaultCursor(SbApp.getMainFrame());
	}

	/**
	 * Sets the default layout.
	 */
	public void setDefaultLayout() {
		SbApp.trace("MainFrame.setDefaultLayout()");
		SbView scenesView = getView(ViewName.SCENES);
		SbView chaptersView = getView(ViewName.CHAPTERS);
		SbView partsView = getView(ViewName.PARTS);
		SbView locationsView = getView(ViewName.LOCATIONS);
		SbView personsView = getView(ViewName.PERSONS);
		SbView relationshipView = getView(ViewName.RELATIONSHIPS);
		SbView gendersView = getView(ViewName.GENDERS);
		SbView categoriesView = getView(ViewName.CATEGORIES);
		SbView listAttributes = getView(ViewName.ATTRIBUTES);
		SbView strandsView = getView(ViewName.STRANDS);
		SbView ideasView = getView(ViewName.IDEAS);
		SbView tagsView = getView(ViewName.TAGS);
		SbView memosView = getView(ViewName.MEMOS);
		SbView itemsView = getView(ViewName.ITEMS);
		SbView tagLinksView = getView(ViewName.TAGLINKS);
		SbView itemLinksView = getView(ViewName.ITEMLINKS);
		SbView internalsView = getView(ViewName.INTERNALS);
		SbView chronoView = getView(ViewName.CHRONO);
		SbView bookView = getView(ViewName.BOOK);
		SbView manageView = getView(ViewName.MANAGE);
		SbView readingView = getView(ViewName.READING);
		SbView memoriaView = getView(ViewName.MEMORIA);
		SbView chartPersonsByDate = getView(ViewName.CHART_PERSONS_BY_DATE);
		SbView chartPersonsByScene = getView(ViewName.CHART_PERSONS_BY_SCENE);
		SbView chartWiWW = getView(ViewName.CHART_WiWW);
		SbView chartStrandsByDate = getView(ViewName.CHART_STRANDS_BY_DATE);
		SbView chartOccurrenceOfPersons = getView(ViewName.CHART_OCCURRENCE_OF_PERSONS);
		SbView chartOccurrenceOfLocations = getView(ViewName.CHART_OCCURRENCE_OF_LOCATIONS);
		SbView chartGantt = getView(ViewName.CHART_GANTT);
		// SbView editorView = getView(ViewName.EDITOR);
		SbView treeView = getView(ViewName.TREE);
		SbView infoView = getView(ViewName.INFO);
		SbView navigationView = getView(ViewName.NAVIGATION);
		SbView planView = getView(ViewName.PLAN);
		SbView timeEventView = getView(ViewName.TIMEEVENT);
		TabWindow tabInfoNavi = new TabWindow(new SbView[] { infoView, navigationView });
		tabInfoNavi.setName("tabInfoNaviWindow");
		SplitWindow swTreeInfo = new SplitWindow(false, 0.6f, treeView, tabInfoNavi);
		swTreeInfo.setName("swTreeInfo");
		TabWindow tabWindow = new TabWindow(new SbView[] { chronoView, bookView, manageView, readingView, memoriaView,
				scenesView, personsView, relationshipView, locationsView, chaptersView, gendersView, categoriesView,
				partsView, strandsView, ideasView, tagsView, itemsView, tagLinksView, itemLinksView, internalsView,
				listAttributes, chartPersonsByDate, chartPersonsByScene, chartWiWW, chartStrandsByDate,
				chartOccurrenceOfPersons, chartOccurrenceOfLocations, chartGantt, planView, timeEventView });
		tabWindow.setName("tabWindow");
		SplitWindow swTabWinMemo = new SplitWindow(true, 0.60f, tabWindow, memosView);
		swTabWinMemo.setName("swTabWinMemos");
		SplitWindow swMain = new SplitWindow(true, 0.20f, swTreeInfo, swTabWinMemo);
		// SplitWindow swMain = new SplitWindow(true, 0.20f, swTreeInfo,
		// tabWindow);
		swMain.setName("swMain");
		rootWindow.setWindow(swMain);
		bookView.close();
		manageView.close();
		readingView.close();
		memoriaView.close();
		chaptersView.close();
		partsView.close();
		personsView.close();
		relationshipView.close();
		gendersView.close();
		categoriesView.close();
		listAttributes.close();
		strandsView.close();
		ideasView.close();
		tagsView.close();
		tagLinksView.close();
		itemsView.close();
		itemLinksView.close();
		internalsView.close();
		chartPersonsByDate.close();
		chartPersonsByScene.close();
		chartWiWW.close();
		chartStrandsByDate.close();
		chartOccurrenceOfPersons.close();
		chartOccurrenceOfLocations.close();
		chartGantt.close();
		planView.close();
		timeEventView.close();
		memosView.close();
		// memoView.minimize(Direction.RIGHT);
		// WindowBar windowBar = rootWindow.getWindowBar(Direction.RIGHT);
		// windowBar.setContentPanelSize(EntityEditor.MINIMUM_SIZE.width + 20);
		infoView.restoreFocus();
		chronoView.restoreFocus();
		rootWindow.getWindowBar(Direction.RIGHT).setEnabled(true);
		DockingWindowUtil.setRespectMinimumSize(SbApp.getMainFrame());
		SbApp.trace("end of MainFrame.setDefaultLayout()");
	}

	/**
	 * Sets the main tool bar.
	 *
	 * @param toolBar
	 *            the new main tool bar
	 */
	public void setMainToolBar(JToolBar toolBar) {
		if (mainToolBar != null) {
			SwingUtil.unfloatToolBar(mainToolBar);
			getContentPane().remove(mainToolBar);
		}
		SbApp.getMainFrame().mainToolBar = toolBar;
		getContentPane().add(mainToolBar, BorderLayout.NORTH);
	}

	/**
	 * Sets the maximized.
	 */
	public void setMaximized() {
		setExtendedState(Frame.MAXIMIZED_BOTH);
	}

	// public void modelPropertyChange(PropertyChangeEvent evt) {
	// Object oldValue = evt.getOldValue();
	// Object newValue = evt.getNewValue();
	// String propName = evt.getPropertyName();
	/**
	 * Sets the title.
	 */
	// }
	public void setTitle() {
		SbApp.trace("MainFrame.setTitle()");
		String prodFullTitle = Storybook.PRODUCT_FULL_NAME.toString();
		if (dbFile != null) {
			Part part = getCurrentPart();
			String partName = "";
			if (part != null) {
				partName = part.getNumberName();
			}
			String title = dbFile.getName();
			Internal internal = BookUtil.get(SbApp.getMainFrame(), BookKey.TITLE, "");
			if (internal != null && !internal.getStringValue().isEmpty()) {
				title = internal.getStringValue();
			}
			setTitle(title + " [" + I18N.getMsg("msg.common.part") + " " + partName + "]" + " - " + prodFullTitle);
		} else {
			setTitle(prodFullTitle);
		}
	}

	/**
	 * Sets the waiting cursor.
	 */
	public void setWaitingCursor() {
		SwingUtil.setWaitingCursor(SbApp.getMainFrame());
	}

	/**
	 * Show and focus.
	 *
	 * @param viewName
	 *            the view name
	 */
	public void showAndFocus(ViewName viewName) {
		SbApp.trace("MainFrame.showAndFocus(" + viewName.name() + ")");
		View view = getView(viewName);
		view.restore();
		view.restoreFocus();
	}

	/**
	 * Show editor.
	 */
	public void showEditor() {
		SbApp.trace("MainFrame.showEditor()");
		/*
		 * SwingUtilities.invokeLater(new Runnable() {
		 * 
		 * @Override public void run() {
		 * SbApp.trace("MainFrame.showEditor()-->run"); SbView editorView =
		 * getView(ViewName.EDITOR); editorView.cleverRestoreFocus(); } });
		 */
		SbApp.trace("no MainFrame.showEditor()");
	}

	/**
	 * Show editor as dialog.
	 *
	 * @param entity
	 *            the entity
	 */
	public void showEditorAsDialog(AbstractEntity entity) {
		JDialog dlg = new JDialog(SbApp.getMainFrame(), true);
		if (EditorModless)
			dlg.setModalityType(Dialog.ModalityType.MODELESS);
		EntityEditor editor = new EntityEditor(SbApp.getMainFrame(), entity, dlg);
		dlg.setTitle(I18N.getMsg("msg.common.editor"));
		dlg.setSize(SbApp.getMainFrame().getWidth() / 2, 680);
		dlg.add(editor);
		dlg.setLocationRelativeTo(SbApp.getMainFrame());
		dlg.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		dlg.setVisible(true);
	}

	/**
	 * Show unicode dialog.
	 */
	public void showUnicodeDialog() {
		unicodeDialog.show();
	}

	/**
	 * Show view.
	 *
	 * @param viewName
	 *            the view name
	 */
	public void showView(ViewName viewName) {
		SbApp.trace("MainFrame.showView(" + viewName.name() + ")");
		// if (viewName.equals(SbConstants.ViewName.EDITOR)) {
		// return;
		// }
		setWaitingCursor();
		SbView view = getView(viewName);
		if (view.getRootWindow() != null) {
			view.restoreFocus();
		} else {
			SbApp.trace(">>> RootWindow=null");
			DockingUtil.addWindow(view, rootWindow);
		}
		view.requestFocusInWindow();
		DockingWindowUtil.setRespectMinimumSize(SbApp.getMainFrame());
		setDefaultCursor();
		/*
		 * if (viewName.equals(SbConstants.ViewName.EDITOR)) { showEditor(); }
		 */
	}

	/**
	 * Update stat.
	 */
	public void updateStat() {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}
}
