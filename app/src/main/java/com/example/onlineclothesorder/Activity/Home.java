package com.example.onlineclothesorder.Activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.onlineclothesorder.Adapter.CategoryAdapter;
import com.example.onlineclothesorder.Adapter.ProductAdapter;
import com.example.onlineclothesorder.DAO.AppDatabase;
import com.example.onlineclothesorder.Entity.Category;
import com.example.onlineclothesorder.Entity.Product;
import com.example.onlineclothesorder.R;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Home extends AppCompatActivity {
    private RecyclerView categoryRecyclerView, productRecyclerView;
    private CategoryAdapter categoryAdapter;
    private ProductAdapter productAdapter;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        database = AppDatabase.getInstance(this);

        categoryRecyclerView = findViewById(R.id.categoryRecyclerView);
        productRecyclerView = findViewById(R.id.productRecyclerView);

        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        productRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // Chạy truy vấn trong luồng riêng
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            List<Category> categories = database.categoryDao().getAllCategories();
            List<Product> products = database.productDao().getAllProducts();

            runOnUiThread(() -> {
                categoryAdapter = new CategoryAdapter(categories);
                productAdapter = new ProductAdapter(products);

                categoryRecyclerView.setAdapter(categoryAdapter);
                productRecyclerView.setAdapter(productAdapter);
            });
        });
    }
}
