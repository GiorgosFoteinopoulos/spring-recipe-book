package com.practice.recipes.recipe;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final RecipeRepository recipeRepository;

    public DataSeeder(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void run(String... args) {
        if (recipeRepository.count() > 0) {
            return;
        }

        Recipe salad = new Recipe(
                "Greek salad",
                """
                Cut the tomatoes and cucumber into large chunks.
                Slice the onion thinly and add the olives.
                Place the feta on top, sprinkle with oregano and drizzle with olive oil.
                        """,
                2, 25);
        salad.addIngredient(new Ingredient("Tomatoes", "3 medium"));
        salad.addIngredient(new Ingredient("Cucumber", "1"));
        salad.addIngredient(new Ingredient("Red onion", "1/2"));
        salad.addIngredient(new Ingredient("Kalamata olives", "a handful"));
        salad.addIngredient(new Ingredient("Feta", "200 g"));
        salad.addIngredient(new Ingredient("Olive oil", "3 tbsp"));
        salad.addIngredient(new Ingredient("Dried oregano", "1 tsp"));

        Recipe pancakes = new Recipe(
                "Pancakes",
                """
                Whisk the flour, sugar and baking powder in a bowl.
                Add the milk and eggs and whisk until smooth.
                Cook ladlefuls in a hot, lightly oiled pan, about 2 minutes per side.
                        """, 4, 25);

        pancakes.addIngredient(new Ingredient("Flour", "250 g"));
        pancakes.addIngredient(new Ingredient("Milk", "300 ml"));
        pancakes.addIngredient(new Ingredient("Eggs", "2"));
        pancakes.addIngredient(new Ingredient("Sugar", "2 tbsp"));
        pancakes.addIngredient(new Ingredient("Baking powder", "2 tsp"));


        recipeRepository.saveAll(List.of(salad, pancakes));
    }
}
