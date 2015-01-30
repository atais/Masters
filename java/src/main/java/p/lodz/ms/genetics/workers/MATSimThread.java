package p.lodz.ms.genetics.workers;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.log4j.Logger;
import org.matsim.core.controler.Controler;

import p.lodz.ms.genetics.LinksChromosome;
import p.lodz.ms.integration.PythonMethods;
import p.lodz.ms.manage.FileManager;

public class MATSimThread implements Runnable {

    private final static Logger logger = Logger.getLogger(MATSimThread.class);
    private LinksChromosome chromosome;

    public MATSimThread(Chromosome chromosome) {
	this.chromosome = (LinksChromosome) chromosome;
    }

    @Override
    public void run() {
	try {
	    logger.info("--------------------------");
	    logger.info("Preparing structure");
	    prepareStructure();
	    logger.info("Running MATSIM");
	    runMatsim();
	    logger.info("Clean-up");
	    cleanAndGraph();
	    logger.info("--------------------------");
	} catch (IOException e) {
	    logger.error(ExceptionUtils.getStackTrace(e));
	} catch (InterruptedException e) {
	    logger.error(ExceptionUtils.getStackTrace(e));
	}

	this.chromosome.getFitness();
    }

    private void cleanAndGraph() {
	new PythonMethods().organiseOutput(FileManager
		.getChromosomeDir(chromosome));
    }

    @SuppressWarnings("deprecation")
    private void runMatsim() throws InterruptedException {
	final Controler controler = new Controler(
		new String[] { FileManager.getChromosomeConfig(chromosome) });
	controler.setOverwriteFiles(true);
	controler.run();
    }

    private void prepareStructure() throws IOException {
	// parse chromosome to network
	FileUtils.forceMkdir(FileManager.getChromosomeDir(chromosome));
	FileManager.convertChromosomeToNetwork(chromosome);
	// write chromosome.txt binary
	FileManager.createChromosomeText(chromosome);
	// copy config
	FileManager.prepareChromosomeConfig(chromosome);
    }
}