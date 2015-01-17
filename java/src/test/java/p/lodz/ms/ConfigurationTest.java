package p.lodz.ms;

import java.net.URL;

import org.apache.commons.configuration.ConfigurationException;
import org.junit.Assert;
import org.junit.Test;

public class ConfigurationTest {

    @Test
    public void sampleConfigTest() throws ConfigurationException {
	ClassLoader classloader = Thread.currentThread()
		.getContextClassLoader();
	URL is = classloader.getResource("test-config.xml");

	Configuration config = Configuration.getInstance().readXMLFile(
		is.toString());

	Assert.assertEquals("siouxfalls", config.getProjectName());
    }
}
