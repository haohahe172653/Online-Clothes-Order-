package com.example.onlineclothesorder.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
    private Activity activity;

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
        // Hiển thị hình ảnh sản phẩm (hiện tại dùng placeholder, sau này có thể dùng Glide)
        // holder.ivProductImage.setImageResource(R.drawable.placeholder_image);

        holder.btnAddToCart.setOnClickListener(v -> {
            new Thread(() -> {
                appDao.insertCart(new Cart(product.getProductId(), 1));
                activity.runOnUiThread(() -> Toast.makeText(activity, "Added to Cart", Toast.LENGTH_SHORT).show());
            }).start();
        });

        holder.btnEditProduct.setOnClickListener(v -> showProductDialog(product, position));

        holder.btnDeleteProduct.setOnClickListener(v -> {
            new Thread(() -> {
                appDao.deleteProduct(product);
                activity.runOnUiThread(() -> {
                    products.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(activity, "Product deleted", Toast.LENGTH_SHORT).show();
                });
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

    public void showProductDialog(Product product, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_product, null);
        builder.setView(dialogView);

        EditText etProductName = dialogView.findViewById(R.id.etProductName);
        EditText etProductPrice = dialogView.findViewById(R.id.etProductPrice);
        EditText etProductStock = dialogView.findViewById(R.id.etProductStock);
        EditText etProductImage = dialogView.findViewById(R.id.etProductImage);
        com.google.android.material.button.MaterialButton btnSave = dialogView.findViewById(R.id.btnSave);

        if (product != null) {
            etProductName.setText(product.getName());
            etProductPrice.setText(String.valueOf(product.getPrice()));
            etProductStock.setText(String.valueOf(product.getStock()));
            etProductImage.setText(product.getImage());
        }

        AlertDialog dialog = builder.create();

        btnSave.setOnClickListener(v -> {
            String name = etProductName.getText().toString().trim();
            String priceStr = etProductPrice.getText().toString().trim();
            String stockStr = etProductStock.getText().toString().trim();
            String image = etProductImage.getText().toString().trim();

            if (name.isEmpty() || priceStr.isEmpty() || stockStr.isEmpty()) {
                Toast.makeText(activity, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            float price;
            int stock;
            try {
                price = Float.parseFloat(priceStr);
                stock = Integer.parseInt(stockStr);
            } catch (NumberFormatException e) {
                Toast.makeText(activity, "Invalid price or stock", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {
                if (product == null) {
                    Product newProduct = new Product(name, price, stock, image.isEmpty() ? null : image);
                    appDao.insertProduct(newProduct);
                    activity.runOnUiThread(() -> {
                        products.add(newProduct);
                        notifyItemInserted(products.size() - 1);
                        Toast.makeText(activity, "Product added", Toast.LENGTH_SHORT).show();
                    });
                } else {
                    product.setName(name);
                    product.setPrice(price);
                    product.setStock(stock);
                    product.setImage(image.isEmpty() ? null : image);
                    appDao.updateProduct(product);
                    activity.runOnUiThread(() -> {
                        products.set(position, product);
                        notifyItemChanged(position);
                        Toast.makeText(activity, "Product updated", Toast.LENGTH_SHORT).show();
                    });
                }
                dialog.dismiss();
            }).start();
        });

        dialog.show();
    }
}