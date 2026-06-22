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
}