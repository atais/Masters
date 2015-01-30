package p.lodz.ms.genetics;

import org.apache.commons.math3.exception.OutOfRangeException;
import org.apache.commons.math3.genetics.CrossoverPolicy;
import org.apache.commons.math3.genetics.GeneticAlgorithm;
import org.apache.commons.math3.genetics.MutationPolicy;
import org.apache.commons.math3.genetics.Population;
import org.apache.commons.math3.genetics.SelectionPolicy;
import org.apache.log4j.Logger;

import p.lodz.ms.Configuration;
import p.lodz.ms.StaticContainer;
import p.lodz.ms.integration.PythonMethods;
import p.lodz.ms.manage.FileManager;
import p.lodz.ms.manage.GraphManager;

public class LinksGeneticAlgorithm extends GeneticAlgorithm {

    private final static Logger logger = Logger
	    .getLogger(LinksGeneticAlgorithm.class);

    public LinksGeneticAlgorithm(CrossoverPolicy crossoverPolicy,
	    double crossoverRate, MutationPolicy mutationPolicy,
	    double mutationRate, SelectionPolicy selectionPolicy)
	    throws OutOfRangeException {
	super(crossoverPolicy, crossoverRate, mutationPolicy, mutationRate,
		selectionPolicy);
    }

    // we need to keep track of the current generation counter
    // as well as, each best chromosome needs some special care
    @Override
    public Population nextGeneration(Population current) {
	logger.info("Evaluating generation: " + this.getGenerationsEvolved()
		+ "/" + Configuration.getInstance().getGaMaxGenerations());
	moveBestChromosome((LinksChromosome) current.getFittestChromosome());
	Population next = super.nextGeneration(current);
	StaticContainer.getInstance().increaseCurrentGeneration();
	return next;
    }

    // special stuff for best chromosome
    private void moveBestChromosome(LinksChromosome chromosome) {
	logger.info("--------------------------");
	logger.info("Working with the best chromosome: "
		+ chromosome.getUuid().toString());

	logger.info("Drawing events");
	GraphManager.drawEventsGraph(chromosome);
	logger.info("... doing all the best!");
	Boolean duplicates = new PythonMethods().organiseBest(FileManager
		.getChromosomeDir(chromosome));
	if (duplicates){
	    logger.warn("There are duplicates in chromosomes!");
	}
	logger.info("--------------------------");
    }

}
