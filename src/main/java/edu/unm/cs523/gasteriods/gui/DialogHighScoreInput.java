package edu.unm.cs523.gasteriods.gui;

import edu.unm.cs523.gasteriods.game.HighScoreInput;

import javax.swing.*;

/**
 * Uses a Swing Dialog to input the HighScore user's name.s
 *
 * @author John Ericksen
 */
public class DialogHighScoreInput implements HighScoreInput {

    public String getName() {
        return JOptionPane.showInputDialog("Highscore! Input your name!");
    }
}
