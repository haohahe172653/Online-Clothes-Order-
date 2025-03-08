package com.example.onlineclothesorder.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineclothesorder.Adapter.ProductAdapter;
import com.example.onlineclothesorder.DAO.AppDAO;
import com.example.onlineclothesorder.DAO.AppDatabase;
import com.example.onlineclothesorder.DAO.DatabaseClient;
import com.example.onlineclothesorder.Entity.Product;
import com.example.onlineclothesorder.R;

import java.util.ArrayList;
import java.util.List;

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

        db = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
        appDao = db.appDAO();

        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));
        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(this, productList); // Truyền this
        recyclerViewProducts.setAdapter(productAdapter);

        Button btnViewCart = findViewById(R.id.btnViewCart);
        btnViewCart.setOnClickListener(v -> {
            startActivity(new Intent(this, CartActivity.class));
        });

        loadProducts();
    }

    private void loadProducts() {
        new Thread(() -> {
            productList.clear();
            if (appDao.getAllProducts().isEmpty()) {
                appDao.insertProduct(new Product("T-Shirt", 19.99f, 100, "image1.jpg"));
                appDao.insertProduct(new Product("Jeans", 39.99f, 50, "image2.jpg"));
            }
            productList.addAll(appDao.getAllProducts());
            runOnUiThread(() -> productAdapter.updateData(productList));
        }).start();
    }
}