package com.example.persistenciadedatos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class FoodListAdapter extends ListAdapter<Food, FoodListAdapter.FoodViewHolder> {
    private int currentPosition;

    private OnFoodAdapterItemClickListener foodAdapterItemClickListener;

    public FoodListAdapter(@NonNull DiffUtil.ItemCallback<Food> diffCallback, OnFoodAdapterItemClickListener listener) {
        super(diffCallback);
        this.foodAdapterItemClickListener = listener;
    }

    @NonNull
    @Override
    public FoodListAdapter.FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_item, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodListAdapter.FoodViewHolder holder, int position) {
        Food current = getItem(position);
        TextView foodName = holder.foodItemView.findViewById(R.id.food_name);
        foodName.setText(current.getName());
    }

    public Food getSelectedFood() {
        return getItem(currentPosition);
    }

    static class FoodDiff extends DiffUtil.ItemCallback<Food> {

        @Override
        public boolean areItemsTheSame(@NonNull Food oldItem, @NonNull Food newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Food oldItem, @NonNull Food newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }

    class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public final View foodItemView;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            foodItemView = itemView.findViewById(R.id.food);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            foodAdapterItemClickListener.onFoodAdapterItemClickListener(getAbsoluteAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            currentPosition = getAbsoluteAdapterPosition();
            return false;
        }
    }
}