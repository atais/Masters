package p.lodz.ms;

import java.net.URL;

public class App {

    public static void main(String[] args) {
	// test only
	ClassLoader classloader = Thread.currentThread()
		.getContextClassLoader();
	URL is = classloader.getResource("config.xml");
	args = new String[] { is.toString() };
	// test only

	try {
	    new ConfigurationModule(args[0]);
	    new GeneticsModule();
	} catch (Exception e) {
	    System.exit(1);
	}
	System.exit(0);
    }

}
