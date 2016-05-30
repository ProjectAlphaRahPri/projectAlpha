/*
 * Copyright (C) 2005 Whitestein Technologies AG, Poststrasse 22, CH-6300 Zug, Switzerland.
 * All rights reserved. The use of this file in source or binary form requires a written license from Whitestein Technologies AG.
 *
 */
package service.util;

import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import service.exceptions.InvalidArgumentException;
import shippingCore.IConstants;
import shippingCore.calculations.time.TimeWindow;


/**
 * Provides static methods for handling of time, date, datetimes
 * <code>Date</code> objects.
 * 
 * @author Last modified by $Author: $
 * @version $Revision: $
 */
public class DateTimeUtils
{
	private static final long TIME_PER_DAY = 86400000;

	private static final long TIME_PER_HOUR = 60 * 60 * 1000;

	private static final TimeZone UTC = new SimpleTimeZone(0, "utc");

	private static final TimeZone CET = TimeZone.getTimeZone("CET");

	private static final Calendar CALENDAR = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

	/**
	 * Merges a date (in UTC) and a time (in UTC) a returns a new date object.
	 * 
	 * @param dateOnly a Date instance with date part only
	 * @param timeOnly a Date instance with time part only
	 * @return a merged Date object
	 */
	public static Date mergeUtcDateTime(Date dateOnly, Date timeOnly)
	{
		if (dateOnly == null) {
			return null;
		}
		Calendar cal = createUTCCalendar(dateOnly);
		if ((cal.get(Calendar.HOUR_OF_DAY) != 0) || (cal.get(Calendar.MINUTE) != 0)
				|| (cal.get(Calendar.SECOND) != 0) || (cal.get(Calendar.MILLISECOND) != 0)) {
			throw new IllegalArgumentException(
					"The supplied date Date is either not constructed in UTC or contains non 0 time values");
		}

		if (timeOnly == null) {
			setHms(cal, 0, 0, 0);
		} else {
			Calendar tc = createUTCCalendar(timeOnly);
			Calendar epoch = createUTCCalendar(0);
			if ((tc.get(Calendar.YEAR) != epoch.get(Calendar.YEAR))
					|| (tc.get(Calendar.MONTH) != epoch.get(Calendar.MONTH))
					|| (tc.get(Calendar.DAY_OF_MONTH) != epoch.get(Calendar.DAY_OF_MONTH))) {
				throw new IllegalArgumentException(
						"The supplied time Date is either not constructed in UTC or contains non 0 date values");
			}

			setHms(cal, tc.get(Calendar.HOUR_OF_DAY), tc.get(Calendar.MINUTE), tc.get(Calendar.SECOND));
		}

		return cal.getTime();
	}

	/**
	 * Merges a date and a time and returns a new date object. BEWARE ! The date
	 * objects have to be created with the same time zone. The time zone has to
	 * be absolute (e.g. "CET" or "CEST", i.e. it must ignore automatic DST
	 * change. The DST considering TZ like "Europe/Bratislava" is not allowed!)
	 * 
	 * @param dateOnly a Date instance with date part only
	 * @param timeOnly a Date instance with time part only
	 * @return a merged Date object
	 */
	public static Date mergeDateTime(Date dateOnly, Date timeOnly)
	{
		long result;

		if (dateOnly == null) {
			return null;
		}

		result = getClosestMidnightTime(dateOnly);

		if (timeOnly == null) {
			return SystemTime.asRealDate(result);
		} else {
			result = result + (timeOnly.getTime() % TIME_PER_DAY);
			return SystemTime.asRealDate(result);
		}
	}

	/**
	 * Split the supplied date into two Date objects - one with date only, the
	 * other with time only
	 * @param dateAndTime
	 * @return an 2 element array with 1st element date only and the 2nd element
	 *         time only
	 */
	public static Date[] splitDateTime(Date dateAndTime)
	{
		Calendar date = createUTCCalendar(dateAndTime);
		Calendar time = createUTCCalendar(0);
		setHms(time, date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE), date
				.get(Calendar.SECOND));
		setHms(date, 0, 0, 0);
		return new Date[] { date.getTime(), time.getTime() };
	}

	/**
	 * This is used to normalize the date to be able to detect a date change. The
	 * hours, minutes, seconds and milliseconds are just set to 0.
	 * 
	 * @param date The date which should be normalized.
	 * @return The normalized date.
	 */
	public static long normalizeDate(long date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date);
		return splitDateTime(calendar.getTime())[0].getTime();
	}

	/**
	 * Number of days between the dateOne and dateTwo. The difference is
	 * positive, if dateOne is after dateTwo, negative otherwise.
	 * @param dateOne
	 * @param dateTwo
	 * @return number of days between the dateTwo and dateOne
	 */
	public static long daysBetween(long dateOne, long dateTwo)
	{
		return (normalizeDate(dateOne) - normalizeDate(dateTwo)) / IConstants.DAY;
	}

	/**
	 * Makes a minimum of the two dates and returns the smaller of the two dates.
	 * If one of the two dates is null, the other is returned.
	 * 
	 * @param d1 a date may be null
	 * @param d2 a date may be null
	 * @return the smaller of the two dates
	 */
	public static Date minDate(Date d1, Date d2)
	{
		if (d1 == null) {
			return d2;
		}

		if (d2 == null) {
			return d1;
		}

		if (d1.before(d2)) {
			return d1;
		}
		return d2;
	}

	/**
	 * Makes a minimum of the three dates and returns the earliest of the three
	 * dates. If any of dates is null, it is ignored and other two are compared.
	 * 
	 * @param d1 a date may be null
	 * @param d2 a date may be null
	 * @param d3 a date may be null
	 * @return the greater of the two dates
	 */
	public static Date minDate(Date d1, Date d2, Date d3)
	{
		return minDate(minDate(d1, d2), d3);
	}

	/**
	 * Makes a maximum of the two dates and returns the greater of the two dates.
	 * If one of the two dates is null, the other is returned.
	 * 
	 * @param d1 a date may be null
	 * @param d2 a date may be null
	 * @return the greater of the two dates
	 */
	public static Date maxDate(Date d1, Date d2)
	{
		if (d1 == null) {
			return d2;
		}

		if (d2 == null) {
			return d1;
		}

		if (d1.before(d2)) {
			return d2;
		}
		return d1;
	}

	/**
	 * Makes a maximum of the three dates and returns the greater of the three
	 * dates. If any of dates is null, it is ignored and other two are compared.
	 * 
	 * @param d1 a date may be null
	 * @param d2 a date may be null
	 * @param d3 a date may be null
	 * @return the greater of the two dates
	 */
	public static Date maxDate(Date d1, Date d2, Date d3)
	{
		return maxDate(maxDate(d1, d2), d3);
	}

	/**
	 * @param year
	 * @param month 1-12
	 * @param day 1-31
	 * @param hour
	 * @param minute
	 * @return <code>Date</code> in UTC format for the given parameters.
	 * @throws InvalidArgumentException 
	 */
	public static Date createUTCDate(int year, int month, int day, int hour, int minute)
	{
		if (month == 0) {
			throw new InvalidArgumentException("Month cannot be zero, use values 1-12");
		}
		if (day == 0) {
			throw new InvalidArgumentException("Day cannot be zero, use values 1-31");
		}
		synchronized (CALENDAR) {
			CALENDAR.set(year, month - 1, day, hour, minute, 0);
			CALENDAR.set(Calendar.MILLISECOND, 0);
			return CALENDAR.getTime();
		}
	}

	/**
	 * @param syear
	 * @param smonth 1-12
	 * @param sday 1-31
	 * @param shour
	 * @param sminute
	 * @param eyear
	 * @param emonth 1-12
	 * @param eday 1-31
	 * @param ehour
	 * @param eminute
	 * @return <code>TimeWindow</code> from the date information
	 * @throws InvalidArgumentException 
	 * @throws IllegalArgumentException
	 *         {@link TimeWindow#TimeWindow(long, boolean, long, boolean)}
	 */
	public static TimeWindow createTimeWindow(int syear, int smonth, int sday, int shour,
			int sminute, int eyear, int emonth, int eday, int ehour, int eminute) 
	{

		return new TimeWindow(createUTCDate(syear, smonth, sday, shour, sminute), createUTCDate(
				eyear, emonth, eday, ehour, eminute));
	}

	/**
	 * Answer true when the supplied date represents a date in UTC with no time
	 * data given (i.e. 0:00.000)
	 * @param date
	 * @return true when the supplied date represents a date in UTC with no time
	 *         data given
	 */
	public static boolean isPlainUtcDate(Date date)
	{
		Calendar cal = createUTCCalendar(date);
		return ((cal.get(Calendar.HOUR_OF_DAY) == 0) && (cal.get(Calendar.MINUTE) == 0)
				&& (cal.get(Calendar.SECOND) == 0) && (cal.get(Calendar.MILLISECOND) == 0));
	}

	/**
	 * Answer true when the supplied date represents a time in UTC with no date
	 * data given (i.e. 1.1.1970)
	 * @param date
	 * @return true when the supplied date represents a time in UTC with no date
	 *         data given
	 */
	public static boolean isPlainUtcTime(Date date)
	{
		Calendar tc = createUTCCalendar(date);
		Calendar epoch = createUTCCalendar(0);
		return ((tc.get(Calendar.YEAR) == epoch.get(Calendar.YEAR))
				&& (tc.get(Calendar.MONTH) == epoch.get(Calendar.MONTH)) && (tc
				.get(Calendar.DAY_OF_MONTH) == epoch.get(Calendar.DAY_OF_MONTH)));
	}

	/**
	 * Adds number of days to the given date.
	 * 
	 * @param date the date to be increased
	 * @param days the number of days to add
	 * @return a new date object or null if the given date is null
	 */
	public static Date addDaysToDate(Date date, int days)
	{
		if (date == null) {
			return null;
		}

		Calendar cal = createUTCCalendar(date);
		cal.add(Calendar.DAY_OF_MONTH, days);
		return cal.getTime();
	}

	/**
	 * Adds number of hours to the given time.
	 * 
	 * @param time the date to be increased
	 * @param hours the number of days to add
	 * @return a new date object or null if the given date is null
	 */
	public static long addHoursToTime(long time, int hours)
	{
		return time + hours * TIME_PER_HOUR;
	}

	private static void setHms(Calendar cal, int hr, int min, int sec)
	{
		cal.set(Calendar.HOUR_OF_DAY, hr);
		cal.set(Calendar.MINUTE, min);
		cal.set(Calendar.SECOND, sec);
		cal.set(Calendar.MILLISECOND, 0);
	}

	/**
	 * Create a Calendar in UTC TZ
	 * @param d
	 * @return a Calendar instance
	 */
	public static Calendar createUTCCalendar(Date d)
	{
		Calendar cal = Calendar.getInstance(UTC);
		cal.setTime(d);
		return cal;
	}

	/**
	 * Creates a Calendar in CET/CEST TZ.
	 * @param d
	 * @return a Calendar instance
	 */
	public static Calendar createCETCalendar(Date d)
	{
		Calendar cal = Calendar.getInstance(CET);
		cal.setTime(d);
		return cal;
	}

	/**
	 * Create a Calendar in UTC TZ
	 * @param sinceEpoch
	 * @return a Calendar instance
	 */
	public static Calendar createUTCCalendar(long sinceEpoch)
	{
		Calendar cal = Calendar.getInstance(UTC);
		cal.setTimeInMillis(sinceEpoch);
		return cal;
	}

	private static long getClosestMidnightTime(Date d)
	{
		long sinceEpoch = d.getTime();
		long timeFragment = d.getTime() % TIME_PER_DAY;
		if (timeFragment > (TIME_PER_DAY / 2)) {
			return ((sinceEpoch / TIME_PER_DAY) + 1) * TIME_PER_DAY;
		} else {
			return (sinceEpoch / TIME_PER_DAY) * TIME_PER_DAY;
		}
	}

	/**
	 * @param d
	 * @return long time equivalent of most recent midnight before (or at)
	 *         supplied date parameter
	 */
	public static Date getLastMidnightUtcTime(Date d)
	{
		return new Date(getLastMidnightUtcTime(d.getTime()));
	}

	/**
	 * @param time
	 * @return long time equivalent of most recent midnight before (or at)
	 *         supplied time parameter
	 */
	public static long getLastMidnightUtcTime(long time)
	{
		return (time / TIME_PER_DAY) * TIME_PER_DAY;
	}

	/**
	 * @param utcTime
	 * @return time window from Monday to Sunday which includes the current
	 *         utcTime
	 */
	public static TimeWindow fromMondayToSunday(long utcTime)
	{
		synchronized (CALENDAR) {
			Calendar cal = CALENDAR; // Calendar.getInstance(UTC);
			cal.setTimeInMillis(utcTime);
			int dayOfTheWeek = cal.get(Calendar.DAY_OF_WEEK);
			int toNextMonday = (7 + Calendar.MONDAY - dayOfTheWeek) % 7;
			cal.add(Calendar.DAY_OF_YEAR, toNextMonday == 0 ? 7 : toNextMonday);
			setHms(cal, 0, 0, 0); // set midnight
			long weekEnd = cal.getTimeInMillis();
			cal.add(Calendar.DAY_OF_WEEK, -7);
			long weekStart = cal.getTimeInMillis();
			TimeWindow time = new TimeWindow(weekStart, true, weekEnd, false);
			// assert time.isTimeWithin(utcTime) :
			// "TimeWindow not correctly calculated: " + time + " utcTime:" +
			// utcTime;
			return time;
		}
	}

	/**
	 * Answer the time window in which the DST applies in given year/timezone
	 * @param year
	 * @param tz
	 * @return the time window in which the DST applies in given year/timezone
	 *         null if no DST is used
	 */
	public static TimeWindow getDaylightSavingPeriod(int year, TimeZone tz)
	{
		// Check the start
		Calendar cal = Calendar.getInstance(tz);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, 2);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		cal = getDstChangeDay(cal, tz);
		if (cal == null) {
			return null;
		}

		Date begin = cal.getTime();

		// Check the end
		cal = Calendar.getInstance(tz);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, 9);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		cal = getDstChangeDay(cal, tz);
		if (cal == null) {
			return null;
		}

		Date end = cal.getTime();

		return new TimeWindow(begin, end);
	}

	/**
	 * Find the date after cal on which DST changes its state (starts or ends)
	 * @param begin
	 * @param tz
	 * @return Date when DST starts or ends or null if not used
	 */
	private static Calendar getDstChangeDay(final Calendar begin, TimeZone tz)
	{
		Calendar cal = (Calendar) begin.clone();
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.HOUR, 0);
		boolean status = tz.inDaylightTime(cal.getTime());

		// Check max 120 days, then assume DST is not appplied
		for (int i = 0; i < 120; i++) {
			cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + 1);
			if (status != tz.inDaylightTime(cal.getTime())) {
				// Revert back by 1 day
				cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) - 1);
				// Now find the time (iterate by 10 minutes)
				for (int j = 0; j < 6 * 24; i++) {
					cal.add(Calendar.MINUTE, 10);
					if (status != tz.inDaylightTime(cal.getTime())) {
						// Now find the time
						return cal;
					}
				}
			}
		}

		return null;
	}

}
