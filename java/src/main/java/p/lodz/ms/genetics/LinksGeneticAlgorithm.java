package p.lodz.ms.genetics;

import org.apache.commons.math3.exception.OutOfRangeException;
import org.apache.commons.math3.genetics.CrossoverPolicy;
import org.apache.commons.math3.genetics.GeneticAlgorithm;
import org.apache.commons.math3.genetics.MutationPolicy;
import org.apache.commons.math3.genetics.Population;
import org.apache.commons.math3.genetics.SelectionPolicy;
import org.apache.commons.math3.genetics.StoppingCondition;
import org.apache.log4j.Logger;

import p.lodz.ms.Context;
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
    public Population evolve(final LinksElitisticListPopulation initial,
	    final StoppingCondition condition) {
	LinksElitisticListPopulation current = initial;
	while (!condition.isSatisfied(current)) {
	    logger.info("--------------------------");
	    logger.info("Evaluating generation: "
		    + Context.getI().getGaCurrentIteration() + "/"
		    + Context.getI().getConfig().getGaMaxGenerations());
	    logger.info("--------------------------");
	    moveBestChromosome((LinksChromosome) current.getFittestChromosome());
	    current = (LinksElitisticListPopulation) nextGeneration(current);
	    Context.getI().increaseGaCurrentIteration();
	}
	return current;
    }

    public Population evolve(final Population initial,
	    final StoppingCondition condition, final int startGenetation) {
	Context.getI().setGaCurrentIteration(startGenetation);
	return this.evolve(initial, condition);
    }

    // special stuff for best chromosome
    private void moveBestChromosome(LinksChromosome chromosome) {
	logger.info("--------------------------");
	logger.info("Working with the best chromosome: "
		+ chromosome.getUuid().toString());

	logger.info("Drawing events");
	GraphManager.drawEventsGraph(chromosome);
	logger.info("... doing all the best!");
	new PythonMethods().organiseBest(FileManager
		.getChromosomeDir(chromosome));
	logger.info("--------------------------");
    }

    @Override
    public int getGenerationsEvolved() {
	return Context.getI().getGaCurrentIteration();
    }
}
