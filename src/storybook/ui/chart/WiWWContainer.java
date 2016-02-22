/*
 * SbApp: Open Source software for novelists and authors.
 * Original idea 2008 - 2012 Martin Mustun
 * Copyrigth (C) Favdb
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */
package storybook.ui.chart;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import storybook.model.BookModel;
import storybook.model.hbn.dao.LocationDAOImpl;
import storybook.model.hbn.entity.Location;
import storybook.model.hbn.entity.Person;
import storybook.toolkit.DateUtil;
import storybook.ui.MainFrame;

// TODO: Auto-generated Javadoc
/**
 * The Class WiWWContainer.
 */
public class WiWWContainer {
	
	/** The main frame. */
	private MainFrame mainFrame;
	
	/** The location. */
	private Location location;
	
	/** The in person list. */
	private List<Person> inPersonList;
	
	/** The out person list. */
	private List<Person> outPersonList;
	
	/** The date. */
	private Date date;
	
	/** The found. */
	private boolean found;

	/**
	 * Instantiates a new wi ww container.
	 *
	 * @param paramMainFrame the param main frame
	 * @param paramDate the param date
	 * @param paramLocation the param location
	 * @param paramList the param list
	 */
	public WiWWContainer(MainFrame paramMainFrame, Date paramDate, Location paramLocation, List<Person> paramList) {
		this.mainFrame = paramMainFrame;
		this.location = paramLocation;
		this.inPersonList = paramList;
		this.date = DateUtil.getZeroTimeDate(paramDate);
		this.outPersonList = new ArrayList<>();
		init();
	}

	/**
	 * Gets the character list.
	 *
	 * @return the character list
	 */
	public List<Person> getCharacterList() {
		return this.outPersonList;
	}

	/**
	 * Inits the.
	 */
	private void init() {
		Iterator localIterator = this.inPersonList.iterator();
		while (localIterator.hasNext()) {
			Person localPerson = (Person) localIterator.next();
			BookModel localDocumentModel = this.mainFrame.getBookModel();
			Session localSession = localDocumentModel.beginTransaction();
			LocationDAOImpl localLocationDAOImpl = new LocationDAOImpl(localSession);
			long l = localLocationDAOImpl.countByPersonLocationDate(localPerson, this.location, this.date);
			localDocumentModel.commit();
			if (l != 0L)
				this.outPersonList.add(localPerson);
		}
		if (this.outPersonList.isEmpty())
			this.found = false;
		else
			this.found = true;
	}

	/**
	 * Checks if is found.
	 *
	 * @return true, if is found
	 */
	public boolean isFound() {
		return this.found;
	}
}