package nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem;

/**
 * [Explation]
 *
 * @author Jordi Smit on 25-4-2018.
 */
public interface DependentAssignable extends Assignable {
    /**
     * @param assignable A assingable object.
     * @return True iff both object can be assigned simultaneously.
     */
    boolean isCompatible(Assignable assignable);


}
