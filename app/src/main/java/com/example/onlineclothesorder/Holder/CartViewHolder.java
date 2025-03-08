package com.example.onlineclothesorder.Holder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineclothesorder.R;

public class CartViewHolder extends RecyclerView.ViewHolder {
    public TextView tvCartProductName, tvCartQuantity;
    public Button btnRemove;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        tvCartProductName = itemView.findViewById(R.id.tvCartProductName);
        tvCartQuantity = itemView.findViewById(R.id.tvCartQuantity);
        btnRemove = itemView.findViewById(R.id.btnRemove);
    }
}
