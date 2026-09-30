package com.practice.recipes.recipe;


import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("recipes", recipeService.findAll());
        return "recipes/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        Recipe recipe = recipeService.findWithIngredients(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Recipe " + id + " not found"));
        model.addAttribute("recipe", recipe);
        return "recipes/detail";
    }

    @GetMapping("/quick")
    public String quick(@RequestParam(name = "max", defaultValue = "20") Integer max, Model model) {
        model.addAttribute("recipes", recipeService.findQuick(max));
        model.addAttribute("filterTitle", "Ready in " + max + " minutes or less");
        return "recipes/list";
    }
}
