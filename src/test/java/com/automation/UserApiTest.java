// GET request tests - JSONPlaceholder API
package com.automation;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class UserApiTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void getUser_shouldReturn200() {
        given()
            .when()
                .get("/users/1")
            .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Leanne Graham"));
    }

    @Test
    public void getUser_shouldHaveEmail() {
        given()
            .when()
                .get("/users/1")
            .then()
                .statusCode(200)
                .body("email", notNullValue());
    }

    @Test
    public void getInvalidUser_shouldReturn404() {
        given()
            .when()
                .get("/users/9999")
            .then()
                .statusCode(404);
    }
}