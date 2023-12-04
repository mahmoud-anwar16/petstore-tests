package com.qpros;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class UserAPITest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/api/v3";
    }

    @Test(dataProvider = "userDataProvider", priority = 1)
    public void testCreateUser(String id, String username, String firstName, String lastName, String email, String password, String phone, String userStatus) {
        String jsonPayload = String.format("{\"id\": %s,\"username\":\"%s\",\"firstName\":\"%s\",\"lastName\":\"%s\",\"email\":\"%s\",\"password\":\"%s\",\"phone\":\"%s\",\"userStatus\":%s}",
                id, username, firstName, lastName, email, password, phone, userStatus);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(jsonPayload)
                .post("/user");

        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test(dependsOnMethods = "testCreateUser", priority = 2)
    public void testGetUserByUsername() {
        String username = "tom-martin";
        Response response = given()
                .get("/user/" + username);

        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test(dependsOnMethods = "testCreateUser", priority = 3)
    public void testGetUserByNonExistingUsername() {
        String nonExistingUsername = "non-existent-user";
        Response nonExistingResponse = given()
                .get("/user/" + nonExistingUsername);

        assertEquals(nonExistingResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @Test(dependsOnMethods = "testCreateUser", dataProvider = "userDataProvider", priority = 4)
    public void testUpdateUser(String id, String username, String firstName, String lastName, String email, String password, String phone, String userStatus) {
        String updatedEmail = "updated_email@example.com";

        String jsonPayload = String.format("{\"id\": %s,\"username\":\"%s\",\"firstName\":\"%s\",\"lastName\":\"%s\",\"email\":\"%s\",\"password\":\"%s\",\"phone\":\"%s\",\"userStatus\":%s}",
                id, username, firstName, lastName, updatedEmail, password, phone, userStatus);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(jsonPayload)
                .put("/user/" + username);

        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test(dependsOnMethods = "testCreateUser", dataProvider = "deleteUserDataProvider", priority = 5)
    public void testDeleteUser(String username) {
        Response response = given()
                .delete("/user/" + username);

        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
    }

    @DataProvider(name = "userDataProvider")
    public Object[][] userDataProvider() {
        return new Object[][]{
                {"10", "john-james", "John", "James", "john@email.com", "12345", "12345", "1"},
                {"20", "tom-martin", "Tom", "Martin", "tom@email.com", "12345", "12345", "1"},
        };
    }

    @DataProvider(name = "deleteUserDataProvider")
    public Object[][] deleteUserDataProvider() {
        return new Object[][]{
                {"john-james"},
                {"tom-martin"}
        };
    }
}
