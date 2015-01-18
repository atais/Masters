package p.lodz.ms.genetics;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.BinaryChromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;
import org.apache.log4j.Logger;

import p.lodz.ms.Configuration;
import p.lodz.ms.StaticContainer;

public class LinksChromosome extends BinaryChromosome {

    private final static Logger logger = Logger
	    .getLogger(LinksChromosome.class);

    private final UUID uuid;
    private File dir;

    public LinksChromosome(Integer[] representation)
	    throws InvalidRepresentationException {
	this(Arrays.asList(representation));
    }

    public LinksChromosome(List<Integer> representation)
	    throws InvalidRepresentationException {
	super(representation);
	this.uuid = UUID.randomUUID();
    }

    public static LinksChromosome parseString(String representation) {
	representation = representation.replace("[", "");
	representation = representation.replace("]", "");
	String[] numberStrs = representation.split(",");
	Integer[] numbers = new Integer[numberStrs.length];
	for (int i = 0; i < numberStrs.length; i++) {
	    numbers[i] = Integer.parseInt(numberStrs[i]);
	}
	return new LinksChromosome(numbers);
    }

    @Override
    public double fitness() {
	double fitness = 0;
	String file = getTripsDurationFile();
	try {
	    List<String> lines = Files.readAllLines(Paths.get(file),
		    Charset.defaultCharset());
	    fitness = new Double(lines.iterator().next());
	} catch (IOException e) {
	    logger.error(e.getCause());
	}
	return fitness;
    }

    private String getTripsDurationFile() {
	String fileName = StaticContainer.tripDurationsFileName;
	String file = dir.getAbsolutePath() + fileName;
	return file;
    }

    public File getDir() {
	if (dir == null) {
	    String output = Configuration.getInstance().getProjectOutputDir();
	    String projName = Configuration.getInstance().getProjectName()
		    + "/";
	    String iteration = StaticContainer.getInstance()
		    .getGaCurrentIterationPath();
	    String chromosome = this.getUuid().toString() + "/";
	    String dirName = output + projName + iteration + chromosome;
	    this.dir = new File(dirName);
	}
	return dir;
    }

    @Override
    public String toString() {
	return this.getRepresentation().toString();
    }

    @Override
    public AbstractListChromosome<Integer> newFixedLengthChromosome(
	    List<Integer> chromosomeRepresentation) {
	return new LinksChromosome(chromosomeRepresentation);
    }

    public UUID getUuid() {
	return uuid;
    }

}
