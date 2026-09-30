package com.practice.recipes.recipe;


import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


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


    @GetMapping("/quick")
    public String quick(@RequestParam(name = "max", defaultValue = "20") Integer max, Model model) {
        model.addAttribute("recipes", recipeService.findQuick(max));
        model.addAttribute("filterTitle", "Ready in " + max + " minutes or less");
        return "recipes/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("recipe", loadWithIngredients(id));
        model.addAttribute("ingredientForm", new IngredientForm());
        return "recipes/detail";
    }


    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("recipeForm", new RecipeForm());
        return "recipes/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("recipeForm") RecipeForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "recipes/form";
        }
        Recipe saved = recipeService.create(form);
        return "redirect:/recipes/" + saved.getId();
    }
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("recipeForm", RecipeForm.from(recipeService.getById(id)));
        return "recipes/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") Long id, @Valid @ModelAttribute("recipeForm") RecipeForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            form.setId(id);
            return "recipes/form";
        }
        recipeService.update(id, form);
        return "redirect:/recipes/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        recipeService.delete(id);
        return "redirect:/recipes";
    }

    @PostMapping("/{id}/ingredients")
    public String addIngredient(@PathVariable("id") Long id,
                                @Valid @ModelAttribute("ingredientForm") IngredientForm form,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("recipe", loadWithIngredients(id));
            return "recipes/detail";
        }
        recipeService.addIngredient(id, form);
        return "redirect:/recipes/" + id;
    }

    @PostMapping("/{id}/ingredients/{ingredientId}/delete")
    public String removeIngredient(@PathVariable("id") Long id,
                                   @PathVariable("ingredientId") Long ingredientId) {
        recipeService.removeIngredient(id, ingredientId);
        return "redirect:/recipes/" + id;
    }

    private Recipe loadWithIngredients(Long id) {
        return recipeService.findWithIngredients(id)
                .orElseThrow(() -> new NotFoundException("Recipe " + id + " not found"));
    }
}
