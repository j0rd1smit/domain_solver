package nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.assignement;

import com.google.common.collect.BiMap;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.model.ObservableModel;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.Assignable;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.DependentAssignable;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.DependentReceiver;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.Receiver;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.assignement.constrain.DependedProblemContrainVisitor;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.assignement.constrain.DependedProblemContrainVisitor.AssingablePair;
import nl.smit.domain_problem.constraint_satisfaction_problem_solver.problem.assignement.constrain.DependedProblemContrainVisitor.ReceiverPair;

import org.chocosolver.solver.variables.IntVar;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Responsible for the depending problem related functionality.
 *
 * @author Jordi Smit on 25-4-2018.
 */
public class DependentAssignmentProblem<A extends DependentAssignable, R extends DependentReceiver<A>> extends AssignmentProblem<A, R> {

    /**
     * The default constructor.
     *
     * @param assingmentCommandFactory The {@link AssingmentCommandFactory} to be used.
     * @param model                    The {@link ObservableModel} to be used.
     * @param assignableIndexMapping   The assingable index mapping.
     * @param recieverVarMapping       The reciever variable mapping.
     */
    DependentAssignmentProblem(AssingmentCommandFactory<A, R> assingmentCommandFactory, ObservableModel model, BiMap<A, Integer> assignableIndexMapping, Map<R, IntVar> recieverVarMapping) {
        super(assingmentCommandFactory, model, assignableIndexMapping, recieverVarMapping);
    }


    /**
     * Adds a DependedProblemContrainVisitor.
     *
     * @param visitor                The visitor to be applied.
     * @param otherAssignmentProblem The related assignment problem.
     * @param <A2>                   The assignable class of the related assignment problem.
     * @param <R2>                   The receiver class of the related assignment problem.
     */
    <A2 extends Assignable, R2 extends Receiver<A2>> void addConstrain(DependedProblemContrainVisitor visitor, AssignmentProblem<A2, R2> otherAssignmentProblem) {
        Set<AssingablePair> assingablePairs = findIncompatibleAssignablePairs(otherAssignmentProblem);
        Set<ReceiverPair> receiverPairs = findDependedReceiverPairs(otherAssignmentProblem);

        visitor.apply(getModel(), assingablePairs, receiverPairs);
    }


    private <A2 extends Assignable, R2 extends Receiver<A2>> Set<AssingablePair> findIncompatibleAssignablePairs(AssignmentProblem<A2, R2> otherAssignmentProblem) {
        return getAssignable()
                .stream()
                .flatMap(dependedAssignable -> otherAssignmentProblem.getAssignable()
                        .stream()
                        .filter(assignable -> !dependedAssignable.isCompatible(assignable))
                        .map(assignable -> new AssingablePair(otherAssignmentProblem.getAssignableIndex(assignable), this.getAssignableIndex(dependedAssignable)))
                )
                .collect(Collectors.toSet());
    }

    private <A2 extends Assignable, R2 extends Receiver<A2>> Set<ReceiverPair> findDependedReceiverPairs(AssignmentProblem<A2, R2> otherAssignmentProblem) {
        return getReceivers()
                .stream()
                .flatMap(dependedReciever -> otherAssignmentProblem
                        .getReceivers()
                        .stream()
                        .filter(receiver -> dependedReciever.getDepandedRecievers().contains(receiver))
                        .map(receiver -> new ReceiverPair(otherAssignmentProblem.getIntVar(receiver), this.getIntVar(dependedReciever)))
                )
                .collect(Collectors.toSet());
    }


}
