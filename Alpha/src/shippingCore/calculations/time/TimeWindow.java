package shippingCore.calculations.time;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import service.dateAndTime.DateFormatter;
import service.exceptions.InvalidArgumentException;
import shippingCore.IConstants;

/**
 * Represents a time interval with starting and endpoint. The endpoint is part
 * of the interval, the start point is not.
 * 
 * @author Last modified by $Author: pzi $
 * @version $Revision: 81359 $
 */
public class TimeWindow implements Serializable, Cloneable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7831209653227210991L;

	/**
	 * for proper date formatting for logfiles, used also by all other classes of
	 * this package
	 */
	protected static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	/**
	 * for proper date formatting for logfiles, used also by all other classes of
	 * this package
	 */
	protected static SimpleDateFormat utcFormatter = new SimpleDateFormat("yyyy.MM.dd HH:mm z");

	/**
	 * specifies if the end point of the interval is included (default including)
	 */
	private boolean isEndTimeIncluding;

	/**
	 * specifies if the start point of the interval is included (default non
	 * including)
	 */
	private boolean isStartTimeIncluding;

	/** the end point of the interval (including) */
	private long endTime;

	/** the start point of the interval */
	private long startTime;

	static {
		utcFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	/**
	 * Constructor for creating a new time window object throws
	 * InvalidArgumentException if end time is less than start time
	 * 
	 * @param startTime the start point of this interval
	 * @param endTime the end point of this interval
	 * @throws InvalidArgumentException if
	 *         {@link TimeWindow#TimeWindow(long, boolean, long, boolean)}
	 */
	public TimeWindow(Date startTime, Date endTime)
	{
		this(startTime.getTime(), endTime.getTime());
	}

	/**
	 * Constructor for creating a new time window object throws
	 * InvalidArgumentException if end time is less than start time
	 * 
	 * @param startTime the start point of this interval
	 * @param startIncluding specifies if the start point of the interval is
	 *        included
	 * @param endTime the end point of this interval
	 * @param endIncluding specifies if the end point of the interval is included
	 * @throws InvalidArgumentException if
	 *         {@link TimeWindow#TimeWindow(long, boolean, long, boolean)}
	 */
	public TimeWindow(Date startTime, boolean startIncluding, Date endTime, boolean endIncluding)
	{
		this(startTime.getTime(), startIncluding, endTime.getTime(), endIncluding);
	}

	/**
	 * Constructor for creating a new time window object throws
	 * InvalidArgumentException if end time is less than start time
	 * 
	 * @param startTime the start point of this interval
	 * @param endTime the end point of this interval
	 * @throws InvalidArgumentException if
	 *         {@link TimeWindow#TimeWindow(long, boolean, long, boolean)}
	 */
	public TimeWindow(long startTime, long endTime)
	{
		this(startTime, false, endTime, true);
	}

	/**
	 * Copy constructor for creating a new time window object
	 * 
	 * @param other the time window object to copy
	 * @throws InvalidArgumentException if
	 *         {@link TimeWindow#TimeWindow(long, boolean, long, boolean)}
	 */
	public TimeWindow(TimeWindow other)
	{
		// this(other.startTime, other.isStartTimeIncluding, other.endTime,
		// other.isEndTimeIncluding);
		this.startTime = other.startTime;
		this.endTime = other.endTime;

		this.isStartTimeIncluding = other.isStartTimeIncluding;
		this.isEndTimeIncluding = other.isEndTimeIncluding;
	}

	/**
	 * Constructor for creating a new time window object throws
	 * InvalidArgumentException if end time is less than start time
	 * 
	 * @param startTime the start point of this interval
	 * @param isStartTimeIncluding specifies if the start point of the interval
	 *        is included
	 * @param endTime the end point of this interval
	 * @param isEndTimeIncluding specifies if the end point of the interval is
	 *        included
	 * @throws InvalidArgumentException if the startTime is after the endTime
	 */
	public TimeWindow(long startTime, boolean isStartTimeIncluding, long endTime,
			boolean isEndTimeIncluding)
	{
		if (startTime > endTime) {
			throw new InvalidArgumentException("EndTime: " + DateFormatter.formatDate(endTime)
					+ " is less than Starttime: " + new Date(startTime));
		}

		this.startTime = startTime;
		this.endTime = endTime;

		this.isStartTimeIncluding = isStartTimeIncluding;
		this.isEndTimeIncluding = isEndTimeIncluding;
	}

	/**
	 * Returns the start time of this interval.
	 * @return the start time of this interval
	 */
	public final long getStartTime()
	{
		return startTime;
	}

	/**
	 * Returns the end time of this interval.
	 * @return the end time of this interval
	 */
	public final long getEndTime()
	{
		return endTime;
	}

	/**
	 * Returns the start time of this interval as string
	 * @return the start time of this interval
	 */
	public final String getStartTimeString()
	{
		return formatter.format(startTime);
	}

	/**
	 * Returns the end time of this interval as string
	 * @return the end time of this interval
	 */
	public final String getEndTimeString()
	{
		return formatter.format(endTime);
	}

	/**
	 * Sets the new start time of this time window.
	 * 
	 * @param time the new start time of this time window
	 * @throws InvalidArgumentException if time is greater than end time
	 */
	public final void setStartTime(long time) throws InvalidArgumentException
	{
		if (time > endTime) {
			throw new InvalidArgumentException("Time: " + DateFormatter.formatDate(time)
					+ " is greater than  endTime: " + new Date(endTime));
		}

		startTime = time;
	}

	/**
	 * Sets the new start time of this time window.
	 * 
	 * @param time the new start time of this time window
	 * @param isStartTimeIncluding specifies if the start point of the interval
	 *        is included
	 * @throws InvalidArgumentException if time is greater than end time
	 */
	public final void setStartTime(long time, boolean isStartTimeIncluding)
			throws InvalidArgumentException
	{
		setStartTime(time);
		this.isStartTimeIncluding = isStartTimeIncluding;
	}

	/**
	 * Shifts the already set start time of this time window by the given value.
	 * 
	 * @param time time (in milliseconds) to shift the start time of this time
	 *        window
	 * @throws InvalidArgumentException if new time is greater than end time
	 */
	public final void shiftStartTime(long time) throws InvalidArgumentException
	{
		if ((startTime + time) > endTime) {
			throw new InvalidArgumentException("new Time: "
					+ DateFormatter.formatDate(startTime + time) + " would be greater than  endTime: "
					+ DateFormatter.formatDate(endTime));
		}

		startTime += time;
	}

	/**
	 * Sets the new end time of this time window.
	 * 
	 * @param time the new end time of this timewindow
	 * @throws InvalidArgumentException if time is less than start time
	 */
	public final void setEndTime(long time) throws InvalidArgumentException
	{
		if (time < startTime) {
			throw new InvalidArgumentException("Time: " + DateFormatter.formatDate(time)
					+ " is less than startTime: " + new Date(startTime));
		}

		endTime = time;
	}

	/**
	 * Sets the new end time of this time window.
	 * 
	 * @param time the new end time of this timewindow
	 * @param isEndTimeIncluding specifies if the end point of the interval is
	 *        included
	 * @throws InvalidArgumentException if time is less than start time
	 */
	public final void setEndTime(long time, boolean isEndTimeIncluding)
			throws InvalidArgumentException
	{
		setEndTime(time);
		this.isEndTimeIncluding = isEndTimeIncluding;
	}

	/**
	 * Shifts the already set end time of this time window by the given value.
	 * 
	 * @param time time (in milliseconds) to shift the end time of this time
	 *        window
	 * @throws InvalidArgumentException if new time is less than start time
	 */
	public final void shiftEndTime(long time) throws InvalidArgumentException
	{
		if ((endTime + time) < startTime) {
			throw new InvalidArgumentException("new Time: " + DateFormatter.formatDate(endTime + time)
					+ " would be less than startTime: " + DateFormatter.formatDate(startTime));
		}

		endTime += time;
	}

	/**
	 * Sets the new times (start and end time) of this time window.
	 * 
	 * @param start the new start time of this timewindow
	 * @param end the new end time of this timewindow
	 * @throws InvalidArgumentException if end is less than start time
	 */
	public final void setTimes(long start, long end) throws InvalidArgumentException
	{
		if (end < start) {
			throw new InvalidArgumentException("end Time: " + DateFormatter.formatDate(end)
					+ " is less than start Time: " + new Date(start));
		}

		endTime = end;
		startTime = start;
	}

	/**
	 * Sets the new times (start and end time) of this time window.
	 * 
	 * @param start the new start time of this timewindow
	 * @param isStartTimeIncluding specifies if the start point of the interval
	 *        is included
	 * @param end the new end time of this timewindow
	 * @param isEndTimeIncluding specifies if the end point of the interval is
	 *        included
	 * @throws InvalidArgumentException if end is less than start time
	 */
	public final void setTimes(long start, boolean isStartTimeIncluding, long end,
			boolean isEndTimeIncluding) throws InvalidArgumentException
	{
		setTimes(start, end);

		this.isStartTimeIncluding = isStartTimeIncluding;
		this.isEndTimeIncluding = isEndTimeIncluding;
	}

	/**
	 * Shifts the already set start/end time of this time window by the given
	 * value.
	 * 
	 * @param shiftValue the value (in milliseconds) to shift start and end time
	 *        of this timewindow
	 */
	public final void shiftTimes(long shiftValue)
	{
		endTime += shiftValue;
		startTime += shiftValue;
	}

	/**
	 * Sets the new times (start and end time) of this time window to the times
	 * passed in other time window.
	 * 
	 * @param other the time window containing the data to set this to
	 */
	public final void setTimeWindow(TimeWindow other)
	{
		endTime = other.endTime;
		startTime = other.startTime;
		this.isStartTimeIncluding = other.isStartTimeIncluding;
		this.isEndTimeIncluding = other.isEndTimeIncluding;
	}

	/**
	 * Returns the duration of this interval in ms.
	 * @return the duration of this interval in ms
	 */
	public final long getDuration()
	{
		return getEndTime() - getStartTime();
	}

	/**
	 * Returns the number passed or 0 if it is negative.
	 * @param l the number to return
	 * @return the number passed or 0 if it is negative
	 */
	private static final long atLeastZero(long l)
	{
		return (l < 0) ? 0 : l;
	}

	/**
	 * Returns the time difference of the passed time and the start time of this
	 * timewindow.
	 * @param time the time to check
	 * @return the time difference of the passed time and the start time of this
	 *         timewindow if time is before the starttime, 0 in all other cases
	 */
	public final long getTimeBefore(long time)
	{
		return atLeastZero(startTime - time);
	}

	/**
	 * Returns the time difference of the passed time and the end time of this
	 * timewindow.
	 * @param time the time to check
	 * @return the time difference of the passed time and the end time of this
	 *         timewindow if time is after the end time, 0 in all other cases
	 */
	public final long getTimeAfter(long time)
	{
		return atLeastZero(time - endTime);
	}

	/**
	 * Returns the time difference between the passed time window and this time
	 * window.
	 * @param other the time window to check against this
	 * @return the time difference of the passed time window and this time
	 *         window. If the two intervals overlap, 0 is returned.
	 */
	public final long getTimeBetween(TimeWindow other)
	{
		TimeWindow earlier = this;
		TimeWindow later = other;

		if (endTime > other.getEndTime()) {
			earlier = other;
			later = this;
		}

		// get the difference if any
		return atLeastZero(later.getStartTime() - earlier.getEndTime());
	}

	/**
	 * Returns the overlap time of this TimeWindow and the passed.
	 * @param other the time window to check against this
	 * @return time the passed timewindow and this timewindow overlap. If the two
	 *         intervals do not overlap, 0 is returned.
	 */
	public final long getOverlap(TimeWindow other)
	{
		TimeWindow window = getOverlapWindow(other);
		return window != null ? window.getDuration() : 0;
	}

	/**
	 * Returns the overlap time of this TimeWindow and the passed time interval.
	 * @param startOther the start time to test against
	 * @param endOther the end time to test against
	 * @return time the passed interval and this timewindow overlap. If the two
	 *         intervals do not overlap, 0 is returned.
	 */
	public final long getOverlap(long startOther, long endOther)
	{
		TimeWindow window = getOverlapWindow(startOther, endOther);
		return window != null ? window.getDuration() : 0;
	}

	/**
	 * Returns the overlap time of this TimeWindow and the passed time interval.
	 * @param other other window to check for overlap
	 * @return the TimeWindow representing the overlap period. Null, if there is
	 *         no overlap
	 */
	public final TimeWindow getOverlapWindow(TimeWindow other)
	{
		return getOverlapWindow(other.getStartTime(), other.getEndTime());
	}

	/**
	 * Returns the overlap time of this TimeWindow and the passed time interval.
	 * @param startOther the start time to test against
	 * @param endOther the end time to test against
	 * @return the TimeWindow representing the overlap period. Null, if there is
	 *         no overlap
	 */
	public final TimeWindow getOverlapWindow(long startOther, long endOther)
	{
		if (startOther >= endTime || endOther <= startTime) {
			return null;
		}
		long maxStart = (startTime > startOther) ? startTime : startOther;
		long minEnd = (endTime > endOther) ? endOther : endTime;
		if (maxStart < minEnd) {
			return new TimeWindow(maxStart, minEnd);
		} else {
			return null;
		}
	}

	/**
	 * Checks if the passed timeWindow lies within this time interval.
	 * @param other the timeWindow to check if it lies within this time interval
	 * @return true if the passed timeWindow is within this time interval
	 */
	public final boolean isTimeWindowWithin(TimeWindow other)
	{
		if ((startTime < other.startTime) && (endTime > other.endTime)) {
			// the other times are inside this time window
			return true;
		}

		if ((startTime > other.startTime) || (endTime < other.endTime)) {
			// the other times are outside this time window
			return false;
		}

		// if the start times are equal, that include flags have to be checked
		if ((startTime == other.startTime) && (!isStartTimeIncluding && other.isStartTimeIncluding)) {
			return false;
		}

		// if the end times are equal, that include flags have to be checked
		if ((endTime == other.endTime) && (!isEndTimeIncluding && other.isEndTimeIncluding)) {
			return false;
		}

		return true;
	}

	/**
	 * Checks if the passed time lies within this time interval (start point is
	 * not included).
	 * @param time the time to check
	 * @return true if the passed time is within this time interval (start point
	 *         is not included)
	 */
	public final boolean isTimeWithin(long time)
	{
		return (!isTimeAfter(time) && !isTimeBefore(time));
	}

	/**
	 * Checks if the passed time lies after (later than) this interval.
	 * @param time the time to check
	 * @return true if the passed time is after the end point
	 */
	public final boolean isTimeAfter(long time)
	{
		return ((time > endTime) || ((time == endTime) && !isEndTimeIncluding));
	}

	/**
	 * Checks if the passed time lies before (earlier than) this interval.
	 * @param time the time to check
	 * @return true if the passed time is before or equal the start point
	 */
	public final boolean isTimeBefore(long time)
	{
		return ((time < startTime) || ((time == startTime) && !isStartTimeIncluding));
	}

	/**
	 * Checks if this TimeWindow object intersects with the passed TimeWindow.
	 * @param other the time window to check against
	 * @return true if the passed time interval overlaps with this interval
	 */
	public final boolean intersects(TimeWindow other)
	{
		// check if both intervals intersect
		// again the end of an interval does not belong to the interval
		if (other.getStartTime() == other.getEndTime()) {

			if (!other.isStartTimeIncluding && !other.isEndTimeIncluding) {

				return false;
			}
			return isTimeWithin(other.getStartTime());
		} else if (getStartTime() == getEndTime()) {

			if (!isStartTimeIncluding && !isEndTimeIncluding) {

				return false;
			}
			return other.isTimeWithin(getStartTime());
		} else {
			return getOverlap(other) > 0;
		}
	}

	/**
	 * Checks if this TimeWindow object intersects with the passed times.
	 * 
	 * @param startOther the start time to test against (inclusive)
	 * @param endOther the end time to test against (inclusive)
	 * @return true if the passed times overlaps with this interval
	 */
	public final boolean intersects(long startOther, long endOther)
	{
		// check if both intervals intersect
		if (startOther == endOther) {
			return isTimeWithin(startOther);

		} else if (getStartTime() == getEndTime()) {

			if (!isStartTimeIncluding && !isEndTimeIncluding) {

				return false;
			}
			return getEndTime() >= startOther && getEndTime() <= endOther;

		} else {
			return getOverlap(startOther, endOther) > 0;
		}
	}

	/**
	 * Returns the time window that is the intersection of this and the passed
	 * time window. In case they do not intersect an empty TimeWindow at the end
	 * time of this is returned.
	 * @param other the time window to check against
	 * @return the intersection of this and the passed time window
	 */
	public final TimeWindow getIntersection(TimeWindow other)
	{
		// check if both intervals intersect
		// again the end of an interval does not belong to the interval
		if (getOverlap(other) > 0) {
			return new TimeWindow((startTime > other.startTime) ? startTime : other.startTime,
					(endTime < other.endTime) ? endTime : other.endTime);
		}

		// no overlap, so we return emtpy time window
		return new TimeWindow(endTime, endTime);
	}

	/**
	 * Returns a string representation of this object.
	 * @return a string representation of this object
	 */
	@Override
	public String toString()
	{
		return toString(formatter);
	}

	/**
	 * @return String representation with utc formater for date/times
	 */
	public String toStringUTC()
	{
		return toString(utcFormatter);
	}

	/**
	 * Returns a string representation of this object using the formatter
	 * @param dateFormatter the date formater to use to display the timewindow
	 * @return a string representation of this object
	 */
	public String toString(SimpleDateFormat dateFormatter)
	{
		StringBuffer result = new StringBuffer(dateFormatter.toPattern().length() * 3 + 3);

		String includeString = isStartTimeIncluding ? "[" : "]";
		result.append(includeString);
		result.append(dateFormatter.format(new Date(startTime)));
		result.append("-");
		result.append(dateFormatter.format(new Date(endTime)));
		includeString = isEndTimeIncluding ? "]" : "[";
		result.append(includeString);
		return result.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other)
	{
		if (other == null) {
			return false;
		}

		if (other.getClass() != getClass()) {
			return false;
		}

		TimeWindow otherTimeWindow = (TimeWindow) other;

		if ((startTime == otherTimeWindow.startTime) && (endTime == otherTimeWindow.endTime)
				&& (isStartTimeIncluding == otherTimeWindow.isStartTimeIncluding)
				&& (isEndTimeIncluding == otherTimeWindow.isEndTimeIncluding)) {
			return true;
		}

		return false;
	}

	/**
	 * Creates new TimeWindow which has shifted startTime of <code>millis</code>
	 * to future, and endTime of <code>millis</code> to past. The the startTime
	 * would be greater than endTime, in this case the endTime is set to
	 * startTime
	 * 
	 * @param millis the millis to use for startTime and endTime shifting
	 * @return new shrinked <code>TimeWindow</code> which can be of zero size
	 */
	public TimeWindow createShrinked(long millis)
	{
		// we will stay in the current timewindow boundary
		long newStartTime = Math.min(endTime, startTime + millis);
		return new TimeWindow(newStartTime, isStartTimeIncluding, Math.max(newStartTime, endTime
				- millis), isEndTimeIncluding);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return (int) (startTime ^ endTime ^ Boolean.valueOf(isStartTimeIncluding).hashCode() ^ Boolean
				.valueOf(isEndTimeIncluding).hashCode());
	}

	/**
	 * @return Returns if the end point of the interval is included
	 */
	public final boolean isEndTimeIncluding()
	{
		return isEndTimeIncluding;
	}

	/**
	 * @return Returns if the start point of the interval is included
	 */
	public final boolean isStartTimeIncluding()
	{
		return isStartTimeIncluding;
	}

	/**
	 * @return duration in hours
	 */
	public final long getDurationHours()
	{
		return getDuration() / IConstants.HOUR;
	}

	/**
	 * @return duration in minutes
	 */
	public final long getDurationMinutes()
	{
		return getDuration() / IConstants.MINUTE;
	}

	/**
	 * @param start
	 * @return TimeWindow from start to infinity
	 */
	public static TimeWindow toInfinity(Date start)
	{
		return new TimeWindow(start != null ? start : new Date(Long.MIN_VALUE), true, new Date(
				Long.MAX_VALUE), true);
	}

	/**
	 * @param end
	 * @return TimeWindow from infinity to end
	 */
	public static TimeWindow fromInfinity(Date end)
	{
		return new TimeWindow(new Date(Long.MIN_VALUE), true, end != null ? end : new Date(
				Long.MAX_VALUE), true);
	}

}
