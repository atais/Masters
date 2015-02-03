package p.lodz.ms.integration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import p.lodz.ms.Configuration;

public class PythonAdapter {

    private final static Logger logger = Logger.getLogger(PythonAdapter.class);

    protected String defaultCall(String... commands) {
	String pyReturn = null;

	List<String> command = new ArrayList<String>();
	command.add(Configuration.getInstance().getProjectPythonPath());
	command.add(Configuration.getInstance().getProjectPythonMain());
	command.addAll(Arrays.asList(commands));

	ProcessBuilder builder = new ProcessBuilder(command);
	Process process;
	try {
	    process = builder.start();
	    InputStream is = process.getInputStream();
	    InputStreamReader isr = new InputStreamReader(is);
	    BufferedReader br = new BufferedReader(isr);
	    String line;
	    while ((line = br.readLine()) != null) {
		if (line.contains("return:")) {
		    pyReturn = line;
		} else {
		    logger.info(line);
		}
	    }
	    process.waitFor();
	} catch (IOException e) {
	    logger.error(ExceptionUtils.getStackTrace(e));
	} catch (InterruptedException e) {
	    logger.error(ExceptionUtils.getStackTrace(e));
	}

	if (pyReturn != null && !pyReturn.isEmpty()) {
	    try {
		pyReturn = pyReturn.substring(7);
	    } catch (StringIndexOutOfBoundsException e) {
		logger.error(ExceptionUtils.getStackTrace(e));
	    }
	}
	return pyReturn;
    }

}
