/**
 * 
 */
package service.util;

/**
 * Class for Combinatorial Number System or Combinadic. Implements methods that
 * count combinations and performs decimal-combinadic conversions.
 * @author Srinivasa Ragavan
 */
public class Combinadic
{

	/**
	 * @param k
	 * @param n
	 * @return int - number of possible 'k' combinations of 'n' elements<br>
	 *         TODO method is not efficient
	 */
	public static int numberOfCombinations(int k, int n)
	{
		if (k == 0) {
			return 0;
		}
		int pdt = 1;
		for (int i = 0; i < k; i++) {
			pdt *= (n - i);
		}
		return pdt / (Factoradic.factorial(k));
	}

	/**
	 * @param decimal - index of the combination in lexicographic order
	 * @param k - number of combining elements
	 * @param n - total number of elements
	 * @return int[] - combinadic
	 * @see <a href= http://en.wikipedia.org/wiki/Combinatorial_number_system
	 *      >Combinatorial Number System</a>
	 */
	public static int[] getCombinadicFromDecimal(int decimal, int k, int n)
	{
		int[] combinadic = new int[k];

		// I really don't know how to comment the code in the loop. But if you
		// take a look at
		// http://en.wikipedia.org/wiki/Combinatorial_number_system,
		// things should be clear enough.
		for (int i = n - 1; i >= 0 && k > 0; i--) {
			int number = numberOfCombinations(k, i);
			if (number <= decimal) {
				decimal -= number;
				k--;
				combinadic[k] = i;
			}
		}
		return combinadic;
	}
}