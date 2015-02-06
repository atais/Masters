package org.matsim.run;

import java.io.IOException;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class Zip {

    public static void unzip(String source, String output) throws IOException,
	    InterruptedException {
	try {
	    ZipFile zipFile = new ZipFile(source);
	    zipFile.extractAll(output);
	} catch (ZipException e) {
	    e.printStackTrace();
	}

    }
}
