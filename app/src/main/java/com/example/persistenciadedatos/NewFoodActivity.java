package com.example.persistenciadedatos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

public class NewFoodActivity extends AppCompatActivity {

    private Integer restaurantId;

    public static final String EXTRA_REPLY = "com.example.persistenciadedatos.REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_food);

        restaurantId = getIntent().getIntExtra("restaurantId", 0);

        Button registerFood = findViewById(R.id.register_button);

        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);

        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        registerFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputEditText foodNameEditText = findViewById(R.id.food_name_edit_text);
                TextInputEditText foodPriceEditText = findViewById(R.id.food_price_edit_text);
                TextInputEditText foodDescriptionEditText = findViewById(R.id.food_description_edit_text);
                TextInputEditText foodTypeEditText = findViewById(R.id.food_type_edit_text);

                Intent replyIntent = new Intent();

                if (TextUtils.isEmpty(foodNameEditText.getText()) || TextUtils.isEmpty(foodPriceEditText.getText()) || TextUtils.isEmpty(foodDescriptionEditText.getText()) || TextUtils.isEmpty(foodTypeEditText.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    Food food = new Food(restaurantId, foodNameEditText.getText().toString(), Double.parseDouble(foodPriceEditText.getText().toString()), foodDescriptionEditText.getText().toString(), foodTypeEditText.getText().toString());
                    replyIntent.putExtra(EXTRA_REPLY, food);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}