package com.example.onlineclothesorder.Holder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineclothesorder.R;
import com.google.android.material.button.MaterialButton;

public class ProductViewHolder extends RecyclerView.ViewHolder {
    public TextView tvProductName, tvProductPrice;
    public ImageView ivProductImage;
    public MaterialButton btnAddToCart, btnEditProduct, btnDeleteProduct;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        tvProductName = itemView.findViewById(R.id.tvProductName);
        tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
        ivProductImage = itemView.findViewById(R.id.ivProductImage);
        btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        btnEditProduct = itemView.findViewById(R.id.btnEditProduct);
        btnDeleteProduct = itemView.findViewById(R.id.btnDeleteProduct);
    }
}
