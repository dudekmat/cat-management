package com.banzo.catmanagement.food.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FoodTypeDetails {

  Long id;
  String type;
}
