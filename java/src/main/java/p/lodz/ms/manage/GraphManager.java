package p.lodz.ms.manage;

import java.io.File;

import p.lodz.ms.Configuration;
import p.lodz.ms.StaticContainer;
import p.lodz.ms.genetics.LinksChromosome;
import p.lodz.ms.integration.PythonMethods;

public class GraphManager {

    public static void drawFacilitiesGraph(LinksChromosome chromosome) {
	File chromosomeDir = FileManager.getChromosomeDir(chromosome);

	File network = new File(chromosomeDir + "/"
		+ StaticContainer.networkFileName);
	File facilities = new File(Configuration.getInstance()
		.getScenarioFacilities());
	File fOutput = new File(chromosomeDir + "/"
		+ StaticContainer.facilitiesGraphName);

	new PythonMethods().facilitiesGraph(network, facilities,
		fOutput);
    }

    public static void drawEventsGraph(LinksChromosome chromosome) {
	File chromosomeDir = FileManager.getChromosomeDir(chromosome);

	File network = new File(chromosomeDir + "/"
		+ StaticContainer.networkFileName);
	File events = new File(chromosomeDir + "/"
		+ StaticContainer.outputEventsFileName);
	File eOutput = new File(chromosomeDir + "/"
		+ StaticContainer.eventsFolderName);

	new PythonMethods().eventsGraph(network, events, eOutput);
    }

    public static void drawNetworkGraph(LinksChromosome chromosome) {
	File chromosomeDir = FileManager.getChromosomeDir(chromosome);

	File network = new File(chromosomeDir + "/"
		+ StaticContainer.networkFileName);
	File nOutput = new File(chromosomeDir + "/"
		+ StaticContainer.networkGraphName);

	new PythonMethods().networkGraph(network, nOutput);
    }
}
