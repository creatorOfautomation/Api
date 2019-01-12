package database;

import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StatementDB {

    private Statement statement = null;
    private ResultSet resultSet = null;
    private Logger log = Logger.getLogger(StatementDB.class);

    private ResultSet getResultSet(DBConnection connection, String query) {

        try {
            log.debug("Try to get statement ");
            statement = connection.getConnection().createStatement();
        } catch (SQLException e) {
            log.error("ERROR! Can't get statement");
            log.error("The stacktrace: " + e.getMessage());
        }
        if (statement != null) {
            log.debug("The statement was set successfully!");
        }
        if (statement != null) {
            try {
                log.debug("Try to resolve resultSet");
                resultSet = statement.executeQuery(query);
                resultSet.next();
            } catch (SQLException e) {
                log.error("ERROR! The result set was not set. The stackTrace is :" + e.getMessage());
            }
        }
        if (resultSet != null) {
            log.debug("ResultSet was set successfully");
        }
        return resultSet;
    }

    private void closeStatement() {
        try {
            log.debug("Try to close statement");
            statement.close();
            if (statement.isClosed())
                log.debug("Statement was closed successfully");
        } catch (SQLException e) {
            log.error("ERROR occured while closing statement ");
            log.error(e.getMessage());
        }

    }

    private void closeResultSet(ResultSet resultSet) {

        try {
            log.debug("Try to close resultSet");
            resultSet.close();
            if (resultSet.isClosed())
                log.debug("ResultSet was closed successfully");
        } catch (SQLException e) {
            log.error("ERROR occured while closing resultSet ");
            log.error(e.getMessage());
        }
    }

    public void putSql(DBConnection connection, String query) {

        try {
            log.debug("Try to get statement ");
            statement = connection.getConnection().createStatement();
        } catch (SQLException e) {
            log.error("ERROR! Can't get statement");
            log.error("The stacktrace: " + e.getMessage());
        }
        if (statement != null) {
            log.debug("The statement was set successfully!");
        }
        if (statement != null) {
            try {
                log.debug("Try to resolve resultSet");
                statement.executeUpdate(query);
            } catch (SQLException e) {
                log.error("ERROR! The result set was not set. The stackTrace is :" + e.getMessage());
            }
        }

    }

    public String getStringFromResultSet(String query, String column) {
        DBConnection connection = new DBConnection();
        ResultSet resultSetForQuery = getResultSet(connection, query);
        if (resultSetForQuery != null) {
            try {
                log.info("Try to get string from DB. Column name: " + "'" + column + "'");
                String string = resultSetForQuery.getString(column);
                closeResultSet(resultSetForQuery);
                closeStatement();
                connection.closeConnection();
                return string;
            } catch (SQLException e) {
                log.error("ERROR! Can't get string from result set: " + e.getSQLState() + " " + e.getMessage());
                return null;
            } catch (NullPointerException e) {
                log.error("ERROR! Got NullPointerException! " + e.getMessage());
                return "";
            }
        } else return null;
    }

    public static void main(String[] args) {

        StatementDB statementDB = new StatementDB();
        String column1 = "somevalue";
        String sql = "select * from postingdata where FILEID=4 order by somevalue desc";
        String stringFromResultSet = statementDB.getStringFromResultSet(sql, column1);
        System.out.println(stringFromResultSet);
    }
}
