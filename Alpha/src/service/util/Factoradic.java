/**
 * 
 */
package service.util;

/**
 * A conversion from decimal number system to factorial number system. <br>
 * Maximum is base 9 in Factoradic(Factorial Number System)
 * @author Srinivasa Ragavan
 */
public class Factoradic
{
	/**
	 * Does not work for base > 9. We run out of decimal digits for a factorial
	 * number with base > 9
	 * @param decimalNumber - the number that has to be converted to factoradic.
	 *        The <tt>decimalNumber < factoradicBase!</tt> (! is factorial)
	 * @param factoradicBase - the base of the factoradic number. If there are
	 *        'n' permuting/permutating elements then the factoradic base is also
	 *        'n'.
	 * @return int - factoradic number
	 * @throws Exception
	 */
	public static int[] getFactoradic(int decimalNumber, int factoradicBase) throws Exception
	{
		int fact = factorial(factoradicBase) - 1;
		if (decimalNumber > fact) {
			throw new Exception("Improper values: (decimalNumber > factoradicBase!-1) ("
					+ decimalNumber + ">" + fact + ")");
		}
		// using integer arrays because the other datatypes remove the leading
		// zeros
		int[] factoradic = new int[factoradicBase];

		// the last position is always 0, so i>0
		for (int i = factoradicBase - 1; i > 0; i--) {
			int factorial = factorial(i);
			int positionValue = decimalNumber / factorial;
			// the quotient is the value at position i
			factoradic[factoradicBase - i - 1] = positionValue;

			// (number - remainder) is the new number
			decimalNumber -= (positionValue * factorial);
		}

		return factoradic;
	}

	/**
	 * Integer based implementation. Do not use for n>12.
	 * @param n
	 * @return
	 */
	public static int factorial(int n)
	{
		if (n == 0) {
			return 1;
		}
		return n * factorial(n - 1);
	}
}
