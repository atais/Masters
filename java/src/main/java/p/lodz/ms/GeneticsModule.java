package p.lodz.ms;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.math3.genetics.BinaryMutation;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.FixedGenerationCount;
import org.apache.commons.math3.genetics.GeneticAlgorithm;
import org.apache.commons.math3.genetics.OnePointCrossover;
import org.apache.commons.math3.genetics.Population;
import org.apache.commons.math3.genetics.StoppingCondition;
import org.apache.commons.math3.genetics.TournamentSelection;
import org.apache.log4j.Logger;

import p.lodz.ms.genetics.LinksChromosome;
import p.lodz.ms.genetics.LinksElitisticListPopulation;
import p.lodz.ms.genetics.LinksGeneticAlgorithm;
import p.lodz.ms.genetics.MATSimThread;
import p.lodz.ms.integration.PythonMethods;
import p.lodz.ms.manage.FileManager;
import p.lodz.ms.manage.GraphManager;

public class GeneticsModule {

    private final static Logger logger = Logger.getLogger(GeneticsModule.class);

    private Configuration config;
    private Double crossoverRate;
    private Double elitismRate;
    private Integer maxGenerations;
    private Double mutationRate;
    private Integer populationSize;
    private Integer tournamentArity;

    public GeneticsModule() {
	try {
	    readGAConfig();
	    runGeneticAlgorithm();
	} catch (IOException e) {
	    logger.error(ExceptionUtils.getStackTrace(e));
	}
    }

    private void runGeneticAlgorithm() throws IOException {
	LinksGeneticAlgorithm ga = new LinksGeneticAlgorithm(
		new OnePointCrossover<Integer>(), crossoverRate,
		new BinaryMutation(), mutationRate, new TournamentSelection(
			tournamentArity));

	// set the prefix for output clarification
	StaticContainer.getInstance().setGaIterationPrefix("begin.");
	logger.info("-----------------------");
	logger.info("Loading initial network");
	logger.info("-----------------------");
	File network = new File(config.getScenarioNetwork());
	LinksChromosome initial = PythonMethods.getInstance()
		.convertNetworkToBinary(network);
	new MATSimThread(initial).run();
	initialGraphMethods(initial);

	// reset the prefix for output clarification
	StaticContainer.getInstance().setGaIterationPrefix("ga.");

	logger.info("--------------------------");
	logger.info("Starting genetic algorithm");
	logger.info("--------------------------");
	LinksElitisticListPopulation population = randomPopulation(initial
		.getLength());
	StoppingCondition stopCond = new FixedGenerationCount(maxGenerations);
	Population finalPopulation = ga.evolve(population, stopCond);
    }

    private void initialGraphMethods(LinksChromosome initial) {
	GraphManager.drawFacilitiesGraph(initial);
	GraphManager.drawEventsGraph(initial);
	GraphManager.drawNetworkGraph(initial);
	FileManager.createInitialFitness(initial);

    }

    private LinksElitisticListPopulation randomPopulation(int chromosomeLength) {
	List<Chromosome> popList = new LinkedList<Chromosome>();
	for (int i = 0; i < populationSize; i++) {
	    // random binary list
	    List<Integer> rList = new ArrayList<Integer>(chromosomeLength);
	    for (int j = 0; j < chromosomeLength; j++) {
		if (GeneticAlgorithm.getRandomGenerator().nextInt(10) < 2) {
		    rList.add(0);
		} else {
		    rList.add(1);
		}
	    }
	    popList.add(new LinksChromosome(rList));
	}
	return new LinksElitisticListPopulation(popList, popList.size(),
		elitismRate);
    }

    private void readGAConfig() {
	config = Configuration.getInstance();

	populationSize = config.getGaPopulationSize();
	maxGenerations = config.getGaMaxGenerations();
	elitismRate = config.getGaElitismRate();
	crossoverRate = config.getGaCrossoverRate();
	mutationRate = config.getGaMutationRate();
	tournamentArity = config.getGaTournamentArity();
    }
}
