package com.example.persistenciadedatos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class RestaurantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        Restaurant restaurant = getIntent().getParcelableExtra(MainActivity.RESTAURANT_ACTIVITY);

        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);

        topAppBar.setTitle(restaurant.getName());

        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.add:
                        /*ItemMenu[] itemsMenu = restaurant.getMenu().getItems();

                        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                        intent.putExtra("com.example.restaurantes.CALLING_ACTIVITY", "RestaurantActivity");
                        intent.putExtra(EXTRA_ITEMS_MENU, itemsMenu);
                        startActivity(intent);
                        return true;*/
                }
                return false;
            }
        });

        ViewPager2 viewPager2 = findViewById(R.id.viewPager2);

        viewPager2.setAdapter(new FoodPagerAdapter(this, restaurant.getId()));

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