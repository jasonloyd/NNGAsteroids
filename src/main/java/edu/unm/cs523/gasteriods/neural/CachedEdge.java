package edu.unm.cs523.gasteriods.neural;

/**
 * Edge class that calculates and caches the output when update() is called.
 *
 * @author John Ericksen
 */
public class CachedEdge implements Edge {

    private final Node node;
    private float value = 0;

    public CachedEdge(Node node) {
        this.node = node;
    }

    public synchronized float getOutput() {
        return value;
    }

    public void update(){
        value = node.getValue();
    }
}
