package nl.smit.domain_problem.csp_solver.helpers;

import com.google.common.collect.ImmutableMap;
import lombok.experimental.UtilityClass;

import java.util.Map;


/**
 * A collection of Immutable related helpers functions.
 *
 * @author Jordi Smit on 28-2-2018.
 */
@UtilityClass
public class ImmutableHelper {

    /**
     * Transforms a map into an ImmutableMap.
     *
     * @param map The map to be transformed.
     * @param <K> The key class.
     * @param <V> The value class
     * @return A ImmutableMap map.
     */
    public static <K, V> ImmutableMap<K, V> makeImmutable(Map<K, V> map) {
        if (map instanceof ImmutableMap) {
            return (ImmutableMap<K, V>) map;
        }
        return ImmutableMap.copyOf(map);
    }
}
