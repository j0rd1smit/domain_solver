package nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem;

/**
 * The required function of a reciever.
 *
 * @author Jordi Smit on 21-3-2018.
 */
public interface Receiver<A extends Assignable> {

    /**
     * @param assignable The assignable value.
     * @return True iff the assignment is a valid option.
     */
    default boolean isValidOption(A assignable) {
        return true;
    }


    /**
     * Restores the assignement to the original assignment.
     */
    void restoreOriginal();

    /**
     * Assigns the assignable.
     *
     * @param assignable THe assignable.
     */
    void assign(A assignable);
}
