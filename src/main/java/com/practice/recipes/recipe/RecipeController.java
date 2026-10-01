package com.practice.recipes.recipe;


import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
@RequestMapping("/recipes")
public class RecipeController {


    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    // ---------------- Lists ----------------

    @GetMapping
    public String list(Principal principal, Model model) {
        model.addAttribute("recipes", recipeService.findAll(principal.getName()));
        return "recipes/list";
    }

    @GetMapping("/quick")
    public String quick(@RequestParam(name = "max", defaultValue = "20") Integer max,
                        Principal principal, Model model) {
        model.addAttribute("recipes", recipeService.findQuick(principal.getName(), max));
        model.addAttribute("filterTitle", "Ready in " + max + " minutes or less");
        return "recipes/list";
    }

    // ---------------- Detail ----------------

    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Long id, Principal principal, Model model) {
        model.addAttribute("recipe", loadWithIngredients(id, principal.getName()));
        model.addAttribute("ingredientForm", new IngredientForm());
        return "recipes/detail";
    }

    // ---------------- Create ----------------

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("recipeForm", new RecipeForm());
        return "recipes/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("recipeForm") RecipeForm form,
                         BindingResult bindingResult,
                         Principal principal) {
        if (bindingResult.hasErrors()) {
            return "recipes/form";
        }
        Recipe saved = recipeService.create(form, principal.getName());
        return "redirect:/recipes/" + saved.getId();
    }

    // ---------------- Update ----------------

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Long id, Principal principal, Model model) {
        model.addAttribute("recipeForm", RecipeForm.from(recipeService.getById(id, principal.getName())));
        return "recipes/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") Long id,
                         @Valid @ModelAttribute("recipeForm") RecipeForm form,
                         BindingResult bindingResult,
                         Principal principal) {
        if (bindingResult.hasErrors()) {
            form.setId(id);
            return "recipes/form";
        }
        recipeService.update(id, form, principal.getName());
        return "redirect:/recipes/" + id;
    }

    // ---------------- Delete ----------------

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id, Principal principal) {
        recipeService.delete(id, principal.getName());
        return "redirect:/recipes";
    }

    // ---------------- Ingredients ----------------

    @PostMapping("/{id}/ingredients")
    public String addIngredient(@PathVariable("id") Long id,
                                @Valid @ModelAttribute("ingredientForm") IngredientForm form,
                                BindingResult bindingResult,
                                Principal principal,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("recipe", loadWithIngredients(id, principal.getName()));
            return "recipes/detail";
        }
        recipeService.addIngredient(id, form, principal.getName());
        return "redirect:/recipes/" + id;
    }

    @PostMapping("/{id}/ingredients/{ingredientId}/delete")
    public String removeIngredient(@PathVariable("id") Long id,
                                   @PathVariable("ingredientId") Long ingredientId,
                                   Principal principal) {
        recipeService.removeIngredient(id, ingredientId, principal.getName());
        return "redirect:/recipes/" + id;
    }

    // ---------------- Helper ----------------

    private Recipe loadWithIngredients(Long id, String username) {
        return recipeService.findWithIngredients(id, username)
                .orElseThrow(() -> new NotFoundException("Recipe " + id + " not found"));
    }
}
