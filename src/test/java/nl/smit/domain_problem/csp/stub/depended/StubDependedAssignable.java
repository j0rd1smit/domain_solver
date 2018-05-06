package nl.smit.domain_problem.constraint_satisfaction_problem_solver.stub.depended;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.Assignable;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.DependentAssignable;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.stub.simple.StubAssignable;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

/**
 * A stub for testing.
 *
 * @author Jordi Smit on 4-4-2018.
 */
@RequiredArgsConstructor
public class StubDependedAssignable implements DependentAssignable {
    private final static AtomicInteger NEXT_ID = new AtomicInteger();

    private final int id = NEXT_ID.getAndIncrement() - 1;

    @Getter
    private final int minAssingment;

    @Getter
    private final int maxAssingment;

    private final Predicate<StubAssignable> isValidOption;

    @Override
    public boolean isCompatible(Assignable assignable) {
        if (assignable instanceof StubAssignable) {
            StubAssignable stubAssignable = (StubAssignable) assignable;
            return isValidOption.test(stubAssignable);
        }
        return false;
    }

    @Override
    public String toString() {
        return "TA" + id;
    }
}
