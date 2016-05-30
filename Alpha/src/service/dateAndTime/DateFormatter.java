package service.dateAndTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import service.exceptions.InvalidArgumentException;
import shippingCore.IConstants;

/**
 * This class is use to form the date object from a given string or string from
 * a given object (for example date).
 * 
 * DateFormatter class helps you to format and parse numbers for any locale. By
 * default, it will take the locale from the JVM to format date.
 * <p>
 * 
 * To get the date formatter for the given locale, use the method
 * getDateFormatterInstance(locale).
 * <p>
 * 
 * It is possible to specify the different style (for example
 * dateformatter.formatDate(date, DateFormat.Long)) to get the date as string.
 * 
 * @author Last modified by $Author: mle $
 * @version $Revision: 79369 $
 */
public class DateFormatter
{
	/** for proper date formatting for logfiles */
	private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm");

	/** used to format the date */
	private static final String DELIMITER = "/";

	/** default formatting style */
	private static final int DEFAULT_STYLE = DateFormat.DEFAULT;

	/** SHORT formatting style */
	private static final int SHORT_STYLE = DateFormat.SHORT;

	/** MEDIUM formatting style */
	private static final int MEDIUM_STYLE = DateFormat.MEDIUM;

	/** LONG formatting style */
	private static final int LONG_STYLE = DateFormat.LONG;

	/** use to check the style pattern */
	private static final String TOKEN_DELIMITER = "_";

	/** store the local specific formatter to use later */
	private static HashMap<Locale, DateFormatter> formatterHash = new HashMap<Locale, DateFormatter>();

	/** use to check the style pattern */
	private static Map<String, Integer> styleMap = new HashMap<String, Integer>();

	/** initializing the style hash */
	static {
		styleMap.put("FULL", Integer.valueOf(DateFormat.FULL));
		styleMap.put("LONG", Integer.valueOf(DateFormat.LONG));
		styleMap.put("MEDIUM", Integer.valueOf(DateFormat.MEDIUM));
		styleMap.put("SHORT", Integer.valueOf(DateFormat.SHORT));
		styleMap.put("DEFAULT", Integer.valueOf(DateFormat.DEFAULT));
	}

	/** used to format the date */
	private Locale locale = null;

	/**
	 * Constructor which sets the locale from JVM
	 */
	public DateFormatter()
	{
		this.locale = Locale.getDefault();
	}

	/**
	 * Constuctor allow to specify the locale
	 * 
	 * @param locale the locel which need to be set
	 */
	private DateFormatter(Locale locale)
	{
		this.locale = locale;
	}

	/**
	 * Forming the date from the given day, month and year. day string should be
	 * always number string, month can be either number string or text string
	 * (Mai, May, depeneds on the locale) and year string should be number
	 * string. In JDK 1.3 two digit year string (00-21) -->(2000-2020) and for
	 * (22-99)-->(1922-1999)
	 * 
	 * 
	 * @param day Day of month (eg. '5', '06', '23', etc).
	 * @param month Month of year (eg. '1', '03', 'Jan', 'May', 'Mai', etc).
	 * @param year Year part of the date (eg. '1972', '2003', '93',etc) it should
	 *        conatain either two digit or four digit number as string
	 * @return date object from the given string
	 * @throws DateFormatterException if the date creation fails or date is
	 *         invalid
	 */
	public Date parseDate(String day, String month, String year) throws DateFormatterException
	{
		return parseDate(null, null, null, day, month, year);
	}

	/**
	 * Forming the date from hours,day, month and year. month value could be
	 * either text string (Mai, May, depeneds on the locale) or number string and
	 * other values should be number string.
	 * 
	 * @param hours Hours of the day (eg. 0-23)
	 * @param day Day of month (eg. '5', '06', '23', etc).
	 * @param month Month of year (eg. '1', '03', 'Jan', 'May', 'Mai', etc).
	 * @param year Year part of the date (eg. '1972', '2003', etc)
	 * @return date object from the given string
	 * @throws DateFormatterException if the date creation fails or date is
	 *         invalid
	 */
	public Date parseDate(String hours, String day, String month, String year)
			throws DateFormatterException
	{
		return parseDate(null, null, hours, day, month, year);
	}

	/**
	 * Forming the date from minutes, hours, day, month and year. month value
	 * could be either text string (Mai, May, depeneds on the locale) or number
	 * string and other values should be number string
	 * 
	 * @param minutes Minutes of hour (eg. 0-59)
	 * @param hours Hours of day (eg. 0-23)
	 * @param day Day of month (eg. '5', '06', '23', etc).
	 * @param month Month of year (eg. '1', '03', 'Jan', 'May', 'Mai', etc).
	 * @param year Year part of the date (eg. '1972', '2003', etc)
	 * @return date object from the given string
	 * @throws DateFormatterException if the date creation fails or date is
	 *         invalid
	 */
	public Date parseDate(String minutes, String hours, String day, String month, String year)
			throws DateFormatterException
	{
		return parseDate(null, minutes, hours, day, month, year);
	}

	/**
	 * Forming the date from second, minutes, hours, day, month and year. month
	 * value could be either text string (Mai, May, depeneds on the locale) or
	 * number string and other values should be number string
	 * 
	 * @param second Second of the hour (eg. 0-59)
	 * @param minutes Minutes of hour (eg. 0-59)
	 * @param hours Hours of day (eg. 0-23)
	 * @param day Day of month (eg. '5', '06', '23', etc).
	 * @param month Month of year (eg. '1', '03', 'Jan', 'May', 'Mai', etc).
	 * @param year Year part of the date (eg. '1972', '2003','93','34' etc)
	 * @return date object from the given string
	 * @throws DateFormatterException if the date creation fails or date is
	 *         invalid
	 */
	public Date parseDate(String second, String minutes, String hours, String day, String month,
			String year) throws DateFormatterException
	{
		StringBuffer pattern = new StringBuffer(IConstants.DEFAULT_STRING_BUFFER_SIZE);
		StringBuffer dateString = new StringBuffer(IConstants.DEFAULT_STRING_BUFFER_SIZE);
		boolean parseOk = false;

		if ((day != null) && (day.length() > 0) && (month != null) && (month.length() > 0)
				&& (year != null) && (year.length() > 0)) {
			pattern.append("dd");
			dateString.append(day);

			if (month.length() > 2) {
				pattern.append(DELIMITER).append("MMM");
			} else {
				pattern.append(DELIMITER).append("MM");
			}

			dateString.append(DELIMITER).append(month);

			if (year.length() == 2) {
				pattern.append(DELIMITER).append("yy");
				parseOk = true;
			} else if (year.length() == 4) {
				pattern.append(DELIMITER).append("yyyy");
				parseOk = true;
			}

			dateString.append(DELIMITER).append(year);

			if (parseOk == false) {
				throw new DateFormatterException(
						"Forming the date is failed : year format should be yy or yyyy");
			}
		}

		if (parseOk == false) {
			throw new DateFormatterException(
					"day, month,year should contain not null or non-empty value");
		}

		if (hours != null) {
			pattern.append(DELIMITER).append("H");
			dateString.append(DELIMITER).append(hours);
		}

		if (minutes != null) {
			pattern.append(DELIMITER).append("m");
			dateString.append(DELIMITER).append(minutes);
		}

		if (second != null) {
			pattern.append(DELIMITER).append("s");
			dateString.append(DELIMITER).append(second);
		}

		return getDate(dateString.toString(), pattern.toString());
	}

	/**
	 * Forming the date from the given date as string and the pattern
	 * 
	 * @param dateString date as string which is used to form the date
	 * @param pattern the pattern to form the date
	 * @return date object from the given string
	 * @throws DateFormatterException if the date formation fails or date is
	 *         invalid
	 * @throws InvalidArgumentException if the dateString or pattern is null
	 */
	public Date getDate(String dateString, String pattern) throws DateFormatterException,
			InvalidArgumentException
	{
		if ((dateString == null) || (dateString.length() == 0) || (pattern == null)
				|| (pattern.length() == 0)) {
			throw new InvalidArgumentException("the given string is null");
		}

		Date date = null;

		if (pattern.equals("SHORT")) {
			return parseDate(dateString, SHORT_STYLE);
		}

		if (pattern.equals("MEDIUM")) {
			return parseDate(dateString, MEDIUM_STYLE);
		}

		if (pattern.equals("LONG")) {
			return parseDate(dateString, LONG_STYLE);
		}

		SimpleDateFormat simpledateformat = new SimpleDateFormat(pattern, this.locale);

		simpledateformat.setLenient(true);

		try {
			date = simpledateformat.parse(dateString);
		} catch (ParseException parseexception) {
			throw new DateFormatterException("Forming the date '" + dateString
					+ "' has failed : format used: " + pattern, parseexception);
		}

		return date;
	}

	/**
	 * Forming the date from the given string. The date string will be checked
	 * using the locale and with the default style
	 * 
	 * @param dateString string representation of date
	 * @return date object from the string
	 * @throws DateFormatterException if date object creation fails or date is
	 *         invalid
	 * @throws InvalidArgumentException if the dateString is null
	 */
	public Date parseDate(String dateString) throws DateFormatterException, InvalidArgumentException
	{
		return parseDate(dateString, DEFAULT_STYLE);
	}

	/**
	 * Forming the date from the given string. The datestring will be checked
	 * using the locale
	 * 
	 * @param dateString string representation of date
	 * @param style the given formatting style
	 * @return The new created date object
	 * @throws DateFormatterException if date object creation fails or date is
	 *         invalid
	 * @throws InvalidArgumentException if the dateString is null
	 */
	public Date parseDate(String dateString, int style) throws DateFormatterException,
			InvalidArgumentException
	{
		if ((dateString == null) || (dateString.length() == 0)) {
			throw new InvalidArgumentException("the given string is null");
		}

		DateFormat dateformat = DateFormat.getDateInstance(style, this.locale);

		dateformat.setLenient(false);

		Date date = null;

		try {
			date = dateformat.parse(dateString);
		} catch (ParseException parseexception) {
			throw new DateFormatterException("Forming the date is failed: ", parseexception);
		}

		return date;
	}

	/**
	 * Return a string containing the formatted date from the given date. Date
	 * will be converted to string using the locale and with the default style
	 * 
	 * @param date the date which need to be formatted
	 * @param pattern pattern to format the date object
	 * @return string containing the formatted date from the given date
	 * @throws DateFormatterException if date object creation fails or date is
	 *         invalid
	 * @throws InvalidArgumentException if the date object is null
	 */
	public String format(Date date, String pattern) throws DateFormatterException,
			InvalidArgumentException
	{
		if ((date == null) || (pattern == null) || (pattern.length() == 0)) {
			throw new InvalidArgumentException("date or pattern is null");
		}

		if (pattern.startsWith("DATE_") || pattern.startsWith("TIME_")) {
			Map<Object, Object> token = getToken(pattern);
			String dateStyle = (String) token.get("DATE");
			String timeStyle = (String) token.get("TIME");

			if (dateStyle == null) {
				Integer intStyle = styleMap.get(timeStyle);

				if (intStyle != null) {
					return formatTime(date, intStyle.intValue());
				}
			} else if (timeStyle == null) {
				Integer intStyle = styleMap.get(dateStyle);

				if (intStyle != null) {
					return formatDate(date, intStyle.intValue());
				}
			} else {
				Integer intTimeStyle = styleMap.get(timeStyle);
				Integer intDateStyle = styleMap.get(dateStyle);

				if ((intTimeStyle != null) && (intDateStyle != null)) {
					return formatDateTime(date, intDateStyle.intValue(), intTimeStyle.intValue());
				}
			}

			throw new DateFormatterException("Pattern is wrong : " + pattern);
		}

		SimpleDateFormat simpleDateformat = new SimpleDateFormat(pattern, this.locale);

		return simpleDateformat.format(date);
	}

	/**
	 * Return a string containing the formatted date from the given date. Date
	 * will be converted to string using the locale and with the default style
	 * 
	 * @param date the date which need to cbe formatted
	 * @param pattern pattern to format the date object
	 * @return string containing the formatted date from the given date
	 * @throws InvalidArgumentException if the date object is null
	 */
	public String formatDate(Date date, String pattern) throws InvalidArgumentException
	{
		if ((date == null) || (pattern == null) || (pattern.length() == 0)) {
			throw new InvalidArgumentException("date or pattern is null");
		}

		SimpleDateFormat simpleDateformat = new SimpleDateFormat(pattern, this.locale);

		return simpleDateformat.format(date);
	}

	/**
	 * Return a string containing the formatted date from the given date. Date
	 * will be converted to string using the locale and with the default style
	 * 
	 * @param date the date which need to be formatted
	 * @return string containing the formatted date from the given date
	 * @throws InvalidArgumentException if the date object is null
	 */
	public String formatDate(Date date) throws InvalidArgumentException
	{
		return formatDate(date, DEFAULT_STYLE);
	}

	/**
	 * @param millis
	 * @return String representation of the millis as Date
	 */
	public static String formatDate(long millis)
	{
		return new Date(millis).toString();
	}

	/**
	 * Return a string containing the formatted date from the given date. Date
	 * will be converted to string using the locale.
	 * 
	 * @param date the date which need to cbe formatted
	 * @param style the given formatting style
	 * @return string containing the formatted date from the given date
	 * @throws InvalidArgumentException if the date object is null
	 */
	public String formatDate(Date date, int style) throws InvalidArgumentException
	{
		if (date == null) {
			throw new InvalidArgumentException("date is null");
		}

		DateFormat dateformat = DateFormat.getDateInstance(style, this.locale);

		return dateformat.format(date);
	}

	/**
	 * Return a string containing the formatted time from the given date. Date
	 * will be converted to string using the locale and with the default style
	 * 
	 * @param date the date which need to be formatted
	 * @return string containing the formatted date from the given date
	 * @throws InvalidArgumentException if the date object is null
	 */
	public String formatTime(Date date) throws InvalidArgumentException
	{
		return formatTime(date, DEFAULT_STYLE);
	}

	/**
	 * Return a string containing the formatted time from the given date. Date
	 * will be converted to string using the locale.
	 * 
	 * @param date the date which need to cbe formatted
	 * @param style the given formatting style
	 * @return string containing the formatted date from the given date
	 * @throws InvalidArgumentException if the date object is null
	 */
	public String formatTime(Date date, int style) throws InvalidArgumentException
	{
		if (date == null) {
			throw new InvalidArgumentException("date is null");
		}

		DateFormat dateformat = DateFormat.getTimeInstance(style, this.locale);

		return dateformat.format(date);
	}

	/**
	 * Return a string containing the formatted date and time from the given
	 * date. Date will be converted to string using the locale.
	 * 
	 * @param date the date which need to cbe formatted
	 * @return string containing the formatted date from the given date
	 * @throws InvalidArgumentException if the date object is null
	 */
	public String formatDateTime(Date date) throws InvalidArgumentException
	{
		return formatDateTime(date, DEFAULT_STYLE, DEFAULT_STYLE);
	}

	/**
	 * Return a string containing the formatted date and time from the given
	 * date. Date will be converted to string using the locale and with the
	 * default style.
	 * 
	 * @param date the date which need to cbe formatted
	 * @param dateStyle the style for date
	 * @param timeStyle the style for time
	 * @return string containing the formatted date from the given date
	 * @throws InvalidArgumentException if the date object is null
	 */
	public String formatDateTime(Date date, int dateStyle, int timeStyle)
			throws InvalidArgumentException
	{
		if (date == null) {
			throw new InvalidArgumentException("date is null");
		}

		DateFormat dateformat = DateFormat.getDateTimeInstance(dateStyle, timeStyle, this.locale);

		return dateformat.format(date);
	}

	/**
	 * returns an instance of DateFormatter class with the specific locale
	 * 
	 * @param locale locale to create the instance of the DateFromatter
	 * @return DateFormatter with specif locale
	 */
	public static DateFormatter getDateFormatterInstance(Locale locale)
	{
		DateFormatter formatter = null;

		synchronized (formatterHash) {
			formatter = formatterHash.get(locale);
		}

		if (formatter != null) {
			return formatter;
		} else {
			formatter = new DateFormatter(locale);

			synchronized (formatterHash) {
				formatterHash.put(locale, formatter);
			}

			return formatter;
		}
	}

	/**
	 * return token from the given string
	 * 
	 * @param pattern string for tokenized
	 * @return a map containing all the token
	 */
	private Map<Object, Object> getToken(String pattern)
	{
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		StringTokenizer stringTokenizer = new StringTokenizer(pattern, TOKEN_DELIMITER);

		while (stringTokenizer.hasMoreTokens()) {
			hashMap.put(stringTokenizer.nextElement(), stringTokenizer.nextElement());
		}

		return hashMap;
	}

	/**
	 * @return thread-safe common Formatter for date formatting
	 */
	public static SimpleDateFormat getSimpleFormatter()
	{
		// SimpleDateFormat is not thread-safe, so we need to return separate
		// instance for each call
		return (SimpleDateFormat) formatter.clone(); // NOPMD by pzi on 2/19/09
		// 5:10 PM
	}

	/**
	 * to test the functions
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args)
	{
		DateFormatter dateFormatter = new DateFormatter();
		String dateString = null;

		try {
			dateString = dateFormatter.format(new Date(), "null");
		} catch (DateFormatterException fx) {
			System.out.println("Exception : " + fx);
		}

		System.out.println("The date is  : " + dateString);
	}
}
