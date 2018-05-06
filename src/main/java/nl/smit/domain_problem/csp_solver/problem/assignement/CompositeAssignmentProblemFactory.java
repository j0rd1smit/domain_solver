package nl.smit.domain_problem.csp_solver.problem.assignement;

import lombok.RequiredArgsConstructor;
import nl.smit.domain_problem.csp_solver.problem.assignement.constrain.DependedExclusionConstrain;
import nl.smit.domain_problem.csp_solver.problem.composite.CompositeAssingmentCommandFactory;
import nl.smit.domain_problem.csp_solver.problem.composite.CompositeProblem;


/**
 * Responsible for creating {@link CompositeProblem} with depended variables.
 *
 * @author Jordi Smit on 27-4-2018.
 */
@RequiredArgsConstructor
public class CompositeAssignmentProblemFactory {
    private final CompositeAssingmentCommandFactory commandFactory;
    private final DependedExclusionConstrain dependedExclusionConstrain;

    /**
     * @param assignmentProblem          A assignement problem.
     * @param dependentAssignmentProblem A assignement problem that depends on the first problem.
     * @return A new {@link CompositeProblem}.
     */
    public CompositeProblem create(AssignmentProblem assignmentProblem, DependentAssignmentProblem dependentAssignmentProblem) {
        dependentAssignmentProblem.addConstrain(dependedExclusionConstrain, assignmentProblem);

        return new CompositeProblem(commandFactory, assignmentProblem, dependentAssignmentProblem);
    }
}
