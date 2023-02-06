package pl.restautomation;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

class ReadEmployeesTest {
    @Test
    void readAllEmployeesTest() {
        Response response = given()
                .when()
                .get("http://localhost:3000/employees");

        Assertions.assertEquals(200, response.getStatusCode());
        JsonPath json = response.jsonPath();
        List<String> firstNames = json.getList("firstName");
        Assertions.assertTrue(firstNames.size() > 0);

//        response.prettyPrint(); // Print without headers
        response.prettyPeek();  // Print with headers

    }

    @Test
    void readOneEmployeeTest() {
        Response response = given()
                .when()
                .get("http://localhost:3000/employees/1");

        Assertions.assertEquals(200, response.getStatusCode());

        JsonPath json = response.jsonPath();

        Assertions.assertEquals("Bartek", json.getString("firstName"));
        Assertions.assertEquals("Czarny", json.getString("lastName"));
        Assertions.assertEquals("bczarny", json.getString("username"));
        Assertions.assertEquals("bczarny@testerprogramuje.pl", json.getString("email"));
    }

    @Test
    void readOneUserWithPathVariableTest() {
        Response response = given()
                .pathParam("id", "1")
                .when()
                .get("http://localhost:3000/employees/{id}");
        Assertions.assertEquals(200, response.getStatusCode());

        JsonPath json = response.jsonPath();

        Assertions.assertEquals("Bartek", json.getString("firstName"));
        Assertions.assertEquals("Czarny", json.getString("lastName"));
        Assertions.assertEquals("bczarny", json.getString("username"));
        Assertions.assertEquals("bczarny@testerprogramuje.pl", json.getString("email"));

    }

    @Test
    void readEmployeesWithQueryParamsTest() {
        Response response = given()
               .queryParam("firstName", "Bartek")
               .queryParam("lastName", "Czarny")
               .when()
               .get("http://localhost:3000/employees");

        Assertions.assertEquals(200, response.getStatusCode());

        JsonPath json = response.jsonPath();

        Assertions.assertEquals("Bartek", json.getList("firstName").get(0));
        Assertions.assertEquals("Czarny", json.getList("lastName").get(0));
        Assertions.assertEquals("bczarny", json.getList("username").get(0));
        Assertions.assertEquals("bczarny@testerprogramuje.pl", json.getList("email").get(0));
    }
}
