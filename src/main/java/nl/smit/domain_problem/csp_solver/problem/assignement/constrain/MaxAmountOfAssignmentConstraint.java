package nl.smit.domain_problem.csp_solver.problem.assignement.constrain;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import nl.smit.domain_problem.csp_solver.problem.Assignable;
import nl.smit.domain_problem.csp_solver.problem.Receiver;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;

import java.util.Map;

/**
 * Defines the max amount of assignement constrain.
 *
 * @author Jordi Smit on 25-2-2018.
 */
@RequiredArgsConstructor
@EqualsAndHashCode
public class MaxAmountOfAssignmentConstraint implements AssignmentProblemConstrainVisitor {
    private static final String VAR_NAME_FORMAT = "Count_Of_%s";

    @Override
    public <A extends Assignable, R extends Receiver<A>> void apply(Model model, Map<A, Integer> assingableIndexMapping, Map<R, IntVar> recieverVarMapping) {
        IntVar[] varsArray = recieverVarMapping.values().toArray(new IntVar[0]);

        apply(model, varsArray, assingableIndexMapping);
    }

    private <A extends Assignable> void apply(Model model, IntVar[] vars, Map<A, Integer> assingableIndexMapping) {
        for (A assingable : assingableIndexMapping.keySet()) {
            int assingableIndex = assingableIndexMapping.get(assingable);
            int lowerLimit = assingable.getMinAssingment();
            int upperLimit = assingable.getMaxAssingment();

            String varName = String.format(VAR_NAME_FORMAT, assingable);
            IntVar limit = model.intVar(varName, lowerLimit, upperLimit);
            Constraint constraint = model.count(assingableIndex, vars, limit);
            constraint.post();
        }
    }

}
