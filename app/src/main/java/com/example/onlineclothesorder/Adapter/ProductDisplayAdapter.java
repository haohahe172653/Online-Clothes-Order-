package com.example.onlineclothesorder.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.onlineclothesorder.Entity.Product;
import com.example.onlineclothesorder.R;
import java.util.List;


public class ProductDisplayAdapter extends RecyclerView.Adapter<ProductDisplayAdapter.ProductViewHolder> {
    private Context context;
    private List<Product> productList;


    public ProductDisplayAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_home, parent, false);
        return new ProductViewHolder(view);
    }
    public void updateProducts(List<Product> newProducts) {
        this.productList = newProducts;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productName.setText(product.getName());
        holder.productPrice.setText(product.getPrice() + "đ");


        Glide.with(context)
                .load(product.getImage())
                .placeholder(R.drawable.placeholder_image)
                .into(holder.productImage);
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice;


        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
        }
    }
}

