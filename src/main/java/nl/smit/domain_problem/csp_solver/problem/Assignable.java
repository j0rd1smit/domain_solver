package nl.smit.domain_problem.csp_solver.problem;

/**
 * The required function of a assingable.
 *
 * @author Jordi Smit on 21-3-2018.
 */
public interface Assignable {

    /**
     * @return The maximum amount of assignments.
     */
    default int getMaxAssingment() {
        return Integer.MAX_VALUE;
    }

    /**
     * @return The minimum amount of assignments.
     */
    default int getMinAssingment() {
        return 0;
    }

}
