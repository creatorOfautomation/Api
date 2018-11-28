package com.posts.tests;

import POJOData.weatherbycity.Main;
import POJOData.weatherbycity.WeatherForCity;
import com.posts.BaseClass;
import com.posts.methods.Posts;
import com.posts.pojo.pojoreq.PostReqPojo;
import com.posts.pojo.pojoresp.PostRespPojo;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;
import org.json.simple.JSONObject;

public class PostTest extends BaseClass {


   /* @Test
    public void testAddAuthor() {

        RequestSpecification request = RestAssured.given();
        PostReqPojo post = new PostReqPojo();
        post.setId("111");
        post.setAuthor("Teest");
        post.setTitle("Magic");

        WeatherForCity weather = new WeatherForCity();
        Main main = new Main();
        main.setTemp(new Double(123));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("main.temp", "test");
        jsonObject.put("dt", "dsad");
        weather.setDt(213);
        weather.setMain(main);
        request.header("Content-type", "application/json")
                // .body(weather, ObjectMapperType.GSON);
                .body(jsonObject.toJSONString());
        Response response = request.when().post("/posts");
        response.then().log().all();
        response.then().assertThat().statusCode(201);
    }*/


    @Test
    public void addAuthor() {

        Posts posts = new Posts();

        Response response = posts.addAuthor("323", "kjsdfnksjfnksjd");

        response.then().assertThat().statusCode(201);

        PostRespPojo author = response.as(PostRespPojo.class, ObjectMapperType.GSON);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(author.getId()).isEqualTo("323");
        softAssertions.assertThat(author.getTitle()).isEqualTo("kjsdfnksjfnksjd");
        softAssertions.assertAll();


    }


}
