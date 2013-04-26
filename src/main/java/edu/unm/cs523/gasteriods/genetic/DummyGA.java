package edu.unm.cs523.gasteriods.genetic;

import java.util.Collection;
import java.util.Map;

/**
 * Non-evolving placeholder GA.
 *
 * @author John Ericksen
 */
public class DummyGA implements GA{

    public Collection<Chromosome> evolve(Map<Chromosome, Integer> fitnessMap) {
        return fitnessMap.keySet();
    }
}
