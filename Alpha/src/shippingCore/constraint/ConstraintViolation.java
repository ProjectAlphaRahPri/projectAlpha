package shippingCore.constraint;

import java.io.Serializable;

import service.util.Tool;

/**
 * Class keeping the information of a hard constraint violation.
 * 
 * @author Last modified by $Author: kdo $
 * @version $Revision: 74397 $
 */
public class ConstraintViolation implements Serializable
{
	private static final long serialVersionUID = -7447893987778122370L;

	/** The object which caused the violation */
	private Object parent;

	/** error message for this exception object */
	private String errorMessage;

	/** contains the amount by which the constraint was violated, if sensible */
	private double violationAmount;

	/** the value at which hard constraint violations start */
	private double hardViolationStart;

	/**
	 * The route ID which is concerned to map the violation to the route on which
	 * the violation occured
	 */
	private long concerningRouteID;

	/** error code for this exception object */
	private int errorCode;

	/** flag that specifies if the violation is acceptable by manual dispatching */
	private boolean manualDispatchAcceptable = false;

	private final boolean isHardViolation;

	/**
	 * Creates a <code>ConstraintViolation</code> with the specified detail
	 * message.
	 * 
	 * @param description The detail message.
	 * @param errorCode the code identifying the constraint broken
	 * @param hardViolationStart the value at which hard constraint violations
	 *        start
	 * @param violationAmount the amount by which the violation was broken, 0.0
	 *        if amount does not make sense
	 * @param parent the object to which this constraint belongs
	 * @param concerningRouteID the route ID to which this constraint belongs
	 * @param isHardViolation
	 */
	public ConstraintViolation(final String description, final int errorCode,
			final double hardViolationStart, final double violationAmount, final Object parent,
			final long concerningRouteID, boolean isHardViolation)
	{
		this.errorMessage = description;
		this.errorCode = errorCode;
		this.hardViolationStart = hardViolationStart;
		this.violationAmount = violationAmount;
		this.parent = parent;
		this.concerningRouteID = concerningRouteID;
		this.isHardViolation = isHardViolation;
	}

	/**
	 * @param hcv
	 */
	public ConstraintViolation(ConstraintViolation hcv, Object parent)
	{
		this.parent = parent;
		this.errorMessage = new String(hcv.errorMessage);
		this.violationAmount = hcv.violationAmount;
		this.hardViolationStart = hcv.hardViolationStart;
		this.concerningRouteID = hcv.concerningRouteID;
		this.errorCode = hcv.errorCode;
		this.manualDispatchAcceptable = hcv.manualDispatchAcceptable;
		this.isHardViolation = hcv.isHardViolation;
	}

	/**
	 * @return Returns the hardViolationStart.
	 */
	public double getHardViolationStart()
	{
		return hardViolationStart;
	}

	/**
	 * Returns the error message for the constraint that was violated
	 * @return the error message for the constraint that was violated
	 */
	public String getErrorMessage()
	{
		return errorMessage;
	}

	/**
	 * Returns the error code for the constraint that was violated
	 * @return the error code for the constraint that was violated
	 */
	public int getErrorCode()
	{
		return errorCode;
	}

	/**
	 * Returns the amount by which the violation was broken, 0.0 if amount does
	 * not make sense
	 * @return the amount by which the violation was broken, 0.0 if amount does
	 *         not make sense
	 */
	public double getViolationAmount()
	{
		return violationAmount;
	}

	/**
	 * Returns the object that caused the violation
	 * @return the object that caused the violation
	 */
	public Object getParent()
	{
		return parent;
	}

	/**
	 * Returns the route ID which is concerned by this violation
	 * @return the route ID which is concerned by this violation
	 */
	public long getRouteID()
	{
		return concerningRouteID;
	}

	/**
	 * Returns a string representation of this object
	 * @return a string representation of this object
	 */
	@Override
	public String toString()
	{
		StringBuffer buffer = new StringBuffer(100);
		buffer.append(errorMessage);
		buffer.append(" Violation amount: " + violationAmount);
		buffer.append(" Violation object: " + parent);
		return buffer.toString();
	}

	/**
	 * Returns a string representation of this object not containing parent
	 * object
	 * @return a string representation of this object not containing parent
	 *         object
	 */
	public String toStringShort()
	{
		StringBuffer buffer = new StringBuffer(100);
		buffer.append(errorMessage);
		buffer.append(" Violation amount: " + violationAmount);
		return buffer.toString();
	}

	/**
	 * Sets flag that specifies if the violation is acceptable by manual
	 * dispatching.
	 * 
	 * @param manualDispatchAcceptable Flag that specifies if the violation is
	 *        acceptable by manual dispatching.
	 */
	public void setManualDispatchAcceptable(boolean manualDispatchAcceptable)
	{
		this.manualDispatchAcceptable = manualDispatchAcceptable;
	}

	/**
	 * @return Returns flag that specifies if the violation is acceptable by
	 *         manual dispatching.
	 */
	public boolean isManualDispatchAcceptable()
	{
		return manualDispatchAcceptable;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null) {
			return false;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}

		ConstraintViolation castObj = (ConstraintViolation) obj;
		return Tool.nullOrEqual(parent, castObj.parent)
				&& Tool.nullOrEqual(errorMessage, castObj.errorMessage)
				&& violationAmount == castObj.violationAmount
				&& hardViolationStart == castObj.hardViolationStart
				&& concerningRouteID == castObj.concerningRouteID && errorCode == castObj.errorCode
				&& manualDispatchAcceptable == castObj.manualDispatchAcceptable;
	}

	@Override
	public int hashCode()
	{
		return Tool.hashcodeOrZero(parent) ^ Tool.hashcodeOrZero(errorMessage)
				^ (int) violationAmount ^ (int) hardViolationStart ^ (int) concerningRouteID
				^ errorCode ^ (manualDispatchAcceptable ? 64 : 8);
	}
}
