package org.matsim.run;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;

public class ControlerTest {

    private URI is;

    @Before
    public void prepare() throws IOException, URISyntaxException {
	ClassLoader classloader = Thread.currentThread()
		.getContextClassLoader();
	URL isU = classloader.getResource("test-config.xml");
	is = isU.toURI();
    }

    // @Test
    public void simpleTest() throws IOException, URISyntaxException,
	    InterruptedException {
	Path path = Paths.get(is);
	Controler.main(new String[] { path.toString() });
    }
}
