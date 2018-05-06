package nl.smit.domain_problem.csp_solver.model;


import nl.smit.domain_problem.csp_solver.helpers.extension.MockitoExtension;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests the implementation of {@link Model}.
 *
 * @author Jordi Smit on 2-3-2018.
 */
@ExtendWith(MockitoExtension.class)
@Tag("fast")
class ModelTest {
    /**
     * Default values.
     */
    //TODO private final static int


    /**
     * Inputs.
     */
    //TODO

    /**
     * Outputs.
     */
    //TODO

    /**
     * Dependencies.
     */
    //TODO


    /**
     * The object under testing.
     */
    private Model model = new Model();

    @BeforeEach
    void beforeEachModelTest() {

    }


    /**
     * Verifies that constant already has a value.
     */
    @Test
    void _constantIntVarTest() {
        int value = 1;

        IntVar var = model.intVar(1);

        assertThat(var.getValue()).isEqualTo(value);
    }

    /**
     * Verifies that
     */
    @Test
    void _solveDomainTest() {
        final int expectedValue = 7;
        IntVar var = model.intVar(0, 10);
        model.arithm(var, "=", expectedValue).post();

        model.getSolver().solve();

        assertThat(var.getValue()).isEqualTo(expectedValue);
    }

    /**
     * Verifies that
     */
    @Test
    void _solvedDomainReturnsTrueTest() {
        assertThat(model.getSolver().solve()).isTrue();
    }

    /**
     * Verifies that
     */
    @Test
    void _unsolveAbleTest() {
        int value = 0;
        IntVar var = model.intVar(value);
        model.arithm(var, "!=", value).post();

        assertThat(model.getSolver().solve()).isFalse();
    }

    /**
     * Verifies that
     */
    @Test
    void _ifThenTest() {
        final int valueVar1 = 7;
        final int expectedValueVar2 = 9;
        IntVar var1 = model.intVar(valueVar1);
        IntVar var2 = model.intVar(0, expectedValueVar2 + 1);
        model.ifThen(
                model.arithm(var1, "=", valueVar1),
                model.arithm(var2, "=", expectedValueVar2)
        );

        model.getSolver().solve();

        assertThat(var2.getValue()).isEqualTo(expectedValueVar2);
    }

    /**
     * Verifies that
     */
    @Test
    void _ifThenExlusionTest() {
        final int valueVar1 = 7;
        IntVar var1 = model.intVar(valueVar1);
        IntVar var2 = model.intVar(0, 1);
        model.ifThen(
                model.arithm(var1, "=", valueVar1),
                model.arithm(var2, "!=", 0)
        );
        model.getSolver().solve();

        assertThat(var2.getValue()).isEqualTo(1);
    }

    /**
     * Verifies that
     */
    @Test
    void _getDomainValuesTest() {
        int lowerBound = 0;
        int upperBound = 10;
        List<Integer> expectedResult = IntStream.range(lowerBound, upperBound + 1).boxed().collect(Collectors.toList());

        IntVar var = model.intVar(lowerBound, upperBound);

        assertThat(var.iterator()).containsExactlyElementsOf(expectedResult);
    }

    /**
     * Verifies that
     */
    @Test
    void _orTest() {
        IntVar var = model.intVar(0, 10);

        model.or(model.arithm(var, "=", 5), model.arithm(var, "=", 4)).post();

        model.getSolver().solve();
        assertThat(var.getValue()).isEqualTo(5);
        model.getSolver().solve();
        assertThat(var.getValue()).isEqualTo(4);
    }
}

