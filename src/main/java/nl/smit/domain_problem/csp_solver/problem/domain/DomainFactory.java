package nl.smit.domain_problem.csp_solver.problem.domain;

import com.google.common.collect.ImmutableMap;
import com.google.common.primitives.Ints;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import nl.smit.domain_problem.csp_solver.exception.UnsolvableDomainException;
import nl.smit.domain_problem.csp_solver.problem.Assignable;
import nl.smit.domain_problem.csp_solver.problem.Receiver;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Responsible for creating valid domain without including invalid options.
 *
 * @author Jordi Smit on 25-2-2018.
 */
@RequiredArgsConstructor
@EqualsAndHashCode
public class DomainFactory {
    private static final String
            INVALID_DOMAIN = "There is no valid domain for: %s";

    /**
     * Creates an mapping between the Recievers and their variable.
     * Whereby each variable only has valid options in their domain.
     *
     * @param model                  The model who will contain the variables.
     * @param assignableIndexMapping The values of the assignables.
     * @param recievers              The recievers that need an variable.
     * @return mapping between the Recievers and their variable
     * @throws UnsolvableDomainException When there exits no valid domains for a Recievers.
     */
    public <A extends Assignable, R extends Receiver<A>> Map<R, IntVar> create(Model model, Map<A, Integer> assignableIndexMapping, Collection<R> recievers) throws UnsolvableDomainException {
        ImmutableMap.Builder<R, IntVar> builder = ImmutableMap.builder();

        int i = 0;
        for (R receiver : recievers) {
            String name = receiver.getClass().getSimpleName() + i++;
            IntVar var = model.intVar(name, createDomain(assignableIndexMapping, receiver));
            builder.put(receiver, var);
        }

        return builder.build();
    }

    private <A extends Assignable, R extends Receiver<A>> int[] createDomain(Map<A, Integer> assignableIndexMapping, R reciever) throws UnsolvableDomainException {
        List<Integer> domain = new LinkedList<>();
        for (Map.Entry<A, Integer> entry : assignableIndexMapping.entrySet()) {
            A assignable = entry.getKey();
            if (reciever.isValidOption(assignable)) {
                int valueIndex = entry.getValue();
                domain.add(valueIndex);
            }
        }

        if (domain.isEmpty()) {
            throw new UnsolvableDomainException(String.format(INVALID_DOMAIN, reciever));
        }

        return Ints.toArray(domain);
    }
}
