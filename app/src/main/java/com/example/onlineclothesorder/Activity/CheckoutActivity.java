package com.example.onlineclothesorder.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onlineclothesorder.DAO.AppDAO;
import com.example.onlineclothesorder.DAO.AppDatabase;
import com.example.onlineclothesorder.DAO.DatabaseClient;
import com.example.onlineclothesorder.Entity.Cart;
import com.example.onlineclothesorder.Entity.Order;
import com.example.onlineclothesorder.Entity.Product;
import com.example.onlineclothesorder.R;

import java.util.List;

public class CheckoutActivity extends AppCompatActivity {
    private AppDatabase db;
    private AppDAO appDao;
    private TextView tvCheckoutItems;
    private EditText etPaymentMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        db = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
        appDao = db.appDAO();

        tvCheckoutItems = findViewById(R.id.tvCheckoutItems);
        etPaymentMethod = findViewById(R.id.etPaymentMethod);
        Button btnSubmitOrder = findViewById(R.id.btnSubmitOrder);

        float total = calculateTotal(); // Tính tổng tiền trong chính Activity này
        tvCheckoutItems.setText(String.format("Items: Total $%.2f", total));

        btnSubmitOrder.setOnClickListener(v -> {
            String paymentMethod = etPaymentMethod.getText().toString().trim();
            if (paymentMethod.isEmpty()) {
                Toast.makeText(this, "Enter payment method", Toast.LENGTH_SHORT).show();
                return;
            }
            new Thread(() -> {
                Order order = new Order("2025-03-08", total, "Pending");
                appDao.insertOrder(order);
                runOnUiThread(() -> {
                    Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, OrderConfirmedActivity.class));
                });
            }).start();
        });
    }

    private float calculateTotal() {
        List<Cart> cartList = appDao.getAllCarts();
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
}