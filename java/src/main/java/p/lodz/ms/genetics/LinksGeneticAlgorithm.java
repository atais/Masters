package p.lodz.ms.genetics;

import org.apache.commons.math3.exception.OutOfRangeException;
import org.apache.commons.math3.genetics.CrossoverPolicy;
import org.apache.commons.math3.genetics.GeneticAlgorithm;
import org.apache.commons.math3.genetics.MutationPolicy;
import org.apache.commons.math3.genetics.Population;
import org.apache.commons.math3.genetics.SelectionPolicy;
import org.apache.commons.math3.genetics.StoppingCondition;

import p.lodz.ms.StaticContainer;

public class LinksGeneticAlgorithm extends GeneticAlgorithm {

    public LinksGeneticAlgorithm(CrossoverPolicy crossoverPolicy,
	    double crossoverRate, MutationPolicy mutationPolicy,
	    double mutationRate, SelectionPolicy selectionPolicy)
	    throws OutOfRangeException {
	super(crossoverPolicy, crossoverRate, mutationPolicy, mutationRate,
		selectionPolicy);
    }

    @Override
    public Population evolve(Population initial, StoppingCondition condition) {
	Population value = super.evolve(initial, condition);
	// we need to increment iteration value in static container
	// otherwise the chromosme does not know where is it's path
	StaticContainer.getInstance().setGaCurrentIteration(getGenerationsEvolved());
	return value;
    }
    
    

}
