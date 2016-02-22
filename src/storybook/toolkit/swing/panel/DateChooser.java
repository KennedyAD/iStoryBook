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

package storybook.toolkit.swing.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.sql.Timestamp;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import org.apache.commons.lang3.time.DateUtils;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import net.miginfocom.swing.MigLayout;
import storybook.SbConstants;
import storybook.model.EntityUtil;
import storybook.toolkit.DateUtil;
import storybook.toolkit.I18N;
import storybook.toolkit.PrefUtil;
import storybook.toolkit.swing.IconButton;
import storybook.ui.MainFrame;
import storybook.ui.panel.AbstractPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class DateChooser.
 *
 * @author martin
 */

public class DateChooser extends AbstractPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5048172756913072867L;
	
	/** The date chooser. */
	private JDateChooser dateChooser;
	
	/** The time spinner. */
	private JSpinner timeSpinner;
	
	/** The show date time. */
	private boolean showDateTime;

	/**
	 * Instantiates a new date chooser.
	 *
	 * @param mainFrame the main frame
	 */
	public DateChooser(MainFrame mainFrame) {
		super(mainFrame);
		init();
		initUi();
	}

	/**
	 * Instantiates a new date chooser.
	 *
	 * @param mainFrame the main frame
	 * @param showDateTime the show date time
	 */
	public DateChooser(MainFrame mainFrame, boolean showDateTime) {
		super(mainFrame);
		this.showDateTime = showDateTime;
		init();
		initUi();
	}

	/**
	 * Gets the clear time action.
	 *
	 * @return the clear time action
	 */
	private AbstractAction getClearTimeAction() {
		return new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -8387950136162198535L;

			@Override
			public void actionPerformed(ActionEvent e) {
				timeSpinner.setValue(DateUtil.getZeroTimeDate());
			}
		};
	}

	/**
	 * Gets the first date action.
	 *
	 * @return the first date action
	 */
	private AbstractAction getFirstDateAction() {
		return new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -5663213252714038622L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Date date = EntityUtil.findFirstDate(mainFrame);
				dateChooser.setDate(date);
			}
		};
	}

	/**
	 * Gets the last date action.
	 *
	 * @return the last date action
	 */
	private AbstractAction getLastDateAction() {
		return new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -6404706225689351989L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Date date = EntityUtil.findLastDate(mainFrame);
				dateChooser.setDate(date);
			}
		};
	}

	/**
	 * Gets the next day action.
	 *
	 * @return the next day action
	 */
	private AbstractAction getNextDayAction() {
		return new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1631656285497087555L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Date date;
				if (dateChooser.getDate() == null) {
					date = EntityUtil.findLastDate(mainFrame);
				} else {
					date = DateUtils.addDays(dateChooser.getDate(), 1);
				}
				dateChooser.setDate(date);
			}
		};
	}

	/**
	 * Gets the prev day action.
	 *
	 * @return the prev day action
	 */
	private AbstractAction getPrevDayAction() {
		return new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -2515717394312376649L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Date date;
				if (dateChooser.getDate() == null) {
					date = EntityUtil.findFirstDate(mainFrame);
				} else {
					date = DateUtils.addDays(dateChooser.getDate(), -1);
				}
				dateChooser.setDate(date);
			}
		};
	}

	/**
	 * Gets the timestamp.
	 *
	 * @return the timestamp
	 */
	public Timestamp getTimestamp() {
		if (dateChooser.getDate() == null) {
			return null;
		}
		Date date = dateChooser.getDate();
		Date time = (Date) timeSpinner.getValue();
		return DateUtil.addTimeFromDate(date, time);
	}

	/**
	 * Checks for error.
	 *
	 * @return true, if successful
	 */
	public boolean hasError() {
		JTextFieldDateEditor tf = (JTextFieldDateEditor) dateChooser.getComponent(1);
		if (tf.getForeground() == Color.red) {
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#init()
	 */
	@Override
	public void init() {
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#initUi()
	 */
	@Override
	public void initUi() {
		setLayout(new MigLayout("flowx, ins 0"));

		dateChooser = new JDateChooser();
		dateChooser
				.setDateFormatString(PrefUtil.get(SbConstants.PreferenceKey.DATEFORMAT, "MM-dd-yyyy").getStringValue());
		dateChooser.setMinimumSize(new Dimension(120, 20));

		JLabel lbTime = new JLabel(I18N.getMsgColon("msg.common.time"));

		IconButton btClearTime = new IconButton("icon.small.clear", getClearTimeAction());
		btClearTime.setSize20x20();

		timeSpinner = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, I18N.TIME_FORMAT);
		timeSpinner.setEditor(timeEditor);
		timeSpinner.setValue(DateUtil.getZeroTimeDate());
		timeSpinner.setPreferredSize(new Dimension(80, 30));

		IconButton btFirstDate = new IconButton("icon.small.first", getFirstDateAction());
		btFirstDate.setSize20x20();

		IconButton btPrevDay = new IconButton("icon.small.previous", getPrevDayAction());
		btPrevDay.setSize20x20();

		IconButton btNextDay = new IconButton("icon.small.next", getNextDayAction());
		btNextDay.setSize20x20();

		IconButton btLastDate = new IconButton("icon.small.last", getLastDateAction());
		btLastDate.setSize20x20();

		// layout
		add(dateChooser, "gapafter 10");
		add(btFirstDate);
		add(btPrevDay);
		add(btNextDay);
		add(btLastDate);
		if (showDateTime) {
			add(lbTime, "aligny center,newline,span,split 3");
			add(timeSpinner);
			add(btClearTime);
		}
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#modelPropertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void modelPropertyChange(PropertyChangeEvent evt) {

	}

	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate(Date date) {
		dateChooser.setDate(date);
		if (date != null) {
			timeSpinner.setValue(date);
		}
	}
}
