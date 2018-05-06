package nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.assignement.constrain;

import nl.smit.domain_problem.constraint_satisfaction_problem_solver.helpers.extension.MockitoExtension;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.assignement.constrain.DependedProblemContrainVisitor.AssingablePair;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.assignement.constrain.DependedProblemContrainVisitor.ReceiverPair;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.mockito.Mockito.*;

/**
 * Tests the implementation of {@link DependedExclusionConstrain}.
 *
 * @author Jordi Smit on 2-3-2018.
 */
@ExtendWith(MockitoExtension.class)
@Tag("fast")
class DependedExclusionConstrainTest {
    /**
     * Default values.
     */
    private final static int
            ASSINGABLE_PAIR = 2,
            DEPENDED_RECEIVER_PAIRS = 2;
    private final static Random RANDOM = new Random(0);


    /**
     * Inputs.
     */
    private Model model = mock(Model.class);
    ;
    private Set<AssingablePair> incompatibleAssignablePairs = createIncompatibleAssignablePairs();
    private Set<ReceiverPair> dependedReceiverPairs = createDependedReceiverPairs();


    private Set<AssingablePair> createIncompatibleAssignablePairs() {
        Set<AssingablePair> set = new HashSet<>();

        for (int i = 0; i < ASSINGABLE_PAIR; i++) {
            set.add(new AssingablePair(RANDOM.nextInt(), RANDOM.nextInt()));
        }

        return set;
    }

    private Set<ReceiverPair> createDependedReceiverPairs() {
        Set<ReceiverPair> set = new HashSet<>();

        for (int i = 0; i < DEPENDED_RECEIVER_PAIRS; i++) {
            set.add(new ReceiverPair(mock(IntVar.class), mock(IntVar.class)));
        }

        return set;
    }


    /**
     * The object under testing.
     */
    private DependedExclusionConstrain dependedExclusionConstrain = new DependedExclusionConstrain();


    //actions
    private void actionApply() {
        dependedExclusionConstrain.apply(model, incompatibleAssignablePairs, dependedReceiverPairs);
    }

    /**
     * Verifies that constrain type has been used.
     */
    @Test
    void _ifThenUsageTest(@Mock Constraint ifConstraint, @Mock Constraint thenConstraint) {
        when(model.arithm(Mockito.any(IntVar.class), Mockito.eq("="), Mockito.anyInt())).thenReturn(ifConstraint);
        when(model.arithm(Mockito.any(IntVar.class), Mockito.eq("!="), Mockito.anyInt())).thenReturn(thenConstraint);

        actionApply();

        verify(model, times(ASSINGABLE_PAIR * DEPENDED_RECEIVER_PAIRS)).ifThen(ifConstraint, thenConstraint);
    }

    /**
     * Verifies that correct if case has been used.
     */
    @Test
    void _correctIfCaseTest() {

        actionApply();

        for (ReceiverPair receiverPair : dependedReceiverPairs) {
            for (AssingablePair assingablePair : incompatibleAssignablePairs) {
                verify(model).arithm(receiverPair.getVariableReceiver(), "=", assingablePair.getIndexAssignable());
            }
        }
    }

    /**
     * Verifies that correct then case has been used.
     */
    @Test
    void _correctThenCaseTest() {

        actionApply();

        for (ReceiverPair receiverPair : dependedReceiverPairs) {
            for (AssingablePair assingablePair : incompatibleAssignablePairs) {
                verify(model).arithm(receiverPair.getVariableDependentReceiver(), "!=", assingablePair.getIndexdependentAssignable());
            }
        }
    }


}