package edu.unm.cs523.gasteriods.game;

/**
 * Logs to System.out all activity of the wrapped delegate GameInput instance.
 *
 * @author John Ericksen
 */
public class GameInputLogger implements GameInput{

    private boolean left;
    private boolean right;
    private boolean forward;
    private boolean shoot;

    private final GameInput delegate;

    public GameInputLogger(GameInput delegate) {
        this.delegate = delegate;
    }

    public boolean getLeft() {
        return left = delegate.getLeft();
    }

    public boolean getRight() {
        return right = delegate.getRight();
    }

    public boolean getForward() {
        return forward = delegate.getForward();
    }

    public boolean getShoot() {
        return shoot = delegate.getShoot();
    }

    public void update() {
        delegate.update();
        System.out.println("L: " + tf(left) + " R: " + tf(right) + " F: " + tf(forward) + " S: " + tf(shoot));
    }

    private String tf(boolean value){
        return value? "T" : "F";
    }

    public void clear() {
        delegate.clear();
    }
}
