package com.example.onlineclothesorder.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineclothesorder.DAO.AppDAO;
import com.example.onlineclothesorder.DAO.AppDatabase;
import com.example.onlineclothesorder.DAO.DatabaseClient;
import com.example.onlineclothesorder.Entity.Order;
import com.example.onlineclothesorder.R;

import java.util.ArrayList;
import java.util.List;

public class OrderListActivity extends AppCompatActivity {
    private AppDatabase db;
    private AppDAO appDao;
    private OrderAdapter orderAdapter;
    private List<Order> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        db = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
        appDao = db.appDAO();

        RecyclerView recyclerViewOrders = findViewById(R.id.recyclerViewOrders);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(this));
        orderList = new ArrayList<>();
        orderAdapter = new OrderAdapter(this, orderList);
        recyclerViewOrders.setAdapter(orderAdapter);

        loadOrders();
    }

    private void loadOrders() {
        new Thread(() -> {
            orderList.clear();
            orderList.addAll(appDao.getAllOrders());
            runOnUiThread(() -> orderAdapter.updateData(orderList));
        }).start();
    }

    private class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> {
        private List<Order> orders;
        private Activity activity;

        public OrderAdapter(Activity activity, List<Order> orders) {
            this.activity = activity;
            this.orders = orders;
        }

        @NonNull
        @Override
        public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
            return new OrderViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
            Order order = orders.get(position);
            holder.tvOrderId.setText(String.valueOf(order.getOrderId()));
            holder.tvOrderDate.setText(order.getOrderDate());
            holder.tvTotalAmount.setText(String.format("$%.2f", order.getTotalAmount()));
            holder.tvStatus.setText(order.getStatus());

            holder.btnUpdateStatus.setOnClickListener(v -> {
                new Thread(() -> {
                    String newStatus = order.getStatus().equals("Pending") ? "Paid" : "Shipped";
                    order.setStatus(newStatus);
                    appDao.updateOrder(order);
                    activity.runOnUiThread(() -> {
                        orders.set(position, order);
                        notifyItemChanged(position);
                        Toast.makeText(activity, "Status updated to " + newStatus, Toast.LENGTH_SHORT).show();
                    });
                }).start();
            });

            holder.btnDeleteOrder.setOnClickListener(v -> {
                new Thread(() -> {
                    appDao.deleteOrder(order);
                    activity.runOnUiThread(() -> {
                        orders.remove(position);
                        notifyItemRemoved(position);
                        Toast.makeText(activity, "Order deleted", Toast.LENGTH_SHORT).show();
                    });
                }).start();
            });
        }

        @Override
        public int getItemCount() {
            return orders.size();
        }

        public void updateData(List<Order> newOrders) {
            this.orders.clear();
            this.orders.addAll(newOrders);
            notifyDataSetChanged();
        }
    }

    private class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvOrderDate, tvTotalAmount, tvStatus;
        Button btnUpdateStatus, btnDeleteOrder;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvTotalAmount = itemView.findViewById(R.id.tvTotalAmount);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            btnUpdateStatus = itemView.findViewById(R.id.btnUpdateStatus);
            btnDeleteOrder = itemView.findViewById(R.id.btnDeleteOrder);
        }
    }
}

