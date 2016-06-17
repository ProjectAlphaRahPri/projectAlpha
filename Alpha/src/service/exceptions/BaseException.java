package service.exceptions;

import java.io.PrintWriter;
import java.io.StringWriter;

import service.properties.IConstants;

/**
 * Systems base checked exception for all other checked exceptions.
 * 
 */
public class BaseException extends Exception {
	/** */
	private static final long serialVersionUID = 3850577893248579044L;

	/**
	 * Nested throwable, which is usually the reason that caused raising the
	 * <code>BaseException</code>.
	 */
	protected Throwable innerThrowable = null;

	/**
	 * Creates a <code>BaseException</code> with no specified detail message.
	 */
	public BaseException() {
		super("General living systems checked exception - no additional information available!");
	}

	/**
	 * Creates a <code>BaseException</code> with the specified detail message.
	 * 
	 * @param description
	 *            the reason for the exception
	 */
	public BaseException(final String description) {
		super(description);
	}

	/**
	 * Creates a <code>BaseException</code> with the specified detail message
	 * and nested exception.
	 * 
	 * @param description
	 *            the reason for the exception
	 * @param throwable
	 *            the nested Throwable which caused the exception
	 */
	public BaseException(final String description, final Throwable throwable) {
		super(description, throwable);
		this.innerThrowable = throwable;
	}

	/**
	 * @return Well formatted detail message, including the message from the
	 *         nested exception if there is one.
	 */
	@Override
	public String getMessage() {
		if (innerThrowable == null) {
			return super.getMessage();
		}

		// else ...
		final StringBuffer message = new StringBuffer(
				IConstants.DEFAULT_STRING_BUFFER_SIZE);

		if (super.getMessage() != null) {
			message.append(super.getMessage());
		}
		message.append("\nnested exception is:\n\t");
		message.append(innerThrowable.toString());
		if (innerThrowable instanceof NullPointerException) {
			NullPointerException npe = (NullPointerException) innerThrowable;
			StringWriter w = new StringWriter();
			npe.printStackTrace(new PrintWriter(w));
			message.append("\nNested NPE stack trace:\n");
			message.append(w.toString());
		}

		return message.toString();
	}

	/**
	 * @return Short reason description as returned by
	 *         {@link Exception#getMessage()}. <em>Note:</em> If there is a
	 *         nested throwable, this is <em>not</em> mentioned in the short
	 *         message!
	 */
	public String getShortMessage() {
		return super.getMessage();
	}

	/**
	 * Prints the composite message to <code>System.err</code>.
	 */
	@Override
	public void printStackTrace() {
		printStackTrace(System.err);
	}

	/**
	 * @return the nested throwable
	 */
	public Throwable getNestedThrowable() {
		return this.innerThrowable;
	}
}
