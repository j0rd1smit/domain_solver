package nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.assignement;

import nl.smit.domain_problem.constraint_satisfaction_problem_solver.helpers.extension.MockitoExtension;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.Receiver;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.assignement.constrain.MaxAmountOfAssignmentConstraint;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.domain.DomainFactory;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.stub.simple.StubAssignable;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.stub.simple.StubReceiver;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Tests the implementation of {@link AssignmentProblemFactory}.
 *
 * @author Jordi Smit on 2-3-2018.
 */
@ExtendWith(MockitoExtension.class)
@Tag("fast")
class AssignmentProblemFactoryTest extends AbstractAssignmentProblemFactoryTest<StubAssignable, StubReceiver, AssignmentProblem<StubAssignable, StubReceiver>> {

    @Override
    protected Class<StubAssignable> getAssignableClass() {
        return StubAssignable.class;
    }

    @Override
    protected <LR extends Receiver<StubAssignable>> Class<LR> getReceiverClass() {
        return (Class<LR>) StubReceiver.class;
    }

    @Override
    protected <LE extends AssignmentProblem> Class<LE> getAssignmentProblemrClass() {
        return (Class<LE>) AssignmentProblem.class;
    }

    @Override
    protected AbstractAssignmentProblemFactory<StubAssignable, StubReceiver, AssignmentProblem<StubAssignable, StubReceiver>> create(AssingmentCommandFactory<StubAssignable, StubReceiver> assingmentCommandFactory, DomainFactory domainFactory, MaxAmountOfAssignmentConstraint amountOfOccurencesConstraint) {
        return new AssignmentProblemFactory<>(assingmentCommandFactory, domainFactory, amountOfOccurencesConstraint);
    }
}