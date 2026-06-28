// GET request tests - JSONPlaceholder API
package com.automation;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.HashSet;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class UserApiTest extends BaseTest {
    @BeforeClass
    public void setupUserTests() {
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
    @Test
    public void getAllUsers_s200shouldReturn(){
        given ()
                .when()
                .get("/users")
                .then()
                .statusCode(200);
    }
    @Test
    public void getPost_shouldReturn200() {
        given()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("title", notNullValue());
    }
    @Test
    public void validateUser_shouldReturn200(){
        given ()
                .when()
                .get("/users/2")
                        .then()
                .log().all()
                        .statusCode(200)
                        .body("id", equalTo(2));
    }
    @Test
    public void validateCommeNTS_shouldReturn200(){
        given ()
                .queryParam("postId", 1)
                .when()
                .get("/comments/")
                .then()
                .log().all()
                .statusCode(200)
                .body("postId[0]", equalTo(1) )
                .body("email[0]", notNullValue());
    }
    @Test
    public void createNewUser_shouldRetrun201(){
        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "Srikanth");
        given()
                .spec(reqSpec)
                .body(requestBody)
                .when().post("/users")
                .then()
                .log().all()
                .statusCode(201)
                .body("id", notNullValue());
    }
    @Test
    public void negativeTestCase_shouLldReturn201(){
        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "Srikanth");
        requestBody.put("job", "QA Engineer");
        given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/users")
                .then()
                .statusCode(201);
    }
    @Test
    public void createUser_shouldReturn201(){
    HashMap<String,Object> data = new HashMap<>();
    data.put ("UserName","Srikanth");
    data.put("position","manager");

        String userId = given()
                .spec(reqSpec)
                .body(data)
                .when()
                .post("/users")
                .then()
                .log().all()
                .statusCode(201)
                .extract().path("id");
    System.out.println("Created user ID: " + userId);
    }
    @Test
    public void extractingId_shouldRetrun200(){
        String firstName = given()
                .spec(reqSpec)
                .when()
                .get("/users/2")
                .then()
                .log().all()
                .statusCode(200)
                .extract().path("data.first_name");

        System.out.println("The first name: " + firstName);
    }
    @Test
    public void updateUserDetails_shouldReturn200(){
        HashMap<String,Object> NewData =  new HashMap<>();
        NewData.put("name", "Srikanth");
        NewData.put("job", "Senior QA Engineer");
        String updatedName = given()
                .spec(reqSpec)
                .body(NewData)
                .when()
                .put("/users/2")
                .then()
                .log().all()
                .statusCode(200)
                .extract().path("name");
        System.out.println("Updated name: " + updatedName);
    }
    @Test
    public void deleteUser_shouldReturn204(){
        given()
                .spec((reqSpec))
                .when()
                .delete("users/2")
                .then()
                .statusCode(204);
    }
    @DataProvider(name = "userData")
    public Object [][] getUserData(){
        return new Object[][]{
                {1},
                {2},
                {3},
        };
    }
    @Test(dataProvider = "userData")
    public void getUser_shouldReturn200(int userId){
        given()
                .when()
                .get("/users/" + userId)
                .then()
                .log().all()
                .statusCode(200);
    }
    @Test //testing loops
    public void loopCase_shouldReturn200(){
        for (int i = 1; i <= 10;i ++){
            given()
                    .when()
                    .get("users/" + i)
                    .then()
                    .log().all()
                    .statusCode(200);
        }
    }
    @Test //testing loops
    public void zipcodeCheck_shouldReturn200() {
        for (int i = 1; i <= 5; i++) {
            String zipcode = given()
                    .when()
                    .get("/users/" + i)
                    .then()
                    .statusCode(200)
                    .extract().path("address.zipcode");

            System.out.println("User " + i + " zipcode: " + zipcode);
        }
    }
}