package edu.unm.cs523.gasteriods.neural;

import edu.unm.cs523.gasteriods.game.GameInput;

import java.util.List;

/**
 * Instance used to map a list of 4 edges to the Asteroid GameInput input class.  Each edge is compaired with 0.5 to
 * determine activation ( > 0.5 on, < 0.5 off).
 *
 * @author John Ericksen
 */
public class EdgeGameInput implements GameInput {

    private List<Edge> outputs;

    public List<Edge> getOutputs(){
        return outputs;
    }

    public EdgeGameInput(List<Edge> outputs) {
        this.outputs = outputs;
    }

    public boolean getLeft() {
        return outputs.get(0).getOutput() > 0.5;
    }

    public boolean getRight() {
        return outputs.get(1).getOutput() > 0.5;
    }

    public boolean getForward() {
        return outputs.get(2).getOutput() > 0.5;
    }

    public boolean getShoot() {
        return outputs.get(3).getOutput() > 0.5;
    }

    public void update() {
        //noop
    }

    public void clear() {
        //noop
    }
}
