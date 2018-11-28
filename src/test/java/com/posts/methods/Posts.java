package com.posts.methods;

import com.posts.pojo.pojoreq.PostReqPojo;
import com.posts.pojo.pojoresp.PostRespPojo;
import com.tests.Post;
import database.StatementDB;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import properties.PropertiesCollection;

import java.sql.ResultSet;

public class Posts {


    public Response addAuthor(String id) {

        RequestSpecification request = RestAssured.given();
        PostReqPojo post = new PostReqPojo();
        post.setId(id);
        request.header("Content-Type", "application/json");
        request.body(post, ObjectMapperType.GSON);
        return request.post(PropertiesCollection.BASE_POST_URL);
    }

    public Response addAuthor(String id, String title) {

        RequestSpecification request = RestAssured.given();
        PostReqPojo post = new PostReqPojo();
        post.setId(id);
        post.setTitle(title);
        request.header("Content-Type", "application/json");
        request.body(post, ObjectMapperType.GSON);
        return request.post("/posts");

    }

    public Response addAuthor(String id, String title, String author) {

        RequestSpecification request = RestAssured.given();
        PostReqPojo post = new PostReqPojo();
        post.setId(id);
        post.setTitle(title);
        post.setAuthor(author);
        request.header("Content-Type", "application/json");
        request.body(post, ObjectMapperType.GSON);
        return request.post(PropertiesCollection.BASE_POST_URL);

    }


}
