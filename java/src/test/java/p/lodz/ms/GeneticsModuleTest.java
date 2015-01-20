package p.lodz.ms;


import java.net.URL;

import org.apache.commons.configuration.ConfigurationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class GeneticsModuleTest {
    private Configuration config;

    @Before
    public void build() throws ConfigurationException {
	ClassLoader classloader = Thread.currentThread()
		.getContextClassLoader();
	URL is = classloader.getResource("test-config.xml");

	config = Configuration.getInstance();
	config.readXMLFile(is.toString());
    }

    @Test
    public void matsimTest() {
	GeneticsModule gm = new GeneticsModule();
	Assert.assertNotNull(gm);
    }
}
