package shippingCore;

import java.util.Properties;

import service.exceptions.InvalidArgumentException;
import service.util.PropertyFileReader;
import shippingCore.IPropertiesConstants.OptimizeBy;


/**
 * The Attractive class is used as a accessor class for application properties.
 * 
 * @author Stefan Glaser
 */
public class ShippingCoreProperties
{
	private static Properties userProperties;

	private static OptimizeBy optimizeBy;

	private ShippingCoreProperties()
	{
	}

	/**
	 * @param propertiesFile - a file containing the specific user properties
	 */
	public static void loadUserProperties(String propertiesFile)
	{
		loadProperties();
		userProperties = PropertyFileReader.readProperties(IConstants.CONFIG_FILES_PATH
				+ propertiesFile, userProperties);

		// initialize cached properties
		initCachedProperties();
	}

	/**
	 * This method loads all attractive related properties files:
	 * <ul>
	 * <li>attractive.properties - basic properties for the whole attractive
	 * project</li>
	 * <li>localOptimization.properties - properties related to the local
	 * optimization</li>
	 * <li>agentOptimization.properties - properties related to agent based
	 * optimization</li>
	 * <li>constraint.properties - properties of the constraints</li>
	 * </ul>
	 */
	public static void loadProperties()
	{
		// Load basic attractive properties
		userProperties = PropertyFileReader.readProperties(IConstants.CONFIG_FILES_PATH
				+ IConstants.ATTRACTIVE_PROPERTIES_FILE, System.getProperties());

		// Load local optimization properties
		userProperties = PropertyFileReader.readProperties(IConstants.CONFIG_FILES_PATH
				+ IConstants.LOCAL_OPTIMIZATION_PROPERTIES_FILE, userProperties);

		// Load agent optimization properties
		userProperties = PropertyFileReader.readProperties(IConstants.CONFIG_FILES_PATH
				+ IConstants.AGENT_OPTIMIZATION_PROPERTIES_FILE, userProperties);

		// Load constraint properties
		userProperties = PropertyFileReader.readProperties(IConstants.CONFIG_FILES_PATH
				+ IConstants.CONSTRAINT_PROPERTIES_FILE, userProperties);

		// initialize cached properties
		initCachedProperties();
	}

	private static void initCachedProperties()
	{
		String result = userProperties.getProperty("optimization.optimizeBy");
		if (result.equals(IPropertiesConstants.OPTIMIZATION_OPTIMIZE_BY_COST)) {
			optimizeBy = OptimizeBy.COST;
		} else if (result.equals(IPropertiesConstants.OPTIMIZATION_OPTIMIZE_BY_DISTANCE)) {
			optimizeBy = OptimizeBy.DISTANCE;
		} else {
			throw new InvalidArgumentException("Property \"optimization.optimizeBy=" + result
					+ "\" is invalid!");
		}
	}

	/**
	 * @param key - the key to search for
	 * @return the value to the given key or <code>null</code>
	 */
	public static String getProperty(String key)
	{
		return getProperty(key, null);
	}

	/**
	 * @param key - the key to the property
	 * @param value - the value to set for the specified property
	 */
	public static void setProperty(String key, String value)
	{
		if (userProperties != null) {
			userProperties.setProperty(key, value);
		}
	}

	/**
	 * @param key - the key to search for
	 * @param defaultValue - the default value, if the properties doesn't contain
	 *        the given key
	 * @return the value to the given key or defaultValue
	 */
	public static String getProperty(String key, String defaultValue)
	{
		if (userProperties == null) {
			loadProperties();
		}

		return userProperties.getProperty(key, defaultValue);
	}

	// /**
	// * AttractiveProperties is encapsulating the default attractive.properties
	// * file located in the configuration folder.
	// *
	// * @author Stefan Glaser
	// */
	// private class AttractiveProperties extends Properties implements
	// PropertiesConstants
	// {
	// /** */
	// private static final long serialVersionUID = -3135404030002022633L;
	//
	// /** */
	// public AttractiveProperties()
	// {
	// super(System.getProperties());
	//
	// try {
	// this.load(new FileInputStream(IConstants.CONFIG_FILES_PATH
	// + IConstants.ATTRACTIVE_PROPERTIES_FILE));
	// } catch (FileNotFoundException e) {
	// setProperty("agent.enabled", "false");
	//
	// setProperty("data.dao.capacity", DATA_DAO_DATABASE);
	// setProperty("data.dao.capacityXMLFile", "/data/customer2/capacities.xml");
	// setProperty("data.dao.capacityWrapper", DATA_DAO_WRAPPER_OWN);
	// setProperty("data.dao.consignment", DATA_DAO_DATABASE);
	// setProperty("data.dao.ipAddress", DATA_DAO_IPAddress);
	// setProperty("data.dao.consignmentXMLFile",
	// "/data/customer2/2010_04/2135Orders.xml");
	// setProperty("data.dao.consignmentGeneratorSize", "100");
	//
	// setProperty("allocation.routeAllocator.strategy",
	// ALLOCATION_ROUTEALLOCATOR_STRATEGY_SHORTESTDISTANCE);
	// setProperty("allocation.routeAllocator.costModel",
	// ALLOCATION_ROUTEALLOCATOR_COSTMODEL_FIXVARIABLE);
	// setProperty("allocation.routeAllocator",
	// ALLOCATION_ROUTEALLOCATOR_EXISTINGTRUCK + ","
	// + ALLOCATION_ROUTEALLOCATOR_VIRTUALTRUCK);
	// setProperty("allocation.readFromFile", "false");
	// setProperty("allocation.saveToFile", "false");
	//
	// setProperty("optimization.optimizeBy", OPTIMIZATION_OPTIMIZE_BY_COST);
	// setProperty("optimization.localSearchStrategy",
	// OPTIMIZATION_LOCALSEARCHSTRATEGY_NOSEARCH);
	// setProperty("optimization.maxRuntime", "-1");
	// setProperty("optimization.neighborhood", "changedIncremental");
	// setProperty("optimization.interCompanyShareRatio",
	// OPTIMIZATION_INTERCOMPANY_SHARE_RATIO_DEFAULT);
	// setProperty("optimization.maxCycles", "-1");
	// setProperty("optimization.hillClimbing.maxRandomRestarts", "1");
	// setProperty("optimization.hillClimbing.maxCyclesWithoutImprovement",
	// "100");
	// setProperty("optimization.simulatedAnnealing.coolingSchedule",
	// OPTIMIZATION_SIMULATEDANNEALING_COOLINGSCHEDULE_HYPERBOLIC);
	// setProperty("optimization.simulatedAnnealing.startTemperature", "1000.0");
	// setProperty("optimization.simulatedAnnealing.endTemperature", "1.0");
	// setProperty("optimization.tabuSearch.tabuSize", "10");
	// setProperty("optimization.randomWalk.steps", "5");
	// setProperty("constraint.maxPickupDistance", "50");
	//
	// try {
	// File file = new File(IConstants.CONFIG_FILES_PATH
	// + IConstants.ATTRACTIVE_PROPERTIES_FILE);
	//
	// file.createNewFile();
	// store(new FileOutputStream(file), "This is a comment");
	// } catch (FileNotFoundException e1) {
	// e1.printStackTrace();
	// } catch (IOException e1) {
	// e1.printStackTrace();
	// }
	//
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// }

	/**
	 * @return the optimization mode
	 */
	public static OptimizeBy getOptimizeBy()
	{
		return optimizeBy;
	}
}
