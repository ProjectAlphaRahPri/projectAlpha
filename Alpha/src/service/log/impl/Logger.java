package service.log.impl;

import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;

import service.log.ILogger;
import service.log.PreciseFormatter;

/**
 * Logger for file logging as well as console logging
 * <p>
 * File logging is specified by calling a constructor with a filename as a
 * parameter
 * <p>
 * Console logging is specified by calling a parameterless constructor
 * 
 * @author Rajit Shahi
 * 
 */

public class Logger implements ILogger
{
	private static final LogManager lm = LogManager.getLogManager();

	private static FileHandler fileHandler;

	/** class name for logging */
	private static final String CLASS_NAME = Logger.class.getName();

	/** handle for logging */
	private static final java.util.logging.Logger alogger = java.util.logging.Logger
			.getLogger(CLASS_NAME);

	public Logger()
	{
		alogger.setLevel(Level.INFO);
	}

	/**
	 * Constructor that includes trying to open the file where the log messages
	 * get stored.
	 * 
	 * @param loggerName
	 * @param fileName name of the log file
	 */
	public Logger(String fileName)
	{
		alogger.setLevel(Level.INFO);
		setFileHandler(fileName, false);
	}

	@Override
	public synchronized boolean setFileHandler(String fileName, boolean append)
	{
		final String methodName = "setFile()";

		if (fileName == "" || fileName == null) {
			log(alogger.getLevel(), CLASS_NAME, methodName, "Invalid Log file");
			return false;
		}

		if (fileHandler != null)
			fileHandler.close();

		try {
			fileHandler = new FileHandler(ILogger.LOG_PATH + fileName, append);

			lm.addLogger(alogger);
			fileHandler.setFormatter(new PreciseFormatter());
			alogger.addHandler(fileHandler);

		} catch (Exception e) {
			log(alogger.getLevel(), CLASS_NAME, methodName, new Object[] { "logfile '", fileName,
					"' could not be closed, switching to logfile '", fileName, "' failed" }, e);
			return false;
		}
		return true;
	}

	@Override
	public synchronized void closeFileHandler()
	{

		final String methodName = "closeFile()";

		if (fileHandler != null)
			try {
				fileHandler.close();
				fileHandler = null;
			} catch (Exception e) {
				log(alogger.getLevel(), CLASS_NAME, methodName, "closing log File failed", e);
			}
	}

	@Override
	public synchronized boolean hasFileHandler()
	{
		if ((fileHandler == null)) {
			return false;
		}

		return true;
	}

	@Override
	public synchronized void log(Level alevel, String className, String methodName,
			String logNotice, Throwable throwable)
	{
		alogger.logp(alevel, className, methodName, logNotice, throwable);
	}

	@Override
	public synchronized void log(Level alevel, String className, String methodName,
			Object[] logNotice, Throwable throwable)
	{
		log(alevel, className, methodName, logNotice);
	}

	@Override
	public synchronized void log(Level alevel, String className, String methodName,
			Throwable throwable)
	{
		alogger.logp(alevel, className, methodName, "", throwable);
	}

	@Override
	public synchronized void log(Level alevel, String className, String methodName, String logNotice)
	{
		alogger.logp(alevel, className, methodName, logNotice);
	}

	@Override
	public synchronized void log(Level alevel, String className, String methodName,
			Object[] logNotice)
	{
		alogger.logp(alevel, className, methodName, "", logNotice);
	}

	@Override
	public synchronized void log(Level alevel, String logNotice, Throwable throwable)
	{
		alogger.log(alevel, logNotice, throwable);
	}

	@Override
	public synchronized void log(Level alevel, Object[] logNotice, Throwable throwable)
	{
		alogger.log(alevel, "", logNotice);
	}

	@Override
	public synchronized void log(Level alevel, Throwable throwable)
	{
		alogger.log(alevel, "", throwable);
	}

	@Override
	public synchronized void log(Level alevel, String logNotice)
	{
		alogger.log(alevel, logNotice);
	}

	@Override
	public synchronized void log(Level alevel, Object[] logNotice)
	{
		alogger.log(alevel, "", logNotice);
	}

	@Override
	public synchronized void setLogLevel(Level level)
	{
		Handler[] handlers = alogger.getHandlers();
		for (int index = 0; index < handlers.length; index++) {
			handlers[index].setLevel(Level.FINE);
		}

		alogger.setLevel(level);
	}

	@Override
	public synchronized boolean setFormatter(java.util.logging.Formatter formatter)
	{
		if (fileHandler == null)
			return false;

		try {
			fileHandler.setFormatter(formatter);
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}