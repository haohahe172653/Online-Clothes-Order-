package com.example.onlineclothesorder.Holder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineclothesorder.R;

public class ProductViewHolder extends RecyclerView.ViewHolder {
    public TextView tvProductName, tvProductPrice;
    public Button btnAddToCart;
    public ProductViewHolder(@NonNull View itemView){
        super(itemView);
        tvProductName = itemView.findViewById(R.id.tvProductName);
        tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
        btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
    }

}
