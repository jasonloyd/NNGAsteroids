package edu.unm.cs523.gasteriods.neural;

import edu.unm.cs523.gasteriods.game.Eyes;
import edu.unm.cs523.gasteriods.game.Game;
import edu.unm.cs523.gasteriods.genetic.Chromosome;
import edu.unm.cs523.gasteriods.genetic.Gene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Builds a NeuralNetAI from the given game and Chromosome.
 *
 * @author John Ericksen
 */
public class AIFactory {

    /**
     * Produces a NeuralNetAI from the given Game and Chromosome.  This method wires up a Neural Network described
     * by the edges, weights and activations defined in the Chromosome.  Nodes either reference a Bias node, the Eyes,
     * the output GameInput instance or arbitrary hidden nodes.  Each node is identified by number.
     *
     * @see Gene for preset node ids.
     *
     * @param game input
     * @param chromosome definition
     * @return AI
     */
    public NeuralNetAI buildNeuralNetAI(Game game, Chromosome chromosome){

        IndexedEyes indexedEyes = new IndexedEyes(new Eyes(game));

        Map<Integer, Node> nodeMapping = new HashMap<Integer, Node>();

        //bias node
        nodeMapping.put(Gene.BIAS_NODE,  new ConstantNode(1));

        //eye nodes
        for (int eyeIndex : Gene.EYE_NODES) {
            nodeMapping.put(eyeIndex, new IndexedNode(indexedEyes, eyeIndex-1));
        }

        //output nodes
        List<Perceptron> output = new ArrayList<Perceptron>();
        for(int outputIndex : Gene.OUTPUT_NODES){
            Perceptron outputNode = new Perceptron();
            output.add(outputNode);
            nodeMapping.put(outputIndex, outputNode);
        }

        List<Edge> updateables = new ArrayList<Edge>();

        //wire edges
        for (Gene gene : chromosome.getGenes()) {
            if(gene.isActive()){
                if(!nodeMapping.containsKey(gene.getFrom())){
                    nodeMapping.put(gene.getFrom(), new Perceptron());
                }
                if(!nodeMapping.containsKey(gene.getTo())){
                    nodeMapping.put(gene.getTo(), new Perceptron());
                }
                Edge edge = new CachedEdge(nodeMapping.get(gene.getFrom()));
                nodeMapping.get(gene.getTo()).addInputEdge(edge, gene.getWeight());
                updateables.add(edge);
            }
        }

        List<Edge> edges = wrapEdges(output);
        updateables.addAll(edges);
        EdgeGameInput gameInput = new EdgeGameInput(edges);

        return new NeuralNetAI(indexedEyes, gameInput, updateables);
    }

    private List<Edge> wrapEdges(List<? extends Node> nodes){
        List<Edge> edges = new ArrayList<Edge>();

        for (Node node : nodes) {
            edges.add(new CachedEdge(node));
        }
        return edges;
    }
}
