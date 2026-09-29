package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import android.widget.Spinner;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.CalendarView;


public class IngredientActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageButton menuButton;
    Spinner Unit;
    EditText editIngredientName;
    EditText editQuantity;
    Button buttonSave;
    CalendarView calendarView;
    String selectedExpiryDate = "";

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ingredient);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        menuButton = findViewById(R.id.menuButton);

        editIngredientName = findViewById(R.id.editIngredientName);
        editQuantity = findViewById(R.id.editQuantity);
        buttonSave = findViewById(R.id.buttonSave);
        Unit = findViewById(R.id.Unit);
        calendarView = findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                selectedExpiryDate = year + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth);
            }
        });

        databaseHelper = new DatabaseHelper(this);

        String[] categories = {
                "Select a Unit",
                "Litres (l)",
                "Millilitre (ml)",
                "Kilograms (kg)",
                "Grams (g)",
                "Piece (p)"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categories
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        Unit.setAdapter(adapter);

        menuButton.setOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });

        buttonSave.setOnClickListener(v -> {
            saveIngredients();
        });

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                Intent intent = new Intent(IngredientActivity.this, MainActivity.class);
                startActivity(intent);
            } else if (id ==R.id.suggest_receipe) {
                Intent intent = new Intent(IngredientActivity.this, SuggestedRecipes.class);
                startActivity(intent);
            } else if (id == R.id.settings) {
                Intent intent = new Intent(IngredientActivity.this, Settings.class);
                startActivity(intent);
            }

            drawerLayout.closeDrawer(GravityCompat.START);

            return true;
        });
    }

    private void saveIngredients() {

        String ingredients = editIngredientName.getText().toString().trim();
        String quantityText = editQuantity.getText().toString().trim();
        String unit = Unit.getSelectedItem().toString();
        String expiryDateText = selectedExpiryDate;


        if (ingredients.isEmpty() || quantityText.isEmpty() || expiryDateText.isEmpty()) {

            Toast.makeText(
                    this,
                    "Please fill in all fields",
                    Toast.LENGTH_SHORT).show();

            return;
        }

        if (unit.equals("Select a Unit")) {

            Toast.makeText(
                    this,
                    "Please select a unit",
                    Toast.LENGTH_SHORT).show();

            return;
        }


        int quantity;

        try {

            quantity = Integer.parseInt(quantityText);

        } catch (NumberFormatException e) {

            Toast.makeText(
                    this,
                    "Please enter a valid quantity",
                    Toast.LENGTH_SHORT).show();

            return;
        }


        SQLiteDatabase db = databaseHelper.getWritableDatabase();


        ContentValues values = new ContentValues();

        values.put("Ingredient", ingredients);
        values.put("Quantity", quantity);
        values.put("Unit", unit);
        values.put("Expired_date", expiryDateText);


        long result = db.insert(
                "PantryList",
                null,
                values
        );


        if (result != -1) {

            Toast.makeText(
                    this,
                    "Ingredient saved successfully!",
                    Toast.LENGTH_SHORT).show();


            editIngredientName.setText("");
            editQuantity.setText("");
            selectedExpiryDate = "";

        } else {

            Toast.makeText(
                    this,
                    "Failed to save ingredient",
                    Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

}
