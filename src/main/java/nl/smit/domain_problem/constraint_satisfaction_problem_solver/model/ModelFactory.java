package nl.smit.domain_problem.constraint_satisfaction_problem_solver.model;

/**
 * Responsible for creating new models.
 *
 * @author Jordi Smit, 10-2-2018.
 */
public class ModelFactory {
    /**
     * @return a new instances of {@link ObservableModel}.
     */
    public ObservableModel make() {
        return new ObservableModel();
    }
}
