package nl.smit.domain_problem.constraint_satisfaction_problem_solver.model;

import nl.smit.domain_problem.constraint_satisfaction_problem_solver.exception.UnsolvableException;
import org.chocosolver.solver.Model;

import java.util.HashSet;
import java.util.Set;

/**
 * Responsible for the communication between multiple users of the model.
 *
 * @author Jordi Smit on 6-4-2018.
 */
public class ObservableModel extends Model {
    private static final String
            UNSOLVABLE = "The problem has no solutions";

    private final Set<ModelObserver> observers = new HashSet<>();


    /**
     * Adds an new observer.
     *
     * @param observer The observer to be added.
     */
    public void addObserver(ModelObserver observer) {
        observers.add(observer);
    }

    /**
     * Solves the model.
     *
     * @throws UnsolvableException When there is no solution for this model.
     */
    public void solve() throws UnsolvableException {
        boolean solutionHasBeenFound = getSolver().solve();
        if (!solutionHasBeenFound) {
            throw new UnsolvableException(UNSOLVABLE);
        }
        updateObservers();
    }

    private void updateObservers() {
        observers.forEach(ModelObserver::updateSolution);
    }


    /**
     * Specifies the interface for an observer of the {@link ObservableModel}.
     */
    public interface ModelObserver {
        /**
         * Updates the solution based on the current solution.
         */
        void updateSolution();
    }
}
