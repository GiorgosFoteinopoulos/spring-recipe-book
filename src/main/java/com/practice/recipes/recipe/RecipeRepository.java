package com.practice.recipes.recipe;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @EntityGraph(attributePaths = "ingredients")
    List<Recipe> findByOwnerUsernameOrderByTitleAsc(String username);

    @EntityGraph(attributePaths = "ingredients")
    List<Recipe> findByOwnerUsernameAndPrepMinutesLessThanEqualOrderByPrepMinutesAsc(
            String username, Integer maxMinutes
    );

    Optional<Recipe> findByIdAndOwnerUsername(Long id, String username);

    @EntityGraph(attributePaths = "ingredients")
    Optional<Recipe> findWithIngredientsByIdAndOwnerUsername(Long id, String username);
}
