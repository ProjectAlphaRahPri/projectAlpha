/**
 * 
 */
package service.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author fhurrle
 * 
 */
public class CSVFileWriter
{

	/**
	 * 
	 * @param filename
	 * @param data
	 * @param seperator
	 */
	public void writeFile(String filename, List<String[]> data, String seperator)
	{

		try {
			FileWriter fstream = new FileWriter(filename);

			BufferedWriter out = new BufferedWriter(fstream);

			for (String[] strings : data) {
				for (String string : strings) {
					out.write(string + seperator);
				}
				out.write("\n");
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
