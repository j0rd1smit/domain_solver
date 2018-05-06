package nl.smit.domain_problem.constraint_satisfaction_problem_solver.exception;

/**
 * A {@link Exception} for a unsolvable model.
 *
 * @author Jordi Smit, 10-2-2018.
 */
public class UnsolvableException extends Exception {
    /**
     * The default constructor.
     *
     * @param message The message of the exception.
     */
    public UnsolvableException(String message) {
        super(message);
    }
}
