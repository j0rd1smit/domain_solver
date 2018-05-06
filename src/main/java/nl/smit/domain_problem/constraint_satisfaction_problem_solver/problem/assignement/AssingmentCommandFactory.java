package nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.assignement;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.Assignable;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.Receiver;


import java.util.Map;

/**
 * Responsible for creating the {@link AssingmentCommand}s.
 *
 * @author Jordi Smit on 25-2-2018.
 */
@RequiredArgsConstructor
@EqualsAndHashCode
public class AssingmentCommandFactory<A extends Assignable, R extends Receiver<A>> {
    /**
     * The new and original assignement must be compatible.
     *
     * @param assignement The assignemnt of the solution.
     * @return A new {@link AssingmentCommand}.
     */
    public AssingmentCommand<A, R> create(Map<R, A> assignement) {
        return new AssingmentCommand<>(assignement);
    }
}
