package storybook.test.model;

import org.hibernate.Session;

import storybook.model.hbn.SbSessionFactory;
import storybook.model.hbn.dao.ChapterDAOImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class TestThread.
 */
public class TestThread extends Thread {

	/** The session factory. */
	private SbSessionFactory sessionFactory;

	/**
	 * Instantiates a new test thread.
	 *
	 * @param sessionFactory the session factory
	 */
	public TestThread(SbSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		System.out.println("Test03.TestThread.run(): start");
		Session session2 = sessionFactory.getSession();
		session2.beginTransaction();
		sessionFactory.query(new ChapterDAOImpl(sessionFactory.getSession()));
		session2.getTransaction().commit();
		System.out.println("Test03.TestThread.run(): finished");
	}
}
