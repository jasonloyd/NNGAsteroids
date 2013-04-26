/**
 * This class is the main class of the game. It handles the game loop containing the game logic
 * and initiates the game.
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

import java.util.HashSet;
import java.util.Set;

/**
 * Game data.  Holds the game state and updates the game state based on the update() and handleControls() methods
 *
 * @author John Ericksen
 */
public class Game {

    public static final int SCREEN_SIZE_X = 800;
    public static final int SCREEN_SIZE_Y = 600;
    public static final Point SCREEN_SIZE = new Point(SCREEN_SIZE_X, SCREEN_SIZE_Y);

    private final Set<Asteroid> asteroids = new HashSet<Asteroid>();
    private final Set<Shot> shots = new HashSet<Shot>();
    private final HighScores highScores = new HighScores();
    private final Clock clock;
    private final HighScoreInput highScoreInput;

    private Player player;
    private GameInput input;

    private int level;
    private int multiplier = 0;
    private int shotsFired = 0;
    private int hits = 0;

    public Game(Clock clock, HighScoreInput highScoreInput) {
        this.clock = clock;
        this.highScoreInput = highScoreInput;
    }

    /**
     * Update the game state, iterating the game state by the given delta value
     *
     * @param delta game state difference
     */
    public void update(int delta) {
        input.update();

        if (!player.isDead()) {
            player.move(delta);

            if (player.makeShot()) {
                Shot s = new Shot(player.getLocation(), player.getDirection(), clock);
                s.move(10);
                shots.add(s);
                shotsFired++;
            }
        }

        player.update();

        for (Asteroid a : asteroids) {
            a.move(delta);
        }

        for (Shot s : shots) {
            s.move(delta);
        }

        checkCollisions();
        levelHandler();
    }

    /**
     * Resets the game state
     */
    public void restart() {
        level = 0;
        multiplier = 0;
        hits = 0;
        shotsFired = 0;
        player = new Player(Game.SCREEN_SIZE.divide(2), clock);
        asteroids.clear();
        shots.clear();
        levelHandler();
    }

    /**
     * Updates the level based on the number of asteroids in the game state.  If zero asteroids exist, the
     * level is incremented and the next batch of asteroids is added.
     */
    private void levelHandler() {
        if (asteroids.size() == 0) {
            level++;
            addAsteroids(level + 2);
            if (level > 1 && hits > 0 && shotsFired > 0) {
                player.addPoints((int) (500 * ((float) hits / shotsFired)));
            }
        }
    }

    /**
     * Determines if the player or bullets are in collision with any asteroids.  If collisions are detected then
     * the relevant action is taken.
     */
    private void checkCollisions() {
        if (!player.isDead()) {
            for (Asteroid asteroid : asteroids) {
                if (player.isCollision(asteroid)) {
                    breakAsteroid(asteroid);

                    if (!player.isInvulnerable()) {
                        player.die();
                        if (player.getLives() == 0 && player.isDead()) {
                            if (highScores.isHighscore(player.getPoints())) {
                                highScores.add(highScoreInput.getName(), player.getPoints());
                            }
                        }
                    }
                    multiplier = 0;

                    asteroids.remove(asteroid);
                    break;
                }
            }
        }

        for (Shot s : shots) {
            boolean remove = false;

            for (Asteroid a : asteroids) {
                if (s.isCollision(a)) {
                    if (multiplier < 5) {
                        multiplier++;
                    }

                    hits++;

                    player.addPoints(a.getPoints() * (multiplier + 1));

                    asteroids.remove(a);
                    remove = true;
                    breakAsteroid(a);
                    break;
                }
            }
            if (s.getCreated() + 750 < clock.getTime() || remove) {
                if (!remove) {
                    multiplier = 0;
                }

                shots.remove(s);
                break;
            }
        }
    }

    private void breakAsteroid(Asteroid asteroid) {
        if (asteroid.getType() == Asteroid.AsteroidType.MEDIUM) {
            for (int i = 0; i < 3; i++) {
                Asteroid a1 = new Asteroid(asteroid.getLocation(), Asteroid.AsteroidType.SMALL, level);
                a1.setDirection(asteroid.getDirection() + i * 120);
                asteroids.add(a1);
            }
        } else if (asteroid.getType() == Asteroid.AsteroidType.LARGE) {
            for (int i = 0; i < 2; i++) {
                Asteroid a1 = new Asteroid(asteroid.getLocation(), Asteroid.AsteroidType.MEDIUM, level);
                a1.setDirection(asteroid.getDirection() + i * 120 - 60);
                asteroids.add(a1);
            }
        }
    }

    public void handleControls(int delta) {
        if (input.getLeft() && !input.getRight()) {
            player.turnLeft();
        } else if (input.getRight() && !input.getLeft()) {
            player.turnRight();
        }

        if (input.getForward()) {
            player.addSpeed(0.25f * delta);
        }

        if (input.getShoot()) {
            player.shoot();
        }
    }

    private void addAsteroids(int amount) {
        for (int i = 0; i < amount; i++) {

            Asteroid asteroid;
            do{
                float x = (int) (Math.random() * SCREEN_SIZE_X);
                float y = (int) (Math.random() * SCREEN_SIZE_Y);
                asteroid = new Asteroid(new Point(x, y), Asteroid.AsteroidType.LARGE, level);
            } while(asteroid.isCollision(player));

            asteroids.add(asteroid);
        }
    }

    public Set<Shot> getShots() {
        return shots;
    }

    public Set<Asteroid> getAsteroids() {
        return asteroids;
    }

    public Player getPlayer() {
        return player;
    }

    public HighScores getHighScores() {
        return highScores;
    }

    public int getLevel() {
        return level;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public int getShotsFired() {
        return shotsFired;
    }

    public int getHits() {
        return hits;
    }

    public void setInput(GameInput input) {
        this.input = input;
    }

    public GameInput getInput() {
        return input;
    }
}
