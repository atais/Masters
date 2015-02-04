package p.lodz.ms.genetics.workers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.math3.genetics.Chromosome;
import org.junit.Before;
import org.junit.Test;

public class ChromosomeInitializerTest {

    private int populationSize;
    private List<Chromosome> popList;

    @Before
    public void prepare() throws ConfigurationException {
	populationSize = 4;
	popList = Collections.synchronizedList(new ArrayList<Chromosome>());
    }

    @Test
    public void speedTest() {

	for (int i = 1; i <= 4; i++) {
	    long startTime = System.nanoTime();
	    runTest(i);
	    long endTime = System.nanoTime();
	    long duration = (endTime - startTime) / 1000000;
	    System.out.println(duration + " ms");
	}

    }

    private void runTest(int threads) {
	ExecutorService executor = Executors.newFixedThreadPool(threads);
	for (int i = 0; i < populationSize; i++) {
	    Runnable worker = new ChromosomeInitializer(popList);
	    executor.execute(worker);
	}
	executor.shutdown();
	while (!executor.isTerminated()) {
	}
    }
}
