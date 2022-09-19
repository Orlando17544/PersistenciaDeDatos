package com.example.persistenciadedatos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements OnRestaurantAdapterItemClickListener {
    private RestaurantListAdapter adapter;
    private DataViewModel dataViewModel;

    public static final int NEW_RESTAURANT_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_RESTAURANT_ACTIVITY_REQUEST_CODE = 2;

    public static final String UPDATE_RESTAURANT_ACTIVITY = "com.example.persistenciadedatos.UPDATE_RESTAURANT_ACTIVITY";

    public static final String RESTAURANT_ACTIVITY = "com.example.persistenciadedatos.RESTAURANT_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);

        topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.add:
                        Intent intent = new Intent(MainActivity.this, NewRestaurantActivity.class);
                        startActivityForResult(intent, NEW_RESTAURANT_ACTIVITY_REQUEST_CODE);
                        return true;
                }
                return false;
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        adapter = new RestaurantListAdapter(new RestaurantListAdapter.RestaurantDiff(), this::onRestaurantAdapterItemClickListener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);

        dataViewModel.getAllRestaurants().observe(this, restaurants -> {
            adapter.submitList(restaurants);
            System.out.println(restaurants + "Restaurantes");
        });

        registerForContextMenu(recyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_RESTAURANT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Restaurant restaurant = data.getParcelableExtra(NewRestaurantActivity.EXTRA_REPLY);
            dataViewModel.insertRestaurant(restaurant);
        } else if (requestCode == UPDATE_RESTAURANT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data.getStringExtra(UpdateRestaurantActivity.EXTRA_ACTION).equals("update")) {
                Restaurant restaurant = data.getParcelableExtra(UpdateRestaurantActivity.EXTRA_REPLY);
                dataViewModel.updateRestaurant(restaurant);
            } else if (data.getStringExtra(UpdateRestaurantActivity.EXTRA_ACTION).equals("delete")) {
                Restaurant restaurant = data.getParcelableExtra(UpdateRestaurantActivity.EXTRA_REPLY);
                dataViewModel.deleteRestaurant(restaurant);
            }
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "No se pudo completar la operación",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(MainActivity.this, UpdateRestaurantActivity.class);
        intent.putExtra(UPDATE_RESTAURANT_ACTIVITY, adapter.getSelectedRestaurant());
        startActivityForResult(intent, UPDATE_RESTAURANT_ACTIVITY_REQUEST_CODE);
        return super.onContextItemSelected(item);
    }


    @Override
    public void onRestaurantAdapterItemClickListener(int position) {
        Intent intent = new Intent(MainActivity.this, RestaurantActivity.class);
        intent.putExtra(RESTAURANT_ACTIVITY, adapter.getCurrentList().get(position));
        startActivity(intent);
    }
}