package p.lodz.ms.manage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import p.lodz.ms.Configuration;
import p.lodz.ms.StaticContainer;
import p.lodz.ms.genetics.LinksChromosome;
import p.lodz.ms.integration.PythonMethods;

public class FileManager {

    private final static Logger logger = Logger.getLogger(FileManager.class);

    public static File getChromosomeDir(LinksChromosome chromosome) {
	String output = Configuration.getInstance().getProjectOutputDir();
	String projName = Configuration.getInstance().getProjectName() + "/";
	String iteration = StaticContainer.getInstance()
		.getGaCurrentIterationPath();
	String uuid = chromosome.getUuid().toString();
	String dirName = output + projName + iteration + uuid;
	return new File(dirName);
    }

    public static File getFitnessFile(LinksChromosome chromosome) {
	String fileName = StaticContainer.fitnessFilename;
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
		+ StaticContainer.fitnessFilename);
	File fitnessDest = new File(Configuration.getInstance().getProjectDir()
		+ StaticContainer.fitnessInitialFilename);

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
		    + StaticContainer.chromosomeFileName);
	    writer.write(chromosome.toString());
	    writer.close();
	} catch (IOException e) {
	    logger.error(ExceptionUtils.getStackTrace(e));
	}
    }

    public static void convertChromosomeToNetwork(LinksChromosome chromosome) {
	File chromosomeDir = FileManager.getChromosomeDir(chromosome);
	File destNetwork = new File(chromosomeDir + "/"
		+ StaticContainer.networkFileName);
	new PythonMethods().convertBinaryToNetwork(chromosome, destNetwork);
    }

    public static void prepareChromosomeConfig(LinksChromosome chromosome) {
	File chromosomeDir = FileManager.getChromosomeDir(chromosome);

	File baseConfig = new File(Configuration.getInstance()
		.getScenarioConfig());
	File chromosomeConfig = new File(chromosomeDir + "/"
		+ StaticContainer.configFileName);
	try {
	    FileUtils.copyFile(baseConfig, chromosomeConfig);
	    File network = new File(chromosomeDir + "/"
		    + StaticContainer.networkFileName);
	    File facilities = new File(Configuration.getInstance()
		    .getScenarioFacilities());
	    File population = new File(Configuration.getInstance()
		    .getScenarioPopulation());
	    Integer iterations = Configuration.getInstance()
		    .getScenarioIterations();
	    new PythonMethods().customizeConfig(chromosomeConfig, facilities,
		    network, population, chromosomeDir, iterations);
	} catch (IOException e) {
	    logger.error(ExceptionUtils.getStackTrace(e));
	}

    }

    public static String getChromosomeConfig(LinksChromosome chromosome) {
	File chromosomeDir = FileManager.getChromosomeDir(chromosome);
	String config = StaticContainer.configFileName;
	return (chromosomeDir + "/" + config);
    }

}
