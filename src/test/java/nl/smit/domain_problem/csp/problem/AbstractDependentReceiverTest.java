package nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem;

import nl.smit.domain_problem.constraint_satisfaction_problem_solver.helpers.extension.MockitoExtension;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.stub.depended.StubDependedReceiver;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.stub.simple.StubReceiver;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;


/**
 * Tests the implementation of {@link AbstractDependentReceiver}.
 *
 * @author Jordi Smit on 2-3-2018.
 */
@ExtendWith(MockitoExtension.class)
@Tag("fast")
class AbstractDependentReceiverTest {
    /**
     * Inputs.
     */
    private StubReceiver receiver = mock(StubReceiver.class);
    private StubDependedReceiver dependedReceiver = mock(StubDependedReceiver.class);

    /**
     * The object under testing.
     */
    private AbstractDependentReceiver abstractDependentReceiver = new Stub();

    //actions
    private void actionAddReceiver() {
        abstractDependentReceiver.add(receiver);
    }

    private void actionAddDependedReceiver() {
        abstractDependentReceiver.add(dependedReceiver);
    }

    private void actionAddAll() {
        List<Receiver> receivers = new ArrayList<>();
        receivers.add(receiver);
        receivers.add(dependedReceiver);

        abstractDependentReceiver.addAll(receivers);
    }

    private void actionRemoveReceiver() {
        abstractDependentReceiver.remove(receiver);
    }

    /**
     * Verifies that a {@link Receiver} can be added.
     */
    @Test
    void _addReceiverTest() {

        actionAddReceiver();

        assertReceiverIsRelated();
    }

    @SuppressWarnings("unchecked")
    private void assertReceiverIsRelated() {
        assertThat(abstractDependentReceiver.getDepandedRecievers()).contains(receiver);
    }


    /**
     * Verifies that a {@link DependentReceiver} can be added.
     */
    @Test
    void _addDependedReceiverTest() {

        actionAddDependedReceiver();

        assertDependedReceiverIsRelated();
    }

    @SuppressWarnings("unchecked")
    private void assertDependedReceiverIsRelated() {
        assertThat(abstractDependentReceiver.getDepandedRecievers()).contains(dependedReceiver);
    }

    /**
     * Verifies that a collection of {@link Receiver}s can be added.
     */
    @Test
    void _addAllTest() {

        actionAddAll();

        assertDependedReceiverIsRelated();
        assertReceiverIsRelated();
    }


    /**
     * Verifies that a {@link Receiver} can be removed.
     */
    @Test
    void _removeTest() {
        actionAddAll();
        assertReceiverIsRelated();

        actionRemoveReceiver();

        assertDependedReceiverIsRelated();
        assertReceiverIsUnrelated();
    }

    @SuppressWarnings("unchecked")
    private void assertReceiverIsUnrelated() {
        assertThat(abstractDependentReceiver.getDepandedRecievers()).doesNotContain(receiver);
    }


    private static class Stub extends AbstractDependentReceiver<Assignable> {

        @Override
        public void restoreOriginal() {

        }

        @Override
        public void assign(Assignable assignable) {

        }
    }
}