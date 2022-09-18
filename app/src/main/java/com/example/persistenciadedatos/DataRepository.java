package com.example.persistenciadedatos;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class DataRepository {
    private RestaurantDao restaurantDao;
    private LiveData<List<Restaurant>> allRestaurants;

    private FoodDao foodDao;
    private LiveData<List<Food>> allFoods;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    DataRepository(Application application) {
        AppRoomDatabase db = AppRoomDatabase.getDatabase(application);
        restaurantDao = db.restaurantDao();
        allRestaurants = restaurantDao.getAlphabetizedRestaurants();

        foodDao = db.foodDao();
        allFoods = foodDao.getAlphabetizedFoods();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Restaurant>> getAllRestaurants() {
        return allRestaurants;
    }

    LiveData<List<Food>> getAllFoods() {
        return allFoods;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insertRestaurant(Restaurant restaurant) {
        AppRoomDatabase.databaseWriteExecutor.execute(() -> {
            restaurantDao.insert(restaurant);
        });
    }

    void updateRestaurant(Restaurant restaurant) {
        AppRoomDatabase.databaseWriteExecutor.execute(() -> {
            restaurantDao.update(restaurant);
        });
    }

    void deleteRestaurant(Restaurant restaurant) {
        AppRoomDatabase.databaseWriteExecutor.execute(() -> {
            restaurantDao.delete(restaurant);
        });
    }

    void insertFood(Food food) {
        AppRoomDatabase.databaseWriteExecutor.execute(() -> {
            foodDao.insert(food);
        });
    }
}