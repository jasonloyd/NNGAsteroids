package edu.unm.cs523.gasteriods.game;

import edu.unm.cs523.gasteriods.genetic.Chromosome;

import java.util.Comparator;
import java.util.Map;

/**
 * @author Jason Loyd
 */
public class FitnessComparator implements Comparator<Chromosome> {

    Map<Chromosome, Integer> fitnessMap;

    public FitnessComparator(Map<Chromosome, Integer> fitnessMap) {
        this.fitnessMap = fitnessMap;
    }

    public int compare(Chromosome c1, Chromosome c2) {
        return fitnessMap.get(c2).compareTo(fitnessMap.get(c1));
    }
}
