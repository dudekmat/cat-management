package com.banzo.catmanagement.food.repository;

import com.banzo.catmanagement.food.model.FoodType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodTypeRepository extends JpaRepository<FoodType, Long> {}
