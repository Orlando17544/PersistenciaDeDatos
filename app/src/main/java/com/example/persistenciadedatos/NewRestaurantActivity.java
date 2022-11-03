package com.example.persistenciadedatos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

public class NewRestaurantActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.persistenciadedatos.REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_restaurant);

        Button registerButton = findViewById(R.id.register_button);

        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);

        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputEditText restaurantNameEditText = findViewById(R.id.restaurant_name_edit_text);

                Intent replyIntent = new Intent();

                if (TextUtils.isEmpty(restaurantNameEditText.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    Restaurant restaurant = new Restaurant(restaurantNameEditText.getText().toString());
                    replyIntent.putExtra(EXTRA_REPLY, restaurant);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}