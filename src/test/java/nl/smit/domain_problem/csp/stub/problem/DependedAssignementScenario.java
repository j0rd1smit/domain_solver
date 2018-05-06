package nl.smit.domain_problem.constraint_satisfaction_problem_solver.stub.problem;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.SneakyThrows;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.model.ObservableModel;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.ProblemFacade;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.assignement.AssignmentProblem;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.assignement.DependentAssignmentProblem;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.stub.depended.StubDependedAssignable;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.stub.depended.StubDependedReceiver;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.stub.simple.StubAssignable;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.stub.simple.StubReceiver;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Defines a Depanded assignement scenario for testing purposes.
 *
 * @author Jordi Smit on 23-4-2018.
 */
@Builder
public class DependedAssignementScenario {
    private final List<StubAssignable> problemAssignables;
    private final List<StubReceiver> problemRecievers;
    private final Map<StubReceiver, StubAssignable> expectedProblemRecievers;

    private final List<StubDependedAssignable> dependedAssignables;
    private final List<StubDependedReceiver> dependedProblemRecievers;
    private final Map<StubDependedReceiver, StubDependedAssignable> expectedDepandedProblemRecievers;


    @Getter(value = AccessLevel.PRIVATE, lazy = true)
    private final ProblemFacade problemFacade = new ProblemFacade();
    @Getter(value = AccessLevel.PRIVATE, lazy = true)
    private final ObservableModel model = getProblemFacade().observableModel();

    @Getter(lazy = true)
    private final AssignmentProblem<StubAssignable, StubReceiver> assignmentProblem = createAssignmentProblem();
    @Getter(lazy = true)
    private final DependentAssignmentProblem<StubDependedAssignable, StubDependedReceiver> dependedProblem = createDependedProblem();

    @SneakyThrows
    private AssignmentProblem<StubAssignable, StubReceiver> createAssignmentProblem() {
        return getProblemFacade().assignmentProblem(getModel(), problemAssignables, problemRecievers);
    }

    @SneakyThrows
    private DependentAssignmentProblem<StubDependedAssignable, StubDependedReceiver> createDependedProblem() {
        return getProblemFacade().dependentAssignmentProblem(getModel(), dependedAssignables, dependedProblemRecievers);
    }

    /**
     * @return Iff the assingment problem has been solved.
     */
    public boolean assignmentProblemHasBeenSolved() {
        for (StubReceiver reciever : problemRecievers) {
            if (!expectedProblemRecievers.get(reciever).equals(reciever.getStubAssignable())) {
                return false;
            }
        }
        return true;
    }

    public void assertAssignmentProblemHasBeenSolved() {
        assertThat(problemRecievers)
                .allMatch(reciever -> expectedProblemRecievers.get(reciever).equals(reciever.getStubAssignable()));
    }

    /**
     * @return Iff the depended assignment problem has been solved.
     */
    public boolean dependedProblemHasBeenSolved() {
        for (StubDependedReceiver reciever : dependedProblemRecievers) {
            if (!expectedDepandedProblemRecievers.get(reciever).equals(reciever.getStubAssignable())) {
                return false;
            }
        }
        return true;
    }

    public void assertDependedProblemHasBeenSolved() {
        assertThat(dependedProblemRecievers)
                .allMatch(reciever -> expectedDepandedProblemRecievers.get(reciever).equals(reciever.getStubAssignable()));
    }
}
