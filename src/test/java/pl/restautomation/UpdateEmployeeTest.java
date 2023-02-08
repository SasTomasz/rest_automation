package pl.restautomation;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class UpdateEmployeeTest {
    private static final String BASE_URL = "http://localhost:3000/employees";

    String body1 = "{\n" +
            "      \"firstName\": \"Tomasz\",\n" +  // Bartek na Tomasz
            "      \"lastName\": \"Czarny\",\n" +
            "      \"username\": \"bczarny\",\n" +
            "      \"email\": \"bczarny@testerprogramuje.pl\",\n" +
            "      \"phone\": \"731-111-111\",\n" +
            "      \"website\": \"testerprogramuje.com\",\n" +   // pl na com
            "      \"role\": \"qa\",\n" +
            "      \"type\": \"b2b\",\n" +
            "      \"address\": {\n" +
            "        \"street\": \"Ul. Sezamkowa\",\n" +
            "        \"suite\": \"8\",\n" +
            "        \"city\": \"Wrocław\",\n" +
            "        \"zipcode\": \"12-123\"\n" +
            "      },\n" +
            "      \"company\": {\n" +
            "        \"companyName\": \"Akademia QA\",\n" +
            "        \"taxNumber\": \"531-1593-430\",\n" +
            "        \"companyPhone\": \"731-111-111\"\n" +
            "      }\n" +
            "    }";

    String body2 = "{\n" +
            "      \"firstName\": \"Agnieszka\"\n" +
            "    }";


    @Test
    void updateEmployeeByPutTest() {
        Response response = given()
                .contentType(ContentType.JSON)
                .pathParam("id", 1)
                .body(body1)
                .when()
                .put(BASE_URL + "/{id}")
                .then()
                .extract()
                .response();

        Assertions.assertEquals(200, response.statusCode());

        JsonPath json = response.jsonPath();

        Assertions.assertEquals("Tomasz", json.getString("firstName"));
        Assertions.assertEquals("testerprogramuje.com", json.getString("website"));
    }

    @Test
    void updateEmployeeByPatchTest() {
        Response response = given()
                .contentType(ContentType.JSON)
                .pathParam("id", 1)
                .body(body2)
                .when()
                .patch(BASE_URL + "/{id}")
                .then()
                .extract()
                .response();

        Assertions.assertEquals(200, response.statusCode());

        JsonPath json = response.jsonPath();

        Assertions.assertEquals("Agnieszka", json.getString("firstName"));

    }
}
