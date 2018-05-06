package nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.assignement.constrain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

import java.util.Set;

/**
 * The interface of a depended problem constrain.
 *
 * @author Jordi Smit on 25-4-2018.
 */
public interface DependedProblemContrainVisitor {

    /**
     * Applies the depanded constrain.
     *
     * @param model                       The model.
     * @param incompatibleAssignablePairs The pairs of incompatible receiver variables.
     * @param dependedReceiverPairs       The pairs of depended receiver variables.
     */
    void apply(Model model, Set<AssingablePair> incompatibleAssignablePairs, Set<ReceiverPair> dependedReceiverPairs);

    @Value
    @Getter(value = AccessLevel.PACKAGE)
    class ReceiverPair {
        private final IntVar variableReceiver;
        private final IntVar variableDependentReceiver;
    }

    @Value
    @Getter(value = AccessLevel.PACKAGE)
    class AssingablePair {
        private final Integer indexAssignable;
        private final Integer indexdependentAssignable;
    }
}
