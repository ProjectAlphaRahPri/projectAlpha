package service.dateAndTime;

import service.exceptions.BaseException;

public class FormatterException extends BaseException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8816479452600988064L;

	/**
	 * Creates a <code>FormatterException</code> with no specified detail
	 * message.
	 */
	public FormatterException()
	{
		super("Formatter Exception - no additional information available!");
	}

	/**
	 * Creates a <code>FormatterException</code> with the specified detail
	 * message.
	 * 
	 * @param description The detail message.
	 */
	public FormatterException(String description)
	{
		super(description);
	}

	/**
	 * Creates a <code>FormatterException</code> with the specified detail
	 * message and nested exception.
	 * 
	 * @param description The detail message.
	 * @param throwable The nested exception.
	 */
	public FormatterException(String description, Throwable throwable)
	{
		super(description, throwable);
	}
}
