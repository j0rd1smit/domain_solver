package nl.smit.domain_problem.csp_solver.problem.assignement;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import lombok.*;
import nl.smit.domain_problem.csp_solver.exception.UnsolvableException;
import nl.smit.domain_problem.csp_solver.model.ObservableModel;
import nl.smit.domain_problem.csp_solver.problem.Assignable;
import nl.smit.domain_problem.csp_solver.problem.ConstraintSatisfactionProblem;
import nl.smit.domain_problem.csp_solver.problem.Receiver;
import nl.smit.domain_problem.csp_solver.problem.SolutionCommand;
import nl.smit.domain_problem.csp_solver.problem.assignement.constrain.AssignmentProblemConstrainVisitor;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.chocosolver.solver.variables.IntVar;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Resposible for solving an assingment problem.
 *
 * @author Jordi Smit on 26-2-2018.
 */
@RequiredArgsConstructor
@ToString(exclude = "model")
@EqualsAndHashCode
public class AssignmentProblem<A extends Assignable, R extends Receiver<A>> implements ConstraintSatisfactionProblem, ObservableModel.ModelObserver {
    private static final String
            INVARIANCE_ASSIGNEMENT = "Invariance error: The index mapping is incorrect for: %s, %s";

    /**
     * Solution output related.
     */
    private final AssingmentCommandFactory<A, R> assingmentCommandFactory;
    @MonotonicNonNull
    private SolutionCommand solutionCommand = null;

    /**
     * Assingment problem related.
     */
    @Getter(AccessLevel.PROTECTED)
    private final ObservableModel model;
    private final BiMap<A, Integer> assignableIndexMapping;
    private final Map<R, IntVar> recieverVarMapping;


    @Override
    @EnsuresNonNull("solutionCommand")
    public void findSolution() throws UnsolvableException {
        model.solve();
        assert solutionCommand != null : "The updateSolution should have been called";
    }

    @Override
    @EnsuresNonNull("solutionCommand")
    public void updateSolution() {
        Map<R, A> assignments = new HashMap<>();

        for (R receiver : recieverVarMapping.keySet()) {
            A assignable = getAssignedAssignable(receiver);

            assignments.put(receiver, assignable);
        }

        solutionCommand = assingmentCommandFactory.create(assignments);
    }

    private A getAssignedAssignable(R receiver) {
        int assignableIndex = recieverVarMapping.get(receiver).getValue();
        invarianceCheckAssignement(assignableIndex);

        return assignableIndexMapping.inverse().get(assignableIndex);
    }

    private void invarianceCheckAssignement(int assignableIndex) {
        String message = String.format(INVARIANCE_ASSIGNEMENT, assignableIndex, assignableIndexMapping.inverse());
        Preconditions.checkArgument(assignableIndexMapping.inverse().containsKey(assignableIndex), message);
    }

    @Override
    public SolutionCommand getSolution() throws UnsolvableException {
        if (solutionCommand == null) {
            findSolution();
        }

        return solutionCommand;
    }

    /**
     * Adds a new constrain.
     * Each constrain should only be added once.
     *
     * @param assignmentProblemConstrainVisitor The constrain to be added.
     */
    void addConstrain(AssignmentProblemConstrainVisitor assignmentProblemConstrainVisitor) {
        assignmentProblemConstrainVisitor.apply(model, assignableIndexMapping, recieverVarMapping);
    }


    //TODO
    protected Collection<R> getReceivers() {
        return recieverVarMapping.keySet();
    }

    protected IntVar getIntVar(R receiver) {
        return recieverVarMapping.get(receiver);
    }


    protected Collection<A> getAssignable() {
        return assignableIndexMapping.keySet();
    }

    protected int getAssignableIndex(A assignable) {
        return assignableIndexMapping.get(assignable);
    }

}
