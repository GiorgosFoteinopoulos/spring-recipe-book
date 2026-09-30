package com.practice.recipes.recipe;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class IngredientForm {

    @NotBlank(message = "Please enter the ingredient's name.")
    @Size(max = 100, message = "The name can be at most 100 characters.")
    private String name;

    @Size(max = 50, message = "The amount can be at most 50 characters.")
    private String amount;

    public IngredientForm() {

    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
}

