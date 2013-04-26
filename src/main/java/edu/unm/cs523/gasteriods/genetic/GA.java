package edu.unm.cs523.gasteriods.genetic;

import java.util.Collection;
import java.util.Map;

/**
 * Genetic Algorithm for evolving a collection of Chromosomes
 *
 * @author John Ericksen
 */
public interface GA {

    /**
     * Evolves the input Chromosomes into the next generation (return value).  Input map includes a fitness
     * as the map value.
     *
     * @param fitnessMap
     * @return next generation
     */
    Collection<Chromosome> evolve(Map<Chromosome, Integer> fitnessMap);
}
