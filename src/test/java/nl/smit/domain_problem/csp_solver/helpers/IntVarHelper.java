package nl.smit.domain_problem.csp_solver.helpers;

import lombok.experimental.UtilityClass;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import org.mockito.Mockito;
import org.mockito.internal.util.MockUtil;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;

/**
 * [Explation]
 *
 * @author Jordi Smit on 3-3-2018.
 */
@UtilityClass
public class IntVarHelper {

    public static <RS extends R, R> Map<RS, IntVar> createRecieverIntVarMapping(Model model, Class<R> clazz, int amount) {
        return createRecieverIntVarMapping(model, clazz, amount, 0, 1);
    }

    public static <RS extends R, R> Map<RS, IntVar> createRecieverIntVarMapping(Model model, Class<R> clazz, int amount, int upperBound) {
        return createRecieverIntVarMapping(model, clazz, amount, 0, upperBound);
    }

    public static <RS extends R, R> Map<RS, IntVar> createRecieverIntVarMapping(Model model, Class<R> clazz, int amount, int lowerBound, int upperBound) {
        Map<RS, IntVar> map = new HashMap<>();
        for (int i = 0; i < amount; i++) {
            RS mock = (RS) mock(clazz);
            IntVar var = model.intVar(lowerBound, upperBound);

            map.put(mock, var);
        }

        if (MockUtil.isSpy(model)) {
            Mockito.reset(model);
        }


        return map;
    }
}
