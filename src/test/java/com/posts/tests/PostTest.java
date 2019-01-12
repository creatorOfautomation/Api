package com.posts.tests;


import com.posts.BaseClass;
import com.posts.methods.Posts;
import com.posts.pojo.posts.resp.PostRespPojo;
import endpoints.Endpoints;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;
import properties.Helpers;

public class PostTest extends BaseClass {

    private Helpers helpers = new Helpers();
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
        String id = helpers.getUnicId();
        String author = helpers.randomPureString(10);
        String title = helpers.randomPureString(10);

        posts.setId(id);
        posts.setAuthor(author);
        posts.setTitle(title);

        Response response = posts.addAuthor();
        response.then().assertThat().statusCode(201);
        PostRespPojo resp = response.as(PostRespPojo.class, ObjectMapperType.GSON);
        posts.assertAuthor(resp.getAuthor(), author)
                .assertId(resp.getId(), id)
                .assertTitle(resp.getTitle(), title)
                .assertAll();
    }


    @Test
    public void addAuthor1() {

        Posts posts = new Posts();

        posts.setAuthor("Author");
        posts.setId("2432");
        posts.setTitle("Title");

        RequestSpecification request = RestAssured.given();
        request.body(posts).post(Endpoints.POSTS).then().log().all();
    }

}
