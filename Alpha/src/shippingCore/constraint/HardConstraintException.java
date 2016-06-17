package shippingCore.constraint;

public class HardConstraintException extends Exception {
	private static final long serialVersionUID = -5137044457112624195L;

	/** The object which caused the violation */
	private ConstraintViolation violation;

	/**
	 * Default constructor
	 */
	public HardConstraintException() {
		super();
	}

	/**
	 * stores the violation object that describes the violation
	 * 
	 * @param violation
	 *            a reference to the hard constraint violation object
	 */
	public HardConstraintException(final ConstraintViolation violation) {
		this.violation = violation;
	}

	/**
	 * returns the violation object that describes the violation
	 * 
	 * @return the violation this exception represents
	 */
	public ConstraintViolation getViolation() {
		return violation;
	}

	/**
	 * Returns a string representation of this object
	 * 
	 * @return a string representation of this object
	 */
	@Override
	public String toString() {
		if (violation != null) {
			return violation.toString();
		}

		return super.toString();
	}
}
