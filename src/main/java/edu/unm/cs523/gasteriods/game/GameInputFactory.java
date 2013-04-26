package edu.unm.cs523.gasteriods.game;

/**
 * Factory interface for building a GameInput based on some container input.  This class abstracts the building of
 * a GameInput and allows the GameInput to be built off of a container class (ie: org.newdawn.slick.GameContainer).
 *
 * @author John Ericksen
 */
public interface GameInputFactory {

    /**
     * Build the GameInput.
     *
     * @param container relevant container instance
     * @return GameInput
     */
    GameInput buildGameInput(Object container);
}
