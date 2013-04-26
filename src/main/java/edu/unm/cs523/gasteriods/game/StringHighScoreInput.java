package edu.unm.cs523.gasteriods.game;

/**
 * Simple HighScoreInput that returns the given name (given in the constructor) when getName() is called.
 *
 * @author John Ericksen
 */
public class StringHighScoreInput implements HighScoreInput {

    private final String name;

    public StringHighScoreInput(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
