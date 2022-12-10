package com.banzo.catmanagement.food.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Builder
public class NutritionalValues {

  @Column(name = "protein")
  private BigDecimal protein;

  @Column(name = "carbohydrates")
  private BigDecimal carbohydrates;

  @Column(name = "fat")
  private BigDecimal fat;
}
