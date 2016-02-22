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

package storybook.model.hbn.dao;

import java.io.Serializable;

import org.hibernate.Session;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class SbGenericDAOImpl.
 *
 * @param <T> the generic type
 * @param <ID> the generic type
 */
public abstract class SbGenericDAOImpl<T, ID extends Serializable> extends GenericDAOImpl<T, ID> {

	/** The session. */
	protected Session session;

	/**
	 * Instantiates a new sb generic dao impl.
	 */
	public SbGenericDAOImpl() {
	}

	/**
	 * Instantiates a new sb generic dao impl.
	 *
	 * @param session the session
	 */
	public SbGenericDAOImpl(Session session) {
		this.session = session;
		setSessionFactory(session.getSessionFactory());
	}

	/* (non-Javadoc)
	 * @see com.googlecode.genericdao.dao.hibernate.HibernateBaseDAO#getSession()
	 */
	@Override
	public Session getSession() {
		return session;
	}

	/**
	 * Sets the session.
	 *
	 * @param session the new session
	 */
	public void setSession(Session session) {
		this.session = session;
		setSessionFactory(session.getSessionFactory());
	}
}
