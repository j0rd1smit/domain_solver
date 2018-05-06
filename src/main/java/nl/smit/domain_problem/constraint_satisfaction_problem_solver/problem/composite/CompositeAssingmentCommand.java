package nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.composite;

import lombok.RequiredArgsConstructor;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.SolutionCommand;


/**
 * Combine 2 {@link SolutionCommand}s into a composite.
 *
 * @author Jordi Smit on 24-4-2018.
 */
@RequiredArgsConstructor
public class CompositeAssingmentCommand implements SolutionCommand {
    private final SolutionCommand
            solutionCommand1,
            solutionCommand2;

    @Override
    public void apply() {
        solutionCommand1.apply();
        solutionCommand2.apply();
    }

    @Override
    public void unApply() {
        solutionCommand1.unApply();
        solutionCommand2.unApply();
    }
}
