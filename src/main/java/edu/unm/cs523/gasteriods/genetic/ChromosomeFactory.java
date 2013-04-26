package edu.unm.cs523.gasteriods.genetic;

import edu.unm.cs523.gasteriods.neural.FeatureIndexer;

import java.util.Random;

/**
 * Builds a Chromosome
 *
 * @author John Ericksen
 */
public class ChromosomeFactory {

    private static final Random RAND = new Random(System.currentTimeMillis());

    public Chromosome buildFromString(String input){
        String[] rawGenes = input.split(";");
        Chromosome chromosome = new Chromosome();
        for (String rawGene : rawGenes) {
            chromosome.addGene(buildGene(rawGene));
        }
        return chromosome;
    }

    private Gene buildGene(String rawGene) {

        String strippedRawGene = rawGene.replaceAll("\\[", "").replaceAll("\\]", "");

        String[] geneToken = strippedRawGene.split(",");

        int feature = Integer.parseInt(geneToken[0]);
        int from = Integer.parseInt(geneToken[1]);
        int to = Integer.parseInt(geneToken[2]);
        float weight = Float.parseFloat(geneToken[3]);
        boolean active = Boolean.parseBoolean(geneToken[4]);

        return new Gene(feature, from, to, weight, active);
    }

    public Chromosome buildRandomFullyConnected(){

        Chromosome chromosome = new Chromosome();

        // wire to all outputs, all inputs including bias node
        for(int outputIndex : Gene.OUTPUT_NODES) {
            chromosome.addGene(new Gene(FeatureIndexer.getIndex(), Gene.BIAS_NODE, outputIndex, randomFloat(), true));
            for(int eyeIndex : Gene.EYE_NODES) {
                chromosome.addGene(new Gene(FeatureIndexer.getIndex(), eyeIndex, outputIndex, randomFloat(), true));
            }
        }

        return chromosome;
    }

    public static float randomFloat(){
        return (RAND.nextBoolean()? -1 : 1) * RAND.nextFloat();
    }
}
