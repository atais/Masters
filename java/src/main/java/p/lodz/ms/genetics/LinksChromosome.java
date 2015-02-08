package p.lodz.ms.genetics;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.BinaryChromosome;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;

import p.lodz.ms.db.ChromosomeDao;

public class LinksChromosome extends BinaryChromosome {

    private final UUID uuid;

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
	return ChromosomeDao.readChromosomeScore(this);
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

    @Override
    protected boolean isSame(Chromosome another) {
	return super.isSame(another);
    }

    public String getShortId() {
	String id = this.toString();
	id = id.replace("[", "");
	id = id.replace("]", "");
	id = id.replaceAll(",", "");
	id = id.replaceAll(" ", "");
	return id;
    }

}
