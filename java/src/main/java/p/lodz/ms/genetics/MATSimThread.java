package p.lodz.ms.genetics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.log4j.Logger;
import org.matsim.core.controler.Controler;

import p.lodz.ms.Configuration;
import p.lodz.ms.StaticContainer;
import p.lodz.ms.integration.PythonMethods;

public class MATSimThread implements Runnable {

    private final static Logger logger = Logger.getLogger(MATSimThread.class);
    private LinksChromosome chromosome;
    private File destNetwork;
    private File destConfig;
    private File dir;

    public MATSimThread(Chromosome chromosome) {
	this.chromosome = (LinksChromosome) chromosome;
    }

    @Override
    public void run() {
	try {
	    logger.info("Preparing structure");
	    prepareStructure();
	    logger.info("Running MATSIM");
	    runMatsim();
	    logger.info("Clean-up");
	    cleanAndGraph();
	} catch (IOException e) {
	    logger.error(e.getCause());
	}

	this.chromosome.getFitness();
    }

    private void cleanAndGraph() {
	PythonMethods.getInstance().organiseOutput(chromosome.getDir());
	
	PythonMethods.getInstance().networkGraph(destNetwork, new File(dir+StaticContainer.networkGraphName));
	PythonMethods.getInstance().eventsGraph(destNetwork, new File(dir+StaticContainer.outputEventsFileName), new File(dir+StaticContainer.eventsFolderName));
    }

    private void runMatsim() {
	final Controler controler = new Controler(
		new String[] { destConfig.getAbsolutePath() });
	controler.setOverwriteFiles(true);
	controler.run();
    }

    private void prepareStructure() throws IOException {
	dir = chromosome.getDir();
	FileUtils.forceMkdir(dir);

	// write chromosome.txt binary
	FileWriter writer = new FileWriter(dir +"/"
		+ StaticContainer.chromosomeFileName);
	writer.write(this.chromosome.toString());
	writer.close();

	// copy config
	File baseConfig = new File(Configuration.getInstance()
		.getScenarioConfig());
	destConfig = new File(dir +"/"+ StaticContainer.configFileName);
	FileUtils.copyFile(baseConfig, destConfig);

	// parse chromosome to network
	destNetwork = new File(dir +"/"+ StaticContainer.networkFileName);
	PythonMethods.getInstance().convertBinaryToNetwork(this.chromosome,
		destNetwork);

	// prepare config
	File facilities = new File(Configuration.getInstance()
		.getScenarioFacilities());
	File population = new File(Configuration.getInstance()
		.getScenarioPopulation());
	Integer iterations = Configuration.getInstance()
		.getScenarioIterations();
	PythonMethods.getInstance().customizeConfig(destConfig, facilities,
		destNetwork, population, dir, iterations);

    }
}
