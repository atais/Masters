package p.lodz.fms;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.lingala.zip4j.exception.ZipException;
import p.lodz.fms.utils.StringUtils;
import p.lodz.fms.utils.Zip;

public class MainOperation {

    public String readConfig(String config) throws IOException {
	// read config
	List<String> lines = Files.readAllLines(Paths.get(config),
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
	return output;
    }

    public void fakeDecompress(String output) throws URISyntaxException,
	    IOException, InterruptedException, ZipException {
	// decompress there
	String path = MainOperation.class.getProtectionDomain().getCodeSource()
		.getLocation().toURI().getPath();
	path = path.substring(0, path.lastIndexOf("/"));
	String is = path + "/example.zip";
	Zip.unzip(is, output);
    }

    public void randomizeTrips(String output) throws IOException {
	// change the time to some random value
	// ITERS/it.100/100.tripdurations.txt
	File duration = new File(output + "/ITERS/it.100/100.tripdurations.txt");
	List<String> dLines = Files.readAllLines(duration.toPath(),
		StandardCharsets.UTF_8);

	// average trip duration: 1305.0772916418975 seconds = 00:21:45
	String line = dLines.get(dLines.size() - 1);
	String pattern = ".*duration: (.*) seconds.*";
	Double random = new Random().nextDouble();
	double start = 1250.0772916418975;
	double end = 1450.0772916418975;
	Double result = start + (random * (end - start));

	line = StringUtils.replaceGroup(pattern, line, 1, result.toString());
	dLines.remove(dLines.size() - 1);
	dLines.add(line);

	Files.write(duration.toPath(), dLines, Charset.defaultCharset(),
		StandardOpenOption.TRUNCATE_EXISTING);
    }

}
