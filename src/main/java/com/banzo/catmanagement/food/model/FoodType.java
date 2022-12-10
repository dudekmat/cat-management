package com.banzo.catmanagement.food.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "food_type")
public class FoodType {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "food_type_generator")
  @SequenceGenerator(name = "food_type_generator", sequenceName = "food_type_id_seq", allocationSize = 1)
  private Long id;

  @Column(name = "type")
  private String type;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "type")
  private List<Food> foodItems;
}
