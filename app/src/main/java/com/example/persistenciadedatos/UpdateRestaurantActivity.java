package com.example.persistenciadedatos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class UpdateRestaurantActivity extends AppCompatActivity {
    private Restaurant restaurant;

    public static final String EXTRA_REPLY = "com.example.persistenciadedatos.REPLY";
    public static final String EXTRA_ACTION = "com.example.persistenciadedatos.ACTION";

    TextInputEditText restaurantNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_restaurant);

        restaurant = getIntent().getParcelableExtra(MainActivity.UPDATE_RESTAURANT_ACTIVITY);

        Button updateButton = findViewById(R.id.update_button);
        Button deleteButton = findViewById(R.id.delete_button);

        restaurantNameEditText = findViewById(R.id.restaurant_name_edit_text);

        restaurantNameEditText.setText(restaurant.getName());

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();

                if (TextUtils.isEmpty(restaurantNameEditText.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    Restaurant newRestaurant = new Restaurant(restaurantNameEditText.getText().toString());
                    newRestaurant.setId(restaurant.getId());
                    replyIntent.putExtra(EXTRA_REPLY, newRestaurant);
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

                replyIntent.putExtra(EXTRA_REPLY, restaurant);
                replyIntent.putExtra(EXTRA_ACTION, "delete");
                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });
    }
}