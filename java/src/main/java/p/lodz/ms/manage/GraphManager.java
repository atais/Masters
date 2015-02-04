package p.lodz.ms.manage;

import java.io.File;

import p.lodz.ms.Context;
import p.lodz.ms.genetics.LinksChromosome;
import p.lodz.ms.integration.PythonMethods;

public class GraphManager {

    public static void drawFacilitiesGraph(LinksChromosome chromosome) {
	File chromosomeDir = FileManager.getChromosomeDir(chromosome);

	File network = new File(chromosomeDir + "/" + Context.networkFileName);
	File facilities = new File(Context.getI().getConfig()
		.getScenarioFacilities());
	File fOutput = new File(chromosomeDir + "/"
		+ Context.facilitiesGraphName);

	new PythonMethods().facilitiesGraph(network, facilities, fOutput);
    }

    public static void drawEventsGraph(LinksChromosome chromosome) {
	File chromosomeDir = FileManager.getChromosomeDir(chromosome);

	File network = new File(chromosomeDir + "/" + Context.networkFileName);
	File events = new File(chromosomeDir + "/"
		+ Context.outputEventsFileName);
	File eOutput = new File(chromosomeDir + "/" + Context.eventsFolderName);

	new PythonMethods().eventsGraph(network, events, eOutput);
    }

    public static void drawNetworkGraph(LinksChromosome chromosome) {
	File chromosomeDir = FileManager.getChromosomeDir(chromosome);

	File network = new File(chromosomeDir + "/" + Context.networkFileName);
	File nOutput = new File(chromosomeDir + "/" + Context.networkGraphName);

	new PythonMethods().networkGraph(network, nOutput);
    }
}
