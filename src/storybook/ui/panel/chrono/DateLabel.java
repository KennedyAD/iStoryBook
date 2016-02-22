package storybook.ui.panel.chrono;

import java.awt.Color;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.apache.commons.lang3.time.FastDateFormat;

import storybook.toolkit.I18N;
import storybook.toolkit.swing.SwingUtil;


// TODO: Auto-generated Javadoc
/**
 * The Class DateLabel.
 */
public class DateLabel extends JLabel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3458661597740453223L;
	
	/** The date. */
	private Date date;

	/**
	 * Instantiates a new date label.
	 *
	 * @param date the date
	 */
	public DateLabel(Date date) {
		super();
		this.date = date;
		setText(getDateText());
		setToolTipText(getDateText());
		setIcon(I18N.getIcon("icon.small.chrono.view"));
		setBackground(new Color(240, 240, 240));
		setOpaque(true);
		setHorizontalAlignment(SwingConstants.CENTER);
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Gets the date text.
	 *
	 * @return the date text
	 */
	public final String getDateText() {
		if (date == null) {
			return "";
		}
		String dateStr = FastDateFormat.getDateInstance(FastDateFormat.MEDIUM).format(date);
		String dayStr = SwingUtil.getDayName(date);
		return dayStr + " - " + dateStr;
	}

	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate(Date date) {
		this.date = date;
	}
}
