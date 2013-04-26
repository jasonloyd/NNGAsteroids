package edu.unm.cs523.gasteriods.game;

/**
 * Generalized game input.  Abstracts the controls of the Asteroids game to allow for alternate types of
 * input to be used (Human, AI, etc).
 *
 * @author John Ericksen
 */
public interface GameInput {

    /**
     * @return Left button is pressed.
     */
    boolean getLeft();

    /**
     * @return Right button is pressed.
     */
    boolean getRight();

    /**
     * @return Forward button is pressed.
     */
    boolean getForward();

    /**
     * @return Shoot button is pressed.
     */
    boolean getShoot();

    /**
     * Called when the game state is updated.
     */
    void update();

    /**
     * Called after the game state is updated.  This is a chance to clear out any state.
     */
    void clear();
}
