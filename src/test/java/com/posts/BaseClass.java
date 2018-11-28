package com.posts;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import properties.PropertiesCollection;

public class BaseClass {

    @BeforeClass
    public void setBaseUri() {

        RestAssured.baseURI = PropertiesCollection.BASE_POST_URL;
    }
}
