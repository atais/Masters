package p.lodz.ms.genetics;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.genetics.BinaryMutation;
import org.apache.commons.math3.genetics.Chromosome;

import p.lodz.ms.Configuration;
import p.lodz.ms.integration.PythonMethods;

public class LinksMutation extends BinaryMutation {

    @Override
    public Chromosome mutate(Chromosome original)
	    throws MathIllegalArgumentException {
	int attempt = 0;
	int max = Configuration.getInstance().getGaMaxMutationAttempts();
	Chromosome mutated = super.mutate(original);
	boolean isStronglyConnected = new PythonMethods()
		.checkChromosome(mutated);

	while ((attempt < max) && !isStronglyConnected) {
	    mutated = super.mutate(original);
	    isStronglyConnected = new PythonMethods().checkChromosome(mutated);
	    ++attempt;
	}

	// if failed, then just return original
	if (!isStronglyConnected) {
	    mutated = original;
	}
	return mutated;
    }

}
