package com.practice.recipes.recipe;


import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("recipe", new Recipe());
        return "recipes/form";
    }

    @PostMapping
    public String create(@ModelAttribute Recipe recipe) {
        Recipe saved = recipeService.create(recipe);
        return "redirect:/recipes/" + saved.getId();
    }
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("recipe", recipeService.getById(id));
        return "recipes/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute Recipe recipe) {
        recipeService.update(id, recipe);
        return "redirect:/recipes/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        recipeService.delete(id);
        return "redirect:/recipes";
    }

    @PostMapping("/{id}/ingredients")
    public String addIngredient(@PathVariable("id") Long id,
                                @RequestParam("name") String name,
                                @RequestParam(name = "amount", defaultValue = "") String amount) {
        recipeService.addIngredient(id, name, amount);
        return "redirect:/recipes/" + id;
    }

    @PostMapping("/{id}/ingredients/{ingredientId}/delete")
    public String removeIngredient(@PathVariable("id") Long id,
                                   @PathVariable("ingredientId") Long ingredientId) {
        recipeService.removeIngredient(id, ingredientId);
        return "redirect:/recipes/" + id;
    }
}
