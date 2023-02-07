package pl.restautomation;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class CreateNewEmployeeTest {
    private static final String BASE_URL = "http://localhost:3000/employees";
    @Test
    void CreateNewEmployeeWithBodyTest(){

        String body = "{\n" +
                "      \"firstName\": \"Bartek\",\n" +
                "      \"lastName\": \"Czarny\",\n" +
                "      \"username\": \"bczarny\",\n" +
                "      \"email\": \"bczarny@testerprogramuje.pl\",\n" +
                "      \"phone\": \"731-111-111\",\n" +
                "      \"website\": \"testerprogramuje.pl\",\n" +
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

        Response response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(BASE_URL)
                .then()
                .extract()
                .response();

        Assertions.assertEquals(201, response.getStatusCode());

        JsonPath json = response.jsonPath();

        Assertions.assertEquals("Bartek", json.getString("firstName"));
        Assertions.assertEquals("Czarny", json.getString("lastName"));
        Assertions.assertEquals("bczarny", json.getString("username"));
        Assertions.assertEquals("bczarny@testerprogramuje.pl", json.getString("email"));

    }
}
