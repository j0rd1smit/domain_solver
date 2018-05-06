package nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem;

import java.util.Set;

/**
 * [Explation]
 *
 * @author Jordi Smit on 25-4-2018.
 */
public interface DependentReceiver<A extends Assignable> extends Receiver<A> {
    Set<Receiver<?>> getDepandedRecievers();
}
