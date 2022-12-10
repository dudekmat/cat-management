package com.banzo.catmanagement.food.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "food")
public class Food {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "food_generator")
  @SequenceGenerator(name = "food_generator", sequenceName = "food_id_seq", allocationSize = 1)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @ManyToOne
  @JoinColumn(name = "type_id", nullable = false)
  private FoodType type;

  @Column(name = "price")
  private BigDecimal price;

  @Column(name = "rating")
  private Integer rating;

  @Column(name = "image")
  private String image;

  @Column(name = "net_mass")
  private BigDecimal netMass;

  @Embedded private NutritionalValues nutritionalValues;

  @Column(name = "created_date")
  private LocalDateTime createdDate;

  @Column(name = "modified_date")
  private LocalDateTime modifiedDate;
}
