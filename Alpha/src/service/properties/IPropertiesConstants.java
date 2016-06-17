package service.properties;

public interface IPropertiesConstants {
	String OPTIMIZEFROM_PROBLEM_DATA = "problemData";

	String OPTIMIZEFROM_RESULT_FILE = "resultFile";

	// DAO Properties -------------------------------------------------------
	// ----------------------------------------------------------------------
	// Capacity DAO definition
	String DATA_DAO_DATABASE = "database";

	String DATA_DAO_XML = "xml";

	String DATA_DAO_BENCHMARK = "benchmark";

	// DAO wrapper definition
	String DATA_DAO_WRAPPER_OWN = "own";

	String DATA_DAO_WRAPPER_ALL = "all";

	// Order DAO definition
	String DATA_DAO_CONSIGNMENT_GENERATOR = "generator";

	// DAO Connectipn IP Address
	String DATA_DAO_IPAddress = "localhost";

	// Distance Properties
	// -------------------------------------------------------
	// ----------------------------------------------------------------------
	// distance source
	String DATA_DISTANCESOURCE_BENCHMARK = "benchmark";

	String DATA_DISTANCESOURCE_EWS = "ews";

	String DATA_DISTANCESOURCE_DIMABIGENDIAN = "dimaBigEndian";

	// Allocation Properties ------------------------------------------------
	// ----------------------------------------------------------------------
	// AllocationStrategy
	String ALLOCATION_ROUTEALLOCATOR_STRATEGY_SHORTESTDISTANCE = "shortestDistance";

	String ALLOCATION_ROUTEALLOCATOR_STRATEGY_CHEAPESTPERMUTATION = "cheapestPermutation";

	String ALLOCATION_ROUTEALLOCATOR_STRATEGY_ALLOCATE_AT_END = "allocateAtEnd";

	// CostModel
	// ->fixVariable | CostModelFixVariable (default)
	// ->matrix | CostModelMatrx
	String ALLOCATION_ROUTEALLOCATOR_COSTMODEL_FIXVARIABLE = "fixVariable";

	String ALLOCATION_ROUTEALLOCATOR_COSTMODEL_MATRIX = "matrix";

	// RouteAllocation
	String ALLOCATION_ROUTEALLOCATOR_EXISTINGTRUCK = "existingTruck";

	String ALLOCATION_ROUTEALLOCATOR_VIRTUALTRUCK = "virtualTruck";

	String ALLOCATION_ROUTEALLOCATOR_TRAIN = "train";

	String ALLOCATION_ROUTEALLOCATOR_SPECIFICTRUCK = "specificTruck";

	String ALLOCATION_ROUTEALLOCATOR_CHERRYPICKING = "cherryPicking";

	// Optimization Properties ----------------------------------------------
	// ----------------------------------------------------------------------
	// Search Strategy
	String OPTIMIZATION_LOCALSEARCHSTRATEGY_NOSEARCH = "noSearch";

	String OPTIMIZATION_LOCALSEARCHSTRATEGY_HILLCLIMBING = "hillClimbing";

	String OPTIMIZATION_LOCALSEARCHSTRATEGY_DEC_HILLCLIMBING = "decHillClimbing";

	String OPTIMIZATION_LOCALSEARCHSTRATEGY_HC_RANDOMSTART = "hcRandomRestart";

	String OPTIMIZATION_LOCALSEARCHSTRATEGY_HC_RANDOM = "hcRandom";

	String OPTIMIZATION_LOCALSEARCHSTRATEGY_GRADIENT = "gradient";

	String OPTIMIZATION_LOCALSEARCHSTRATEGY_K_OPT = "kOpt";

	String OPTIMIZATION_LOCALSEARCHSTRATEGY_SIMULATEDANNEALING = "simulatedAnnealing";

	String OPTIMIZATION_LOCALSEARCHSTRATEGY_TABU = "tabu";

	String OPTIMIZATION_LOCALSEARCHSTRATEGY_DEC_TABU = "decTabu";

	String OPTIMIZATION_LOCALSEARCHSTRATEGY_RANDOM = "random";

	// Cooling schedule
	String OPTIMIZATION_SIMULATEDANNEALING_COOLINGSCHEDULE_HYPERBOLIC = "hyperbolic";

	String OPTIMIZATION_SIMULATEDANNEALING_COOLINGSCHEDULE_SIGMOIDAL = "sigmoidal";

	String OPTIMIZATION_SIMULATEDANNEALING_COOLINGSCHEDULE_LINEAR = "linear";

	// optimization criterium
	String OPTIMIZATION_OPTIMIZE_BY_COST = "cost";

	// Specification for Inter-company Route Share Ratio, specified as 0-1
	String OPTIMIZATION_INTERCOMPANY_SHARE_RATIO_MIN = "0";

	String OPTIMIZATION_INTERCOMPANY_SHARE_RATIO_MAX = "1";

	String OPTIMIZATION_INTERCOMPANY_SHARE_RATIO_DEFAULT = "1";

	String OPTIMIZATION_OPTIMIZE_BY_DISTANCE = "distance";

	String OPTIMIZATION_NEIGHBORHOOD = "optimization.neighborhood";

	String OPTIMIZATION_NEIGHBORHOOD_ALL_PAIRS = "allPairs";

	String OPTIMIZATION_NEIGHBORHOOD_CHANGED_ROUTES = "changedRoutes";

	String OPTIMIZATION_NEIGHBORHOOD_CHANGED_INCREMENTAL = "changedIncremental";

	enum OptimizeBy {
		COST, DISTANCE
	};
}
