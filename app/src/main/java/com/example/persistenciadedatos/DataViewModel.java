package com.example.persistenciadedatos;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class DataViewModel extends AndroidViewModel {
    private DataRepository repository;

    private final LiveData<List<Restaurant>> allRestaurants;
    private final LiveData<List<Food>> allFoods;

    public DataViewModel(@NonNull Application application) {
        super(application);
        this.repository = new DataRepository(application);

        this.allRestaurants = repository.getAllRestaurants();
        this.allFoods = repository.getAllFoods();
    }

    LiveData<List<Restaurant>> getAllRestaurants() { return allRestaurants; }
    public void insertRestaurant(Restaurant restaurant) { repository.insertRestaurant(restaurant); }

    public void updateRestaurant(Restaurant restaurant) {
        repository.updateRestaurant(restaurant);
    }

    public void deleteRestaurant(Restaurant restaurant) {
        repository.deleteRestaurant(restaurant);
    }

    LiveData<List<Food>> getAllFoods() { return allFoods; }
    public void insertFood(Food food) { repository.insertFood(food); }

    LiveData<List<Food>> getFoods(Integer restaurantId, String type) {return repository.getFoods(restaurantId, type);}
}
