package com.example.persistenciadedatos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;

public class FoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        Food food = getIntent().getParcelableExtra(RestaurantActivity.FOOD_ACTIVITY);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);

        toolbar.setTitle(food.getName());

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ImageView foodTypeImageView = findViewById(R.id.food_type_image_view);
        TextView foodNameTextView = findViewById(R.id.food_name_text_view);
        TextView foodPriceTextView = findViewById(R.id.food_price_text_view);
        TextView foodDescriptionTextView = findViewById(R.id.food_description_text_view);

        if (food.getType().equals("food")) {
            foodTypeImageView.setImageResource(R.drawable.food);
        } else if (food.getType().equals("drink")) {
            foodTypeImageView.setImageResource(R.drawable.drink);
        } else if (food.getType().equals("complement")) {
            foodTypeImageView.setImageResource(R.drawable.complement);
        }

        foodNameTextView.setText(food.getName());
        foodPriceTextView.setText(String.valueOf(food.getPrice()));
        foodDescriptionTextView.setText(food.getDescription());
    }
}