package p.lodz.fms.utils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class Zip {

    public static void unzip(String file, String output) throws ZipException {

	ZipFile zipFile = new ZipFile(file);
	zipFile.extractAll(output);

    }
}
