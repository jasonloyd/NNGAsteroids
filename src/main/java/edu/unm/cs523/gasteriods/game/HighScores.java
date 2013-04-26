/**
 * @author David Laurell <david.laurell@gmail.com>
 *
 * Copyright 2008 David Laurell
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.unm.cs523.gasteriods.game;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Stores the high score of the game.
 *
 * @author David Laurell <david.laurell@gmail.com>
 */
public class HighScores {

    private static final String FILENAME = "highscore.save";
    private static final int SIZE = 10;
    private List<HighScoreItem> highscores = new ArrayList<HighScoreItem>();

    public HighScores() {
        loadHighscore();
    }

    /**
     * Adds a player highscore
     *
     * @param name of the player
     * @param points collected
     */
    public void add(String name, int points) {
        HighScoreItem hsi = new HighScoreItem(name, points);
        highscores.add(hsi);
        Collections.sort(highscores);
        while(highscores.size() > SIZE){
            highscores.remove(SIZE);
        }

        saveHighscore();
    }

    public boolean isHighscore(int score) {
        return highscores.size() <= 0 || highscores.get(0).getScore() < score;
    }

    /**
     * Loads highscore from the highscore file
     */
    public void loadHighscore() {
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(FILENAME));
            ObjectInputStream ois = new ObjectInputStream(bis);

            highscores = (List<HighScoreItem>) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves highscore to the highscore file
     */
    public void saveHighscore() {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(FILENAME));
            ObjectOutputStream oos = new ObjectOutputStream(bos);

            oos.writeObject(highscores);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a string containing a readable list of the highscores
     *
     * @return String the higshcore list
     */
    public List<HighScoreItem> getScores() {
        return highscores;
    }
}
