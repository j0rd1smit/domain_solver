package nl.smit.domain_problem.csp_solver.problem.assignement;

import nl.smit.domain_problem.csp_solver.helpers.extension.MockitoExtension;
import nl.smit.domain_problem.csp_solver.problem.assignement.constrain.DependedExclusionConstrain;
import nl.smit.domain_problem.csp_solver.problem.composite.CompositeAssingmentCommandFactory;
import nl.smit.domain_problem.csp_solver.problem.composite.CompositeProblem;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Tests the implementation of {@link CompositeAssignmentProblemFactory}.
 *
 * @author Jordi Smit on 2-3-2018.
 */
@ExtendWith(MockitoExtension.class)
@Tag("fast")
class CompositeAssignmentProblemFactoryTest {
    /**
     * Inputs.
     */
    private AssignmentProblem assignmentProblem = mock(AssignmentProblem.class);
    private DependentAssignmentProblem dependentAssignmentProblem = mock(DependentAssignmentProblem.class);

    /**
     * Outputs.
     */
    private CompositeProblem result;

    /**
     * Dependencies.
     */
    private CompositeAssingmentCommandFactory commandFactory = mock(CompositeAssingmentCommandFactory.class);
    private DependedExclusionConstrain dependedExclusionConstrain = mock(DependedExclusionConstrain.class);


    /**
     * The object under testing.
     */
    private CompositeAssignmentProblemFactory compositeAssignmentProblemFactory = new CompositeAssignmentProblemFactory(commandFactory, dependedExclusionConstrain);


    //actions
    private void actionCreate() {
        result = compositeAssignmentProblemFactory.create(assignmentProblem, dependentAssignmentProblem);
    }

    /**
     * Verifies that the depended constrain has been added.
     */
    @Test
    void _addConstrainTest() {

        actionCreate();

        verify(dependentAssignmentProblem).addConstrain(dependedExclusionConstrain, assignmentProblem);
    }

    /**
     * Verifies that result has the correct attributes.
     */
    @Test
    void _createResultTest() {

        actionCreate();

        assertThat(result).isEqualToComparingFieldByField(expectedCompositeProblem());
    }

    private CompositeProblem expectedCompositeProblem() {
        return new CompositeProblem(commandFactory, assignmentProblem, dependentAssignmentProblem);
    }
}