package storybook.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.hibernate.Session;

import storybook.SbApp;
import storybook.model.hbn.SbSessionFactory;
import storybook.model.hbn.entity.AbstractEntity;
import storybook.ui.MainFrame;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractModel.
 */
public abstract class AbstractModel {

	/** The property change support. */
	protected PropertyChangeSupport propertyChangeSupport;
	
	/** The session factory. */
	protected SbSessionFactory sessionFactory;
	
	/** The main frame. */
	MainFrame mainFrame;

	/**
	 * Instantiates a new abstract model.
	 *
	 * @param m the m
	 */
	public AbstractModel(MainFrame m) {
		mainFrame = m;
		propertyChangeSupport = new PropertyChangeSupport(this);
		sessionFactory = new SbSessionFactory();
	}

	/**
	 * Adds the property change listener.
	 *
	 * @param l the l
	 */
	public void addPropertyChangeListener(PropertyChangeListener l) {
		SbApp.trace("AbstractModel.addPropertyChangeListener(" + l.toString() + ")");
		propertyChangeSupport.addPropertyChangeListener(l);
	}

	/**
	 * Begin transaction.
	 *
	 * @return the session
	 */
	public Session beginTransaction() {
		SbApp.trace("AbstractModel.beginTransaction()");
		Session session = sessionFactory.getSession();
		session.beginTransaction();
		return session;
	}

	/**
	 * Close session.
	 */
	public void closeSession() {
		sessionFactory.closeSession();
	}

	/**
	 * Commit.
	 */
	public void commit() {
		Session session = sessionFactory.getSession();
		session.getTransaction().commit();
	}

	/**
	 * Edits the entity.
	 *
	 * @param entity the entity
	 */
	public void editEntity(AbstractEntity entity) {
		mainFrame.showEditorAsDialog(entity);
	}

	/**
	 * Fire again.
	 */
	public abstract void fireAgain();

	/**
	 * Fire property change.
	 *
	 * @param propertyName the property name
	 * @param oldValue the old value
	 * @param newValue the new value
	 */
	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		SbApp.trace(
				"AbstractModel.firePropertyChange(" + propertyName + "," + "oldValue..." + "," + "newValue..." + ")");
		propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}

	/**
	 * Gets the session.
	 *
	 * @return the session
	 */
	public Session getSession() {
		return sessionFactory.getSession();
	}

	/**
	 * Gets the session factory.
	 *
	 * @return the session factory
	 */
	public SbSessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * Inits the default.
	 */
	public void initDefault() {
		fireAgain();
	}

	/**
	 * Inits the session.
	 *
	 * @param dbName the db name
	 */
	public void initSession(String dbName) {
		SbApp.trace("AbstractModel.initSession(" + dbName + ")");
		sessionFactory.init(dbName);
	}

	/**
	 * Removes the property change listener.
	 *
	 * @param l the l
	 */
	public void removePropertyChangeListener(PropertyChangeListener l) {
		propertyChangeSupport.removePropertyChangeListener(l);
	}
}
