package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class SuggestedRecipes extends AppCompatActivity {

    RecyclerView recyclerRecipes;
    DatabaseHelper databaseHelper;
    RecipeAdapter recipeAdapter;
    List<Recipe> recipeList;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageButton menuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_suggested_recipes);


        databaseHelper = new DatabaseHelper(this);


        recyclerRecipes = findViewById(R.id.recyclerViewRecipes);

        recyclerRecipes.setLayoutManager(
                new LinearLayoutManager(this)
        );


        if (databaseHelper.getRecipeCount() == 0) {
            addRecipesToDatabase();
        }


        recipeList = databaseHelper.getStrictSuggestedRecipes();

        recipeAdapter = new RecipeAdapter(recipeList);


        recyclerRecipes.setAdapter(recipeAdapter);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        menuButton = findViewById(R.id.menuButton);

        menuButton.setOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });

        navigationView.setNavigationItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home) {

                Intent intent = new Intent(
                        SuggestedRecipes.this,
                        MainActivity.class
                );

                startActivity(intent);

            } else if (id == R.id.nav_pantry) {

                Intent intent = new Intent(
                        SuggestedRecipes.this,
                        MainActivity.class
                );

                startActivity(intent);

            } else if (id == R.id.addingredient) {

                Intent intent = new Intent(
                        SuggestedRecipes.this,
                        IngredientActivity.class
                );

                startActivity(intent);

            } else if (id == R.id.settings) {

                Intent intent = new Intent(
                        SuggestedRecipes.this,
                        Settings.class
                );

                startActivity(intent);
            }

            drawerLayout.closeDrawer(GravityCompat.START);

            return true;
        });
    }


    private void addRecipesToDatabase() {

        databaseHelper.insertRecipe(
                "Classic Garlic and Oil Pasta",
                "Pasta:200:g,Garlic:4:pieces,Olive Oil:3:tbsp",
                "Boil pasta in salted water until al dente. Reserve some pasta water before draining. Heat olive oil in a skillet over low heat and gently cook the sliced garlic until light golden. Toss the drained pasta into the garlic oil with a splash of pasta water until glossy and coated."
        );


        databaseHelper.insertRecipe(
                "3-Ingredient Tomato Soup",
                "Tomatoes:400:g,Butter:3:tbsp,Onion:1:piece,Salt:1:tsp",
                "Combine tomatoes, butter, onion and salt in a saucepan over medium heat. Simmer uncovered for about 30 minutes, stirring occasionally. Remove the onion before serving."
        );


        databaseHelper.insertRecipe(
                "Crispy Chickpea and Rice Bowl",
                "Chickpeas:400:g,Rice:1:kg,Olive Oil:2:tbsp,Cumin:1:tsp,Salt:1:tsp",
                "Cook the rice according to the package instructions. Toss chickpeas with olive oil, salt and cumin. Roast at 200 C for 20 to 25 minutes until crispy. Serve over the cooked rice."
        );


        databaseHelper.insertRecipe(
                "Simple Fried Rice",
                "Rice:2:kg,Eggs:2:pieces,Soy Sauce:2:tbsp,Oil:2:tbsp",
                "Heat oil in a pan. Scramble the eggs and set them aside. Add cooked rice and stir-fry for 3 to 4 minutes. Add soy sauce and the scrambled eggs. Stir until everything is hot."
        );


        databaseHelper.insertRecipe(
                "Classic French Omelet",
                "Eggs:3:pieces,Butter:1:tbsp,Salt:1:tsp,Black Pepper:1:tsp",
                "Whisk the eggs with salt. Melt butter in a non-stick skillet over medium-low heat. Add the eggs and gently push the cooked edges towards the centre. Fold the omelet and serve immediately."
        );


        databaseHelper.insertRecipe(
                "Peanut Butter and Soy Noodles",
                "Noodles:150:g,Peanut Butter:2:tbsp,Soy Sauce:1:tbsp,Water:2:tbsp",
                "Boil the noodles according to the package instructions and drain. Mix peanut butter, soy sauce and warm water until smooth. Toss the hot noodles in the peanut sauce until coated."
        );


        databaseHelper.insertRecipe(
                "Black Bean Quesadillas",
                "Tortillas:2:pieces,Black Beans:200:g,Cheese:50:g",
                "Mash the black beans with a little salt. Spread the beans and cheese over a tortilla and fold it closed. Cook in a lightly oiled skillet for 2 to 3 minutes per side until golden and the cheese has melted."
        );


        databaseHelper.insertRecipe(
                "Creamy Canned Tuna Salad",
                "Tuna:150:g,Mayonnaise:2:tbsp,Mustard:1:tsp,Salt:1:tsp,Pepper:1:tsp",
                "Flake the tuna into a bowl. Mix in mayonnaise, mustard, salt and pepper. Serve as a sandwich filling, over crackers or on its own."
        );


        databaseHelper.insertRecipe(
                "Stovetop Potato Hash",
                "Potatoes:2:pieces,Oil:2:tbsp,Salt:1:tsp,Pepper:1:tsp",
                "Dice the potatoes into small pieces. Heat oil in a skillet over medium-high heat. Add the potatoes and cook until golden and tender. Season with salt and pepper."
        );


        databaseHelper.insertRecipe(
                "Quick Tomato and Bean Stew",
                "Beans:400:g,Tomatoes:400:g,Garlic Powder:1:tsp,Olive Oil:1:tbsp,Salt:1:tsp,Pepper:1:tsp",
                "Heat olive oil in a pot over medium heat. Add tomatoes, beans, garlic powder, salt and pepper. Simmer for 10 to 12 minutes until thick. Serve with bread or rice."
        );


        databaseHelper.insertRecipe(
                "Easy Pancake Batter",
                "Flour:125:g,Milk:180:ml,Egg:1:piece,Baking Powder:2:tsp,Butter:1:tbsp",
                "Whisk the flour and baking powder. Add milk and egg and stir until combined. Melt butter in a skillet. Pour small portions of batter into the skillet and cook until bubbles form. Flip and cook until golden."
        );


        databaseHelper.insertRecipe(
                "Cheesy Baked Potato",
                "Potato:1:piece,Butter:1:tbsp,Cheese:35:g,Salt:1:tsp,Pepper:1:tsp",
                "Prick the potato with a fork. Bake at 200 C for 45 to 60 minutes or microwave until soft. Slice the potato open and add butter, salt, pepper and cheese."
        );
    }
}