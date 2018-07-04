package database;

import org.apache.log4j.Logger;
import properties.PropertiesCollection;

import java.sql.*;
import java.util.Locale;

public class GetConnectionDB {

    private String hostDB = PropertiesCollection.HOST_DB;
    private String portDB = PropertiesCollection.PORT_DB;
    private String driverDB = PropertiesCollection.DRIVER_DB;
    private String userDB = PropertiesCollection.USER_DB;
    private String passwordDB = PropertiesCollection.PASSWORD_DB;
    private String schemeDB = PropertiesCollection.SCHEME_DB;
    private String jdbsURI = "jdbc:oracle:thin";
    private Logger log = Logger.getLogger(GetConnectionDB.class);
    private Connection connection = null;

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
            System.out.println(jdbsURI + ":@" + hostDB + ":" + portDB + ":" + schemeDB + userDB + passwordDB);
        }catch (SQLException e) {
            log.error("Connection failed!");
            log.error(e.getMessage());
            return null;
        }
        if (connection != null) {
            log.debug("Connection is set up");
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

    public static void main(String[] args) throws Exception {
        GetConnectionDB getConnectionDB = new GetConnectionDB();
        GetConnectionDB getConnectionDB1 = new GetConnectionDB();
        Statement stmt = getConnectionDB1.openConnection().createStatement();
        ResultSet rs = stmt.executeQuery("select * from postingdata where fileid=1");
        rs.next() ;
        System.out.println(getConnectionDB.jdbsURI + ":@" + getConnectionDB.hostDB + ":" + getConnectionDB.portDB + ":" + getConnectionDB.schemeDB + getConnectionDB.userDB + getConnectionDB.passwordDB);
    }
}
