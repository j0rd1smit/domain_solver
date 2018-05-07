package nl.smit.domain_problem.csp_solver.problem.assignement;

import com.google.common.collect.BiMap;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;
import nl.smit.domain_problem.csp_solver.exception.UnsolvableException;
import nl.smit.domain_problem.csp_solver.helpers.IntVarHelper;
import nl.smit.domain_problem.csp_solver.helpers.MockHelper;
import nl.smit.domain_problem.csp_solver.helpers.extension.MockitoExtension;
import nl.smit.domain_problem.csp_solver.model.ObservableModel;
import nl.smit.domain_problem.csp_solver.problem.Assignable;
import nl.smit.domain_problem.csp_solver.problem.Receiver;
import nl.smit.domain_problem.csp_solver.problem.SolutionCommand;
import nl.smit.domain_problem.csp_solver.problem.assignement.constrain.AssignmentProblemConstrainVisitor;
import org.checkerframework.checker.initialization.qual.UnderInitialization;
import org.checkerframework.checker.initialization.qual.UnknownInitialization;
import org.chocosolver.solver.variables.IntVar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests the implementation of {@link AssignmentProblem}.
 *
 * @author Jordi Smit on 2-3-2018.
 */
@ExtendWith(MockitoExtension.class)
@Tag("fast")
abstract class GenericAssignmentProblemTest<A extends Assignable, R extends Receiver<A>> {
    /**
     * Default values.
     */
    protected final static int
            ASSIGNABLES = 3,
            RECEIVERS = 2,
            ASSINGMENT_INDEX = 0,
            UPPERBOUND_INTVAR = ASSIGNABLES;

    /**
     * Inputs
     */
    @Mock
    @SuppressWarnings("nullness")
    private AssignmentProblemConstrainVisitor assignmentProblemConstrainVisitor;

    /**
     * Dependencies.
     */
    @Mock
    @SuppressWarnings("nullness")
    private AssingmentCommandFactory<A, R> assingmentCommandFactory;

    @Getter(AccessLevel.PROTECTED)
    private ObservableModel model = new ObservableModel();
    @Getter(AccessLevel.PROTECTED)
    private BiMap<A, Integer> assignableIndexMapping = MockHelper.createBiMapMockIndexMapping(getAssignableClass(), ASSIGNABLES);
    @Getter(AccessLevel.PROTECTED)
    private Map<R, IntVar> recieverVarMapping = IntVarHelper.createRecieverIntVarMapping(model, getReceiverClass(), RECEIVERS, UPPERBOUND_INTVAR);

    /**
     * The object under testing.
     */
    @Getter(AccessLevel.PROTECTED)
    @SuppressWarnings("nullness")
    private AssignmentProblem<A, R> assignmentProblem;


    protected abstract Class<A> getAssignableClass(@UnderInitialization GenericAssignmentProblemTest<A, R>this);

    protected abstract <LR extends Receiver> Class<LR> getReceiverClass(@UnknownInitialization GenericAssignmentProblemTest<A, R>this);

    @BeforeEach
    void beforeEachAssignmentProblem2Test() {
        setUpDepandancies();

        assignmentProblem = create(assingmentCommandFactory, model, assignableIndexMapping, recieverVarMapping);
        model.addObserver(getAssignmentProblem());
    }

    private void setUpDepandancies() {
        when(assingmentCommandFactory.create(any())).thenReturn(mock(AssingmentCommand.class));

        addConstrains();
    }

    private void addConstrains() {
        for (IntVar intVar : recieverVarMapping.values()) {
            model.arithm(intVar, "=", ASSINGMENT_INDEX).post();
        }
    }

    /**
     * Creates the assingment problem
     *
     * @param assingmentCommandFactory The mocked assingmentCommandFactory to be used.
     * @param model                    The model to be used.
     * @param assignableIndexMapping   The assignableIndexMapping to be used.
     * @param recieverVarMapping       The recieverVarMapping to be used.
     * @return The object to be tested.
     */
    protected abstract AssignmentProblem<A, R> create(AssingmentCommandFactory<A, R> assingmentCommandFactory, ObservableModel model, BiMap<A, Integer> assignableIndexMapping, Map<R, IntVar> recieverVarMapping);


    //actions
    @SneakyThrows
    private void actionFindSolution() {
        actionUnwarpedFindSolution();
    }

    private void actionUnwarpedFindSolution() throws UnsolvableException {
        getAssignmentProblem().getSolution();
    }

    private void actionGetSolution() {
        try {
            getAssignmentProblem().getSolution();
        } catch (UnsolvableException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Verifies that the correct {@link SolutionCommand} will be created.
     */
    @Test
    void _findSolutionTest() {

        actionFindSolution();

        assertSolutionCommandHasBeenCreated();
    }

    private void assertSolutionCommandHasBeenCreated() {
        verify(assingmentCommandFactory).create(expectedAssignments());
    }

    private Map<R, A> expectedAssignments() {
        Map<R, A> map = new HashMap<>();

        for (R receiverStub : recieverVarMapping.keySet()) {
            map.put(receiverStub, assignableIndexMapping.inverse().get(ASSINGMENT_INDEX));
        }

        return map;
    }

    /**
     * Verifies that the correct {@link SolutionCommand} will be created when there exits none yet.
     */
    @Test
    void _getSolutionTest() {

        actionGetSolution();

        assertSolutionCommandHasBeenCreated();
    }

    /**
     * Verifies that the cached solution will be returned instaste of recalulated.
     */
    @Test
    void _getSolutionMultipleTimesTest() {

        actionGetSolution();
        actionGetSolution();

        assertSolutionCommandHasBeenCreated();
    }

    /**
     * Verifies that that ilegal solution will be found.
     */
    @Test
    void _invalidAssingmentIndexTest() {
        addIllegalIndexMappingToSolution();

        assertThatThrownBy(this::actionUnwarpedFindSolution)
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    private void addIllegalIndexMappingToSolution() {
        R reciever = (R) mock(getReceiverClass());
        recieverVarMapping.put(reciever, model.intVar(-1));
    }

    /**
     * Verifies that an {@link UnsolvableException} will be thrown when there is no solution.
     */
    @Test
    void _findSolutionUnsolvableExceptionTest() {
        addContradiction();

        assertThatThrownBy(this::actionUnwarpedFindSolution)
                .isExactlyInstanceOf(UnsolvableException.class);

    }

    private void addContradiction() {
        for (IntVar intVar : recieverVarMapping.values()) {
            model.arithm(intVar, "!=", ASSINGMENT_INDEX).post();
        }
    }


    /**
     * Verifies that the vistor will be given the correct inputs.
     */
    @Test
    void _addConstrainTest() {

        getAssignmentProblem().addConstrain(assignmentProblemConstrainVisitor);

        verify(assignmentProblemConstrainVisitor).apply(model, assignableIndexMapping, recieverVarMapping);
    }
}
