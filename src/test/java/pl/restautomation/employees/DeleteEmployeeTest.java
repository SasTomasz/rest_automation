package pl.restautomation.employees;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class DeleteEmployeeTest {

    public static final String BASE_URL = "http://localhost:3000/employees";

    @Test
    void deleteEmployeeTest() {
        Response response = given()
                .pathParam("id", 6)
                .when()
                .delete(BASE_URL + "/{id}")
                .then()
                .extract()
                .response();

        Assertions.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
    }
}