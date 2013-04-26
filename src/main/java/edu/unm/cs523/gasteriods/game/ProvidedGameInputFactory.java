package edu.unm.cs523.gasteriods.game;

/**
 * GameInput Factory for providing the GameInput given as the constructor parameter.  This factory acts as an
 * adapter to the GUI to allow non-container based GameInput instance to be used.
 *
 * @author John Ericksen
 */
public class ProvidedGameInputFactory implements GameInputFactory {

    private final GameInput input;

    public ProvidedGameInputFactory(GameInput input) {
        this.input = input;
    }

    public GameInput buildGameInput(Object container) {
        return input;
    }
}
