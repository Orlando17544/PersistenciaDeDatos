package com.example.persistenciadedatos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.RestaurantViewHolder> {
    private final LinkedList<String> restaurantList;
    private LayoutInflater inflater;

    public RestaurantListAdapter(Context context, LinkedList<String> restaurantList) {
        this.inflater = LayoutInflater.from(context);
        this.restaurantList = restaurantList;
    }

    @NonNull
    @Override
    public RestaurantListAdapter.RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.restaurant_item,
                parent, false);
        return new RestaurantViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantListAdapter.RestaurantViewHolder holder, int position) {
        String current = restaurantList.get(position);
        holder.restaurantItemView.setText(current);
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView restaurantItemView;
        final RestaurantListAdapter adapter;

        public RestaurantViewHolder(@NonNull View itemView, RestaurantListAdapter adapter) {
            super(itemView);
            restaurantItemView = itemView.findViewById(R.id.restaurant);
            this.adapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();

            String element = restaurantList.get(position);
            restaurantList.set(position, "Clicked! " + element);

            adapter.notifyDataSetChanged();
        }
    }
}