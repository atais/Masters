package p.lodz.ms.genetics;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.BinaryChromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;

import p.lodz.ms.Configuration;
import p.lodz.ms.StaticContainer;

public class LinksChromosome extends BinaryChromosome {

    private UUID uuid;

    public LinksChromosome(Integer[] representation)
	    throws InvalidRepresentationException {
	super(representation);
	this.setUuid(UUID.randomUUID());
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
	    e.printStackTrace();
	}
	return fitness;
    }

    private String getTripsDurationFile() {
	String output = Configuration.getInstance().getProjectOutputDir();
	String projName = Configuration.getInstance().getProjectName() + "/";
	String iteration = StaticContainer.getInstance()
		.getGaCurrentIterationPath();
	String chromosome = this.getUuid().toString() + "/";
	String fileName = StaticContainer.tripDurationsFileName;

	String file = output + projName + iteration + chromosome + fileName;
	return file;
    }

    public LinksChromosome(List<Integer> representation)
	    throws InvalidRepresentationException {
	super(representation);
    }

    @Override
    public AbstractListChromosome<Integer> newFixedLengthChromosome(
	    List<Integer> chromosomeRepresentation) {
	return new LinksChromosome(chromosomeRepresentation);
    }

    public UUID getUuid() {
	return uuid;
    }

    public void setUuid(UUID uuid) {
	this.uuid = uuid;
    }
}
