package edu.unm.cs523.gasteriods.game;

/**
 * Decorator that updates the given eye class when update() is called.  Otherwise, all calls are delegated to the
 * given GameInput delegate instance.
 *
 * @author John Ericksen
 */
public class GameInputEyeDecorator implements GameInput {

    private final GameInput delegate;
    private final Eyes eyes;

    public GameInputEyeDecorator(Eyes eyes, GameInput delegate) {
        this.delegate = delegate;
        this.eyes = eyes;
    }

    public boolean getLeft() {
        return delegate.getLeft();
    }

    public boolean getRight() {
        return delegate.getRight();
    }

    public boolean getForward() {
        return delegate.getForward();
    }

    public boolean getShoot() {
        return delegate.getShoot();
    }

    public void update() {
        eyes.getOutput();
    }

    public void clear() {
        delegate.clear();
    }
}
