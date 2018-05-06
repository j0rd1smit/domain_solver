package nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.assignement;

import lombok.SneakyThrows;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.exception.UnsolvableDomainException;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.exception.UnsolvableException;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.model.ObservableModel;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.ProblemFacade;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.stub.simple.StubAssignable;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.stub.simple.StubReceiver;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Test the functionality of {@link AssignmentProblem}.
 *
 * @author Jordi Smit on 29-3-2018.
 */
@Tag("fast")
class AssignmentProblemFunctionalTest {
    /**
     * Default values.
     */
    private final static int
            ASSINGABLES = 3,
            RECIEVERS = 3,
            MAX_ASSIGNEMENT = 1,
            MIN_ASSIGNEMENT = 0;

    /**
     * Inputs.
     */
    private ObservableModel model = new ObservableModel();

    /**
     * Objects under testing.
     */
    private ProblemFacade problemFacade = new ProblemFacade();
    private AssignmentProblemFactory<StubAssignable, StubReceiver> assignmentProblemFactory = problemFacade.assignmentProblemFactory();

    /**
     * Actions that can be called.
     */
    @SneakyThrows(UnsolvableException.class)
    private void actionAssign(List<StubAssignable> assignables, List<StubReceiver> recievers) {
        actionAssignWithoutWrapper(assignables, recievers);
    }

    private void actionAssignWithoutWrapper(List<StubAssignable> assignables, List<StubReceiver> recievers) throws UnsolvableException {
        AssignmentProblem<StubAssignable, StubReceiver> assignmentProblem = createAssignmentProblem(assignables, recievers);
        assignmentProblem.findSolution();
        assignmentProblem.getSolution().apply();
    }

    private AssignmentProblem<StubAssignable, StubReceiver> createAssignmentProblem(List<StubAssignable> assignables, List<StubReceiver> recievers) throws UnsolvableException {
        return assignmentProblemFactory.create(model, assignables, recievers);
    }


    /**
     * Verifies that a basic problem with the following option can be solved:
     * Reciever1: assignable1 or assignable2;
     * Reciever2: assignable1;
     */
    @Test
    void _basicAssingmentProblemTest() {
        List<StubAssignable> assignables = createDefaultAssignables();
        List<StubReceiver> recievers = createBasicRecieverStub(assignables);

        actionAssign(assignables, recievers);

        assertThat(recievers.get(0).getStubAssignable()).isEqualTo(assignables.get(1));
        assertThat(recievers.get(1).getStubAssignable()).isEqualTo(assignables.get(0));
    }

    private List<StubAssignable> createDefaultAssignables() {
        List<StubAssignable> stubAssignables = new ArrayList<>();

        for (int i = 0; i < ASSINGABLES; i++) {
            stubAssignables.add(new StubAssignable(MIN_ASSIGNEMENT, MAX_ASSIGNEMENT));
        }

        return stubAssignables;
    }

    private List<StubReceiver> createBasicRecieverStub(List<StubAssignable> assignables) {
        List<StubReceiver> recievers = new ArrayList<>();

        final List<StubAssignable> reciever1Options = assignables.subList(0, 2);
        final List<StubAssignable> reciever2Options = assignables.subList(0, 1);

        recievers.add(new StubReceiver(reciever1Options::contains));
        recievers.add(new StubReceiver(reciever2Options::contains));

        return recievers;
    }

    /**
     * Verifies that a domain without a solution will throw a {@link UnsolvableDomainException}.
     */
    @Test
    void _unSolveableDomainTest() {
        List<StubAssignable> assingables = createDefaultAssignables();
        List<StubReceiver> recievers = createUnSolveableRecievers();

        assertThatThrownBy(() -> actionAssignWithoutWrapper(assingables, recievers)).isExactlyInstanceOf(UnsolvableDomainException.class);
    }

    private List<StubReceiver> createUnSolveableRecievers() {
        List<StubReceiver> recievers = new ArrayList<>();

        for (int i = 0; i < RECIEVERS; i++) {
            recievers.add(new StubReceiver(e -> false));
        }

        return recievers;
    }

    /**
     * Verifies that a assignable with a max assignment constraint will be assigned at most n times.
     *
     * @param assingableIndex The index of the assignable.
     * @param n               The amount of times the assignable will be assigned.
     */
    @ParameterizedTest
    @CsvSource( {"1, 1", "2, 2", "0, 0",})
    void _maximumAssignementTest(int assingableIndex, int n) {
        List<StubAssignable> assingables = createMaximumAssingmentAssignables(assingableIndex, n);
        List<StubReceiver> recievers = createDefaultRecievers();

        actionAssign(assingables, recievers);

        Predicate<StubReceiver> predicate = (r) -> r.getStubAssignable().equals(assingables.get(assingableIndex));
        Condition<StubReceiver> condition = new Condition<>(predicate, "has bene assigned the assingable with max constrain");
        assertThat(recievers).haveAtMost(n, condition);
    }

    private List<StubAssignable> createMaximumAssingmentAssignables(int index, int max) {
        List<StubAssignable> stubAssignables = createDefaultAssignables();

        stubAssignables.set(index, new StubAssignable(0, max));
        //Needed to always provided a valid solution
        stubAssignables.add(new StubAssignable(MIN_ASSIGNEMENT, RECIEVERS));

        return stubAssignables;
    }

    private List<StubReceiver> createDefaultRecievers() {
        List<StubReceiver> recievers = new ArrayList<>();

        for (int i = 0; i < RECIEVERS; i++) {
            recievers.add(new StubReceiver(e -> true));
        }

        return recievers;
    }

    /**
     * Verifies that a assignable with a min assignment constraint will be assigned at least n times.
     *
     * @param assingableIndex The index of the assignable.
     * @param n               The amount of times the assignable will be assigned.
     */
    @ParameterizedTest
    @CsvSource( {"0, 3", "1, 2", "2, 1"})
    void _minimumAssingmentConstrainTest(int assingableIndex, int n) {

        List<StubAssignable> assingables = createMinimumAssingmentAssignables(assingableIndex, n);
        List<StubReceiver> recievers = createDefaultRecievers();

        actionAssign(assingables, recievers);

        Predicate<StubReceiver> predicate = (r) -> r.getStubAssignable().equals(assingables.get(assingableIndex));
        Condition<StubReceiver> condition = new Condition<>(predicate, "has bene assigned the assingable with min constrain");
        assertThat(recievers).haveAtLeast(n, condition);
    }

    private List<StubAssignable> createMinimumAssingmentAssignables(int assingableIndex, int min) {
        List<StubAssignable> stubAssignables = createDefaultAssignables();

        stubAssignables.set(assingableIndex, new StubAssignable(min, RECIEVERS));

        return stubAssignables;
    }
}
