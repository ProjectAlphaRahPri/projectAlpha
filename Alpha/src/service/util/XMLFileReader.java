/**
 * 
 */
package service.util;

//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileReader;

//import com.thoughtworks.xstream.XStream;
//import com.thoughtworks.xstream.converters.Converter;
//import com.thoughtworks.xstream.io.xml.DomDriver;
//import com.thoughtworks.xstream.mapper.MapperWrapper;

/**
 * @author Srinivasa Ragavan
 * 
 */
public class XMLFileReader
{
//	XStream xStream;
//
//	public XMLFileReader()
//	{
//		// Borrowed from http://pvoss.wordpress.com/2009/01/08/xstream/
//		// to allow loading older versions of classes.
//		// If the xml-serialized file contains more information than the actual
//		// class definition (some member fields were removed of that class)
//		// then XStream usually throws an exception - which is not wanted in
//		// some cases (e.g. by loading result-files). This hack prevents XStream
//		// from throwing such exceptions.
//		this.xStream = new XStream(new DomDriver()) {
//			@Override
//			protected MapperWrapper wrapMapper(MapperWrapper next)
//			{
//				return new MapperWrapper(next) {
//					@Override
//					public boolean shouldSerializeMember(@SuppressWarnings("rawtypes") Class definedIn,
//							String fieldName)
//					{
//						if (definedIn == Object.class) {
//							return false;
//						}
//						return super.shouldSerializeMember(definedIn, fieldName);
//					}
//				};
//			}
//		};
//	}
//
//	@SuppressWarnings("unchecked")
//	public <T> T readFromXML(String fileSource, String parentTag, String canonicalClassName)
//	{
//		T object = null;
//		File inFile = new File(fileSource);
//
//		try {
//			xStream.alias(parentTag, Class.forName(canonicalClassName));
//			object = (T) xStream.fromXML(new FileReader(inFile));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//		return object;
//	}
//
//	public void registerConverter(Converter conv)
//	{
//		this.xStream.registerConverter(conv);
//	}
//
//	public void registerAlias(String name, Class<? extends Object> type)
//	{
//		xStream.alias(name, type);
//	}
}
