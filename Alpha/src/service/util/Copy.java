/**
 * 
 */
package service.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Utility for copying serialized objects
 */
public class Copy {

	public static <T> T deepCopy(T obj)
			throws IOException, ClassNotFoundException {
		/** Results are for copying a Route 10.000 times. Time is in ms */

		/**
		 * Best Result - 15ms. By using copy constructor everywhere, in it's
		 * true sense :-) Below results are for using serialization in all/some
		 * places.
		 */

		// return normalCopy(obj); //-slow
		// plain serialization - 32422,
		// with copy cons in Route and serialization for nodes - 3094

		// return deepCopyWithAnnotations(obj); //- faster
		// plain Serialization
		// with corba.osc - 32422, with io.osc - 32125
		// with copy cons in Route and serialization for nodes - 2422

		// return deepCopyWithFastByteArray(obj); //- fast
		// plain Serialization - 32313,
		// with copy cons in Route and serialization for nodes - 2798

		return deepCopyWithFBArrayAndAnnotations(obj); // - fastest
		// plain Serialization with corba.osc 32281, with io.osc 31890
		// with copy cons in Route and serialization for nodes - 2203

	}

	/**
	 * Creates a deepCopy of an object by serialization and deserialization.
	 * Objects should implement java.io.Serializable.
	 * 
	 * @param <T>
	 * @param sourceObject
	 *            object to be copied
	 * @return copy of the sourceObject. Will not contain transient and static
	 *         fields
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T normalCopy(final T sourceObject)
			throws IOException, ClassNotFoundException

	{
		T result = null;

		// serialize
		ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
		ObjectOutputStream out = new ObjectOutputStream(bos);
		out.writeObject(sourceObject);

		out.flush();
		out.close();

		// de-serialize
		ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
		ObjectInputStream in = new ObjectInputStream(bin);

		try {
			result = (T) in.readObject();
		} catch (ClassNotFoundException cnfe) {
			throw new IOException(cnfe);
		} finally {
			in.close();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public static <T> T deepCopyWithFastByteArray(T orig) {
		T result = null;
		try {
			// Write the object out to a byte array
			FastByteArrayOutputStream fbos = new FastByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(fbos);
			out.writeObject(orig);
			out.flush();
			out.close();

			// Retrieve an input stream from the byte array and read
			// a copy of the object back in.
			ObjectInputStream in = new ObjectInputStream(fbos.getInputStream());
			result = (T) in.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		return result;
	}

	/** with annotations */

	public static <T> T deepCopyWithAnnotations(T x)
			throws IOException, ClassNotFoundException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		CloneOutput cout = new CloneOutput(bout);
		cout.writeObject(x);
		byte[] bytes = bout.toByteArray();

		ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
		CloneInput cin = new CloneInput(bin, cout);

		@SuppressWarnings("unchecked")
		T clone = (T) cin.readObject();
		return clone;
	}

	public static <T> T deepCopyWithFBArrayAndAnnotations(T x)
			throws IOException, ClassNotFoundException {
		// ByteArrayOutputStream bout = new ByteArrayOutputStream();
		FastByteArrayOutputStream bout = new FastByteArrayOutputStream();
		CloneOutput cout = new CloneOutput(bout);
		cout.writeObject(x);

		CloneInput cin = new CloneInput(bout.getInputStream(), cout);

		@SuppressWarnings("unchecked")
		T clone = (T) cin.readObject();
		return clone;
	}

	private static class CloneOutput extends ObjectOutputStream {
		Queue<Class<?>> classQueue = new LinkedList<Class<?>>();

		CloneOutput(OutputStream out) throws IOException {
			super(out);
		}

		@Override
		protected void annotateClass(Class<?> c) {
			classQueue.add(c);
		}

		@Override
		protected void annotateProxyClass(Class<?> c) {
			classQueue.add(c);
		}
	}

	private static class CloneInput extends ObjectInputStream {
		private final CloneOutput output;

		CloneInput(InputStream in, CloneOutput output) throws IOException {
			super(in);
			this.output = output;

		}

		@Override
		protected Class<?> resolveClass(ObjectStreamClass desc)
				throws IOException, ClassNotFoundException {
			Class<?> c = output.classQueue.poll();
			String expected = desc.getName();
			String found = (c == null) ? null : c.getName();
			if (!expected.equals(found)) {
				throw new InvalidClassException("Classes desynchronized: "
						+ "found " + found + " when expecting " + expected);
			}
			return c;
		}

		@Override
		protected Class<?> resolveProxyClass(String[] interfaceNames)
				throws IOException, ClassNotFoundException {
			return output.classQueue.poll();
		}
	}

}
