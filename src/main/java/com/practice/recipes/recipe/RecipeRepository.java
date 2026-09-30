package com.practice.recipes.recipe;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @EntityGraph(attributePaths = "ingredients")
    List<Recipe> findAllByOrderByTitleAsc();

    List<Recipe> findByPrepMinutesLessThanEqualOrderByPrepMinutesAsc(Integer minutes);

    @EntityGraph(attributePaths = "ingredients")
    Optional<Recipe> findWithIngredientsById(Long id);
}
