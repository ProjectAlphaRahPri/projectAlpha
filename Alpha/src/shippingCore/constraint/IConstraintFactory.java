package shippingCore.constraint;

/**
 * Factory from which all constraints, both soft and hard are accessible.
 */
public interface IConstraintFactory {
	/**
	 * @return the capacityTypeConstraint
	 */
	public abstract IConstraint getCapacityTypeConstraint();

	/**
	 * @return the maxEmptyKilometersConstraint
	 */
	public abstract IConstraint getMaxEmptyKilometersConstraint();

	/**
	 * @return the lifoConstraint
	 */
	public abstract IConstraint getLifoConstraint();

	/**
	 * @return the loadUnloadSequenceConstraint
	 */
	public abstract IConstraint getLoadUnloadSequenceConstraint();

	/**
	 * @return the earliestDeliveryTimeConstraint
	 */
	public abstract IConstraint getEarliestDeliveryTimeConstraint();

	/**
	 * @return the earliestPickupTimeConstraint
	 */
	public abstract IConstraint getEarliestPickupTimeConstraint();

	/**
	 * @return the latestDeliveryTimeConstraint
	 */
	public abstract IConstraint getLatestDeliveryTimeConstraint();

	/**
	 * @return the latestPickupTimeConstraint
	 */
	public abstract IConstraint getLatestPickupTimeConstraint();

	/**
	 * @return the routeTooEarlyConstraint
	 */
	public abstract IConstraint getRouteTooEarlyConstraint();

	/**
	 * @return the routeTooLateConstraint
	 */
	public abstract IConstraint getRouteTooLateConstraint();

	/**
	 * @return the utilizationConstraint
	 */
	public abstract IConstraint getUtilizationConstraint();

	/**
	 * @return the weightConstraint
	 */
	public abstract IConstraint getWeightConstraint();

	/**
	 * @return the waitTimeConstraint
	 */
	public abstract IConstraint getWaitTimeConstraint();

	/**
	 * @return the tourPickupOverlapConstraint
	 */
	public abstract IConstraint getTourPickupOverlapConstraint();

}