package com.example.onlineclothesorder.DAO;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.onlineclothesorder.Entity.Cart;
import com.example.onlineclothesorder.Entity.Category;
import com.example.onlineclothesorder.Entity.Order;
import com.example.onlineclothesorder.Entity.Product;

@Database(entities = {Product.class, Cart.class, Order.class, Category.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;
    public abstract AppDAO appDAO();
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "shopping_db")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
