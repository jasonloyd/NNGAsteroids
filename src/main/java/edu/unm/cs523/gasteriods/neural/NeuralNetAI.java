package edu.unm.cs523.gasteriods.neural;

import edu.unm.cs523.gasteriods.game.GameInput;

import java.util.Collections;
import java.util.List;

/**
 * Orchestrates the AI controlled by a Neural Network.  This class can be used directly as a GameInput class.
 *
 * @author John Ericksen
 */
public class NeuralNetAI implements GameInput {

    private IndexedEyes indexedEyes;
    private EdgeGameInput gameInput;
    private List<Edge> updateables;

    public NeuralNetAI(IndexedEyes indexedEyes, EdgeGameInput gameInput, List<Edge> updateables){
        this.indexedEyes = indexedEyes;
        this.gameInput = gameInput;
        this.updateables = updateables;
    }

    public IndexedEyes getIndexedEyes(){
        return indexedEyes;
    }

    public List<Edge> getOutputs(){
        return gameInput.getOutputs();
    }

    public boolean getLeft() {
        return gameInput.getLeft();
    }

    public boolean getRight() {
        return gameInput.getRight();
    }

    public boolean getForward() {
        return gameInput.getForward();
    }

    public boolean getShoot() {
        return gameInput.getShoot();
    }

    public void update() {
        indexedEyes.update();
        Collections.shuffle(updateables);

        for (Edge edge : updateables) {
            edge.update();
        }
    }

    public void clear() {
        gameInput.clear();
    }
}