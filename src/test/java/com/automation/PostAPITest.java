// POST request test - created by Srikanth
package com.automation;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import org.testng.annotations.TestInstance;

import java.io.IOException;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class PostAPITest extends BaseTest {

    @Test
    public void createPost_shouldReturn201() {
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
                .body("id", notNullValue());
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
    public void createPost_missingTitle_shouldReturn500() {
        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("body", "learning API Automation");
        requestBody.put("userId", 1);
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

    @DataProvider(name = "userData")
    public Object[][] getUserData() {
        return new Object[][]{
                {"Srikanth", "QA Engineer"},
                {"John", "Developer"},
                {"Sarah", "Manager"}
        };
    }

    @Test(dataProvider = "userData")
    public void createMultipleUsers(String name, String job) {
        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", name);
        requestBody.put("job", job);
        given()
                .spec(reqSpec)
                .body(requestBody)
                .when()
                .post("/users")
                .then()
                .log().all()
                .statusCode(201)
                .body("name", equalTo(name));
    }

    @DataProvider(name = "invalidUserIds")
    public Object[][] negativeData() {
        return new Object[][]{
                {9999},
                {8888},
                {7777},
        };
    }

    @Test(dataProvider = "invalidUserIds")
    public void negativeData_shouldReturn404(int invalidId) {
        given()
                .spec(reqSpec)
                .when()
                .get("/users/" + invalidId)
                .then()
                .statusCode(404);
    }

    @Test
    public void ifElse_shouldReturn200() {
        int statusCode = given()
                .spec(reqSpec)
                .when()
                .get("/users 1/")
                // .get("/unknown/endpoint")
                .then()
                .log().all()
                .extract().statusCode();
        if (statusCode == 200) {
            System.out.println("user exists");
        } else if (statusCode == 404) {
            System.out.println("user not found");
        } else if (statusCode == 401) {
            System.out.println("not authorized");
        } else {
            System.out.println("unexpected status: " + statusCode);
        }
    }
    @Test
    public void runApiCollection_fromExcel() throws IOException {
        Object[][] data = ExcelUtils.readExcelData(
                "src/test/resources/testdata.xlsx", "ApiCollection"
        );

        for (Object[] row : data) {
            String method         = row[0].toString();
            String url            = row[1].toString();
            String payload        = row[2] != null ? row[2].toString() : "";
            int expectedStatus    = Integer.parseInt(row[3].toString());

            if (method.equals("POST")) {
                given().spec(reqSpec).body(payload)
                        .when().post(url)
                        .then().statusCode(expectedStatus);

            } else if (method.equals("GET")) {
                given().spec(reqSpec)
                        .when().get(url)
                        .then().statusCode(expectedStatus);

            } else if (method.equals("PUT")) {
                given().spec(reqSpec).body(payload)
                        .when().put(url)
                        .then().statusCode(expectedStatus);

            } else if (method.equals("DELETE")) {
                given().spec(reqSpec)
                        .when().delete(url)
                        .then().statusCode(expectedStatus);
            }
            System.out.println(method + " " + url + " → " + expectedStatus);
        }
    }
}