package p.lodz.ms.manage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import p.lodz.ms.Context;
import p.lodz.ms.genetics.LinksChromosome;
import p.lodz.ms.integration.PythonMethods;

public class FileManager {

    private final static Logger logger = Logger.getLogger(FileManager.class);

    public static File getChromosomeDir(LinksChromosome chromosome) {
	String output = Context.getI().getConfig().getProjectOutputDir();
	String projName = Context.getI().getConfig().getProjectName() + "/";
	String iteration = Context.getI().getGaCurrentIterationPath();
	String uuid = chromosome.getUuid().toString();
	String dirName = output + projName + iteration + uuid;
	return new File(dirName);
    }

    public static File getFitnessFile(LinksChromosome chromosome) {
	String fileName = Context.fitnessFilename;
	String file = getChromosomeDir(chromosome) + "/" + fileName;
	return new File(file);
    }

    /**
     * The initial fitness file in project dir
     * 
     * @param chromosome
     */
    public static void createInitialFitness(LinksChromosome chromosome) {
	File fitnessSource = new File(getChromosomeDir(chromosome) + "/"
		+ Context.fitnessFilename);
	File fitnessDest = new File(Context.getI().getConfig().getProjectDir()
		+ Context.fitnessInitialFilename);

	try {
	    FileUtils.copyFile(fitnessSource, fitnessDest);
	} catch (IOException e) {
	    logger.error(ExceptionUtils.getStackTrace(e));
	}
    }

    public static void createChromosomeText(LinksChromosome chromosome) {
	File chromosomeDir = FileManager.getChromosomeDir(chromosome);

	try {
	    FileWriter writer = new FileWriter(chromosomeDir + "/"
		    + Context.chromosomeFileName);
	    writer.write(chromosome.toString());
	    writer.close();
	} catch (IOException e) {
	    logger.error(ExceptionUtils.getStackTrace(e));
	}
    }

    public static void convertChromosomeToNetwork(LinksChromosome chromosome) {
	File chromosomeDir = FileManager.getChromosomeDir(chromosome);
	File destNetwork = new File(chromosomeDir + "/"
		+ Context.networkFileName);
	new PythonMethods().convertBinaryToNetwork(chromosome, destNetwork);
    }

    public static void prepareChromosomeConfig(LinksChromosome chromosome) {
	File chromosomeDir = FileManager.getChromosomeDir(chromosome);

	File baseConfig = new File(Context.getI().getConfig()
		.getScenarioConfig());
	File chromosomeConfig = new File(chromosomeDir + "/"
		+ Context.configFileName);
	try {
	    FileUtils.copyFile(baseConfig, chromosomeConfig);
	    File network = new File(chromosomeDir + "/"
		    + Context.networkFileName);
	    File facilities = new File(Context.getI().getConfig()
		    .getScenarioFacilities());
	    File population = new File(Context.getI().getConfig()
		    .getScenarioPopulation());
	    Integer iterations = Context.getI().getConfig()
		    .getScenarioIterations();
	    new PythonMethods().customizeConfig(chromosomeConfig, facilities,
		    network, population, chromosomeDir, iterations);
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
	String config = Context.configFileName;
	return (chromosomeDir + "/" + config);
    }

}
