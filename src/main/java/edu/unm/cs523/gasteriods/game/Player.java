/**
 * The player class handles all player specific method such as how to move and shoot.
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

import edu.unm.cs523.gasteriods.math.Point;

/**
 * Player Game Object holding all Player relevant information.
 */
public class Player extends GameObject {

    private static final float ROTATE_SPEED = 0.25f;
    private static final int SHOT_WAIT = 500;
    private static final int DEAD_TIME = 1000;
    private static final int SPAWN_INVULNERABLE_TIME = 1500;
    public static final int PLAYER_RADIUS = 16;

    private final Alarm dontshoot;
    private final Alarm invtimer;
    private final Alarm deadtimer;

    private boolean isShooting = false;
    private boolean dead;
    private boolean invulnerable;
    private int lives = 3;
    private int points = 0;

    public Player(Point point, Clock clock) {
        super(point, PLAYER_RADIUS, true);
        this.dontshoot = new Alarm(SHOT_WAIT, clock);
        this.invtimer = new Alarm(SPAWN_INVULNERABLE_TIME, clock);
        this.deadtimer = new Alarm(DEAD_TIME, clock);
        this.dead = false;
    }

    public int getPoints() {
        return points;
    }

    public int getLives() {
        return lives;
    }

    /**
     * Changes the player state to be invincible.
     */
    public void invincibility() {
        invulnerable = true;
        invtimer.start();
    }

    public boolean isInvulnerable() {
        return invulnerable;
    }

    public boolean isDead() {
        return dead;
    }

    /**
     * Kills the player which decreases the lives and pauses for a set amount of time.
     */
    public void die() {
        lives--;
        deadtimer.start();
        dead = true;
    }

    public void addPoints(int amount) {
        points += amount;
    }

    public void turnRight() {
        setRotationSpeed(+ROTATE_SPEED);
    }

    public void turnLeft() {
        setRotationSpeed(-ROTATE_SPEED);
    }

    /**
     * Triggers a shot to take place.
     */
    public void shoot() {
        if (!isShooting && !dontshoot.isAlarm()) {
            isShooting = true;
        }
    }

    /**
     * Updates the Player's state.  This shoudl be called as often as the game state is updated.
     */
    public void update() {
        if (!invtimer.isAlarm() && invulnerable) {
            invulnerable = false;
        }

        if (!deadtimer.isAlarm() && dead && lives > 0) {
            dead = false;
            respawn();
            invincibility();
        }
    }

    /**
     * Determines if a Shot object should be introduced.
     *
     * @return introduce shot object
     */
    public boolean makeShot() {
        if (isShooting && !dontshoot.isAlarm()) {
            isShooting = false;
            dontshoot.start();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Resets the player location, speed and direction.
     */
    public void respawn() {
        setLocation(Game.SCREEN_SIZE.divide(2));
        setSpeed(Point.ZERO);
        setDirection(0);
    }

    @Override
    public <V, T> V accept(GameObjectVisitor<V, T> visitor, T input) {
        return visitor.visit(this, input);
    }
}
