package nl.smit.domain_problem.csp_solver.problem.composite;

import nl.smit.domain_problem.csp_solver.helpers.extension.MockitoExtension;
import nl.smit.domain_problem.csp_solver.problem.SolutionCommand;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests the implementation of {@link CompositeAssingmentCommandFactory}.
 *
 * @author Jordi Smit on 2-3-2018.
 */
@ExtendWith(MockitoExtension.class)
@Tag("fast")
class CompositeAssingmentCommandFactoryTest {
    /**
     * The object under testing.
     */
    private CompositeAssingmentCommandFactory compositeAssingmentCommandFactory = new CompositeAssingmentCommandFactory();


    /**
     * Verifies that
     */
    @Test
    void _createTest(@Mock SolutionCommand solutionCommand1, @Mock SolutionCommand solutionCommand2) {
        CompositeAssingmentCommand expected = new CompositeAssingmentCommand(solutionCommand1, solutionCommand2);

        CompositeAssingmentCommand result = compositeAssingmentCommandFactory.create(solutionCommand1, solutionCommand2);

        assertThat(result).isEqualToComparingFieldByField(expected);
    }
}