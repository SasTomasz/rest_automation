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

class CreateBugsTests {
    public static final String BASE_URL = "http://localhost:3000/bugs";
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
    void createNewBug() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(bug.toString())
                .when()
                .post(BASE_URL)
                .then()
                .extract()
                .response();

        System.out.println(bug.toString());
        Assertions.assertEquals(HttpStatus.SC_CREATED, response.getStatusCode());

        JsonPath json = response.jsonPath();
        Assertions.assertEquals(title, json.getString("title"));
        Assertions.assertEquals(description, json.getString("description"));
    }
}
