package pl.restautomation.bugs;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class ReadBugsTests {
    static final String BASE_URL = "http://localhost:3000/bugs";

    @Test
    void ReadOneBugWithPathVariableTest() {
        Response response = given()
                .pathParam("id", 1)
                .when()
                .get(BASE_URL + "/{id}")
                .then()
                .extract()
                .response();

        Assertions.assertEquals(HttpStatus.SC_OK, response.getStatusCode());

        JsonPath json = response.jsonPath();
        Assertions.assertEquals("Incorrect response code after PATH to /bugs", json.getString("title"));
        Assertions.assertEquals("open", json.getString("status"));
    }

    @Test
    void ReadOneBugWithoutQueryParamTest() {
        Response response = given()
                .queryParam("status", "closed")
                .when()
                .get(BASE_URL)
                .then()
                .extract()
                .response();

        Assertions.assertEquals(HttpStatus.SC_OK, response.getStatusCode());

        JsonPath json = response.jsonPath();
        Assertions.assertEquals("Et quia deserunt animi reiciendis omnis.", json.getList("title").get(0));

    }
}
