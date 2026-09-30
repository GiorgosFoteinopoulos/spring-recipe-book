package com.practice.recipes.recipe;

import jakarta.validation.constraints.*;

public class RecipeForm {

    private Long id;

    @NotBlank
    @Size(max = 100, message = "The title can be at most 100 characters.")
    private String title;

    @NotNull(message = "Please enter the number of servings.")
    @Min(value = 1, message = "Servings must be at least 1.")
    @Max(value = 50, message = "Servings can be at most 50.")
    private Integer servings;

    @NotNull(message = "Please enter the preparation time.")
    @Min(value = 1, message = "Prep must be at least 1 minute.")
    @Max(value = 1440, message = "Prep time can be at most 1440 minutes (24 hours).")
    private Integer prepMinutes;

    @NotBlank(message = "Please write the instructions.")
    @Size(max = 4000, message = "Instructions can be at most 4000 characters.")
    private String instructions;

   public RecipeForm() {

   }

   public static RecipeForm from(Recipe recipe) {
       RecipeForm form = new RecipeForm();
       form.setId(recipe.getId());
       form.setTitle(recipe.getTitle());
       form.setServings(recipe.getServings());
       form.setPrepMinutes(recipe.getPrepMinutes());
       form.setInstructions(recipe.getInstructions());
       return form;
   }
   public Long getId() {
       return id;
   }
   public void setId(Long id) {
       this.id = id;
   }
   public String getTitle() {
       return title;
   }
   public void setTitle(String title) {
       this.title = title;
   }
   public Integer getServings() {
       return servings;
   }
   public void setServings(Integer servings) {
       this.servings = servings;
   }
   public Integer getPrepMinutes() {
       return prepMinutes;
   }
   public void setPrepMinutes(Integer prepMinutes) {
       this.prepMinutes = prepMinutes;
   }
   public String getInstructions() {
       return instructions;
   }
   public void setInstructions(String instructions) {
       this.instructions = instructions;
   }
}
