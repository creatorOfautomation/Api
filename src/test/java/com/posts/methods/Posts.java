package com.posts.methods;

import endpoints.Endpoints;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.SoftAssertions;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import properties.Helpers;
import properties.PropertiesCollection;


public class Posts {


    private String author;
    private String id;
    private String title;
    private RequestSpecification request = RestAssured.given();
    private JSONObject param = new JSONObject();
    private String authorParam = "author";
    private String idParam = "id";
    private String titleParam = "title";
    private Helpers hepl = new Helpers();
    private SoftAssertions assertions = new SoftAssertions();

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
        hepl.putParam(authorParam,author,param);
    }

    public String getId() {
        return id;

    }

    public void setId(String id) {
        this.id = id;
        hepl.putParam(idParam,id,param);

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {

        this.title = title;
        hepl.putParam(titleParam,title,param);
    }

    public Response addAuthor() {

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(hepl.toJSON(param));
        return hepl.post(Endpoints.POSTS, request);
    }


    public Posts assertId(String act, String exp) {
        hepl.softAssertEquals(act, exp, assertions);
        return this;
    }

    public Posts assertAuthor(String act, String exp) {
        hepl.softAssertEquals(act, exp, assertions);
        return this;
    }

    public Posts assertTitle(String act, String exp) {
        hepl.softAssertEquals(act, exp, assertions);
        return this;
    }

    public void assertAll() {

        assertions.assertAll();
    }


    public static void main(String[] args) {

        /*JSONArray city = new JSONArray();
        JSONObject object = new JSONObject();


        object.put("temperature", "25.5");
        object.put("Wind", "34");
        city.add(0,object);

        System.out.println(object.toJSONString());*/

       /* JsonObject json = new JsonObject();
        json.addProperty("user_id", "23");
        JsonArray contacts = new JsonArray();
        JsonArray workplaces = new JsonArray();
        JsonArray specialities = new JsonArray();
        JsonArray regalia = new JsonArray();

        json.add("contacts", contacts);
        json.add("workplaces", workplaces);
        json.add("specialities", specialities);
        json.add("regalia", regalia);
        System.out.println(json.toString());*/

        JSONObject jo = new JSONObject();
        jo.put("firstName", "John");
        jo.put("lastName", "Doe");
        JSONObject jo1 = new JSONObject();
        jo1.put("firstName", "John");
        jo1.put("lastName", "Doe");

        JSONArray ja = new JSONArray();
        ja.add(jo);

        JSONObject mainObj = new JSONObject();
        mainObj.put("employees", jo);
        System.out.println(mainObj.toJSONString());
    }

}
