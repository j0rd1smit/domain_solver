package nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.assignement;

import nl.smit.domain_problem.constraint_satisfaction_problem_solver.helpers.MockHelper;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.helpers.extension.MockitoExtension;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.Assignable;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.Receiver;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests the implementation of {@link AssingmentCommandFactory}.
 *
 * @author Jordi Smit on 2-3-2018.
 */
@ExtendWith(MockitoExtension.class)
@Tag("fast")
class AssingmentCommandFactoryTest {
    /**
     * Inputs.
     */
    private Map<Receiver<Assignable>, Assignable> assignement = MockHelper.createMockMap(Receiver.class, Assignable.class, 2);

    /**
     * The object under testing.
     */
    private AssingmentCommandFactory<Assignable, Receiver<Assignable>> assingmentCommandFactory = new AssingmentCommandFactory<>();

    /**
     * Verifies that the createDepandedMultipleAssingment method createDepandedMultipleAssingment the command correctly.
     */
    @Test
    void _createTest() {
        AssingmentCommand<Assignable, Receiver<Assignable>> expected = new AssingmentCommand<>(assignement);

        AssingmentCommand<Assignable, Receiver<Assignable>> assingmentCommand = assingmentCommandFactory.create(assignement);

        assertThat(assingmentCommand).isEqualToComparingFieldByField(expected);
    }
}