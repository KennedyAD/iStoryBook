package storybook.ui.panel.linkspanel;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.util.Collections;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import org.hibernate.Session;
import org.hibernate.UnresolvableObjectException;

import net.miginfocom.swing.MigLayout;
import storybook.controller.BookController;
import storybook.model.BookModel;
import storybook.model.hbn.entity.Location;
import storybook.model.hbn.entity.Scene;
import storybook.toolkit.swing.SwingUtil;
import storybook.ui.MainFrame;
import storybook.ui.panel.AbstractPanel;


// TODO: Auto-generated Javadoc
/**
 * The Class LocationLinksPanel.
 */
public class LocationLinksPanel extends AbstractPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2782944328954941552L;
	
	/** The scene. */
	private Scene scene;
	
	/** The set size. */
	private boolean setSize;

	/**
	 * Instantiates a new location links panel.
	 *
	 * @param mainFrame the main frame
	 * @param scene the scene
	 */
	public LocationLinksPanel(MainFrame mainFrame, Scene scene) {
		this(mainFrame, scene, true);
	}

	/**
	 * Instantiates a new location links panel.
	 *
	 * @param mainFrame the main frame
	 * @param scene the scene
	 * @param setSize the set size
	 */
	public LocationLinksPanel(MainFrame mainFrame, Scene scene, boolean setSize) {
		this.mainFrame = mainFrame;
		this.scene = scene;
		this.setSize = setSize;
		refresh();
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#init()
	 */
	@Override
	public void init() {
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#initUi()
	 */
	@Override
	public void initUi() {
		setLayout(new MigLayout("insets 0", "grow"));
		JTextArea ta = new JTextArea();
		ta.setLineWrap(true);
		ta.setWrapStyleWord(true);
		ta.setEditable(false);
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		session.refresh(scene);
		List<Location> locations = scene.getLocations();
		if (locations != null) {
			Collections.sort(locations);
		}
		int c = 0;
		for (Location location : locations) {
			try {
				session.refresh(location);
			} catch (UnresolvableObjectException e) {
				e.printStackTrace();
				continue;
			}
			ta.append(location.toString());
			if (c < locations.size() - 1) {
				ta.append(", ");
			}
			++c;
		}
		model.commit();

		if (setSize) {
			JScrollPane scroller = new JScrollPane();
			scroller.setViewportView(ta);
			scroller.setBorder(null);
			if (setSize) {
				scroller.setMinimumSize(new Dimension(100, 30));
				scroller.setPreferredSize(new Dimension(170, 30));
			}
			SwingUtil.setUnitIncrement(scroller);
			scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			ta.setCaretPosition(0);
			add(scroller, "grow");
		} else {
			add(ta, "grow");
		}
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#modelPropertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void modelPropertyChange(PropertyChangeEvent evt) {
		Object newValue = evt.getNewValue();
		String propName = evt.getPropertyName();

		if (BookController.SceneProps.UPDATE.check(propName)) {
			if (!((Scene) newValue).getId().equals(scene.getId())) {
				// not this scene
				return;
			}
			refresh();
			return;
		}

		if (BookController.LocationProps.UPDATE.check(propName)) {
			refresh();
			return;
		}
	}
}
