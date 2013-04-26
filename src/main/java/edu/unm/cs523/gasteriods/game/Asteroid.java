/**
 * This is the class for an asteroid object in the game.
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
 * Asteroid Game Object representing the Asteroid sprite data.
 */
public class Asteroid extends GameObject {

    public enum AsteroidType {
        SMALL(16, 10, 0.2f) {
            @Override
            public float getSpeed(int level) {
                return (float) (Math.random() * 50) + 40 + level * 10;
            }
        },
        MEDIUM(32, 5, 0.1f) {
            @Override
            public float getSpeed(int level) {
                return (float) (Math.random() * 25) + 15 + level * 10;
            }
        },
        LARGE(64, 1, 0.05f) {
            @Override
            public float getSpeed(int level) {
                return (float) (Math.random() * 15) + level * 10;
            }
        };

        private final float radius;
        private final int points;
        private final float rotationSpeed;

        private AsteroidType(int radius, int points, float rotationSpeed) {
            this.radius = radius;
            this.points = points;
            this.rotationSpeed = rotationSpeed;
        }

        public float getRadius() {
            return radius;
        }

        public int getPoints() {
            return points;
        }

        public float getRotationSpeed() {
            return rotationSpeed;
        }

        public abstract float getSpeed(int level);
    }

    private final AsteroidType type;

    public Asteroid(Point point, AsteroidType type, int level) {
        super(point, type.getRadius(), false);
        this.type = type;

        setDirection((float) Math.random() * 360);
        float left = (float) ((Math.random() * -5) + 2);

        setRotationSpeed(left * type.getRotationSpeed());
        addSpeed(type.getSpeed(level));
    }

    /**
     * Returns the number of points gained by shooting this asteroid.
     *
     * @return points
     */
    public int getPoints() {
        return type.getPoints();
    }

    /**
     * Returns the type of asteriod.
     *
     * @return type
     */
    public AsteroidType getType() {
        return type;
    }

    @Override
    public <V, T> V accept(GameObjectVisitor<V, T> visitor, T input) {
        return visitor.visit(this, input);
    }
}
