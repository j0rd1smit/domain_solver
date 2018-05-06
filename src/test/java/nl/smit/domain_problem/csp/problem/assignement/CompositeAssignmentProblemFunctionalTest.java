package nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.assignement;

import nl.smit.domain_problem.constraint_satisfaction_problem_solver.exception.UnsolvableException;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.helpers.extension.MockitoExtension;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.ProblemFacade;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.composite.CompositeProblem;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.stub.problem.DependedAssignementScenario;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.stub.problem.DependedAssignementScenarioFactory;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests the implementation of {@link CompositeProblem}.
 *
 * @author Jordi Smit on 2-3-2018.
 */
@ExtendWith(MockitoExtension.class)
@Tag("fast")
class CompositeAssignmentProblemFunctionalTest {

    /**
     * @return A stream of {@link DependedAssignementScenario}s that will be tested.
     */
    private static Stream<DependedAssignementScenario> scenarioSupplier() {
        DependedAssignementScenarioFactory factory = new DependedAssignementScenarioFactory();

        return Stream.of(factory.createSimpleScenario(), factory.createDepandedMultipleAssingment());
    }

    /**
     * Helper
     */
    private ProblemFacade problemFacade = new ProblemFacade();

    /**
     * Verifies that the assingment problem will be solved.
     */
    @ParameterizedTest
    @MethodSource("scenarioSupplier")
    void _getSolutionSolvesAssignmentProblemTest(DependedAssignementScenario scenario) throws UnsolvableException {
        CompositeProblem compositeProblem = createCompositeProblem(scenario);

        compositeProblem.getSolution().apply();

        scenario.assertAssignmentProblemHasBeenSolved();
    }

    private CompositeProblem createCompositeProblem(DependedAssignementScenario scenario) {
        CompositeAssignmentProblemFactory factory = problemFacade.compositeAssignmentProblemFactory();

        return factory.create(scenario.getAssignmentProblem(), scenario.getDependedProblem());
    }

    /**
     * Verifies that the depanded assingment problem will be solved for each scenario.
     *
     * @param scenario The scenario to solve.
     * @throws UnsolvableException Should not happen.
     */
    @ParameterizedTest
    @MethodSource("scenarioSupplier")
    void _getSolutionSolvesDependedProblem2Test(DependedAssignementScenario scenario) throws UnsolvableException {
        CompositeProblem compositeProblem = createCompositeProblem(scenario);


        compositeProblem.getSolution().apply();

        scenario.assertDependedProblemHasBeenSolved();
    }

    /**
     * Verifies that the depanded constrain result in a unsolvable problem.
     */
    @Test
    void _unsolvableDepandedProblemTest() {
        DependedAssignementScenarioFactory scenarioFactory = new DependedAssignementScenarioFactory();
        DependedAssignementScenario scenario = scenarioFactory.createSimpleUnsolvableScenario();
        CompositeProblem compositeProblem = createCompositeProblem(scenario);


        assertThatThrownBy(compositeProblem::findSolution)
                .isInstanceOf(UnsolvableException.class);
    }
}