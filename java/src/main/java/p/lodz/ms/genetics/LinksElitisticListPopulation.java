package p.lodz.ms.genetics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.math3.exception.NotPositiveException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.exception.NumberIsTooLargeException;
import org.apache.commons.math3.exception.OutOfRangeException;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.ElitisticListPopulation;
import org.apache.commons.math3.genetics.Population;
import org.apache.commons.math3.util.FastMath;
import org.apache.log4j.Logger;

import p.lodz.ms.Context;
import p.lodz.ms.genetics.workers.MATSimThread;

public class LinksElitisticListPopulation extends ElitisticListPopulation {

    private final static Logger logger = Logger
	    .getLogger(LinksElitisticListPopulation.class);

    public LinksElitisticListPopulation(int populationLimit, double elitismRate)
	    throws NotPositiveException, OutOfRangeException {
	super(populationLimit, elitismRate);
    }

    public LinksElitisticListPopulation(List<Chromosome> chromosomes,
	    int populationLimit, double elitismRate)
	    throws NullArgumentException, NotPositiveException,
	    NumberIsTooLargeException, OutOfRangeException {
	super(chromosomes, populationLimit, elitismRate);
    }

    // MAT-sim needs the fitness to decrease.
    // Because fitness is an average trip duration, the shorter the better.
    @Override
    public Chromosome getFittestChromosome() {
	// precalculate using threads
	List<Chromosome> needToPrecalc = new ArrayList<Chromosome>();
	for (Chromosome chromosome : getChromosomes()) {
	    // .fitness not .getFitness!!!
	    if (chromosome.fitness() == Double.MAX_VALUE) {
		needToPrecalc.add(chromosome);
	    }
	}
	logger.info("Precalculating " + needToPrecalc.size() + "/"
		+ getChromosomes().size());
	precalculate(needToPrecalc);

	// best so far
	Chromosome bestChromosome = this.getChromosomes().get(0);
	for (Chromosome chromosome : this.getChromosomes()) {
	    // We look for the minimum of fitness!!! That's why < 0
	    if (chromosome.compareTo(bestChromosome) < 0) {
		// better chromosome found
		bestChromosome = chromosome;
	    }
	}
	return bestChromosome;
    }

    // We need to precalculate the fitness using threads due to long
    // calculations.
    private void precalculate(List<Chromosome> needToPrecalc) {
	Integer threads = Context.getI().getConfig().getProjectThreads();
	ExecutorService executor = Executors.newFixedThreadPool(threads);
	for (Chromosome chromosome : needToPrecalc) {
	    Runnable worker = new MATSimThread(chromosome);
	    executor.execute(worker);
	}
	executor.shutdown();
	while (!executor.isTerminated()) {
	}
    }

    // To maintain the type during generations
    @Override
    public Population nextGeneration() {
	// initialize a new generation with the same parameters
	LinksElitisticListPopulation nextGeneration = new LinksElitisticListPopulation(
		getPopulationLimit(), getElitismRate());

	final List<Chromosome> oldChromosomes = getChromosomeList();
	Collections.sort(oldChromosomes);

	// index of the last "not good enough" chromosome
	int boundIndex = (int) FastMath.ceil((1.0 - getElitismRate())
		* oldChromosomes.size());
	for (int i = 0; i < boundIndex; i++) {
	    nextGeneration.addChromosome(oldChromosomes.get(i));
	}
	return nextGeneration;
    }

}
