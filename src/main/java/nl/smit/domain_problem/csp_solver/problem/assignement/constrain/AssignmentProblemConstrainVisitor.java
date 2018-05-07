package nl.smit.domain_problem.csp_solver.problem.assignement.constrain;

import nl.smit.domain_problem.csp_solver.problem.Assignable;
import nl.smit.domain_problem.csp_solver.problem.Receiver;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

import java.util.Map;

/**
 * The vistor interface constrains for the {@link AssignmentProblem}.
 *
 * @author Jordi Smit on 25-2-2018.
 */
public interface AssignmentProblemConstrainVisitor {
    /**
     * @param model                  The model on which the contrain will be added.
     * @param assingableIndexMapping The mapping between the assignable and its value.
     * @param recieverVarMapping     The mapping between the recievers and their variable.
     * @param <A>                    The implementation of the Assignable.
     */
    <A extends Assignable, R extends Receiver<A>> void apply(Model model, Map<A, Integer> assingableIndexMapping, Map<R, IntVar> recieverVarMapping);
}
