package service.properties;

import java.util.Properties;

import service.util.PropertyFileReader;

/**
 * Class to access system properties from anywhere.
 */
public class SystemProperties {
	private static Properties userProperties;

	private SystemProperties() {
	}

	public static void loadUserProperties(String userPropertiesFile) {
		loadDefaultProperties();
		userProperties = PropertyFileReader.readProperties(userPropertiesFile,
				userProperties);
	}

	private static void loadDefaultProperties() {
		// TODO Create and load all System level to algorithm level defaults.
		// Properties file is at the moment empty
		String fileName = IConstants.DEFAULT_CONFIG_FILES_PATH
				+ IConstants.SHIPPING_PROPERTIES_FILE;
		userProperties = PropertyFileReader.readProperties(fileName, null);
	}
}
