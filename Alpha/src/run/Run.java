package run;

import framework.problem.IProblem;
import representations.ShippingProblem;
import service.properties.SystemProperties;

public class Run {

	private static IProblem problem;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		/*
		 * - Read properties from args -
		 */
		String userPropertiesFile = args[0];
		SystemProperties.loadUserProperties(userPropertiesFile);

		/*
		 * Initialise Problem,
		 */
		createShippingProblem();

		/*
		 * State, Operators, Algorithm & Optimisation techniques - Readand load
		 * problem data - Run Algorithm
		 */

	}

	private static void createShippingProblem() {
		// TODO Auto-generated method stub
		problem = new ShippingProblem(null);

	}

}
