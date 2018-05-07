package nl.smit.domain_problem.csp_solver.helpers;

import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.Optional;

/**
 * A Collection utility class.
 *
 * @author Jordi Smit on 26-4-2018.
 */
@UtilityClass
public class CollectionHelper {

    /**
     * @param collection The collection to be accessed.
     * @param n          The element index starting form 0.
     * @param <E>        The class of the element.
     * @return The nth element.
     */
    public <E> E getNth(Collection<E> collection, int n) {
        Optional<E> option = collection.stream().skip(n).findFirst();
        if (!option.isPresent()) {
            throw new RuntimeException("Collection cannont be empty: " + collection);
        }
        return option.get();
    }

    /**
     * @param collection The collection to be accessed.
     * @param <E>        The class of the element.
     * @return The last element.
     */
    public <E> E getLast(Collection<E> collection) {
        int size = collection.size();
        return getNth(collection, size - 1);
    }
}
