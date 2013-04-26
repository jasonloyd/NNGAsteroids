package edu.unm.cs523.gasteriods;

import edu.unm.cs523.gasteriods.game.*;
import edu.unm.cs523.gasteriods.genetic.*;
import edu.unm.cs523.gasteriods.gui.ChromosomeRenderer;
import edu.unm.cs523.gasteriods.gui.DialogHighScoreInput;
import edu.unm.cs523.gasteriods.gui.GameRenderer;
import edu.unm.cs523.gasteriods.gui.SlickGameInputFactory;
import edu.unm.cs523.gasteriods.neural.AIFactory;
import edu.unm.cs523.gasteriods.neural.NeuralNetAI;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Main Genetic Algorithm Asteroids game and simulation.
 *
 * @author John Ericksen
 */
public class GAsteroids {

    private static final String DEMO = "demo";
    private static final String HEADLESS = "headless";
    private final ChromosomeFactory chromosomeFactory = new ChromosomeFactory();
    private final AIFactory aiFactory = new AIFactory();

    public static void main(String[] args) throws SlickException, InterruptedException, FileNotFoundException {

        GAsteroids gAsteroids = new GAsteroids();

        if(args.length == 0){
            System.out.println("Usage: GAsteroids [optional command: " + DEMO + ", " + HEADLESS  + "]");
            gAsteroids.originalGame();
        }
        else{
            if(args[0].equals(DEMO)){

                if(args.length > 1){
                    gAsteroids.demoGame(new Scanner(new File(args[1])).next().trim());
                }
                else{
                    gAsteroids.demoGame();
                }

            }
            else if(args[0].equals(HEADLESS)){
                gAsteroids.headlessGame();
            }
        }
    }

    public void originalGame() throws SlickException {
        Game game = new Game(new SystemClock(), new DialogHighScoreInput());
        AppGameContainer container = new AppGameContainer(
                new GameRenderer(game,
                        new GameInputEyeDecoratorFactory(new Eyes(game), new SlickGameInputFactory())));
        container.setDisplayMode(Game.SCREEN_SIZE_X, Game.SCREEN_SIZE_Y, false);
        container.setTargetFrameRate(60);
        container.setShowFPS(false);
        container.getAlwaysRender();
        container.start();
    }

    public void demoGame() throws SlickException {
        Game game = new Game(new SystemClock(), new StringHighScoreInput("GA"));
        AppGameContainer container = new AppGameContainer(new GameRenderer(game,
                new ProvidedGameInputFactory(
                        new GameInputLogger(buildRandomNeuralNetAI(game)))));
        container.setDisplayMode(Game.SCREEN_SIZE_X, Game.SCREEN_SIZE_Y, false);
        container.setTargetFrameRate(60);
        container.setShowFPS(false);
        container.getAlwaysRender();
        container.start();
    }

    public void demoGame(String chromosomeString) throws SlickException {
        Chromosome chromosome = chromosomeFactory.buildFromString(chromosomeString);

        Game game = new Game(new SystemClock(), new StringHighScoreInput("GA"));
        NeuralNetAI neuralNetAI = aiFactory.buildNeuralNetAI(game, chromosome);
        AppGameContainer container = new AppGameContainer(new ChromosomeRenderer(chromosome, neuralNetAI, new GameRenderer(game,
                new ProvidedGameInputFactory(new GameInputLogger(neuralNetAI)))));
        container.setDisplayMode(Game.SCREEN_SIZE_X + 475, Game.SCREEN_SIZE_Y, false);
        container.setTargetFrameRate(60);
        container.setShowFPS(false);
        container.getAlwaysRender();
        container.start();
    }

    public void headlessGame() throws InterruptedException {

        GA ga_basic = new BasicGA();

        Collection<Chromosome> chromosomes = new HashSet<Chromosome>();

        final int timeStep = 10;
        final int loop = 1;
        int generations = 100;
        int population = 1000;
        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println("Number of cores: " + processors);

        Chromosome chromosomeInput = chromosomeFactory.buildRandomFullyConnected();
        for(int i = 0; i < population; i++){
            chromosomes.add(randomizeWeights(chromosomeInput.copy()));
        }

        for(int gen = 0; gen < generations; gen++){

            ExecutorService executor = Executors.newFixedThreadPool(processors);

            long start = System.currentTimeMillis();

            List<TrialRunner> runners = new ArrayList<TrialRunner>();

            for (final Chromosome chromosome : chromosomes) {

                TrialRunner trialRunner = new TrialRunner(loop, timeStep, chromosome, aiFactory);
                runners.add(trialRunner);

                executor.execute(trialRunner);
            }

            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);

            final Map<Chromosome, Integer> fitnessMap = new HashMap<Chromosome, Integer>();

            for (TrialRunner runner : runners) {
                fitnessMap.put(runner.getChromosome(), runner.getFitness());
            }

            // evolve!
            chromosomes = ga_basic.evolve(fitnessMap);
            outputFitnesses(fitnessMap.values());
            long time = (System.currentTimeMillis() - start);
            System.out.println("Generation " + gen +
                    "\tTotal: " + time + "ms" +
                    new Statistics(fitnessMap.values()));

            System.out.println("Max Chr: " + getMaxChromosome(fitnessMap));
        }
    }

    private Chromosome randomizeWeights(Chromosome input){
        for (Gene gene : input.getGenes()) {
            gene.setWeight(ChromosomeFactory.randomFloat());
        }
        return input;
    }

    private void outputFitnesses(Collection<Integer> fitnesses){
        System.out.print("Fitness: ");
        for (Integer fitness : fitnesses) {
            System.out.print(fitness);
            System.out.print(",");
        }
        System.out.println();
    }

    private Chromosome getMaxChromosome(Map<Chromosome, Integer> fitnessMap) {
        int max = Integer.MIN_VALUE;
        Chromosome maxChromosome = null;

        for (Map.Entry<Chromosome, Integer> fitnessEntry : fitnessMap.entrySet()) {
            if(fitnessEntry.getValue() > max){
                max = fitnessEntry.getValue();
                maxChromosome = fitnessEntry.getKey();
            }
        }

        return maxChromosome;
    }

    private static final class Statistics{
        private int max = Integer.MIN_VALUE;
        private int min = Integer.MAX_VALUE;
        private int mean;
        private int stddev;

        private Statistics(Collection<Integer> values) {
            int sum = 0;
            for (Integer value : values) {
                if(value > this.max){
                    this.max = value;
                }
                if(value < this.min){
                    this.min = value;
                }
                sum += value;
            }
            this.mean = sum / values.size();
            int tempStdDev = 0;
            for (Integer value : values) {
                tempStdDev += Math.sqrt((mean - value)*(mean - value));
            }
            this.stddev = tempStdDev / values.size();
        }

        @Override
        public String toString() {
            return "\tAve: " + mean +
                    "\tStd dev: " + stddev +
                    "\tMax: " + max +
                    "\tMin: " + min;
        }
    }

    private NeuralNetAI buildRandomNeuralNetAI(Game game){
        Chromosome chromosome = chromosomeFactory.buildRandomFullyConnected();
        return aiFactory.buildNeuralNetAI(game, chromosome);
    }
}
