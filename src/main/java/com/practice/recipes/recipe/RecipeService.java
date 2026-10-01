package com.practice.recipes.recipe;


import com.practice.recipes.user.AppUser;
import com.practice.recipes.user.AppUserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final AppUserRepository userRepository;

    public RecipeService(RecipeRepository recipeRepository, AppUserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    public List<Recipe> findAll(String username) {
        return recipeRepository.findByOwnerUsernameOrderByTitleAsc(username);
    }

    public List<Recipe> findQuick(String username, Integer maxMinutes) {
        return recipeRepository.findByOwnerUsernameAndPrepMinutesLessThanEqualOrderByPrepMinutesAsc(username,maxMinutes);
    }

    public Optional<Recipe> findWithIngredients(Long id, String username) {
        return recipeRepository.findWithIngredientsByIdAndOwnerUsername(id, username);
    }

    public Recipe getById(Long id, String username) {
        return recipeRepository.findByIdAndOwnerUsername(id, username)
                .orElseThrow(() -> new NotFoundException("Recipe" + id + " not found"));
    }

    @Transactional
    public Recipe create(RecipeForm form, String username) {

        AppUser owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User" + username + " not found"));

        Recipe recipe = new Recipe(
                form.getTitle().trim(),
                form.getInstructions().trim(),
                form.getServings(),
                form.getPrepMinutes());
        recipe.setOwner(owner);
        return recipeRepository.save(recipe);
    }

    @Transactional
    public Recipe update(Long id, RecipeForm form, String username) {
        Recipe existing = getById(id, username);
        existing.setTitle(form.getTitle().trim());
        existing.setServings(form.getServings());
        existing.setPrepMinutes(form.getPrepMinutes());
        existing.setInstructions(form.getInstructions().trim());
        return existing;
    }

    @Transactional
    public void delete(Long id, String username) {
        recipeRepository.delete(getById(id, username));
    }

    @Transactional
    public void addIngredient(Long recipeId, IngredientForm form, String username) {
        Recipe recipe = getById(recipeId, username);
        String amount = form.getAmount() == null ? "" : form.getAmount().trim();
        recipe.addIngredient(new Ingredient(form.getName().trim(), amount));
    }

    @Transactional
    public void removeIngredient(Long recipeId, Long ingredientId, String username) {
        Recipe recipe = getById(recipeId, username);

        Ingredient ingredient = recipe.getIngredients().stream()
                .filter(i -> i.getId().equals(ingredientId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(
                        "Ingredient " + ingredientId + " not found in recipe " + recipeId));

        recipe.removeIngredient(ingredient);
    }
}
