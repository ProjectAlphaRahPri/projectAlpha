package shippingCore;

import java.io.File;

public interface IConstants {

	/** convert from nanoSec to seconds. */
	int nanoToSec = 1000000000;

	/** One second in milli seconds. */
	int SECOND = 1000;

	/** One minute in milli seconds. */
	int MINUTE = 60 * SECOND;

	/** One hour in milli seconds. */
	int HOUR = 60 * MINUTE;

	/** One day in milli seconds. */
	int DAY = 24 * HOUR;

	/** One week in milli seconds. */
	int WEEK = 7 * DAY;
	
	/**
	 * file name of DistanceMap File (EWS Format)
	 */
	public static final String DISTANCEMAP_EWS_FILE = "eu0902bin.dmb";

	/**
	 * file name of DistanceMap File (EWS Format)
	 */
	public static final String DISTANCEMAP_DIMABIGENDIAN_FILE = "Dima-BigEndian.dat";
	
	/**
	 * file location path of DistanceMap File
	 */
	public static final String DISTANCEMAP_EWS_FILE_PATH = System.getProperty("user.dir")
			+ File.separator + "distanceMap" + File.separator + DISTANCEMAP_EWS_FILE;

	/**
	 * file location path of DistanceMap File
	 */
	public static final String DISTANCEMAP_DIMABIGENDIAN_FILE_PATH = System.getProperty("user.dir")
			+ File.separator + "distanceMap" + File.separator + DISTANCEMAP_DIMABIGENDIAN_FILE;

	/** file location path of misc Files */
	public static final String MISC_FILES_PATH = System.getProperty("user.dir") + File.separator
			+ "miscfiles" + File.separator;

	/** file location path of data Files */
	public static final String DATA_FILES_PATH = System.getProperty("user.dir") + File.separator
			+ "data" + File.separator;

	/** file location path of benchmark data Files */
	public static final String DATA_BENCHMARK_PATH = System.getProperty("user.dir") + File.separator
			+ "data" + File.separator + "benchmark" + File.separator + "800customer" + File.separator
			+ "LR181.txt";

	/** file location path of configuration Files */
	public static final String CONFIG_FILES_PATH = System.getProperty("user.dir") + File.separator
			+ "configuration" + File.separator;

	/** General Properties file for attractive */
	public static final String ATTRACTIVE_PROPERTIES_FILE = "attractive.properties";

	public static final String LOCAL_OPTIMIZATION_PROPERTIES_FILE = "localOptimization.properties";

	public static final String AGENT_OPTIMIZATION_PROPERTIES_FILE = "agentOptimization.properties";

	public static final String CONSTRAINT_PROPERTIES_FILE = "constraint.properties";

	public static final String CUSTOMER1_TRUCK_FILENAME = "LKWDaten156.txt";

	public static final String CUSTOMER1_TRUCK_TYPES_FILENAME = "";

	public static final String LOCATION_SQL_FILENAME = "location_DHL.sql";
	

	/**
	 * The default size of a {@link StringBuffer}. Should be used for nearly
	 * every creation of a StringBuffer.
	 */
	int DEFAULT_STRING_BUFFER_SIZE = 10; // 512;

	/**
	 * The Java Virtual Machine is terminated with exit code 6, if the access is
	 * denied.
	 */
	int EXIT_CODE_ACCESS_DENIED = 6;

	/**
	 * The Java Virtual Machine is terminated with exit code 5, if a connection
	 * cannot be established or fails.
	 */
	int EXIT_CODE_CONNECTION_FAILURE = 5;

	/**
	 * The Java Virtual Machine is terminated with exit code 31, if configuration
	 * file has no keys for the module file.
	 */
	int EXIT_CODE_NO_KEYS_FOR_MODULE_FILE = 31;

	/**
	 * The Java Virtual Machine is terminated with exit code 8, if there are no
	 * license rights
	 */
	int EXIT_CODE_NO_LICENSE_RIGHTS = 21;

	/**
	 * The Java Virtual Machine is terminated with exit code 9, if no local IP
	 * address is configured and the local IP address cannot be determined.
	 */
	int EXIT_CODE_NO_LOCAL_IP_ADDRESS = 9;

	//
	// 20 <= exit code < 30: license problem
	//

	/**
	 * The Java Virtual Machine is terminated with exit code 20, if a license
	 * file is not valid
	 */
	int EXIT_CODE_NO_VALID_LICENSE_FILE = 20;

	/**
	 * The Java Virtual Machine is terminated with exit code 10, if a security
	 * manager is already set
	 */
	int EXIT_CODE_SECURITY_MANAGER_ALREADY_SET = 10;

	/**
	 * The Java Virtual Machine is terminated with exit code 4, if any unforeseen
	 * Error is caught.
	 */
	int EXIT_CODE_UNFORESEEN_ERROR = 4;

	/**
	 * The Java Virtual Machine is terminated with exit code 3, if any unforeseen
	 * RuntimeException is caught.
	 */
	int EXIT_CODE_UNFORESEEN_EXCEPTION = 3;

	//
	// 30 <= exit code < 40: wrong configuration
	//

	/**
	 * The Java Virtual Machine is terminated with exit code 30, if configuration
	 * is wrong somehow (for example a system configuration file has a severe
	 * error).
	 */
	int EXIT_CODE_WRONG_CONFIGURATION = 30;

	/**
	 * The Java Virtual Machine is terminated with exit code 1, if there is any
	 * (not nearer specified) failure.
	 */
	int EXIT_CODE_GENERAL_FAILURE = 1;

	// ///
	//
	// definition of exit codes:
	//

	/**
	 * The Java Virtual Machine is terminated with exit code 0, if there is a
	 * successful termination of the program (no failure).
	 */
	int EXIT_CODE_SUCCESS = 0;

	/**
	 * The Java Virtual Machine is terminated with exit code 2, if there is a
	 * failure due to a wrong command line invocation.
	 */
	int EXIT_CODE_WRONG_COMMAND_LINE = 2;

	/** represents an empty string */
	String EMPTY_STRING = "";

	/**
	 * Constant representing invalid ID value (e.g. for references via primitive
	 * types where null is not possible
	 */
	int INVALID_ID = -1;


}
