package properties;

import io.restassured.RestAssured;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeTest;

public class BaseClass {

    private Logger log = Logger.getLogger(BaseClass.class);

    @BeforeTest
    public void configuredBaseURL() {
        log.debug("Try to set base URL: " + PropertiesCollection.BASE_URL);
        RestAssured.baseURI = PropertiesCollection.BASE_URL;
    }

}
