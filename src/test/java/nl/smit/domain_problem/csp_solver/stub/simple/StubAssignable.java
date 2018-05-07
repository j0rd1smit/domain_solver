package nl.smit.domain_problem.csp_solver.stub.simple;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.smit.domain_problem.csp_solver.problem.Assignable;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * A stub for testing.
 *
 * @author Jordi Smit on 29-3-2018.
 */
@RequiredArgsConstructor
//@ToString
public class StubAssignable implements Assignable {
    private final static AtomicInteger NEXT_ID = new AtomicInteger();

    private final int id = NEXT_ID.getAndIncrement() - 1;

    @Getter
    private final int minAssingment;

    @Getter
    private final int maxAssingment;

    @Override
    public String toString() {
        return "A" + id;
    }
}
