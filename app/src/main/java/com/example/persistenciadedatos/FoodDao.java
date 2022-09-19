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
public interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Food food);

    @Update
    void update(Food food);

    @Delete
    void delete(Food food);

    @Query("DELETE FROM food_table")
    void deleteAll();

    @Query("SELECT * FROM food_table ORDER BY name ASC")
    LiveData<List<Food>> getAlphabetizedFoods();

    @Query("SELECT * FROM food_table WHERE restaurant_id = :restaurantId AND type = :type")
    LiveData<List<Food>> getFoods(Integer restaurantId, String type);
}