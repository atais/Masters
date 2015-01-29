package p.lodz.ms.genetics.workers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.genetics.Chromosome;
import org.apache.log4j.Logger;

import p.lodz.ms.genetics.LinksChromosome;
import p.lodz.ms.genetics.LinksMutation;

public class ChromosomeInitializer implements Runnable {

    private final static Logger logger = Logger
	    .getLogger(ChromosomeInitializer.class);

    private List<Chromosome> popList;
    private int chromosomeLength;

    public ChromosomeInitializer(List<Chromosome> popList, int chromosomeLength) {
	this.popList = popList;
	this.chromosomeLength = chromosomeLength;
    }

    @Override
    public void run() {
	// binary all true list
	logger.debug("Randomizing new chromosome");
	List<Integer> rList = new ArrayList<Integer>(chromosomeLength);
	for (int j = 0; j < chromosomeLength; j++) {
	    rList.add(1);
	}

	Chromosome chromosome = new LinksChromosome(rList);
	LinksMutation mutation = new LinksMutation();
	// try to delete as much as possible
	for (int i = 0; i < chromosomeLength / 2; i++) {
	    chromosome = mutation.mutate(chromosome);
	}

	popList.add(chromosome);
    }
}
