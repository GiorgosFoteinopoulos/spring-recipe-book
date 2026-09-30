package com.practice.recipes.recipe;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }
    public List<Recipe> findAll() {
        return recipeRepository.findAllByOrderByTitleAsc();
    }
    public Optional<Recipe> findWithIngredients(Long id) {
        return recipeRepository.findWithIngredientsById(id);
    }

    public List<Recipe> findQuick(Integer maxMinutes) {
        return recipeRepository.findByPrepMinutesLessThanEqualOrderByPrepMinutesAsc(maxMinutes);
    }

    public Recipe getById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Recipe" + id + " not found"));
    }

    @Transactional
    public Recipe create(Recipe recipe) {
        recipe.setId(null);
        return recipeRepository.save(recipe);
    }

    @Transactional
    public Recipe update(Long id, Recipe form) {
        Recipe existing = getById(id);
        existing.setTitle(form.getTitle());
        existing.setServings(form.getServings());
        existing.setPrepMinutes(form.getPrepMinutes());
        existing.setInstructions(form.getInstructions());
        return existing;


















        
    }

    @Transactional
    public void delete(Long id) {
        Recipe existing = getById(id);
        recipeRepository.delete(existing);
    }

    @Transactional
    public void addIngredient(Long recipeId, String name, String amount) {
        Recipe recipe = getById(recipeId);
        recipe.addIngredient(new Ingredient(name.trim(), amount.trim()));
    }

    @Transactional
    public void removeIngredient(Long recipeId, Long ingredientId) {
        Recipe recipe = getById(recipeId);

        Ingredient ingredient = recipe.getIngredients().stream()
                .filter(i -> i.getId().equals(ingredientId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(
                        "Ingredient " + ingredientId + " not found in recipe " + recipeId));

        recipe.removeIngredient(ingredient);
    }
}
