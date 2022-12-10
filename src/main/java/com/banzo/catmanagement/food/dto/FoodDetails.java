package com.banzo.catmanagement.food.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FoodDetails {

  Long id;
  String name;
  String type;
  Long typeId;
  BigDecimal price;
  Integer rating;
  String image;
  BigDecimal netMass;
  BigDecimal protein;
  BigDecimal carbohydrates;
  BigDecimal fat;
  LocalDateTime createdDate;
  LocalDateTime modifiedDate;
}
