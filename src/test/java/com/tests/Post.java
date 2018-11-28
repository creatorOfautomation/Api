package com.tests;

import database.StatementDB;
import endpoints.Endpoints;
import io.restassured.RestAssured;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import properties.Helpers;
import properties.PropertiesCollection;

public class Post {

    private Helpers help = new Helpers();
    private String idParam = "id";
    private String titleParam = "title";
    private String authorParam = "author";
    private StatementDB statementDB = new StatementDB();

    @BeforeClass
    public void getBaseURL() {
        RestAssured.baseURI = PropertiesCollection.BASE_POST_URL;
    }

    //json-server --watch db.json --port 3004
    //  @Test
    public void makePost() {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");

        Response response = request.body(help
                .setJsonParam(idParam, 15)
                .setJsonParam(titleParam, help.randomPureString(12))
                .setJsonParam(authorParam, help.randomPureString(18))
                .putParam()
        ).post(Endpoints.POST_AUTHOR);

    }


    @Test
    public void makePost1() {

        String title = help.randomPureString(20);

        RequestSpecification request = RestAssured.given();

        request.body(help
                .setJsonParam("id", 60)
                .setJsonParam("title", title)
                .setJsonParam("author", help.randomPureString(5))
                .putParam());
        Response response = help.post("posts", request);
        response.then().assertThat().statusCode(201);
       // help.softAssertEquals("test", "test1");
       // help.softAssertEquals("test", "test1");
       // help.softAssertEquals("test", "test1");
        help.assertAll();


    }


    public static void main(String[] args) {
        System.out.println(PropertiesCollection.BASE_POST_URL + "post");

    }
}
