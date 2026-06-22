// POST request test - created by Srikanth
package com.automation;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class PostAPITest extends BaseTest {

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
    @Test
    public void updatePost_shouldReturn200() {
        String requestBody = "{\"title\": \"Updated Post\", \"body\": \"Updated content\", \"userId\": 1}";

        given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .put("/posts/1")
                .then()
                .statusCode(200)
                .body("title", equalTo("Updated Post"));
    }

    @Test
    public void deletePost_shouldReturn200() {
        given()
                .when()
                .delete("/posts/1")
                .then()
                .statusCode(200);
    }
}