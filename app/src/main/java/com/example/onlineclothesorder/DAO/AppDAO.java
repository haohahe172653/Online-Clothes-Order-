package com.example.onlineclothesorder.DAO;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.onlineclothesorder.Entity.Banner;
import com.example.onlineclothesorder.Entity.Cart;
import com.example.onlineclothesorder.Entity.Category;
import com.example.onlineclothesorder.Entity.Order;
import com.example.onlineclothesorder.Entity.Product;


import java.util.List;


@Dao
public interface AppDAO {
    // Product CRUD
    @Insert
    void insertProduct(Product product);


    @Query("SELECT * FROM products")
    List<Product> getAllProducts();
    @Insert
    void insertProducts(List<Product> products);
    @Update
    void updateProduct(Product product);


    @Delete
    void deleteProduct(Product product);


    @Query("DELETE FROM products")
    void deleteAllProducts();


    @Query("SELECT * FROM products WHERE name = :name LIMIT 1")
    Product getProductByName(String name);


    //Category CRUD
    @Query("SELECT * FROM categories")
    List<Category> getAllCategories();
    @Insert
    void insertCategories(List<Category> categories);
    @Insert
    void insertCategory(Category category);


    @Update
    void updateCategory(Category category);


    @Delete
    void deleteCategory(Category category);


    // Cart CRUD
    @Insert
    void insertCart(Cart cart);


    @Query("SELECT * FROM carts")
    List<Cart> getAllCarts();


    @Update
    void updateCart(Cart cart);


    @Delete
    void deleteCart(Cart cart);


    // Order CRUD
    @Insert
    void insertOrder(Order order);


    @Query("SELECT * FROM orders")
    List<Order> getAllOrders();


    @Update
    void updateOrder(Order order);


    @Delete
    void deleteOrder(Order order);


    @Query("UPDATE orders SET status = :status WHERE orderId = :orderId")
    void updateOrderStatus(int orderId, String status);


    //Banner
    @Insert
    void insertBanner(Banner banner);


    @Insert
    void insertBanners(List<Banner> banners);


    @Query("SELECT * FROM banners")
    List<Banner> getAllBanners();
}

