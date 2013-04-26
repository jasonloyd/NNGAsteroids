package edu.unm.cs523.gasteriods.genetic;

import edu.unm.cs523.gasteriods.game.Eyes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * An individual Gene that maps a Neural Network.  Each Gene contains the edges in the Neural Network, connecting
 * nodes identified by number.  In addition a weight is associated with each edge and if it is active.  To keep the
 * continuity of the information the Gene represent, a feature id is stored with each gene and should be used
 * during mutation, crossover, etc to align genes across Chromosomes.
 *
 * @author John Ericksen
 */
public class Gene {

    private static final Random RAND = new Random(System.currentTimeMillis());

    public static final int BIAS_NODE = 0;
    public static final int[] EYE_NODES;
    public static final int[] OUTPUT_NODES;
    public static final AtomicInteger nodeIndex;
    public static final List<Integer> ALL_GIVEN_NODES = new ArrayList<Integer>();

    static{
        ALL_GIVEN_NODES.add(BIAS_NODE);
        EYE_NODES = new int[Eyes.EYE_COUNT];
        for(int i = 0; i < Eyes.EYE_COUNT; i++){
            EYE_NODES[i] = i + 1;
            ALL_GIVEN_NODES.add(EYE_NODES[i]);
        }

        OUTPUT_NODES = new int[4];
        for(int i = 0; i < 4; i++){
            OUTPUT_NODES[i] = Eyes.EYE_COUNT + 1 + i;
            ALL_GIVEN_NODES.add(OUTPUT_NODES[i]);
        }

        nodeIndex = new AtomicInteger(Eyes.EYE_COUNT + 5);
    }

    private final int feature;
    private final int from;
    private final int to;
    private float weight;
    private boolean active;

    public Gene(int feature, int from, int to, float weight, boolean active) {
        this.feature = feature;
        this.from = from;
        this.to = to;
        this.weight = weight;
        this.active = active;
    }

    public int getFeature() {
        return feature;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight){
        this.weight = weight;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "[" + feature + "," + from + "," + to + "," + weight + "," + active + ']';
    }

    public Gene copy() {
        return new Gene(feature, from, to, weight, active);
    }

    public static int getNextNodeId(){
        return nodeIndex.getAndIncrement();
    }
}
