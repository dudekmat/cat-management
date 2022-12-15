package com.github.dudekmat.catmanagement.food.controller;

import com.github.dudekmat.catmanagement.BaseIntegrationTest;
import com.github.dudekmat.catmanagement.shared.jwt.JwtTokenProvider;
import com.github.dudekmat.catmanagement.food.dto.FoodPayload;
import com.github.dudekmat.catmanagement.shared.exception.ValidationError;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class FoodControllerIT extends BaseIntegrationTest {

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
  @DisplayName("Should return food item list")
  @Sql(scripts = {"/db/scripts/clean_food.sql",
      "/db/scripts/insert_food.sql"}, executionPhase = BEFORE_TEST_METHOD)
  void shouldReturnFoodItemList() {

    var givenToken = jwtTokenProvider.generateToken(
        new UsernamePasswordAuthenticationToken("admin", "test"));

    RestAssured
        .given()
        .auth()
        .preemptive()
        .oauth2(givenToken)
        .when()
        .get("/api/products")
        .then()
        .log()
        .all()
        .statusCode(200)
        .and()
        .body("id[0]", equalTo(1))
        .body("name[0]", equalTo("Feringa"))
        .body("type[0]", equalTo("Dry"))
        .body("price[0]", equalTo(28.00F))
        .body("rating[0]", equalTo(5))
        .body("netMass[0]", equalTo(750.00F))
        .body("id[1]", equalTo(2))
        .body("name[1]", equalTo("Macs"))
        .body("type[1]", equalTo("Wet"))
        .body("price[1]", equalTo(25.20F))
        .body("rating[1]", equalTo(4))
        .body("netMass[1]", equalTo(400.00F));
  }

  @Test
  @DisplayName("Should return food item by id")
  @Sql(scripts = {"/db/scripts/clean_food.sql",
      "/db/scripts/insert_food.sql"}, executionPhase = BEFORE_TEST_METHOD)
  void shouldReturnFoodItemById() {

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
        .get("/api/products/{id}")
        .then()
        .log()
        .all()
        .statusCode(200)
        .and()
        .body("id", equalTo(1))
        .body("name", equalTo("Feringa"))
        .body("type", equalTo("Dry"))
        .body("price", equalTo(28.00F))
        .body("rating", equalTo(5))
        .body("netMass", equalTo(750.00F));
  }

  @Test
  @DisplayName("Should add food item")
  @Sql(scripts = {"/db/scripts/clean_food.sql"}, executionPhase = BEFORE_TEST_METHOD)
  void shouldAddFoodItem() {

    var givenToken = jwtTokenProvider.generateToken(
        new UsernamePasswordAuthenticationToken("admin", "test"));
    var givenFoodPayload = FoodPayload.builder()
        .name("Feringa")
        .netMass(BigDecimal.valueOf(200))
        .typeId(1L)
        .price(BigDecimal.valueOf(4.50))
        .build();

    RestAssured
        .given()
        .auth()
        .preemptive()
        .oauth2(givenToken)
        .body(givenFoodPayload)
        .contentType(ContentType.JSON)
        .when()
        .post("/api/products")
        .then()
        .log()
        .all()
        .statusCode(201);
  }

  @Test
  @DisplayName("Should update food item")
  @Sql(scripts = {"/db/scripts/clean_food.sql",
      "/db/scripts/insert_food.sql"}, executionPhase = BEFORE_TEST_METHOD)
  void shouldUpdateFoodItem() {

    var givenToken = jwtTokenProvider.generateToken(
        new UsernamePasswordAuthenticationToken("admin", "test"));
    long givenId = 1L;
    var givenFoodPayload = FoodPayload.builder()
        .name("Feringa")
        .netMass(BigDecimal.valueOf(200))
        .typeId(1L)
        .price(BigDecimal.valueOf(4.50))
        .build();

    RestAssured
        .given()
        .auth()
        .preemptive()
        .oauth2(givenToken)
        .pathParam("id", givenId)
        .body(givenFoodPayload)
        .contentType(ContentType.JSON)
        .when()
        .put("/api/products/{id}")
        .then()
        .log()
        .all()
        .statusCode(200);
  }

  @Test
  @DisplayName("Should delete food item")
  @Sql(scripts = {"/db/scripts/clean_food.sql",
      "/db/scripts/insert_food.sql"}, executionPhase = BEFORE_TEST_METHOD)
  void shouldDeleteFoodItem() {

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
        .delete("/api/products/{id}")
        .then()
        .log()
        .all()
        .statusCode(200);
  }

  @Test
  @DisplayName("Should return food items of type")
  @Sql(scripts = {"/db/scripts/clean_food.sql",
      "/db/scripts/insert_food.sql"}, executionPhase = BEFORE_TEST_METHOD)
  void shouldReturnFoodItemsOfType() {

    var givenToken = jwtTokenProvider.generateToken(
        new UsernamePasswordAuthenticationToken("admin", "test"));
    long givenId = 1L;

    RestAssured
        .given()
        .auth()
        .preemptive()
        .oauth2(givenToken)
        .queryParam("id", givenId)
        .when()
        .get("/api/products/search/findByTypeId")
        .then()
        .log()
        .all()
        .statusCode(200)
        .and()
        .body("id[0]", equalTo(2))
        .body("name[0]", equalTo("Macs"))
        .body("type[0]", equalTo("Wet"))
        .body("price[0]", equalTo(25.20F))
        .body("rating[0]", equalTo(4))
        .body("netMass[0]", equalTo(400.00F))
        .body("id[1]", equalTo(3))
        .body("name[1]", equalTo("Catz Finefood"))
        .body("type[1]", equalTo("Wet"))
        .body("price[1]", equalTo(28.75F))
        .body("rating[1]", equalTo(5))
        .body("netMass[1]", equalTo(400.00F));
  }

  @Test
  @DisplayName("Should return food items by name")
  @Sql(scripts = {"/db/scripts/clean_food.sql",
      "/db/scripts/insert_food.sql"}, executionPhase = BEFORE_TEST_METHOD)
  void shouldReturnFoodItemsByName() {

    var givenToken = jwtTokenProvider.generateToken(
        new UsernamePasswordAuthenticationToken("admin", "test"));
    String givenName = "macs";

    RestAssured
        .given()
        .auth()
        .preemptive()
        .oauth2(givenToken)
        .queryParam("name", givenName)
        .when()
        .get("/api/products/search/findByName")
        .then()
        .log()
        .all()
        .statusCode(200)
        .and()
        .body("id[0]", equalTo(2))
        .body("name[0]", equalTo("Macs"))
        .body("type[0]", equalTo("Wet"))
        .body("price[0]", equalTo(25.20F))
        .body("rating[0]", equalTo(4))
        .body("netMass[0]", equalTo(400.00F));
  }

  @ParameterizedTest
  @MethodSource(
      "com.banzo.catmanagement.food.controller.FoodControllerTestValidationGenerator#generateFoodPayloadValidationData")
  @DisplayName("Should return error when validation failed for create food item")
  @Sql(
      scripts = {"/db/scripts/clean_food.sql"},
      executionPhase = BEFORE_TEST_METHOD)
  void shouldReturnErrorWhenValidationFailedForCreate(FoodPayload givenBody,
      List<ValidationError> expectedValidationErrors) {

    var givenToken = jwtTokenProvider.generateToken(
        new UsernamePasswordAuthenticationToken("admin", "test"));

    List<ValidationError> actualValidationErrors =
        RestAssured
            .given()
            .auth()
            .preemptive()
            .oauth2(givenToken)
            .body(givenBody)
            .contentType(ContentType.JSON)
            .when()
            .post("/api/products")
            .then()
            .log()
            .all()
            .statusCode(400)
            .body("message", equalTo("alert_validation_error"))
            .extract()
            .jsonPath()
            .getList("errors", ValidationError.class);

    assertThat(actualValidationErrors).containsExactlyElementsOf(expectedValidationErrors);
  }
}
