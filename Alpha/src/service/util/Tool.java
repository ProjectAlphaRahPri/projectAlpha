package service.util;

/**
 * @author Srinivasa Ragavan
 * 
 */
public class Tool
{
	/**
	 * Check whether both values are equal or both null
	 * @param obj1
	 * @param obj2
	 * @return <code>true</code>, if both are <code>null</code> or both are equal
	 */
	public static boolean nullOrEqual(Object obj1, Object obj2)
	{
		return (obj1 == null && obj2 == null) || (obj1 != null && obj1.equals(obj2));
	}

	/**
	 * @param obj Object for which to get hashCode
	 * @return hashcode of the object, or 0 if it is null
	 */
	public static int hashcodeOrZero(Object obj)
	{
		return obj != null ? obj.hashCode() : 0;
	}
}