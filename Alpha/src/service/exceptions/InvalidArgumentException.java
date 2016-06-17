package service.exceptions;

/**
 * Exception to identify that a argument for a method is null or empty.
 */
public class InvalidArgumentException extends BaseRuntimeException {
	/** */
	private static final long serialVersionUID = -5090320640094935713L;

	/**
	 * Creates a <code>InvalidArgumentException</code> with no specified detail
	 * message.
	 */
	public InvalidArgumentException() {
		super("General invalid argument exception - no additional information available!");
	}

	/**
	 * Creates a <code>InvalidArgumentException</code> with the specified detail
	 * message.
	 * 
	 * @param description
	 *            The detail message.
	 */
	public InvalidArgumentException(final String description) {
		super(description);
	}

	/**
	 * Creates a <code>InvalidArgumentException</code> with the specified detail
	 * message and nested exception.
	 * 
	 * @param description
	 *            The detail message.
	 * @param throwable
	 *            The nested exception.
	 */
	public InvalidArgumentException(final String description,
			final Throwable throwable) {
		super(description, throwable);
	}
}
