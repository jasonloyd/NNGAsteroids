package edu.unm.cs523.gasteriods.neural;

import java.util.HashMap;
import java.util.Map;

/**
 * Basic Neural Network Perceptron.  This Perceptron sums the weighted values provided by all the input edges, feeds
 * this value into the Sigmoid function and returns the result via the getValue() method.
 *
 * @author John Ericksen
 */
public class Perceptron implements Node {

    private final Map<Edge, Float> edges = new HashMap<Edge, Float>();

    public float getValue(){
        double sum = 0;

        for (Map.Entry<Edge, Float> edgeFloatEntry : edges.entrySet()) {
            sum += edgeFloatEntry.getValue() * edgeFloatEntry.getKey().getOutput();
        }

        // Sigmoid
        return (float)(1 / (1 + Math.exp(sum)));
    }

    public void addInputEdge(Edge edge, float weight){
        edges.put(edge, weight);
    }
}
