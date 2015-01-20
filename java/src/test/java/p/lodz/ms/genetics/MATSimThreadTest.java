package p.lodz.ms.genetics;

import java.io.File;
import java.net.URL;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.math3.genetics.Chromosome;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import p.lodz.ms.Configuration;
import p.lodz.ms.integration.PythonMethods;


public class MATSimThreadTest {

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
	chromosome = PythonMethods.getInstance()
		.convertNetworkToBinary(network);

    }

    @Test
    public void matsimTest() {
	MATSimThread t = new MATSimThread(chromosome);
	t.run();
	System.out.println(chromosome.getFitness());
	Assert.assertNotNull(chromosome.getFitness());
    }

}
