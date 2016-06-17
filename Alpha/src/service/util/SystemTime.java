package service.util;

import java.util.Calendar;
import java.util.Date;

import service.properties.IConstants;

/**
 * This class keeps the System Time within the whole System.
 * 
 * In case of a simulation/demonstration with virtual times this current time is
 * distributed (even to clients and maybe other servers) and set.
 * 
 * Else just the current OS time is returned.
 * 
 */
public class SystemTime {
	/** the constant for real time mode */
	public static final int SYSTEM_TIME_MODE_REAL = 100;

	/** the constant for simulation/demonstration time mode */
	public static final int SYSTEM_TIME_MODE_DEMO = 200;

	/**
	 * the constant for simulation/demonstration time mode that skips all time
	 * consuming steps not needed for simulation
	 */
	public static final int SYSTEM_TIME_MODE_DEMO_SIMULATION = 300;

	private static final Date MIN_DB_DATE = DateTimeUtils.createUTCDate(1, 1, 1,
			0, 0);

	private static final Date MAX_DB_DATE = DateTimeUtils.createUTCDate(9999,
			12, 31, 0, 0);

	/** maximal time between two time steps */
	public static long maxTimeStep = IConstants.HOUR;

	/** the time mode of the system */
	private static int systemTimeMode = -1;

	/** the current (simulation) time */
	private static long nowTime;

	/** the current (simulation) time */
	private static long oldNowTime;

	/** flag that specifies if this class was correctly initialized */
	private static boolean initialized = false;

	/**
	 * Initializes this class.
	 * 
	 * @param newTimeMode
	 *            the time mode that has to be used:
	 *            {@link #SYSTEM_TIME_MODE_REAL} {@link #SYSTEM_TIME_MODE_DEMO}
	 *            {@link #SYSTEM_TIME_MODE_DEMO_SIMULATION}
	 */
	public static void init(final int newTimeMode) {
		// final String methodName = "init()";

		if (initialized) {
			// newLogger.log(CLASS_NAME, methodName, COMPONENT_LOG_LEVEL,
			// ILogger.WARNING,
			// "The system time is more than one time initialized!");
		}

		// if (newLogger != null) {
		// SystemTime.logger = newLogger;
		// }

		nowTime = getLocalizedZeroTime();
		oldNowTime = nowTime;
		setSystemTimeMode(newTimeMode);

		// SystemTime.logger.log(CLASS_NAME, methodName, COMPONENT_LOG_LEVEL,
		// ILogger.INFO,
		// new Object[] { "Initialized SystemTime class with systemTimeMode '",
		// Integer.toString(systemTimeMode),
		// "' (SYSTEM_TIME_MODE_DEMO = 0, SYSTEM_TIME_MODE_REAL = 1)." });
	}

	/**
	 * @return the current system time
	 */
	public static long getSystemTime() {
		return nowTime;
	}

	/**
	 * @return the current system time
	 */
	public static long getRealTime() {
		return System.currentTimeMillis();
	}

	/**
	 * @return the current system time
	 */
	public static long getSystemTimeForDB() {
		return nowTime - nowTime % 1000;
	}

	/**
	 * @return the current system time
	 */
	public static long getRealTimeForDB() {
		long millis = System.currentTimeMillis();
		return millis - millis % 1000;
	}

	/**
	 * @return the current system time as Date
	 */
	public static Date getSystemTimeAsDate() {
		return new Date(getSystemTime());
	}

	/**
	 * @return Date of the application time intented to be stored in DB
	 */
	public static Date getSystemTimeAsDBDate() {
		return new Date(getSystemTimeForDB());
	}

	/**
	 * @return Date of the operation system time intented to be stored in DB
	 */
	public static Date getRealTimeAsDBDate() {
		return new Date(getRealTimeForDB());
	}

	/**
	 * @return Date of the operation system time
	 */
	public static Date getRealTimeAsDate() {
		return new Date(getRealTime());
	}

	/**
	 * @return the system time mode: either {@link #SYSTEM_TIME_MODE_DEMO},
	 *         {@link #SYSTEM_TIME_MODE_REAL} or
	 *         {@link #SYSTEM_TIME_MODE_DEMO_SIMULATION}
	 */
	public static int getSystemTimeMode() {
		return systemTimeMode;
	}

	/**
	 * @return <code>true</code> if we are in real mode, or in demo mode (not
	 *         demo simulation mode)
	 */
	public static boolean notDemoSimulation() {
		return getSystemTimeMode() < SYSTEM_TIME_MODE_DEMO_SIMULATION;
	}

	/**
	 * @return the string representation of the current system time mode
	 */
	public static String getSystemTimeModeString() {
		// <todo> 2005-07-12 ohi: introduce SystemTimeMode objects wrapping the
		// integer, implementing toString()
		if (systemTimeMode == SYSTEM_TIME_MODE_REAL) {
			return "SYSTEM_TIME_MODE_REAL";
		} else if (systemTimeMode == SYSTEM_TIME_MODE_DEMO) {
			return "SYSTEM_TIME_MODE_DEMO";
		} else if (systemTimeMode == SYSTEM_TIME_MODE_DEMO_SIMULATION) {
			return "SYSTEM_TIME_MODE_DEMO_SIMULATION";
		}

		return "-";
	}

	/**
	 * sets the new system time, if it is bigger than current system time
	 * 
	 * @param newSystemTime
	 *            the new system time
	 * @return true when the system time was changed, false if time change was
	 *         ignored/refused
	 */
	public static boolean setSystemTime(final long newSystemTime) {
		if (!isUpdateTimePossible(newSystemTime)) {
			// final String methodName = "setSystemTime(long)";
			// logger.log(CLASS_NAME, methodName, COMPONENT_LOG_LEVEL,
			// ILogger.WARNING, new Object[] {
			// "Ignored try to set the system backwards from ", new
			// Date(nowTime),
			// " to ",
			// new Date(newSystemTime) });
			return false;
		}

		oldNowTime = nowTime;

		// ensure that no seconds an milliseconds are used
		nowTime = (newSystemTime / IConstants.MINUTE) * IConstants.MINUTE;

		return true;
	}

	/**
	 * Checks if updating/forwarding of the systemTime is possible/necessary
	 * according the new given SystemTime.
	 * 
	 * @param newSystemTime
	 *            the new system time
	 * 
	 * @return true if updating the system time is possible/necessary, false if
	 *         not
	 */
	public static boolean isUpdateTimePossible(final long newSystemTime) {
		// ensure that no seconds an milliseconds are used
		long clearedSystemTime = (newSystemTime / IConstants.MINUTE)
				* IConstants.MINUTE;

		if (clearedSystemTime <= nowTime) {
			return false;
		}

		return true;
	}

	/**
	 * Returns true if at the last call to setSystemTime(long) the time was
	 * increased.
	 * 
	 * @return true if at the last call to setSystemTime(long) the time was
	 *         increased.
	 */
	public static boolean hasTimeProceeded() {
		return oldNowTime != nowTime;
	}

	/**
	 * Resets the system time if time mode is not {@link #SYSTEM_TIME_MODE_REAL}
	 * . Else this set is just ignored.
	 * 
	 */
	public static void resetSystemTime() {
		if (systemTimeMode == SYSTEM_TIME_MODE_REAL) {
			return;
		}

		nowTime = getLocalizedZeroTime();
		oldNowTime = nowTime;
	}

	/**
	 * @param newTimeMode
	 *            the new system time mode
	 */
	private static void setSystemTimeMode(final int newTimeMode) {
		systemTimeMode = newTimeMode;
		// for realtime mode ensure to have the time set immediately
		if (systemTimeMode == SYSTEM_TIME_MODE_REAL) {
			setSystemTime(System.currentTimeMillis());
		}
	}

	/**
	 * Gets the localized zero time.
	 * 
	 * @return The localized zero time.
	 */
	private static final long getLocalizedZeroTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(0);

		calendar.set(Calendar.HOUR_OF_DAY, 0);

		return calendar.getTimeInMillis();
	}

	/**
	 * @param time
	 * @return Date created from the time millis intented to be stored in DB or
	 *         some repository
	 */
	public static Date asDBDate(long time) {
		return new Date(time - time % 1000L);
	}

	/**
	 * @param time
	 * @return Date created from the time millis
	 */
	public static Date asRealDate(long time) {
		return new Date(time);
	}

	/**
	 * @return minimal date that can be used in DB
	 */
	public static Date minDBDate() {
		return MIN_DB_DATE;
	}

	/**
	 * @return maximal date that can be used in DB
	 */
	public static Date maxDBDate() {
		return MAX_DB_DATE;
	}
}