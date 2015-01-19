package p.lodz.ms.genetics;

import org.junit.Test;

public class LinksChromosomeTest {

    @Test
    public void representationTest() {

	LinksChromosome ls = new LinksChromosome(
		LinksChromosome.randomBinaryRepresentation(10));
	System.out.println(ls.toString());
    }

}
