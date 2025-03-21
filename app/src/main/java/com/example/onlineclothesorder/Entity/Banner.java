package com.example.onlineclothesorder.Entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "banners")
public class Banner {
    @PrimaryKey(autoGenerate = true)
    private int bannerId;
    private String image;


    public Banner( String image) {
        this.image = image;
    }


    public int getBannerId() {
        return bannerId;
    }


    public void setBannerId(int bannerId) {
        this.bannerId = bannerId;
    }


    public String getImage() {
        return image;
    }


    public void setImage(String image) {
        this.image = image;
    }
}

