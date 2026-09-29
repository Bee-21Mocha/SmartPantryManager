package com.example.smartpantrymanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    private List<PantryItem> pantryList;

    public PantryAdapter(List<PantryItem> pantryList) {
        this.pantryList = pantryList;
    }

    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pantry, parent, false);

        return new PantryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PantryViewHolder holder, int position) {

        PantryItem item = pantryList.get(position);

        holder.txtIngredient.setText(item.getIngredient());

        holder.txtQuantity.setText(
                "Quantity: " + item.getQuantity() + " " + item.getUnit()
        );

        holder.txtExpiry.setText(
                "Expires: " + item.getExpiryDate()
        );
    }

    @Override
    public int getItemCount() {
        return pantryList.size();
    }

    public static class PantryViewHolder extends RecyclerView.ViewHolder {

        TextView txtIngredient;
        TextView txtQuantity;
        TextView txtExpiry;

        public PantryViewHolder(@NonNull View itemView) {
            super(itemView);

            txtIngredient = itemView.findViewById(R.id.txtIngredient);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            txtExpiry = itemView.findViewById(R.id.txtExpiry);
        }
    }
}