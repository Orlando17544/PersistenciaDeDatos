package com.example.persistenciadedatos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class RestaurantActivity extends AppCompatActivity {

    public static final String FOOD_ACTIVITY = "com.example.persistenciadedatos.FOOD_ACTIVITY";

    public static final String UPDATE_FOOD_ACTIVITY = "com.example.persistenciadedatos.UPDATE_FOOD_ACTIVITY";

    public static final String UPDATE_FOOD_ACTIVITY2 = "com.example.persistenciadedatos.UPDATE_FOOD_ACTIVITY2";

    public static final int NEW_FOOD_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_FOOD_ACTIVITY_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        Restaurant restaurant = getIntent().getParcelableExtra(MainActivity.RESTAURANT_ACTIVITY);

        ViewPager2 viewPager2 = findViewById(R.id.viewPager2);

        viewPager2.setAdapter(new FoodPagerAdapter(this, restaurant));

        TabLayout tabLayout = findViewById(R.id.tabLayout);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Comida");
                        break;
                    case 1:
                        tab.setText("Bebidas");
                        break;
                    case 2:
                        tab.setText("Complementos");
                        break;
                }
            }
        }
        );
        tabLayoutMediator.attach();
    }
}