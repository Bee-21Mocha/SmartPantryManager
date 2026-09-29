package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetails extends AppCompatActivity {

    TextView txtRecipeName;
    TextView txtIngredients;
    TextView txtInstructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_details);

        txtRecipeName = findViewById(R.id.txtRecipeName);
        txtIngredients = findViewById(R.id.txtRecipeIngredients);
        txtInstructions = findViewById(R.id.txtRecipeInstructions);


        String recipeName = getIntent().getStringExtra("recipeName");
        String ingredients = getIntent().getStringExtra("ingredients");
        String instructions = getIntent().getStringExtra("instructions");


        txtRecipeName.setText(recipeName);
        txtIngredients.setText(ingredients);
        txtInstructions.setText(instructions);
    }
}