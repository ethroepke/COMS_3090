package myapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(classes = {coms309.Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)


public class ThienSystemTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void getAllUserProfilesTest() {
        // Send GET request to retrieve all user profiles
        Response response = RestAssured.given()
                .when()
                .get("/api/userprofile/all");

        // Check status code
        assertEquals(200, response.getStatusCode(), "Expected HTTP status 200");

        // Ensure response body is not empty
        assertNotNull(response.getBody().asString(), "Response body should not be null or empty");

        // Optionally check content type
        assertEquals("application/json", response.getContentType(), "Expected content type to be application/json");
    }


    @Test
    public void getUserProfileByIdTest() {
        // Assuming a user with ID 1 exists
        long userId = 9;

        // Send GET request to retrieve user profile by ID
        Response response = RestAssured.given()
                .when()
                .get("/api/userprofile/{id}", userId);

        // Check status code
        assertEquals(200, response.getStatusCode(), "Expected HTTP status 200");

        // Ensure response body is not empty
        assertNotNull(response.getBody().asString(), "Response body should not be null or empty");

        // Optionally check if the profile contains user ID in response
        assertEquals(userId, response.jsonPath().getLong("userId"), "User ID should match");
    }
    @Test
    public void loginTest() {
        // Prepare login credentials including email
        String loginUserJson = "{\n" +
                "  \"username\": \"johndoe\",\n" +
                "  \"password\": \"Encodedpassword1!\",\n" +
                "  \"email\": \"johndoe@example.com\"\n" +
                "}";

        // Send POST request to login
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(loginUserJson)
                .when()
                .post("/api/userprofile/login");

        // Check for successful login
        assertEquals(200, response.getStatusCode(), "Expected HTTP status 200 (OK)");

        // Optionally check response message for confirmation
        String expectedMessage = "Login successful";  // Adjust as needed based on your actual response
        assertEquals(expectedMessage, response.getBody().asString(), "Login message should match");
    }

    @Test
    public void loginInvalidCredentialsTest() {
        // Prepare invalid login credentials including email
        String invalidLoginUserJson = "{\n" +
                "  \"username\": \"johndoe\",\n" +
                "  \"password\": \"wrongpassword\",\n" +
                "  \"email\": \"johndoe@example.com\"\n" +
                "}";

        // Send POST request with invalid credentials
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(invalidLoginUserJson)
                .when()
                .post("/api/userprofile/login");

        // Check for failed login (assuming 400 for invalid credentials)
        assertEquals(400, response.getStatusCode(), "Expected HTTP status 400 (Bad Request) for invalid credentials");

        // Optionally check response message for error
        String expectedErrorMessage = "Login failed";  // Updated based on the actual response
        assertEquals(expectedErrorMessage, response.getBody().asString(), "Error message should match for invalid credentials");
    }

    @Test
    public void getAllProjectsTest() {
        // Send GET request to retrieve all projects
        Response response = RestAssured.given()
                .when()
                .get("/api/project/allproject");

        // Check if the status code is 200 OK
        assertEquals(200, response.getStatusCode(), "Expected HTTP status 200");

        // Ensure response body is not empty (i.e., there are projects)
        assertEquals(true, response.getBody().asString().length() > 0, "Response body should not be empty");
    }

    @Test
    public void getAllSalariesForUserTest() {
        Long userId = 9L;  // User ID for which all salary details are being requested
        Response response = RestAssured.given()
                .when()
                .get("/api/salary/all/" + userId);

        // Verifying if the response status code is 200 OK
        assertEquals(200, response.getStatusCode(), "Expected HTTP status 200");

        // Verifying that the response body is not empty
        assertNotNull(response.getBody().asString(), "Response body should not be empty");

        // Verify the salary details in the response body for userId 9
        assertEquals(1, response.jsonPath().getInt("[0].salaryId"), "Salary ID should be 1");
        assertEquals("johndoe", response.jsonPath().getString("[0].username"), "Username should be johndoe");
    }

    @Test
    public void getSalaryByUsernameTest() {
        String username = "johndoe";  // Replace with a valid username
        Response response = RestAssured.given()
                .when()
                .get("/api/salary/username/" + username);

        assertEquals(200, response.getStatusCode(), "Expected HTTP status 200");
        assertNotNull(response.getBody().asString(), "Response body should not be empty");
    }


}
