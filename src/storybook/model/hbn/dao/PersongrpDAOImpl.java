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

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import storybook.SbApp;
import storybook.model.hbn.entity.Person;
import storybook.model.hbn.entity.Persongrp;
import storybook.model.hbn.entity.Item;
import storybook.model.hbn.entity.Location;
import storybook.model.hbn.entity.Scene;

public class PersongrpDAOImpl extends SbGenericDAOImpl<Persongrp, Long> implements PersongrpDAO {

	public PersongrpDAOImpl() {
		super();
	}

	public PersongrpDAOImpl(Session session) {
		super(session);
		SbApp.trace("PersongrpDAOImpl("+session.getEntityName(this)+")");
	}

	@SuppressWarnings("unchecked")
	public List<Persongrp> findByScene(Scene scene) {
		Criteria crit = session.createCriteria(Persongrp.class);
		crit.add(Restrictions.eq("startScene", scene));
		List<Persongrp> persongrps = (List<Persongrp>) crit.list();
		return persongrps;
	}

	@SuppressWarnings("unchecked")
	public List<Persongrp> findByStartOrEndScene(Scene scene) {
		Criteria crit = session.createCriteria(Persongrp.class);
		Criterion cr1 = Restrictions.eq("startScene", scene);
		Criterion cr2 = Restrictions.eq("endScene", scene);
		crit.add(Restrictions.or(cr1, cr2));
		List<Persongrp> groups = (List<Persongrp>) crit.list();
		return groups;
	}

	@SuppressWarnings("unchecked")
	public List<Persongrp> findByPerson(Person person) {
		Query query = session.createQuery("select s from Persongrp as s"
				+ " join s.persons as p"
				+ " where p=:person");
		query.setParameter("person", person);
		List<Persongrp> ret = query.list();
		return ret;
	}

	@SuppressWarnings("unchecked")
	public List<Persongrp> findByItem(Item item) {
		Query query = session.createQuery("select s from Persongrp as s"
				+ " join s.items as p"
				+ " where p=:item");
		query.setParameter("item", item);
		List<Persongrp> ret = query.list();
		return ret;
	}

	@SuppressWarnings("unchecked")
	public List<Persongrp> findByLocation(Location location) {
		Query query = session.createQuery("select s from Persongrp as s"
				+ " join s.locations as p"
				+ " where p=:location");
		query.setParameter("location", location);
		List<Persongrp> ret = query.list();
		return ret;
	}

}
