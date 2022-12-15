package com.github.dudekmat.catmanagement.food.service;

import com.github.dudekmat.catmanagement.food.dto.FoodTypeDetails;
import java.util.List;

public interface FoodTypeService {

  List<FoodTypeDetails> findAll();

  FoodTypeDetails findById(long id);
}
