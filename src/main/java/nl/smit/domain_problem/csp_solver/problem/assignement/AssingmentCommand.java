package nl.smit.domain_problem.csp_solver.problem.assignement;

import lombok.RequiredArgsConstructor;
import nl.smit.domain_problem.csp_solver.problem.Assignable;
import nl.smit.domain_problem.csp_solver.problem.Receiver;
import nl.smit.domain_problem.csp_solver.problem.SolutionCommand;


import java.util.Map;

/**
 * Responsible for preforming and undoing an assignment solution.
 *
 * @author Jordi Smit on 25-2-2018.
 */
@RequiredArgsConstructor
public class AssingmentCommand<A extends Assignable, R extends Receiver<A>> implements SolutionCommand {
    private final Map<R, A> assignments;

    @Override
    public void apply() {
        for (R receiver : assignments.keySet()) {
            receiver.assign(assignments.get(receiver));
        }
    }

    @Override
    public void unApply() {
        for (R receiver : assignments.keySet()) {
            receiver.restoreOriginal();
        }
    }
}
