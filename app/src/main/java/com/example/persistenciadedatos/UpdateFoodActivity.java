package com.example.persistenciadedatos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class UpdateFoodActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.persistenciadedatos.REPLY";
    public static final String EXTRA_ACTION = "com.example.persistenciadedatos.ACTION";

    TextInputEditText foodNameEditText;
    TextInputEditText foodPriceEditText;
    TextInputEditText foodDescriptionEditText;
    TextInputEditText foodTypeEditText;

    Restaurant restaurant;
    Food food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_food);

        food = getIntent().getParcelableExtra(RestaurantActivity.UPDATE_FOOD_ACTIVITY);
        restaurant = getIntent().getParcelableExtra(RestaurantActivity.UPDATE_FOOD_ACTIVITY2);

        Button updateButton = findViewById(R.id.update_button);
        Button deleteButton = findViewById(R.id.delete_button);

        foodNameEditText = findViewById(R.id.food_name_edit_text);
        foodPriceEditText = findViewById(R.id.food_price_edit_text);
        foodDescriptionEditText = findViewById(R.id.food_description_edit_text);
        foodTypeEditText = findViewById(R.id.food_type_edit_text);

        foodNameEditText.setText(food.getName());
        foodPriceEditText.setText(String.valueOf(food.getPrice()));
        foodDescriptionEditText.setText(food.getDescription());
        foodTypeEditText.setText(food.getType());

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();

                if (TextUtils.isEmpty(foodNameEditText.getText()) || TextUtils.isEmpty(foodPriceEditText.getText()) || TextUtils.isEmpty(foodDescriptionEditText.getText()) || TextUtils.isEmpty(foodTypeEditText.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    Food newFood = new Food(restaurant.getId(), foodNameEditText.getText().toString(), Double.valueOf(foodPriceEditText.getText().toString()), foodDescriptionEditText.getText().toString(), foodTypeEditText.getText().toString());
                    newFood.setId(food.getId());
                    replyIntent.putExtra(EXTRA_REPLY, newFood);
                    replyIntent.putExtra(EXTRA_ACTION, "update");
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();

                replyIntent.putExtra(EXTRA_REPLY, food);
                replyIntent.putExtra(EXTRA_ACTION, "delete");
                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });
    }
}