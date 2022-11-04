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

                food = new Food(1, "Mole", 50.50, "Rico mole", "food", "android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.mole);
                foodDao.insert(food);

                food = new Food(1, "Pozole", 120.50, "Mejor pozole", "food", "android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.pozole);
                foodDao.insert(food);

                food = new Food(1, "Cochinita pibil", 30.50, "Deliciosa", "food", "android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.cochinita_pibil);
                foodDao.insert(food);

                food = new Food(1, "Margarita", 49.50, "Contiene alcohol", "drink", "android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.margarita);
                foodDao.insert(food);

                food = new Food(1, "Mojito", 22.50, "Es muy barato", "drink", "android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.mojito);
                foodDao.insert(food);

                food = new Food(1, "Gintonic", 19.50, "Es con ginebra", "drink", "android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.gintonic);
                foodDao.insert(food);

                food = new Food(1, "Tarta vianner", 22.50, "Contiene chocolate", "complement", "android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.tarta_vianner);
                foodDao.insert(food);

                food = new Food(1, "Gelato", 75.50, "Es de Italia", "complement", "android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.gelato);
                foodDao.insert(food);

                restaurant = new Restaurant("Zibu Allende");

                restaurantDao.insert(restaurant);

                food = new Food(2, "Chiles en nogada", 40.50, "Son de 1821", "food", "android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.chiles_en_nogada);
                foodDao.insert(food);

                food = new Food(2, "Barbacoa", 22.50, "Es prehispánica", "food", "android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.barbacoa);
                foodDao.insert(food);

                food = new Food(2, "Carnitas", 27.50, "Deliciosas", "food", "android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.carnitas);
                foodDao.insert(food);

                food = new Food(2, "Caipirinha", 14.50, "Contiene cachaza", "drink", "android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.caipirinha);
                foodDao.insert(food);

                food = new Food(2, "Manhattan", 95.50, "Contiene whisky", "drink", "android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.manhattan);
                foodDao.insert(food);

                food = new Food(2, "Piña colada", 19.50, "Contiene ron", "drink", "android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.pina_colada);
                foodDao.insert(food);

                food = new Food(2, "Tiramisu", 152.50, "Es un postre fino", "complement", "android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.tiramisu);
                foodDao.insert(food);

                food = new Food(2, "Panna cotta", 75.50, "Es italiana", "complement", "android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.panna_cotta);
                foodDao.insert(food);

                restaurant = new Restaurant("ZUMO");

                restaurantDao.insert(restaurant);

                food = new Food(3, "Pescado a la talla", 42.50, "Contiene omega-3", "food", "android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.pescado_a_la_talla);
                foodDao.insert(food);

                food = new Food(3, "Pescado a la veracruzana", 123.50, "Es de Veracruz", "food", "android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.pescado_a_la_veracruzana);
                foodDao.insert(food);

                food = new Food(3, "Tlayudas", 49.50, "Deliciosas", "food", "android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.tlayudas);
                foodDao.insert(food);

                food = new Food(3, "Daiquiri", 12.50, "Es con ron blanco", "drink", "android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.daiquiri);
                foodDao.insert(food);

                food = new Food(3, "Cosmopolitan", 105.50, "Contiene vodka", "drink", "android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.cosmopolitan);
                foodDao.insert(food);

                food = new Food(3, "Martini", 12.50, "Contiene ginebra", "drink", "android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.martini);
                foodDao.insert(food);

                food = new Food(3, "Pasteis de Belem", 200.50, "Es un postre fino", "complement", "android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.pasteis_de_belem);
                foodDao.insert(food);

                food = new Food(3, "Pavlola", 175.50, "Es de Nueva Zelanda", "complement", "android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.pavlola);
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