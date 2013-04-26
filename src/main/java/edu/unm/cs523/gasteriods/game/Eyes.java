package edu.unm.cs523.gasteriods.game;

import edu.unm.cs523.gasteriods.math.Matrix;
import edu.unm.cs523.gasteriods.math.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Eye calculations determining vision (distance) to the nearest Asteroid from the player's location.
 *
 * @author Jason Loyd
 */
public class Eyes {

    public static final int EYE_COUNT = 20;
    private static final int TOTAL_DEGREES = 360;
    private static final float MAX_VISION = 1000;
    private static final boolean DEBUG = false;
    private static final Matrix[] ROTATION_MATRICES;
    private static final Point[] SCREEN_DUPLICATION_POINTS;

    static{
        // Produce rotation matrix at every 18 degrees.
        ROTATION_MATRICES = new Matrix[EYE_COUNT];
        for(int i = 0; i < EYE_COUNT; i++){
            ROTATION_MATRICES[i] = Matrix.rotationMatrix(i * TOTAL_DEGREES / EYE_COUNT);
        }

        List<Point> screenDuplicationPoints = new ArrayList<Point>();
        for(float offsetX : new float[]{-Game.SCREEN_SIZE_X, 0, Game.SCREEN_SIZE_X}) {
            for(float offsetY : new float[]{-Game.SCREEN_SIZE_Y, 0, Game.SCREEN_SIZE_Y}) {
                screenDuplicationPoints.add(new Point(offsetX, offsetY));
            }
        }
        SCREEN_DUPLICATION_POINTS = screenDuplicationPoints.toArray(new Point[9]);
    }

    private Game game;

    public Eyes(Game game) {
        this.game = game;
    }

    /**
     * Calculates and returns a float array containing the distances to the nearest asteroid, normalized from 0 to 1.
     * This is calculated by determining the collision point along the angle of the given eye angle.
     *
     * @return asteroid distance array
     */
    public float[] getOutput() {
        float[] eyes = new float[EYE_COUNT];

        Point agentLocation = game.getPlayer().getLocation();

        // generate 3 x 3 representation of game board and adjust location in respect to player (zero player)
        HashMap<Point,Float> asteroidPositionGrid = new HashMap<Point, Float>();
        List<Point> gridOffsets = new ArrayList<Point>();

        for (Point offset : SCREEN_DUPLICATION_POINTS) {
            gridOffsets.add(offset.minus(agentLocation));
        }

        for (Asteroid a : game.getAsteroids()) {
            for (Point gridOffset : gridOffsets) {
                asteroidPositionGrid.put(a.getLocation().plus(gridOffset), a.getRadius());
            }
        }

        // get R_theta - agent's rotation matrix
        Matrix agentsRotationMatrix = Matrix.rotationMatrix(game.getPlayer().getDirection());

        // rotate by each eye R_theta * R_alpha1 to R_alpha20
        List<Matrix> linesOfSight = new ArrayList<Matrix>();

        for (Matrix ra : ROTATION_MATRICES) {
            linesOfSight.add(ra.multiply(agentsRotationMatrix));
        }

        // determine if an asteroid is a candidate i.e. |y| < R
        // add smallest s to eyes[], where s = x - R
        for (int i = 0; i < EYE_COUNT; i++) {
            Matrix lineOfSight = linesOfSight.get(i);
            eyes[i] = 1;
            for (Map.Entry<Point, Float> asteroidEntry : asteroidPositionGrid.entrySet()) {

                Point asteroidSight = lineOfSight.multiply(asteroidEntry.getKey());
                float radius = asteroidEntry.getValue();

                if(Math.abs(asteroidSight.getY()) < radius) {
                    float s = (asteroidSight.getX() - radius) / MAX_VISION;
                    if(s > 0 && eyes[i] > s){
                        eyes[i] = s;
                    }
                }
            }
        }

        if(DEBUG){
            //print the distance array
            for (float eye : eyes) {
                if(eye > 1000){
                    System.out.print("m");
                }
                else{
                    System.out.print(eye);
                }
                System.out.print(", ");
            }
            System.out.println();
        }

        return eyes;
    }
 }
