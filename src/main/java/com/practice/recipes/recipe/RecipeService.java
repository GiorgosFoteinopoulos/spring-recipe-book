package com.practice.recipes.recipe;


import jakarta.validation.Valid;
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
    public Recipe create(RecipeForm form) {
        Recipe recipe = new Recipe(
                form.getTitle().trim(),
                form.getInstructions().trim(),
                form.getServings(),
                form.getPrepMinutes());
        return recipeRepository.save(recipe);
    }

    @Transactional
    public Recipe update(Long id, RecipeForm form) {
        Recipe existing = getById(id);
        existing.setTitle(form.getTitle().trim());
        existing.setServings(form.getServings());
        existing.setPrepMinutes(form.getPrepMinutes());
        existing.setInstructions(form.getInstructions().trim());
        return existing;
    }

    @Transactional
    public void delete(Long id) {
        recipeRepository.delete(getById(id));
    }

    @Transactional
    public void addIngredient(Long recipeId, IngredientForm form) {
        Recipe recipe = getById(recipeId);
        String amount = form.getAmount() == null ? "" : form.getAmount().trim();
        recipe.addIngredient(new Ingredient(form.getName().trim(), amount));
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
