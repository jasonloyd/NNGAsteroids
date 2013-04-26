/**
 *
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

import java.io.Serializable;

/**
 * Associates the given score with a name to be contained in the HighScores.
 */
public class HighScoreItem implements Serializable, Comparable<HighScoreItem> {

    private static final long serialVersionUID = 1L;

    private final String name;
    private final int score;

    public HighScoreItem(String name, int score) {
        this.score = score;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public int compareTo(HighScoreItem highScoreItem) {
        return highScoreItem.getScore() - score;
    }
}
