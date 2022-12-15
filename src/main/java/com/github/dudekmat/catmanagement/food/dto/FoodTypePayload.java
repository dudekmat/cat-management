package com.github.dudekmat.catmanagement.food.dto;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FoodTypePayload {

  @NotBlank String type;
}
