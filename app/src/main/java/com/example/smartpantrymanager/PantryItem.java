package com.example.smartpantrymanager;

public class PantryItem {
    private int id;
    private String ingredient;
    private int quantity;
    private String unit;
    private String expiryDate;

    public PantryItem(int id, String ingredient, int quantity, String unit, String expiryDate) {
        this.id = id;
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.unit = unit;
        this.expiryDate = expiryDate;
    }

    public int getId(){
        return id;
    }

    public String getIngredient(){
        return ingredient;
    }

    public int getQuantity(){
        return quantity;
    }

    public String getUnit(){
        return unit;
    }

    public String getExpiryDate(){
        return expiryDate;
    }
}
