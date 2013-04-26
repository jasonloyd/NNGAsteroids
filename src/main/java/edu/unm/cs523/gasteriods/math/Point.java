package edu.unm.cs523.gasteriods.math;

/**
 * Two dimensional immutable vector.
 *
 * @author John Ericksen
 */
public class Point {

    public static final Point ZERO = new Point(0, 0);

    private final float x;
    private final float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    /**
     * Adds this point to the given point and returns the result.
     *
     * @param input to add
     * @return sum of this and input.
     */
    public Point plus(Point input) {
        return new Point(x + input.getX(), y + input.getY());
    }

    /**
     * Multiplies this point by the given scalar and returns the result.
     *
     * @param scalar to multiply by
     * @return product of this and the input scalar.
     */
    public Point multiply(float scalar) {
        return new Point(x * scalar, y * scalar);
    }

    /**
     * Divides this point to the given scalar and returns the result.
     *
     * @param scalar to divide by
     * @return quotient of this and the input scalar.
     */
    public Point divide(int scalar) {
        return new Point(x / scalar, y / scalar);
    }

    /**
     * Subtracts from this point the given input and returns the result.
     *
     * @param input to add
     * @return sum of this and input.
     */
    public Point minus(Point input) {
        return new Point(x - input.getX(), y - input.getY());
    }

    /**
     * Determines the magnitude from the origin of this Point.
     *
     * @return magnitude
     */
    public float getMagnitude() {
        return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }
}
