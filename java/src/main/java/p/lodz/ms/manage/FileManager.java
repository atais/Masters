package p.lodz.ms.manage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import p.lodz.ms.Configuration;
import p.lodz.ms.Context;
import p.lodz.ms.genetics.LinksChromosome;
import p.lodz.ms.integration.PythonMethods;

public class FileManager {

    private final static Logger logger = Logger.getLogger(FileManager.class);

    public static File getChromosomeDir(LinksChromosome chromosome) {
	Context c = Context.getI();
	String output = c.getConfig().getProjectOutputDir();
	String projName = c.getConfig().getProjectName() + "/";
	String iteration = c.getGaCurrentIterationPath();
	String uuid = chromosome.getUuid().toString();
	String dirName = output + projName + iteration + uuid;
	return new File(dirName);
    }

    public static File getFitnessFile(LinksChromosome chromosome) {
	String fileName = Context.getI().getProp("file.fitness");
	String file = getChromosomeDir(chromosome) + "/" + fileName;
	return new File(file);
    }

    /**
     * The initial fitness file in project dir
     * 
     * @param chromosome
     */
    public static void createInitialFitness(LinksChromosome chromosome) {
	Context c = Context.getI();
	File fitnessSource = new File(getChromosomeDir(chromosome) + "/"
		+ c.getProp("file.fitness"));
	File fitnessDest = new File(c.getConfig().getProjectDir()
		+ c.getProp("file.fitnessInitial"));

	try {
	    FileUtils.copyFile(fitnessSource, fitnessDest);
	} catch (IOException e) {
	    logger.error(ExceptionUtils.getStackTrace(e));
	}
    }

    public static void createChromosomeText(LinksChromosome chromosome) {
	File chromDir = FileManager.getChromosomeDir(chromosome);

	try {
	    FileWriter writer = new FileWriter(chromDir + "/"
		    + Context.getI().getProp("file.chromosome"));
	    writer.write(chromosome.toString());
	    writer.close();
	} catch (IOException e) {
	    logger.error(ExceptionUtils.getStackTrace(e));
	}
    }

    public static void convertChromosomeToNetwork(LinksChromosome chromosome) {
	File chromosomeDir = FileManager.getChromosomeDir(chromosome);
	File destNetwork = new File(chromosomeDir + "/"
		+ Context.getI().getProp("file.network"));
	new PythonMethods().convertBinaryToNetwork(chromosome, destNetwork);
    }

    public static void prepareChromosomeConfig(LinksChromosome chromosome) {
	Configuration c = Context.getI().getConfig();
	File chromDir = FileManager.getChromosomeDir(chromosome);
	File baseConfig = new File(c.getScenarioConfig());
	File chromosomeConfig = new File(chromDir + "/"
		+ Context.getI().getProp("file.config"));

	try {
	    FileUtils.copyFile(baseConfig, chromosomeConfig);
	    File network = new File(chromDir + "/"
		    + Context.getI().getProp("file.network"));
	    File facilities = new File(c.getScenarioFacilities());
	    File population = new File(c.getScenarioPopulation());
	    Integer iterations = c.getScenarioIterations();

	    new PythonMethods().customizeConfig(chromosomeConfig, facilities,
		    network, population, chromDir, iterations);
	} catch (IOException e) {
	    logger.error(ExceptionUtils.getStackTrace(e));
	}

    }

    public static void removeOutputEvents(LinksChromosome chromosome) {
	File chromosomeDir = FileManager.getChromosomeDir(chromosome);
	new PythonMethods().removeOutputEvents(chromosomeDir);
    }

    public static String getChromosomeConfig(LinksChromosome chromosome) {
	File chromosomeDir = FileManager.getChromosomeDir(chromosome);
	String config = Context.getI().getProp("file.config");
	return (chromosomeDir + "/" + config);
    }
}
