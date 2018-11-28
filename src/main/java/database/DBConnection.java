package database;

import org.apache.log4j.Logger;
import properties.PropertiesCollection;
import java.sql.*;
import java.util.Locale;

public class DBConnection {

    private String hostDB = PropertiesCollection.HOST_DB;
    private String portDB = PropertiesCollection.PORT_DB;
    private String driverDB = PropertiesCollection.DRIVER_DB;
    private String userDB = PropertiesCollection.USER_DB;
    private String passwordDB = PropertiesCollection.PASSWORD_DB;
    private String schemeDB = PropertiesCollection.SCHEME_DB;
    private String jdbsURI = "jdbc:oracle:thin";
    private Logger log = Logger.getLogger(DBConnection.class);
    private Connection connection;

    public DBConnection() {
        try {
            log.debug("!!Try to set JDBC Driver");
            Class.forName(driverDB);
            Locale.setDefault(Locale.ENGLISH);
            log.debug("JDBS was set");
        } catch (ClassNotFoundException e) {
            log.error("Class not found exception occurred. Driver was not set " + e.getMessage());

        }
        try {
            log.debug("Try to get connection to Oracle DB whit jdbsURL: " + jdbsURI + " host: " + hostDB + " port: " + portDB +
                    " DBName: " + schemeDB);
            connection = DriverManager.getConnection(jdbsURI + ":@" + hostDB + ":" + portDB + ":" + schemeDB, userDB, passwordDB);
            log.debug("The jdbc is: " + jdbsURI + ":@" + hostDB + ":" + portDB + ":" + schemeDB + userDB + passwordDB);
        }catch (SQLException e) {
            log.error("Connection failed!");
            log.error(e.getMessage());

        }catch (NullPointerException e) {
            log.error("ERROR! Can't set connection: " + e.getMessage() + e.getLocalizedMessage());
        }
        if (connection != null) {
            log.debug("Connection is set up successfully " + connection);
        }

    }

    public Connection getConnection() {
        return connection;
    }

    public Connection openConnection() {
        try {
            log.debug("Try to set JDBC Driver");
            Class.forName(driverDB);
            Locale.setDefault(Locale.ENGLISH);
            log.debug("JDBS was set");
        } catch (ClassNotFoundException e) {
            log.error("Class not found exception occurred. Driver was not set " + e.getMessage());
            return null;
        }
        try {
            log.debug("Try to get connection to Oracle DB whit jdbsURL: " + jdbsURI + " host: " + hostDB + " port: " + portDB +
                    " DBName: " + schemeDB);
            connection = DriverManager.getConnection(jdbsURI + ":@" + hostDB + ":" + portDB + ":" + schemeDB, userDB, passwordDB);
            log.debug("The jdbc is: " + jdbsURI + ":@" + hostDB + ":" + portDB + ":" + schemeDB + userDB + passwordDB);
        }catch (SQLException e) {
            log.error("Connection set up failed!");
            log.error(e.getMessage());
            return null;
        }
        if (connection != null) {
            log.debug("Connection is set up successfully");
        }
        return connection;
    }

    public void closeConnection() {
        try {
            log.debug("Try to close connection");
            connection.close();
            log.debug("Connection was closed");
        } catch (SQLException e) {
            log.error("Connection was not closed");
            log.error(e.getMessage());

        }
    }
}
