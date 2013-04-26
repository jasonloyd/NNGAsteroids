package edu.unm.cs523.gasteriods.math;

/**
 * Immutable Matrix class that performs Matrix operations on other Matrices or Point vectors.
 *
 * @author John Ericksen
 */
public class Matrix {

    private final double[][] matrix;

    public Matrix(double[][] matrix){
        this.matrix = matrix;
    }

    /**
     * Builds a 2x2 rotation matrix.
     * @param degrees to rotate
     * @return rotation matrix
     */
    public static Matrix rotationMatrix(float degrees){
        double radians = Math.toRadians((double) degrees);
        return new Matrix(new double[][]{{Math.cos(radians), Math.sin(radians)}, {Math.sin(radians), -Math.cos(radians)}});
    }

    /**
     * Multiplies the current matrix by the given point (this) * p
     *
     * @param input point
     * @return result point
     */
    public Point multiply(Point input){
        return new Point(
                (float)(input.getX() * matrix[0][0] + input.getY() * matrix[0][1]),
                (float)(input.getX() * matrix[1][0] + input.getY() * matrix[1][1]));
    }

    /**
     * Multiplies the current matrix by the given matrix (this) * (that) and returns the result.
     *
     * @param matrix2 input matrix
     * @return product
     */
    public Matrix multiply(Matrix matrix2) {

        //Matrix dimensions must match.
        if (matrix.length != matrix2.matrix[0].length) {
            throw new RuntimeException("Matrix dimensions don't agree");
        }

        double[][] matrixProductArray = new double[matrix.length][matrix2.matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix2.matrix[0].length; j++ ) {
                for (int k = 0; k < this.matrix[0].length; k++) {
                    matrixProductArray[i][j] += (this.matrix[i][k] * matrix2.matrix[k][j]);
                }
            }
        }

        return new Matrix(matrixProductArray);
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                builder.append(matrix[i][j]);
                builder.append(' ');
            }
            builder.append('\n');
        }
        return builder.toString();
    }

    protected int getNumberOfRows() {
        return matrix.length;
    }

    protected int getNumberOfColumns() {
        return matrix[0].length;
    }

    protected double getMatrixEntry(int row, int col) {
        return matrix[row][col];
    }
}
