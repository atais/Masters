package p.lodz.playground;

import junit.framework.Assert;

import org.junit.Test;
import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.scenario.ScenarioUtils;

public class ScenarioTest {

    @Test
    public void simpleTest() {
	String defaultConfig = "../scenarios/siouxfalls/config_default.xml";
//	Config config = ConfigUtils.loadConfig(defaultConfig);
//
//	Scenario scenario = ScenarioUtils.loadScenario(config);
//
//	Assert.assertNotNull(scenario);
//	
//	Controler controller = new Controler(scenario);
//	controller.run();
	
	final Controler controler = new Controler(new String[]{defaultConfig});
	controler.setOverwriteFiles(true);
	controler.run();

	System.out.println("noz kurwa");
    }
}
