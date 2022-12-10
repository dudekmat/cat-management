package com.banzo.catmanagement.food.repository;

import com.banzo.catmanagement.food.model.Food;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {

  List<Food> findByTypeId(long id);

  List<Food> findByNameContainingIgnoreCase(String name);

  List<Food> findByOrderByRatingDesc();
}
