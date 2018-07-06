package database;

import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StatementDB {

    private Statement statement = null;
    private ResultSet resultSet = null;
    private GetConnectionDB connectionDB = new GetConnectionDB();
    private Logger log = Logger.getLogger(StatementDB.class);

    public ResultSet getResultSet(String query) {

        try {
            log.debug("Try to get statement ");
            statement = connectionDB.openConnection().createStatement();
        } catch (SQLException e) {
            log.error("ERROR! Can't get statement");
            log.error("The stacktrace: " + e.getMessage());
        }
        if (statement != null) {
            log.debug("The statement was set successfully!");
        }
        try {
            log.debug("Try to resolve resultSet");
            resultSet = statement.executeQuery(query);
            resultSet.next();
        } catch (SQLException e) {
            log.error("ERROR! The result set was not set. The stackTrace is :" + e.getMessage());
        }
        if (resultSet != null) {
            log.debug("ResultSet was set successfully");
        }
        return resultSet;
    }
}
