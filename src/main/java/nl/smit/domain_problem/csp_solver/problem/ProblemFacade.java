package nl.smit.domain_problem.csp_solver.problem;

import nl.smit.domain_problem.csp_solver.exception.UnsolvableException;
import nl.smit.domain_problem.csp_solver.model.ObservableModel;
import nl.smit.domain_problem.csp_solver.problem.assignement.*;
import nl.smit.domain_problem.csp_solver.problem.assignement.constrain.DependedExclusionConstrain;
import nl.smit.domain_problem.csp_solver.problem.assignement.constrain.MaxAmountOfAssignmentConstraint;
import nl.smit.domain_problem.csp_solver.problem.composite.CompositeAssingmentCommandFactory;
import nl.smit.domain_problem.csp_solver.problem.domain.DomainFactory;

import java.util.List;

/**
 * Responsible for simplifing factory creations.
 *
 * @author Jordi Smit on 18-3-2018.
 */
public class ProblemFacade {

    /**
     * @param <A> The assignable class.
     * @param <R> The reciever class.
     * @return A new {@link AssignmentProblemFactory}.
     */
    public <A extends Assignable, R extends Receiver<A>> AssignmentProblemFactory<A, R> assignmentProblemFactory() {
        AssingmentCommandFactory<A, R> assingmentCommandFactory = assingmentCommandFactory();
        DomainFactory domainFactory = domainFactory();
        MaxAmountOfAssignmentConstraint maxAmountOfAssignmentConstraint = maxAmountOfAssignmentConstraint();

        return new AssignmentProblemFactory<>(assingmentCommandFactory, domainFactory, maxAmountOfAssignmentConstraint);
    }

    private <A extends Assignable, R extends Receiver<A>> AssingmentCommandFactory<A, R> assingmentCommandFactory() {
        return new AssingmentCommandFactory<>();
    }

    private DomainFactory domainFactory() {
        return new DomainFactory();
    }

    private MaxAmountOfAssignmentConstraint maxAmountOfAssignmentConstraint() {
        return new MaxAmountOfAssignmentConstraint();
    }

    /**
     * @return A new {@link ObservableModel}.
     */
    public ObservableModel observableModel() {
        return new ObservableModel();
    }

    /**
     * @param model       The model that is being used.
     * @param assignables The assignable options.
     * @param recievers   The receivers.
     * @param <A>         The assignable class.
     * @param <R>         The reciever class.
     * @return A new {@link AssignmentProblem}.
     * @throws UnsolvableException When there are no valid assingement options.
     */
    public <A extends Assignable, R extends Receiver<A>> AssignmentProblem<A, R> assignmentProblem(ObservableModel model, List<A> assignables, List<R> recievers) throws UnsolvableException {
        AssignmentProblemFactory<A, R> problemFactory = assignmentProblemFactory();

        return problemFactory.create(model, assignables, recievers);
    }


    public <A extends DependentAssignable, R extends DependentReceiver<A>> DependentAssignmentProblem<A, R> dependentAssignmentProblem(ObservableModel model, List<A> assignables, List<R> recievers) throws UnsolvableException {
        DependentAssignmentProblemFactory<A, R> problemFactory = dependentAssignmentProblemFactory();

        return problemFactory.create(model, assignables, recievers);
    }

    private <A extends DependentAssignable, R extends DependentReceiver<A>> DependentAssignmentProblemFactory<A, R> dependentAssignmentProblemFactory() {
        AssingmentCommandFactory<A, R> assingmentCommandFactory = assingmentCommandFactory();
        DomainFactory domainFactory = domainFactory();
        MaxAmountOfAssignmentConstraint maxAmountOfAssignmentConstraint = maxAmountOfAssignmentConstraint();

        return new DependentAssignmentProblemFactory<>(assingmentCommandFactory, domainFactory, maxAmountOfAssignmentConstraint);
    }

    /**
     * @return A new {@link CompositeAssingmentCommandFactory}.
     */
    public CompositeAssignmentProblemFactory compositeAssignmentProblemFactory() {
        CompositeAssingmentCommandFactory commandFactory = new CompositeAssingmentCommandFactory();
        DependedExclusionConstrain dependedExclusionConstrain = new DependedExclusionConstrain();

        return new CompositeAssignmentProblemFactory(commandFactory, dependedExclusionConstrain);
    }

}
