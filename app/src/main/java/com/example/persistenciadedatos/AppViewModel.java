package com.example.persistenciadedatos;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class AppViewModel extends AndroidViewModel {
    private DataRepository repository;

    private final LiveData<List<Restaurant>> allRestaurants;
    private final LiveData<List<Food>> allFoods;

    public AppViewModel (Application application) {
        super(application);
        repository = new DataRepository(application);
        allRestaurants = repository.getAllRestaurants();
        allFoods = repository.getAllFoods();
    }

    LiveData<List<Restaurant>> getAllRestaurants() { return allRestaurants; }

    LiveData<List<Food>> getAllFoods() { return allFoods; }

    public void insertRestaurant(Restaurant restaurant) { repository.insertRestaurant(restaurant); }

    public void insertFood(Food food) { repository.insertFood(food);}
}
