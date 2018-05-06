package nl.smit.domain_problem.csp_solver.problem.assignement;

import com.google.common.collect.BiMap;
import lombok.Getter;
import nl.smit.domain_problem.csp_solver.helpers.CollectionHelper;
import nl.smit.domain_problem.csp_solver.helpers.IntVarHelper;
import nl.smit.domain_problem.csp_solver.helpers.MockHelper;
import nl.smit.domain_problem.csp_solver.helpers.extension.MockitoExtension;
import nl.smit.domain_problem.csp_solver.model.ObservableModel;
import nl.smit.domain_problem.csp_solver.problem.Assignable;
import nl.smit.domain_problem.csp_solver.problem.DependentAssignable;
import nl.smit.domain_problem.csp_solver.problem.Receiver;
import nl.smit.domain_problem.csp_solver.problem.assignement.constrain.DependedProblemContrainVisitor;
import nl.smit.domain_problem.csp_solver.problem.assignement.constrain.DependedProblemContrainVisitor.AssingablePair;
import nl.smit.domain_problem.csp_solver.problem.assignement.constrain.DependedProblemContrainVisitor.ReceiverPair;
import nl.smit.domain_problem.csp_solver.stub.depended.StubDependedAssignable;
import nl.smit.domain_problem.csp_solver.stub.depended.StubDependedReceiver;
import org.chocosolver.solver.variables.IntVar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Tests the implementation of {@link DependentAssignmentProblem}.
 *
 * @author Jordi Smit on 26-4-2018.
 */
@ExtendWith(MockitoExtension.class)
@Tag("fast")
public class DependentAssignmentProblemTest extends GenericAssignmentProblemTest<StubDependedAssignable, StubDependedReceiver> {
    /**
     * Default values.
     */
    private static final boolean COMPATIBLE = false;

    /**
     * Inputs.
     */
    @Mock
    private DependedProblemContrainVisitor visitor;
    private BiMap<Assignable, Integer> assignableIndexMapping = MockHelper.createBiMapMockIndexMapping(Assignable.class, ASSIGNABLES);
    private Map<Receiver<Assignable>, IntVar> recieverVarMapping = IntVarHelper.createRecieverIntVarMapping(getModel(), Receiver.class, RECEIVERS);
    private AssignmentProblem<Assignable, Receiver<Assignable>> relatedProblem = createOtherProblem();

    /**
     * Helpers.
     */
    @SuppressWarnings("unchecked")
    private ArgumentCaptor<Set<AssingablePair>> assingablePairsCaptor = ArgumentCaptor.forClass(Set.class);
    @SuppressWarnings("unchecked")
    private ArgumentCaptor<Set<ReceiverPair>> receiverPairsCaptor = ArgumentCaptor.forClass(Set.class);

    /**
     * The object under testing.
     */
    @Getter(lazy = true)
    private final DependentAssignmentProblem<StubDependedAssignable, StubDependedReceiver> dependentAssignmentProblem = (DependentAssignmentProblem<StubDependedAssignable, StubDependedReceiver>) getAssignmentProblem();

    private AssignmentProblem<Assignable, Receiver<Assignable>> createOtherProblem() {
        //noinspection unchecked
        AssingmentCommandFactory<Assignable, Receiver<Assignable>> assingmentCommandFactory = mock(AssingmentCommandFactory.class);
        return new AssignmentProblem<>(assingmentCommandFactory, getModel(), assignableIndexMapping, recieverVarMapping);
    }

    @Override
    protected Class<StubDependedAssignable> getAssignableClass() {
        return StubDependedAssignable.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Class<StubDependedReceiver> getReceiverClass() {
        return StubDependedReceiver.class;
    }

    @Override
    protected AssignmentProblem<StubDependedAssignable, StubDependedReceiver> create(AssingmentCommandFactory<StubDependedAssignable, StubDependedReceiver> assingmentCommandFactory, ObservableModel model, BiMap<StubDependedAssignable, Integer> assignableIndexMapping, Map<StubDependedReceiver, IntVar> recieverVarMapping) {
        return new DependentAssignmentProblem<>(assingmentCommandFactory, model, assignableIndexMapping, recieverVarMapping);
    }

    @BeforeEach
    void beforeEachDependentAssignmentProblemTest() {
        makeAllAssignableCompatableValue();
        makeAllReceiversRelated();
    }

    private void makeAllAssignableCompatableValue() {
        for (DependentAssignable assignable : getAssignableIndexMapping().keySet()) {
            when(assignable.isCompatible(any())).thenReturn(COMPATIBLE);
        }

    }

    private void makeAllReceiversRelated() {
        for (StubDependedReceiver receiver : getRecieverVarMapping().keySet()) {
            Set<Receiver<?>> receivers = new HashSet<>(recieverVarMapping.keySet());
            when(receiver.getDepandedRecievers()).thenReturn(receivers);
        }
    }

    private void actionApply() {
        getDependentAssignmentProblem().addConstrain(visitor, relatedProblem);
    }

    /**
     * Verifies that the correct amount of {@link AssingablePair} have be provided.
     */
    @Test
    void _addConstrainDependedProblemContrainVisitorAmountOfAssignablesPairsTest() {
        assertThat(retriveInputAssingablePairs()).hasSize(ASSIGNABLES * ASSIGNABLES);
    }

    private Set<AssingablePair> retriveInputAssingablePairs() {
        actionApply();

        verify(visitor, atLeastOnce()).apply(eq(getModel()), assingablePairsCaptor.capture(), receiverPairsCaptor.capture());
        return assingablePairsCaptor.getValue();
    }

    /**
     * Verifies that the correct {@link AssingablePair} have be provided.
     */
    @ParameterizedTest
    @CsvSource( {
            "0, 0",
            "0, 1",
            "0, 2",
            "1, 0",
            "1, 1",
            "1, 2",
            "2, 0",
            "2, 1",
            "2, 2",
    })
    void _addConstrainHasInputIncompatableAssignablePairsTest(int assignable, int depandedAssignable) {
        assertThat(retriveInputAssingablePairs()).contains(assignablePairOf(assignable, depandedAssignable));
    }

    private AssingablePair assignablePairOf(int assignable, int depandedAssignable) {
        int assignableIndex = assignableIndexMapping.get(getAssignable(assignable));
        int depandedAssignableIndex = getAssignableIndexMapping().get(getDependedAssignable(depandedAssignable));

        return new AssingablePair(assignableIndex, depandedAssignableIndex);
    }

    private Assignable getAssignable(int index) {
        return CollectionHelper.getNth(assignableIndexMapping.keySet(), index);
    }

    private StubDependedAssignable getDependedAssignable(int index) {
        return CollectionHelper.getNth(getAssignableIndexMapping().keySet(), index);
    }

    /**
     * Verifies that the correct {@link AssingablePair} have be filtered.
     */
    @ParameterizedTest
    @CsvSource( {
            "0, 0",
            "0, 1",
            "0, 2",
            "1, 0",
            "1, 1",
            "1, 2",
            "2, 0",
            "2, 1",
            "2, 2",
    })
    void _addConstrainDoesNotHasCompatableInputAssignablePairsTest(int assignable, int depandedAssignable) {
        makeCompatable(assignable, depandedAssignable);

        assertThat(retriveInputAssingablePairs()).doesNotContain(assignablePairOf(assignable, depandedAssignable));
    }

    private void makeCompatable(int assignableIndex, int depandedAssignableIndex) {
        Assignable assignable = getAssignable(assignableIndex);
        StubDependedAssignable dependedAssignable = getDependedAssignable(depandedAssignableIndex);

        when(dependedAssignable.isCompatible(assignable)).thenReturn(true);
    }

    /**
     * Verifies that the correct amount of {@link ReceiverPair} have been provided.
     */
    @Test
    void _addConstrainDependedProblemContrainVisitorAmountOfReceiverPairsTest() {
        assertThat(retriveInputReceiverPairs()).hasSize(RECEIVERS * RECEIVERS);
    }

    private Set<ReceiverPair> retriveInputReceiverPairs() {
        actionApply();

        verify(visitor, atLeastOnce()).apply(eq(getModel()), assingablePairsCaptor.capture(), receiverPairsCaptor.capture());
        return receiverPairsCaptor.getValue();
    }

    /**
     * Verifies that the correct  {@link ReceiverPair} have been provided.
     */
    @ParameterizedTest
    @CsvSource( {
            "0, 0",
            "1, 1",
            "1, 0",
            "0, 1"
    })
    void _addConstrainHasInputReceiverPairsTest(int reciever, int depandedReciever) {
        assertThat(retriveInputReceiverPairs()).contains(receiverPairOf(reciever, depandedReciever));
    }

    private ReceiverPair receiverPairOf(int reciever, int depandedReciever) {
        IntVar recieverVar = recieverVarMapping.get(getReceiever(reciever));
        IntVar depandedRecieverVar = getRecieverVarMapping().get(getDependedReceiver(depandedReciever));

        return new ReceiverPair(recieverVar, depandedRecieverVar);
    }

    private Receiver<Assignable> getReceiever(int index) {
        return CollectionHelper.getNth(recieverVarMapping.keySet(), index);
    }

    private StubDependedReceiver getDependedReceiver(int index) {
        return CollectionHelper.getNth(getRecieverVarMapping().keySet(), index);
    }

    /**
     * Verifies that the correct amount of {@link ReceiverPair} have been filtered.
     */
    @ParameterizedTest
    @CsvSource( {
            "0, 0",
            "1, 1",
            "1, 0",
            "0, 1"
    })
    void _addConstrainDidNotHaveInputReceiverPairsTest(int reciever, int depandedReciever) {
        makeUnRelated(reciever, depandedReciever);

        assertThat(retriveInputReceiverPairs()).doesNotContain(receiverPairOf(reciever, depandedReciever));
    }

    private void makeUnRelated(int recieverIndex, int depandedRecieveIndex) {
        StubDependedReceiver dependedReceiver = getDependedReceiver(depandedRecieveIndex);
        Receiver<Assignable> receiver = getReceiever(recieverIndex);

        dependedReceiver.getDepandedRecievers().remove(receiver);
    }
}
