package p.lodz.ms;

import java.io.IOException;
import java.net.URL;

import org.apache.commons.configuration.ConfigurationException;
import org.junit.Before;
import org.junit.Test;

public class AppTest {

    private URL is;

    @Before
    public void build() throws ConfigurationException {
	ClassLoader classloader = Thread.currentThread()
		.getContextClassLoader();
	is = classloader.getResource("test-config.xml");
    }

    @Test
    public void mainTest() throws IOException {
	App.main(new String[] { is.toString() });
    }
}
