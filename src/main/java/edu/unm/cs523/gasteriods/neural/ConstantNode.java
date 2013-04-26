package edu.unm.cs523.gasteriods.neural;

/**
 * Node that return a constant value from getValue() given in the constructor.
 *
 * @author John Ericksen
 */
public class ConstantNode implements Node {

    private final float value;

    public ConstantNode(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    public void addInputEdge(Edge edge, float weight) {
        //noop
    }
}
