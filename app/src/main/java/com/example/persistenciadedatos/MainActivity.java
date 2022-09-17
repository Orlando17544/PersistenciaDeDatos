package com.example.persistenciadedatos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RestaurantListAdapter adapter;
    private final LinkedList<String> restaurantList = new LinkedList<>();
    private DataViewModel dataViewModel;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);

        topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.addRestaurant:
                        return true;
                }
                return false;
            }
        });

        for (int i = 0; i < 20; i++) {
            restaurantList.addLast("Word " + i);
        }

        // Get a handle to the RecyclerView.
        recyclerView = findViewById(R.id.recycler_view);
        // Create an adapter and supply the data to be displayed.
        adapter = new RestaurantListAdapter(this, restaurantList);
        // Connect the adapter with the RecyclerView.
        recyclerView.setAdapter(adapter);
        // Give the RecyclerView a default layout manager.
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);

        dataViewModel.getAllRestaurants().observe(this, restaurants -> {
            adapter.notifyDataSetChanged();
        });
    }
}