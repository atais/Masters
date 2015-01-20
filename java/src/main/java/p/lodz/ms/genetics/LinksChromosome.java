package p.lodz.ms.genetics;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.BinaryChromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;
import org.apache.log4j.Logger;

import p.lodz.ms.manage.FileManager;

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
	representation = representation.replace(" ", "");
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
	File file = FileManager.getFitnessFile(this);
	try {
	    List<String> lines = Files
		    .readAllLines(Paths.get(file.getAbsolutePath()),
			    Charset.defaultCharset());
	    fitness = new Double(lines.iterator().next());
	} catch (IOException e) {
	    logger.error(ExceptionUtils.getStackTrace(e));
	    fitness = Double.MAX_VALUE;
	}
	return fitness;
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
