// POST request test - created by Srikanth
package com.automation;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class PostAPITest extends BaseTest {

    @Test
    public void createPost_shouldReturn201(){
        String requestBody = "{\"title\": \"My First Post\", \"body\": \"Learning API automation\", \"userId\": 1}";
        given()
                .spec(reqSpec)
                .body(requestBody)
                .log().all()        // ← prints the REQUEST
                .when()
                .post("/posts")
                .then()
                .log().all()        // ← prints the RESPONSE
                .statusCode(201)
                .body("title", equalTo("My First Post"))
                .body("id",notNullValue());
    }
    @Test
    public void updatePost_shouldReturn200() {

        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("title", "Updated Post");
        requestBody.put("body", "Updated content");
        requestBody.put("userId", 1);

        given()
                .spec(reqSpec)
                .body(requestBody)
                .when()
                .put("/posts/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("title", equalTo("Updated Post"));
    }
    @Test
    public void deletePost_shouldReturn200() {
        given()
                .spec(reqSpec)
                .when()
                .delete("/posts/1")
                .then()
                .statusCode(204);
    }

    @Test
    public void createAndVerifyPost() {

        // Step 1 — build request body using HashMap
        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("title", "Chained Test Post");
        requestBody.put("body", "Learning chained API calls");
        requestBody.put("userId", 1);

        // Step 2 — POST and extract the id
        String postId = given()
                .spec(reqSpec)
                .body(requestBody)
                .when()
                .post("/posts")
                .then()
                .log().all()
                .statusCode(201)
                .extract().path("id");


        // Step 3 — print the id we got back
        System.out.println("Created post with ID: " + postId);

        // Step 4 — GET using that id and verify
        given()
                .spec(reqSpec)          // ← add this
                .when()
                .get("/users/2")        // ← use existing ReqRes user
                .then()
                .statusCode(200)
                .log().all()
                .body("data.id", equalTo(2));
    }

    // Negative Test cases

    @Test
    public void invalidApiKey_shouldReturn403() {
        HashMap<String, Object> body = new HashMap<>();
        body.put("name", "test");

        given()
                .header("Content-Type", "application/json")
                .header("x-api-key", "wrong-key-123")
                .body(body)
                .when()
                .post("/users")
                .then()
                .statusCode(403);
    }
    @Test
    public void createPost_missingTitle_shouldReturn500(){
        HashMap<String,Object> requestBody = new HashMap<>();
        requestBody.put("body","learning API Automation");
        requestBody.put("userId",1);
        // title is missing deliberately

        given()
                .spec(reqSpec)
                .body(requestBody)
                .when()
                .post("/posts")
                .then()
                .statusCode(201);
    }

    @Test
    public void invalidEndpoint_shouldReturn404() {
        given()
                .spec(reqSpec)
                .when()
                .get("/nonexistent/endpoint")
                .then()
                .statusCode(404);
    }
}