package nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.composite;

import nl.smit.domain_problem.constraint_satisfaction_problem_solver.helpers.extension.MockitoExtension;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.SolutionCommand;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Tests the implementation of {@link CompositeAssingmentCommand}.
 *
 * @author Jordi Smit on 2-3-2018.
 */
@ExtendWith(MockitoExtension.class)
@Tag("fast")
class CompositeAssingmentCommandTest {
    /**
     * Dependencies.
     */
    private SolutionCommand
            solutionCommand1 = mock(SolutionCommand.class),
            solutionCommand2 = mock(SolutionCommand.class);


    /**
     * The object under testing.
     */
    private CompositeAssingmentCommand compositeAssingmentCommand = new CompositeAssingmentCommand(solutionCommand1, solutionCommand2);

    private void actionApply() {
        compositeAssingmentCommand.apply();
    }

    private void actionUnApply() {
        compositeAssingmentCommand.unApply();
    }

    /**
     * Verifies that apply applies the solution command of command 1.
     */
    @Test
    void _applyAppliesSolutionCommand1Test() {

        actionApply();

        verify(solutionCommand1).apply();
    }

    /**
     * Verifies that apply applies the solution command of command 2.
     */
    @Test
    void _applyAppliesSolutionCommand2Test() {

        actionApply();

        verify(solutionCommand2).apply();
    }

    /**
     * Verifies that unapply unapplies the solution command of command 1.
     */
    @Test
    void _unapplyUnAppliesSolutionCommand1Test() {

        actionUnApply();

        verify(solutionCommand1).unApply();
    }

    /**
     * Verifies that unapply unapplies the solution command of command 2.
     */
    @Test
    void _unapplyUnAppliesSolutionCommand2Test() {

        actionUnApply();

        verify(solutionCommand2).unApply();
    }
}