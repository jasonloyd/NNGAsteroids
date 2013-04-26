package edu.unm.cs523.gasteriods.game;

/**
 * GameInput factory for building a GameInputEyeDecorator class and wrapping the given instance produced out of the
 * delegate factory.
 *
 * @author John Ericksen
 */
public class GameInputEyeDecoratorFactory implements GameInputFactory {

    private final Eyes eyes;
    private final GameInputFactory delegate;

    public GameInputEyeDecoratorFactory(Eyes eyes, GameInputFactory delegate) {
        this.eyes = eyes;
        this.delegate = delegate;
    }

    public GameInput buildGameInput(Object container) {
        return new GameInputEyeDecorator(eyes, delegate.buildGameInput(container));
    }
}
