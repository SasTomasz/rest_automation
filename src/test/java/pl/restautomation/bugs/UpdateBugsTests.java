package pl.restautomation.bugs;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class UpdateBugsTests {
    static final String BASE_URL = "http://localhost:3000/bugs";
    static Faker faker;
    static JSONObject bug;
    String title;
    String description;

    @BeforeAll
    static void beforeAll() {
        faker = new Faker();
        bug = new JSONObject();
    }

    @BeforeEach
    void beforeEach() {
        title = faker.lorem().sentence();
        description = faker.lorem().paragraph(20);

        bug.put("title", title);
        bug.put("description", description);
        bug.put("employeeId", "1");
        bug.put("status", "open");
    }


    @Test
    void UpdateBugWithPutTest() {
        Response response = given()
                .contentType(ContentType.JSON)
                .pathParam("id", 1)
                .body(bug.toString())
                .when()
                .put(BASE_URL + "/{id}")
                .then()
                .extract()
                .response();

        Assertions.assertEquals(HttpStatus.SC_OK, response.getStatusCode());

        JsonPath json = response.jsonPath();
        Assertions.assertEquals(bug.get("title").toString(), json.get("title"));
        Assertions.assertEquals(bug.get("description").toString(), json.get("description"));
    }

    @Test
    void UpdateBugWithPatch() {
        JSONObject bug2 = new JSONObject();
        bug2.put("title", title);
        Response response = given()
                .contentType(ContentType.JSON)
                .pathParam("id", 1)
                .body(bug2.toString())
                .when()
                .patch(BASE_URL + "/{id}")
                .then()
                .extract()
                .response();

        Assertions.assertEquals(HttpStatus.SC_OK, response.getStatusCode());

        JsonPath json = response.jsonPath();
        Assertions.assertEquals(bug2.get("title").toString(), json.get("title"));
    }
}
