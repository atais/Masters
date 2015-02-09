package p.lodz.ms.db;

import java.sql.SQLException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import p.lodz.ms.genetics.LinksChromosome;

public class ChromosomeDao {

    private final static Logger logger = Logger.getLogger(ChromosomeDao.class);

    public static Double readChromosomeScore(LinksChromosome chromosome) {
	double score = Double.NEGATIVE_INFINITY;
	Chromosome chromo;
	try {
	    String id = chromosome.getShortId();
	    chromo = DBConnector.getI().getDao().queryForId(id);
	    score = chromo.getScore();
	} catch (SQLException e) {
	    logger.error(ExceptionUtils.getStackTrace(e));
	} catch (NullPointerException e) {
	    logger.debug("Trying to read not calculated fitness, may be on purpose.");
	}
	return score;
    }

    public static void writeChromosome(LinksChromosome lch, Double fitness) {
	String id = lch.getShortId();
	String uuid = lch.getUuid().toString();
	try {
	    if (DBConnector.getI().getDao().idExists(id)) {
		updateCheck(id, fitness, uuid);
	    } else {
		Chromosome chromo = new Chromosome(id, fitness, uuid);
		DBConnector.getI().getDao().create(chromo);
	    }
	} catch (SQLException e) {
	    logger.error(ExceptionUtils.getStackTrace(e));
	}

    }

    private static void updateCheck(String id, Double fitness, String uuid)
	    throws SQLException {
	Chromosome current = DBConnector.getI().getDao().queryForId(id);
	if (fitness < current.getScore()) {
	    logger.warn("Updating chromosome's score " + id);
	    Chromosome chromo = new Chromosome(id, fitness, uuid);
	    DBConnector.getI().getDao().update(chromo);
	}
    }
}
