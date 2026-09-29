package com.example.smartpantrymanager;

public class Recipe {
    private int id;
    private String recipeName;
    private String ingredients;
    private String instructions;

    public Recipe(int id, String recipeName, String ingredients, String instructions) {
        this.id = id;
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public int getId(){
        return id;
    }

    public String getRecipeName(){
        return recipeName;
    }

    public String getIngredients(){
        return ingredients;
    }

    public String getInstructions(){
        return instructions;
    }
}
