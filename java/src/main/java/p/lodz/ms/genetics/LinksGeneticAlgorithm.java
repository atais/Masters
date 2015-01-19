package p.lodz.ms.genetics;

import java.io.File;

import org.apache.commons.math3.exception.OutOfRangeException;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.CrossoverPolicy;
import org.apache.commons.math3.genetics.GeneticAlgorithm;
import org.apache.commons.math3.genetics.MutationPolicy;
import org.apache.commons.math3.genetics.Population;
import org.apache.commons.math3.genetics.SelectionPolicy;
import org.apache.log4j.Logger;

import p.lodz.ms.Configuration;
import p.lodz.ms.StaticContainer;
import p.lodz.ms.integration.PythonMethods;

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

    @Override
    public Population nextGeneration(Population current) {
	logger.info("Evaluating generation: " + this.getGenerationsEvolved());
	moveBestChromosome((LinksChromosome) current.getFittestChromosome());
	Population value = super.nextGeneration(current);
	// we need to increment iteration value in static container
	// otherwise the chromosme does not know where is it's path
	StaticContainer.getInstance().setGaCurrentIteration(
		StaticContainer.getInstance().getGaCurrentIteration() + 1);
	return value;
    }

    private void moveBestChromosome(LinksChromosome fittest) {
	File network = new File(fittest.getDir()+"/"+StaticContainer.networkFileName);
	File facilities = new File(Configuration.getInstance().getScenarioFacilities());
	File output = fittest.getDir();
	File events = new File(fittest.getDir()+"/"+StaticContainer.outputEventsFileName);
	File eOutput = new File(output+"/"+StaticContainer.eventsFolderName);

	PythonMethods.getInstance().facilitiesGraph(network, facilities, output);
	PythonMethods.getInstance().networkGraph(network, output);
	PythonMethods.getInstance().eventsGraph(network, events, eOutput);
	
	File dir = ((LinksChromosome) fittest).getDir();
	PythonMethods.getInstance().organiseBest(dir);
    }

}
