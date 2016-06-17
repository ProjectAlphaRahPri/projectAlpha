package service.log;

import java.io.File;
import java.util.logging.Level;

/**
 * Interface for all different logger implementation. Further on the different
 * log levels are defined. Uses Java's Level to specify the Log level
 * 
 */

public interface ILogger {
	/**
	 * location of the log file to be saved
	 */
	public static final String LOG_PATH = System.getProperty("user.dir")
			+ File.separator + "logs" + File.separator;

	/**
	 * Logs the given notice, if {@link #canLog(ComponentLogLevel,int)
	 * canLog(component, level)} returns true.
	 * 
	 * @param className
	 *            name of the class in which the log message was created
	 * @param methodName
	 *            name of the method in which the log message was created
	 * @param logNotice
	 *            content of the log message
	 * @param throwable
	 *            Throwable (Exception or Error) that should be logged
	 */
	void log(Level alevel, String className, String methodName,
			String logNotice, Throwable throwable);

	/**
	 * Logs the given notice
	 * 
	 * @param className
	 *            name of the class in which the log message was created
	 * @param methodName
	 *            name of the method in which the log message was created
	 * @param logNotice
	 *            content of the log message
	 * @param throwable
	 *            Throwable (Exception or Error) that should be logged
	 */
	void log(Level alevel, String className, String methodName,
			Object[] logNotice, Throwable throwable);

	/**
	 * Logs the given notice
	 * 
	 * @param level
	 *            level of the message (Level.INFO ... LEVEL.ALL) determining,
	 *            if the notice gets logged or ignored
	 * @param className
	 *            name of the class in which the log message was created
	 * @param methodName
	 *            name of the method in which the log message was created
	 * @param throwable
	 *            Throwable (Exception or Error) that should be logged
	 */
	void log(Level alevel, String className, String methodName,
			Throwable throwable);

	/**
	 * Logs the given notice
	 * 
	 * @param level
	 *            level of the message (Level.INFO ... LEVEL.ALL) determining,
	 *            if the notice gets logged or ignored
	 * @param className
	 *            name of the class in which the log message was created
	 * @param methodName
	 *            name of the method in which the log message was created
	 * @param logNotice
	 *            content of the log message
	 */
	void log(Level alevel, String className, String methodName,
			String logNotice);

	/**
	 * Logs the given notice
	 * 
	 * @param level
	 *            level of the message (Level.INFO ... LEVEL.ALL) determining,
	 *            if the notice gets logged or ignored
	 * @param className
	 *            name of the class in which the log message was created
	 * @param methodName
	 *            name of the method in which the log message was created
	 * @param logNotice
	 *            content of the log message
	 */
	void log(Level alevel, String className, String methodName,
			Object[] logNotice);

	/**
	 * Logs the given notice and throwable
	 * 
	 * @param level
	 *            level of the message (Level.INFO ... LEVEL.ALL) determining,
	 *            if the notice gets logged or ignored
	 * @param logNotice
	 *            content of the log message
	 * @param throwable
	 *            Throwable (Exception or Error) that should be logged
	 */
	void log(Level alevel, String logNotice, Throwable throwable);

	/**
	 * Logs the given notice and throwable
	 * 
	 * @param level
	 *            level of the message (Level.INFO ... LEVEL.ALL) determining,
	 *            if the notice gets logged or ignored
	 * @param logNotice
	 *            content of the log message
	 * @param throwable
	 *            Throwable (Exception or Error) that should be logged
	 */
	void log(Level alevel, Object[] logNotice, Throwable throwable);

	/**
	 * Logs the given throwable
	 * 
	 * @param level
	 *            level of the message (Level.INFO ... LEVEL.ALL) determining,
	 *            if the notice gets logged or ignored
	 * @param throwable
	 *            Throwable (Exception or Error) that should be logged
	 */
	void log(Level alevel, Throwable throwable);

	/**
	 * Logs the given notice
	 * 
	 * @param level
	 *            level of the message (Level.INFO ... LEVEL.ALL) determining,
	 *            if the notice gets logged or ignored
	 * @param logNotice
	 *            content of the log message
	 */
	void log(Level alevel, String logNotice);

	/**
	 * Logs the given notice
	 * 
	 * @param level
	 *            level of the message (Level.INFO ... LEVEL.ALL) determining,
	 *            if the notice gets logged or ignored
	 * @param logNotice
	 *            content of the log message
	 */
	void log(Level alevel, Object[] logNotice);

	/**
	 * Changes the log file to another file
	 * 
	 * @param filename
	 *            name of the file to be used as log file
	 * @param append
	 *            specifies append Mode
	 * @return false, if the new log file could not be opened or the old one
	 *         could not be closed; true else
	 */
	boolean setFileHandler(String filename, boolean append);

	/**
	 * closes the log file
	 */
	void closeFileHandler();

	/**
	 * checks, if the logger uses a log file or Console logging
	 * 
	 * @return true, if a log file is in use; false otherwise
	 */
	boolean hasFileHandler();

	/**
	 * Sets the Loggers log level. The log level is used to specify if message
	 * of a certain level has to be logged: If the log level of a log message is
	 * at least as important the current log level, the message is written,
	 * otherwise it is ignored.
	 * 
	 * @param level
	 *            log level as an Level Object (use defined constants like
	 *            ILogger.WARNING)
	 */
	void setLogLevel(Level level);

	/**
	 * Sets the new Formatter for the Logger.
	 * 
	 * @param formatter
	 *            new formatter to set the Logger with
	 */

	boolean setFormatter(java.util.logging.Formatter formatter);
}