package nl.smit.domain_problem.csp_solver.problem.assignement;

import com.google.common.collect.BiMap;
import nl.smit.domain_problem.csp_solver.model.ObservableModel;
import nl.smit.domain_problem.csp_solver.problem.Assignable;
import nl.smit.domain_problem.csp_solver.problem.Receiver;
import nl.smit.domain_problem.csp_solver.problem.assignement.constrain.MaxAmountOfAssignmentConstraint;
import nl.smit.domain_problem.csp_solver.problem.domain.DomainFactory;
import org.chocosolver.solver.variables.IntVar;

import java.util.Map;


/**
 * Responsible for creating and initialzing {@link AssignmentProblem}s.
 *
 * @author Jordi Smit on 25-2-2018.
 */
public class AssignmentProblemFactory<A extends Assignable, R extends Receiver<A>> extends AbstractAssignmentProblemFactory<A, R, AssignmentProblem<A, R>> {
    public AssignmentProblemFactory(AssingmentCommandFactory<A, R> assingmentCommandFactory, DomainFactory domainFactory, MaxAmountOfAssignmentConstraint amountOfOccurencesConstraint) {
        super(assingmentCommandFactory, domainFactory, amountOfOccurencesConstraint);
    }

    @Override
    protected AssignmentProblem<A, R> create(AssingmentCommandFactory<A, R> assingmentCommandFactory, ObservableModel model, BiMap<A, Integer> assignableIndexMapping, Map<R, IntVar> recieversVarMapping) {
        return new AssignmentProblem<>(assingmentCommandFactory, model, assignableIndexMapping, recieversVarMapping);
    }
}
