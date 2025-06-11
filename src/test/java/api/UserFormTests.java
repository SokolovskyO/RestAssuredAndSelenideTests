package api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

public class UserFormTests {

    @BeforeAll
    public static void setup() {
        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://reqres.in/api")
                .addHeader("x-api-key", "reqres-free-v1")
                .setContentType("application/json")
                .build();

        RestAssured.requestSpecification = requestSpec;
    }

    @Test
    @DisplayName("Получение списка пользователей")
    void testGetUsersList() {
        given()
                .queryParam("page", 2)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .body("page", equalTo(2))
                .body("data", hasSize(greaterThan(0)));
    }

    @Test
    @DisplayName("Получение пользователя по id")
    void testGetSingleUser() {
        when()
                .get("/users/2")
                .then()
                .statusCode(200)
                .body("data.id", equalTo(2))
                .body("data.email", containsString("@reqres.in"));
    }

    @Test
    @DisplayName("Получение несуществующего пользователя")
    void testUserNotFound() {
        when()
                .get("/users/23")
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Создание пользователя")
    void testCreateUser() {
        String requestBody = """
                {
                  "name": "morpheus",
                  "job": "leader"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .body("name", equalTo("morpheus"))
                .body("job", equalTo("leader"));
    }

    @Test
    @DisplayName("Обновление пользователя")
    void testUpdateUser() {
        String requestBody = """
                {
                  "name": "morpheus",
                  "job": "zion resident"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put("/users/2")
                .then()
                .statusCode(200)
                .body("job", equalTo("zion resident"));
    }

    @Test
    @DisplayName("Удаление пользователя")
    void testDeleteUser() {
        when()
                .delete("/users/2")
                .then()
                .statusCode(204);
    }

    @Test
    @DisplayName("Успешный логин пользователя")
    void testSuccessfulLogin() {
        String requestBody = """
                {
                  "email": "eve.holt@reqres.in",
                  "password": "cityslicka"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @Test
    @DisplayName("Некорректный логин пользователя")
    void testUnsuccessfulLogin() {
        String requestBody = """
                {
                  "email": "peter@klaven"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/login")
                .then()
                .statusCode(400)
                .body("error", equalTo("Missing password"));
    }
}
