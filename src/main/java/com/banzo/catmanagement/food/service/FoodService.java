package com.banzo.catmanagement.food.service;

import com.banzo.catmanagement.food.dto.FoodDetails;
import com.banzo.catmanagement.food.dto.FoodPayload;
import java.util.List;

public interface FoodService {

  List<FoodDetails> findAll();

  FoodDetails findById(long id);

  FoodDetails create(FoodPayload foodPayload);

  void deleteById(long id);

  List<FoodDetails> findByTypeId(long typeId);

  List<FoodDetails> findByName(String name);

  List<FoodDetails> findAllOrderByRating();

  FoodDetails update(long id, FoodPayload foodPayload);
}
