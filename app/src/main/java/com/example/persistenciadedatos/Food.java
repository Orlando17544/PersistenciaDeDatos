package com.example.persistenciadedatos;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "food_table", foreignKeys = {@ForeignKey(entity = Restaurant.class,
        parentColumns = "id",
        childColumns = "restaurant_id",
        onDelete = ForeignKey.CASCADE)
})
public class Food {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private Integer id;

    @NonNull
    @ColumnInfo(name = "restaurant_id")
    private Integer restaurantId;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @ColumnInfo(name = "price")
    private double price;

    @NonNull
    @ColumnInfo(name = "description")
    private String description;

    @NonNull
    @ColumnInfo(name = "type")
    private String type;

    public Food(@NonNull String name, @NonNull double price, @NonNull String description, @NonNull String type) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.type = type;
    }

    public Integer getId() {
        return this.id;
    }

    public Integer getRestaurantId() {
        return this.restaurantId;
    }

    public String getName(){return this.name;}

    public double getPrice(){return this.price;}

    public String getDescription(){return this.description;}

    public String getType(){return this.type;}

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }
}