package nl.smit.domain_problem.constraint_satisfaction_problem_solver.stub.depended;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.DependentReceiver;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.Receiver;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Set;
import java.util.function.Predicate;

/**
 * [Explation]
 *
 * @author Jordi Smit on 11-4-2018.
 */
@RequiredArgsConstructor
@ToString(exclude = {"isValidOption"})
public class StubDependedReceiver implements DependentReceiver<StubDependedAssignable> {

    private final Predicate<StubDependedAssignable> isValidOption;
    private final Set<Receiver<?>> depenededReceievers;
    @Getter
    private @Nullable StubDependedAssignable stubAssignable;


    @Override
    public boolean isValidOption(StubDependedAssignable assignable) {
        return isValidOption.test(assignable);
    }

    @Override
    public void restoreOriginal() {
        stubAssignable = null;
    }

    @Override
    public void assign(StubDependedAssignable assignable) {
        stubAssignable = assignable;
    }

    @Override
    public Set<Receiver<?>> getDepandedRecievers() {
        return ImmutableSet.copyOf(depenededReceievers);
    }
}
