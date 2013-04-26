/**
 * This class handles alarm-specific methods. 
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

/**
 * General use timer.
 */
public class Alarm {

    private final Clock clock;
    private final int time;

    private long end;
    private boolean active;

    public Alarm(int time, Clock clock) {
        this.clock = clock;
        this.time = time;
        this.active = false;
    }

    /**
     * Starts the timer
     */
    public void start() {
        active = true;
        end = clock.getTime() + time;
    }

    /**
     * Determines if the alarm has hit or not
     * @return alarm status (true == ended)
     */
    public boolean isAlarm() {
        return active && end > clock.getTime();
    }
}
