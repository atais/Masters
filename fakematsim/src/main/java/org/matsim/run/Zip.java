package org.matsim.run;

import java.io.IOException;

import javax.naming.Context;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.LogOutputStream;
import org.apache.commons.exec.PumpStreamHandler;

public class Zip {

    public static void unzip(String source, String output) throws IOException,
	    InterruptedException {
	CommandLine cmdLine = new CommandLine("unzip");
	cmdLine.addArgument("-o");
	cmdLine.addArgument("-q");
	cmdLine.addArgument(source);
	cmdLine.addArgument("-d");
	cmdLine.addArgument(output);

	DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
	Executor executor = new DefaultExecutor();
	executor.setStreamHandler(new PumpStreamHandler(new LogOutputStream() {
	    @Override
	    protected void processLine(String line, int logLevel) {
		System.err.println(line);
	    }
	}));
	executor.setWatchdog(new ExecuteWatchdog(
		ExecuteWatchdog.INFINITE_TIMEOUT));
	try {
	    executor.execute(cmdLine, resultHandler);
	    resultHandler.waitFor();
	} catch (Exception e) {
	    System.err.println(e);
	}

    }
}
