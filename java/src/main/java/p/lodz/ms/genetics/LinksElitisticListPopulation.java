package p.lodz.ms.genetics;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.math3.exception.NotPositiveException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.exception.NumberIsTooLargeException;
import org.apache.commons.math3.exception.OutOfRangeException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.ElitisticListPopulation;
import org.apache.commons.math3.genetics.Population;

import p.lodz.ms.Context;
import p.lodz.ms.genetics.workers.MATSimThread;

public class LinksElitisticListPopulation extends ElitisticListPopulation {

    // needs to be overriden
    private double elitismRate;

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
	boolean requirePrecalc = false;
	for (Chromosome chromosome : this.getChromosomes()) {
	    // fitness not GETfitness!
	    if (chromosome.fitness() == Double.NEGATIVE_INFINITY) {
		requirePrecalc = true;
		break;
	    }
	}
	if (requirePrecalc) {
	    precalculate();
	}

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
    private void precalculate() {
	Integer threads = Context.getI().getConfig().getProjectThreads();
	ExecutorService executor = Executors.newFixedThreadPool(threads);
	for (Chromosome chromosome : getChromosomeList()) {
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

	// how many are copied from previous gen
	for (int i = 0; i < getElitismRate(); i++) {
	    nextGeneration.addChromosome(oldChromosomes.get(i));
	}
	return nextGeneration;
    }

    @Override
    public void setElitismRate(final double elitismRate)
	    throws OutOfRangeException {
	if (elitismRate < 0 || elitismRate > this.getPopulationLimit()) {
	    throw new OutOfRangeException(LocalizedFormats.ELITISM_RATE,
		    elitismRate, 0, this.getPopulationLimit());
	}
	this.elitismRate = elitismRate;
    }
    
    public double getElitismRate() {
        return this.elitismRate;
    }

}
