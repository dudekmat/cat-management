package com.banzo.catmanagement.food.controller;

import com.banzo.catmanagement.BaseIntegrationTest;
import com.banzo.catmanagement.shared.jwt.JwtTokenProvider;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class FoodTypeControllerIT extends BaseIntegrationTest {

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @LocalServerPort
  private int port;

  @BeforeEach
  void setUp() {
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    RestAssured.port = port;
  }

  @Test
  @DisplayName("Should return food type list")
  void shouldReturnFoodTypeList() {

    var givenToken = jwtTokenProvider.generateToken(
        new UsernamePasswordAuthenticationToken("admin", "test"));

    RestAssured
        .given()
        .auth()
        .preemptive()
        .oauth2(givenToken)
        .when()
        .get("/api/product-types")
        .then()
        .log()
        .all()
        .statusCode(200)
        .and()
        .body("id[0]", equalTo(1))
        .body("type[0]", equalTo("Wet"))
        .body("id[1]", equalTo(2))
        .body("type[1]", equalTo("Dry"));
  }

  @Test
  @DisplayName("Should return food type by id")
  void shouldReturnFoodTypeById() {

    var givenToken = jwtTokenProvider.generateToken(
        new UsernamePasswordAuthenticationToken("admin", "test"));

    long givenId = 1L;

    RestAssured
        .given()
        .auth()
        .preemptive()
        .oauth2(givenToken)
        .pathParam("id", givenId)
        .when()
        .get("/api/product-types/{id}")
        .then()
        .log()
        .all()
        .statusCode(200)
        .and()
        .body("id", equalTo(1))
        .body("type", equalTo("Wet"));
  }
}
