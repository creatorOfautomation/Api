package com.tests;

import io.restassured.RestAssured;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import properties.Helpers;
import properties.PropertiesCollection;

public class Post {

    //private JSONObject reqParam = new JSONObject();
    private Helpers help = new Helpers();
    String sd = "{";
    String sd1 = "}";

    @BeforeClass
    public void getBaseURL() {
        RestAssured.baseURI = PropertiesCollection.BASE_POST_URL;
    }

    @Test
    public void makePost() {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(help
                .setJsonParam("id", 15)
                .setJsonParam("title", "Dima")
                .setJsonParam("author", "Fedia")
                .putParam()
        ).post("posts").then().log().all();
    }


    @Test
    public void makePost1() {


            RequestSpecification request = RestAssured.given();

            request.body(help
                    .setJsonParam("id", 60)
                    .setJsonParam("title", help.randomPureString(20))
                    .setJsonParam("author", help.randomPureString(15))
                    .putParam());
            Response response = help.post("posts", request);
            response.then().assertThat().statusCode(201);




    }


    public static void main(String[] args) {
        System.out.println(PropertiesCollection.BASE_POST_URL + "post");

    }
}
