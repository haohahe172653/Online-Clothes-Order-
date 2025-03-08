package com.example.onlineclothesorder.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineclothesorder.Activity.CartActivity;
import com.example.onlineclothesorder.DAO.AppDAO;
import com.example.onlineclothesorder.DAO.AppDatabase;
import com.example.onlineclothesorder.DAO.DatabaseClient;
import com.example.onlineclothesorder.Entity.Cart;
import com.example.onlineclothesorder.Entity.Product;
import com.example.onlineclothesorder.Holder.CartViewHolder;
import com.example.onlineclothesorder.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {
    private List<Cart> carts;
    private AppDatabase db;
    private AppDAO appDao;
    private Activity activity;

    public CartAdapter(Activity activity, List<Cart> carts) {
        this.activity = activity;
        this.carts = carts;
        this.db = DatabaseClient.getInstance(activity.getApplicationContext()).getAppDatabase();
        this.appDao = db.appDAO();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart cart = carts.get(position);
        Product product = appDao.getAllProducts().stream()
                .filter(p -> p.getProductId() == cart.getProductId())
                .findFirst().orElse(null);
        if (product != null) {
            holder.tvCartProductName.setText(product.getName());
            holder.etCartQuantity.setText(String.valueOf(cart.getQuantity()));

            holder.btnUpdateQuantity.setOnClickListener(v -> {
                String quantityStr = holder.etCartQuantity.getText().toString().trim();
                if (quantityStr.isEmpty()) {
                    Toast.makeText(activity, "Enter quantity", Toast.LENGTH_SHORT).show();
                    return;
                }
                int quantity = Integer.parseInt(quantityStr);
                if (quantity <= 0) {
                    Toast.makeText(activity, "Quantity must be greater than 0", Toast.LENGTH_SHORT).show();
                    return;
                }
                cart.setQuantity(quantity);
                new Thread(() -> {
                    appDao.updateCart(cart);
                    activity.runOnUiThread(() -> {
                        Toast.makeText(activity, "Quantity updated", Toast.LENGTH_SHORT).show();
                        // Cập nhật lại tổng tiền trong CartActivity
                        ((CartActivity) activity).loadCart();
                    });
                }).start();
            });

            holder.btnRemove.setOnClickListener(v -> {
                new Thread(() -> {
                    appDao.deleteCart(cart);
                    activity.runOnUiThread(() -> {
                        carts.remove(position);
                        notifyItemRemoved(position);
                        Toast.makeText(activity, "Removed from Cart", Toast.LENGTH_SHORT).show();
                        ((CartActivity) activity).loadCart(); // Cập nhật tổng tiền
                    });
                }).start();
            });
        }
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public void updateData(List<Cart> newCarts) {
        this.carts.clear();
        this.carts.addAll(newCarts);
        notifyDataSetChanged();
    }
}