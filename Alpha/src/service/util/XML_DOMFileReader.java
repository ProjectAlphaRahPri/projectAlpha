package service.util;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

/**
 * Util class to read xml files.
 * 
 * @author Stefan Glaser
 */
public class XML_DOMFileReader
{
	/**
	 * @param fileSource - the file to read
	 * @return a document reference
	 */
	public static Document readFromXML(String fileSource)
	{
		Document document;

		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			document = builder.parse(new File(fileSource));
			document.normalize();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return document;
	}
}
