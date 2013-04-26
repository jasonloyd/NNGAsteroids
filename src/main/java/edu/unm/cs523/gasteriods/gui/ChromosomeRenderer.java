package edu.unm.cs523.gasteriods.gui;

import edu.unm.cs523.gasteriods.genetic.Chromosome;
import edu.unm.cs523.gasteriods.genetic.Gene;
import edu.unm.cs523.gasteriods.math.Point;
import edu.unm.cs523.gasteriods.neural.NeuralNetAI;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

import java.util.HashMap;
import java.util.Map;

/**
 * @author John Ericksen
 */
public class ChromosomeRenderer extends BasicGame {

    private GameRenderer delegate;
    private Chromosome chromosome;
    private NeuralNetAI ai;
    private Point pointIncrement = new Point(20.0f, 0.0f);
    private static final int NODE_RADIUS = 10;

    public ChromosomeRenderer(Chromosome chromosome, NeuralNetAI ai, GameRenderer delegate) {
        super("GAsteroids");
        this.ai = ai;
        this.delegate = delegate;
        this.chromosome = chromosome;
    }

    public void init(GameContainer container) throws SlickException {
        delegate.init(container);
    }

    public void render(GameContainer container, Graphics graphics) throws SlickException {

        Point inputStartingPoint = new Point(805.0f, 500.0f);
        Point outputStartingPoint = new Point(950.0f, 100.0f);
        Point midStartingPoint = new Point(805.0f, 300.0f);
        Map<Integer, Point> nodePoints = new HashMap<Integer, Point>();

        for (int i : Gene.ALL_GIVEN_NODES) {
            if (i == Gene.BIAS_NODE) {
                drawNode(graphics, nodePoints, i, inputStartingPoint = inputStartingPoint.plus(pointIncrement), Color.white);
            } else if (contains(Gene.EYE_NODES, i)) {
                Color  color = buildNodeColor(1 - ai.getIndexedEyes().getValue(i-1));
                drawNode(graphics, nodePoints, i, inputStartingPoint = inputStartingPoint.plus(pointIncrement), color);
            } else {
                Color color = buildNodeColor(ai.getOutputs().get(i - 21).getOutput());
                drawNode(graphics, nodePoints, i, outputStartingPoint = outputStartingPoint.plus(pointIncrement), color);
            }
        }

        for (Gene g : chromosome.getGenes()) {
            if(!nodePoints.containsKey(g.getFrom())){
                drawNode(graphics, nodePoints, g.getFrom(), midStartingPoint = midStartingPoint.plus(pointIncrement), Color.white);
            }
            if(!nodePoints.containsKey(g.getTo())){
                drawNode(graphics, nodePoints, g.getTo(), midStartingPoint = midStartingPoint.plus(pointIncrement), Color.white);
            }
        }

        graphics.setColor(Color.black);

        for (Gene g : chromosome.getGenes()) {
            graphics.drawLine(nodePoints.get(g.getFrom()).getX(), nodePoints.get(g.getFrom()).getY(), nodePoints.get(g.getTo()).getX(), nodePoints.get(g.getTo()).getY());
        }

        delegate.render(container, graphics);
    }

    private boolean contains(int[] nodes, int id) {
        for (int node : nodes) {
            if(node == id){
                return true;
            }
        }
        return false;
    }

    private Color buildNodeColor(float value) {
        return new Color(value, value, value);
    }

    private void drawNode(Graphics graphics, Map<Integer, Point> nodePoints, int id, Point point, Color color) {
        graphics.setColor(color);
        Shape node = new Circle(point.getX(), point.getY(), NODE_RADIUS);
        graphics.fill(node);
        graphics.draw(node);
        nodePoints.put(id, point);
    }

    public void update(GameContainer container, int delta) throws SlickException {
        delegate.update(container, delta);
    }
}
