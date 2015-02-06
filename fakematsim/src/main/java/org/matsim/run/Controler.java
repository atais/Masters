package org.matsim.run;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// A fake matsim which simply unzips some package and randomizes the results
public class Controler {

    public static void main(String[] args) throws IOException,
	    URISyntaxException, InterruptedException {
	// read config
	List<String> lines = Files.readAllLines(Paths.get(args[0]),
		StandardCharsets.UTF_8);

	// find output folder
	String output = null;
	for (String line : lines) {
	    if (line.contains("outputDirectory")) {
		Pattern pattern = Pattern.compile(".*value=\"(.*)\".*");
		Matcher m = pattern.matcher(line.trim());
		m.matches();
		output = Paths.get(m.group(1)).toString();
	    }
	}

	// decompress there
	ClassLoader classloader = Thread.currentThread()
		.getContextClassLoader();
	Path is = Paths.get(classloader.getResource("example.zip").toURI());
	Zip.unzip(is.toString(), output);

	// change the time to some random value
	// ITERS/it.100/100.tripdurations.txt
	File duration = new File(output + "/ITERS/it.100/100.tripdurations.txt");
	System.out.println(duration.exists());
	// List<String> dLines = Files.readAllLines(duration.toPath(),
	// StandardCharsets.UTF_8);
	//
	// // average trip duration: 1305.0772916418975 seconds = 00:21:45
	// String line = dLines.get(dLines.size() - 1);
	// String pattern = ".+duration: (.+) seconds.+";
	// Double random = new Random().nextDouble();
	// Double result = 1250 + (random * (1450 - 1250));
	// line.replaceAll(pattern, result.toString());
	//
	// Files.write(duration.toPath(), dLines, Charset.defaultCharset(),
	// StandardOpenOption.TRUNCATE_EXISTING);
    }
}
