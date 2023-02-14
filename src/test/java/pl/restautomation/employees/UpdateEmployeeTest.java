package pl.restautomation.employees;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class UpdateEmployeeTest {
    private static final String BASE_URL = "http://localhost:3000/employees";
    private static Faker faker;
    private String randomFirstName;
    private String randomEmail;

    @BeforeAll
    static void beforeAll() {
        faker = new Faker();
    }

    @BeforeEach
    void beforeEach() {
        randomFirstName = faker.name().firstName();
        randomEmail = faker.internet().emailAddress();
    }

    @Test
    void updateEmployeeByPutTest() {

        JSONObject company = new JSONObject();
        company.put("companyName", "Akademia QA");
        company.put("taxNumber", "531-1593-430");
        company.put("companyPhone", "731-111-111");

        JSONObject address = new JSONObject();
        address.put("street", "Ul. Sezamkowa");
        address.put("suite", "8");
        address.put("city", "Wrocław");
        address.put("zipcode", "12-123");

        JSONObject employee = new JSONObject();
        employee.put("firstName", randomFirstName);
        employee.put("lastName", "Czarny");
        employee.put("username", "bczarny");
        employee.put("email", "bczarny@testerprogramuje.pl");
        employee.put("phone", "731-111-111");
        employee.put("website", randomEmail);
        employee.put("role", "qa");
        employee.put("type", "b2b");
        employee.put("address", address);
        employee.put("company", company);


        Response response = given()
                .contentType(ContentType.JSON)
                .pathParam("id", 1)
                .body(employee.toString())
                .when()
                .put(BASE_URL + "/{id}")
                .then()
                .extract()
                .response();

        Assertions.assertEquals(200, response.statusCode());

        JsonPath json = response.jsonPath();

        Assertions.assertEquals(randomFirstName, json.getString("firstName"));
        Assertions.assertEquals(randomEmail, json.getString("website"));
    }

    @Test
    void updateEmployeeByPatchTest() {

        JSONObject employee = new JSONObject();
        employee.put("firstName", randomFirstName);

        Response response = given()
                .contentType(ContentType.JSON)
                .pathParam("id", 1)
                .body(employee.toString())
                .when()
                .patch(BASE_URL + "/{id}")
                .then()
                .extract()
                .response();

        Assertions.assertEquals(200, response.statusCode());

        JsonPath json = response.jsonPath();

        Assertions.assertEquals(randomFirstName, json.getString("firstName"));

    }
}
