package shippingCore.constraint;

import shippingCore.IConstants;
import shippingCore.model.Route;

/**
 * Interface defining the basic behavior of constraints
 * 
 * @author Srinivasa Ragavan
 */
public interface IConstraint
{
	/*
	 * BEGIN Constants - Error codes, Basic costs and others
	 */
	/** return code for no error */
	public final static int NO_ERROR = 0;

	/** error code for an order not matching the transportation type */
	public final static int ORDER_TYPE_MISMATCH = 100;

	/** error code for an order not matching the transportation subtype */
	public final static int ORDER_SUBTYPE_MISMATCH = 102;

	/** error code for an adr order not matching the trucks adr equipment */
	public final static int ORDER_TRUCK_ADR_MISMATCH = 105;

	/** error code for an international order on a national truck */
	public final static int ORDER_TRUCK_INTERNATIONAL_MISMATCH = 108;

	/** error code for an overloaded truck (weight) */
	public final static int TRUCK_OVERLOAD_WEIGHT = 110;

	/**
	 * error code for a route marked as full but has additional loading meters
	 * added
	 */
	public final static int TRUCK_FULL_VIOLATION = 112;

	/** error code for an overloaded truck (loading meters) */
	public final static int TRUCK_OVERLOAD_LDM = 115;

	/** error code for an overloaded truck (loading meters) */
	public final static int ORDER_TOO_LONG_FOR_TRUCK = 116;

	/** error code for an overloaded truck (loading meters) */
	public final static int ORDER_TOO_HIGH_FOR_TRUCK = 117;

	/** error code for an overloaded truck (loading meters) */
	public final static int ORDER_TOO_WIDE_FOR_TRUCK = 118;

	/** error code for a truck that is not returned to home too long */
	public final static int TRUCK_RETURNED_TOO_LATE = 120;

	/** error code for a route that would start too early */
	public final static int ROUTE_STARTED_TOO_EARLY = 130;

	/** error code for a route that would end too late */
	public final static int ROUTE_ENDED_TOO_LATE = 140;

	/** error code if a truck is on the negative list of the order */
	public final static int TRUCK_ON_NEGATIVE_LIST_OF_ORDER = 150;

	/** error code for early pickup violation */
	public final static int EARLIEST_PICKUP_TIME_VIOLATION = 200;

	/** error code for latest pickup violation */
	public final static int LATEST_PICKUP_TIME_VIOLATION = 210;

	/** error code for early delivery violation */
	public final static int EARLIEST_DELIVERY_TIME_VIOLATION = 220;

	/** error code for latest delivery violation */
	public final static int LATEST_DELIVERY_TIME_VIOLATION = 230;

	/** error code for staying too long at a node */
	public final static int STAY_AT_NODE_TIME_VIOLATION = 240;

	/** error code for arriving too late at a node */
	public final static int TOO_LATE_AT_NODE = 250;

	/** error code for arriving too early at a node */
	public final static int TOO_EARLY_AT_NODE = 260;

	/** error code for arriving too early at a node */
	public final static int NO_TIME_AT_NODE = 270;

	/** requested delivery or pickup is outside earliest-latest delivery pickup */
	public final static int ORDER_TIMES_INCONSISTENT = 280;

	/** error code for too much empty kilometers in a leg */
	public final static int LEG_MAX_EMPTY_KILOMETER_VIOLATION = 300;

	/** error code for combined loading not allowed */
	public final static int COMBINED_LOADING_NOT_ALLOWED = 310;

	/** error code for LIFO violation */
	public final static int LIFO_VIOLATION = 320;

	/** error code for violation of pickup before delivery order */
	public final static int PICKUP_DELIVERY_VIOLATION = 325;

	/** error code for a to long tour violation */
	public final static int TOUR_TOO_LONG_VIOLATION = 330;

	/** error code for a routes overlap violation */
	public final static int ROUTES_FREEZED_VIOLATION = 340;

	/** error code for a routes overlap violation */
	public final static int ROUTES_FIXED_VIOLATION = 341;

	/** error code for a routes overlap violation */
	public final static int ROUTES_USAGE_VIOLATION = 342;

	/** error code for a routes overlap violation */
	public final static int ROUTES_OVERLAP_VIOLATION = 343;

	/** error code for a real capacity switched violation */
	public final static int REAL_CAPACITY_SWITCHED_VIOLATION = 350;

	/** error code for a real capacity switched violation */
	public final static int AVAILABILITY_TIME_LEFT_SIDE_CONSTRAINT_VIOLATION = 360;

	/** error code for a real capacity switched violation */
	public final static int AVAILABILITY_TIME_RIGHT_SIDE_CONSTRAINT_VIOLATION = 365;

	/** error code for removing an order during or after execution */
	public final static int DEALLOCATED_ORDER_IN_EXECUTION = 400;

	/** error code for compatibility check of loaded orders */
	public final static int LOADED_ORDERS_COMPATIBILITY_CONSTRAINT_VIOLATION = 500;

	/** error code for compatibility check of loaded order with vehicle */
	public final static int LOADED_ORDER_AND_VEHICLE_COMPATIBILITY_CONSTRAINT_VIOLATION = 510;

	/** error code for compatibility check of loaded order with trip */
	public final static int LOADED_ORDER_AND_TRIP_COMPATIBILITY_CONSTRAINT_VIOLATION = 520;

	/** error code for compatibility check of trip with vehicle */
	public final static int TRIP_AND_VEHICLE_COMPATIBILITY_CONSTRAINT_VIOLATION = 530;

	/** error code for compatibility check of trip with vehicle */
	public final static int FROZEN_PROPOSAL_MANIPULATION_VIOLATION = 540;

	/** fix cost for an order not matching the transportation type */
	public final static double ORDER_TYPE_MISMATCH_FIXCOST = 1e9;

	/** fix cost for an order not matching the transportation subtype */
	public final static double ORDER_SUBTYPE_MISMATCH_FIXCOST = 1e9;

	/** fix cost for an overloaded truck (weight) */
	public final static double TRUCK_OVERLOAD_WEIGHT_FIXCOST = 1e15;

	/** fix cost for an overloaded truck (weight) */
	public final static double TRUCK_FULL_VIOLATION_FIXCOST = 1.5e15;

	/** fix cost for an overloaded truck (loading meters) */
	public final static double TRUCK_OVERLOAD_LDM_FIXCOST = 1e14;

	/** fix cost for a truck that is not returned to home too long */
	public final static double TRUCK_RETURNED_TOO_LATE_FIXCOST = 1e4;

	/** fix cost for a route that would start too early */
	public final static double ROUTE_STARTED_TOO_EARLY_FIXCOST = 1e7;

	/** fix cost for a route that would end too late */
	public final static double ROUTE_ENDED_TOO_LATE_FIXCOST = 1e7;

	/** fix cost if a truck is on the negative list of the order */
	public final static double TRUCK_ON_NEGATIVE_LIST_OF_ORDER_FIXCOST = 1e5;

	/** fix cost for early pickup violation */
	public final static double EARLIEST_PICKUP_TIME_VIOLATION_FIXCOST = 1e11;

	/** variable cost for early pickup violation per minute */
	public final static double EARLIEST_PICKUP_TIME_VIOLATION_VARIABLECOST = 1.0 / IConstants.MINUTE;

	/** fix cost for latest pickup violation */
	public final static double LATEST_PICKUP_TIME_VIOLATION_FIXCOST = 1e12;

	/** variable cost for latest pickup violation per minute */
	public final static double LATEST_PICKUP_TIME_VIOLATION_VARIABLECOST = 1.0 / IConstants.MINUTE;

	/** fix cost for early delivery violation */
	public final static double EARLIEST_DELIVERY_TIME_VIOLATION_FIXCOST = 1e11;

	/** variable cost for earliest delivery violation per minute */
	public final static double EARLIEST_DELIVERY_TIME_VIOLATION_VARIABLECOST = 1.0 / IConstants.MINUTE;

	/** fix cost for latest delivery violation */
	public final static double LATEST_DELIVERY_TIME_VIOLATION_FIXCOST = 1e12;

	/** variable cost for latest delivery violation per minute */
	public final static double LATEST_DELIVERY_TIME_VIOLATION_VARIABLECOST = 1.0 / IConstants.MINUTE;

	/** fix cost for staying too long at a node */
	public final static double STAY_AT_NODE_TIME_VIOLATION_FIXCOST = 1e7;

	/** fix cost for arriving too late at a node */
	public final static double TOO_LATE_AT_NODE_FIXCOST = 1e13;

	/** fix cost for arriving too early at a node */
	public final static double TOO_EARLY_AT_NODE_FIXCOST = 1e11;

	/** fix cost for arriving too early at a node */
	public final static double NO_TIME_AT_NODE_FIXCOST = 1e4;

	/** fix cost for too much empty kilometers in a leg */
	public final static double LEG_MAX_EMPTY_KILOMETER_VIOLATION_FIXCOST = 1e8;

	/** fix cost for combined loading not allowed */
	public final static double COMBINED_LOADING_NOT_ALLOWED_FIXCOST = Double.MAX_VALUE;

	/** fix cost for LIFO violation */
	public final static double LIFO_VIOLATION_FIXCOST = 1e6;

	/** fix cost for pickup after deivery violation */
	public final static double PICKUP_DELIVERY_VIOLATION_FIXCOST = 1e20;

	/** fix cost for a too long tour violation */
	public final static double TOUR_TOO_LONG_VIOLATION_FIXCOST = 1e7;

	/** variable cost for a too long tour violation (units per minute) */
	public final static double TOUR_TOO_LONG_VIOLATION_VARIABLECOST = 5;

	/** fix cost penalty for noncompatible loaded orders */
	public final static double LOADED_ORDERS_COMPATIBILITY_CONSTRAINT_VIOLATION_FIXCOST = 1e15;

	/** fix cost penalty for noncompatible loaded order and vehicle */
	public final static double LOADED_ORDER_AND_VEHICLE_COMPATIBILITY_CONSTRAINT_VIOLATION_FIXCOST = 1e15;

	/** fix cost penalty for noncompatible loaded order and capacity */
	public final static double LOADED_ORDER_AND_TRIP_COMPATIBILITY_CONSTRAINT_VIOLATION_FIXCOST = 1e15;

	/** fix cost penalty for noncompatible trip and vehicle */
	public final static double TRIP_AND_VEHICLE_COMPATIBILITY_CONSTRAINT_VIOLATION_FIXCOST = 1e15;

	/*
	 * END Constants
	 */

	/**
	 * Called to check the passed value against this constraint.
	 * @param nodeIndex - index of the node on which to check the constraint.
	 *        Each Constraint decides how to use the index (<b>from/to/on</b> the
	 *        <tt>nodeIndex</tt>.)
	 * @param value - the value that is checked if it satisfies this constraint.
	 * @param parent - the route/order to which this constraint belongs
	 * @param route - the route to which this constraint belongs
	 * @return double 0.0 if the constraint is not violated, a cost value if the
	 *         constraint is violated
	 * @throws HardConstraintException
	 */
	public double check(int nodeIndex, final double value, final Object parent, final Route route)
			throws HardConstraintException;

	/**
	 * To get violation boundaries/limits for each parent Object. It is the
	 * border up to (if isUpward is true) or down to (if isUpward is false) which
	 * the constraint is not violated (border not including).
	 * 
	 * @param parent the parent Object on whom the constraints are checked
	 */
	public double getViolationStart(Object parent);

	/**
	 * @return double - the fix costs that should be added as soon as the
	 *         constraint is violated
	 */
	public double getBaseCostForViolation();

	public boolean isSoftViolationAllowed(Object parent);

}
