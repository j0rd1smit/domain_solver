package nl.smit.domain_problem.csp_solver.problem;

import com.google.common.collect.ImmutableSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Responsible for the DepandedReciever relationship of {@link }.
 *
 * @author Jordi Smit on 24-4-2018.
 */
public abstract class AbstractDependentReceiver<A extends Assignable> implements DependentReceiver<A> {
    /**
     * The set of related {@link Receiver}s.
     */
    private final Set<Receiver<?>> related = new HashSet<>();

    /**
     * Makes all {@link Receiver}s related to this.
     *
     * @param relatedRecievers The {@link Receiver}s.
     */
    public final <E extends Receiver> void addAll(Collection<E> relatedRecievers) {
        relatedRecievers.forEach(this::add);
    }

    /**
     * Makes the {@link Receiver} related to this.
     *
     * @param relatedReciever The {@link Receiver}.
     */
    public final <E extends Receiver> void add(E relatedReciever) {
        this.related.add(relatedReciever);
    }

    /**
     * Makes the {@link Receiver} unrelated.
     *
     * @param relatedReciever he {@link Receiver}.
     */
    public final <E extends Receiver> void remove(E relatedReciever) {
        this.related.remove(relatedReciever);
    }

    @Override
    public final Set<Receiver<?>> getDepandedRecievers() {
        return ImmutableSet.copyOf(related);
    }
}
