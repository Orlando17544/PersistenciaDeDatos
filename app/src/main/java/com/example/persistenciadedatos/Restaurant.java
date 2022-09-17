package com.example.persistenciadedatos;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "restaurant_table")
public class Restaurant {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private Integer id;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    public Restaurant(@NonNull String name) {this.name = name;}

    public Integer getId() {
        return this.id;
    }

    public String getName(){return this.name;}

    public void setId(Integer id) {
        this.id = id;
    }
}