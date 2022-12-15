package com.github.dudekmat.catmanagement.food.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

import com.github.dudekmat.catmanagement.BaseIntegrationTest;
import com.github.dudekmat.catmanagement.food.dto.FoodDetails;
import com.github.dudekmat.catmanagement.food.dto.FoodPayload;
import com.github.dudekmat.catmanagement.shared.exception.RecordNotFoundException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class FoodServiceIT extends BaseIntegrationTest {

  @Autowired
  FoodService foodService;

  @Autowired
  Clock clock;

  @Test
  @DisplayName("Should add food item")
  @Sql(scripts = {"/db/scripts/clean_food.sql"}, executionPhase = BEFORE_TEST_METHOD)
  void shouldAddFoodItem() {

    var givenFoodPayload = FoodPayload.builder()
        .name("Feringa")
        .netMass(BigDecimal.valueOf(200))
        .typeId(1L)
        .price(BigDecimal.valueOf(4.50))
        .build();

    var expectedFood = FoodDetails.builder()
        .name("Feringa")
        .netMass(BigDecimal.valueOf(200))
        .type("Wet")
        .typeId(1L)
        .price(BigDecimal.valueOf(4.50))
        .createdDate(LocalDateTime.parse("2022-12-31T23:15:30.00"))
        .build();

    FoodDetails actualFood = foodService.create(givenFoodPayload);

    assertThat(actualFood).usingRecursiveComparison()
        .ignoringFields("id").isEqualTo(expectedFood);
  }

  @Test
  @DisplayName("Should throw exception for invalid type when creating food item")
  @Sql(scripts = {"/db/scripts/clean_food.sql"}, executionPhase = BEFORE_TEST_METHOD)
  void shouldThrowExceptionForInvalidTypeWhenCreatingFoodItem() {
    var givenFoodPayload = FoodPayload.builder()
        .name("Feringa")
        .netMass(BigDecimal.valueOf(200))
        .typeId(0L)
        .price(BigDecimal.valueOf(4.50))
        .build();

    assertThatThrownBy(() -> foodService.create(givenFoodPayload))
        .isInstanceOf(RecordNotFoundException.class)
        .hasMessage("Not found food type with id=0");
  }
}
