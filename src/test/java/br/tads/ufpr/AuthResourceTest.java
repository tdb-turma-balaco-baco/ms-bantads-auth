package br.tads.ufpr;

import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.vertx.http.runtime.devmode.Json;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class AuthResourceTest {

    @Test
    public void testLoginEndpointWithoutJsonBody() {
        given().contentType(ContentType.JSON)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void testLoginEndpointWithInvalidCredentials() {
        var body = Json.object()
                .put("invalid", "invalid")
                .build();

        given().contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void testLoginEndpointWithInvalidLogin() {
        var body = Json.object()
                .put("email", "invalid")
                .put("password", "invalid")
                .build();

        Log.info(body);

        given().contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

}