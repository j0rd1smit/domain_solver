package nl.smit.domain_problem.csp_solver.problem.assignement.constrain;

import org.chocosolver.solver.Model;

import java.util.Set;

/**
 * Implements a depended exclusion constrain.
 *
 * @author Jordi Smit on 25-4-2018.
 */
public class DependedExclusionConstrain implements DependedProblemContrainVisitor {
    @Override
    public void apply(Model model, Set<AssingablePair> incompatibleAssignablePairs, Set<ReceiverPair> dependedReceiverPairs) {
        for (ReceiverPair receiverPair : dependedReceiverPairs) {
            for (AssingablePair assingablePair : incompatibleAssignablePairs) {
                model.ifThen(
                        model.arithm(receiverPair.getVariableReceiver(), "=", assingablePair.getIndexAssignable()),
                        model.arithm(receiverPair.getVariableDependentReceiver(), "!=", assingablePair.getIndexdependentAssignable())
                );
            }
        }
    }


}
