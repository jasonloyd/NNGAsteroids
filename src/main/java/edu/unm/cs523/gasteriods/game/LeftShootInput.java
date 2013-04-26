package edu.unm.cs523.gasteriods.game;

/**
 * Dummy AI that holds down the Left, Forward and Shoot buttons.
 *
 * @author John Ericksen
 */
public class LeftShootInput implements GameInput {

    public boolean getLeft() {
        return true;
    }

    public boolean getRight() {
        return false;
    }

    public boolean getForward() {
        return true;
    }

    public boolean getShoot() {
        return true;
    }

    public void update() {
        //noop
    }

    public void clear() {
        //noop
    }
}
