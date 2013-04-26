package edu.unm.cs523.gasteriods.neural;

import edu.unm.cs523.gasteriods.game.Eyes;

/**
 * Caches the output of the Eyes for referencing later.  Each call to update() will update the cached value
 * of the eyes.  Subsequent calls to getValue() with the index of the eye will return the given eye value.
 *
 * @author John Ericksen
 */
public class IndexedEyes implements IndexedNode.NodeValueHolder {

    private final Eyes eyes;
    private float[] values = new float[Eyes.EYE_COUNT];

    public IndexedEyes(Eyes eyes) {
        this.eyes = eyes;
    }

    public float getValue(int index) {
        return values[index];
    }

    /**
     * Update the cached eye values
     */
    public void update(){
        values = eyes.getOutput();
    }
}
