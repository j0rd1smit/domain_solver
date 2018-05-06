package nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem;

/**
 * [Explation]
 *
 * @author Jordi Smit on 15-2-2018.
 */
public interface SolutionCommand {

    /**
     * Applies the solution.
     */
    void apply();

    /**
     * Undos the applied solution.
     */
    void unApply();
}
