package com.github.dudekmat.catmanagement.food.controller;

import com.github.dudekmat.catmanagement.food.dto.FoodTypeDetails;
import com.github.dudekmat.catmanagement.food.service.FoodTypeService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/product-types")
@RequiredArgsConstructor
public class FoodTypeController {

  private final FoodTypeService foodTypeService;

  @Operation(description = "Get list of food types")
  @GetMapping
  public List<FoodTypeDetails> getFoodTypes() {

    return foodTypeService.findAll();
  }

  @Operation(description = "Get food type details by id")
  @GetMapping("/{id}")
  public FoodTypeDetails getFoodTypeById(@PathVariable("id") @NotNull Long id) {

    return foodTypeService.findById(id);
  }
}
