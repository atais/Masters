package p.lodz.ms.genetics;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.ChromosomePair;
import org.apache.commons.math3.genetics.OnePointCrossover;

import p.lodz.ms.Configuration;
import p.lodz.ms.integration.PythonMethods;

public class LinksOnePointCrossover extends OnePointCrossover<Integer> {

    @Override
    public ChromosomePair crossover(Chromosome first, Chromosome second)
	    throws DimensionMismatchException, MathIllegalArgumentException {
	int attempt = 0;
	int max = Configuration.getInstance().getGaMaxCrossoverAttempts();

	ChromosomePair crossovered = super.crossover(first, second);
	boolean isStronglyConnected1 = new PythonMethods()
		.checkChromosome(crossovered.getFirst());
	boolean isStronglyConnected2 = new PythonMethods()
		.checkChromosome(crossovered.getSecond());

	boolean isStronglyConnected = isStronglyConnected1
		&& isStronglyConnected2;

	while ((attempt < max) && !isStronglyConnected) {
	    crossovered = super.crossover(first, second);
	    isStronglyConnected1 = new PythonMethods()
		    .checkChromosome(crossovered.getFirst());
	    isStronglyConnected2 = new PythonMethods()
		    .checkChromosome(crossovered.getSecond());
	    isStronglyConnected = isStronglyConnected1 && isStronglyConnected2;
	    ++attempt;
	}

	// if failed, then just return original
	if (!isStronglyConnected) {
	    crossovered = new ChromosomePair(first, second);
	}
	return crossovered;
    }
}
