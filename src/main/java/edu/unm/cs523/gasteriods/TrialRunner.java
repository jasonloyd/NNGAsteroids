package edu.unm.cs523.gasteriods;

import edu.unm.cs523.gasteriods.game.Game;
import edu.unm.cs523.gasteriods.game.ManualClock;
import edu.unm.cs523.gasteriods.game.StringHighScoreInput;
import edu.unm.cs523.gasteriods.genetic.Chromosome;
import edu.unm.cs523.gasteriods.neural.AIFactory;

/**
 * @author John Ericksen
 */
public class TrialRunner implements Runnable {

    private final int loop;
    private final int timeStep;
    private final Chromosome chromosome;
    private final AIFactory aiFactory;
    private int fitness;

    public TrialRunner(int loop, int timeStep, Chromosome chromosome, AIFactory aiFactory) {
        this.loop = loop;
        this.timeStep = timeStep;
        this.chromosome = chromosome;
        this.aiFactory = aiFactory;
    }

    public void run() {
        fitness = 0;
        for (int i = 0; i < loop; i++) {
            ManualClock clock = new ManualClock(0);
            Game game = new Game(clock, new StringHighScoreInput("GA"));
            game.setInput(aiFactory.buildNeuralNetAI(game, chromosome));
            game.restart();
            while (game.getPlayer().getLives() > 0 && clock.getTime() < 900000) {
                clock.addTime(timeStep);
                game.handleControls(timeStep);
                game.update(timeStep);
            }
            fitness += game.getPlayer().getPoints();
        }
    }

    public Chromosome getChromosome() {
        return chromosome;
    }

    public int getFitness() {
        return fitness / loop;
    }
}
