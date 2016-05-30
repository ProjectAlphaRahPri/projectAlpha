/**
 * 
 */
package service.util;

//import java.io.File;
//import java.io.FileWriter;
//
//import com.thoughtworks.xstream.XStream;
//import com.thoughtworks.xstream.converters.Converter;
//import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author Srinivasa Ragavan
 * 
 */
public class XMLFileWriter
{
//	XStream xstream;
//
//	public XMLFileWriter()
//	{
//		xstream = new XStream(new DomDriver());
//	}
//
//	/**
//	 * @param fileDestination - destination where to write the file
//	 * @param parentTag - the name of the parentTag
//	 * @param objectToWrite - the object that has to be serialized
//	 */
//	public boolean writeToXMLFile(String fileDestination, String parentTag, Object objectToWrite)
//	{
//		xstream.alias(parentTag, objectToWrite.getClass());
//
//		String xmlString = xstream.toXML(objectToWrite);
//
//		File outputFile = null;
//		FileWriter outputFileWriter = null;
//
//		try {
//			outputFile = new File(fileDestination);
//			outputFileWriter = new FileWriter(outputFile);
//			outputFileWriter.write(xmlString);
//			outputFileWriter.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//
//		return true;
//	}
//
//	public void registerConverter(Converter converter)
//	{
//		xstream.registerConverter(converter);
//	}
//
//	public void registerAlias(String name, Class<? extends Object> type)
//	{
//		xstream.alias(name, type);
//	}
}
