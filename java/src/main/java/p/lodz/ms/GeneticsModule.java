package p.lodz.ms;

import org.apache.commons.math3.genetics.BinaryMutation;
import org.apache.commons.math3.genetics.OnePointCrossover;
import org.apache.commons.math3.genetics.TournamentSelection;
import org.apache.log4j.Logger;

import p.lodz.ms.genetics.LinksGeneticAlgorithm;

public class GeneticsModule {

    private final static Logger logger = Logger.getLogger(GeneticsModule.class);

    private Configuration config;
    private StaticContainer container;
    private Double crossoverRate;
    private Double elitismRate;
    private Integer maxGenerations;
    private Double mutationRate;
    private Integer populationSize;
    private Integer tournamentArity;

    private LinksGeneticAlgorithm ga;

    public GeneticsModule() {
	readGAConfig();

	createGeneticAlgorithm();
    }

    private void createGeneticAlgorithm() {
	ga = new LinksGeneticAlgorithm(new OnePointCrossover<Integer>(),
		crossoverRate, new BinaryMutation(), mutationRate,
		new TournamentSelection(tournamentArity));

    }

    private void readGAConfig() {
	config = Configuration.getInstance();
	container = StaticContainer.getInstance();

	populationSize = config.getGaPopulationSize();
	maxGenerations = config.getGaMaxGenerations();
	elitismRate = config.getGaElitismRate();
	crossoverRate = config.getGaCrossoverRate();
	mutationRate = config.getGaMutationRate();
	tournamentArity = config.getGaTournamentArity();
    }
}
