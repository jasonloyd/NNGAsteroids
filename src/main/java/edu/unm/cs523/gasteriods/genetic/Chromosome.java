package edu.unm.cs523.gasteriods.genetic;

import java.util.*;

/**
 * Chromosome object that holds an ordered list of Genes.
 *
 * @author John Ericksen
 */
public class Chromosome {

    private static final Random RAND = new Random(System.currentTimeMillis());

    private Map<Integer, Gene> genes = new HashMap<Integer, Gene>();
    private List<Gene> geneList = new ArrayList<Gene>();
    private List<Integer> nodes = new ArrayList<Integer>();
    private Integer minFeature = null;
    private Integer maxFeature = null;

    public Chromosome() {
        nodes.addAll(Gene.ALL_GIVEN_NODES);
    }

    public Collection<Gene> getGenes() {
        return genes.values();
    }

    public void addGene(Gene gene){
        if(minFeature == null || minFeature > gene.getFeature()){
            minFeature = gene.getFeature();
        }
        if(maxFeature == null || maxFeature < gene.getFeature()){
            maxFeature = gene.getFeature();
        }
        genes.put(gene.getFeature(), gene);
        geneList.add(gene);
        if(!nodes.contains(gene.getFrom())){
            nodes.add(gene.getFrom());
        }
        if(!nodes.contains(gene.getTo())){
            nodes.add(gene.getTo());
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        boolean first = true;
        for (Gene gene : genes.values()) {
            if(first){
                first = false;
            }
            else{
                builder.append(";");
            }
            builder.append(gene);
        }

        return builder.toString();
    }

    public Gene getGene(int feature) {
        return genes.get(feature);
    }

    public Gene getRandomGene(){
        return geneList.get(RAND.nextInt(geneList.size()));
    }

    public int getRandomNode(){
        return nodes.get(RAND.nextInt(nodes.size()));
    }

    public Integer getMaxFeature(){
        return maxFeature;
    }

    public Integer getMinFeature(){
        return minFeature;
    }

    public Chromosome copy() {
        Chromosome copy = new Chromosome();

        for (Gene gene : genes.values()) {
            copy.addGene(gene.copy());
        }

        return copy;
    }

    public int getNodeCount() {
        return nodes.size();
    }
}
