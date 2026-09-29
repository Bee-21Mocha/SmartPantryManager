package com.example.smartpantrymanager;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecipeAdapter
        extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> recipeList;

    public RecipeAdapter(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipeitem, parent, false);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull RecipeViewHolder holder,
            int position) {

        Recipe recipe = recipeList.get(position);


        holder.txtRecipeName.setText(
                recipe.getRecipeName()
        );


        holder.txtIngredients.setText(
                recipe.getIngredients()
        );


        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(
                    v.getContext(),
                    RecipeDetails.class
            );

            intent.putExtra(
                    "recipeName",
                    recipe.getRecipeName()
            );

            intent.putExtra(
                    "ingredients",
                    recipe.getIngredients()
            );

            intent.putExtra(
                    "instructions",
                    recipe.getInstructions()
            );

            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }


    public static class RecipeViewHolder
            extends RecyclerView.ViewHolder {

        TextView txtRecipeName;
        TextView txtIngredients;

        public RecipeViewHolder(
                @NonNull View itemView) {

            super(itemView);

            txtRecipeName = itemView.findViewById(
                    R.id.txtRecipeName
            );

            txtIngredients = itemView.findViewById(
                    R.id.txtIngredients
            );
        }
    }
}