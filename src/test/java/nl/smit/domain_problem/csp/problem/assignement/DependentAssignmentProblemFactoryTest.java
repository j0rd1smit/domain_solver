package nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.assignement;

import nl.smit.domain_problem.constraint_satisfaction_problem_solver.helpers.extension.MockitoExtension;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.assignement.constrain.MaxAmountOfAssignmentConstraint;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.domain.DomainFactory;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.stub.depended.StubDependedAssignable;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.stub.depended.StubDependedReceiver;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Tests the implementation of {@link DependentAssignmentProblemFactory}.
 *
 * @author Jordi Smit on 2-3-2018.
 */
@ExtendWith(MockitoExtension.class)
@Tag("fast")
class DependentAssignmentProblemFactoryTest extends AbstractAssignmentProblemFactoryTest<StubDependedAssignable, StubDependedReceiver, DependentAssignmentProblem<StubDependedAssignable, StubDependedReceiver>> {
    @Override
    protected Class<StubDependedAssignable> getAssignableClass() {
        return StubDependedAssignable.class;
    }

    @Override
    protected Class<DependentAssignmentProblem> getAssignmentProblemrClass() {
        return DependentAssignmentProblem.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Class<StubDependedReceiver> getReceiverClass() {
        return StubDependedReceiver.class;
    }

    @Override
    protected AbstractAssignmentProblemFactory<StubDependedAssignable, StubDependedReceiver, DependentAssignmentProblem<StubDependedAssignable, StubDependedReceiver>> create(AssingmentCommandFactory<StubDependedAssignable, StubDependedReceiver> assingmentCommandFactory, DomainFactory domainFactory, MaxAmountOfAssignmentConstraint amountOfOccurencesConstraint) {
        return new DependentAssignmentProblemFactory<>(assingmentCommandFactory, domainFactory, amountOfOccurencesConstraint);
    }
}