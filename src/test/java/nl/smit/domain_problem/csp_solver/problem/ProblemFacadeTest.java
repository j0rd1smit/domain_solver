package nl.smit.domain_problem.csp_solver.problem;

import nl.smit.domain_problem.csp_solver.helpers.extension.MockitoExtension;
import nl.smit.domain_problem.csp_solver.problem.assignement.AssignmentProblemFactory;
import nl.smit.domain_problem.csp_solver.problem.assignement.AssingmentCommandFactory;
import nl.smit.domain_problem.csp_solver.problem.assignement.constrain.MaxAmountOfAssignmentConstraint;
import nl.smit.domain_problem.csp_solver.problem.domain.DomainFactory;
import nl.smit.domain_problem.csp_solver.stub.simple.StubAssignable;
import nl.smit.domain_problem.csp_solver.stub.simple.StubReceiver;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests the implementation of {@link ProblemFacade}.
 *
 * @author Jordi Smit on 2-3-2018.
 */
@ExtendWith(MockitoExtension.class)
@Tag("fast")
class ProblemFacadeTest {
    /**
     * The object under testing.
     */
    private ProblemFacade problemFacade = new ProblemFacade();


    /**
     * Verifies that will be created using the expected classes.
     */
    @Test
    void _assignmentProblemFactoryTest() {
        assertThat(problemFacade.assignmentProblemFactory())
                .isEqualToComparingFieldByField(expectedAssignmentProblemFactory());
    }

    private AssignmentProblemFactory<StubAssignable, StubReceiver> expectedAssignmentProblemFactory() {
        AssingmentCommandFactory<StubAssignable, StubReceiver> assingmentCommandFactory = new AssingmentCommandFactory<>();
        DomainFactory domainFactory = new DomainFactory();
        MaxAmountOfAssignmentConstraint maxAmountOfAssignmentConstraint = new MaxAmountOfAssignmentConstraint();

        return new AssignmentProblemFactory<>(assingmentCommandFactory, domainFactory, maxAmountOfAssignmentConstraint);
    }
}