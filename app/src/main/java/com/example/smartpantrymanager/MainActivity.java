package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Gravity;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class MainActivity extends AppCompatActivity{

    DatabaseHelper databaseHelper;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageButton menuButton;

    RecyclerView recyclerPantry;
    PantryAdapter pantryAdapter;
    List<PantryItem> pantryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        menuButton = findViewById(R.id.menuButton);

        menuButton.setOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });

        recyclerPantry = findViewById(R.id.recyclerPantry);

        recyclerPantry.setLayoutManager( new LinearLayoutManager(this));

        pantryList = databaseHelper.getAllPantryItems();
        pantryAdapter = new PantryAdapter(pantryList);

        recyclerPantry.setAdapter(pantryAdapter);


        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home)  {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_pantry) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            } else if (id == R.id.addingredient){
                Intent intent = new Intent(MainActivity.this, IngredientActivity.class);
                startActivity(intent);
            } else if (id == R.id.suggest_receipe) {
                Intent intent = new Intent(MainActivity.this, SuggestedRecipes.class);
                startActivity(intent);
            } else if (id == R.id.settings){
                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);
            }

            drawerLayout.closeDrawer(GravityCompat.START);

            return true;
        });
    }
}