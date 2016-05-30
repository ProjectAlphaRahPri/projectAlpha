package service.dateAndTime;

public class DateFormatterException extends FormatterException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5682023343245277155L;

	/**
	 * Creates a <code>DateFormatterException</code> with no specified detail
	 * message.
	 */
	public DateFormatterException()
	{
		super("Formatter Exception - no additional information available!");
	}

	/**
	 * Creates a <code>DateFormatterException</code> with the specified detail
	 * message.
	 * 
	 * @param description The detail message.
	 */
	public DateFormatterException(String description)
	{
		super(description);
	}

	/**
	 * Creates a <code>DateFormatterException</code> with the specified detail
	 * message and nested exception.
	 * 
	 * @param description The detail message.
	 * @param throwable The nested exception.
	 */
	public DateFormatterException(String description, Throwable throwable)
	{
		super(description, throwable);
	}
}
