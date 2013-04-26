package edu.unm.cs523.gasteriods.gui;

import edu.unm.cs523.gasteriods.game.GameInput;
import org.newdawn.slick.Input;

/**
 * Keyboard GameInput baesd on the Slick Input instance.  This class includes more than the GameInput requires as
 * pausing, quitting and restarting are additional functions that are not needed during the core gameplay.
 *
 * @author John Ericksen
 */
public class SlickGameInput implements GameInput {

    private final Input input;

    public SlickGameInput(Input input) {
        this.input = input;
    }

    public boolean getLeft() {
        return input.isKeyDown(Input.KEY_LEFT);
    }

    public boolean getRight() {
        return input.isKeyDown(Input.KEY_RIGHT);
    }

    public boolean getForward() {
        return input.isKeyDown(Input.KEY_UP);
    }

    public boolean getShoot() {
        return input.isKeyDown(Input.KEY_SPACE);
    }

    public boolean getEscape() {
        return input.isKeyDown(Input.KEY_ESCAPE);
    }

    public boolean getPause() {
        return input.isKeyPressed(Input.KEY_F3);
    }

    public boolean getRestart() {
        return input.isKeyPressed(Input.KEY_F2);
    }

    public void update() {
        //noop
    }

    public void clear() {
        input.clearKeyPressedRecord();
    }
}
