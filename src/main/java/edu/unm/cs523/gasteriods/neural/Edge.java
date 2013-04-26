package edu.unm.cs523.gasteriods.neural;

/**
 * Edge that connects two nodes.  Each edge is responsible for passing the output of one node via the getOutput()
 * method to another node (caller).
 *
 * @author John Ericksen
 */
public interface Edge {

    float getOutput();

    void update();
}
