/**
 * This simple class initiates is used to locate a shot in the game.
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
 * Shot Game Object holding all relevant shot information.
 */
public class Shot extends GameObject {

    public static final int SHOT_RADIUS = 8;
    public static final float SHOT_SPEED = 500;

    private final long created;

    public Shot(Point point, float direction, Clock clock) {
        super(point, SHOT_RADIUS, false);
        setDirection(direction);
        addSpeed(SHOT_SPEED);

        this.created = clock.getTime();
    }

    @Override
    public <V, T> V accept(GameObjectVisitor<V, T> visitor, T input) {
        return visitor.visit(this, input);
    }

    public long getCreated() {
        return created;
    }
}
