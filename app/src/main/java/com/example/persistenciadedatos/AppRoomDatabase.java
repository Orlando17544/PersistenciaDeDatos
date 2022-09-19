package com.example.persistenciadedatos;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Restaurant.class, Food.class}, version = 1, exportSchema = false)
public abstract class AppRoomDatabase extends RoomDatabase {
    public abstract RestaurantDao restaurantDao();
    public abstract FoodDao foodDao();

    private static volatile AppRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static RoomDatabase.Callback roomDatabaseCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                RestaurantDao restaurantDao = INSTANCE.restaurantDao();
                restaurantDao.deleteAll();

                FoodDao foodDao = INSTANCE.foodDao();
                foodDao.deleteAll();

                Restaurant restaurant;
                Food food;

                restaurant = new Restaurant("Casa Nostra");

                restaurantDao.insert(restaurant);

                food = new Food(1, "Mole", 50.50, "Rico mole", "food");
                foodDao.insert(food);

                food = new Food(1, "Pozole", 120.50, "Mejor pozole", "food");
                foodDao.insert(food);

                food = new Food(1, "Cochinita pibil", 30.50, "Deliciosa", "food");
                foodDao.insert(food);

                food = new Food(1, "Margarita", 49.50, "Contiene alcohol", "drink");
                foodDao.insert(food);

                food = new Food(1, "Mojito", 22.50, "Es muy barato", "drink");
                foodDao.insert(food);

                food = new Food(1, "Gintonic", 19.50, "Es con ginebra", "drink");
                foodDao.insert(food);

                food = new Food(1, "Tarta vianner", 22.50, "Contiene chocolate", "complement");
                foodDao.insert(food);

                food = new Food(1, "Gelato", 75.50, "Es de Italia", "complement");
                foodDao.insert(food);

                restaurant = new Restaurant("Zibu Allende");

                restaurantDao.insert(restaurant);

                food = new Food(2, "Chiles en nogada", 40.50, "Son de 1821", "food");
                foodDao.insert(food);

                food = new Food(2, "Barbacoa", 22.50, "Es prehispánica", "food");
                foodDao.insert(food);

                food = new Food(2, "Carnitas", 27.50, "Deliciosas", "food");
                foodDao.insert(food);

                food = new Food(2, "Caipirinha", 14.50, "Contiene cachaza", "drink");
                foodDao.insert(food);

                food = new Food(2, "Manhattan", 95.50, "Contiene whisky", "drink");
                foodDao.insert(food);

                food = new Food(2, "Piña colada", 19.50, "Contiene ron", "drink");
                foodDao.insert(food);

                food = new Food(2, "Tiramisu", 152.50, "Es un postre fino", "complement");
                foodDao.insert(food);

                food = new Food(2, "Panna cotta", 75.50, "Es italiana", "complement");
                foodDao.insert(food);

                restaurant = new Restaurant("ZUMO");

                restaurantDao.insert(restaurant);

                food = new Food(3, "Pescado a la talla", 42.50, "Contiene omega-3", "food");
                foodDao.insert(food);

                food = new Food(3, "Pescado a la veracruzana", 123.50, "Es de Veracruz", "food");
                foodDao.insert(food);

                food = new Food(3, "Tlayudas", 49.50, "Deliciosas", "food");
                foodDao.insert(food);

                food = new Food(3, "Daiquiri", 12.50, "Es con ron blanco", "drink");
                foodDao.insert(food);

                food = new Food(3, "Cosmopolitan", 105.50, "Contiene vodka", "drink");
                foodDao.insert(food);

                food = new Food(3, "Martini", 12.50, "Contiene ginebra", "drink");
                foodDao.insert(food);

                food = new Food(3, "Pasteis de Belem", 200.50, "Es un postre fino", "complement");
                foodDao.insert(food);

                food = new Food(3, "Pavlola", 175.50, "Es de Nueva Zelanda", "complement");
                foodDao.insert(food);
            });
        }
    };

    static AppRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppRoomDatabase.class, "app_database")
                            .addCallback(roomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}