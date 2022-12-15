package com.github.dudekmat.catmanagement.food.service;

import static com.github.dudekmat.catmanagement.food.service.FoodTypeServiceImpl.FOOD_TYPE_NOT_FOUND_MESSAGE;

import com.github.dudekmat.catmanagement.food.dto.FoodDetails;
import com.github.dudekmat.catmanagement.food.dto.FoodPayload;
import com.github.dudekmat.catmanagement.food.model.Food;
import com.github.dudekmat.catmanagement.food.model.FoodType;
import com.github.dudekmat.catmanagement.food.model.NutritionalValues;
import com.github.dudekmat.catmanagement.food.repository.FoodRepository;
import com.github.dudekmat.catmanagement.food.repository.FoodTypeRepository;
import com.github.dudekmat.catmanagement.shared.exception.RecordNotFoundException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

  private final FoodRepository foodRepository;
  private final FoodTypeRepository foodTypeRepository;
  private final Clock clock;

  public static final String FOOD_NOT_FOUND_MESSAGE = "Not found food with id=";

  @Transactional
  @Override
  public List<FoodDetails> findAll() {
    return foodRepository.findAll().stream()
        .map(this::mapToFoodDetails)
        .collect(Collectors.toList());
  }

  @Transactional
  @Override
  public FoodDetails findById(long id) {
    return foodRepository
        .findById(id)
        .map(this::mapToFoodDetails)
        .orElseThrow(() -> new RecordNotFoundException(FOOD_NOT_FOUND_MESSAGE + id));
  }

  @Transactional
  @Override
  public FoodDetails create(FoodPayload foodPayload) {

    FoodType foodType =
        foodTypeRepository
            .findById(foodPayload.getTypeId())
            .orElseThrow(
                () ->
                    new RecordNotFoundException(
                        FOOD_TYPE_NOT_FOUND_MESSAGE + foodPayload.getTypeId()));

    return mapToFoodDetails(foodRepository.save(Food.builder()
        .name(foodPayload.getName())
        .type(foodType)
        .price(foodPayload.getPrice())
        .rating(foodPayload.getRating())
        .image(foodPayload.getImage())
        .netMass(foodPayload.getNetMass())
        .nutritionalValues(
            NutritionalValues.builder()
                .protein(foodPayload.getProtein())
                .carbohydrates(foodPayload.getCarbohydrates())
                .fat(foodPayload.getFat())
                .build())
        .createdDate(LocalDateTime.now(clock))
        .build()));
  }

  @Transactional
  @Override
  public void deleteById(long id) {
    Food food = foodRepository
        .findById(id)
        .orElseThrow(() -> new RecordNotFoundException(FOOD_NOT_FOUND_MESSAGE + id));

    foodRepository.delete(food);
  }

  @Transactional
  @Override
  public List<FoodDetails> findByTypeId(long typeId) {
    return foodRepository.findByTypeId(typeId).stream()
        .map(this::mapToFoodDetails)
        .collect(Collectors.toList());
  }

  @Transactional
  @Override
  public List<FoodDetails> findByName(String name) {
    return foodRepository.findByNameContainingIgnoreCase(name).stream()
        .map(this::mapToFoodDetails)
        .collect(Collectors.toList());
  }

  @Transactional
  @Override
  public List<FoodDetails> findAllOrderByRating() {
    return foodRepository.findByOrderByRatingDesc().stream()
        .map(this::mapToFoodDetails)
        .collect(Collectors.toList());
  }

  @Override
  public FoodDetails update(long id, FoodPayload foodPayload) {
    Food existingFood = foodRepository
        .findById(id)
        .orElseThrow(() -> new RecordNotFoundException(FOOD_NOT_FOUND_MESSAGE + id));

    FoodType foodType =
        foodTypeRepository
            .findById(foodPayload.getTypeId())
            .orElseThrow(
                () ->
                    new RecordNotFoundException(
                        FOOD_TYPE_NOT_FOUND_MESSAGE + foodPayload.getTypeId()));

    return mapToFoodDetails(foodRepository.save(Food.builder()
        .id(existingFood.getId())
        .name(foodPayload.getName())
        .type(foodType)
        .price(foodPayload.getPrice())
        .rating(foodPayload.getRating())
        .image(foodPayload.getImage())
        .netMass(foodPayload.getNetMass())
        .nutritionalValues(
            NutritionalValues.builder()
                .protein(foodPayload.getProtein())
                .carbohydrates(foodPayload.getCarbohydrates())
                .fat(foodPayload.getFat())
                .build())
        .createdDate(existingFood.getCreatedDate())
        .modifiedDate(LocalDateTime.now(clock))
        .build()));
  }

  private FoodDetails mapToFoodDetails(Food food) {
    return FoodDetails.builder()
        .id(food.getId())
        .name(food.getName())
        .type(food.getType().getType())
        .typeId(food.getType().getId())
        .price(food.getPrice())
        .rating(food.getRating())
        .image(food.getImage())
        .netMass(food.getNetMass())
        .protein(
            Objects.nonNull(food.getNutritionalValues())
                ? food.getNutritionalValues().getProtein()
                : null)
        .carbohydrates(
            Objects.nonNull(food.getNutritionalValues())
                ? food.getNutritionalValues().getCarbohydrates()
                : null)
        .fat(
            Objects.nonNull(food.getNutritionalValues())
                ? food.getNutritionalValues().getFat()
                : null)
        .createdDate(food.getCreatedDate())
        .modifiedDate(food.getModifiedDate())
        .build();
  }
}
