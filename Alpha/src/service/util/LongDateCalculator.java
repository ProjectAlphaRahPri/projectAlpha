package service.util;

import java.util.Calendar;
import java.util.TimeZone;

import service.properties.IConstants;
import shippingCore.calculations.time.TimeWindow;

/**
 * Class to calculate with dates (as longs due to performance reasons). (There
 * is an implicit correction for the time difference to the default locale).
 */
public class LongDateCalculator {
	private static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getDefault();

	/** represents the milliseconds in day till 12.30 */
	protected static final int TIME_1230 = (12 * IConstants.HOUR)
			+ (30 * IConstants.MINUTE);

	/** represents the milliseconds in day till 13.00 */
	protected static final int TIME_1300 = 13 * IConstants.HOUR;

	/**
	 * represents the time window of lunch break (starting at {@link #TIME_1230}
	 * and ending at {@link #TIME_1300}
	 */
	private static final TimeWindow LUNCH_BREAK = new TimeWindow(TIME_1230,
			TIME_1300);

	/** time window of the summer time in 21st century */
	private final static TimeWindow[] DST_TIME_PERIODS = new TimeWindow[130];

	// used for calculating the time difference from the used locale to the
	// default loacale
	static {
		TimeZone timeZone = DEFAULT_TIME_ZONE;

		for (int i = 0; i < DST_TIME_PERIODS.length; i++) {
			DST_TIME_PERIODS[i] = DateTimeUtils
					.getDaylightSavingPeriod(1970 + i, timeZone);
		}
	}

	/**
	 * Checks if the given time is within lunch break. Lunch break starts at
	 * {@link #TIME_1230} and ends at {@link #TIME_1300}
	 * 
	 * @param time
	 *            number that represents date and time in milliseconds
	 * @param tz
	 * @return true if the given time is within lunch break, else false
	 */
	public static boolean isLunchBreak(final long time, final TimeZone tz) {
		int millisInDay = LongDateCalculator.getMillisInDay(time, tz);

		if ((millisInDay >= TIME_1230) && (millisInDay <= TIME_1300)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns the milliseconds within lunch break
	 * 
	 * @param start
	 *            number that represents start date and time in milliseconds
	 * @param end
	 *            number that represents end date and time (later than start) in
	 *            milliseconds
	 * @return the milliseconds within lunch break
	 */
	public static long getMillisWithinLunchBreak(final long start,
			final long end) {
		long startDay = LongDateCalculator.getPureDays(start);
		long endDay = LongDateCalculator.getPureDays(end);

		// if the start is day(s) before end, then the lunch break is not
		// important
		if (startDay < endDay) {
			return 0;
		}

		return LUNCH_BREAK.getOverlap(LongDateCalculator.getMillisInDay(start),
				LongDateCalculator.getMillisInDay(end));
	}

	/**
	 * Returns the involved days within the given times. Every started day is
	 * counted.
	 * 
	 * @param time
	 *            number that represents date and time in milliseconds
	 * @param time2
	 *            number that represents another date and time in milliseconds
	 * @return the involved days within the given times. Every started day is
	 *         counted.
	 */
	public static int getInvolvedDays(final long time, final long time2) {
		int delta = (int) Math.abs(LongDateCalculator.getPureDays(time)
				- LongDateCalculator.getPureDays(time2));

		return delta + 1;
	}

	/**
	 * Returns the pure days (amount) represented by the given time.
	 * 
	 * @param time
	 *            number that represents date and time in milliseconds
	 * @return the number of pure days represented by the given time.
	 */
	private static long getPureDays(final long time) {
		return (time + getTimeOffset(time, DEFAULT_TIME_ZONE)) / IConstants.DAY;
	}

	/**
	 * Returns the milliseconds within the last day of the given time
	 * 
	 * @param time
	 *            number that represents date and time in milliseconds
	 * @param tz
	 * @return the milliseconds within the last day of the given time
	 */
	public static int getMillisInDay(final long time, final TimeZone tz) {
		int millisInDay = (int) ((time + getTimeOffset(time, tz))
				% IConstants.DAY);

		if (millisInDay < 0) {
			millisInDay += IConstants.DAY;
		}

		return millisInDay;
	}

	/**
	 * Returns the milliseconds within the last day of the given time
	 * 
	 * @param time
	 *            number that represents date and time in milliseconds
	 * @return the milliseconds within the last day of the given time
	 */
	public static int getMillisInDay(final long time) {
		return getMillisInDay(time, DEFAULT_TIME_ZONE);
	}

	/**
	 * Returns the time in milliseconds the next day starts. E.g. the given time
	 * is 2004-05-23 11:23 (in milliseconds) than this method gives back
	 * 2004-05-24 00:00 (in milliseconds).
	 * 
	 * @param time
	 *            number that represents date and time in milliseconds
	 * @return the time in milliseconds the next day starts
	 */
	public static long getNextDayStart(final long time) {
		long temp = time + IConstants.DAY;

		// in seldom cases a days length is not 24h (e.g. summer or winter time
		// change)
		// if the day is only 23h long (summertime change): fixed here
		temp -= LongDateCalculator.getMillisInDay(temp);

		if (LongDateCalculator.getMillisInDay(temp) != 0) {

			// the day is 25h long (winter time change --> lets adapt this
			temp = (time + 25 * IConstants.HOUR);
			temp -= LongDateCalculator.getMillisInDay(temp);
		}

		return temp;
	}

	/**
	 * Returns the time in milliseconds the day starts. E.g. the given time is
	 * 2004-05-23 11:23 (in milliseconds) than this method gives back 2004-05-22
	 * 00:00 (in milliseconds).
	 * 
	 * @param time
	 *            number that represents date and time in milliseconds
	 * @return the time in milliseconds the next day starts
	 */
	public static long getDayStart(final long time) {
		long temp = time;

		temp -= LongDateCalculator.getMillisInDay(temp);

		// in seldom cases a days length is not 24h (e.g. summer or winter time
		// change)
		if (LongDateCalculator.getMillisInDay(temp) != 0) {

			if (LongDateCalculator.getMillisInDay(temp) < 12
					* IConstants.HOUR) {

				// the day is 23h long (summertime change --> lets adapt this
				temp = (temp - 1 * IConstants.HOUR);
			} else if (LongDateCalculator.getMillisInDay(temp) >= 12
					* IConstants.HOUR) {
				// the day is only 25h long (wintertime change): fixed here
				temp = (temp + 1 * IConstants.HOUR);

			}
		}

		return temp;
	}

	/**
	 * Returns the weekday of the passed time as specified in java.util.Calendar
	 * 
	 * @param time
	 *            number that represents date and time in milliseconds
	 * @return the weekday of the passed time as specified in
	 *         {@link java.util.Calendar#DAY_OF_WEEK}
	 */
	public static int getWeekday(final long time) {
		return getWeekday(time, DEFAULT_TIME_ZONE);
	}

	/**
	 * Returns the weekday of the passed time as specified in java.util.Calendar
	 * 
	 * @param time
	 *            number that represents date and time in milliseconds
	 * @param tz
	 * @return the weekday of the passed time as specified in
	 *         {@link java.util.Calendar#DAY_OF_WEEK}
	 */
	public static int getWeekday(final long time, final TimeZone tz) {
		// long day = time / ONE_DAY;
		long day = (time + getTimeOffset(time, tz)) / IConstants.DAY;

		if (time < 0) {
			day--;
		}

		// int dayOfWeek = (int)((day + 1) % 7);
		// idea jbogenschuetz -> weekday of 01.01.1970 was a thursday
		// -> sunday is the first day of the week with constant int value 1
		// thursday has the constant int value 5 : difference between 5 and 1 is
		// 4
		// -> +4 for the weekday
		int dayOfWeek = (int) ((day + 4) % 7);

		// +1 because first day (sunday) starts with constant int value 1
		// instead
		// of 0
		return dayOfWeek >= 0 ? dayOfWeek + 1 : (dayOfWeek + 7) % 7 + 1;
	}

	/**
	 * Returns the timeOffset in milliseconds (e.g. summertime)
	 * 
	 * @param time
	 *            for which the offset is needed
	 * @param tz
	 *            timezone in which the date is created or null if it's UTC date
	 * @return the timeOffset in milliseconds (e.g. summertime)
	 */
	public static long getTimeOffset(final long time, final TimeZone tz) {
		long offset = (tz != null) ? tz.getRawOffset() : 0;

		int year = (int) (time / (365L * IConstants.DAY));
		if (time < 0) {
			year--;
		}
		TimeWindow dstPeriod;
		if (year >= 0 && year < DST_TIME_PERIODS.length) {
			dstPeriod = DST_TIME_PERIODS[year];
		} else {
			dstPeriod = DateTimeUtils.getDaylightSavingPeriod(1970 + year,
					DEFAULT_TIME_ZONE);
		}

		if ((dstPeriod != null) && (dstPeriod.isTimeWithin(time))) {
			// its summertime
			offset += IConstants.HOUR;
		}

		return offset;
	}

	/**
	 * Returns the milliseconds of an timeintervall within sundays
	 * 
	 * @param startTime
	 *            time in milliseconds representing the begin of the interval
	 * @param endTime
	 *            time in milliseconds representing the end of the interval
	 * @return the milliseconds within one or more Sundays
	 */
	public static int getMillisWithinSunday(final long startTime,
			final long endTime) {
		if (startTime >= endTime) {
			return 0;
		}

		int involvedDays = getInvolvedDays(startTime, endTime);

		// Week of day: Sunday = 1, Monday = 2, ... Saturday = 7
		int startWeekDay = LongDateCalculator.getWeekday(startTime);
		int currentWeekday = startWeekDay % 7; // <-- Saturday = 7 becomes 0

		int millisInSunday = 0;

		// is the start day at Sunday
		if (currentWeekday == Calendar.SUNDAY) {
			millisInSunday = IConstants.DAY - getMillisInDay(startTime);
		}

		// only the days between start and end are checked here
		for (int i = 2; i < involvedDays; i++) {
			currentWeekday = (currentWeekday + 1) % 7;

			if (currentWeekday == Calendar.SUNDAY) {
				millisInSunday += IConstants.DAY;
			}
		}

		int endWeekDay = LongDateCalculator.getWeekday(endTime);
		currentWeekday = endWeekDay % 7; // <-- Saturday = 7 becomes 0

		// is the endday day at Sunday
		if (currentWeekday == Calendar.SUNDAY) {
			if (involvedDays > 1) {
				millisInSunday += getMillisInDay(endTime);
			} else {
				millisInSunday = millisInSunday
						- (IConstants.DAY - getMillisInDay(endTime));
			}
		}

		return millisInSunday;
	}

	/**
	 * Returns the milliseconds of an time interval within weekends
	 * 
	 * @param startTime
	 *            time in milliseconds representing the begin of the interval
	 * @param endTime
	 *            time in milliseconds representing the end of the interval
	 * @return the milliseconds within one or more weekends
	 */
	public static int getMillisWithinWeekend(final long startTime,
			final long endTime) {
		if (startTime >= endTime) {
			return 0;
		}

		int involvedDays = getInvolvedDays(startTime, endTime);

		// Week of day: Sunday = 1, Monday = 2, ... Saturday = 7
		int startWeekDay = LongDateCalculator.getWeekday(startTime);
		int currentWeekday = startWeekDay % 7; // <-- Saturday = 7 becomes 0

		int millisInWeekend = 0;

		// is the start day at Sunday or Saturday
		if (currentWeekday <= Calendar.SUNDAY) {
			millisInWeekend = IConstants.DAY - getMillisInDay(startTime);
		}

		// only the days between start and end are checked here
		for (int i = 2; i < involvedDays; i++) {
			currentWeekday = (currentWeekday + 1) % 7;

			// is the start day at Sunday or Saturday
			if (currentWeekday <= Calendar.SUNDAY) {
				millisInWeekend += IConstants.DAY;
			}
		}

		int endWeekDay = LongDateCalculator.getWeekday(endTime);
		currentWeekday = endWeekDay % 7; // <-- Saturday = 7 becomes 0

		// is the endday day at Sunday or Saturday
		if (currentWeekday <= Calendar.SUNDAY) {
			if (involvedDays > 1) {
				millisInWeekend += getMillisInDay(endTime);
			} else {
				millisInWeekend = millisInWeekend
						- (IConstants.DAY - getMillisInDay(endTime));
			}
		}

		return millisInWeekend;
	}

	/**
	 * Calculates the drive forbid time (german "Sonntagsfahrverbot") interval
	 * in milliseconds.
	 * 
	 * This interval is from Sunday 0:00a.m. till Sunday 10:00p.m.
	 * 
	 * @param lastDeparture
	 *            start time of a leg
	 * @param arrivalTime
	 *            end time of a leg
	 * @return the drive forbid time (german "Sonntagsfahrverbot") interval in
	 *         milliseconds
	 */
	public static long getDriveRestrictionTimeMillis(final long lastDeparture,
			final long arrivalTime) {
		if (lastDeparture >= arrivalTime) {
			return 0;
		}

		int involvedDays = LongDateCalculator.getInvolvedDays(lastDeparture,
				arrivalTime);

		// Week of day: Sunday = 1, Monday = 2, ... Saturday = 7
		int startWeekDay = LongDateCalculator.getWeekday(lastDeparture);
		int currentWeekday = startWeekDay % 7; // <-- Saturday = 7 becomes 0

		long millisDriveForbid = 0;

		// is the start day at Sunday
		if (currentWeekday == Calendar.SUNDAY) {
			long millisInDay = LongDateCalculator.getMillisInDay(lastDeparture);

			if (millisInDay < (22 * IConstants.HOUR)) {
				// the start is on Sunday, it must be waited till 10:00p.m. to
				// start
				// driving
				millisDriveForbid = IConstants.DAY
						- LongDateCalculator.getMillisInDay(lastDeparture)
						- (2 * IConstants.HOUR);
			}
		}

		// only the days between start and end are checked here
		for (int i = 2; i < involvedDays; i++) {
			currentWeekday = (currentWeekday + 1) % 7;

			if (currentWeekday == Calendar.SUNDAY) {
				millisDriveForbid += (22 * IConstants.HOUR);
			}
		}

		int endWeekDay = LongDateCalculator.getWeekday(arrivalTime);
		currentWeekday = endWeekDay % 7; // <-- Saturday = 7 becomes 0

		// is the endday day at Sunday
		if (currentWeekday == Calendar.SUNDAY) {
			if (involvedDays > 1) {
				millisDriveForbid += (22 * IConstants.HOUR);
			} else {
				// as the start and end is on same Sunday, it is sufficient to
				// prolong the drive start
			}
		}

		return millisDriveForbid;
	}

	/**
	 * Returns the next Sunday (0:00) in milliseconds, except the given date is
	 * within Sunday, then the given date (which is Sunday) is returned.
	 * 
	 * 
	 * @param timeMillisBase
	 *            to calculate the nextSunday
	 * @return the next Sunday (0:00) in milliseconds, except the given date is
	 *         within Sunday, then the given date (which is Sunday) is returned.
	 */
	public static long getNextSundayTimeMillis(final long timeMillisBase) {
		// Week of day: Sunday = 1, Monday = 2, ... Saturday = 7
		int weekDay = LongDateCalculator.getWeekday(timeMillisBase);

		long nextSundayInMillis = timeMillisBase;

		// is the start day at Sunday
		if (weekDay == Calendar.SUNDAY) {
			return nextSundayInMillis;
		}

		// only the days between start and end are checked here
		for (int i = weekDay; i < 7; i++) {
			nextSundayInMillis += IConstants.DAY;
		}

		int millisInDay = LongDateCalculator.getMillisInDay(timeMillisBase);
		nextSundayInMillis += (IConstants.DAY - millisInDay);

		return nextSundayInMillis;
	}

	/**
	 * Returns the next Saturday (0:00) in milliseconds, except the given date
	 * is within Saturday, then the given date (which is Saturday) is returned.
	 * 
	 * 
	 * @param timeMillisBase
	 *            to calculate the nextSaturday
	 * @return the next Saturday (0:00) in milliseconds, except the given date
	 *         is within Saturday, then the given date (which is Saturday) is
	 *         returned.
	 */
	public static long getNextSaturdayTimeMillis(final long timeMillisBase) {
		// Week of day: Sunday = 1, Monday = 2, ... Saturday = 7
		int weekDay = LongDateCalculator.getWeekday(timeMillisBase);

		long nextSaturdayInMillis = timeMillisBase;

		// is the start day at SATURDAY
		if (weekDay == Calendar.SATURDAY) {
			return nextSaturdayInMillis;
		}

		// only the days between start and end are checked here
		for (int i = weekDay; i < 6; i++) {
			nextSaturdayInMillis += IConstants.DAY;
		}

		int millisInDay = LongDateCalculator.getMillisInDay(timeMillisBase);
		nextSaturdayInMillis += (IConstants.DAY - millisInDay);

		return nextSaturdayInMillis;
	}

	/**
	 * Returns the previous Sunday (0:00) in milliseconds, except the given date
	 * is within Sunday, then the given date (which is Sunday) is returned.
	 * 
	 * 
	 * @param timeMillisBase
	 *            to calculate the previousSunday
	 * @return the previous Sunday (0:00) in milliseconds, except the given date
	 *         is within Sunday, then the given date (which is Sunday) is
	 *         returned.
	 */
	public static long getPreviousSundayTimeMillis(final long timeMillisBase) {
		long nextSundayInMillis = getNextSundayTimeMillis(timeMillisBase);

		if (nextSundayInMillis == timeMillisBase) {
			// the give time is in Sunday
			return timeMillisBase;
		}

		// got the next sunday, but we want previous sunday
		return nextSundayInMillis - IConstants.WEEK;
	}
}
