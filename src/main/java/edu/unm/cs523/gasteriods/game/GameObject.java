/**
 * Basic functions and variables for an object in the game.
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

import edu.unm.cs523.gasteriods.math.Matrix;
import edu.unm.cs523.gasteriods.math.Point;

/**
 * Base class for all Game Objects in Asteroids.  Contains the common fields of location, direction, speed, etc that are
 * useful in updating the game state.
 *
 * @author David Laurell <david.laurell@gmail.com>
 */
public abstract class GameObject {

    private Point location;
    private Point speed = Point.ZERO;
    private boolean stopTurn = false;
    private float radius;
    private float direction;
    private float rotationSpeed;

    protected GameObject(Point location, float radius, boolean stopTurn) {
        this.location = location;
        this.radius = radius;
        this.stopTurn = stopTurn;
    }

    public float getDirection() {
        return direction;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Point getLocation(){
        return location;
    }

    public float getRadius() {
        return radius;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public void setDirection(float angle) {
        direction = limit(angle, 360);
    }

    public void setSpeed(Point speed){
        this.speed = speed;
    }

    public void addSpeed(float speedInput) {
        speed = speed.plus(Matrix.rotationMatrix(direction).multiply(new Point(speedInput, 0)));
    }

    public void move(float delta) {
        location = limit(location.plus(speed.multiply(delta).divide(1000)), Game.SCREEN_SIZE);

		/* rotate object */
        direction += limit(delta * rotationSpeed, 360);

        if (stopTurn) {
            rotationSpeed = 0;
        }
    }

    public boolean isCollision(GameObject other) {
        return location.minus(other.getLocation()).getMagnitude() <= this.getRadius() + other.getRadius();
    }

    private Point limit(Point input, Point value){
        return new Point(limit(input.getX(), value.getX()), limit(input.getY(), value.getY()));
    }

    private float limit(float input, float value){
        if(input > value){
            return input - value;
        }
        if(input < 0){
            return input + value;
        }
        return input;
    }

    public abstract <V, T> V accept(GameObjectVisitor<V, T> visitor, T input);
}
