package com.example.persistenciadedatos;

import android.os.Parcel;
import android.os.Parcelable;

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
public class Food implements Parcelable {

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

    @NonNull
    @ColumnInfo(name = "imagePath")
    private String imagePath;

    public Food(@NonNull Integer restaurantId, @NonNull String name, @NonNull double price, @NonNull String description, @NonNull String type, @NonNull String imagePath) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.type = type;
        this.imagePath = imagePath;
    }

    protected Food(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        if (in.readByte() == 0) {
            restaurantId = null;
        } else {
            restaurantId = in.readInt();
        }
        name = in.readString();
        price = in.readDouble();
        description = in.readString();
        type = in.readString();
        imagePath = in.readString();
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

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

    public String getImagePath() {return this.imagePath;}

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        if (restaurantId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(restaurantId);
        }
        parcel.writeString(name);
        parcel.writeDouble(price);
        parcel.writeString(description);
        parcel.writeString(type);
        parcel.writeString(imagePath);
    }
}