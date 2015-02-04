package p.lodz.ms;

import org.junit.Assert;
import org.junit.Test;

public class ContextTest {

    @Test
    public void simpleTest() {
	Context inst = Context.getI();
	Assert.assertNotNull(inst);
    }
}
