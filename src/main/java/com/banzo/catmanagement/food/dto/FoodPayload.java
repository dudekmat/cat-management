package com.banzo.catmanagement.food.dto;

import java.math.BigDecimal;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FoodPayload {

  @NotBlank(message = "is required")
  @Size(min = 1, message = "is required")
  String name;

  @NotNull(message = "is required")
  Long typeId;

  @NotNull(message = "is required")
  BigDecimal price;

  @Min(value = 1, message = "must be greater than or equal to 1")
  @Max(value = 5, message = "must be less than or equal to 5")
  Integer rating;

  String image;

  BigDecimal netMass;

  BigDecimal protein;

  BigDecimal carbohydrates;

  BigDecimal fat;
}
