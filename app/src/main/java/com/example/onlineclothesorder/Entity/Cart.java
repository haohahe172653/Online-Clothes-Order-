package com.example.onlineclothesorder.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "carts",
        foreignKeys = @ForeignKey(entity = Product.class,
                parentColumns = "productId",
                childColumns = "productId",
                onDelete = ForeignKey.CASCADE)
)
public class Cart {
    @PrimaryKey(autoGenerate = true)
    private int cartId;
    private int productId;
    private int quantity;

    public Cart(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
