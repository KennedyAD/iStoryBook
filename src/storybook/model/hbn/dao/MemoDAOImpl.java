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

import storybook.model.hbn.entity.Memo;

// TODO: Auto-generated Javadoc
/**
 * The Class MemoDAOImpl.
 */
public class MemoDAOImpl extends SbGenericDAOImpl<Memo, Long> implements MemoDAO {

	/**
	 * Instantiates a new memo dao impl.
	 */
	public MemoDAOImpl() {
		super();
	}

	/**
	 * Instantiates a new memo dao impl.
	 *
	 * @param session the session
	 */
	public MemoDAOImpl(Session session) {
		super(session);
	}
}
