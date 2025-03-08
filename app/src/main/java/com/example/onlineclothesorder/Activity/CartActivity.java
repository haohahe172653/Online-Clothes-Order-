package com.example.onlineclothesorder.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineclothesorder.Adapter.CartAdapter;
import com.example.onlineclothesorder.DAO.AppDAO;
import com.example.onlineclothesorder.DAO.AppDatabase;
import com.example.onlineclothesorder.DAO.DatabaseClient;
import com.example.onlineclothesorder.Entity.Cart;
import com.example.onlineclothesorder.Entity.Product;
import com.example.onlineclothesorder.R;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private AppDatabase db;
    private AppDAO appDao;
    private RecyclerView recyclerViewCart;
    private CartAdapter cartAdapter;
    private List<Cart> cartList;
    private TextView tvTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        db = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
        appDao = db.appDAO();

        recyclerViewCart = findViewById(R.id.recyclerViewCart);
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));
        cartList = new ArrayList<>();
        cartAdapter = new CartAdapter(this, cartList);
        recyclerViewCart.setAdapter(cartAdapter);

        tvTotal = findViewById(R.id.tvTotal);
        Button btnProceedCheckout = findViewById(R.id.btnProceedCheckout);
        btnProceedCheckout.setOnClickListener(v -> startActivity(new Intent(this, CheckoutActivity.class)));

        loadCart();
    }

    public void loadCart() {
        new Thread(() -> {
            cartList.clear();
            cartList.addAll(appDao.getAllCarts());
            float total = calculateTotal();
            runOnUiThread(() -> {
                cartAdapter.updateData(cartList);
                tvTotal.setText(String.format("Total: $%.2f", total));
            });
        }).start();
    }

    protected float calculateTotal() {
        float total = 0;
        for (Cart cart : cartList) {
            Product product = appDao.getAllProducts().stream()
                    .filter(p -> p.getProductId() == cart.getProductId())
                    .findFirst().orElse(null);
            if (product != null) {
                total += product.getPrice() * cart.getQuantity();
            }
        }
        return total;
    }

    @Override
    protected void onResume() {

        super.onResume();
        loadCart();
    }
}

