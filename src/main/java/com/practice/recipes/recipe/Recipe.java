package com.practice.recipes.recipe;


import com.practice.recipes.user.AppUser;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 4000)
    private String instructions;

    private Integer servings;
    private Integer prepMinutes;


    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    private List<Ingredient> ingredients = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private AppUser owner;

    public Recipe() {

    }

    public Recipe(String title, String instructions, Integer servings, Integer prepMinutes) {
        this.title = title;
        this.instructions = instructions;
        this.servings = servings;
        this.prepMinutes = prepMinutes;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
        ingredient.setRecipe(this);
    }

    public void removeIngredient(Ingredient ingredient) {
        ingredients.remove(ingredient);
        ingredient.setRecipe(null);
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
    public String getInstructions() {
        return instructions;
    }
    public void setInstructions(String instructions) {
        this.instructions = instructions;
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
    public List<Ingredient> getIngredients() {
        return ingredients;
    }
    public AppUser getOwner() {
        return owner;
    }
    public void setOwner(AppUser owner) {
        this.owner = owner;
    }
}

