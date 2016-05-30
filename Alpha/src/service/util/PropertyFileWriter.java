package service.util;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Stefan Glaser
 */
public class PropertyFileWriter
{
	public static void writeProperties(Properties props, String fileName)
	{
		writeProperties(props, fileName, "");
	}

	public static void writeProperties(Properties props, String fileName, String comments)
	{
		BufferedOutputStream out;
		try {
			out = new BufferedOutputStream(new FileOutputStream(fileName));
			props.store(out, comments);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
