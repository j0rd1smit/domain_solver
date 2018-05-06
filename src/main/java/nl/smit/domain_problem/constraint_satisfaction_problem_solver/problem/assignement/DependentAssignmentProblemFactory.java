package nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.assignement;

import com.google.common.collect.BiMap;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.model.ObservableModel;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.DependentAssignable;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.DependentReceiver;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.assignement.constrain.DependedExclusionConstrain;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.assignement.constrain.MaxAmountOfAssignmentConstraint;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.domain.DomainFactory;
import org.chocosolver.solver.variables.IntVar;

import java.util.Map;

/**
 * [Explation]
 *
 * @author Jordi Smit on 26-4-2018.
 */
public class DependentAssignmentProblemFactory<A extends DependentAssignable, R extends DependentReceiver<A>> extends AbstractAssignmentProblemFactory<A, R, DependentAssignmentProblem<A, R>> {
    private final DependedExclusionConstrain dependedExclusionConstrain = new DependedExclusionConstrain();

    public DependentAssignmentProblemFactory(AssingmentCommandFactory<A, R> assingmentCommandFactory, DomainFactory domainFactory, MaxAmountOfAssignmentConstraint amountOfOccurencesConstraint) {
        super(assingmentCommandFactory, domainFactory, amountOfOccurencesConstraint);
    }

    @Override
    protected DependentAssignmentProblem<A, R> create(AssingmentCommandFactory<A, R> assingmentCommandFactory, ObservableModel model, BiMap<A, Integer> assignableIndexMapping, Map<R, IntVar> recieversVarMapping) {
        return new DependentAssignmentProblem<>(assingmentCommandFactory, model, assignableIndexMapping, recieversVarMapping);
    }
}
