package nl.smit.domain_problem.csp_solver.helpers;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import lombok.experimental.UtilityClass;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.Mockito.mock;

/**
 * Mock related helper functions.
 *
 * @author Jordi Smit on 23-2-2018.
 */
@UtilityClass
public class MockHelper {

    /**
     * @param clazz  The class to be mocked.
     * @param amount The amount of mocks.
     * @param <T>    The class to be mocked.
     * @return A list full of initialized mocks.
     */
    public static <M, T extends M> List<T> createMockList(Class<M> clazz, int amount) {
        return IntStream.range(0, amount).boxed().map(i -> (T) mock(clazz)).collect(Collectors.toList());
    }

    public static <M, T extends M> Set<T> createMockSet(Class<M> clazz, int amount) {
        return IntStream.range(0, amount).boxed().map(i -> (T) mock(clazz)).collect(Collectors.toSet());
    }


    public static <SK extends K, K, SV extends V, V> Map<SK, SV> createMockMap(Class<K> key, Class<V> value, int amount) {
        return IntStream.range(0, amount).boxed().collect(Collectors.toMap(e -> (SK) mock(key), e -> (SV) mock(value)));
    }


    public static <K, KS extends K> Map<KS, Integer> createMockIndexMapping(Class<K> key, int amount) {
        Map<KS, Integer> map = new LinkedHashMap<>();
        for (int i = 0; i < amount; i++) {
            map.put((KS) mock(key), i);
        }

        return map;
    }

    public static <KS extends K, K> BiMap<KS, Integer> createBiMapMockIndexMapping(Class<K> key, int amount) {
        Map<KS, Integer> map = createMockIndexMapping(key, amount);
        return HashBiMap.create(map);
    }
}
