package com.example.persistenciadedatos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;

import java.io.File;

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


        if (food.getImagePath().contains("android.resource")) {
            foodTypeImageView.setImageURI(Uri.parse(food.getImagePath()));
        } else {
            foodTypeImageView.setImageURI(Uri.fromFile(new File(food.getImagePath())));
        }



        foodNameTextView.setText(food.getName());
        foodPriceTextView.setText(String.valueOf(food.getPrice()));
        foodDescriptionTextView.setText(food.getDescription());
    }
}