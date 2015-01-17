package p.lodz.ms.genetics;

import org.apache.commons.math3.genetics.Chromosome;

public class MATSimThread implements Runnable {

    private Chromosome chromosome;

    public MATSimThread(Chromosome chromosome) {
	this.chromosome = chromosome;
    }

    @Override
    public void run() {
	this.chromosome.getFitness();
    }

}
