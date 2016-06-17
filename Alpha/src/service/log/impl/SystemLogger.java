package service.log.impl;

import service.log.ILogger;

/**
 * Container class for a Logger
 * <p>
 * To access the Logger, this class should be used to get the Singleton
 * <b> Logger</b> object. This will discourage creating own Logger object at
 * will.
 * 
 */
public final class SystemLogger {
	private static ILogger aLogger;

	/**
	 * returns the Singleton instance of the Logger Object
	 * 
	 * @return - Singleton Logger object
	 */
	public synchronized static ILogger getLogger() {
		if (aLogger == null)
			aLogger = new Logger();
		return aLogger;
	}

	@Deprecated
	public static void setLogger(ILogger arg0) {
		aLogger = arg0;
	}
}
