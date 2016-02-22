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
package storybook.toolkit;

import java.util.List;

import storybook.ui.MainFrame;

// TODO: Auto-generated Javadoc
/**
 * The Class SbCalendar.
 *
 * @author favdb
 */
public class SbCalendar {
	
	/** The Days by year. */
	int DaysByYear = 365;
	
	/** The Days by week. */
	int DaysByWeek = 7;
	
	/** The Weeks by year. */
	int WeeksByYear = 52;
	
	/** The Month by year. */
	int MonthByYear = 12;
	
	/** The Hours by day. */
	int HoursByDay = 24;
	
	/** The Min by hour. */
	int MinByHour = 60;
	
	/** The Sec by min. */
	int SecByMin = 60;
	
	/** The Start date. */
	long StartDate = 20000101000000L;
	
	/** The lst days. */
	List lstDays;
	
	/** The lst month. */
	List lstMonth;
	
	/** The main frame. */
	MainFrame mainFrame;

	/**
	 * Instantiates a new sb calendar.
	 *
	 * @param mainFrame the main frame
	 */
	public SbCalendar(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	/**
	 * Date diff.
	 *
	 * @param date1 the date1
	 * @param date2 the date2
	 * @return the long
	 */
	public long dateDiff(long date1, long date2) {
		long rc = 0L;
		return rc;
	}

	/**
	 * Date to string.
	 *
	 * @param date the date
	 * @return the string
	 */
	public String dateToString(long date) {
		String rc = "";
		return rc;
	}

	/**
	 * Gets the calendar.
	 *
	 * @return the calendar
	 */
	public void getCalendar() {

	}

	/**
	 * Sets the days by week.
	 *
	 * @param n the new days by week
	 */
	public void setDaysByWeek(int n) {
		DaysByWeek = n;
	}

	/**
	 * Sets the days by year.
	 *
	 * @param n the new days by year
	 */
	public void setDaysByYear(int n) {
		DaysByYear = n;
	}

	/**
	 * Sets the hours by day.
	 *
	 * @param n the new hours by day
	 */
	public void setHoursByDay(int n) {
		HoursByDay = n;
	}

	/**
	 * Sets the min by hour.
	 *
	 * @param n the new min by hour
	 */
	public void setMinByHour(int n) {
		MinByHour = n;
	}

	/**
	 * Sets the month by year.
	 *
	 * @param n the new month by year
	 */
	public void setMonthByYear(int n) {
		MonthByYear = n;
	}

	/**
	 * Sets the sec by min.
	 *
	 * @param n the new sec by min
	 */
	public void setSecByMin(int n) {
		SecByMin = n;
	}

	/**
	 * Sets the weeks by year.
	 *
	 * @param n the new weeks by year
	 */
	public void setWeeksByYear(int n) {
		WeeksByYear = n;
	}

	/**
	 * Store calendar.
	 */
	public void storeCalendar() {

	}

}
