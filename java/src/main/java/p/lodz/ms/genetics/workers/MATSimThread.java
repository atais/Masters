package p.lodz.ms.genetics.workers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.log4j.Logger;

import p.lodz.ms.Context;
import p.lodz.ms.db.ChromosomeDao;
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
	    logger.debug("--------------------------");
	    logger.debug("Preparing structure");
	    prepareStructure();
	    if (chromosome.getFitness() == Double.NEGATIVE_INFINITY) {
		logger.info("Running MATSIM");
		runMatsim();
		logger.debug("Clean-up");
		cleanAndGraph();
	    } else {
		String fitness = Double.toString(this.chromosome.getFitness());
		File f = FileManager.getFitnessFile(this.chromosome);
		Files.write(f.toPath(), fitness.getBytes(),
			new OpenOption[] { StandardOpenOption.CREATE });
		logger.info("Skipping calculations!");
	    }
	    logger.debug("--------------------------");
	} catch (IOException e) {
	    logger.error(ExceptionUtils.getStackTrace(e));
	} catch (InterruptedException e) {
	    logger.error(ExceptionUtils.getStackTrace(e));
	}
    }

    private void cleanAndGraph() {
	Double fitness = new PythonMethods().organiseOutput(FileManager
		.getChromosomeDir(chromosome));
	ChromosomeDao.writeChromosome(chromosome, fitness);
    }

    private void runMatsim() throws InterruptedException {
	List<String> command = new ArrayList<String>();

	command.add(Context.getI().getConfig().getProjectJavaPath());
	command.add(Context.getI().getConfig().getProjectMatsimXmxArg());
	command.add("-cp");
	command.add(Context.getI().getConfig().getProjectMatsimJar());
	command.add("org.matsim.run.Controler");
	command.add(FileManager.getChromosomeConfig(chromosome));

	logger.debug(command);

	try {
	    ProcessBuilder builder = new ProcessBuilder(command);
	    Process process = builder.start();
	    Context.getI().addChildProcess(process);
	    InputStream is = process.getInputStream();
	    InputStreamReader isr = new InputStreamReader(is);
	    BufferedReader br = new BufferedReader(isr);
	    String line;
	    while ((line = br.readLine()) != null) {
		if (line.contains("### ITERATION")) {
		    logger.debug(line);
		} else if (line.contains("ERROR")) {
		    logger.error(line);
		} else if (line.contains("WARN")) {
		    logger.warn(line);
		} else if (line.contains("FATAL")) {
		    logger.fatal(line);
		} else if (line.contains("Exception")) {
		    if (!line.contains("do not use except of you have to")) {
			logger.fatal(line);
		    }
		}
	    }
	    process.waitFor();
	    Context.getI().removeChildProcess(process);
	} catch (IOException e) {
	    logger.error(ExceptionUtils.getStackTrace(e));
	} catch (InterruptedException e) {
	    logger.error(ExceptionUtils.getStackTrace(e));
	}

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
