package com.example.onlineclothesorder.DAO;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.onlineclothesorder.Entity.Cart;
import com.example.onlineclothesorder.Entity.Order;
import com.example.onlineclothesorder.Entity.Product;

@Database(entities = {Product.class, Cart.class, Order.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AppDAO appDAO();
}
