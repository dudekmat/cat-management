package com.github.dudekmat.catmanagement.food.controller;

import com.github.dudekmat.catmanagement.food.dto.FoodPayload;
import com.github.dudekmat.catmanagement.shared.exception.ValidationError;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.junit.jupiter.params.provider.Arguments;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FoodControllerTestValidationGenerator {

  static Stream<Arguments> generateFoodPayloadValidationData() {
    return Stream.of(nameBlank(), typeIdNull(), priceNull(), ratingTooLow(), ratingTooHigh());
  }

  private static Arguments nameBlank() {
    return Arguments.of(FoodPayload.builder()
            .netMass(BigDecimal.valueOf(200))
            .typeId(1L)
            .price(BigDecimal.valueOf(4.50))
            .build(),
        List.of(
            ValidationError.builder()
                .object("foodPayload")
                .field("name")
                .validationCode("NotBlank")
                .build()));
  }

  private static Arguments typeIdNull() {
    return Arguments.of(
        FoodPayload.builder()
            .name("Feringa")
            .netMass(BigDecimal.valueOf(200))
            .price(BigDecimal.valueOf(4.50))
            .build(),
        List.of(
            ValidationError.builder()
                .object("foodPayload")
                .field("typeId")
                .validationCode("NotNull")
                .build()));
  }

  private static Arguments priceNull() {
    return Arguments.of(
        FoodPayload.builder()
            .name("Feringa")
            .netMass(BigDecimal.valueOf(200))
            .typeId(1L)
            .build(),
        List.of(
            ValidationError.builder()
                .object("foodPayload")
                .field("price")
                .validationCode("NotNull")
                .build()));
  }

  private static Arguments ratingTooLow() {
    return Arguments.of(
        FoodPayload.builder()
            .name("Feringa")
            .netMass(BigDecimal.valueOf(200))
            .typeId(1L)
            .price(BigDecimal.valueOf(4.50))
            .rating(-1)
            .build(),
        List.of(
            ValidationError.builder()
                .object("foodPayload")
                .field("rating")
                .validationCode("Min")
                .build()));
  }

  private static Arguments ratingTooHigh() {
    return Arguments.of(
        FoodPayload.builder()
            .name("Feringa")
            .netMass(BigDecimal.valueOf(200))
            .typeId(1L)
            .price(BigDecimal.valueOf(4.50))
            .rating(7)
            .build(),
        List.of(
            ValidationError.builder()
                .object("foodPayload")
                .field("rating")
                .validationCode("Max")
                .build()));
  }
}
