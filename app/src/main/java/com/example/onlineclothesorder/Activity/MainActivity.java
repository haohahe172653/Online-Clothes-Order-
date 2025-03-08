package com.example.onlineclothesorder.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineclothesorder.Adapter.ProductAdapter;
import com.example.onlineclothesorder.DAO.AppDAO;
import com.example.onlineclothesorder.DAO.AppDatabase;
import com.example.onlineclothesorder.DAO.DatabaseClient;
import com.example.onlineclothesorder.Entity.Product;
import com.example.onlineclothesorder.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private AppDatabase db;
    private AppDAO appDao;
    private RecyclerView recyclerViewProducts;
    private ProductAdapter productAdapter;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Thiết lập Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
        appDao = db.appDAO();

        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));
        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(this, productList);
        recyclerViewProducts.setAdapter(productAdapter);

        // Thiết lập FAB
        FloatingActionButton fabAddProduct = findViewById(R.id.fabAddProduct);
        fabAddProduct.setOnClickListener(v -> productAdapter.showProductDialog(null, -1));

        FloatingActionButton fabViewCart = findViewById(R.id.fabViewCart);
        fabViewCart.setOnClickListener(v -> startActivity(new Intent(this, CartActivity.class)));

        // Thêm logic cho FAB Auto Add Product
        FloatingActionButton fabAutoAddProduct = findViewById(R.id.fabAutoAddProduct);
        fabAutoAddProduct.setOnClickListener(v -> {
            new Thread(() -> {
                autoAddProducts();
                productList.clear();
                productList.addAll(appDao.getAllProducts());
                runOnUiThread(() -> {
                    productAdapter.updateData(productList);
                    Toast.makeText(MainActivity.this, "Products auto added", Toast.LENGTH_SHORT).show();
                });
            }).start();
        });
        loadProducts();
    }

    private void loadProducts() {
        new Thread(() -> {
            if (appDao.getAllProducts().isEmpty()) {
                autoAddProducts();
            }
            productList.clear();
            productList.addAll(appDao.getAllProducts());
            runOnUiThread(() -> productAdapter.updateData(productList));
        }).start();
    }

    private void autoAddProducts() {
        List<Product> sampleProducts = new ArrayList<>();
        sampleProducts.add(new Product("T-Shirt", 19.99f, 100, "image1.jpg"));
        sampleProducts.add(new Product("Jeans", 39.99f, 50, "image2.jpg"));
        sampleProducts.add(new Product("Jacket", 59.99f, 30, "image3.jpg"));
        sampleProducts.add(new Product("Shoes", 79.99f, 20, "image4.jpg"));
        sampleProducts.add(new Product("Hat", 14.99f, 80, "image5.jpg"));

        for (Product product : sampleProducts) {
            Product existingProduct = appDao.getProductByName(product.getName());
            if (existingProduct == null) {
                appDao.insertProduct(product);
            }
        }
    }
}