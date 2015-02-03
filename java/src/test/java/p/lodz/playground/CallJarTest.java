package p.lodz.playground;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class CallJarTest {

    @Test
    public void simpleTest() throws IOException, InterruptedException {
	List<String> command = new ArrayList<String>();
	command.add("/usr/bin/java");
	command.add("-Xmx2g");
	command.add("-cp");
	command.add("../matsim/matsim-r31927mod.jar");
	command.add("org.matsim.run.Controler");
	command.add("../scenarios/perfromance/performance_AStarLandmarks.xml");

	ProcessBuilder builder = new ProcessBuilder(command);
	Process process = builder.start();
	InputStream is = process.getInputStream();
	InputStreamReader isr = new InputStreamReader(is);
	BufferedReader br = new BufferedReader(isr);
	String line;
	while ((line = br.readLine()) != null) {
	    System.err.println(line);
	}
	process.waitFor();

    }
}
