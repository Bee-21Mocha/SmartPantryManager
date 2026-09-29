package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SmartPantryManager.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(
                "CREATE TABLE PantryList (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "Ingredient TEXT, " +
                        "Quantity INTEGER, " +
                        "Unit TEXT, " +
                        "Expired_date TEXT" +
                        ")"
        );

        db.execSQL(
                "CREATE TABLE Recipes (" +
                        "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "RecipeName TEXT, " +
                        "Ingredients TEXT, " +
                        "Instructions TEXT" +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS PantryList");
        db.execSQL("DROP TABLE IF EXISTS Recipes");

        onCreate(db);
    }

    public void insertRecipe(String name, String ingredients, String instructions) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("RecipeName", name);
        values.put("Ingredients", ingredients);
        values.put("Instructions", instructions);

        db.insert("Recipes", null, values);

        db.close();
    }

    public List<Recipe> getAllRecipes() {

        List<Recipe> recipeList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM Recipes",
                null
        );

        if (cursor.moveToFirst()) {

            do {

                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow("ID")
                );

                String recipeName = cursor.getString(
                        cursor.getColumnIndexOrThrow("RecipeName")
                );

                String ingredients = cursor.getString(
                        cursor.getColumnIndexOrThrow("Ingredients")
                );

                String instructions = cursor.getString(
                        cursor.getColumnIndexOrThrow("Instructions")
                );


                Recipe recipe = new Recipe(
                        id,
                        recipeName,
                        ingredients,
                        instructions
                );


                recipeList.add(recipe);


            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recipeList;
    }

    public int getRecipeCount() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM Recipes",
                null
        );

        int count = 0;

        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        return count;
    }

    public List<PantryItem> getAllPantryItems() {
        List<PantryItem> pantryList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM PantryList",
                null
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow("id")
                );

                String ingredient = cursor.getString(
                        cursor.getColumnIndexOrThrow("Ingredient")
                );

                int quantity = cursor.getInt(
                        cursor.getColumnIndexOrThrow("Quantity")
                );

                String unit = cursor.getString(
                        cursor.getColumnIndexOrThrow("Unit")
                );

                String expireDate = cursor.getString(
                        cursor.getColumnIndexOrThrow("Expired_date")
                );

                PantryItem pantryItem = new PantryItem(id, ingredient, quantity, unit, expireDate);

                pantryList.add(pantryItem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return pantryList;
    }

    public List<Recipe> getStrictSuggestedRecipes() {

        List<Recipe> suggestedRecipes = new ArrayList<>();

        List<Recipe> allRecipes = getAllRecipes();
        List<PantryItem> pantryItems = getAllPantryItems();

        for (Recipe recipe : allRecipes) {

            String ingredients = recipe.getIngredients();

            String[] requiredIngredients = ingredients.split(",");

            boolean recipeMatches = true;

            for (String required : requiredIngredients) {

                required = required.trim();

                String[] parts = required.split(":");

                // Every ingredient must have:
                // name : quantity : unit
                if (parts.length != 3) {
                    recipeMatches = false;
                    break;
                }

                String requiredName = parts[0].trim();

                int requiredQuantity;

                try {

                    requiredQuantity =
                            Integer.parseInt(parts[1].trim());

                } catch (NumberFormatException e) {

                    recipeMatches = false;
                    break;
                }

                String requiredUnit = parts[2].trim();

                boolean ingredientFound = false;

                for (PantryItem pantryItem : pantryItems) {

                    String pantryName =
                            pantryItem.getIngredient().trim();

                    String pantryUnit =
                            pantryItem.getUnit().trim();

                    if (ingredientMatch(
                            requiredName,
                            pantryName)) {

                        if (quantityIsEnough(
                                requiredQuantity,
                                requiredUnit,
                                pantryItem.getQuantity(),
                                pantryUnit)) {

                            ingredientFound = true;
                            break;
                        }
                    }
                }

                if (!ingredientFound) {

                    recipeMatches = false;
                    break;
                }
            }

            if (recipeMatches) {

                suggestedRecipes.add(recipe);
            }
        }

        return suggestedRecipes;
    }

    private boolean quantityIsEnough(
            int requiredQuantity,
            String requiredUnit,
            int pantryQuantity,
            String pantryUnit) {

        requiredUnit = requiredUnit.toLowerCase().trim();
        pantryUnit = pantryUnit.toLowerCase().trim();

        // Same unit
        if (requiredUnit.equals(pantryUnit)) {
            return pantryQuantity >= requiredQuantity;
        }

        // kg to g
        if (isKilogram(requiredUnit) && isGram(pantryUnit)) {

            double requiredInGrams =
                    requiredQuantity * 1000.0;

            return pantryQuantity >= requiredInGrams;
        }

        // g to kg
        if (isGram(requiredUnit) && isKilogram(pantryUnit)) {

            double pantryInGrams =
                    pantryQuantity * 1000.0;

            return pantryInGrams >= requiredQuantity;
        }

        // L to ml
        if (isLitre(requiredUnit) && isMillilitre(pantryUnit)) {

            double requiredInMl =
                    requiredQuantity * 1000.0;

            return pantryQuantity >= requiredInMl;
        }

        // ml to L
        if (isMillilitre(requiredUnit) && isLitre(pantryUnit)) {

            double pantryInMl =
                    pantryQuantity * 1000.0;

            return pantryInMl >= requiredQuantity;
        }

        return false;
    }

    private boolean isKilogram(String unit) {

        return unit.equals("kg")
                || unit.equals("kilogram")
                || unit.equals("kilograms");
    }

    private boolean isGram(String unit) {

        return unit.equals("g")
                || unit.equals("gram")
                || unit.equals("grams");
    }

    private boolean isLitre(String unit) {

        return unit.equals("l")
                || unit.equals("litre")
                || unit.equals("litres")
                || unit.equals("liter")
                || unit.equals("liters");
    }

    private boolean isMillilitre(String unit) {

        return unit.equals("ml")
                || unit.equals("millilitre")
                || unit.equals("millilitres")
                || unit.equals("milliliter")
                || unit.equals("milliliters");
    }

    private boolean ingredientMatch(
            String required,
            String pantry) {

        required = required.toLowerCase().trim();
        pantry = pantry.toLowerCase().trim();

        required = required.replace("-", " ");
        pantry = pantry.replace("-", " ");

        if (required.equals(pantry)) {
            return true;
        }


        if (required.endsWith("s")
                && required.length() > 1) {

            required =
                    required.substring(
                            0,
                            required.length() - 1
                    );
        }


        if (pantry.endsWith("s") && pantry.length() > 1) {
            pantry = pantry.substring(0, pantry.length() - 1
                    );
        }

        return required.equals(pantry);
    }
}


