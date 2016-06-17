package shippingCore.model;

import java.util.List;

import shippingCore.calculations.time.TimeWindow;
import shippingCore.cost.ICosts;

public class Route {

	/** Unique identifier for this Route */
	private long routeId;

	private int nodeHeadingToOrAtIndex;

	/** The Time Window within which this route is valid */
	private TimeWindow timeLimit;

	/**
	 * The list of nodes which this route is made of. The first node is always
	 * the zero node
	 */
	private List<Node> nodes;

	/** The vehcile which services this Route */
	private transient Vehicle vehicle;

	/** Holds the real transportation costs */
	private ICosts transportationCosts;

	/**
	 * holding the costs caused by constraint violations (last calculateRoute)
	 */
	private ICosts constraintCosts;

	// /** If violations occur they will be added to this list */
	// private List<ConstraintViolation> violations;

	private boolean routeChanged;

}
