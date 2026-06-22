// Written by Srikanth - API Automation Framework
package com.automation;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class PostAPITest {
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
public void createPost_shouldReturn201(){
        String requestBody = "{\"title\": \"My First Post\", \"body\": \"Learning API automation\", \"userId\": 1}";

        given()
                .header("Content-type","application/Json")
                .body(requestBody)
                .when()
                .post("posts")
                .then()
                .statusCode(201)
                .body("title", equalTo("My First Post"))
                .body("id",notNullValue());

    }
}