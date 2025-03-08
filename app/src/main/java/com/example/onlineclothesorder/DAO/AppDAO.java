package com.example.onlineclothesorder.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.onlineclothesorder.Entity.Cart;
import com.example.onlineclothesorder.Entity.Order;
import com.example.onlineclothesorder.Entity.Product;

import java.util.List;

@Dao
public interface AppDAO {
    @Insert
    void insertProduct(Product product);

    @Query("SELECT * FROM products")
    List<Product> getAllProducts();

    @Insert
    void insertCart(Cart cart);
    @Delete
    void deleteCart(Cart cart);

    @Query("SELECT * FROM carts")
    List<Cart> getAllCarts();

    @Insert
    void insertOrder(Order order);

    @Query("SELECT * FROM orders")
    List<Order> getAllOrders();

    @Query("UPDATE orders SET status = :status WHERE orderId = :orderId")
    void updateOrderStatus(int orderId, String status);
}
