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

/**
 *
 * @author favdb
 */
public class SbCalendar {
	int DaysByYear=365;
	int DaysByWeek=7;
	int WeeksByYear=52;
	int MonthByYear=12;
	int HoursByDay=24;
	int MinByHour=60;
	int SecByMin=60;
	long StartDate=20000101000000L;
	List lstDays;
	List lstMonth;
	MainFrame mainFrame;
	public SbCalendar(MainFrame mainFrame) {
		this.mainFrame=mainFrame;
	}
	
	public void storeCalendar() {
		
	}
	
	public void getCalendar() {
		
	}
	
	public void setDaysByYear(int n) {DaysByYear=n; }
	
	public void setDaysByWeek(int n) {DaysByWeek=n; }
	
	public void setWeeksByYear(int n) {WeeksByYear=n;}
	
	public void setMonthByYear(int n) {MonthByYear=n;}
	
	public void setHoursByDay(int n) {HoursByDay=n;}
	
	public void setMinByHour(int n) {MinByHour=n;}
	
	public void setSecByMin(int n) {SecByMin=n;}
	
	public String dateToString(long date) {
		String rc="";
		return rc;
	}
	
	public long dateDiff(long date1, long date2) {
		long rc=0L;
		return rc;
	}
	
}
