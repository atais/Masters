package p.lodz.ms.genetics;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.ChromosomePair;
import org.apache.commons.math3.genetics.GeneticAlgorithm;
import org.apache.commons.math3.genetics.Population;
import org.apache.commons.math3.genetics.TournamentSelection;

public class LinkTournamentSelection extends TournamentSelection {

    public LinkTournamentSelection(int arity) {
	super(arity);
    }

    @Override
    public ChromosomePair select(final Population population)
	    throws MathIllegalArgumentException {
	return new ChromosomePair(
		tournament((LinksElitisticListPopulation) population),
		tournament((LinksElitisticListPopulation) population));
    }

    private Chromosome tournament(final LinksElitisticListPopulation population)
	    throws MathIllegalArgumentException {
	if (population.getPopulationSize() < this.getArity()) {
	    throw new MathIllegalArgumentException(
		    LocalizedFormats.TOO_LARGE_TOURNAMENT_ARITY,
		    this.getArity(), population.getPopulationSize());
	}
	// auxiliary population
	LinksElitisticListPopulation tournamentPopulation = new LinksElitisticListPopulation(
		this.getArity(), population.getElitismRate());

	// create a copy of the chromosome list
	List<Chromosome> chromosomes = new ArrayList<Chromosome>(
		population.getChromosomes());
	for (int i = 0; i < this.getArity(); i++) {
	    // select a random individual and add it to the tournament
	    int rind = GeneticAlgorithm.getRandomGenerator().nextInt(
		    chromosomes.size());
	    tournamentPopulation.addChromosome(chromosomes.get(rind));
	    // do not select it again
	    chromosomes.remove(rind);
	}
	// the winner takes it all
	return tournamentPopulation.getFittestChromosome();
    }

}
