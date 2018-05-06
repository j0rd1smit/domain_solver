package nl.smit.domain_problem.csp_solver.problem;

import nl.smit.domain_problem.csp_solver.exception.UnsolvableException;

/**
 * [Explation]
 *
 * @author Jordi Smit on 15-2-2018.
 */
public interface ConstraintSatisfactionProblem {

    /**
     * Tries to find a valid solution.
     *
     * @throws UnsolvableException When there is no valid solution.
     */
    void findSolution() throws UnsolvableException;

    /**
     * @return A command object that is able to apply the found solution.
     * @throws UnsolvableException When there is no valid solution.
     */
    SolutionCommand getSolution() throws UnsolvableException;
}
