package com.example.persistenciadedatos;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class RestaurantListAdapter extends ListAdapter<Restaurant, RestaurantListAdapter.RestaurantViewHolder> {
    private int currentPosition;

    private OnRestaurantAdapterItemClickListener restaurantAdapterItemClickListener;

    public RestaurantListAdapter(@NonNull DiffUtil.ItemCallback<Restaurant> diffCallback, OnRestaurantAdapterItemClickListener listener) {
        super(diffCallback);
        this.restaurantAdapterItemClickListener = listener;
    }

    @NonNull
    @Override
    public RestaurantListAdapter.RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_item, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantListAdapter.RestaurantViewHolder holder, int position) {
        Restaurant current = getItem(position);
        holder.restaurantItemView.setText(current.getName());
    }

    public Restaurant getSelectedRestaurant() {
        return getItem(currentPosition);
    }

    static class RestaurantDiff extends DiffUtil.ItemCallback<Restaurant> {

        @Override
        public boolean areItemsTheSame(@NonNull Restaurant oldItem, @NonNull Restaurant newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Restaurant oldItem, @NonNull Restaurant newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }

    class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public final TextView restaurantItemView;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantItemView = itemView.findViewById(R.id.restaurant);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            restaurantAdapterItemClickListener.onRestaurantAdapterItemClickListener(getAbsoluteAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            currentPosition = getAbsoluteAdapterPosition();
            return false;
        }
    }
}