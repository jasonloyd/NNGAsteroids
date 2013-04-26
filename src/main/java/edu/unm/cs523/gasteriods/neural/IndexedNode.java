package edu.unm.cs523.gasteriods.neural;

/**
 * Node that returns a value of the indexed NodeValueHolder.
 *
 * @author John Ericksen
 */
public class IndexedNode implements Node{

    private final NodeValueHolder holder;
    private final int index;

    public IndexedNode(NodeValueHolder holder, int index) {
        this.holder = holder;
        this.index = index;
    }

    public float getValue() {
        return holder.getValue(index);
    }

    public void addInputEdge(Edge edge, float weight) {
        //noop
    }

    public interface NodeValueHolder {
        /**
         * Returns the value at the given index.
         * @param index
         * @return value
         */
        float getValue(int index);
    }
}
