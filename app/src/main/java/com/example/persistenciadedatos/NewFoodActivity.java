package com.example.persistenciadedatos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class NewFoodActivity extends AppCompatActivity {

    public static final int GET_FROM_GALLERY = 3;

    private Integer restaurantId;

    public static final String EXTRA_REPLY = "com.example.persistenciadedatos.REPLY";

    private static final String[] TYPES = new String[]{
            "Comida", "Bebida", "Complemento"
    };

    TextInputEditText foodNameEditText;
    TextInputEditText foodPriceEditText;
    TextInputEditText foodDescriptionEditText;
    AutoCompleteTextView foodTypeAutoCompleteTextView;
    String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_food);

        restaurantId = getIntent().getIntExtra("restaurantId", 0);

        foodNameEditText = findViewById(R.id.food_name_edit_text);
        foodPriceEditText = findViewById(R.id.food_price_edit_text);
        foodDescriptionEditText = findViewById(R.id.food_description_edit_text);
        foodTypeAutoCompleteTextView = findViewById(R.id.food_type);

        Button registerFood = findViewById(R.id.register_button);

        ImageButton uploadImageButton = findViewById(R.id.upload_image_button);

        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
        });

        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);

        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, TYPES);
        foodTypeAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.food_type);
        foodTypeAutoCompleteTextView.setAdapter(adapter);

        registerFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String foodType = "";

                if (foodTypeAutoCompleteTextView.getText().toString().equals("Comida")) {
                    foodType = "food";
                } else if (foodTypeAutoCompleteTextView.getText().toString().equals("Bebida")) {
                    foodType = "drink";
                } else if (foodTypeAutoCompleteTextView.getText().toString().equals("Complemento")) {
                    foodType = "complement";
                }

                Intent replyIntent = new Intent();

                if (TextUtils.isEmpty(foodNameEditText.getText()) || TextUtils.isEmpty(foodPriceEditText.getText()) || TextUtils.isEmpty(foodDescriptionEditText.getText()) || TextUtils.isEmpty(foodType) || TextUtils.isEmpty(imagePath)) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    Food food = new Food(restaurantId, foodNameEditText.getText().toString(), Double.parseDouble(foodPriceEditText.getText().toString()), foodDescriptionEditText.getText().toString(), foodType, imagePath);
                    replyIntent.putExtra(EXTRA_REPLY, food);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Detects request codes
        if (requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();

            //Convert to bitmap
            Bitmap bitmap = null;

            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Save the bitmap in internal storage and retrieve the path
            imagePath = saveToInternalStorage(bitmap);
        }
    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imagesDirectory
        File directory = cw.getDir("imagesDirectory", Context.MODE_PRIVATE);
        // Create imageDir
        if (foodNameEditText == null) {
            return null;
        }

        String fileName = foodNameEditText.getText().toString().toLowerCase().replace(" ", "_");
        File mypath = new File(directory,fileName + ".png");

        //Create the image
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath() + "/" + fileName + ".png";
    }
}