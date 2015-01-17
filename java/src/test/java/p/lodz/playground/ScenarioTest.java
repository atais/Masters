package p.lodz.playground;

import org.junit.Test;
import org.matsim.core.controler.Controler;

public class ScenarioTest {

    @Test
    public void simpleTest() {
	String defaultConfig = "../scenarios/siouxfalls/config_default.xml";
	final Controler controler = new Controler(
		new String[] { defaultConfig });
	controler.run();
    }
}
