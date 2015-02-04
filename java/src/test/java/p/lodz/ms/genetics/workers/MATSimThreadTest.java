package p.lodz.ms.genetics.workers;

import java.io.File;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.configuration.ConfigurationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import p.lodz.ms.Configuration;
import p.lodz.ms.genetics.LinksChromosome;
import p.lodz.ms.integration.PythonMethods;

public class MATSimThreadTest {

    private Configuration config;
    private File network;

    @Before
    public void build() throws ConfigurationException {
	ClassLoader classloader = Thread.currentThread()
		.getContextClassLoader();
	URL is = classloader.getResource("test-config.xml");

	config = Configuration.getInstance();
	config.readXMLFile(is.toString());

	network = new File(config.getScenarioNetwork());

    }

    // @Test
    public void matsimTest() {
	LinksChromosome chromosome = new PythonMethods()
		.convertNetworkToBinary(network);
	MATSimThread t = new MATSimThread(chromosome);
	t.run();
	Assert.assertNotNull(chromosome.getFitness());
    }

    @Test
    public void speedTest() {

	for (int i = 1; i <= 4; i++) {
	    long startTime = System.nanoTime();
	    runTest(i);
	    long endTime = System.nanoTime();
	    long duration = (endTime - startTime) / 1000000;
	    System.err.println("The test took " + duration + " ms");
	}

    }

    private void runTest(int threads) {
	ExecutorService executor = Executors.newFixedThreadPool(threads);
	for (int i = 0; i < 12; i++) {
	    LinksChromosome chromosome = new PythonMethods()
		    .convertNetworkToBinary(network);
	    Runnable worker = new MATSimThread(chromosome);
	    executor.execute(worker);
	}
	executor.shutdown();
	while (!executor.isTerminated()) {
	}
    }

}
