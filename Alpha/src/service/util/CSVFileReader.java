/**
 * 
 */
package service.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to read any text File separated by any separation string
 * 
 * @author Rajit Shahi
 * 
 */
public class CSVFileReader
{

	/**
	 * Enum for defining the separator in the file
	 * 
	 * @author Rajit Shahi
	 * 
	 */
	public enum Seperator {
		Comma(","), SemiColon(";"), Tab("\t");

		private String sepString;

		private Seperator(String sepString)
		{
			this.sepString = sepString;
		}

		public String getSeperatorString()
		{
			return this.sepString;
		}
	}

	/**
	 * reads the text based file
	 * 
	 * @param filename name of the file to read
	 * @param seperator Seperator Enum
	 * @return list of string arrays each array containing Strings from the file
	 */
	public static List<String[]> readFile(String filename, Seperator seperator)
	{
		List<String[]> aList = new ArrayList<String[]>();

		try {
			FileReader csvFile = new FileReader(filename);
			BufferedReader buffReader = new BufferedReader(csvFile);

			String line = null;

			while ((line = buffReader.readLine()) != null) {
				// each line of the file is one order
				String[] splitedStringArray = line.split(seperator.sepString);
				if (splitedStringArray.length > 0) {
					aList.add(splitedStringArray);
				}
			}

			buffReader.close();
			csvFile.close();

		} catch (Exception e) {
			return null;
		}

		return aList;
	}
}
