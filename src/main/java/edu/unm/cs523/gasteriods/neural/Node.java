package edu.unm.cs523.gasteriods.neural;

/**
 * Node that produces a real number output.  Nodes may be given input edges which may be used for calculating the
 * output of the node (or not, depending upon Node implementation).
 *
 * @author John Ericksen
 */
public interface Node {

    /**
     * Returns the output value of the node
     *
     * @return output
     */
    float getValue();

    /**
     * Add an input edge to the node.
     *
     * @param edge input
     * @param weight associated with edge
     */
    void addInputEdge(Edge edge, float weight);
}
