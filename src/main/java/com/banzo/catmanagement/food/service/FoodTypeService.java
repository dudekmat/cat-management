package com.banzo.catmanagement.food.service;

import com.banzo.catmanagement.food.dto.FoodTypeDetails;
import java.util.List;

public interface FoodTypeService {

  List<FoodTypeDetails> findAll();

  FoodTypeDetails findById(long id);
}
