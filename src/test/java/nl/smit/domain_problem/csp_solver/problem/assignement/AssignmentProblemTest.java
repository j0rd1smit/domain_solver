package nl.smit.domain_problem.csp_solver.problem.assignement;

import com.google.common.collect.BiMap;
import nl.smit.domain_problem.csp_solver.model.ObservableModel;
import nl.smit.domain_problem.csp_solver.stub.simple.StubAssignable;
import nl.smit.domain_problem.csp_solver.stub.simple.StubReceiver;
import org.chocosolver.solver.variables.IntVar;

import java.util.Map;

/**
 * Implements the generic {@link GenericAssignmentProblemTest}.
 *
 * @author Jordi Smit on 26-4-2018.
 */
public class AssignmentProblemTest extends GenericAssignmentProblemTest<StubAssignable, StubReceiver> {
    @Override
    protected Class<StubAssignable> getAssignableClass() {
        return StubAssignable.class;
    }

    @Override
    protected Class<StubReceiver> getReceiverClass() {
        return StubReceiver.class;
    }

    @Override
    protected AssignmentProblem<StubAssignable, StubReceiver> create(AssingmentCommandFactory<StubAssignable, StubReceiver> assingmentCommandFactory, ObservableModel model, BiMap<StubAssignable, Integer> assignableIndexMapping, Map<StubReceiver, IntVar> recieverVarMapping) {
        return new AssignmentProblem<>(assingmentCommandFactory, model, assignableIndexMapping, recieverVarMapping);
    }


}
