package nl.smit.domain_problem.csp_solver.model;

import nl.smit.domain_problem.csp_solver.exception.UnsolvableException;
import nl.smit.domain_problem.csp_solver.helpers.extension.MockitoExtension;
import org.chocosolver.solver.variables.IntVar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;

/**
 * Tests the implementation of {@link ObservableModel}.
 *
 * @author Jordi Smit on 2-3-2018.
 */
@ExtendWith(MockitoExtension.class)
@Tag("fast")
class ObservableModelTest {
    /**
     * Inputs.
     */
    @Mock
    private ObservableModel.ModelObserver observer;

    /**
     * The object under testing.
     */
    private ObservableModel observableModel = new ObservableModel();

    @BeforeEach
    void beforeEachObservableModelTest() {
        observableModel.addObserver(observer);
    }

    private void actionSolve() {
        try {
            actionUnwrappedSolve();
        } catch (UnsolvableException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private void actionUnwrappedSolve() throws UnsolvableException {
        observableModel.solve();
    }


    /**
     * Verifies that solving the model updates all observers.
     */
    @Test
    void _solveTest() {

        actionSolve();

        verify(observer).updateSolution();
    }

    /**
     * Verifies that an unsolveable model throws an {@link UnsolvableException}.
     */
    @Test
    void _unsolvableabletest() {
        makeUnSolveable();

        assertThatThrownBy(this::actionUnwrappedSolve)
                .isInstanceOf(UnsolvableException.class)
                .hasMessageContaining("no solutions");
    }

    private void makeUnSolveable() {
        int value = 1;
        IntVar var = observableModel.intVar(value);
        observableModel.arithm(var, "!=", value).post();
    }
}