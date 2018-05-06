package nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.composite;


import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.SolutionCommand;

/**
 * Responsible for create {@link CompositeAssingmentCommand}.
 *
 * @author Jordi Smit on 24-4-2018.
 */
public class CompositeAssingmentCommandFactory {
    /**
     * @param solutionCommand1 The first {@link CompositeAssingmentCommand}.
     * @param solutionCommand2 The second {@link CompositeAssingmentCommand}.
     * @return a new {@link CompositeAssingmentCommand}.
     */
    CompositeAssingmentCommand create(SolutionCommand solutionCommand1, SolutionCommand solutionCommand2) {
        return new CompositeAssingmentCommand(solutionCommand1, solutionCommand2);
    }
}
