package com.example.onlineclothesorder.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onlineclothesorder.DAO.AppDAO;
import com.example.onlineclothesorder.DAO.AppDatabase;
import com.example.onlineclothesorder.DAO.DatabaseClient;
import com.example.onlineclothesorder.Entity.Order;
import com.example.onlineclothesorder.R;

public class OrderConfirmedActivity extends AppCompatActivity {
    private AppDatabase db;
    private AppDAO appDao;
    private TextView tvOrderDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmed);

        db = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
        appDao = db.appDAO();

        tvOrderDetails = findViewById(R.id.tvOrderDetails);
        Button btnTrackOrder = findViewById(R.id.btnTrackOrder);

        // Mô phỏng order cuối cùng
        Order order = appDao.getAllOrders().get(appDao.getAllOrders().size() - 1);
        tvOrderDetails.setText(String.format("Order ID: %d, Total: $%.2f, Status: %s",
                order.getOrderId(), order.getTotalAmount(), order.getStatus()));

        // Mô phỏng cập nhật trạng thái
        new Handler().postDelayed(() -> {
            new Thread(() -> {
                appDao.updateOrderStatus(order.getOrderId(), "Paid");
                runOnUiThread(() -> {
                    Toast.makeText(this, "Order Paid", Toast.LENGTH_SHORT).show();
                    // Mô phỏng email
                    Toast.makeText(this, "Order receipt sent to email", Toast.LENGTH_SHORT).show();
                });
            }).start();
        }, 2000); // Delay 2 giây

        new Handler().postDelayed(() -> {
            new Thread(() -> {
                appDao.updateOrderStatus(order.getOrderId(), "Shipped");
                runOnUiThread(() -> {
                    Toast.makeText(this, "Order Shipped", Toast.LENGTH_SHORT).show();
                    // Mô phỏng email vận chuyển
                    Toast.makeText(this, "Shipping confirmation sent", Toast.LENGTH_SHORT).show();
                });
            }).start();
        }, 4000); // Delay 4 giây

        btnTrackOrder.setOnClickListener(v -> {
            Toast.makeText(this, "Tracking: Order " + order.getOrderId() + " shipped", Toast.LENGTH_SHORT).show();
        });
    }
}