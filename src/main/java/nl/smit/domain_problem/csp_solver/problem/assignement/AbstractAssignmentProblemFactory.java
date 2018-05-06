package nl.smit.domain_problem.csp_solver.problem.assignement;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import nl.smit.domain_problem.csp_solver.exception.UnsolvableException;
import nl.smit.domain_problem.csp_solver.model.ObservableModel;
import nl.smit.domain_problem.csp_solver.problem.Assignable;
import nl.smit.domain_problem.csp_solver.problem.Receiver;
import nl.smit.domain_problem.csp_solver.problem.assignement.constrain.MaxAmountOfAssignmentConstraint;
import nl.smit.domain_problem.csp_solver.problem.domain.DomainFactory;
import nl.smit.domain_problem.csp_solver.helpers.ImmutableHelper;
import org.chocosolver.solver.variables.IntVar;

import java.util.List;
import java.util.Map;

/**
 * [Explation]
 *
 * @author Jordi Smit on 27-4-2018.
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
abstract class AbstractAssignmentProblemFactory<A extends Assignable, R extends Receiver<A>, E extends AssignmentProblem<A, R>> {
    private final AssingmentCommandFactory<A, R> assingmentCommandFactory;
    private final DomainFactory domainFactory;
    private final MaxAmountOfAssignmentConstraint amountOfOccurencesConstraint;


    /**
     * @param model       The model to be used.
     * @param assignables The assingable object.
     * @param recievers   The recieving object.
     * @return A new intialized {@link AssignmentProblem}.
     * @throws UnsolvableException When no valid assingment option exists.
     */
    public E create(ObservableModel model, List<A> assignables, List<R> recievers) throws UnsolvableException {
        E assignmentProblem = make(model, assignables, recievers);

        initAssignmentProblem(model, assignmentProblem);

        return assignmentProblem;
    }

    private E make(ObservableModel model, List<A> assignables, List<R> recievers) throws UnsolvableException {
        BiMap<A, Integer> assignableIndexMapping = createAssignableIndexMapping(assignables);
        Map<R, IntVar> recieversVarMapping = ImmutableHelper.makeImmutable(domainFactory.create(model, assignableIndexMapping, recievers));

        return create(assingmentCommandFactory, model, assignableIndexMapping, recieversVarMapping);
    }

    private BiMap<A, Integer> createAssignableIndexMapping(final List<A> assignables) {
        ImmutableBiMap.Builder<A, Integer> builder = ImmutableBiMap.builder();

        for (int i = 0; i < assignables.size(); i++) {
            builder.put(assignables.get(i), i);
        }

        return builder.build();
    }


    protected abstract E create(
            AssingmentCommandFactory<A, R> assingmentCommandFactory,
            ObservableModel model,
            BiMap<A, Integer> assignableIndexMapping,
            Map<R, IntVar> recieversVarMapping);


    void initAssignmentProblem(ObservableModel model, E assignmentProblem) {
        initConstrains(assignmentProblem);
        model.addObserver(assignmentProblem);
    }

    private void initConstrains(AssignmentProblem<A, R> assignmentProblem) {
        assignmentProblem.addConstrain(amountOfOccurencesConstraint);
    }

}
