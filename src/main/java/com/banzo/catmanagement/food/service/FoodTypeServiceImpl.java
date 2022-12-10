package com.banzo.catmanagement.food.service;

import com.banzo.catmanagement.food.dto.FoodTypeDetails;
import com.banzo.catmanagement.food.model.FoodType;
import com.banzo.catmanagement.food.repository.FoodTypeRepository;
import com.banzo.catmanagement.shared.exception.RecordNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FoodTypeServiceImpl implements FoodTypeService {

  private final FoodTypeRepository foodTypeRepository;

  public static final String FOOD_TYPE_NOT_FOUND_MESSAGE = "Not found food type with id=";

  @Transactional
  @Override
  public List<FoodTypeDetails> findAll() {
    return foodTypeRepository.findAll().stream()
        .map(this::mapToFoodTypeDetails)
        .collect(Collectors.toList());
  }

  @Transactional
  @Override
  public FoodTypeDetails findById(long id) {
    return foodTypeRepository
        .findById(id)
        .map(this::mapToFoodTypeDetails)
        .orElseThrow(() -> new RecordNotFoundException(FOOD_TYPE_NOT_FOUND_MESSAGE + id));
  }

  private FoodTypeDetails mapToFoodTypeDetails(FoodType foodType) {
    return FoodTypeDetails.builder().id(foodType.getId()).type(foodType.getType()).build();
  }
}
