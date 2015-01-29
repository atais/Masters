package p.lodz.ms.integration;

import java.io.File;
import java.net.URL;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.math3.genetics.Chromosome;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import p.lodz.ms.Configuration;

public class PythonMethodsTest {

    private Chromosome chromosome;
    private Configuration config;

    @Before
    public void build() throws ConfigurationException {
	ClassLoader classloader = Thread.currentThread()
		.getContextClassLoader();
	URL is = classloader.getResource("test-config.xml");

	config = Configuration.getInstance();
	config.readXMLFile(is.toString());

	File network = new File(config.getScenarioNetwork());
	chromosome = new PythonMethods().convertNetworkToBinary(network);

    }

    @Test
    public void testCheckSpeed() {
	long startTime = System.nanoTime();
	boolean result = new PythonMethods().checkChromosome(chromosome);
	long endTime = System.nanoTime();

	long duration = (endTime - startTime) / 1000000;
	System.err.println(duration);
	Assert.assertTrue(result);
    }

}
