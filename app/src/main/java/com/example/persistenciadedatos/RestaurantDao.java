package com.example.persistenciadedatos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RestaurantDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Restaurant restaurant);

    @Update
    void update(Restaurant restaurant);

    @Delete
    void delete(Restaurant restaurant);

    @Query("DELETE FROM restaurant_table")
    void deleteAll();

    @Query("SELECT * FROM restaurant_table ORDER BY name ASC")
    LiveData<List<Restaurant>> getAlphabetizedRestaurants();
}