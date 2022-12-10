package com.banzo.catmanagement.food.controller;

import com.banzo.catmanagement.food.dto.FoodDetails;
import com.banzo.catmanagement.food.dto.FoodPayload;
import com.banzo.catmanagement.food.service.FoodService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@Validated
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class FoodController {

  private final FoodService foodService;

  @Operation(description = "Get food item list")
  @GetMapping
  public List<FoodDetails> getFoodItems() {

    return foodService.findAll();
  }

  @Operation(description = "Get food item by id")
  @GetMapping("/{id}")
  public FoodDetails getFood(@PathVariable @NotNull Long id) {

    return foodService.findById(id);
  }

  @Operation(description = "Create new food item")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public FoodDetails addFood(@RequestBody @Valid FoodPayload foodPayload) {

    return foodService.create(foodPayload);
  }

  @Operation(description = "Update existing food item")
  @PutMapping("/{id}")
  public FoodDetails updateFood(
      @PathVariable @NotNull Long id, @RequestBody @Valid FoodPayload foodPayload) {

    return foodService.update(id, foodPayload);
  }

  @Operation(description = "Delete existing food item")
  @DeleteMapping("/{id}")
  public void deleteFood(@PathVariable @NotNull Long id) {

    foodService.deleteById(id);
  }

  @Operation(description = "Find food items by type id")
  @GetMapping("/search/findByTypeId")
  public List<FoodDetails> getFoodItemsOfType(@RequestParam("id") @NotNull Long typeId) {

    return foodService.findByTypeId(typeId);
  }

  @Operation(description = "Find food items by name")
  @GetMapping("/search/findByName")
  public List<FoodDetails> getFoodItemsByName(@RequestParam("name") @NotBlank String name) {

    return foodService.findByName(name);
  }

  @Operation(description = "Find food items by rating")
  @GetMapping("/search/findByRating")
  public List<FoodDetails> getFoodItemsOrderedByRating() {

    return foodService.findAllOrderByRating();
  }
}
