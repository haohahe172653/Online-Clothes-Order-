package com.example.onlineclothesorder.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineclothesorder.DAO.AppDAO;
import com.example.onlineclothesorder.DAO.AppDatabase;
import com.example.onlineclothesorder.DAO.DatabaseClient;
import com.example.onlineclothesorder.Entity.Cart;
import com.example.onlineclothesorder.Entity.Product;
import com.example.onlineclothesorder.Holder.ProductViewHolder;
import com.example.onlineclothesorder.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {
    private List<Product> products;
    private AppDatabase db;
    private AppDAO appDao;
    private Activity activity; // Thay Context bằng Activity

    public ProductAdapter(Activity activity, List<Product> products) {
        this.activity = activity;
        this.products = products;
        this.db = DatabaseClient.getInstance(activity.getApplicationContext()).getAppDatabase();
        this.appDao = db.appDAO();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.tvProductName.setText(product.getName());
        holder.tvProductPrice.setText(String.format("$%.2f", product.getPrice()));
        holder.btnAddToCart.setOnClickListener(v -> {
            new Thread(() -> {
                appDao.insertCart(new Cart(product.getProductId(), 1));
                activity.runOnUiThread(() -> Toast.makeText(activity, "Added to Cart", Toast.LENGTH_SHORT).show());
            }).start();
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void updateData(List<Product> newProducts) {
        this.products.clear();
        this.products.addAll(newProducts);
        notifyDataSetChanged();
    }
}