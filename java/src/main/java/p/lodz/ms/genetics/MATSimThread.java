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

    public MATSimThread(Chromosome chromosome) {
	this.chromosome = (LinksChromosome) chromosome;
    }

    @Override
    public void run() {
	try {
	    File config = prepareStructure();
	    runMatsim(config);
	    cleanAndGraph();
	} catch (IOException e) {
	    logger.error(e.getCause());
	}

	this.chromosome.getFitness();
    }

    private void cleanAndGraph() {
	PythonMethods.getInstance().organiseOutput(chromosome.getDir());
    }

    private void runMatsim(File config) {
	final Controler controler = new Controler(
		new String[] { config.getAbsolutePath() });
	controler.run();
    }

    private File prepareStructure() throws IOException {
	File dir = chromosome.getDir();
	FileUtils.forceMkdir(chromosome.getDir());

	// write chromosome.txt binary
	FileWriter writer = new FileWriter(dir
		+ StaticContainer.chromosomeFileName);
	writer.write(this.chromosome.toString());
	writer.close();

	// copy config
	File baseConfig = new File(Configuration.getInstance()
		.getScenarioConfig());
	File destConfig = new File(dir + StaticContainer.configFileName);
	FileUtils.copyFile(baseConfig, destConfig);

	// parse chromosome to network
	File destNetwork = new File(dir + StaticContainer.networkFileName);
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

	return destConfig;
    }
}
