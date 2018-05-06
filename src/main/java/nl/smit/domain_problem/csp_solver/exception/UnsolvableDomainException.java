package nl.smit.domain_problem.csp_solver.exception;

/**
 * A exception that will be thrown when no solvable model can be created.
 *
 * @author Jordi Smit on 10-3-2018.
 */
public class UnsolvableDomainException extends UnsolvableException {
    /**
     * The default constructor.
     *
     * @param message The message of the exception.
     */
    public UnsolvableDomainException(String message) {
        super(message);
    }
}
