package p.lodz.ms;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.math3.genetics.Chromosome;
import org.junit.Assert;
import org.junit.Before;

import p.lodz.ms.genetics.LinksChromosome;
import p.lodz.ms.genetics.LinksElitisticListPopulation;
import p.lodz.ms.integration.PythonMethods;

public class GeneticsModuleTest {
    private Configuration config;
    private LinksChromosome chromosome;

    @Before
    public void build() throws ConfigurationException {
	ClassLoader classloader = Thread.currentThread()
		.getContextClassLoader();
	URL is = classloader.getResource("test-config.xml");

	config = new Configuration(is.toString());

	File network = new File(config.getScenarioNetwork());
	chromosome = new PythonMethods().convertNetworkToBinary(network);
    }

    // @Test
    public void realTest() throws IOException {
	GeneticsModule gm = new GeneticsModule();
	Assert.assertNotNull(gm);
	gm.runGeneticAlgorithm();
    }

    // @Test
    public void initTest() throws IOException {
	GeneticsModule gm = new GeneticsModule();
	Assert.assertNotNull(gm);

	LinksElitisticListPopulation population = gm
		.randomPopulation(chromosome.getLength());
	for (Chromosome chromosome : population) {
	    System.out.println(chromosome);
	}
    }
}
