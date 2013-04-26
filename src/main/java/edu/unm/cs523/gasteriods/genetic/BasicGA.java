package edu.unm.cs523.gasteriods.genetic;

import edu.unm.cs523.gasteriods.game.FitnessComparator;
import edu.unm.cs523.gasteriods.neural.FeatureIndexer;

import java.util.*;

/**
 * @author Munand Kotha
 */
public class BasicGA implements GA{

    private static final Random RAND = new Random(System.currentTimeMillis());
    private static final int TOTAL_NODE_COUNT = 30;
    private static final float ELITE_PERCENTAGE = 0.30F;
    private static final float WEIGHT_MUTATION_PERCENTAGE = 0.20F;
    private static final float EDGE_ADD_MUTATION_PERCENTAGE = 0.20F;
    private static final float EDGE_REMOVE_MUTATION_PERCENTAGE = 0.10F;
    private static final float NODE_ADD_MUTATION_PERCENTAGE = 0.10F;

    public Collection<Chromosome> evolve(Map<Chromosome, Integer> fitnessMap){

        int populationSize = fitnessMap.keySet().size();

        //tournament selection
        //List<Chromosome> tournamentChromosomes = tournamentSelection(fitnessMap, populationSize);
        List<Chromosome> tournamentChromosomes = rouletteSelection(fitnessMap, populationSize);

        //crossover
        List<Chromosome> crossedOverChromosomes = crossOver(tournamentChromosomes, populationSize);

        //mutation
        Collection<Chromosome> mutatedChromosomes = mutation(crossedOverChromosomes);

        //elitism
        // copy sorted chromosomes after crossover and mutation
        return elitism(mutatedChromosomes, sortMap(fitnessMap));
    }

    private Map<Chromosome, Integer> sortMap(Map<Chromosome, Integer> fitnessMap) {
        FitnessComparator fitnessComparator = new FitnessComparator(fitnessMap);
        TreeMap<Chromosome, Integer> sortedFitness = new TreeMap<Chromosome, Integer>(fitnessComparator);
        sortedFitness.putAll(fitnessMap);
        return sortedFitness;
    }

    private Collection<Chromosome> elitism(Collection<Chromosome> someChromosomes, Map<Chromosome, Integer> sortedMap) {
        List<Chromosome> elite = new ArrayList<Chromosome>();
        Iterator<Chromosome> sortedMapIt = sortedMap.keySet().iterator();
        Iterator<Chromosome> chromosomeIt = someChromosomes.iterator();
        int eliteCandidateSize = Math.round(someChromosomes.size() * ELITE_PERCENTAGE);
        for (int i = 0; i < someChromosomes.size(); i++) {
            if (i <= eliteCandidateSize && sortedMapIt.hasNext()) {
                elite.add(sortedMapIt.next());
            } else {
                elite.add(chromosomeIt.next());
            }
        }

        return elite;
    }

    private List<Chromosome> tournamentSelection(Map<Chromosome, Integer> fitnessMap, int populationSize){
        List<Chromosome> newChromosomes = new ArrayList<Chromosome>();
        List<Chromosome> chromosomes = new ArrayList<Chromosome>(fitnessMap.keySet());

        for (int i = 0; i < populationSize; i++){
            Chromosome randomKey1 = chromosomes.get(RAND.nextInt(chromosomes.size()));
            Chromosome randomKey2 = chromosomes.get(RAND.nextInt(chromosomes.size()));
            Integer score1 = fitnessMap.get(randomKey1);
            Integer score2 = fitnessMap.get(randomKey2);

            if (score1 >= score2){
                newChromosomes.add(randomKey1);
            }
            else {
                newChromosomes.add(randomKey2);
            }
        }
        return newChromosomes;
    }

    private List<Chromosome> rouletteSelection(Map<Chromosome, Integer> fitnessMap, int populationSize){
        List<Chromosome> chromosomes = new ArrayList<Chromosome>();
        List<Float> fitnesses = new ArrayList<Float>();
        int fitnessSum = 0;
        for (Map.Entry<Chromosome, Integer> entry : fitnessMap.entrySet()) {
            chromosomes.add(entry.getKey());
            fitnessSum += entry.getValue() * entry.getValue();
            fitnesses.add((float) fitnessSum);
        }

        for(int i = 0; i < fitnesses.size(); i++){
            if(fitnessSum == 0){
                fitnesses.set(i, 1.0f);
            }
            else{
                fitnesses.set(i, (fitnesses.get(i) - fitnesses.get(0)) / (fitnessSum - fitnesses.get(0)));
            }
        }

        List<Chromosome> outputChromosomes = new ArrayList<Chromosome>();
        for(int i = 0; i < populationSize; i++){
            float threshold = RAND.nextFloat();
            boolean found = false;
            for(int j = 0; j < fitnesses.size() - 1; j++){
                if(fitnesses.get(j) < threshold && fitnesses.get(j + 1) > threshold){
                    outputChromosomes.add(chromosomes.get(j + 1));
                    found = true;
                    break;
                }
            }
            if(!found){
                outputChromosomes.add(chromosomes.get(0));
            }
        }

        return outputChromosomes;
    }

    private List<Chromosome> onePointCrossover(List<Chromosome> tournamentChromosomes, int population){
        List<Chromosome> crossedChromosomes = new ArrayList<Chromosome>();

        for (int i=0; i<population; i++){
            //Select candidates for crossover at random
            Chromosome candidate1 = tournamentChromosomes.get(RAND.nextInt(tournamentChromosomes.size()));
            Chromosome candidate2 = tournamentChromosomes.get(RAND.nextInt(tournamentChromosomes.size()));

            Chromosome offSpring = new Chromosome();

            int maxFeature = candidate1.getMaxFeature() > candidate2.getMaxFeature() ? candidate1.getMaxFeature() : candidate2.getMaxFeature();
            int minFeature = candidate1.getMinFeature() < candidate2.getMinFeature() ? candidate1.getMinFeature() : candidate2.getMinFeature();

            int crossoverPoint = candidate1.getRandomGene().getFeature();

            //randomly choose parent chromosome feature to incorporate.
            for(int f = minFeature; f <= maxFeature; f++){
                Gene gene1 = candidate1.getGene(f);
                Gene gene2 = candidate2.getGene(f);

                if(f > crossoverPoint){
                    if(gene1 != null){
                        offSpring.addGene(gene1.copy());
                    }
                }
                else{
                    if(gene2 != null){
                        offSpring.addGene(gene2.copy());
                    }
                }
            }

            crossedChromosomes.add(offSpring);
        }
        return crossedChromosomes;
    }

    private List<Chromosome> crossOver(List<Chromosome> tournamentChromosomes, int population){

        List<Chromosome> crossedChromosomes = new ArrayList<Chromosome>();

        for (int i=0; i<population; i++){
            //Select candidates for crossover at random
            Chromosome candidate1 = tournamentChromosomes.get(RAND.nextInt(tournamentChromosomes.size()));
            Chromosome candidate2 = tournamentChromosomes.get(RAND.nextInt(tournamentChromosomes.size()));

            Chromosome offSpring = new Chromosome();

            int maxFeature = candidate1.getMaxFeature() > candidate2.getMaxFeature() ? candidate1.getMaxFeature() : candidate2.getMaxFeature();
            int minFeature = candidate1.getMinFeature() < candidate2.getMinFeature() ? candidate1.getMinFeature() : candidate2.getMinFeature();

            //randomly choose parent chromosome feature to incorporate.
            for(int f = minFeature; f <= maxFeature; f++){
                Gene gene1 = candidate1.getGene(f);
                Gene gene2 = candidate2.getGene(f);

                if(RAND.nextBoolean()){
                    if(gene1 != null){
                        offSpring.addGene(gene1.copy());
                    }
                }
                else{
                    if(gene2 != null){
                        offSpring.addGene(gene2.copy());
                    }
                }
            }

            crossedChromosomes.add(offSpring);
        }
        return crossedChromosomes;
    }

    public Collection<Chromosome> mutation(List<Chromosome> chromosomes){

        // weight mutation
        for (Chromosome candidate : chromosomes) {
            if (RAND.nextFloat() <= WEIGHT_MUTATION_PERCENTAGE) {
                Gene genCandidate = candidate.getRandomGene();
                genCandidate.setWeight(genCandidate.getWeight() + (RAND.nextFloat() - 0.5f));
            }
        }

        //edge add mutation
        for (Chromosome candidate : chromosomes) {
            if (RAND.nextFloat() <= EDGE_ADD_MUTATION_PERCENTAGE) {
                int edgesToAdd = RAND.nextInt(100);
                for(int i = 0; i < edgesToAdd; i++){
                    int startNode = candidate.getRandomNode();
                    int endNode = candidate.getRandomNode();

                    boolean buildEdge = true;
                    // if connection exists and is disabled, enable
                    for (Gene gene : candidate.getGenes()) {
                        if (gene.getFrom() == startNode && gene.getTo() == endNode){
                            if(!gene.isActive()){
                                gene.setActive(true);
                            }
                            //connection exists
                            buildEdge = false;
                        }
                    }

                    if(buildEdge){
                        candidate.addGene(new Gene(FeatureIndexer.getIndex(), startNode, endNode, RAND.nextFloat(), true));
                    }
                }
            }
        }

        //edge remove mutation
        for (Chromosome candidate : chromosomes) {
            if (RAND.nextFloat() <= EDGE_REMOVE_MUTATION_PERCENTAGE) {
                int edgesToRemove = RAND.nextInt(20);
                for(int i = 0; i < edgesToRemove; i++){
                    candidate.getGenes().remove(candidate.getRandomGene());
                }
            }
        }

        //node add mutation
        for (Chromosome candidate : chromosomes){
            if (RAND.nextFloat() <= NODE_ADD_MUTATION_PERCENTAGE) {
                int nodesToAdd = RAND.nextInt(5);
                for(int i = 0; i < nodesToAdd && candidate.getNodeCount() < TOTAL_NODE_COUNT; i++){
                    Gene splitEdge = candidate.getRandomGene();
                    if(splitEdge.isActive()){
                        int new_node = Gene.getNextNodeId();

                        candidate.addGene(new Gene(FeatureIndexer.getIndex(), splitEdge.getFrom(), new_node, RAND.nextFloat(), true));
                        candidate.addGene(new Gene(FeatureIndexer.getIndex(), new_node, splitEdge.getTo(), RAND.nextFloat(), true));

                        splitEdge.setActive(false);
                    }
                }
            }
        }

        return chromosomes;
    }
}
