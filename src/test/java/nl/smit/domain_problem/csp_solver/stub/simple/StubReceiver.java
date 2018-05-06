package nl.smit.domain_problem.csp_solver.stub.simple;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import nl.smit.domain_problem.csp_solver.problem.Assignable;
import nl.smit.domain_problem.csp_solver.problem.Receiver;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.function.Predicate;

/**
 * A stub for testing.
 *
 * @author Jordi Smit on 29-3-2018.
 */
@RequiredArgsConstructor
@ToString(exclude = {"isValidOption"})
public class StubReceiver implements Receiver<StubAssignable> {
    private final Predicate<StubAssignable> isValidOption;
    @Getter
    @Nullable
    private Assignable stubAssignable;


    @Override
    public boolean isValidOption(StubAssignable assignable) {
        return isValidOption.test(assignable);
    }

    @Override
    public void restoreOriginal() {
        stubAssignable = null;
    }

    @Override
    public void assign(StubAssignable assignable) {
        stubAssignable = assignable;
    }
}
