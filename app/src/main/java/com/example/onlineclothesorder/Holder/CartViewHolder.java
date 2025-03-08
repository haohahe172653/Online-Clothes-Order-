package com.example.onlineclothesorder.Holder;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineclothesorder.R;
import com.google.android.material.textfield.TextInputEditText;

public class CartViewHolder extends RecyclerView.ViewHolder {
    public TextView tvCartProductName;
    public TextInputEditText etCartQuantity; // Đổi thành TextInputEditText
    public Button btnUpdateQuantity, btnRemove;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        tvCartProductName = itemView.findViewById(R.id.tvCartProductName);
        etCartQuantity = itemView.findViewById(R.id.etCartQuantity);
        btnUpdateQuantity = itemView.findViewById(R.id.btnUpdateQuantity);
        btnRemove = itemView.findViewById(R.id.btnRemove);
    }
}
