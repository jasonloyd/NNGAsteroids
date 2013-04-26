package edu.unm.cs523.gasteriods.math;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Jason Loyd
 */
public class TestMatrix {

    @Test
    public void testRowsColumns(){
        Matrix a = new Matrix(new double[][]{{1,0},{1,0}});
        Matrix b = new Matrix(new double[][]{{1,2},{3,4}});

        assertEquals(2, a.getNumberOfRows());
        assertEquals(2, a.getNumberOfColumns());

        assertEquals(2, b.getNumberOfRows());
        assertEquals(2, b.getNumberOfColumns());

        Matrix ab = a.multiply(b);
        Matrix ba = b.multiply(a);

        assertEquals(2, ab.getNumberOfRows());
        assertEquals(2, ab.getNumberOfColumns());

        assertEquals(2, ba.getNumberOfRows());
        assertEquals(2, ba.getNumberOfColumns());
    }

    @Test
    public void testDifferentDimensionsMatrixMultiply(){
        Matrix a = new Matrix(new double[][]{{1,1,1},{2,2,2}});
        Matrix b = new Matrix(new double[][]{{1,1},{2,2},{3,3},{4,4}});

        assertEquals(2, a.getNumberOfRows());
        assertEquals(3, a.getNumberOfColumns());

        assertEquals(4, b.getNumberOfRows());
        assertEquals(2, b.getNumberOfColumns());

        Matrix ab = a.multiply(b);

        assertEquals(2, ab.getNumberOfRows());
        assertEquals(2, ab.getNumberOfColumns());
    }

    @Test
    public void testRotationMatrix(){
        Matrix ninetyDegrees = Matrix.rotationMatrix(90);

        Point rotated = ninetyDegrees.multiply(new Point(1, 0));

        assertEquals(0, rotated.getX(), 0.001);
        assertEquals(1, rotated.getY(), 0.001);

    }
}
