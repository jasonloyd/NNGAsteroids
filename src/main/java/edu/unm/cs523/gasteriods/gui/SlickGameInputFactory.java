package edu.unm.cs523.gasteriods.gui;

import edu.unm.cs523.gasteriods.game.GameInput;
import edu.unm.cs523.gasteriods.game.GameInputFactory;
import org.newdawn.slick.GameContainer;

/**
 * Factory to create a SlickGameInput from the given container.  This factory can only be used with a
 * org.newdawn.slick.GameContainer container class otherwise it will throw a RuntimeException during the
 * buildGameInput() method
 *
 * @author John Ericksen
 */
public class SlickGameInputFactory implements GameInputFactory {

    public GameInput buildGameInput(Object container) {

        if(!(container instanceof GameContainer)){
            throw new RuntimeException("Unable to create user input without Slick Game Container");
        }

        return new SlickGameInput(((GameContainer)container).getInput());
    }
}
