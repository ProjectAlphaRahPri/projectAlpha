package service.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileReader {
	public static Properties readProperties(String fileName,
			Properties parentProperties) {
		Properties props = new Properties(parentProperties);

		if (fileName == null) {
			return props;
		}

		BufferedInputStream stream = null;
		try {
			stream = new BufferedInputStream(new FileInputStream(fileName));
			props.load(stream);
			stream.close();
		} catch (FileNotFoundException e) {
			System.out.println("Config-File not Found: " + fileName
					+ "\nFallback to default!");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return props;
	}
}
