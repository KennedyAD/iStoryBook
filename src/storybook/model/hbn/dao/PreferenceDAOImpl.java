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

import org.hibernate.Session;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

import storybook.model.hbn.entity.Preference;

// TODO: Auto-generated Javadoc
/**
 * The Class PreferenceDAOImpl.
 */
public class PreferenceDAOImpl extends SbGenericDAOImpl<Preference, Long> implements PreferenceDAO {

	/**
	 * Instantiates a new preference dao impl.
	 */
	public PreferenceDAOImpl() {
		super();
	}

	/**
	 * Instantiates a new preference dao impl.
	 *
	 * @param session the session
	 */
	public PreferenceDAOImpl(Session session) {
		super(session);
	}

	/**
	 * Find by key.
	 *
	 * @param key the key
	 * @return the preference
	 */
	public Preference findByKey(String key) {
		Filter filter = new Filter("key", key, Filter.OP_EQUAL);
		Search search = new Search();
		search.addFilter(filter);
		return searchUnique(search);
	}

	/**
	 * Removes the.
	 *
	 * @param key the key
	 */
	public void remove(String key) {
		Preference pref = findByKey(key);
		if (pref != null) {
			session.delete(pref);
		}
	}

	/**
	 * Save or update.
	 *
	 * @param key the key
	 * @param value the value
	 */
	public void saveOrUpdate(String key, Boolean value) {
		Preference pref = findByKey(key);
		if (pref == null) {
			pref = new Preference(key, value);
			session.save(pref);
		} else {
			pref.setBooleanValue(value);
			session.update(pref);
		}
	}

	/**
	 * Save or update.
	 *
	 * @param key the key
	 * @param value the value
	 */
	public void saveOrUpdate(String key, byte[] value) {
		Preference pref = findByKey(key);
		if (pref == null) {
			pref = new Preference(key, value);
			session.save(pref);
		} else {
			pref.setBinValue(value);
			session.update(pref);
		}
	}

	/**
	 * Save or update.
	 *
	 * @param key the key
	 * @param value the value
	 */
	public void saveOrUpdate(String key, Integer value) {
		Preference pref = findByKey(key);
		if (pref == null) {
			pref = new Preference(key, value);
			session.save(pref);
		} else {
			pref.setIntegerValue(value);
			session.update(pref);
		}
	}

	/**
	 * Save or update.
	 *
	 * @param key the key
	 * @param value the value
	 */
	public void saveOrUpdate(String key, Object value) {
		if (value instanceof String) {
			saveOrUpdate(key, (String) value);
			return;
		}
		if (value instanceof Integer) {
			saveOrUpdate(key, (Integer) value);
			return;
		}
		if (value instanceof Boolean) {
			saveOrUpdate(key, (Boolean) value);
			return;
		}
		if (value instanceof byte[]) {
			saveOrUpdate(key, (byte[]) value);
			return;
		}
	}

	/**
	 * Save or update.
	 *
	 * @param key the key
	 * @param value the value
	 */
	public void saveOrUpdate(String key, String value) {
		Preference pref = findByKey(key);
		if (pref == null) {
			pref = new Preference(key, value);
			session.save(pref);
		} else {
			pref.setStringValue(value);
			session.update(pref);
		}
	}
}
