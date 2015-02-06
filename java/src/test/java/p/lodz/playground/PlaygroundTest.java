package p.lodz.playground;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

public class PlaygroundTest {

    @Test
    public void playground() {

	List<Double> a = new ArrayList<Double>();
	a.add(5.0);
	a.add(3.0);
	a.add(6.0);
	a.add(2.0);
	a.add(1.0);

	System.out.println(a);
	Collections.sort(a, new Comparator<Double>() {

	    @Override
	    public int compare(Double o1, Double o2) {
		return Double.compare(o2, o1);
	    }
	});
	;
	System.out.println(a);

    }
}
