package nl.smit.domain_problem.csp_solver.problem.assignement;

import com.google.common.collect.BiMap;
import lombok.SneakyThrows;
import nl.smit.domain_problem.csp_solver.exception.UnsolvableDomainException;
import nl.smit.domain_problem.csp_solver.exception.UnsolvableException;
import nl.smit.domain_problem.csp_solver.helpers.MockHelper;
import nl.smit.domain_problem.csp_solver.helpers.extension.MockitoExtension;
import nl.smit.domain_problem.csp_solver.model.ObservableModel;
import nl.smit.domain_problem.csp_solver.problem.Assignable;
import nl.smit.domain_problem.csp_solver.problem.Receiver;
import nl.smit.domain_problem.csp_solver.problem.assignement.constrain.MaxAmountOfAssignmentConstraint;
import nl.smit.domain_problem.csp_solver.problem.domain.DomainFactory;
import org.checkerframework.checker.initialization.qual.UnderInitialization;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Tests the implementation of {@link AbstractAssignmentProblemFactory}.
 *
 * @author Jordi Smit on 2-3-2018.
 */
@ExtendWith(MockitoExtension.class)
@Tag("fast")
abstract class AbstractAssignmentProblemFactoryTest<A extends Assignable, R extends Receiver<A>, E extends AssignmentProblem<A, R>> {
    /**
     * Default values.
     */
    private final static int
            ASSIGNABLES = 2,
            RECIEVERS = 2;

    /**
     * Inputs.
     */
    private ObservableModel model = spy(new ObservableModel());
    private List<A> assignables = MockHelper.createMockList(getAssignableClass(), ASSIGNABLES);
    private List<R> receivers = MockHelper.createMockList(getReceiverClass(), RECIEVERS);

    private E assignmentProblem = (E) mock(getAssignmentProblemrClass());

    /**
     * Dependencies.
     */
    private AssingmentCommandFactory<A, R> assingmentCommandFactory = mock(AssingmentCommandFactory.class);
    private DomainFactory domainFactory = mock(DomainFactory.class);
    private MaxAmountOfAssignmentConstraint amountOfOccurencesConstraint = mock(MaxAmountOfAssignmentConstraint.class);


    /**
     * The object under testing.
     */
    private AbstractAssignmentProblemFactory<A, R, E> abstractAssignmentProblemFactory = create(
            assingmentCommandFactory,
            domainFactory,
            amountOfOccurencesConstraint);

    protected abstract Class<A> getAssignableClass(
            @UnderInitialization AbstractAssignmentProblemFactoryTest<A, R, E>this
    );

    protected abstract <LR extends Receiver<A>> Class<LR> getReceiverClass(
            @UnderInitialization AbstractAssignmentProblemFactoryTest<A, R, E>this
    );

    protected abstract <LE extends AssignmentProblem> Class<LE> getAssignmentProblemrClass(
            @UnderInitialization AbstractAssignmentProblemFactoryTest<A, R, E>this
    );

    protected abstract AbstractAssignmentProblemFactory<A, R, E> create(
            @UnderInitialization AbstractAssignmentProblemFactoryTest<A, R, E>this,
            AssingmentCommandFactory<A, R> assingmentCommandFactory,
            DomainFactory domainFactory,
            MaxAmountOfAssignmentConstraint amountOfOccurencesConstraint
    );


    //actions
    @SneakyThrows(UnsolvableException.class)
    private void actionCreate() {
        abstractAssignmentProblemFactory.create(model, assignables, receivers);
    }

    private void actionInitAssignmentProblem() {
        abstractAssignmentProblemFactory.initAssignmentProblem(model, assignmentProblem);
    }

    /**
     * Verifies that will the correct index mapping has been provided to the domain.
     */
    @Test
    void _domainFactoryTest() throws UnsolvableDomainException {
        ArgumentCaptor<BiMap<A, Integer>> captor = ArgumentCaptor.forClass(BiMap.class);

        actionCreate();

        verify(domainFactory).create(eq(model), captor.capture(), eq(receivers));
        assertAssignablesHaveCorrectIndexMapping(captor.getValue());
    }

    private void assertAssignablesHaveCorrectIndexMapping(Map<A, Integer> assignableIndexMapping) {
        for (Map.Entry<A, Integer> entry : assignableIndexMapping.entrySet()) {
            assertThat(assignables).contains(entry.getKey());
            assertThat(assignables.indexOf(entry.getKey())).isEqualTo(entry.getValue());
        }
    }

    /**
     * Verifies that the problem has been added as a observer to the model.
     */
    @Test
    void _addOberserTest() {

        actionCreate();

        verify(model).addObserver(any(AssignmentProblem.class));
    }

    /**
     * Verifies that observer relationship will be added during the init.
     */
    @Test
    void _addOberserInitTestTest() {

        actionInitAssignmentProblem();

        verify(model).addObserver(assignmentProblem);
    }


    /**
     * Verifies that amountOfOccurencesConstraint will be added.
     */
    @Test
    void _initConstrainsTest() throws UnsolvableDomainException {

        actionInitAssignmentProblem();

        verify(assignmentProblem).addConstrain(amountOfOccurencesConstraint);
    }


}