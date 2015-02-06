package org.matsim.run;

import java.io.IOException;
import java.net.URISyntaxException;

import net.lingala.zip4j.exception.ZipException;
import p.lodz.fms.MainOperation;

// A fake matsim which simply unzips some package and randomizes the results
public class Controler {

    public static void main(String[] args) {
	MainOperation mo = new MainOperation();

	String output;
	try {
	    output = mo.readConfig(args[0]);
	    mo.fakeDecompress(output);
	    mo.randomizeTrips(output);
	} catch (IOException e) {
	    e.printStackTrace();
	} catch (URISyntaxException e) {
	    e.printStackTrace();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	} catch (ZipException e) {
	    e.printStackTrace();
	}
    }

}
