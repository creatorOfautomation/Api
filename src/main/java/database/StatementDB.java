package database;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StatementDB {

    private Statement statement = null;
    private ResultSet resultSet = null;
    private DBConnection connectionDB = new DBConnection();
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

    public void putSql(String query) {

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
           statement.executeUpdate(query);
           // resultSet.next();
        } catch (SQLException e) {
            log.error("ERROR! The result set was not set. The stackTrace is :" + e.getMessage());
        }



    }

    public String getStringFromResultSet(ResultSet resultSet, String column) {
        try {
            log.info("Try to get string from resultSet" + resultSet + " the columnName is: " + column);

            return resultSet.getString(column);
        } catch (SQLException e) {
            log.error("ERROR! Can't get string from result set: " + e.getSQLState() + " " + e.getMessage());
            e.printStackTrace();

            return null;
        } catch (NullPointerException e) {
            log.error("ERROR! Got NullPointerException! " + e.getMessage());
            return "";
        }
    }


    //private Connection connection = new DBConnection().getConnection();
    DBConnection dbConnection = new DBConnection();

    /*public ResultSet getResultSet1(String query) {

        try {
            log.debug("Try to get statement ");
            statement = dbConnection.getConnection().createStatement();
        } catch (SQLException e) {
            log.error("ERROR! Can't get statement");
            log.error("The stacktrace: " + e.getMessage());

        }finally {
            try {
                dbConnection.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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

    public String getStringFromResultSet1(ResultSet resultSet, String column) {
        try {
            log.info("Try to get string from resultSet" + resultSet + " the columnName is: " + column);

            return resultSet.getString(column);
        } catch (SQLException e) {
            log.error("ERROR! Can't get string from result set: " + e.getSQLState() + " " + e.getMessage());


            return null;
        } catch (NullPointerException e) {
            log.error("ERROR! Got NullPointerException! " + e.getMessage());
            return "";
        }
    }*/

    public static void main(String[] args) throws SQLException {

        StatementDB statementDB = new StatementDB();
        String column1 = "somevalue";
        String sql = "select * from postingdata where FILEID=3 order by somevalue desc";
        ResultSet resultSet = statementDB.getResultSet(sql);
        String stringFromResultSet = statementDB.getStringFromResultSet(resultSet, column1);
        System.out.println(stringFromResultSet);
    }
}
