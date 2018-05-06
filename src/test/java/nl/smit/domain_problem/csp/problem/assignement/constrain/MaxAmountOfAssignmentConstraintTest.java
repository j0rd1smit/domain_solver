package nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.assignement.constrain;

import nl.smit.domain_problem.constraint_satisfaction_problem_solver.helpers.IntVarHelper;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.helpers.MockHelper;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.helpers.extension.MockitoExtension;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.Assignable;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.Receiver;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Tests the implementation of {@link MaxAmountOfAssignmentConstraint}.
 *
 * @author Jordi Smit on 2-3-2018.
 */
@ExtendWith(MockitoExtension.class)
@Tag("fast")
class MaxAmountOfAssignmentConstraintTest {
    /**
     * Default values.
     */
    private final static int
            MAX_ASSIGNEMENTS = 1,
            AMOUNT_OF_ASSIGNABLE = 2,
            AMOUNT_OF_RECIEVERS = 2,

    LOWER_BOUND = 0;

    /**
     * Inputs.
     */
    private Model model = spy(new Model());
    private Map<Assignable, Integer> assingableIndexMapping2 = MockHelper.createMockIndexMapping(Assignable.class, AMOUNT_OF_ASSIGNABLE);
    private Map<Receiver<Assignable>, IntVar> recieverVarMapping2 = IntVarHelper.createRecieverIntVarMapping(model, Receiver.class, AMOUNT_OF_RECIEVERS);


    /**
     * The object under testing.
     */
    private MaxAmountOfAssignmentConstraint maxAmountOfAssignmentConstraint;

    @BeforeEach
    void beforeEachMaxAmountOfAssignmentConstraint2Test() {
        setUpGetMaxAssingmentStrategy();

        maxAmountOfAssignmentConstraint = new MaxAmountOfAssignmentConstraint();
    }

    private void setUpGetMaxAssingmentStrategy() {
        for (Assignable assignableStub : assingableIndexMapping2.keySet()) {
            when(assignableStub.getMaxAssingment()).thenReturn(MAX_ASSIGNEMENTS);
            when(assignableStub.getMinAssingment()).thenReturn(LOWER_BOUND);
        }
    }

    private void actionApply() {
        maxAmountOfAssignmentConstraint.apply(model, assingableIndexMapping2, recieverVarMapping2);
    }

    /**
     * Verifies that constrain will be created for each assingable.
     */
    @Test
    void _applyTest() {

        actionApply();

        assertAllAssingableHaveRecievedAnMaxAmountConstrain();
    }

    private void assertAllAssingableHaveRecievedAnMaxAmountConstrain() {
        IntVar[] vars = recieverVarMapping2.values().toArray(new IntVar[AMOUNT_OF_RECIEVERS]);

        for (Assignable assignableStub : assingableIndexMapping2.keySet()) {
            int assingableIndex = assingableIndexMapping2.get(assignableStub);

            verify(model).count(eq(assingableIndex), eq(vars), any(IntVar.class));
        }
    }


    /**
     * Verifies that max amount of assingment variable is createDepandedMultipleAssingment with the correct lower bound.
     */
    @Test
    void _maxAssingmentVariableTest() {

        actionApply();

        verify(model, times(AMOUNT_OF_ASSIGNABLE)).intVar(any(String.class), eq(LOWER_BOUND), eq(MAX_ASSIGNEMENTS));
    }
}