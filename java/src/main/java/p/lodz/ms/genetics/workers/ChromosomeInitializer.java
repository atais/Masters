package p.lodz.ms.genetics.workers;

import java.util.List;

import org.apache.commons.math3.genetics.Chromosome;
import org.apache.log4j.Logger;

import p.lodz.ms.genetics.LinksChromosome;
import p.lodz.ms.integration.PythonMethods;

public class ChromosomeInitializer implements Runnable {
    private final static Logger logger = Logger
	    .getLogger(ChromosomeInitializer.class);
    private List<Chromosome> popList;

    public ChromosomeInitializer(List<Chromosome> popList) {
	this.popList = popList;
    }

    @Override
    public void run() {
	logger.debug("Creating random network");
	LinksChromosome chromosome = new PythonMethods()
		.createRandomChromosome();
	popList.add(chromosome);
    }
}
