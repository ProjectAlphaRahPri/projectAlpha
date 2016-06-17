package service.exceptions;

/**
 * Systems base runtime exception for all other runtime exceptions.
 * 
 */
public class BaseRuntimeException extends RuntimeException {
	/** */
	private static final long serialVersionUID = 6483248795617712779L;

	/**
	 * Nested throwable, which is usually the reason that caused raising the
	 * <code>BaseRuntimeException</code>.
	 */
	protected Throwable innerThrowable = null;

	/**
	 * Creates a <code>BaseRuntimeException</code> with no specified detail
	 * message.
	 */
	public BaseRuntimeException() {
		super("General living systems runtime exception - no additional information available!");
	}

	/**
	 * Creates a <code>BaseRuntimeException</code> with the specified detail
	 * message.
	 * 
	 * @param description
	 *            the reason for the exception
	 */
	public BaseRuntimeException(final String description) {
		super(description);
	}

	/**
	 * Creates a <code>BaseRuntimeException</code> with the specified detail
	 * message and nested exception.
	 * 
	 * @param description
	 *            the reason for the exception
	 * @param throwable
	 *            the nested Throwable which caused the exception
	 */
	public BaseRuntimeException(final String description,
			final Throwable throwable) {
		super(description, throwable);
		this.innerThrowable = throwable;
	}

	/**
	 * @return Short reason description as returned by
	 *         {@link Throwable#getMessage()}. <em>Note:</em> If there is a
	 *         nested throwable, this is <em>not</em> mentioned in the short
	 *         message!
	 */
	public String getShortMessage() {
		return super.getMessage();
	}

	/**
	 * @return the nested throwable
	 */
	public Throwable getNestedThrowable() {
		return this.innerThrowable;
	}
}
