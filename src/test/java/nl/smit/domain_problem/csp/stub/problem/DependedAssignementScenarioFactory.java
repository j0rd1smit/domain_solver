package nl.smit.domain_problem.constraint_satisfaction_problem_solver.stub.problem;

import com.google.common.collect.ImmutableMap;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.Receiver;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.stub.depended.StubDependedAssignable;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.stub.depended.StubDependedReceiver;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.stub.simple.StubAssignable;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.stub.simple.StubReceiver;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Creates fixed {@link DependedAssignementScenario}.
 *
 * @author Jordi Smit on 23-4-2018.
 */
public class DependedAssignementScenarioFactory {
    private static final int
            AMOUNT_OF_ASSIGNABLES_MULTIPLE = 3,
            MIN_ASSINGMENT = 0,
            MAX_ASSIGNEMENT = 1;

    /**
     * R0 - A0 - DA0 - DR0
     *
     * @return A very simple assignment scenario without any choices.
     */
    public DependedAssignementScenario createSimpleScenario() {
        return createSimple(true);
    }

    private DependedAssignementScenario createSimple(boolean solveable) {
        List<StubAssignable> assignables = createStubAssignable(1);
        List<StubReceiver> recievers = Collections.singletonList(new StubReceiver(e -> true));
        Map<StubReceiver, StubAssignable> expectedProblemRecievers = ImmutableMap.of(recievers.get(0), assignables.get(0));

        List<StubDependedAssignable> dependedAssignables = Collections.singletonList(new StubDependedAssignable(MIN_ASSINGMENT, MAX_ASSIGNEMENT, e -> solveable));
        List<StubDependedReceiver> dependedRecievers = createSimpleDependedRecievers(recievers);
        Map<StubDependedReceiver, StubDependedAssignable> expectedDepandedProblemRecievers = ImmutableMap.of(dependedRecievers.get(0), dependedAssignables.get(0));

        return DependedAssignementScenario
                .builder()
                .problemAssignables(assignables)
                .problemRecievers(recievers)
                .expectedProblemRecievers(expectedProblemRecievers)
                .dependedAssignables(dependedAssignables)
                .dependedProblemRecievers(dependedRecievers)
                .expectedDepandedProblemRecievers(expectedDepandedProblemRecievers)
                .build();
    }

    private List<StubDependedReceiver> createSimpleDependedRecievers(List<StubReceiver> recievers) {
        StubDependedReceiver dependedReceiver = new StubDependedReceiver(e -> true, new HashSet<>(recievers));

        return Collections.singletonList(dependedReceiver);
    }

    /**
     * R0 - A0   DA0 - DR0
     *
     * @return A very simple assignment scenario without any choices without compatible depanded assingables.
     */
    public DependedAssignementScenario createSimpleUnsolvableScenario() {
        return createSimple(false);
    }

    /**
     * R0-A0  DA0-DR0
     * \       /
     * A1--DA1
     * <p>
     * R1-A2--DA2--DR1
     *
     * @return A depanded multiple assignement problem scenario.
     */
    public DependedAssignementScenario createDepandedMultipleAssingment() {
        List<StubAssignable> assignables = createStubAssignable(AMOUNT_OF_ASSIGNABLES_MULTIPLE);
        List<StubReceiver> recievers = createStubReciever(assignables);
        Map<StubReceiver, StubAssignable> expectedProblemRecievers = createExpectedProblemRecievers(assignables, recievers);


        List<StubDependedAssignable> dependedAssignables = createDependedAssignables(assignables);
        List<StubDependedReceiver> dependedRecievers = createDependedRecievers(dependedAssignables, recievers);
        Map<StubDependedReceiver, StubDependedAssignable> expectedDepandedProblemRecievers = createExpectedDepandedProblemRecievers(dependedAssignables, dependedRecievers);

        return DependedAssignementScenario
                .builder()
                .problemAssignables(assignables)
                .problemRecievers(recievers)
                .expectedProblemRecievers(expectedProblemRecievers)
                .dependedAssignables(dependedAssignables)
                .dependedProblemRecievers(dependedRecievers)
                .expectedDepandedProblemRecievers(expectedDepandedProblemRecievers)
                .build();
    }

    private List<StubAssignable> createStubAssignable(int amount) {
        return IntStream.range(0, amount)
                .boxed()
                .map(i -> new StubAssignable(MIN_ASSINGMENT, MAX_ASSIGNEMENT))
                .collect(Collectors.toList());
    }

    private List<StubReceiver> createStubReciever(List<StubAssignable> assignables) {
        List<StubReceiver> recievers = new ArrayList<>();

        List<StubAssignable> assignablesOption0 = Arrays.asList(assignables.get(0), assignables.get(1));
        List<StubAssignable> assignablesOption1 = Collections.singletonList(assignables.get(2));

        recievers.add(new StubReceiver(assignablesOption0::contains));
        recievers.add(new StubReceiver(assignablesOption1::contains));

        return recievers;
    }

    private List<StubDependedAssignable> createDependedAssignables(List<StubAssignable> assignables) {
        List<StubDependedAssignable> dependedAssignables = new ArrayList<>();

        List<StubAssignable> assignablesOption0 = new ArrayList<>();
        List<StubAssignable> assignablesOption1 = Collections.singletonList(assignables.get(1));
        List<StubAssignable> assignablesOption2 = Collections.singletonList(assignables.get(2));

        dependedAssignables.add(createStubDependedAssignable(assignablesOption0));
        dependedAssignables.add(createStubDependedAssignable(assignablesOption1));
        dependedAssignables.add(createStubDependedAssignable(assignablesOption2));

        return dependedAssignables;
    }

    private StubDependedAssignable createStubDependedAssignable(List<StubAssignable> validDependedOptions) {
        return new StubDependedAssignable(MIN_ASSINGMENT, MAX_ASSIGNEMENT, validDependedOptions::contains);
    }

    private List<StubDependedReceiver> createDependedRecievers(List<StubDependedAssignable> dependedAssignables, List<StubReceiver> relatedRecievers) {
        List<StubDependedReceiver> dependedRecievers = new ArrayList<>();
        List<StubDependedAssignable> assignablesOption0 = Arrays.asList(dependedAssignables.get(0), dependedAssignables.get(1));
        List<StubDependedAssignable> assignablesOption1 = Collections.singletonList(dependedAssignables.get(2));

        dependedRecievers.add(createStubDependedReciever(assignablesOption0, relatedRecievers.get(0)));
        dependedRecievers.add(createStubDependedReciever(assignablesOption1, relatedRecievers.get(1)));

        return dependedRecievers;
    }

    private StubDependedReceiver createStubDependedReciever(List<StubDependedAssignable> assignablesOptions, Receiver<?> relatedReceiver) {
        StubDependedReceiver receiver = new StubDependedReceiver(assignablesOptions::contains, Collections.singleton(relatedReceiver));

        return receiver;
    }

    private Map<StubReceiver, StubAssignable> createExpectedProblemRecievers(List<StubAssignable> assignables, List<StubReceiver> recievers) {
        Map<StubReceiver, StubAssignable> expected = new HashMap<>();

        expected.put(recievers.get(0), assignables.get(1));
        expected.put(recievers.get(1), assignables.get(2));

        return expected;
    }

    private Map<StubDependedReceiver, StubDependedAssignable> createExpectedDepandedProblemRecievers(List<StubDependedAssignable> assignables, List<StubDependedReceiver> recievers) {
        Map<StubDependedReceiver, StubDependedAssignable> expected = new HashMap<>();

        expected.put(recievers.get(0), assignables.get(1));
        expected.put(recievers.get(1), assignables.get(2));

        return expected;
    }

}


