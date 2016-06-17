
package service.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public final class Format {
	/** for proper cost formatting */
	public static final DecimalFormat costFormatter;

	/** for proper double formatting */
	public static final DecimalFormat doubleFormatter;

	/** for date/time formatting */
	public static final SimpleDateFormat dateFormatter;

	static {
		costFormatter = (DecimalFormat) NumberFormat
				.getNumberInstance(Locale.US);
		costFormatter.applyPattern("0.00");
		doubleFormatter = (DecimalFormat) NumberFormat
				.getNumberInstance(Locale.US);
		doubleFormatter.applyPattern("0.00");
		dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	}
}
