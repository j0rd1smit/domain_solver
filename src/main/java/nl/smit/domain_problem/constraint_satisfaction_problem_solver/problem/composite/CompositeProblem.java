package nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.composite;

import lombok.RequiredArgsConstructor;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.exception.UnsolvableException;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.ConstraintSatisfactionProblem;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.SolutionCommand;


/**
 * Responsible for solving multiple problem simultaneously.
 *
 * @author Jordi Smit on 23-4-2018.
 */
@RequiredArgsConstructor
public class CompositeProblem implements ConstraintSatisfactionProblem {
    private final CompositeAssingmentCommandFactory compositeAssingmentCommandFactory;
    private final ConstraintSatisfactionProblem
            problem1,
            problem2;


    @Override
    public void findSolution() throws UnsolvableException {
        problem1.findSolution();
    }

    @Override
    public SolutionCommand getSolution() throws UnsolvableException {
        return compositeAssingmentCommandFactory.create(problem1.getSolution(), problem2.getSolution());
    }
}
