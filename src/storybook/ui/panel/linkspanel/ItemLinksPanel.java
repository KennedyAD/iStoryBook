package storybook.ui.panel.linkspanel;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.util.Collections;
import java.util.List;

import javax.swing.JLabel;

import org.hibernate.Session;
import org.hibernate.UnresolvableObjectException;

import net.miginfocom.swing.MigLayout;
import storybook.controller.BookController;
import storybook.model.BookModel;
import storybook.model.EntityUtil;
import storybook.model.hbn.entity.Item;
import storybook.model.hbn.entity.Scene;
import storybook.toolkit.swing.label.CleverLabel;
import storybook.ui.MainFrame;
import storybook.ui.panel.AbstractPanel;


// TODO: Auto-generated Javadoc
/**
 * The Class ItemLinksPanel.
 */
public class ItemLinksPanel extends AbstractPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5538737201850806204L;
	
	/** The scene. */
	private Scene scene;
	
	/** The vertical. */
	private boolean vertical = false;

	/**
	 * Instantiates a new item links panel.
	 *
	 * @param mainFrame the main frame
	 * @param scene the scene
	 */
	public ItemLinksPanel(MainFrame mainFrame, Scene scene) {
		this(mainFrame, scene, false);
	}

	/**
	 * Instantiates a new item links panel.
	 *
	 * @param mainFrame the main frame
	 * @param scene the scene
	 * @param vertical the vertical
	 */
	public ItemLinksPanel(MainFrame mainFrame, Scene scene, boolean vertical) {
		this.mainFrame = mainFrame;
		this.scene = scene;
		this.vertical = vertical;
		refresh();
	}

	/**
	 * Gets the scene.
	 *
	 * @return the scene
	 */
	public Scene getScene() {
		return scene;
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
		if (vertical) {
			setLayout(new MigLayout("wrap 2,insets 0"));
		} else {
			setLayout(new MigLayout("flowx,insets 0"));
			setMaximumSize(new Dimension(170, 50));
		}
		setOpaque(false);
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		session.refresh(scene);
		List<Item> list = scene.getItems();
		if (list != null) {
			Collections.sort(list);
		}
		for (Item item : list) {
			try {
				session.refresh(item);
			} catch (UnresolvableObjectException e) {
				e.printStackTrace();
				continue;
			}
			// Color color = item.getJColor();
			JLabel lbName = new JLabel(item.getName());
			CleverLabel lbAbbr = new CleverLabel(item.getAbbr());
			lbAbbr.setToolTipText(EntityUtil.getToolTip(item, scene.getDate()));
			// if (color != null) {
			// lbAbbr.setBackground(color);
			// } else {
			lbAbbr.setOpaque(false);
			// }
			if (vertical) {
				add(lbAbbr);
				add(lbName);
			} else {
				add(lbAbbr, "gap 0");
			}
		}
		model.commit();
	}

	/**
	 * Checks if is vertical.
	 *
	 * @return true, if is vertical
	 */
	public boolean isVertical() {
		return vertical;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#modelPropertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void modelPropertyChange(PropertyChangeEvent evt) {
		// Object oldValue = evt.getOldValue();
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

		if (BookController.PersonProps.UPDATE.check(propName)) {
			refresh();
			return;
		}
	}
}
