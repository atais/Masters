package p.lodz.ms.db;

import java.net.URL;
import java.sql.SQLException;

import org.apache.commons.configuration.ConfigurationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import p.lodz.ms.ConfigurationModule;

public class DBConnectorTest {

    @Before
    public void setup() throws ConfigurationException {
	ClassLoader classloader = Thread.currentThread()
		.getContextClassLoader();
	URL is = classloader.getResource("test-config.xml");

	new ConfigurationModule(is.toString());
    }

    @Test
    public void startTest() {
	DBConnector db = DBConnector.getI();
	Assert.assertNotNull(db);
    }

    @Test
    public void dbTest() throws SQLException {
	String id = "0000001";
	Chromosome chro = new Chromosome(id);

	DBConnector.getI().getDao().create(chro);
	Assert.assertEquals(chro, DBConnector.getI().getDao().queryForId(id));

	chro.setScore(1000.50);
	DBConnector.getI().getDao().update(chro);
	Assert.assertEquals(chro, DBConnector.getI().getDao().queryForId(id));

    }
}
