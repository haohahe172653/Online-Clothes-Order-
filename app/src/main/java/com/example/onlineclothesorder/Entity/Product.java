package com.example.onlineclothesorder.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "products")

public class Product {
    @PrimaryKey(autoGenerate = true)
    private int productId;
    private String name;
    private float price;
    private int stock;
    private String image;

    public Product(String name, float price, int stock, String image) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.image = image;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
