package storybook.ui.edit;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

import org.hibernate.Session;

import com.googlecode.genericdao.search.Search;

import net.miginfocom.swing.MigLayout;
import storybook.model.BookModel;
import storybook.model.handler.AbstractEntityHandler;
import storybook.model.hbn.dao.SbGenericDAOImpl;
import storybook.model.hbn.entity.AbstractEntity;
import storybook.ui.MainFrame;
import storybook.ui.interfaces.IRefreshable;
import storybook.ui.panel.AbstractPanel;


// TODO: Auto-generated Javadoc
/**
 * The Class CheckBoxPanel.
 */
public class CheckBoxPanel extends AbstractPanel implements IRefreshable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8154756084759578453L;
	
	/** The cb map. */
	// private MainFrame mainFrame;
	private Map<AbstractEntity, JCheckBox> cbMap;
	
	/** The decorator. */
	private CbPanelDecorator decorator;
	
	/** The entity. */
	private AbstractEntity entity;
	
	/** The entity handler. */
	private AbstractEntityHandler entityHandler;
	
	/** The entities. */
	private List<AbstractEntity> entities;
	
	/** The search. */
	private Search search;
	
	/** The auto select. */
	private boolean autoSelect = true;

	/**
	 * Instantiates a new check box panel.
	 *
	 * @param mainFrame the main frame
	 */
	public CheckBoxPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		cbMap = new TreeMap<AbstractEntity, JCheckBox>();
	}

	/**
	 * Adds the entity.
	 *
	 * @param session the session
	 * @param entity the entity
	 */
	public void addEntity(Session session, AbstractEntity entity) {
		session.refresh(entity);
		JCheckBox cb = new JCheckBox();
		cb.setOpaque(false);
		cbMap.put(entity, cb);
		cb.setText(entity.toString());
		add(cb);
		add(new JLabel(entity.getIcon()));
	}

	/**
	 * Gets the auto select.
	 *
	 * @return the auto select
	 */
	public boolean getAutoSelect() {
		return autoSelect;
	}

	/**
	 * Gets the decorator.
	 *
	 * @return the decorator
	 */
	public CbPanelDecorator getDecorator() {
		return decorator;
	}

	/**
	 * Gets the entity.
	 *
	 * @return the entity
	 */
	public AbstractEntity getEntity() {
		return entity;
	}

	/**
	 * Gets the entity handler.
	 *
	 * @return the entity handler
	 */
	public AbstractEntityHandler getEntityHandler() {
		return entityHandler;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#getMainFrame()
	 */
	@Override
	public MainFrame getMainFrame() {
		return mainFrame;
	}

	/**
	 * Gets the search.
	 *
	 * @return the search
	 */
	public Search getSearch() {
		return search;
	}

	/**
	 * Gets the selected entities.
	 *
	 * @return the selected entities
	 */
	public List<AbstractEntity> getSelectedEntities() {
		ArrayList<AbstractEntity> ret = new ArrayList<AbstractEntity>();
		Iterator<Entry<AbstractEntity, JCheckBox>> it = cbMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<AbstractEntity, JCheckBox> pairs = it.next();
			JCheckBox cb = pairs.getValue();
			if (cb.isSelected()) {
				ret.add(pairs.getKey());
			}
		}
		return ret;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#init()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void init() {
		cbMap.clear();

		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		SbGenericDAOImpl<?, ?> dao = entityHandler.createDAO();
		dao.setSession(session);
		List<AbstractEntity> allEntities = null;
		if (search != null) {
			allEntities = dao.search(search);
		} else {
			allEntities = (List<AbstractEntity>) dao.findAll();
		}
		for (AbstractEntity entity2 : allEntities) {
			addEntity(session, entity2);
		}
		refresh();

		// refresh entities, must be before selectEntity()
		if (entities != null) {
			for (AbstractEntity ent : entities) {
				session.refresh(ent);
			}
			if (autoSelect) {
				for (AbstractEntity ent : entities) {
					selectEntity(session, ent);
				}
			}
		}
		model.commit();
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#initUi()
	 */
	@Override
	public void initUi() {
		setLayout(new MigLayout("wrap"));
		setBackground(Color.white);
		refresh();
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#modelPropertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void modelPropertyChange(PropertyChangeEvent evt) {
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#refresh()
	 */
	@Override
	public void refresh() {
		removeAll();

		decorator.decorateBeforeFirstEntity();
		Iterator<Entry<AbstractEntity, JCheckBox>> it = cbMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<AbstractEntity, JCheckBox> pairs = it.next();
			AbstractEntity ent = pairs.getKey();
			if (decorator != null) {
				decorator.decorateBeforeEntity(ent);
				decorator.decorateEntity(pairs.getValue(), ent);
			} else {
				add(pairs.getValue(), "split 2");
				add(new JLabel(ent.getIcon()));
			}
			if (decorator != null) {
				decorator.decorateAfterEntity(ent);
			}
		}

		revalidate();
		repaint();
	}

	/**
	 * Select entity.
	 *
	 * @param session the session
	 * @param ent the ent
	 */
	private void selectEntity(Session session, AbstractEntity ent) {
		JCheckBox cb = cbMap.get(ent);
		if (cb != null) {
			cb.setSelected(true);
		}
	}

	/**
	 * Sets the auto select.
	 *
	 * @param flag the new auto select
	 */
	public void setAutoSelect(boolean flag) {
		this.autoSelect = flag;
	}

	/**
	 * Sets the decorator.
	 *
	 * @param decorator the new decorator
	 */
	public void setDecorator(CbPanelDecorator decorator) {
		this.decorator = decorator;
	}

	/**
	 * Sets the entity.
	 *
	 * @param entity the new entity
	 */
	public void setEntity(AbstractEntity entity) {
		this.entity = entity;
	}

	/**
	 * Sets the entity handler.
	 *
	 * @param entityHandler the new entity handler
	 */
	public void setEntityHandler(AbstractEntityHandler entityHandler) {
		this.entityHandler = entityHandler;
	}

	/**
	 * Sets the entity list.
	 *
	 * @param entities the new entity list
	 */
	public void setEntityList(List<AbstractEntity> entities) {
		this.entities = entities;
	}

	/**
	 * Sets the search.
	 *
	 * @param search the new search
	 */
	public void setSearch(Search search) {
		this.search = search;
	}
}
