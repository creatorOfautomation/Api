package properties;

import java.io.IOException;
import java.util.Properties;

public class PropertiesProvider {

    private Properties properties;

    public PropertiesProvider() {
        properties = new Properties();
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            System.exit(1);
        }
    }

    public String getBaseURL() {
        return properties.getProperty("base.url");
    }

    public String getPathForExcelData() {
        return properties.getProperty("excel.data.path");
    }

    public String getContentResponseType() {
        return properties.getProperty("response.content.type");
    }

    public String getHostDataBase() {
        return properties.getProperty("host.databade");
    }

    public String getPortDataBase() {
        return properties.getProperty("port.database");

    }

    public String getUserDataBase() {
        return properties.getProperty("user.database");
    }

    public String getPasswordDataBase() {
        return properties.getProperty("password.database");
    }

    public String getDriveDataBaseOracle() {
        return properties.getProperty("driver.name");
    }

    public String getSchemeDataBase() {
        return properties.getProperty("scheme.database");
    }

}
