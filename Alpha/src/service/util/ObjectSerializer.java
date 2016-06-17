package service.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Class to serialize/de-serialize objects to file.
 * 
 */
public class ObjectSerializer {
	/**
	 * @param object
	 * @param qualifiedFilePath
	 */
	public static void serializeToFile(Object object,
			String qualifiedFilePath) {
		try {
			FileOutputStream fos = new FileOutputStream(qualifiedFilePath);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(object);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param qualifiedFilePath
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object deserializeFromFile(String qualifiedFilePath)
			throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream(qualifiedFilePath);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Object object = ois.readObject();
		ois.close();

		return object;
	}
}
