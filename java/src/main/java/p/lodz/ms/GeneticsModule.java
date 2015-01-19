package p.lodz.ms;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.math3.genetics.BinaryMutation;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.FixedGenerationCount;
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
	logger.info("Loading initial network");
	File network = new File(config.getScenarioNetwork());
	LinksChromosome initial = PythonMethods.getInstance()
		.convertNetworkToBinary(network);
	new MATSimThread(initial).run();

	File fitnessSource = new File(initial.getDir() + "/"
		+ StaticContainer.fitnessFilename);
	File fitnessDest = new File(Configuration.getInstance()
		.getProjectDir()
		+ StaticContainer.fitnessInitialFilename);

	FileUtils.copyFile(fitnessSource, fitnessDest);

	// reset the prefix for output clarification
	StaticContainer.getInstance().setGaIterationPrefix("ga.");

	logger.info("Starting genetic algorithm");
	Population population = randomPopulation(initial.getLength());
	StoppingCondition stopCond = new FixedGenerationCount(maxGenerations);
	Population finalPopulation = ga.evolve(population, stopCond);

    }

    private Population randomPopulation(int chromosomeLength) {
	List<Chromosome> popList = new LinkedList<Chromosome>();
	for (int i = 0; i < populationSize; i++) {
	    LinksChromosome randChrom = new LinksChromosome(
		    LinksChromosome
			    .randomBinaryRepresentation(chromosomeLength));
	    popList.add(randChrom);
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
