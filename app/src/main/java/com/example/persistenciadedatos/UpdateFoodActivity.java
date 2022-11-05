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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class UpdateFoodActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.persistenciadedatos.REPLY";
    public static final String EXTRA_ACTION = "com.example.persistenciadedatos.ACTION";

    public static final int GET_FROM_GALLERY = 3;

    TextInputEditText foodNameEditText;
    TextInputEditText foodPriceEditText;
    TextInputEditText foodDescriptionEditText;
    TextInputEditText foodTypeEditText;
    AutoCompleteTextView foodTypeAutoCompleteTextView;
    String imagePath;

    Restaurant restaurant;
    Food food;

    private static final String[] TYPES = new String[]{
            "Comida", "Bebida", "Complemento"
    };

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
        foodTypeAutoCompleteTextView = findViewById(R.id.food_type);

        foodNameEditText.setText(food.getName());
        foodPriceEditText.setText(String.valueOf(food.getPrice()));
        foodDescriptionEditText.setText(food.getDescription());

        if (food.getType().equals("food")) {
            foodTypeAutoCompleteTextView.setText("Comida");
        } else if (food.getType().equals("drink")) {
            foodTypeAutoCompleteTextView.setText("Bebida");
        } else if (food.getType().equals("complement")) {
            foodTypeAutoCompleteTextView.setText("Complemento");
        }

        imagePath = food.getImagePath();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, TYPES);
        foodTypeAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.food_type);
        foodTypeAutoCompleteTextView.setAdapter(adapter);

        ImageButton uploadImageButton = findViewById(R.id.upload_image_button);

        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();

                String foodType = "";

                if (foodTypeAutoCompleteTextView.getText().toString().equals("Comida")) {
                    foodType = "food";
                } else if (foodTypeAutoCompleteTextView.getText().toString().equals("Bebida")) {
                    foodType = "drink";
                } else if (foodTypeAutoCompleteTextView.getText().toString().equals("Complemento")) {
                    foodType = "complement";
                }

                if (TextUtils.isEmpty(foodNameEditText.getText()) || TextUtils.isEmpty(foodPriceEditText.getText()) || TextUtils.isEmpty(foodDescriptionEditText.getText()) || TextUtils.isEmpty(foodType) || TextUtils.isEmpty(imagePath)) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    Food newFood = new Food(restaurant.getId(), foodNameEditText.getText().toString(), Double.valueOf(foodPriceEditText.getText().toString()), foodDescriptionEditText.getText().toString(), foodType, imagePath);
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

            //Delete previous image
            deleteFromInternalStorage(imagePath);

            //Save the bitmap in internal storage and retrieve the path
            imagePath = saveToInternalStorage(bitmap);
        }
    }

    private void deleteFromInternalStorage(String imagePath) {
        File previousFile;
        if (imagePath.contains("android.resource")) {
            previousFile = new File(Uri.parse(food.getImagePath()).getPath());
        } else {
            previousFile = new File(imagePath);
        }

        previousFile.delete();
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