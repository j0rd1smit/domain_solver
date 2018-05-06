package nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.composite;

import nl.smit.domain_problem.constraint_satisfaction_problem_solver.exception.UnsolvableException;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.helpers.extension.MockitoExtension;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.ConstraintSatisfactionProblem;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.SolutionCommand;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

/**
 * Tests the implementation of {@link CompositeProblem}.
 *
 * @author Jordi Smit on 2-3-2018.
 */
@ExtendWith(MockitoExtension.class)
@Tag("fast")
class CompositeProblemTest {
    /**
     * Dependencies.
     */
    private CompositeAssingmentCommandFactory compositeAssingmentCommandFactory = mock(CompositeAssingmentCommandFactory.class);
    private ConstraintSatisfactionProblem
            problem1 = mock(ConstraintSatisfactionProblem.class),
            problem2 = mock(ConstraintSatisfactionProblem.class);


    /**
     * The object under testing.
     */
    private CompositeProblem compositeProblem = new CompositeProblem(compositeAssingmentCommandFactory, problem1, problem2);

    /**
     * Verifies that findSolution will be called on problem 1.
     */
    @Test
    void _findSolutionTest() throws UnsolvableException {

        compositeProblem.findSolution();

        verify(problem1).findSolution();
    }

    /**
     * Verifies that problem2 will not update manualy.
     * Assumes that the observer relationship will update problem2.
     */
    @Test
    void _findSolutionLetsObserverRelationShipHandelFindsolutionProblem2Test() throws UnsolvableException {

        compositeProblem.findSolution();

        verifyZeroInteractions(problem2);
    }

    /**
     * Verifies that {@link CompositeAssingmentCommand} will be created with the correct order.
     */
    @Test
    void _getSolutionTest(@Mock SolutionCommand solutionCommand1, @Mock SolutionCommand solutionCommand2) throws UnsolvableException {
        when(problem1.getSolution()).thenReturn(solutionCommand1);
        when(problem2.getSolution()).thenReturn(solutionCommand2);

        compositeProblem.getSolution();

        verify(compositeAssingmentCommandFactory).create(solutionCommand1, solutionCommand2);
    }
}