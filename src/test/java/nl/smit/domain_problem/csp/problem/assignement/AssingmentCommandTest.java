package nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.assignement;

import nl.smit.domain_problem.constraint_satisfaction_problem_solver.helpers.extension.MockitoExtension;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.Assignable;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.Receiver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Tests the implementation of {@link AssingmentCommand}.
 *
 * @author Jordi Smit on 2-3-2018.
 */
@ExtendWith(MockitoExtension.class)
@Tag("fast")
class AssingmentCommandTest {


    /**
     * Inputs
     */
    private Map<Receiver<Assignable>, Assignable> assignments;

    /**
     * The object under testing.
     */
    private AssingmentCommand<Assignable, Receiver<Assignable>> assingmentCommand;

    @BeforeEach
    void beforeEachAssingmentCommand2Test() {
        assignments = IntStream.range(0, 2).boxed().collect(Collectors.toMap(e -> (Receiver<Assignable>) mock(Receiver.class), e -> mock(Assignable.class)));
        assingmentCommand = new AssingmentCommand<>(assignments);
    }


    /**
     * Verifies that assignement will be restored.
     */
    @Test
    void _applyTest() {

        assingmentCommand.apply();

        for (Receiver<Assignable> receiver : assignments.keySet()) {
            verify(receiver).assign(assignments.get(receiver));
        }
    }

    /**
     * Verifies that the original will be restored.
     */
    @Test
    void _unApplyTest() {

        assingmentCommand.unApply();

        for (Receiver receiver : assignments.keySet()) {
            verify(receiver).restoreOriginal();
        }
    }
}