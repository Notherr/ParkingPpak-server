/*
package com.luppy.parkingppak.config;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class JavaSecurityConfigTest {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    public void google로그인_시도시_oauth2인증창이_등장한다 () {
        given()
                .when()
                .redirects().follow(false)
                .get( "/oauth2/authorization/google")
                .then()
                .statusCode(302)
                .header("Location", containsString("https://accounts.google.com/o/oauth2"));
    }
}

 */
