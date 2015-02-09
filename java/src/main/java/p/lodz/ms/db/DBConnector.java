package p.lodz.ms.db;

import java.sql.SQLException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import p.lodz.ms.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DBConnector {

    private final static Logger logger = Logger.getLogger(DBConnector.class);

    private final String dbPrefix = "jdbc:sqlite:";
    private final String dbName = "/chromosome.sqlite";
    private final String dbUser = "user";
    private final String dbPassword = "user";

    private String databaseUrl;
    private JdbcConnectionSource connectionSource;

    private Dao<Chromosome, String> chromosomeDao;
    
    private static class Holder {
	static final DBConnector INSTANCE = new DBConnector();
    }

    public static DBConnector getI() {
	return Holder.INSTANCE;
    }

    private DBConnector() {
	try {
	    databaseUrl = dbPrefix + Context.getI().getConfig().getProjectDir()
		    + dbName;
	    connectionSource = new JdbcConnectionSource(databaseUrl, dbUser,
		    dbPassword);

	    chromosomeDao = DaoManager.createDao(connectionSource,
		    Chromosome.class);

	    // if you need to create the table
	    TableUtils.createTableIfNotExists(connectionSource,
		    Chromosome.class);
	} catch (SQLException e) {
	    logger.error(ExceptionUtils.getStackTrace(e));
	}

    }

    public synchronized Dao<Chromosome, String> getDao() {
	return chromosomeDao;
    }

    public void closeConnection() {
	this.connectionSource.closeQuietly();
    }
}
