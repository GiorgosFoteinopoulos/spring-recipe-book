package com.practice.recipes.recipe;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }
    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }
    public Optional<Recipe> findWithIngredients(Long id) {
        return recipeRepository.findWithIngredientsById(id);
    }
}
