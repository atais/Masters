package p.lodz.ms.manage;

import java.io.File;

import p.lodz.ms.Context;
import p.lodz.ms.genetics.LinksChromosome;
import p.lodz.ms.integration.PythonMethods;

public class GraphManager {

    public static void drawFacilitiesGraph(LinksChromosome chromosome) {
	Context c = Context.getI();
	File chromosomeDir = FileManager.getChromosomeDir(chromosome);

	File network = new File(chromosomeDir + "/" + c.getProp("file.network"));
	File facilities = new File(c.getConfig().getScenarioFacilities());
	File fOutput = new File(chromosomeDir + "/"
		+ c.getProp("file.graph.facilities"));

	new PythonMethods().facilitiesGraph(network, facilities, fOutput);
    }

    public static void drawEventsGraph(LinksChromosome chromosome) {
	Context c = Context.getI();
	File chromDir = FileManager.getChromosomeDir(chromosome);

	File network = new File(chromDir + "/" + c.getProp("file.network"));
	File events = new File(chromDir + "/" + c.getProp("file.outputEvents"));
	File eOutput = new File(chromDir + "/"
		+ c.getProp("file.folder.events"));

	new PythonMethods().eventsGraph(network, events, eOutput);
    }

    public static void drawNetworkGraph(LinksChromosome chromosome) {
	Context c = Context.getI();
	File chromDir = FileManager.getChromosomeDir(chromosome);

	File network = new File(chromDir + "/" + c.getProp("file.network"));
	File nOutput = new File(chromDir + "/"
		+ c.getProp("file.graph.network"));

	new PythonMethods().networkGraph(network, nOutput);
    }
}
